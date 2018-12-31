package com.annotation.dao;

import com.annotation.model.DtdItemLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface DtdItemLabelMapper {
    int deleteByPrimaryKey(Integer dtdItlid);

    int insert(DtdItemLabel record);

    DtdItemLabel selectByPrimaryKey(Integer dtdItlid);

    List<DtdItemLabel> selectAll();

    int updateByPrimaryKey(DtdItemLabel record);

    DtdItemLabel selectByDtInstIdAndItemId(@Param("dtInstId")Integer dtInstId, @Param("itemId")Integer itemId);

    int updateItemLabelByPrimaryKey(@Param("dtdItlid")Integer dtdItlid, @Param("itemLabel")String itemLabel);
}