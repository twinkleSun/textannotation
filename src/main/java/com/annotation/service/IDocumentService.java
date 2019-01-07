package com.annotation.service;

import com.annotation.model.Document;
import com.annotation.model.User;
import com.annotation.model.entity.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 设置数据库自增长为1
     * @return
     */
    public int alterDocumentTable();

    /**
     * 信息抽取和分类多文件上传
     * @param files
     * @return
     */
    public ResponseEntity addMultiFile(MultipartFile[] files,User user);

    /**
     * 两个item多文件上传
     * @param files
     * @return
     */
    public ResponseEntity addMultiFileTwoItems(MultipartFile[] files,User user,int labelnum,int labelitem1,int labelitem2);

    /**
     * 两个item插入文件
     * @param document 文件相关信息
     * @param user 用户相关信息
     * @param docContent 文件内容
     * @return
     */
    public int addDocumentTwoitems(Document document,User user,String docContent,int labelnum,int labelitem1,int labelitem2);

    /**
     * 信息抽取和分类插入文件内容
     * @param docId
     * @param instanceArr
     * @param labelnum
     * @return
     */
    public int addInstanceTwoitems(int docId,String[] instanceArr,int labelnum,int labelitem1,int labelitem2);

    /**
     * 两个文本插入item内容
     * @param instId
     * @param itemArr
     * @param labelitem1
     * @param labelitem2
     * @return
     */
    public int addItem(int instId,String[] itemArr,int labelitem1,int labelitem2);

    /*
     * 文本配对的多文件上传
     * @param files
     * @param user
     */
    public ResponseEntity addMultiFileMatchCategory(MultipartFile[] files,User user);

    /**
     * 文本匹配插入文件
     * @param document 文件相关信息
     * @param user 用户相关信息
     * @param docContent 文件内容
     * @return
     */
    public int addDocumentMatchCategory(Document document,User user,String docContent);

    /**
     * 文本匹配插入instance内容
     * @param docId
     * @param instanceArr
     * @return
     */
    public int addInstanceMatchCategory(int docId,String[] instanceArr);

    /**
     * 文本匹配插入listitem内容
     * @param instId
     * @param itemArr
     * @return
     */
    public int addListItem(int instId,String[] itemArr);

}
