package com.annotation.dao;

import com.annotation.model.Instance;
import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.InstanceListitemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstanceMapper {

    int deleteByPrimaryKey(Integer insid);

    int insert(Instance record);

    Instance selectByPrimaryKey(Integer insid);

    List<Instance> selectAll();

    int updateByPrimaryKey(Instance record);

    /**
     * 根据文件id获取所有的instance和item
     * 包括已经做好的
     * @param docId
     * @param userId
     * @return
     */
    List<InstanceItemEntity> selectInstanceItem(@Param("docId")Integer docId, @Param("userId")Integer userId);

    /**
     * 根据文件id获取所有的instance和item
     * 文本关系标注
     * @param docId
     * @return
     */
    List<InstanceListitemEntity> selectInstanceListitem(@Param("docId")Integer docId, @Param("userId")Integer userId);

    /**
     * 设置数据库自增长为1
     * @return
     */
    int alterInstanceTable();


    /**
     * 根据文件id获取所有的instance和item
     * 包括已经做好的
     * @param docId
     * @param userId
     * @return
     */
    List<InstanceItemEntity> selectSortingInstanceItem(@Param("docId")Integer docId, @Param("userId")Integer userId);

}