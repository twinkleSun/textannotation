package com.annotation.service;

import com.annotation.model.Document;
import com.annotation.model.User;

import java.io.IOException;
import java.util.List;

/**
 * Created by twinkleStar on 2018/12/8.
 */
public interface IDocumentService {

    /**
     * 插入文件
     * @param document 文件相关信息
     * @param user 用户相关信息
     * @param docContent 文件内容
     * @return
     */
    public int addDocument(Document document,User user,String docContent);

    /**
     * 文件分页查询
     * @param userId
     * @param page 页数
     * @param limit 每页数量
     * @return
     */
    public List<Document> queryDocByRelatedInfo(int userId, int page, int limit);

    /**
     * 根据用户ID查询文件总数
     * @param userId
     * @return
     */
    public int countNumByUserId(int userId);

    /**
     * 插入文件内容
     * @param docId
     * @param contentArr
     * @return
     */
    public int addContent(int docId,String[] contentArr);

}
