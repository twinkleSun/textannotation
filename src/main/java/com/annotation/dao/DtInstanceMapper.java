package com.annotation.dao;

import com.annotation.model.DtInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DtInstanceMapper {
    int deleteByPrimaryKey(Integer dtInstid);

    int insert(DtInstance record);

    DtInstance selectByPrimaryKey(Integer dtInstid);

    List<DtInstance> selectAll();

    int updateByPrimaryKey(DtInstance record);

    /**
     * 查询做任务时，这个表是否已经插入
     * @param userId
     * @param taskId
     * @param instanceId
     * @return
     */
    DtInstance selectDtInstance(@Param("userId")int userId, @Param("taskId")int taskId, @Param("instanceId")int instanceId);

    int alterDtInstanceTable();
}