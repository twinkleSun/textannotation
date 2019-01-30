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

    /**
     * 查询这个listitem关系是否已经存在
     * @param dtInstId
     * @param aListItemId
     * @param bListItemId
     * @return
     */
    DtdItemRelation selectDtItemRelation(@Param("dtInstId")int dtInstId, @Param("aListItemId")int aListItemId, @Param("bListItemId")int bListItemId);


    /**
     * 单个记录判定重复后再插入
     * 一对一
     * @param dtInstId
     * @param aListItemId
     * @param bListItemId
     * @return
     */
    int insertRelationListByOneToOne(@Param("dtInstId")int dtInstId, @Param("aListItemId")int aListItemId, @Param("bListItemId")int bListItemId);

    /**
     * 单个记录判定重复后再插入
     * 一对多
     * @param dtInstId
     * @param aListItemId
     * @param bListItemId
     * @return
     */
    int insertRelationListByOneToMany(@Param("dtInstId")int dtInstId, @Param("aListItemId")int aListItemId, @Param("bListItemId")int bListItemId);

    /**
     * 单个记录判定重复后再插入
     * 多对多
     * @param dtInstId
     * @param aListItemId
     * @param bListItemId
     * @return
     */
    int insertRelationListByManyToMany(@Param("dtInstId")int dtInstId, @Param("aListItemId")int aListItemId, @Param("bListItemId")int bListItemId);


    int alterDtdItemRelationTable();
}