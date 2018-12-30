package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;

import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.InstanceListitemEntity;
import com.annotation.service.IInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by twinkleStar on 2018/12/29.
 */

@RestController
@RequestMapping("instance/")
public class InstanceController {

    @Autowired
    IInstanceService iInstanceService;

    @RequestMapping(value = "getInstanceItem", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getContent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int docId) {
        List<InstanceItemEntity> instanceItemEntityList = iInstanceService.queryInstanceItem(docId);
        JSONObject rs = new JSONObject();
        if(instanceItemEntityList != null){
            rs.put("msg","查询文件内容成功");
            rs.put("code",0);
            rs.put("instanceItem",instanceItemEntityList);
        }else{
            rs.put("msg","查询文件内容失败");
            rs.put("code",-1);
        }
        return rs;
    }



    @RequestMapping(value = "getInstanceListitem", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getInstanceListitem(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int docId) {
        List<InstanceListitemEntity> instanceItemEntityList = iInstanceService.queryInstanceListitem(docId);
        JSONObject rs = new JSONObject();
        if(instanceItemEntityList != null){
            rs.put("msg","查询文件内容成功");
            rs.put("code",0);
            rs.put("instanceItem",instanceItemEntityList);
        }else{
            rs.put("msg","查询文件内容失败");
            rs.put("code",-1);
        }
        return rs;
    }

}
