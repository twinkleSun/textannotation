package com.annotation.service;

import com.annotation.model.Content;
import com.annotation.model.entity.ContentLabelEntity;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/16.
 */
public interface IContentService {

    /**
     * 根据文件ID查询content内容
     * @param docId
     * @return
     */
    public List<Content> selectContentByDocId(int docId);


    /**
     * 查询content+label
     * @param docId
     * @return
     */
    List<ContentLabelEntity> queryContentLabel(int docId, int userId);
}
