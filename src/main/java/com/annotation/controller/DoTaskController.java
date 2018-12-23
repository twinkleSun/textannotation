package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.Content;
import com.annotation.model.ResponseEntity;
import com.annotation.model.DoTask;
import com.annotation.model.User;
import com.annotation.service.IDoTaskService;
import com.annotation.service.IUserService;
import com.annotation.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/16.
 */


@RestController
@RequestMapping("dotask/")
public class DoTaskController {

    @Autowired
    IDoTaskService iDoTaskService;

    @Autowired
    IUserService iUserService;

    @Autowired
    ITaskService iTaskService;

    /**
     * 创建任务
     * @param httpSession
     * @param dotask
     * @param labelId
     * @param conbegin
     * @param conend
     * @return
     */
    @RequestMapping(value = "addDoTask", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTask(HttpSession httpSession, DoTask dotask,int labelId,int conbegin,int conend) {

        User user =(User)httpSession.getAttribute("currentUser");
        String userid = String.valueOf(user.getId());

        //User user =(User)iUserService.queryUserByUsername("test");
       // userid = String.valueOf(user.getId());

        int dotaskRes =iDoTaskService.addDoTask(dotask,userid,labelId,conbegin,conend);//创建做任务表的结果

        ResponseEntity responseEntity = new ResponseEntity();

        switch (dotaskRes){
            case -1:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("添加做任务失败，请检查");
                break;
            case -2:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("添加做任务详细信息失败");
                break;
            case -3:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("更新任务状态失败");
                break;
            case -4:
                responseEntity.setStatus(-1);
                responseEntity.setMsg("更新文档状态失败");
                break;
            default:
                responseEntity.setStatus(0);
                responseEntity.setMsg("添加做任务表成功");
                Map<String, Object> data = new HashMap<>();
                data.put("dotaskid", dotaskRes);//返回做任务id
                responseEntity.setData(data);
        }
        return responseEntity;
    }
}