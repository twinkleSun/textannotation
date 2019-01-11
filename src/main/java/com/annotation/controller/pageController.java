package com.annotation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by twinkleStar on 2019/1/9.
 */
@RestController
@RequestMapping("page/")
public class pageController {

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response,int tid){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");

        mav.addObject("time", tid);
        return mav;
    }

}
