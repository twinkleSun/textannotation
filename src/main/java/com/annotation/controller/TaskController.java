package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.Label;
import com.annotation.model.ResponseEntity;
import com.annotation.model.Task;
import com.annotation.model.User;
import com.annotation.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by twinkleStar on 2018/12/9.
 */

@RestController
@RequestMapping("task/")
public class TaskController {

    @Autowired
    ITaskService iTaskService;

    /**
     * 创建任务
     * @param httpSession
     * @param task
     * @param docid
     * @param label
     * @param userid
     * @return
     */
    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTask(HttpSession httpSession, Task task, int docid,String label,String userid) {

        User user =(User)httpSession.getAttribute("currentUser");
        if(!userid.equals(null) && !userid.equals("")){
            user.setId(Integer.parseInt(userid));
        }

        int taskRes =iTaskService.addTask(task,user,docid,label);//创建任务的结果
        ResponseEntity responseEntity = new ResponseEntity();

        switch (taskRes){
            case -1:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("添加任务失败，请检查");
                break;
            case -2:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("任务-文件关系插入失败");
                break;
            case -3:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("标签插入失败");
                break;
            case -4:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("文件-标签关系插入失败");
                break;
            default:
                responseEntity.setStatus(0);
                responseEntity.setMsg("文件上传成功");
                Map<String, Object> data = new HashMap<>();
                data.put("taskid", taskRes);//返回文件id，方便后续添加任务
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
        }else{
            rs.put("msg","success");
            rs.put("code",0);
            rs.put("data",tasklist);
            rs.put("count",count);
        }

        return rs;
    }





}
