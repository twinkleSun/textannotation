package com.annotation.service.impl;

import com.annotation.dao.LabelMapper;
import com.annotation.model.Label;
import com.annotation.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/19.
 */
@Repository
public class LabelServiceImpl implements ILabelService {

    @Autowired
    LabelMapper labelMapper;

    /**
     * 根据任务ID查询标签
     * @param taskid
     * @return
     */
    public List<Label> queryLabelByTaskId(int taskid){
        List<Label> labelList=labelMapper.selectLabelByTaskid(taskid);
        return labelList;
    }
}
