package com.annotation.service.impl;

import com.annotation.dao.LabelMapper;
import com.annotation.dao.TaskDocumentMapper;
import com.annotation.dao.TaskLabelMapper;
import com.annotation.dao.TaskMapper;
import com.annotation.model.*;
import com.annotation.model.entity.TaskInfoEntity;
import com.annotation.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * 添加任务
     * @param task
     * @param user
     * @param docid
     * @param labels
     * @return
     */
    @Transactional
    public int addTask(Task task,User user,int docid,String labels){

        task.setUserid(user.getId());
        int taskRes=taskMapper.insertTask(task);//插入任务

        //插入任务表失败返回-1
        if(taskRes == -1){
            return -1;
        }

        //任务表插入成功，继续插入关系表
        TaskDocument taskDocument =new TaskDocument();
        taskDocument.setDocumentid(docid);
        taskDocument.setTaskid(task.getTid());
        int task_docRes = taskAssociateDocumentMapper.insertTaskDocument(taskDocument);//插入关系表

        //插入任务-文件 关系表，失败返回-2
        if(task_docRes == -1){
            //todo:删除已经插入的任务表
            return -2;
        }

        //继续插入标签表
        String[] labelArr=labels.split("#");
        for(int i=0;i<labelArr.length;i++){
            Label label=new Label();
            label.setLabelname(labelArr[i]);

            //查询该标签是否已经存在
            Label selectLabel =labelMapper.selectLabel(labelArr[i]);

            int labelId;
            //查询成功，则返回标签ID进行下一步插入
            if(selectLabel == null){
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
        }else if(task.getType().equals("文本配对标注")){
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

}





