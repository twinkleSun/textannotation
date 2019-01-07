package com.annotation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
     * 创建信息抽取和分类的任务
     * @param httpSession
     * @param request
     * @param multipartFiles
     * @param task
     * @param label
     * @param userid
     * @return
     */
    @Transactional
    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTask(@RequestParam( value="files[]",required=false)MultipartFile[] multipartFiles,HttpServletRequest request,HttpSession httpSession, Task task,String label,String userid)throws IllegalStateException, IOException {

        //User user =(User)httpSession.getAttribute("currentUser");
//        if(!userid.equals(null) && !userid.equals("")){
//            user.setId(Integer.parseInt(userid));
//        }
        ResponseEntity responseEntity = new ResponseEntity();
        List<Integer> docids = new ArrayList<Integer>();
        User user =(User)iUserService.queryUserByUsername("test");

        //获取上传的文件数组
        ResponseEntity fileResponseEntity = iDocumentService.addMultiFile(multipartFiles,user);
        int num = fileResponseEntity.getStatus();
        if(num<0){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else{
            //获取到上传文件的ID
            HashMap<String,List<Integer>> hashmap = (HashMap)fileResponseEntity.getData();
            List<Integer> list = hashmap.get("docIds");
            for(int i=0;i<list.size();i++){
                docids.add(list.get(i));
                //System.out.println("----------"+list.get(i));
            }
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
     * 创建两个item的任务
     * @param httpSession
     * @param request
     * @param multipartFiles
     * @param task
     * @param label
     * @param labelstr1
     * @param labelstr2
     * @param labelnum
     * @param labelitem1
     * @param labelitem2
     * @param userid
     * @return
     */
    @Transactional
    @RequestMapping(value = "addTaskTwoitems", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTaskTwoitems(@RequestParam( value="files[]",required=false)MultipartFile[] multipartFiles,HttpServletRequest request,HttpSession httpSession, Task task,String label,String labelstr1,String labelstr2,int labelnum,int labelitem1,int labelitem2,String userid)throws IllegalStateException, IOException {

        //User user =(User)httpSession.getAttribute("currentUser");
//        if(!userid.equals(null) && !userid.equals("")){
//            user.setId(Integer.parseInt(userid));
//        }
        ResponseEntity responseEntity = new ResponseEntity();
        List<Integer> docids = new ArrayList<Integer>();
        User user =(User)iUserService.queryUserByUsername("test");

        //获取上传的文件数组
        ResponseEntity fileResponseEntity = iDocumentService.addMultiFileTwoItems(multipartFiles,user,labelnum,labelitem1,labelitem2);
        int num = fileResponseEntity.getStatus();
        if(num<0){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else{
            //获取到上传文件的ID
            HashMap<String,List<Integer>> hashmap = (HashMap)fileResponseEntity.getData();
            List<Integer> list = hashmap.get("docIds");
            for(int i=0;i<list.size();i++){
                docids.add(list.get(i));
                //System.out.println("----------"+list.get(i));
            }
        }

        //防止自增的ID不连续
        iTaskService.alterTaskTable();
        ResponseEntity taskRes = new ResponseEntity();
        taskRes =iTaskService.addTaskTwoitems(task,user,docids,label,labelstr1,labelstr2);//创建任务的结果
        int resnum = taskRes.getStatus();
        if(resnum<0){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else{
            responseEntity.setStatus(0);
            responseEntity.setMsg("创建任务成功");
            Map<String, Object> data = new HashMap<>();
            StringBuffer fileids = new StringBuffer();
            data.put("taskid", taskRes.getData());//返回文件id，方便后续添加任务
            data.put("docIds",docids);
            responseEntity.setData(data);
        }

        return responseEntity;
    }


    /**
     * 创建文本配对的任务
     * @param httpSession
     * @param request
     * @param multipartFiles
     * @param task
     * @param userid
     * @return
     */
    @Transactional
    @RequestMapping(value = "addTaskTMatchCategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTaskMatchCategory(@RequestParam( value="files[]",required=false)MultipartFile[] multipartFiles,HttpServletRequest request,HttpSession httpSession, Task task,String userid)throws IllegalStateException, IOException {

        //User user =(User)httpSession.getAttribute("currentUser");
//        if(!userid.equals(null) && !userid.equals("")){
//            user.setId(Integer.parseInt(userid));
//        }
        ResponseEntity responseEntity = new ResponseEntity();
        List<Integer> docids = new ArrayList<Integer>();
        User user =(User)iUserService.queryUserByUsername("test");

        //获取上传的文件数组
        ResponseEntity fileResponseEntity = iDocumentService.addMultiFileMatchCategory(multipartFiles,user);
        int num = fileResponseEntity.getStatus();
        if(num<0){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else{
            //获取到上传文件的ID
            HashMap<String,List<Integer>> hashmap = (HashMap)fileResponseEntity.getData();
            List<Integer> list = hashmap.get("docIds");
            for(int i=0;i<list.size();i++){
                docids.add(list.get(i));
                //System.out.println("----------"+list.get(i));
            }
        }

        //防止自增的ID不连续
        iTaskService.alterTaskTable();
        ResponseEntity taskRes = new ResponseEntity();
        taskRes =iTaskService.addTaskMatchCategory(task,user,docids);//创建任务的结果
        int resnum = taskRes.getStatus();
        if(resnum<0){
            responseEntity.setStatus(-1);
            responseEntity.setMsg(fileResponseEntity.getMsg());
            //插入数据库有错误时整体回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseEntity;
        }else{
            responseEntity.setStatus(0);
            responseEntity.setMsg("创建任务成功");
            Map<String, Object> data = new HashMap<>();
            StringBuffer fileids = new StringBuffer();
            data.put("taskid", taskRes.getData());//返回文件id，方便后续添加任务
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
