package com.annotation.service.impl;

import com.annotation.dao.*;
import com.annotation.model.*;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.service.IDoTaskService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/9.
 */

@Repository
public class DoTaskServiceImpl implements IDoTaskService{

    @Autowired
    TaskMapper taskMapper;
    @Autowired
    DoTaskMapper dotaskMapper;
    @Autowired
    DoTaskDetailMapper doTaskDetailMapperr;
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    DocumentMapper documentMapper;
    @Autowired
    DtInstanceMapper dtInstanceMapper;
    @Autowired
    DtdItemRelationMapper dtdItemRelationMapper;
    @Autowired
    InstanceMapper instanceMapper;
    @Autowired
    DtdItemLabelMapper dtdItemLabelMapper;

    @Autowired
    DtdItemSortingMapper dtdItemSortingMapper;

    /**
     * 添加做任务表
     * @param dotask
     * @param userid
     * @param labelId
     * @param conbegin
     * @param conend
     * @return
     */
    public int addDoTask(DoTask dotask, String userid, int labelId, int conbegin,int conend){

        dotask.setUserid(Integer.parseInt(userid));

        //先判断插入标签的content有没有之前有没有插入过标签，有就不用再新建dotask表了
        DoTask doTaskselect = dotaskMapper.selectTask(Integer.parseInt(userid),dotask.getTaskid(),dotask.getContentid());
        int dotaskID=-1;

        if(doTaskselect==null){
            //如果做任务表不存在则插入

            //设置时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            dotask.setDotime(df.format(new Date()));
            dotaskMapper.alterDotaskTable();
            int dotaskRes=dotaskMapper.insertDoTask(dotask);//插入做任务表
            dotaskID = dotask.getDtid();
            //插入做任务表失败返回-1
            if(dotaskRes == -1){
                return -1;
            }
        }else{
            //已经存在就不用再插入了
            dotaskID = doTaskselect.getDtid();
        }

        //做任务表插入成功，继续插入做任务详细信息表
        DoTaskDetail doTaskDetail = new DoTaskDetail();
        //doTaskDetail.setDoTaskid(dotask.getDtid());
        doTaskDetail.setDoTaskid(dotaskID);
        doTaskDetail.setLabelid(labelId);
        doTaskDetail.setContentbegin(conbegin);
        doTaskDetail.setContentend(conend);
        doTaskDetailMapperr.alterDotaskDetailTable();
        int dotask_detRes = doTaskDetailMapperr.insertDoTaskDetail(doTaskDetail);

        if(dotask_detRes == -1){
            return -2;
        }

        //更新任务表的状态
        Task task = taskMapper.selectTaskById(dotask.getTaskid());
        task.setTaskcompstatus("正在进行");
        int updatetask = taskMapper.updateById(task);
        if(updatetask==-1){
            return -3;
        }

        //更新文档的状态
        int documentid = contentMapper.selectContentDocumentId(dotask.getContentid());
        Document document = documentMapper.selectDocumentById(documentid);
        document.setDocstatus("正在进行");
        int updatedocument = documentMapper.updateDocumentById(document);
        if(updatedocument==-1){
            return -4;
        }
        //返回做任务ID
        //return dotask.getDtid();
        return dotaskID;
    }


    /**
     * 做任务----文本关系类型
     * @param dtInstance
     * @param userId
     * @param itemId1
     * @param item1Labels
     * @param itemId2
     * @param item2Labels
     * @return
     */
    @Transactional
    public int addInstanceItem(DtInstance dtInstance,int userId,int itemId1,int[] item1Labels,int itemId2,int[] item2Labels,int[] instanceLabels){
        dtInstance.setUserId(userId);

        DtInstance isDtInstance = dtInstanceMapper.selectDtInstance(userId,dtInstance.getTaskId(),dtInstance.getInstanceId());
        int dtInstId=-1;

        if(isDtInstance==null){
            //如果做任务表不存在则插入

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            dtInstance.setDotime(df.format(new Date()));
            dtInstanceMapper.alterDtInstanceTable();
            int dtInstanceRes=dtInstanceMapper.insert(dtInstance);//插入做任务表
            dtInstId = dtInstance.getDtInstid();
            //插入做任务表失败返回-1
            if(dtInstanceRes == -1){
                return -1;
            }
        }else{
            //已经存在就不用再插入了
            dtInstId = isDtInstance.getDtInstid();
        }

        if(instanceLabels!=null){
            int iRes0= insertLabels(dtInstId,"instance",instanceLabels);
            if(iRes0==-2){
                return -2;
            }
        }


        if(item1Labels!=null){
            int iRes0= insertLabels(dtInstId,"item1",item1Labels);
            if(iRes0==-2){
                return -2;
            }
        }


        if(item2Labels!=null){
            int iRes0= insertLabels(dtInstId,"item2",item2Labels);
            if(iRes0==-2){
                return -2;
            }
        }

        //更新任务表的状态
        Task task = taskMapper.selectTaskById(dtInstance.getTaskId());
        task.setTaskcompstatus("正在进行");
        int updatetask = taskMapper.updateById(task);
        if(updatetask==-1){
            return -3;
        }

        //更新文档的状态
        int updatedocument = documentMapper.updateDocStatusByInstanceId(dtInstance.getInstanceId(),"正在进行");
        if(updatedocument==-1){
            return -4;
        }
        //返回做任务ID
        return dtInstId;
    }

    /**
     * 做任务---文本关系配对
     * todo:更新状态的逻辑
     * todo:了解数据库事务
     * @param dtInstance
     * @param userId
     * @param aListItemId
     * @param bListItemId
     * @return
     */
    @Transactional
    public String addListItem(DtInstance dtInstance,int userId,int[] aListItemId,int[] bListItemId,String taskType){
        dtInstance.setUserId(userId);

        DtInstance dtInstanceR = dtInstanceMapper.selectDtInstance(userId,dtInstance.getTaskId(),dtInstance.getInstanceId());
        int dtInstId=-1;

        if(dtInstanceR==null){
            //如果做任务表不存在则插入
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            dtInstance.setDotime(df.format(new Date()));

            dtInstanceMapper.alterDtInstanceTable();
            int dtInstanceRes = dtInstanceMapper.insert(dtInstance);//插入做任务表

            //插入做任务表失败返回-1
            if(dtInstanceRes == -1){
                return "-1";
            }else{
                dtInstId = dtInstance.getDtInstid();
            }
        }else{
            //已经存在就不用再插入了
            dtInstId = dtInstanceR.getDtInstid();
        }

        if(taskType.equals("文本配对标注#一对一")){
            String iRes=insertOneToOneRelations(dtInstId,aListItemId,bListItemId);
            if(!iRes.equals("0")){
                return iRes;
            }
        }else if(taskType.equals("文本配对标注#一对多")){
            String iRes=insertOneToManyRelations(dtInstId,aListItemId,bListItemId);
            if(!iRes.equals("0")){
                return iRes;
            }
        }else if(taskType.equals("文本配对标注#多对多")){
            String iRes=insertManyToManyRelations(dtInstId,aListItemId,bListItemId);
            if(!iRes.equals("0")){
                return iRes;
            }
        }

//        DtdItemRelation dtdItemRelation1 =dtdItemRelationMapper.selectDtItemRelation(dtInstId,aListItemId,bListItemId);
//        if(dtdItemRelation1==null){
//            dtdItemRelation.setDtInstId(dtInstId);
//            dtdItemRelation.setaListitemId(aListItemId);
//            dtdItemRelation.setbListitemId(bListItemId);
//            int dtdItemRelationRes = dtdItemRelationMapper.insert(dtdItemRelation);
//            if(dtdItemRelationRes == -1){
//                return -2;
//            }
//        }else{
//            //todo:返回值问题
//        }

        //更新任务表的状态
        Task task = taskMapper.selectTaskById(dtInstance.getTaskId());
        task.setTaskcompstatus("正在进行");
        int updatetask = taskMapper.updateById(task);
        if(updatetask==-1){
            return "-3";
        }

        //更新文档的状态
        int updatedocument = documentMapper.updateDocStatusByInstanceId(dtInstance.getInstanceId(),"正在进行");
        if(updatedocument==-1){
            return "-4";
        }
        //返回做任务ID
        return dtInstId+"";
    }

    /**
     * 插入dtdItemLabel表的操作
     * 重复则不添加，数据库语句实现
     * @param dtInstId
     * @param labeltype
     * @param itemLabels
     * @return
     */
    public int insertLabels(int dtInstId,String labeltype,int[] itemLabels){
//        Map<String, Object> map = new HashMap<>();
//        map.put("itemLabels", ArrayUtils.toObject(itemLabels));
//        map.put("dtInstId", dtInstId);
//        map.put("labeltype", labeltype);

        dtdItemLabelMapper.alterDtdItemLabelTable();
        int res=dtdItemLabelMapper.insertLabelList(dtInstId,labeltype,itemLabels);

        return res;
    }



    public String insertOneToOneRelations(int dtInstId,int[] aListitemId,int[] bListitemId){

        StringBuffer sb=new StringBuffer();

        sb.append(0);
        dtdItemRelationMapper.alterDtdItemRelationTable();
        for(int i=0;i<aListitemId.length;i++){
            int dtdItemRelationRes = dtdItemRelationMapper.insertRelationListByOneToOne(dtInstId,aListitemId[i],bListitemId[i]);
            if(dtdItemRelationRes != 1){
                sb=sb.append(i+"#");
            }
        }
        return sb.toString();
    }


    public String insertOneToManyRelations(int dtInstId,int[] aListitemId,int[] bListitemId){

        StringBuffer sb=new StringBuffer();

        sb.append(0);
        dtdItemRelationMapper.alterDtdItemRelationTable();
        for(int i=0;i<aListitemId.length;i++){
            int dtdItemRelationRes = dtdItemRelationMapper.insertRelationListByOneToMany(dtInstId,aListitemId[i],bListitemId[i]);
            if(dtdItemRelationRes != 1){
                sb=sb.append(i+"#");
            }
        }
        return sb.toString();
    }


    public String insertManyToManyRelations(int dtInstId,int[] aListitemId,int[] bListitemId){

        StringBuffer sb=new StringBuffer();

        sb.append(0);
        dtdItemRelationMapper.alterDtdItemRelationTable();
        for(int i=0;i<aListitemId.length;i++){
            int dtdItemRelationRes = dtdItemRelationMapper.insertRelationListByManyToMany(dtInstId,aListitemId[i],bListitemId[i]);
            if(dtdItemRelationRes != 1){
                sb=sb.append(i+"#");
            }
        }
        return sb.toString();
    }



    /**
     * 添加做任务表
     * @param dotask
     * @param userid
     * @param labelId
     * @param conbegin
     * @param conend
     * @return
     */
    public ResponseEntity addClassifyTask(DoTask dotask, int userid, int[] labelId, int conbegin, int conend){

        ResponseEntity responseEntity=new ResponseEntity();

        //先判断插入标签的content有没有之前有没有插入过标签，有就不用再新建dotask表了
        DoTask doTaskselect = dotaskMapper.selectTask(userid,dotask.getTaskid(),dotask.getContentid());
        int dotaskID=-1;

        if(doTaskselect==null){
            //如果做任务表不存在则插入
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            dotask.setDotime(df.format(new Date()));
            dotaskMapper.alterDotaskTable();
            int dotaskRes=dotaskMapper.insertDoTask(dotask);//插入做任务表
            dotaskID = dotask.getDtid();
            //插入做任务表失败返回-1
            if(dotaskRes == -1){
                responseEntity.setMsg("添加做任务失败，请检查");
                responseEntity.setStatus(-1);
                return responseEntity;
            }
        }else{
            //已经存在就不用再插入了
            dotaskID = doTaskselect.getDtid();
        }

        //做任务表插入成功，继续插入做任务详细信息表
        DoTaskDetail doTaskDetail = new DoTaskDetail();
        //doTaskDetail.setDoTaskid(dotask.getDtid());
        doTaskDetail.setDoTaskid(dotaskID);
        //doTaskDetail.setLabelid(labelId);
        doTaskDetail.setContentbegin(conbegin);
        doTaskDetail.setContentend(conend);
        String dotask_detRes =insertClassifyLabels(doTaskDetail,labelId);

//        int dotask_detRes = doTaskDetailMapperr.insertDoTaskDetail(doTaskDetail);
//
        if(!dotask_detRes.equals("0#")){
            responseEntity.setStatus(-2);
            responseEntity.setMsg("添加做任务详细信息失败");
            responseEntity.setData(dotask_detRes);
            return responseEntity;

        }

        //更新任务表的状态
        Task task = taskMapper.selectTaskById(dotask.getTaskid());
        task.setTaskcompstatus("正在进行");
        int updatetask = taskMapper.updateById(task);
        if(updatetask==-1){
            responseEntity.setStatus(-3);
            responseEntity.setMsg("更新任务状态失败");
            return responseEntity;
        }

        //更新文档的状态
        int documentid = contentMapper.selectContentDocumentId(dotask.getContentid());
        Document document = documentMapper.selectDocumentById(documentid);
        document.setDocstatus("正在进行");
        int updatedocument = documentMapper.updateDocumentById(document);
        if(updatedocument==-1){
            responseEntity.setStatus(-4);
            responseEntity.setMsg("更新文档状态失败");
            return responseEntity;
        }
        //返回做任务ID
        //return dotask.getDtid();
        responseEntity.setStatus(0);
        responseEntity.setMsg("添加做任务表成功");
        Map<String, Object> data = new HashMap<>();
        data.put("dotaskid", dotaskID);//返回做任务id
        responseEntity.setData(data);

        return responseEntity;
    }


    public String insertClassifyLabels( DoTaskDetail doTaskDetail,int[] labelId){

        StringBuffer sb=new StringBuffer();


        sb.append(0+"#");
        doTaskDetailMapperr.alterDotaskDetailTable();
        for(int i=0;i<labelId.length;i++){
            doTaskDetail.setLabelid(labelId[i]);
            int dotask_detRes = doTaskDetailMapperr.insertDoTaskDetail(doTaskDetail);

            if(dotask_detRes == -1){
                sb=sb.append(labelId[i]+"#");
            }

        }
        return sb.toString();
    }


    public ResponseEntity addSortingInstanceItem(DtInstance dtInstance, int userId,int[] itemIds,int[] newIndexs){
        dtInstance.setUserId(userId);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setStatus(0);
        DtInstance isDtInstance = dtInstanceMapper.selectDtInstance(userId,dtInstance.getTaskId(),dtInstance.getInstanceId());
        int dtInstId=-1;

        if(isDtInstance==null){
            //如果做任务表不存在则插入
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            dtInstance.setDotime(df.format(new Date()));
            dtInstanceMapper.alterDtInstanceTable();
            int dtInstanceRes=dtInstanceMapper.insert(dtInstance);//插入做任务表
            dtInstId = dtInstance.getDtInstid();
            //插入做任务表失败返回-1
            if(dtInstanceRes == -1){
                responseEntity.setStatus(-1);
                responseEntity.setMsg("添加做任务失败，请检查");
                return responseEntity;
            }
        }else{
            //已经存在就不用再插入了
            dtInstId = isDtInstance.getDtInstid();
        }



        String iRes= insertSortingItem(dtInstId,itemIds,newIndexs);
        if(!iRes.equals("0#")){
            responseEntity.setStatus(-2);
            responseEntity.setMsg("添加失败");
            responseEntity.setData(iRes);
            return responseEntity;
        }


        //更新任务表的状态
        Task task = taskMapper.selectTaskById(dtInstance.getTaskId());
        task.setTaskcompstatus("正在进行");
        int updatetask = taskMapper.updateById(task);
        if(updatetask==-1){
            responseEntity.setStatus(-3);
            responseEntity.setMsg("更新任务状态失败");
            return responseEntity;
        }

        //更新文档的状态
        int updatedocument = documentMapper.updateDocStatusByInstanceId(dtInstance.getInstanceId(),"正在进行");
        if(updatedocument==-1){
            responseEntity.setStatus(-4);
            responseEntity.setMsg("更新文档状态失败");
            return responseEntity;
        }
        //返回做任务ID

        responseEntity.setMsg("添加做任务表成功");
        Map<String, Object> data = new HashMap<>();
        data.put("dtInstid", dtInstId);//返回做任务id
        responseEntity.setData(data);
        return responseEntity;
    }



    public String insertSortingItem(int dtInstId,int[] itemIds,int[] newIndexs){

        StringBuffer sb=new StringBuffer();

        sb.append(0+"#");
        dtdItemSortingMapper.alterDtdItemSortingTable();
        for(int i=0;i<itemIds.length;i++){
            int dtdItemRelationRes = dtdItemSortingMapper.insertSortingItem(dtInstId,itemIds[i],newIndexs[i]);
            if(dtdItemRelationRes != 1 && dtdItemRelationRes != 2){
                sb=sb.append(i+"#");
            }
        }
        return sb.toString();
    }
}





