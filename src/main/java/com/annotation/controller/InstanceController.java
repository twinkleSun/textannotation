package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;

import com.annotation.model.DtdItemLabel;
import com.annotation.model.Label;
import com.annotation.model.User;
import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.InstanceListitemEntity;
import com.annotation.service.IInstanceService;
import com.annotation.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/29.
 */

@RestController
@RequestMapping("instance/")
public class InstanceController {

    @Autowired
    IInstanceService iInstanceService;
    @Autowired
    ILabelService iLabelService;



    /**
     * 根据文件ID获取instance+item
     * @param httpServletRequest
     * @param httpServletResponse
     * @param docId
     * @return
     */
    @RequestMapping(value = "getInstanceItem", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getContent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession, int docId,String userid) {
        User user =(User)httpSession.getAttribute("currentUser");

        List<InstanceItemEntity> instanceItemEntityList = iInstanceService.queryInstanceItem(docId,user.getId());
        List<Label> instanceLabel =iLabelService.queryInstanceLabelByDocId(docId);
        List<Label> item1Label =iLabelService.queryItem1LabelByDocId(docId);
        List<Label> item2Label = iLabelService.queryItem2LabelByDocId(docId);

        JSONObject rs = new JSONObject();
        if(instanceItemEntityList != null){
            rs.put("msg","查询成功");
            rs.put("code",0);
            rs.put("instanceItem",instanceItemEntityList);
            rs.put("instanceLabel",instanceLabel);
            rs.put("item1Label",item1Label);
            rs.put("item2Label",item2Label);
        }else{
            rs.put("msg","查询失败");
            rs.put("code",-1);
        }
        return rs;
    }

    /**
     * 根据文件ID查询instance+listitem
     * @param httpServletRequest
     * @param httpServletResponse
     * @param docId
     * @return
     */
    @RequestMapping(value = "getInstanceListitem", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getInstanceListitem(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession,int docId,String userid) {
        User user =(User)httpSession.getAttribute("currentUser");


        List<InstanceListitemEntity> instanceItemEntityList = iInstanceService.queryInstanceListitem(docId,user.getId());
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
