package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.User;
import com.annotation.model.entity.ParagraphLabelEntity;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.service.IDParagraphService;
import com.annotation.service.IDtClassifyService;
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
@RequestMapping("classify")
public class DtClassifyController {

    @Autowired
    IDtClassifyService iDtClassifyService;
    @Autowired
    ResponseUtil responseUtil;
    @Autowired
    IDParagraphService idParagraphService;

    /**
     * 根据文件ID查询内容
     * @param httpServletRequest
     * @param httpServletResponse
     * @param docId
     * @return
     */
    @GetMapping
    public JSONObject getClassificationPara(HttpServletRequest httpServletRequest, HttpSession httpSession, HttpServletResponse httpServletResponse,
                                            int docId,String status,int taskId) {
        User user =(User)httpSession.getAttribute("currentUser");

        List<ParagraphLabelEntity> paragraphLabelEntityList=iDtClassifyService.queryClassifyParaLabel(docId,user.getId(),status,taskId);

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
     * @param dtId
     * @return
     */
    @PostMapping
    public ResponseEntity doClassify(HttpSession httpSession,
                                     int taskId,int docId,int paraId,int[] labelId) {

        User user =(User)httpSession.getAttribute("currentUser");

        int dtid =iDtClassifyService.addClassify(user.getId(),taskId, docId, paraId,labelId);//创建做任务表的结果

        if(dtid==4001 || dtid==4005|| dtid==4006|| dtid==4007|| dtid==4008|| dtid==4009){
            ResponseEntity responseEntity = responseUtil.judgeResult(dtid);
            return responseEntity;
        }else{
            return responseUtil.judgeDoTaskController(dtid);
        }

    }


    /**
     * 根据文件ID和UserID查询内容
     * @param httpServletRequest
     * @param httpServletResponse
     * @param docId
     * @return
     */
    @GetMapping("/detail")
    public JSONObject getClassificationDetail(HttpServletRequest httpServletRequest, HttpSession httpSession, HttpServletResponse httpServletResponse,
                                              int docId,int userId,String status,int taskId) {
        List<ParagraphLabelEntity> paragraphLabelEntityList=iDtClassifyService.queryClassifyParaLabel(docId,userId,status,taskId);

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


//    @PostMapping("/doc/status")
//    public ResponseEntity updateStatus(HttpServletRequest httpServletRequest, HttpSession httpSession, HttpServletResponse httpServletResponse,
//                                              int docId,int taskId) {
//        User user =(User)httpSession.getAttribute("currentUser");
//
//        int upRes=idParagraphService.updateStatus(user.getId(),docId,taskId);
//        if(upRes==4010|| upRes==4011|| upRes==4012){
//            ResponseEntity responseEntity=responseUtil.judgeResult(upRes);
//            return responseEntity;
//        }else{
//            ResponseEntity responseEntity=new ResponseEntity();
//            responseEntity.setMsg("完成该文档");
//            responseEntity.setStatus(0);
//            return responseEntity;
//        }
//
//    }




}
