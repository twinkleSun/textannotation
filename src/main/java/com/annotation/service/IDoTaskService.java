package com.annotation.service;

import com.annotation.model.Content;
import com.annotation.model.DoTask;
import com.annotation.model.DtInstance;
import com.annotation.model.User;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/16.
 */
public interface IDoTaskService {

    /**
     * 添加做任务表
     * 做任务----信息抽取+分类
     * 分类的conbegin/conend直接传-1
     * @param dotask
     * @param userid
     * @param labelId
     * @param conbegin
     * @param conend
     * @return
     */
    int addDoTask(DoTask dotask, String userid, int labelId, int conbegin,int conend);


    /**
     * 做任务---添加文本关系类型标注
     * @param dtInstance
     * @param userId
     * @param itemId1
     * @param itemLabel1
     * @param itemId2
     * @param itemLabel2
     * @return
     */
    int addInstanceItem(DtInstance dtInstance,int userId,int itemId1,String itemLabel1,int itemId2,String itemLabel2);

    /**
     * 做任务---文本配对关系
     * @param dtInstance
     * @param userId
     * @param aListItemId
     * @param bListItemId
     * @return
     */
   int addListItem(DtInstance dtInstance,int userId,int aListItemId,int bListItemId);

    /**
     * 做任务--插入dtdItemLabel表的操作，因为要插入两次，所以单独写出来
     * todo:不同service之间可以互相调用吗？
     * @param dtInstId
     * @param itemId
     * @param itemLabel
     * @return
     */
   int insertOrUpdate(int dtInstId,int itemId,String itemLabel);
}