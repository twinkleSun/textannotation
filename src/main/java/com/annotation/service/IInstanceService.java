package com.annotation.service;

import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.InstanceListitemEntity;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/29.
 */
public interface IInstanceService {

    /**
     * 查询instance+item
     * @param docId
     * @return
     */
    List<InstanceItemEntity> queryInstanceItem(int docId);

    /**
     * 查询instance+listitem
     * @param docId
     * @return
     */
    List<InstanceListitemEntity> queryInstanceListitem(int docId);
}
