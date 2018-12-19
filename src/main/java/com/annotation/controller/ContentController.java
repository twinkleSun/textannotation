package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.Content;
import com.annotation.model.ResponseEntity;
import com.annotation.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/16.
 */


@RestController
@RequestMapping("content/")
public class ContentController {

    @Autowired
    IContentService iContentService;

    /**
     * 根据文件ID查询内容
     * @param httpServletRequest
     * @param httpServletResponse
     * @param docId
     * @return
     */
    @RequestMapping(value = "getContent", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getContent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int docId) {
        List<Content> contentList = iContentService.selectContentByDocId(docId);
        JSONObject rs = new JSONObject();
        if(contentList != null){
            rs.put("msg","查询文件内容成功");
            rs.put("code",0);
            rs.put("data",contentList);
        }else{
            rs.put("msg","查询文件内容失败");
            rs.put("code",-1);
        }
        return rs;
    }
}
