package com.annotation.dao;

import com.annotation.model.TaskDocument;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by twinkleStar on 2018/12/10.
 */
@Mapper
@Repository
public interface TaskDocumentMapper {

    /**
     * 插入任务-文件关系表
     * @param taskDocument
     * @return
     */
    int insertTaskDocument(TaskDocument taskDocument);
}
