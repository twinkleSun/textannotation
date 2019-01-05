package com.annotation.service.impl;

import com.annotation.dao.ContentMapper;
import com.annotation.dao.DocumentMapper;
import com.annotation.model.Content;
import com.annotation.model.Document;
import com.annotation.model.User;
import com.annotation.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by twinkleStar on 2018/12/8.
 */

@Repository
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    DocumentMapper documentMapper;
    @Autowired
    ContentMapper contentMapper;

    /**
     * 插入文件
     * @param document 文件相关信息
     * @param user 用户相关信息
     * @param docContent 文件内容
     * @return
     */
    public int addDocument(Document document,User user,String docContent){

        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] contentArr = docContent.split("#");

        //先检查有没有内容长度太长的，有的话直接返回不插入
        /*for(int i=0;i<contentArr.length;i++){
            if(contentArr[i].length()>20000){
                return -2;
            }
        }*/

        //开始插入文件相关信息
        document.setUserid(user.getId());
        int docInsertRes = documentMapper.insertDocument(document);//插入结果
        //插入文件失败
        if(docInsertRes == -1){
            return docInsertRes;
        }else{
            //插入文件成功
            int docId=document.getDid();//插入成功的文件ID
            //文件内容，用#分隔了
            int addContentRes =addContent(docId,contentArr);
            //文件内容插入失败
            if(addContentRes == -3){
                //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                return -3;
            }else{
                return docId;
            }
        }

    }

    /**
     * 插入文件
     * @param document 文件相关信息
     * @param user 用户相关信息
     * @param docContent 文件内容
     * @return
     */
    public int addDocuments(Document document,User user,String docContent){

        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] contentArr = docContent.split("#");

        //先检查有没有内容长度太长的，有的话直接返回不插入
        for(int i=0;i<contentArr.length;i++){
            if(contentArr[i].length()>20000){
                return -2;
            }
        }

        //开始插入文件相关信息
        document.setUserid(user.getId());
        int docInsertRes = documentMapper.insertDocument(document);//插入结果
        //插入文件失败
        if(docInsertRes == -1){
            return docInsertRes;
        }else{
            //插入文件成功
            int docId=document.getDid();//插入成功的文件ID
            //文件内容，用#分隔了
            int addContentRes =addContent(docId,contentArr);
            //文件内容插入失败
            if(addContentRes == -3){
                //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                return -3;
            }else{
                return docId;
            }
        }

    }

    /**
     * 插入文件内容
     * @param docId
     * @param contentArr
     * @return
     */
    public int addContent(int docId,String[] contentArr){
        for(int i=0;i<contentArr.length;i++){
            Content content =new Content();
            content.setParacomptime("");
            content.setParacontent(contentArr[i]);
            content.setParaindex(i+1);
            content.setParastatus("未完成");
            content.setDocumentid(docId);
            int contentRes =contentMapper.insertContent(content);
            //文件内容插入失败则返回3
            if(contentRes == -1){
                return -3;
            }
        }
        return 0;

    }

    /**
     * 分页查询
     * @param userid
     * @param page 页数
     * @param limit 每页数量
     * @return
     */
    public List<Document> queryDocByRelatedInfo(int userid, int page, int limit){
        int startNum =(page-1)*limit;
        Map<String,Object> data =new HashMap();
        data.put("currIndex",startNum);
        data.put("pageSize",limit);
        data.put("userid",userid);
        List<Document> task =documentMapper.selectDocumentByRelatedInfo(data);
        return task;
    }

    /**
     * 计算用户文件总数
     * @param userId
     * @return
     */
    public int countNumByUserId(int userId){
        Integer numInt = documentMapper.countDocNumByUserId(userId);
        if(numInt == null){
            return 0;
        } else{
            return numInt.intValue();
        }
    }

    /**
     * 设置数据库自增长为1
     * @return
     */
    public int alterDocumentTable(){
        int num = documentMapper.alterDocumentTable();
        return num;
    }


//    public  String readContent(String path) throws IOException {
//
//        InputStream is = new FileInputStream(path);
//        InputStreamReader inputFileReader =new InputStreamReader(is,"Unicode");
//        BufferedReader in = new BufferedReader(inputFileReader);
//        StringBuffer buffer = new StringBuffer();
//        String line = "";
//        while ((line = in.readLine()) != null){
//            buffer.append(line);
//        }
//        return buffer.toString();
//
//    }

//    public String getDocumentContent(String filename) throws IOException{
//        String docContent = "";
//        try {
//            if (filename.endsWith(".doc")) {
//
//                InputStream is = new FileInputStream(new java.io.File(filename));
//                WordExtractor ex = new WordExtractor(is);
//                docContent = ex.getText();
//            } else if (filename.endsWith("docx")) {
//                FileInputStream fis = new FileInputStream(filename);
//                XWPFDocument xdoc = new XWPFDocument(fis);
//                XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
//                docContent= extractor.getText();
//                fis.close();
//            } else {
//                docContent="此文件不是word文件！";
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return docContent;
//
//    }
}
