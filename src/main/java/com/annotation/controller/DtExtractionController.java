package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.User;
import com.annotation.model.entity.ParagraphLabelEntity;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.service.IDParagraphService;
import com.annotation.service.IDtExtractionService;
import com.annotation.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2019/2/2.
 */
@RestController
@RequestMapping("/extraction")
public class DtExtractionController {

    @Autowired
    IDtExtractionService iDtExtractionService;
    @Autowired
    ResponseUtil responseUtil;



    /**
     * 根据文件ID查询内容
     * 信息抽取
     * @param httpServletRequest
     * @param httpServletResponse
     * @param docId
     * @return
     */
    @GetMapping
    public JSONObject getExtractionPara(HttpServletRequest httpServletRequest, HttpSession httpSession, HttpServletResponse httpServletResponse,
                                        int docId,String status,int taskId) {
        User user =(User)httpSession.getAttribute("currentUser");

        List<ParagraphLabelEntity> paragraphLabelEntityList=iDtExtractionService.queryExtractionParaLabel(docId,user.getId(),status,taskId);

        //List<Content> contentList = iContentService.selectContentByDocId(docId);
        JSONObject rs = new JSONObject();
        if(paragraphLabelEntityList != null){
            rs.put("msg","查询文件内容成功");
            rs.put("code",0);
            rs.put("data",paragraphLabelEntityList);
        }else{
            rs.put("msg","查询文件内容失败");
            rs.put("code",-1);
        }
        return rs;
    }


    /**
     *
     * @param httpSession
     * @param taskId
     * @param docId
     * @param paraId
     * @param labelId
     * @param indexBegin
     * @param indexEnd
     * @return
     */
   @PostMapping
    public ResponseEntity doExtraction(HttpSession httpSession,
                                       int taskId,int docId,int paraId,int labelId,int indexBegin,int indexEnd) {

        User user =(User)httpSession.getAttribute("currentUser");

        int dtid =iDtExtractionService.addExtraction(user.getId(),taskId,docId,paraId,labelId,indexBegin,indexEnd);//创建做任务表的结果

        if(dtid==4001 || dtid==4002|| dtid==4003|| dtid==4005){
            ResponseEntity responseEntity = responseUtil.judgeResult(dtid);
            return responseEntity;
        }else{
            return responseUtil.judgeDoTaskController(dtid);
        }

    }


    @GetMapping("/detail")
    public JSONObject getExtractionDetail(HttpServletRequest httpServletRequest, HttpSession httpSession, HttpServletResponse httpServletResponse,
                                          int docId,int userId,String status,int taskId) {


        List<ParagraphLabelEntity> paragraphLabelEntityList=iDtExtractionService.queryExtractionParaLabel(docId,userId,status,taskId);

        //List<Content> contentList = iContentService.selectContentByDocId(docId);
        JSONObject rs = new JSONObject();
        if(paragraphLabelEntityList != null){
            rs.put("msg","查询文件内容成功");
            rs.put("code",0);
            rs.put("data",paragraphLabelEntityList);
        }else{
            rs.put("msg","查询文件内容失败");
            rs.put("code",-1);
        }
        return rs;
    }





}
