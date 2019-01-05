package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.*;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.model.entity.TaskInfoEntity;
import com.annotation.service.IDocumentService;
import com.annotation.service.ITaskService;
import com.annotation.service.IUserService;
import com.annotation.util.FileUtil;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by twinkleStar on 2018/12/9.
 */

@RestController
@RequestMapping("task/")
public class TaskController {

    @Autowired
    ITaskService iTaskService;

    @Autowired
    IUserService iUserService;


    @Autowired
    IDocumentService iDocumentService;

    private List<Integer> docids = new ArrayList<Integer>();

    @Transactional
    @RequestMapping(value = "addmultifile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addMultiFile(List<MultipartFile> files) throws IllegalStateException, IOException {

        boolean filetype = true;
        List<String> fielist = new ArrayList<String>();
        //获取上传的文件数组
        //List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");
        ResponseEntity responseEntity = new ResponseEntity();
        //List<ResponseEntity> list = new ArrayList<ResponseEntity>();
        //遍历处理文件

        //首先检查文件类型是否符合要求
        for (MultipartFile file:files) {
            try {
                String filename =file.getOriginalFilename();//文件名称
                int res= FileUtil.checkfiletype(filename);
                if(res==-1){
                    responseEntity.setStatus(-5);
                    if(responseEntity.getMsg()==null) {
                        responseEntity.setMsg(filename + "文件类型不符合要求");
                    }else{
                        responseEntity.setMsg(filename + "+"+responseEntity.getMsg());
                    }
                    filetype = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(filetype==false){
            return responseEntity;
        }

        //然后检查文件内容是否符合要求
        for (MultipartFile file : files) {
                try {
                    String filename = file.getOriginalFilename();//文件名称
                    int content = FileUtil.checkfilecontent(file);
                    if(content==-4){
                        responseEntity.setStatus(-10);
                        responseEntity.setMsg(filename +"单个文件大小超过限制");
                        return responseEntity;
                    }else if(content==-2){
                        responseEntity.setStatus(-6);
                        responseEntity.setMsg(filename +"文件中有的item为空");
                        return responseEntity;
                    }else if(content==-3){
                        responseEntity.setStatus(-7);
                        responseEntity.setMsg(filename+"文件中有的item超过字数限制,文件分段内容长度太长，请重新用#分段");
                        return responseEntity;
                    }
                } catch (Exception e) {
                    //ResponseEntity  responseEntity2 = handleException(e,file);
                    e.printStackTrace();
                    //return responseEntity2;
                }
            }

        //最后插入document和content
        if(filetype==true) {
             //User user =(User)httpSession.getAttribute("currentUser");
//                    if(!userid.equals(null) && !userid.equals("")){
//                    user.setId(Integer.parseInt(userid));

            User user =(User)iUserService.queryUserByUsername("巴卫君");

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
                    iDocumentService.alterDocumentTable();
                    int docRes =iDocumentService.addDocument(document,user,docContent);

                    switch (docRes){
                        case -1:
                            responseEntity.setStatus(-8);
                            responseEntity.setMsg(filename+"添加文件失败，请检查");
                            //插入数据库有错误时整体回滚
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            break;
                        case -3:
                            responseEntity.setStatus(-9);
                            responseEntity.setMsg(filename+"文件内容插入失败");
                            //插入数据库有错误时整体回滚
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            break;
                        default:
                            responseEntity.setStatus(0);
                            responseEntity.setMsg(filename+"文件上传成功");
                            Map<String, Object> data = new HashMap<>();
                            docids.add(docRes);
                            data.put("docIds", docids);//返回文件id，方便后续添加任务
                            responseEntity.setData(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return responseEntity;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex,MultipartFile file) {
        ResponseEntity responseEntity = new ResponseEntity();
        String filename =file.getOriginalFilename();//文件名称
        if(ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){
            //request.setAttribute("error", "文件超过长度");
            responseEntity.setStatus(-10);
            responseEntity.setMsg(filename+"添加文件失败，请检查");
        }
        return responseEntity;
    }
    /**
     * 创建任务
     * @param httpSession
     * @param task
     * @param docid
     * @param label
     * @param userid
     * @return
     */
    @Transactional
    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTask(HttpServletRequest request,HttpSession httpSession, Task task,String label,String userid)throws IllegalStateException, IOException {



        User user =(User)httpSession.getAttribute("currentUser");
//        if(!userid.equals(null) && !userid.equals("")){
//            user.setId(Integer.parseInt(userid));
//        }
        ResponseEntity responseEntity = new ResponseEntity();

//        User user =(User)iUserService.queryUserByUsername("test");

        //获取上传的文件数组
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");
        ResponseEntity fileResponseEntity = addMultiFile(files);
        int num = fileResponseEntity.getStatus();
        if(num==-5){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else if(num==-6){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else if(num==-7){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else if(num==-8){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else if(num==-9){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else if(num==-10){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }


        //防止自增的ID不连续
        iTaskService.alterTaskTable();
        int taskRes =iTaskService.addTask(task,user,docids,label);//创建任务的结果


        switch (taskRes){
            case -1:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("添加任务失败，请检查");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                break;
            case -2:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("任务-文件关系插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                break;
            case -3:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("标签插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                break;
            case -4:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("文件-标签关系插入失败");
                //插入数据库有错误时整体回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                break;
            default:
                responseEntity.setStatus(0);
                responseEntity.setMsg("创建任务成功");
                Map<String, Object> data = new HashMap<>();
                StringBuffer fileids = new StringBuffer();
                data.put("taskid", taskRes);//返回文件id，方便后续添加任务
                data.put("docIds",docids);
                responseEntity.setData(data);
        }

        return responseEntity;
    }

    /**
     * 分页查询
     * @param request
     * @param httpServletResponse
     * @param httpSession
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "getTasklist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getTasklist(HttpServletRequest request,HttpServletResponse httpServletResponse, HttpSession httpSession, int page, int limit) {
        User user =(User)httpSession.getAttribute("currentUser");

        List<Task> tasklist = iTaskService.queryTaskByRelatedInfo(user.getId(),page,limit);
        int count=iTaskService.countTasknumByUserId(user.getId());
        JSONObject rs =new JSONObject();
        if(tasklist==null){
            rs.put("msg","查询失败");
            rs.put("code",-1);
            rs.put("count",0);
        }else{
            rs.put("msg","success");
            rs.put("code",0);
            rs.put("data",tasklist);
            rs.put("count",count);
        }

        return rs;
    }

    /**
     * 获取所有的任务
     * todo:不确定有没有用到，忘了？？
     * @return
     */
    @RequestMapping(value = "selectAll",method = RequestMethod.POST)
    @ResponseBody
    public List<Task> selectAll() {
        List<Task> tasks = iTaskService.getAll();
        return tasks;
    }

    /**
     * 分页查询所有的任务
     * @param request
     * @param httpServletResponse
     * @param httpSession
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "getAllTask", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllTask(HttpServletRequest request,HttpServletResponse httpServletResponse, HttpSession httpSession, int page, int limit) {
        List<Task> allTasklist = iTaskService.queryAllTask(page,limit);
        int countAll=iTaskService.countAllTasknum();
        JSONObject jso =new JSONObject();
        if(allTasklist==null){
            jso.put("msg","查询失败");
            jso.put("code",-1);
        }else{
            jso.put("msg","success");
            jso.put("code",0);
            jso.put("data",allTasklist);
            jso.put("count",countAll);
        }

        return jso;
    }


    /**
     * 根据任务ID获取任务详情
     * todo：目前有四种类型
     * @param request
     * @param httpServletResponse
     * @param httpSession
     * @param tid
     * @return
     */
    @RequestMapping(value = "getTaskInfo", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getTaskInfo(HttpServletRequest request,HttpServletResponse httpServletResponse, HttpSession httpSession, int tid) {
        TaskInfoEntity taskInfoEntity = iTaskService.queryTaskInfo(tid);
        String username=iTaskService.queryUserName(tid);
        JSONObject jso =new JSONObject();
        if(taskInfoEntity==null){
            jso.put("msg","查询失败");
            jso.put("code",-1);
        }else{
            jso.put("msg","success");
            jso.put("code",0);
            jso.put("data",taskInfoEntity);
            jso.put("pubUserName",username);
        }

        return jso;
    }


}
