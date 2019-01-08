package com.annotation.dao;

import com.annotation.model.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DocumentMapper {

       /**
        * 插入文件
        * @param document
        * @return
        */
       int insertDocument(Document document);//将文件存进数据库

       /**
        * 分页查询
        * 参数：用户ID,页数，每页数量
        * @param data
        * @return
        */
       List<Document> selectDocumentByRelatedInfo(Map<String,Object> data);

       /**
        * 根据文档ID查询
        * 参数：文档ID
        * @param documentid
        * @return
        */
       Document  selectDocumentById(int documentid);

       /**
        * 根据用户ID获取记录总数
        * @param userid
        * @return
        */
       Integer countDocNumByUserId(int userid);

       /**
        * 根据contentid更新content的完成状态
        * @param document
        * @return
        */
       int updateDocumentById(Document document);

       /**
        * 根据instID更新文档状态
        * @param instId
        * @param docStatus
        * @return
        */
       int updateDocStatusByInstanceId(@Param("instId")int instId, @Param("docStatus")String docStatus);


       /**
        * 设置数据库自增长为1
        * @return
        */
        int alterDocumentTable();



}