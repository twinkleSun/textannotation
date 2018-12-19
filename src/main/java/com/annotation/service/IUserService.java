package com.annotation.service;

import com.annotation.model.User;

/**
 * Created by twinkleStar on 2018/12/4.
 */


public interface IUserService {


    public User queryUserByUsername(String username);

    public User queryUserByUserId(int id);
}
