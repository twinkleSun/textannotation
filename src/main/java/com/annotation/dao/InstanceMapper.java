package com.annotation.dao;

import com.annotation.model.Instance;
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


}