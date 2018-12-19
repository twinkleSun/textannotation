package com.annotation.dao;

import com.annotation.model.Content;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/12.
 */

@Mapper
@Repository
public interface ContentMapper {

    /**
     * 将文件内容存进数据库
     * @param content
     * @return
     */
    int insertContent(Content content);

    /**
     * 根据文件ID查找文件内容
     * @param documentid
     * @return
     */
    List<Content> selectContentByDocumentId(Integer documentid);
}
