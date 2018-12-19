package com.annotation.service.impl;

import com.annotation.dao.ContentMapper;
import com.annotation.model.Content;
import com.annotation.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/16.
 */

@Repository
public class ContentServiceImpl implements IContentService {

    @Autowired
    ContentMapper contentMapper;

    public List<Content> selectContentByDocId(int docId){
        List<Content> contentList =contentMapper.selectContentByDocumentId(docId);
        return contentList;
    }
}
