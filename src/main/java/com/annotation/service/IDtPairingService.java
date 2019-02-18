package com.annotation.service;

import com.annotation.model.entity.InstanceListitemEntity;

import java.util.List;

/**
 * Created by twinkleStar on 2019/2/2.
 */
public interface IDtPairingService {

    /**
     * 查询instance+listitem
     * @param docId
     * @return
     */
    List<InstanceListitemEntity> queryInstanceListitem(int docId, int userId,String status,int taskId);

    /**
     * 做任务---文本配对关系
     * @param dtInstance
     * @param userId
     * @param aListItemId
     * @param bListItemId
     * @return
     */
    String addPairing(int taskId,int docId,int instanceId,int userId,int[] aListItemId,int[] bListItemId,String taskType);




    /**
     * 文本配对--一对一
     * @param dtInstId
     * @param aListitemId
     * @param bListitemId
     * @return
     */
    String insertOneToOneRelations(int dtInstId,int[] aListitemId,int[] bListitemId);

    /**
     * 文本配对--一对多
     * @param dtInstId
     * @param aListitemId
     * @param bListitemId
     * @return
     */
    String insertOneToManyRelations(int dtInstId,int[] aListitemId,int[] bListitemId);


    /**
     * 文本配对--多对多
     * @param dtInstId
     * @param aListitemId
     * @param bListitemId
     * @return
     */
    String insertManyToManyRelations(int dtInstId,int[] aListitemId,int[] bListitemId);




}
