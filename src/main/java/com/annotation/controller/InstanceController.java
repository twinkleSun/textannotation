package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;

import com.annotation.model.Label;
import com.annotation.model.User;
import com.annotation.model.entity.InstanceItemEntity;
import com.annotation.model.entity.InstanceListitemEntity;
import com.annotation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;
import java.util.List;

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



}
