package com.annotation.service;

import com.annotation.model.User;

/**
 * Created by twinkleStar on 2018/12/4.
 */


public interface IUserService {

    User queryUserByUsername(String username);

    User queryUserByUserId(int id);

    /**
     * 新建用户
     * @param user
     * @return
     */
    int insertNewUser(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUserInfo(User user);

    /**
     * 根据邮箱查询用户是否已经存在
     * @param email
     * @return
     */
    User queryUserByEmail(String email);
}
