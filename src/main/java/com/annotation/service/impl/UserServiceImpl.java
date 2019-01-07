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


    /**
     * 新建用户
     * @param user
     * @return
     */
    public int insertNewUser(User user){
        int res=userMapper.insert(user);
        return res;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public int updateUserInfo(User user){
        int res=userMapper.updateByPrimaryKey(user);

       return res;

    }

    /**
     * 根据邮箱查询用户是否已经存在
     * @param email
     * @return
     */
    public User queryUserByEmail(String email){
        User user=userMapper.selectUserByEmail(email);
        return user;
    }

}
