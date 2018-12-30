package com.annotation.dao;

import com.annotation.model.TaskLabel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/12.
 */

@Mapper
@Repository
public interface TaskLabelMapper {

    /**
     * 插入任务-标签关系表
     * @param taskLabel
     * @return
     */
    int insertTaskLabel(TaskLabel taskLabel);

    List<TaskLabel> selectAll();
}
