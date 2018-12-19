package com.annotation.service;

import com.annotation.model.Content;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/16.
 */
public interface IContentService {

    public List<Content> selectContentByDocId(int docId);
}
