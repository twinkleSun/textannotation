package com.annotation.service;

import com.annotation.model.Task;
import com.annotation.model.entity.ResponseEntity;
import com.annotation.model.entity.TaskInfoEntity;
import com.annotation.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/9.
 */
public interface ITaskService {

    /**
     * 添加任务
     *
     * @param task
     * @param user
     * @param docId
     * @param labels
     * @return
     */
    int addTask(Task task, User user, List<Integer> docId, String labels,String colors);

    /**
     * 分页查询
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    List<Task> queryTaskByRelatedInfo(int userId, int page, int limit);

    /**
     * 根据用户ID查询任务总数
     *
     * @param userId
     * @return
     */
    int countTasknumByUserId(int userId);

    /**
     * 获取所有的任务
     *
     * @return
     */
    public List<Task> getAll();

    /**
     * 分页查询所有的任务
     *
     * @param page
     * @param limit
     * @return
     */
    List<Task> queryAllTask(int page, int limit);

    /**
     * 查询所有任务的数量
     *
     * @return
     */
    int countAllTasknum();

    /**
     * 根据ID查询任务详情
     *
     * @param tid
     * @return
     */
    TaskInfoEntity queryTaskInfo(int tid);

    /**
     * 根据任务ID查询发布者姓名
     *
     * @param tid
     * @return
     */
    String queryUserName(int tid);


    /**
     * 设置数据库自增长为1
     *
     * @return
     */
    int alterTaskTable();

    /**
     * 两个item添加任务
     *
     * @param task
     * @param user
     * @param docids
     * @param labels
     * @param labelstr1
     * @param labelstr2
     * @return
     */
    ResponseEntity addTaskTwoitems(Task task, User user, List<Integer> docids, String labels, String labelstr1, String labelstr2);

    /**
     * 文本匹配添加任务
     * @param task
     * @param user
     * @param docids
     * @return
     */
    @Transactional
    ResponseEntity  addTaskMatchCategory(Task task, User user, List<Integer> docids);


    ResponseEntity  addTaskOneSorting(Task task, User user, List<Integer> docids);

}
