package com.annotation.dao;

import com.annotation.model.Content;
import com.annotation.model.entity.ContentLabelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据contentID查找documentid
     * @param contentid
     * @return
     */
    int selectContentDocumentId(Integer contentid);

    /**
     * 根据文件id获取所有的instance和item
     * 包括已经做好的
     * @param docId
     * @param userId
     * @return
     */
    List<ContentLabelEntity> selectContentLabel(@Param("docId")Integer docId, @Param("userId")Integer userId);


}
