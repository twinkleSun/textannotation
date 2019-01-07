package com.annotation.service.impl;

import com.annotation.dao.InstanceMapper;
import com.annotation.model.Instance;
import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.InstanceListitemEntity;
import com.annotation.service.IInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/29.
 */


@Repository
public class InstanceServiceImpl implements IInstanceService {

    @Autowired
    InstanceMapper instanceMapper;

    /**
     * 根据文件的ID查询instance+Item
     * 文本关系类别
     * @param docId
     * @return
     */
    public List<InstanceItemEntity> queryInstanceItem(int docId ,int userid){
        List<InstanceItemEntity> instanceItemEntityList=instanceMapper.selectInstanceItem(docId,userid);
        return instanceItemEntityList;
    }

    /**
     * 根据文件ID查询instance+listitem
     * 文本配对关系
     * @param docId
     * @return
     */
    public List<InstanceListitemEntity> queryInstanceListitem(int docId,int userid){
        List<InstanceListitemEntity> instanceListitemEntityList=instanceMapper.selectInstanceListitem(docId,userid);
        return instanceListitemEntityList;
    }
}
