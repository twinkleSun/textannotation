package com.annotation.controller;

import com.annotation.model.User;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.service.IDInstanceService;
import com.annotation.service.IDParagraphService;
import com.annotation.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by twinkleStar on 2019/2/8.
 */
@RestController
@RequestMapping("dinstance")
public class DInstanceController {

    @Autowired
    IDInstanceService idInstanceService;
    @Autowired
    ResponseUtil responseUtil;



    @PostMapping("/doc/status")
    public ResponseEntity updateStatusByDocId(HttpServletRequest httpServletRequest, HttpSession httpSession, HttpServletResponse httpServletResponse,
                                       int docId, int taskId) {
        User user =(User)httpSession.getAttribute("currentUser");

        int upRes=idInstanceService.updateStatusByDocId(user.getId(),docId,taskId);
        if(upRes==4010|| upRes==4011|| upRes==4012){
            ResponseEntity responseEntity=responseUtil.judgeResult(upRes);
            return responseEntity;
        }else{
            ResponseEntity responseEntity=new ResponseEntity();
            responseEntity.setMsg("完成该文档");
            responseEntity.setStatus(0);
            return responseEntity;
        }

    }


    @PostMapping("/status")
    public ResponseEntity updateStatus(HttpServletRequest httpServletRequest, HttpSession httpSession, HttpServletResponse httpServletResponse,
                                       int docId, int taskId,int instanceId) {
        User user =(User)httpSession.getAttribute("currentUser");

        int upRes=idInstanceService.updateStatus(user.getId(),docId,taskId,instanceId);
        if(upRes==4010|| upRes==4013|| upRes==4012){
            ResponseEntity responseEntity=responseUtil.judgeResult(upRes);
            return responseEntity;
        }else{
            ResponseEntity responseEntity=new ResponseEntity();
            responseEntity.setMsg("完成该文档");
            responseEntity.setStatus(0);
            return responseEntity;
        }

    }

}
