package com.annotation.dao;

import com.annotation.model.DoTaskDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/12.
 */

@Mapper
@Repository
public interface DoTaskDetailMapper {

    /**
     * 插入做任务表
     * @param dotaskdetail
     * @return
     */
    int insertDoTaskDetail(DoTaskDetail dotaskdetail);
}