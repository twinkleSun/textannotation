package com.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.model.User;
import com.annotation.model.entity.ParagraphLabelEntity;
import com.annotation.service.IDtClassifyService;
import com.annotation.service.IDtExtractionService;
import com.annotation.service.IParagraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by twinkleStar on 2018/12/16.
 */


@RestController
@RequestMapping("paragraph/")
public class ParagraphController {

    @Autowired
    IParagraphService iParagraphService;

}
