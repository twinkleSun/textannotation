package com.annotation.service.impl;

import com.annotation.dao.*;
import com.annotation.model.*;
import com.annotation.model.entity.MyPubTaskByDoing;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.model.entity.TaskInfoEntity;
import com.annotation.model.InstaLabel;
import com.annotation.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * Created by twinkleStar on 2018/12/9.
 */

@Repository
public class TaskServiceImpl implements ITaskService{

    @Autowired
    TaskMapper taskMapper;
    @Autowired
    TaskDocumentMapper taskAssociateDocumentMapper;
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    TaskLabelMapper taskLabelMapper;
    @Autowired
    InstanceLabelMapper instanceLabelMapper;
    @Autowired
    DoTaskMapper doTaskMapper;

    /**
     * 信息抽取和分类添加任务
     * @param task
     * @param user
     * @param docids
     * @param labels
     * @return
     */
    @Transactional
    public int addTask(Task task, User user, List<Integer> docids, String labels,String colors){

        task.setUserid(user.getId());
        taskMapper.alterTaskTable();
        int taskRes=taskMapper.insertTask(task);//插入任务

        //插入任务表失败返回-1
        if(taskRes == -1){
            return -1;
        }

        //任务表插入成功，继续插入关系表

        for(int i=0;i<docids.size();i++){
            TaskDocument taskDocument =new TaskDocument();
            taskDocument.setDocumentid(docids.get(i));
            taskDocument.setTaskid(task.getTid());
            int task_docRes = taskAssociateDocumentMapper.insertTaskDocument(taskDocument);//插入关系表
            //插入任务-文件 关系表，失败返回-2
            if(task_docRes == -1){
                //todo:删除已经插入的任务表
                return -2;
            }
        }

        //继续插入标签表
        String[] labelArr=labels.split("#");
        String[] colorArr=colors.split("@");
        labelMapper.alterLabelTable();
        for(int i=0;i<labelArr.length;i++){

            //查询该标签是否已经存在
            Label selectLabel =labelMapper.selectLabel(labelArr[i]);

            int labelId;
            //查询成功，则返回标签ID进行下一步插入
            if(selectLabel == null){
                //标签不存在再新建标签
                Label label=new Label();
                label.setLabelname(labelArr[i]);
                int labelRes=labelMapper.insertLabel(label);
                labelId =label.getLid();

                //标签插入失败
                if(labelRes ==-1){
                    //todo:删除已经插入的标签，或者返回插入失败的这个标签，这里逻辑有点问题
                    return -3;
                }

            }else{
                labelId=selectLabel.getLid();
            }

            //插入文件-标签关系表
            TaskLabel taskLabel = new TaskLabel();
            taskLabel.setLabelid(labelId);
            taskLabel.setTaskid(task.getTid());
            taskLabel.setColor(colorArr[i]);
            int task_labelRes = taskLabelMapper.insertTaskLabel(taskLabel);

            //文件-标签关系表插入失败
            if(task_labelRes==-1){
                //todo:插入失败需要删除标签或者只返回失败的这个标签，逻辑问题
                return -4;
            }

        }
        //返回任务ID
        return task.getTid();
    }


    /**
     * 两个item添加任务
     * @param task
     * @param user
     * @param docids
     * @param labels
     * @param labelstr1
     * @param labelstr2
     * @return
     */
    @Transactional
    public ResponseEntity  addTaskTwoitems(Task task, User user, List<Integer> docids, String labels,String labelstr1,String labelstr2){

        ResponseEntity responseEntity = new ResponseEntity();
        task.setUserid(user.getId());
        taskMapper.alterTaskTable();
        int taskRes=taskMapper.insertTask(task);//插入任务

        //插入任务表失败返回-1
        if(taskRes == -1){
            responseEntity.setStatus(-1);
            responseEntity.setMsg("添加任务失败，请检查");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }

        //任务表插入成功，继续插入关系表
        for(int i=0;i<docids.size();i++){
            TaskDocument taskDocument =new TaskDocument();
            taskDocument.setDocumentid(docids.get(i));
            taskDocument.setTaskid(task.getTid());
            int task_docRes = taskAssociateDocumentMapper.insertTaskDocument(taskDocument);//插入关系表
            //插入任务-文件 关系表，失败返回-2
            if(task_docRes == -1){
                //todo:删除已经插入的任务表
                responseEntity.setStatus(-2);
                responseEntity.setMsg("任务-文件关系插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return responseEntity;
            }
        }

        //继续插入标签表
        labelMapper.alterLabelTable();
        String[] labelArr=labels.split("#");
        for(int i=0;i<labelArr.length;i++) {

            //查询该标签是否已经存在
            Label selectLabel = labelMapper.selectLabel(labelArr[i]);

            int labelId;
            //查询成功，则返回标签ID进行下一步插入
            if (selectLabel == null) {
                //标签不存在再新建标签
                Label label = new Label();
                label.setLabelname(labelArr[i]);
                int labelRes1 = labelMapper.insertLabel(label);
                labelId = label.getLid();

                //标签插入失败
                if (labelRes1 == -1) {
                    //todo:删除已经插入的标签，或者返回插入失败的这个标签，这里逻辑有点问题
                    responseEntity.setStatus(-3);
                    responseEntity.setMsg("instance标签插入失败");
                    //插入数据库有错误时整体回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return responseEntity;
                }

            } else {
                labelId = selectLabel.getLid();
            }

            //插入文件-标签关系表
            TaskLabel taskLabel = new TaskLabel();
            taskLabel.setLabelid(labelId);
            taskLabel.setTaskid(task.getTid());
            int task_labelRes = taskLabelMapper.insertTaskLabel(taskLabel);

            //文件-标签关系表插入失败
            if (task_labelRes == -1) {
                //todo:插入失败需要删除标签或者只返回失败的这个标签，逻辑问题
                responseEntity.setStatus(-4);
                responseEntity.setMsg("文件-标签关系插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return responseEntity;
            }

        }

        //继续插入insta_label标签表
        String[] labelitemArr1=labelstr1.split("#");
        String[] labelitemArr2=labelstr2.split("#");
        for(int j=0;j<labelitemArr1.length;j++){
            //查询该标签是否已经存在
            Label selectitemLabel1 =labelMapper.selectLabel(labelitemArr1[j]);

            int labelitemId1;
            //查询成功，则返回标签ID进行下一步插入
            if(selectitemLabel1 == null){
               //标签不存在再新建标签
               Label label=new Label();
               label.setLabelname(labelitemArr1[j]);
               int labelRes1=labelMapper.insertLabel(label);
               labelitemId1 =label.getLid();

                //标签插入失败
                if(labelRes1 ==-1){
                 //todo:删除已经插入的标签，或者返回插入失败的这个标签，这里逻辑有点问题
                 responseEntity.setStatus(-5);
                 responseEntity.setMsg("item1标签插入失败");
                 //插入数据库有错误时整体回滚
                 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                 return responseEntity;
                 }

             }else{
                    labelitemId1=selectitemLabel1.getLid();
             }

            //插入insta_label关系表
            InstaLabel instaLabel = new InstaLabel();
            instaLabel.setLebelid(labelitemId1);
            instaLabel.setLebelindex(1);
            instaLabel.setTaskid(task.getTid());
            int insta_labelRes1 = instanceLabelMapper.insertInstanceLabel(instaLabel);
            //文件-标签关系表插入失败
            if(insta_labelRes1==-1){
                //todo:插入失败需要删除标签或者只返回失败的这个标签，逻辑问题
                responseEntity.setStatus(-6);
                responseEntity.setMsg("insta_label1关系插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return responseEntity;
            }
        }

        for(int k=0;k<labelitemArr2.length;k++){
            //查询该标签是否已经存在
            Label selectitemLabel2 =labelMapper.selectLabel(labelitemArr1[k]);

            int labelitemId2;
            //查询成功，则返回标签ID进行下一步插入
            if(selectitemLabel2 == null){
                //标签不存在再新建标签
                Label label=new Label();
                label.setLabelname(labelitemArr1[k]);
                int labelRes2 =labelMapper.insertLabel(label);
                labelitemId2 =label.getLid();

                //标签插入失败
                if(labelRes2 ==-1){
                    //todo:删除已经插入的标签，或者返回插入失败的这个标签，这里逻辑有点问题
                    responseEntity.setStatus(-7);
                    responseEntity.setMsg("item2标签插入失败");
                    //插入数据库有错误时整体回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return responseEntity;
                }

            }else{
                labelitemId2=selectitemLabel2.getLid();
            }

            //插入insta_label关系表
            InstaLabel instaLabel = new InstaLabel();
            instaLabel.setLebelid(labelitemId2);
            instaLabel.setLebelindex(2);
            instaLabel.setTaskid(task.getTid());
            int insta_labelRes2 = instanceLabelMapper.insertInstanceLabel(instaLabel);
            //文件-标签关系表插入失败
            if(insta_labelRes2==-1){
                //todo:插入失败需要删除标签或者只返回失败的这个标签，逻辑问题
                responseEntity.setStatus(-8);
                responseEntity.setMsg("insta_label2关系插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return responseEntity;
            }else{
                responseEntity.setStatus(0);
                responseEntity.setMsg("创建任务成功");
                responseEntity.setData(task.getTid());
                return responseEntity;
            }
        }

        //返回任务ID
        return responseEntity;
    }


    /**
     * 文本匹配添加任务
     * @param task
     * @param user
     * @param docids
     * @return
     */
    @Transactional
    public ResponseEntity  addTaskMatchCategory(Task task, User user, List<Integer> docids){

        ResponseEntity responseEntity = new ResponseEntity();
        task.setUserid(user.getId());
        taskMapper.alterTaskTable();
        int taskRes=taskMapper.insertTask(task);//插入任务

        //插入任务表失败返回-1
        if(taskRes == -1){
            responseEntity.setStatus(-1);
            responseEntity.setMsg("添加任务失败，请检查");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }

        //任务表插入成功，继续插入关系表
        for(int i=0;i<docids.size();i++){
            TaskDocument taskDocument =new TaskDocument();
            taskDocument.setDocumentid(docids.get(i));
            taskDocument.setTaskid(task.getTid());
            int task_docRes = taskAssociateDocumentMapper.insertTaskDocument(taskDocument);//插入关系表
            //插入任务-文件 关系表，失败返回-2
            if(task_docRes == -1){
                //todo:删除已经插入的任务表
                responseEntity.setStatus(-2);
                responseEntity.setMsg("任务-文件关系插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return responseEntity;
            }else{
                responseEntity.setStatus(0);
                responseEntity.setMsg("创建任务成功");
                responseEntity.setData(task.getTid());
                return responseEntity;
            }
        }
        //返回任务ID
        return responseEntity;
    }

    /**
     * 分页查询
     * @param userid
     * @param page
     * @param limit
     * @return
     */
    public List<Task> queryTaskByRelatedInfo(int userid,int page,int limit){
        int startNum =(page-1)*limit;
        Map<String,Object> data =new HashMap();
        data.put("currIndex",startNum);
        data.put("pageSize",limit);
        data.put("userid",userid);
        List<Task> task =taskMapper.selectTaskByRelatedInfo(data);
        return task;
    }

    /**
     * 根据用户ID查询任务总数
     * @param userId
     * @return
     */
    public int countTasknumByUserId(int userId){
        Integer numInt = taskMapper.countTaskNumByUserid(userId);
        if(numInt == null){
            return 0;
        } else{
            return numInt.intValue();
        }
    }

    /**
     * 获取所有的任务
     * @return
     */
    public List<Task> getAll(){
        List<Task> tasks = taskMapper.getAll();
        return tasks;
    }

    /**
     * 分页查询所有的任务
     * @param page
     * @param limit
     * @return
     */
    public List<Task> queryAllTask(int page,int limit){
        int startNum =(page-1)*limit;
        Map<String,Object> data =new HashMap();
        data.put("currIndex",startNum);
        data.put("pageSize",limit);
        List<Task> task =taskMapper.selectAllTask(data);
        return task;
    }


    /**
     * 获取所有任务的数量
     * @return
     */
    public int countAllTasknum(){
        Integer numInt = taskMapper.countAllTaskNum();
        if(numInt == null){
            return 0;
        } else{
            return numInt.intValue();
        }
    }

    /**
     * 根据任务ID获取任务详情
     * 不同任务类型调用不同的接口
     * @param tid
     * @return
     */
    public TaskInfoEntity queryTaskInfo(int tid){
        TaskInfoEntity taskInfoEntity =new TaskInfoEntity();

        //获取task基础信息，其实只要查询类型就可以了
        Task task =taskMapper.selectTaskById(tid);

        /**
         * 信息抽取和分类
         */
        if (task.getType().equals("信息抽取") || task.getType().equals("分类")){
            taskInfoEntity =taskMapper.selectTaskInfoWithDocLabel(tid);

        /**
         * 文本关系类别标注
         */
        }else if(task.getType().equals("文本关系类别标注") ){
            taskInfoEntity =taskMapper.selectTaskInfoWithDoc(tid);

            //如果该任务有标签，则查询并返回标签列表
            if(taskLabelMapper.selectLabelsByTaskid(tid)!=null){
                TaskInfoEntity taskInfoEntity1=taskMapper.selectTaskInfoWithLabel(tid);
                taskInfoEntity.setLabelList(taskInfoEntity1.getLabelList());
            }

        /**
         * 文本配对标注
         */
        }else if(task.getType().contains("文本配对标注")){
            taskInfoEntity =taskMapper.selectTaskInfoWithDoc(tid);
        }else if(task.getType().equals("文本排序")){


            taskInfoEntity =taskMapper.selectTaskInfoWithDoc(tid);
        }else if(task.getType().contains("文本类比排序")){


            taskInfoEntity =taskMapper.selectTaskInfoWithDoc(tid);
        }else{
            //todo：其他类型，待开发
        }

        return taskInfoEntity;
    }

    /**
     * 返回这个任务的发布者姓名
     * @param tid
     * @return
     */
    public String queryUserName(int tid){
        String username=taskMapper.selectUserName(tid);
        return username;
    }
	
	
	    /**
     * 设置数据库自增长为1
     * @return
     */
    public int alterTaskTable(){
        int num = taskMapper.alterTaskTable();
        return num;
    }



    public ResponseEntity  addTaskOneSorting(Task task, User user, List<Integer> docids){

        ResponseEntity responseEntity = new ResponseEntity();
        task.setUserid(user.getId());
        taskMapper.alterTaskTable();
        int taskRes=taskMapper.insertTask(task);//插入任务

        //插入任务表失败返回-1
        if(taskRes == -1){
            responseEntity.setStatus(-1);
            responseEntity.setMsg("添加任务失败，请检查");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }

        //任务表插入成功，继续插入关系表
        for(int i=0;i<docids.size();i++){
            TaskDocument taskDocument =new TaskDocument();
            taskDocument.setDocumentid(docids.get(i));
            taskDocument.setTaskid(task.getTid());
            int task_docRes = taskAssociateDocumentMapper.insertTaskDocument(taskDocument);//插入关系表
            //插入任务-文件 关系表，失败返回-2
            if(task_docRes == -1){
                //todo:删除已经插入的任务表
                responseEntity.setStatus(-2);
                responseEntity.setMsg("任务-文件关系插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return responseEntity;
            }
        }

        //返回任务ID
        return responseEntity;
    }


    public List<MyPubTaskByDoing> queryMyPubTaskByRelatedInfo(int userid, int taskId,int page, int limit,String taskType){
        int startNum =(page-1)*limit;


        /**
         * 信息抽取和分类
         */
        if (taskType.equals("信息抽取") || taskType.equals("分类")){
            List<MyPubTaskByDoing> myPubTask =doTaskMapper.selectMyDoingPubTaskInfo(userid,taskId,startNum,limit);
            return myPubTask;

            /**
             * 文本关系类别标注
             */
        }else if(taskType.equals("文本关系类别标注")|| taskType.contains("文本配对标注")|| taskType.equals("文本排序")||taskType.contains("文本类比排序")){
            List<MyPubTaskByDoing> myPubTask =doTaskMapper.selectMyDoingPubTaskInfoInst(userid,taskId,startNum,limit);
            return myPubTask;

        }else{
            //todo:这里不是这么写的
            List<MyPubTaskByDoing> myPubTask =doTaskMapper.selectMyDoingPubTaskInfoInst(userid,taskId,startNum,limit);
            return myPubTask;
        }


    }


    public List<MyPubTaskByDoing> queryTaskIPartIn(int userid, String dtstatus,int page, int limit){
        int startNum =(page-1)*limit;

        List<MyPubTaskByDoing> myPubTask1 =doTaskMapper.selectTaskIPartIn(userid,dtstatus,startNum,limit);

        List<MyPubTaskByDoing> myPubTask2 =doTaskMapper.selectTaskIPartInInstance(userid,dtstatus,startNum,limit);


        myPubTask1.addAll(myPubTask2);
        return myPubTask1;

    }


}





