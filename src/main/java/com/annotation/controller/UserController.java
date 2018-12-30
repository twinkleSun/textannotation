package com.annotation.controller;

import com.annotation.model.entity.ResponseEntity;
import com.annotation.model.User;
import com.annotation.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/4.
 */

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    IUserService iUserService;

    /**
     * 登陆，
     * 登陆成功后只返回用户ID和用户名
     * @param request
     * @param httpResponse
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity userLogin(HttpServletRequest request, HttpServletResponse httpResponse, String username, String password) {
        User user =iUserService.queryUserByUsername(username);//查找用户
        ResponseEntity responseEntity = new ResponseEntity();//设置返回值
        //如果用户不存在
        if(user ==null){
            responseEntity.setStatus(-1);
            responseEntity.setMsg("该用户不存在");
        }else{
            //如果用户名密码正确
            if (user.getPassword().equals(password)){
                HttpSession session=request.getSession(true);//session的创建
                session.setAttribute("currentUser",user);//给session添加属性

                //设置返回值
                responseEntity.setStatus(0);
                responseEntity.setMsg("登陆成功");
                Map<String, Object> data = new HashMap<>();
                data.put("username", user.getUsername());
                data.put("userid", user.getId());
                responseEntity.setData(data);
            }else{
                //密码错误的返回
                responseEntity.setStatus(-1);
                responseEntity.setMsg("密码错误");
            }
        }
        return responseEntity;

    }

    /**
     * 退出登录，移除session
     * @param request
     * @param httpServletResponse
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity userLogout(HttpServletRequest request,HttpServletResponse httpServletResponse,HttpSession httpSession){
        httpSession.removeAttribute("currentUser");
        ResponseEntity responseEntity = new ResponseEntity();//设置返回值
        responseEntity.setStatus(0);
        responseEntity.setMsg("退出登录成功");
        return responseEntity;
    }

    /**
     * 根据用户ID获取用户全部信息
     * @param request
     * @param httpServletResponse
     * @param httpSession
     * @param userid
     * @return
     */
    @RequestMapping(value = "getUserinfo",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getUserInfo(HttpServletRequest request, HttpServletResponse httpServletResponse,
                                      HttpSession httpSession, String userid){
        User userInfo;
        ResponseEntity responseEntity = new ResponseEntity();
        if(userid==null){
            User user =(User)httpSession.getAttribute("currentUser");
            userInfo =iUserService.queryUserByUserId(user.getId());
        }else{
            userInfo =iUserService.queryUserByUserId(Integer.parseInt(userid));
        }

        if(userInfo != null){
            //设置返回值
            responseEntity.setStatus(0);
            responseEntity.setMsg("登陆成功");
            //设置返回值，用户名、用户ID
            Map<String, Object> data = new HashMap<>();
            data.put("userInfo",userInfo);
            responseEntity.setData(data);
        }else{
            responseEntity.setStatus(-1);
            responseEntity.setMsg("查询失败，该用户不存在");//一般查询的是已经连接的用户的信息，不存在失败情况
        }
        return responseEntity;
    }

}
