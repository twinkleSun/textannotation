package com.annotation.service.impl;

import com.annotation.dao.DtdItemLabelMapper;
import com.annotation.dao.LabelMapper;
import com.annotation.model.DtdItemLabel;
import com.annotation.model.Label;
import com.annotation.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/19.
 */
@Repository
public class LabelServiceImpl implements ILabelService {

    @Autowired
    LabelMapper labelMapper;
    @Autowired
    DtdItemLabelMapper dtdItemLabelMapper;

    /**
     * 根据任务ID查询标签
     * @param taskid
     * @return
     */
    public List<Label> queryLabelByTaskId(int taskid){
        List<Label> labelList=labelMapper.selectLabelByTaskid(taskid);
        return labelList;
    }


    /**
     * 根据文件ID查询instance对应的label
     * @param docId
     * @return
     */
    public List<Label> queryInstanceLabelByDocId(int docId){
        List<Label> labelList =labelMapper.selectInstanceLabelByDocId(docId);
        return labelList;
    }

    /**
     * 根据文件ID查询item1对应的label
     * @param docId
     * @return
     */
    public List<Label> queryItem1LabelByDocId(int docId){
        List<Label> labelList =labelMapper.selectItem1LabelByDocId(docId);
        return labelList;
    }

    /**
     * 根据文件ID查询item2对应的label
     * @param docId
     * @return
     */
    public List<Label> queryItem2LabelByDocId(int docId){
        List<Label> labelList =labelMapper.selectItem2LabelByDocId(docId);
        return labelList;
    }



}
