package com.annotation.dao;

import com.annotation.model.Label;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/12.
 */

@Mapper
@Repository
public interface LabelMapper {

    /**
     * 插入标签
     * @param label
     * @return
     */
    int insertLabel(Label label);

    /**
     * 查找根据标签名查找标签
     * @param labelname
     * @return
     */
    Label selectLabel(String labelname);

    /**
     * 根据任务ID获取标签
     * @param id
     * @return
     */
    List<Label> selectLabelByTaskid(Integer id);
}
