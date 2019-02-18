package com.annotation.dao;

import com.annotation.model.DtSorting;
import com.annotation.model.entity.InstanceItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DtSortingMapper {


    /**
     * 根据文件id获取所有的instance和item
     * 包括已经做好的
     * @param docId
     * @param userId
     * @return
     */
    List<InstanceItemEntity> selectSorting(@Param("docId")Integer docId);

    List<InstanceItemEntity> selectSortingInstanceItem(@Param("docId")Integer docId,
                                                       @Param("userId")Integer userId,
                                                       @Param("dTaskId")Integer dTaskId);


    List<InstanceItemEntity> selectSortingWithStatus(Map<String,Object> data);


    int deleteByDtId(Integer dtId);



    DtSorting selectByDtIdAndItemId(@Param("dtId")Integer dtid,
                                    @Param("itemId")Integer itemId);




    int alterDtSortingTable();

    int insert(DtSorting record);

    int updateNewIndex(DtSorting record);



    int deleteByPrimaryKey(Integer dtdId);



    DtSorting selectByPrimaryKey(Integer dtdId);

    List<DtSorting> selectAll();

    int updateByPrimaryKey(DtSorting record);
}