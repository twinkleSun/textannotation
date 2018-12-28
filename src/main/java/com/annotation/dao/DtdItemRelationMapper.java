package com.annotation.dao;

import com.annotation.model.DtdItemRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DtdItemRelationMapper {
    int deleteByPrimaryKey(Integer dtdItrid);

    int insert(DtdItemRelation record);

    DtdItemRelation selectByPrimaryKey(Integer dtdItrid);

    List<DtdItemRelation> selectAll();

    int updateByPrimaryKey(DtdItemRelation record);

    DtdItemRelation selectDtItemRelation(@Param("dtInstId")int dtInstId, @Param("aListItemId")int aListItemId, @Param("bListItemId")int bListItemId);
}