package com.annotation.service.impl;

import com.annotation.dao.UserMapper;
import com.annotation.model.User;
import com.annotation.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by twinkleStar on 2018/12/4.
 */

@Repository
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User queryUserByUsername(String username){
        User user =userMapper.selectUserByUsername(username);
        return user;
    }

    /**
     * 根据用户ID查找用户
     * @param id
     * @return
     */
    public User queryUserByUserId(int id){
        User user =userMapper.selectUserByUserId(id);
        return user;
    }

}
