package com.annotation.dao;

import com.annotation.model.Instance;
import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.InstanceListitemEntity;
import org.apache.ibatis.annotations.Mapper;
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
     * 文本关系标注
     * @param docId
     * @return
     */
    List<InstanceItemEntity> selectInstanceItem(Integer docId);

    List<InstanceListitemEntity> selectInstanceListitem(Integer docId);
}