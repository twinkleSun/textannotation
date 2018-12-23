package com.annotation.service;

import com.annotation.model.Content;
import com.annotation.model.DoTask;
import com.annotation.model.User;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/16.
 */
public interface IDoTaskService {

    /**
     * 添加做任务表
     * @param dotask
     * @param userid
     * @param labelId
     * @param conbegin
     * @param conend
     * @return
     */
    int addDoTask(DoTask dotask, String userid, int labelId, int conbegin,int conend);
}