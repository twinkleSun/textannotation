package com.annotation.service.impl;

import com.annotation.dao.LabelMapper;
import com.annotation.dao.DocumentMapper;
import com.annotation.dao.ContentMapper;
import com.annotation.dao.TaskMapper;
import com.annotation.dao.DoTaskMapper;
import com.annotation.dao.DoTaskDetailMapper;
import com.annotation.model.*;
import com.annotation.service.IDoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
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

}





