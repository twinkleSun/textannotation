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

    /**
     * 新建用户
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 修改用户信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);


    /**
     * 根据邮箱查找用户是否已经存在
     * @param email
     * @return
     */
    User selectUserByEmail(String email);
}