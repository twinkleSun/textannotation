package com.annotation.service;

import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.InstanceListitemEntity;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/29.
 */
public interface IInstanceService {

    List<InstanceItemEntity> queryInstanceItem(int docId);

    List<InstanceListitemEntity> queryInstanceListitem(int docId);
}