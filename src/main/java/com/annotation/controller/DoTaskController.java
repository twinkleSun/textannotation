package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.*;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.service.IDoTaskService;
import com.annotation.service.IUserService;
import com.annotation.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
    public ResponseEntity addTask(HttpSession httpSession, DoTask dotask, int labelId, int conbegin, int conend) {

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

    /**
     * 文本关系类型标注
     * todo:这个labelid待定
     * @param httpSession
     * @param dtInstance
     * @param itemId
     * @param labelId
     * @param item_label
     * @return
     */
    @RequestMapping(value = "addInstanceItem", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addInstanceItem(HttpSession httpSession, DtInstance dtInstance, int itemId, int labelId, String item_label) {

        User user =(User)httpSession.getAttribute("currentUser");

        //User user =(User)iUserService.queryUserByUsername("test");
        // userid = user.getId();

        int dtInstItemRes =iDoTaskService.addInstanceItem(dtInstance,user.getId(),itemId,labelId,item_label);//创建做任务表的结果

        ResponseEntity responseEntity = new ResponseEntity();

        switch (dtInstItemRes){
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
                data.put("dtInstid", dtInstItemRes);//返回做任务id
                responseEntity.setData(data);
        }
        return responseEntity;
    }

    /**
     * 做任务--文本配对关系
     * @param httpSession
     * @param dtInstance
     * @param aListitemId
     * @param bListitemId
     * @return
     */
    @RequestMapping(value = "addListItem", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addListItem(HttpSession httpSession, DtInstance dtInstance, int aListitemId, int bListitemId) {

        User user =(User)httpSession.getAttribute("currentUser");

        //User user =(User)iUserService.queryUserByUsername("test");
        // userid = user.getId();

        int dtInstItRes =iDoTaskService.addListItem(dtInstance,user.getId(),aListitemId,bListitemId);
        JSONObject jso =new JSONObject();

        switch (dtInstItRes){
            case -1:
                jso.put("msg","添加做任务表失败，请检查");
                jso.put("code",-1);
                break;
            case -2:
                jso.put("msg","添加做任务详细信息失败");
                jso.put("code",-1);
                break;
            case -3:
                jso.put("msg","更新任务状态失败");
                jso.put("code",-1);
                break;
            case -4:
                jso.put("msg","更新文档状态失败");
                jso.put("code",-1);
                break;
            default:
                jso.put("msg","添加做任务表成功");
                jso.put("code",0);
                jso.put("dtInstid",dtInstItRes);
        }
        return jso;
    }
}