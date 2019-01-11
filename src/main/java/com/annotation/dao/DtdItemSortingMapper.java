package com.annotation.dao;

import com.annotation.model.DtdItemSorting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DtdItemSortingMapper {
    int insert(DtdItemSorting record);

    int insertSelective(DtdItemSorting record);
}