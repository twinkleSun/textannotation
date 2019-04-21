package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.User;
import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.service.IDtSortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by twinkleStar on 2019/2/2.
 */
@RestController
@RequestMapping("/sorting")
public class DtSortingController {

    @Autowired
    IDtSortingService iDtSortingService;


    /**
     * 根据文件ID获取instance+item
     * @param httpServletRequest
     * @param httpServletResponse
     * @param docId
     * @return
     */
    @GetMapping
    public JSONObject getSortingInstanceItem(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession,
                                             int docId,String status,int taskId) {
        User user =(User)httpSession.getAttribute("currentUser");

        List<InstanceItemEntity> instanceItemEntityList = iDtSortingService.querySortingInstanceItem(docId,user.getId(),status,taskId);

        JSONObject rs = new JSONObject();
        if(instanceItemEntityList != null){
            rs.put("msg","查询成功");
            rs.put("code",0);
            rs.put("instanceItem",instanceItemEntityList);

        }else{
            rs.put("msg","查询失败");
            rs.put("code",-1);
        }
        return rs;
    }


    @GetMapping("/detail")
    public JSONObject getSortingInstanceItemDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession,
                                             int docId,String status,int taskId,int userId) {

        List<InstanceItemEntity> instanceItemEntityList = iDtSortingService.querySortingInstanceItem(docId,userId,status,taskId);

        JSONObject rs = new JSONObject();
        if(instanceItemEntityList != null){
            rs.put("msg","查询成功");
            rs.put("code",0);
            rs.put("instanceItem",instanceItemEntityList);

        }else{
            rs.put("msg","查询失败");
            rs.put("code",-1);
        }
        return rs;
    }

    @PostMapping
    public ResponseEntity doSorting(HttpSession httpSession,
                                         int taskId,int docId,int instanceId, int[] itemIds, int[] newIndex) {

        User user =(User)httpSession.getAttribute("currentUser");
        ResponseEntity responseEntity =iDtSortingService.addSorting(taskId,docId, instanceId,user.getId(),itemIds,newIndex);//创建做任务表的结果
        return responseEntity;
    }
}
