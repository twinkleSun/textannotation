package com.annotation.service;

import com.annotation.model.Task;
import com.annotation.model.User;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/9.
 */
public interface ITaskService {

    /**
     * 添加任务
     * @param task
     * @param user
     * @param docId
     * @param labels
     * @return
     */
     int addTask(Task task, User user,int docId,String labels);

    /**
     * 分页查询
     * @param userId
     * @param page
     * @param limit
     * @return
     */
     List<Task> queryTaskByRelatedInfo(int userId,int page,int limit);

    /**
     * 根据用户ID查询任务总数
     * @param userId
     * @return
     */
     int countTasknumByUserId(int userId);


}
