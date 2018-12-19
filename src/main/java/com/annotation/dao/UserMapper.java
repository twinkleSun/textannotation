package com.annotation.dao;

import com.annotation.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User selectUserByUsername(String username);

    /**
     * 根据用户ID查找用户
     * @param id
     * @return
     */
    User selectUserByUserId(Integer id);
}