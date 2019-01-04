package com.annotation.service;

import com.annotation.model.DtdItemLabel;
import com.annotation.model.Label;
import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/19.
 */
public interface ILabelService {

    /**
     * 根据任务ID查询标签
     * @param taskid
     * @return
     */
    List<Label> queryLabelByTaskId(int taskid);

    /**
     * 根据文件ID查询instance对应的label
     * @param docId
     * @return
     */
    List<Label> queryInstanceLabelByDocId(int docId);

    /**
     * 根据文件ID查询item1对应的label
     * @param docId
     * @return
     */
    List<Label> queryItem1LabelByDocId(int docId);

    /**
     * 根据文件ID查询item2对应的label
     * @param docId
     * @return
     */
    List<Label> queryItem2LabelByDocId(int docId);


}
