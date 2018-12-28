package com.annotation.dao;

import com.annotation.model.DoTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/12.
 */

@Mapper
@Repository
public interface DoTaskMapper {

    /**
     * 插入做任务表
     * @param dotask
     * @return
     */
    int insertDoTask(DoTask dotask);

    /**
     * 根据userid,taskid,contentid查询dotask
     * @param userid
     * @param taskid
     * @param contentid
     * @return
     */
    DoTask selectTask(@Param("userid") int userid,@Param("taskid") int taskid,@Param("contentid") int contentid);



}