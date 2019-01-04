package com.annotation.dao;

import com.annotation.model.DtdItemLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DtdItemLabelMapper {

    int insert(DtdItemLabel record);

    List<DtdItemLabel> selectAll();

    /**
     * 批量插入labelid
     * 做任务表
     * @param dtInstId
     * @param labeltype
     * @param itemLabels
     * @return
     */
    int insertLabelList(@Param("dtInstId")Integer dtInstId, @Param("labeltype")String labeltype, @Param("itemLabels")int[] itemLabels);


}