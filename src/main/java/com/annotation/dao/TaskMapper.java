package com.annotation.dao;

import com.annotation.model.Task;
import com.annotation.model.entity.TaskInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/9.
 */
@Mapper
@Repository
public interface TaskMapper {

    /**
     * 插入任务
     * @param task
     * @return
     */
    int insertTask(Task task);

    /**
     * 分页查询
     * todo:任务返回发布者的用户名
     * @param data
     * @return
     */
    List<Task> selectTaskByRelatedInfo(Map<String,Object> data);

    /**
     * 计算用户发布的任务总数
     * @param userid
     * @return
     */
    Integer countTaskNumByUserid(int userid);

    /**
     * 获取所有的任务
     * todo:任务返回发布者的用户名
     * @return
     */
    List<Task> getAll();


    /**
     * 根据taskid查询task
     * todo：也返回任务的发布者姓名
     * @param taskid
     * @return
     */
    Task selectTaskById(int taskid);

    /**
     * 根据taskid更新task的完成状态
     * @param task
     * @return
     */
    int updateById(Task task);

    /**
     * 分页查询
     * todo:也返回任务的发布者姓名
     * @return
     */
    List<Task> selectAllTask(Map<String,Object> data);

    /**
     * 计算所有任务的数量
     * @return
     */
    Integer countAllTaskNum();

    /**
     * 信息抽取和分类调用接口
     * 该类任务一定有标签和文件
     * 否则会出错
     * @param tid
     * @return
     */
    TaskInfoEntity selectTaskInfoWithDocLabel(Integer tid);

    /**
     * 文本关系类别标注调用接口
     * 该类任务只返回了文件和任务详情
     * @param tid
     * @return
     */
    TaskInfoEntity selectTaskInfoWithDoc(Integer tid);

    /**
     * 适用文本关系类别标注
     * 查询文件关联的label
     * @param tid
     * @return
     */
    TaskInfoEntity selectTaskInfoWithLabel(Integer tid);

    /**
     * 查询用户名
     * todo:上面三个查询接口直接返回用户名
     * @param tid
     * @return
     */
    String selectUserName(Integer tid);

}
