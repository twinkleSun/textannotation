package com.annotation.service.impl;

import com.annotation.dao.*;
import com.annotation.model.*;
import com.annotation.model.entity.ParagraphLabelEntity;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.service.IDParagraphService;
import com.annotation.service.IDTaskService;
import com.annotation.service.IDtClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2019/2/2.
 */
@Repository
public class DtClassifyServiceImpl implements IDtClassifyService {

    @Autowired
    DtClassifyMapper dtClassifyMapper;
    @Autowired
    IDTaskService idTaskService;
    @Autowired
    IDParagraphService idParagraphService;
    @Autowired
    DTaskMapper dTaskMapper;
    @Autowired
    DParagraphMapper dParagraphMapper;
    @Autowired
    ParagraphMapper paragraphMapper;


    /**
     * 查询content+label
     * @param docId
     * @return
     */
    public List<ParagraphLabelEntity> queryClassifyParaLabel(int docId, int userId,String status,int taskId){


        DTask dTask=dTaskMapper.selectByTaskIdAndUserId(taskId,userId);
        if(dTask!=null){
            if(status.equals("全部")){
                List<ParagraphLabelEntity> contentLabelEntityList=dtClassifyMapper.selectClassifyParaLabel(docId,userId,dTask.getTkid());
                return contentLabelEntityList;
            }else{
                Map<String,Object> data =new HashMap();
                data.put("docId",docId);
                data.put("userId",userId);
                data.put("status",status);
                data.put("dTaskId",dTask.getTkid());
                List<ParagraphLabelEntity> contentLabelEntityList=dtClassifyMapper.selectClassifyWithStatus(data);
                return contentLabelEntityList;
            }
        }else{
            List<ParagraphLabelEntity> contentLabelEntityList=dtClassifyMapper.selectClassify(docId);
            return contentLabelEntityList;
        }




        //List<ParagraphLabelEntity> contentLabelEntityList=dtClassifyMapper.selectClassifyWithoutDocId(14,userId);

    }


    /**
     *
     * @param userId
     * @param taskId
     * @param docId
     * @param paraId
     * @param labelId
     * @return
     */
    public int addClassify(int userId,int taskId,int docId,int paraId,int[] labelId){

        //先判断d_task表有没有插入

        int dTaskId=idTaskService.addDTaskOfPara(userId,taskId);
        if(dTaskId==4001|| dTaskId==4005){
            return dTaskId;
        }

        int dtid=idParagraphService.addDParagraph(dTaskId,docId,paraId);
        if(dtid==4002){
            return dtid;
        }

        dtClassifyMapper.alterDtClassifyTable();
        for(int i=0;i<labelId.length;i++){
            DtClassify dtClassify =new DtClassify();
            dtClassify.setDtId(dtid);
            dtClassify.setLabelId(labelId[i]);
            int dtClassifyRes=dtClassifyMapper.insert(dtClassify);
            if(dtClassifyRes<0){
                return 4004;
            }
        }

        return dtid;

    }


//    public int updateStatus(int userId,int docId,int taskId){
//        DTask dTask=dTaskMapper.selectByTaskIdAndUserId(taskId,userId);
//        if(dTask==null){
//            return 4010;
//        }else{
//            int dTaskId=dTask.getTkid();
//
//            int[] pids=paragraphMapper.selectParaByDocId(docId);
//            List<DParagraph> dParagraphList=dParagraphMapper.selectByDtaskIdAndDocId(dTaskId,docId);
//            if(pids.length!=dParagraphList.size()){
//                return 4011;
//            }else {
//
//                for(DParagraph dParagraph:dParagraphList){
//                    dParagraph.setDtstatus("已完成");
//                    int dRes=dParagraphMapper.updateStatusByPk(dParagraph);
//                    if(dRes<0){
//                        return 4012;
//                    }
//                }
//            }
//        }
//        return 0;
//    }
}
