package com.annotation.service;

import com.annotation.model.DtClassify;
import com.annotation.model.entity.LabelCountEntity;
import com.annotation.model.entity.ParagraphLabelEntity;
import com.annotation.model.entity.ResponseEntity;

import java.util.List;

/**
 * Created by twinkleStar on 2019/2/2.
 */
public interface IDtClassifyService {

    /**
     * 查询content+label
     * @param docId
     * @return
     */
    List<ParagraphLabelEntity> queryClassifyParaLabel(int docId, int userId,String status,int taskId);


    /**
     * 分类任务
     * @param userId
     * @param taskId
     * @param docId
     * @param paraId
     * @param labelId
     * @return
     */
    int addClassify(int userId,int taskId,int docId,int paraId,int[] labelId);


    DtClassify addComment(int dtdId, int cNum, int flag, int uId);

    List<LabelCountEntity> queryAlreadyLabel(int tid);

    //int updateStatus(int userId,int docId,int taskId);
}
