package com.annotation.dao;

import com.annotation.model.Listitem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ListitemMapper {

    int deleteByPrimaryKey(Integer liid);

    int insert(Listitem record);

    Listitem selectByPrimaryKey(Integer liid);

    List<Listitem> selectAll();

    int updateByPrimaryKey(Listitem record);

    int alterListitemTable();
}