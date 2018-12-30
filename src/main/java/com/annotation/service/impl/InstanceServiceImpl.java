package com.annotation.service.impl;

import com.annotation.dao.InstanceMapper;
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



    public List<InstanceItemEntity> queryInstanceItem(int docId){
        List<InstanceItemEntity> instanceItemEntityList=instanceMapper.selectInstanceItem(docId);
        return instanceItemEntityList;
    }

    public List<InstanceListitemEntity> queryInstanceListitem(int docId){
        List<InstanceListitemEntity> instanceListitemEntityList=instanceMapper.selectInstanceListitem(docId);
        return instanceListitemEntityList;
    }
}
