package com.annotation.dao;

import com.annotation.model.DtdItemSorting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DtdItemSortingMapper {
    int insert(DtdItemSorting record);

    int insertSelective(DtdItemSorting record);

    int insertSortingItem(@Param("dtInstId")int dtInstId,
                          @Param("itemId")int itemId,
                          @Param("newIndex")int newIndex);

    int alterDtdItemSortingTable();

}