package com.annotation.service.impl;

import com.annotation.dao.ContentMapper;
import com.annotation.dao.DocumentMapper;
import com.annotation.dao.InstanceMapper;
import com.annotation.dao.ItemMapper;
import com.annotation.dao.ListitemMapper;
import com.annotation.model.Content;
import com.annotation.model.Document;
import com.annotation.model.Instance;
import com.annotation.model.User;
import com.annotation.model.Item;
import com.annotation.model.Listitem;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.service.IDocumentService;
import com.annotation.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by twinkleStar on 2018/12/8.
 */

@Repository
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    DocumentMapper documentMapper;
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    InstanceMapper instanceMapper;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ListitemMapper listitemMapper;


    /**
     * 信息抽取和分类插入文件
     * @param document 文件相关信息
     * @param user 用户相关信息
     * @param docContent 文件内容
     * @return
     */
    public int addDocument(Document document,User user,String docContent){

        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] contentArr = docContent.split("#");

        //开始插入文件相关信息
        document.setUserid(user.getId());
        documentMapper.alterDocumentTable();
        int docInsertRes = documentMapper.insertDocument(document);//插入结果
        //插入文件失败
        if(docInsertRes == -1){
            return docInsertRes;
        }else{
            //插入文件成功
            int docId=document.getDid();//插入成功的文件ID
            //文件内容，用#分隔了
            contentMapper.alterContentTable();
            int addContentRes =addContent(docId,contentArr);
            //文件内容插入失败
            if(addContentRes == -2){
                //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                return -2;
            }else{
                return docId;
            }
        }

    }

    /**
     * 信息抽取和分类插入文件内容
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
                return -2;
            }
        }
        return 0;
    }

    /**
     * 两个item插入文件
     * @param document 文件相关信息
     * @param user 用户相关信息
     * @param docContent 文件内容
     * @param labelnum instance的标签个数
     * @param labelitem1 item1的标签个数
     * @param labelitem2 item2的标签个数
     * @return
     */
    public int addDocumentTwoitems(Document document,User user,String docContent,int labelnum,int labelitem1,int labelitem2){

        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] instanceArr = docContent.split("#");

        //开始插入文件相关信息
        document.setUserid(user.getId());
        documentMapper.alterDocumentTable();
        int docInsertRes = documentMapper.insertDocument(document);//插入结果

        //插入文件失败
        if(docInsertRes == -1){
            return docInsertRes;
        }else{
            //插入文件成功
            int docId=document.getDid();//插入成功的文件ID
            //防止自增的ID不连续
            instanceMapper.alterInstanceTable();
            //文件内容，用#分隔了
            int addContentRes =addInstanceTwoitems(docId,instanceArr,labelnum,labelitem1,labelitem2);
            //instance插入失败
            if(addContentRes == -2){
                //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                return -2;
            }else{
                return docId;
            }
        }
    }

    /**
     * 信息抽取和分类插入文件内容
     * @param docId
     * @param instanceArr
     * @param labelnum
     * @return
     */
    public int addInstanceTwoitems(int docId,String[] instanceArr,int labelnum,int labelitem1,int labelitem2){

        for(int i=0;i<instanceArr.length;i++){

            String[] itemArr = instanceArr[i].split("-------");
            Instance instance =new Instance();
            instance.setInsindex(String.valueOf(i+1));
            instance.setInsstatus("未完成");
            instance.setDocumentid(docId);
            instance.setLabelnum(labelnum);
            int instanceRes =instanceMapper.insert(instance);
            //Instance插入失败则返回3
            if(instanceRes == -1){
                return -2;
            }else{
                //Instance插入成功
                int instId=instance.getInsid();//插入成功的文件ID
                //文件内容，用#分隔了
                itemMapper.alterItemTable();
                int addItemRes =addItem(instId,itemArr,labelitem1,labelitem2);
                //文件内容插入失败
                if(addItemRes == -3){
                    //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                    return -3;
                }
            }
        }
        return 0;
    }

    /**
     * 两个文本插入item内容
     * @param instId
     * @param itemArr
     * @param labelitem1
     * @param labelitem2
     * @return
     */
    public int addItem(int instId,String[] itemArr,int labelitem1,int labelitem2){

        for(int i=0;i<itemArr.length;i++){
            Item item = new Item();
            item.setItemcontent(itemArr[i]);
            item.setItemindex(String.valueOf(i+1));
            item.setInstanceid(instId);
            if(i==0) {
                item.setLabelnum(labelitem1);
            }else {
                item.setLabelnum(labelitem2);
            }
            int itemRes = itemMapper.insert(item);
            //文件内容插入失败则返回3
            if(itemRes == -1){
                return -3;
            }
        }
        return 0;
    }


    /**
     * 文本匹配插入文件
     * @param document 文件相关信息
     * @param user 用户相关信息
     * @param docContent 文件内容
     * @return
     */
    public int addDocumentMatchCategory(Document document,User user,String docContent){

        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] instanceArr = docContent.split("#");

        //开始插入文件相关信息
        document.setUserid(user.getId());
        documentMapper.alterDocumentTable();
        int docInsertRes = documentMapper.insertDocument(document);//插入结果

        //插入文件失败
        if(docInsertRes == -1){
            return docInsertRes;
        }else{
            //插入文件成功
            int docId=document.getDid();//插入成功的文件ID
            //防止自增的ID不连续
            instanceMapper.alterInstanceTable();
            //文件内容，用#分隔了
            int addContentRes =addInstanceMatchCategory(docId,instanceArr);
            //instance插入失败
            if(addContentRes == -2){
                //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                return -2;
            }else{
                return docId;
            }
        }
    }

    /**
     * 文本匹配插入instance内容
     * @param docId
     * @param instanceArr
     * @return
     */
    public int addInstanceMatchCategory(int docId,String[] instanceArr){

        for(int i=0;i<instanceArr.length;i++){

            String[] itemArr = instanceArr[i].split("-------");
            Instance instance =new Instance();
            instance.setInsindex(String.valueOf(i+1));
            instance.setInsstatus("未完成");
            instance.setDocumentid(docId);
            instanceMapper.alterInstanceTable();
            int instanceRes =instanceMapper.insert(instance);
            //Instance插入失败则返回3
            if(instanceRes == -1){
                return -2;
            }else{
                //Instance插入成功
                int instId=instance.getInsid();//插入成功的文件ID
                //文件内容，用#分隔了

                listitemMapper.alterListitemTable();
                int addItemRes =addListItem(instId,itemArr);
                //文件内容插入失败
                if(addItemRes == -3){
                    //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                    return -3;
                }
            }
        }
        return 0;
    }

    /**
     * 文本匹配插入listitem内容
     * @param instId
     * @param itemArr
     * @return
     */
    public int addListItem(int instId,String[] itemArr){

        for(int i=0;i<itemArr.length;i++){

            String[] listitemArr = itemArr[i].split("&&&&&&&");
            for(int j=0;j<listitemArr.length;j++){
                Listitem listitem = new Listitem();
                listitem.setListindex(String.valueOf(i+1));
                listitem.setLitemindex(String.valueOf(j+1));
                listitem.setLitemcontent(listitemArr[j]);
                listitem.setLitemstatus("未完成");
                listitem.setInstanceid(instId);
                int listitemRes = listitemMapper.insert(listitem);
                //文件内容插入失败则返回3
                if(listitemRes == -1){
                    return -3;
                }
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



    /*
     * 信息抽取和分类的多文件上传
     */
    @Transactional
    public ResponseEntity addMultiFile(MultipartFile[] files,User user) throws IllegalStateException{

        boolean filetype = true;
        ResponseEntity responseEntity = new ResponseEntity();
        //遍历处理文件
        responseEntity = FileUtil.checkfile(files);
        if(responseEntity.getStatus()<0){
            return responseEntity;
        }

        //然后检查文件内容是否符合要求
        for (MultipartFile file : files) {
            try {
                String filename = file.getOriginalFilename();//文件名称
                int content = FileUtil.checkfilecontent(file);
                if(content==-2){
                    responseEntity.setStatus(-3);
                    responseEntity.setMsg(filename +"单个文件大小超过限制");
                    return responseEntity;
                }else if(content==-3){
                    responseEntity.setStatus(-4);
                    responseEntity.setMsg(filename +"文件中有的item为空");
                    return responseEntity;
                }else if(content==-4){
                    responseEntity.setStatus(-5);
                    responseEntity.setMsg(filename+"文件中有的item超过字数限制,文件分段内容长度太长，请重新用#分段");
                    return responseEntity;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //最后插入document和content
            List<Integer> docids = new ArrayList<Integer>();
            for (MultipartFile file : files) {
                try {
                    String filename =file.getOriginalFilename();//文件名称
                    String docContent=FileUtil.parsefilecontent(file);
                    String docType="";//文件类型

                    if (filename.endsWith(".doc")) {
                        docType=".doc";
                    } else if (filename.endsWith("docx")) {
                        docType=".docx";
                    } else if(filename.endsWith(".txt")){
                        docType=".txt";
                    }

                    Document document = new Document();
                    document.setFilename(filename);
                    document.setFiletype(docType);
                    document.setFilesize((int)file.getSize());

                    //todo:建文件服务器后设置路径
                    document.setAbsolutepath("");
                    document.setRelativepath("");

                    //设置时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    document.setDocuploadtime(df.format(new Date()));
                    document.setDoccomptime("");
                    document.setDocstatus("未完成");

                    //添加文件
                    //防止自增的ID不连续
                    alterDocumentTable();
                    int docRes = addDocument(document,user,docContent);

                    switch (docRes){
                        case -1:
                            responseEntity.setStatus(-6);
                            responseEntity.setMsg(filename+"添加文件失败，请检查");
                            //插入数据库有错误时整体回滚
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            break;
                        case -2:
                            responseEntity.setStatus(-7);
                            responseEntity.setMsg(filename+"文件内容插入失败");
                            //插入数据库有错误时整体回滚
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            break;
                        default:
                            responseEntity.setStatus(0);
                            responseEntity.setMsg("文件上传成功");
                            Map<String, Object> data = new HashMap<>();
                            docids.add(docRes);
                            data.put("docIds", docids);//返回文件id，方便后续添加任务
                            responseEntity.setData(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return responseEntity;
    }

    /*
     * 两个item的多文件上传
     */
    @Transactional
    public ResponseEntity addMultiFileTwoItems(MultipartFile[] files,User user,int labelnum,int labelitem1,int labelitem2) throws IllegalStateException{

        boolean filetype = true;
        ResponseEntity responseEntity = new ResponseEntity();
        //遍历处理文件
        responseEntity = FileUtil.checkfile(files);
        if(responseEntity.getStatus()<0){
            return responseEntity;
        }

        //然后检查文件内容是否符合要求
        for (MultipartFile file : files) {
            try {
                String filename = file.getOriginalFilename();//文件名称
                int content = FileUtil.checktwoitem(file);
                if(content==-2){
                    responseEntity.setStatus(-3);
                    responseEntity.setMsg(filename +"单个文件大小超过限制");
                    return responseEntity;
                }else if(content==-3){
                    responseEntity.setStatus(-4);
                    responseEntity.setMsg(filename +"每个instance中的item的个数不正确");
                    return responseEntity;
                }else if(content==-4){
                    responseEntity.setStatus(-5);
                    responseEntity.setMsg(filename+"文件中有的item为空");
                    return responseEntity;
                } else if(content==-5){
                    responseEntity.setStatus(-5);
                    responseEntity.setMsg(filename+"文件中有的item超过字数限制,文件分段内容长度太长，请重新用#分段");
                    return responseEntity;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //最后插入document和content
        List<Integer> docids = new ArrayList<Integer>();
        for (MultipartFile file : files) {
            try {
                String filename =file.getOriginalFilename();//文件名称
                String docContent=FileUtil.parsefilecontent(file);
                String docType="";//文件类型

                if (filename.endsWith(".doc")) {
                    docType=".doc";
                } else if (filename.endsWith("docx")) {
                    docType=".docx";
                } else if(filename.endsWith(".txt")){
                    docType=".txt";
                }

                Document document = new Document();
                document.setFilename(filename);
                document.setFiletype(docType);
                document.setFilesize((int)file.getSize());

                //todo:建文件服务器后设置路径
                document.setAbsolutepath("");
                document.setRelativepath("");

                //设置时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                document.setDocuploadtime(df.format(new Date()));
                document.setDoccomptime("");
                document.setDocstatus("未完成");

                //添加文件
                //防止自增的ID不连续
                alterDocumentTable();
                int docRes = addDocumentTwoitems(document,user,docContent,labelnum,labelitem1,labelitem2);

                switch (docRes){
                    case -1:
                        responseEntity.setStatus(-6);
                        responseEntity.setMsg(filename+"添加文件失败，请检查");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    case -2:
                        responseEntity.setStatus(-7);
                        responseEntity.setMsg(filename+"instance插入失败");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    case -3:
                        responseEntity.setStatus(-8);
                        responseEntity.setMsg(filename+"item插入失败");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    default:
                        responseEntity.setStatus(0);
                        responseEntity.setMsg("文件上传成功");
                        Map<String, Object> data = new HashMap<>();
                        docids.add(docRes);
                        data.put("docIds", docids);//返回文件id，方便后续添加任务
                        responseEntity.setData(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }


    /*
     * 文本配对的多文件上传
     * @param files
     * @param user
     */
    @Transactional
    public ResponseEntity addMultiFileMatchCategory(MultipartFile[] files,User user) throws IllegalStateException{

        boolean filetype = true;
        ResponseEntity responseEntity = new ResponseEntity();
        //遍历处理文件
        responseEntity = FileUtil.checkfile(files);
        if(responseEntity.getStatus()<0){
            return responseEntity;
        }

        //然后检查文件内容是否符合要求
        for (MultipartFile file : files) {
            try {
                String filename = file.getOriginalFilename();//文件名称
                int content = FileUtil.checkmatchcategory(file);
                if(content==-2){
                    responseEntity.setStatus(-3);
                    responseEntity.setMsg(filename +"单个文件大小超过限制");
                    return responseEntity;
                }else if(content==-3){
                    responseEntity.setStatus(-4);
                    responseEntity.setMsg(filename +"每个instance中的list的个数不正确");
                    return responseEntity;
                }else if(content==-4){
                    responseEntity.setStatus(-5);
                    responseEntity.setMsg(filename+"文件中有的item为空");
                    return responseEntity;
                } else if(content==-5){
                    responseEntity.setStatus(-5);
                    responseEntity.setMsg(filename+"文件中有的item超过字数限制,文件分段内容长度太长，请重新用#分段");
                    return responseEntity;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //最后插入document和content
        List<Integer> docids = new ArrayList<Integer>();
        for (MultipartFile file : files) {
            try {
                String filename =file.getOriginalFilename();//文件名称
                String docContent=FileUtil.parsefilecontent(file);
                String docType="";//文件类型

                if (filename.endsWith(".doc")) {
                    docType=".doc";
                } else if (filename.endsWith("docx")) {
                    docType=".docx";
                } else if(filename.endsWith(".txt")){
                    docType=".txt";
                }

                Document document = new Document();
                document.setFilename(filename);
                document.setFiletype(docType);
                document.setFilesize((int)file.getSize());

                //todo:建文件服务器后设置路径
                document.setAbsolutepath("");
                document.setRelativepath("");

                //设置时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                document.setDocuploadtime(df.format(new Date()));
                document.setDoccomptime("");
                document.setDocstatus("未完成");

                //添加文件
                //防止自增的ID不连续
                alterDocumentTable();
                int docRes = addDocumentMatchCategory(document,user,docContent);

                switch (docRes){
                    case -1:
                        responseEntity.setStatus(-6);
                        responseEntity.setMsg(filename+"添加文件失败，请检查");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    case -2:
                        responseEntity.setStatus(-7);
                        responseEntity.setMsg(filename+"instance插入失败");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    case -3:
                        responseEntity.setStatus(-8);
                        responseEntity.setMsg(filename+"item插入失败");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    default:
                        responseEntity.setStatus(0);
                        responseEntity.setMsg("文件上传成功");
                        Map<String, Object> data = new HashMap<>();
                        docids.add(docRes);
                        data.put("docIds", docids);//返回文件id，方便后续添加任务
                        responseEntity.setData(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }


    @Transactional
    public ResponseEntity addMultiFileOneSorting(MultipartFile[] files,User user,String taskType) throws IllegalStateException{

        //boolean filetype = true;
       ResponseEntity responseEntity = new ResponseEntity();
//        //遍历处理文件
//        responseEntity = FileUtil.checkfile(files);
//        if(responseEntity.getStatus()<0){
//            return responseEntity;
//        }
//
//        //然后检查文件内容是否符合要求
//        for (MultipartFile file : files) {
//            try {
//                String filename = file.getOriginalFilename();//文件名称
//                int content = FileUtil.checktwoitem(file);
//                if(content==-2){
//                    responseEntity.setStatus(-3);
//                    responseEntity.setMsg(filename +"单个文件大小超过限制");
//                    return responseEntity;
//                }else if(content==-3){
//                    responseEntity.setStatus(-4);
//                    responseEntity.setMsg(filename +"每个instance中的item的个数不正确");
//                    return responseEntity;
//                }else if(content==-4){
//                    responseEntity.setStatus(-5);
//                    responseEntity.setMsg(filename+"文件中有的item为空");
//                    return responseEntity;
//                } else if(content==-5){
//                    responseEntity.setStatus(-5);
//                    responseEntity.setMsg(filename+"文件中有的item超过字数限制,文件分段内容长度太长，请重新用#分段");
//                    return responseEntity;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        //最后插入document和content
        List<Integer> docIds = new ArrayList<Integer>();
        for (MultipartFile file : files) {
            try {
                String filename =file.getOriginalFilename();//文件名称
                String docContent=FileUtil.parsefilecontent(file);
                String docType="";//文件类型

                if (filename.endsWith(".doc")) {
                    docType=".doc";
                } else if (filename.endsWith("docx")) {
                    docType=".docx";
                } else if(filename.endsWith(".txt")){
                    docType=".txt";
                }

                Document document = new Document();
                document.setFilename(filename);
                document.setFiletype(docType);
                document.setFilesize((int)file.getSize());

                //todo:建文件服务器后设置路径
                document.setAbsolutepath("");
                document.setRelativepath("");

                //设置时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                document.setDocuploadtime(df.format(new Date()));
                document.setDoccomptime("");
                document.setDocstatus("未完成");

                //添加文件
                //防止自增的ID不连续
                alterDocumentTable();
                int docRes = addTwoInstances(document,user,docContent,taskType);

                switch (docRes){
                    case -1:
                        responseEntity.setStatus(-6);
                        responseEntity.setMsg(filename+"添加文件失败，请检查");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    case -2:
                        responseEntity.setStatus(-7);
                        responseEntity.setMsg(filename+"instance插入失败");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    case -3:
                        responseEntity.setStatus(-8);
                        responseEntity.setMsg(filename+"item插入失败");
                        //插入数据库有错误时整体回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break;
                    default:
                        responseEntity.setStatus(0);
                        responseEntity.setMsg("文件上传成功");
                        Map<String, Object> data = new HashMap<>();
                        docIds.add(docRes);
                        data.put("docIds", docIds);//返回文件id，方便后续添加任务
                        responseEntity.setData(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }


    public int addTwoInstances(Document document,User user,String docContent,String taskType){

        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] instanceArr = docContent.split("#");

        //开始插入文件相关信息
        document.setUserid(user.getId());
        int docInsertRes = documentMapper.insertDocument(document);//插入结果

        //插入文件失败
        if(docInsertRes == -1){
            return docInsertRes;
        }else{
            //插入文件成功
            int docId=document.getDid();//插入成功的文件ID
            //防止自增的ID不连续
            instanceMapper.alterInstanceTable();
            //文件内容，用#分隔了
            int addContentRes =addTwoItems(docId,instanceArr,taskType);
            //instance插入失败
            if(addContentRes == -2){
                //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                return -2;
            }else{
                return docId;
            }
        }
    }



    public int addTwoItems(int docId,String[] instanceArr,String taskType){

        for(int i=0;i<instanceArr.length;i++){

            String[] itemArr = instanceArr[i].split("-------");
            Instance instance =new Instance();


            instance.setInsindex(String.valueOf(i+1));
            instance.setInsstatus("未完成");
            instance.setDocumentid(docId);
            instance.setLabelnum(0);
            int instanceRes =instanceMapper.insert(instance);
            //Instance插入失败则返回3
            if(instanceRes == -1){
                return -2;
            }else{
                //Instance插入成功
                int instId=instance.getInsid();//插入成功的文件ID
                //文件内容，用#分隔了
                itemMapper.alterItemTable();
                int addItemRes =addItems(instId,itemArr,taskType);
                //文件内容插入失败
                if(addItemRes == -3){
                    //todo:有内容插入失败的情况，要删除已经插入的文件以及文件内容
                    return -3;
                }
            }
        }
        return 0;
    }




    public int addItems(int instId,String[] itemArr,String taskType){

        for(int i=0;i<itemArr.length;i++){
            Item item = new Item();
            item.setItemcontent(itemArr[i]);
            if(taskType.equals("文本排序")){
                item.setItemindex(String.valueOf(i+1));
            }else if(taskType.equals("文本类比排序")){
                item.setItemindex(String.valueOf(i));
            }
            item.setInstanceid(instId);
            item.setLabelnum(0);
            int itemRes = itemMapper.insert(item);
            //文件内容插入失败则返回3
            if(itemRes == -1){
                return -3;
            }
        }
        return 0;
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
