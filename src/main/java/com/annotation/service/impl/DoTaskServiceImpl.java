package com.annotation.service.impl;

import com.annotation.dao.*;
import com.annotation.model.*;
import com.annotation.service.IDoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    DtdItemLabelMapper dtdItemLabelMapper;
    @Autowired
    DtdItemRelationMapper dtdItemRelationMapper;
    @Autowired
    InstanceMapper instanceMapper;

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
     * todo:有点问题，数据库表和逻辑不太对
     * @param dtInstance
     * @param userId
     * @param itemId
     * @param labelId
     * @param itemLabel
     * @return
     */
    @Transactional
    public int addInstanceItem(DtInstance dtInstance,int userId,int itemId,int labelId,String itemLabel){
        dtInstance.setUserId(userId);

        DtInstance isDtInstance = dtInstanceMapper.selectDtInstance(userId,dtInstance.getTaskId(),dtInstance.getInstanceId());
        int dtInstId=-1;

        if(isDtInstance==null){
            //如果做任务表不存在则插入
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

        DtdItemLabel dtdItemLabel = new DtdItemLabel();

        dtdItemLabel.setDtInstId(dtInstId);
        dtdItemLabel.setItemId(itemId);
        dtdItemLabel.setItemLabel(itemLabel);
        dtdItemLabel.setLabelId(labelId);
        int dtdItemLabelRes = dtdItemLabelMapper.insert(dtdItemLabel);

        if(dtdItemLabelRes == -1){
            return -2;
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
    public int addListItem(DtInstance dtInstance,int userId,int aListItemId,int bListItemId){
        dtInstance.setUserId(userId);

        DtInstance dtInstanceR = dtInstanceMapper.selectDtInstance(userId,dtInstance.getTaskId(),dtInstance.getInstanceId());
        int dtInstId=-1;

        if(dtInstanceR==null){
            //如果做任务表不存在则插入
            int dtInstanceRes = dtInstanceMapper.insert(dtInstance);//插入做任务表

            //插入做任务表失败返回-1
            if(dtInstanceRes == -1){
                return -1;
            }else{
                dtInstId = dtInstance.getDtInstid();
            }
        }else{
            //已经存在就不用再插入了
            dtInstId = dtInstanceR.getDtInstid();
        }

        DtdItemRelation dtdItemRelation = new DtdItemRelation();
        
        DtdItemRelation dtdItemRelation1 =dtdItemRelationMapper.selectDtItemRelation(dtInstId,aListItemId,bListItemId);
        if(dtdItemRelation1==null){
            dtdItemRelation.setDtInstId(dtInstId);
            dtdItemRelation.setaListitemId(aListItemId);
            dtdItemRelation.setbListitemId(bListItemId);
            int dtdItemRelationRes = dtdItemRelationMapper.insert(dtdItemRelation);
            if(dtdItemRelationRes == -1){
                return -2;
            }
        }else{
            //todo:返回值问题
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
}





