package com.annotation.service;

/**
 * Created by twinkleStar on 2019/2/6.
 */
public interface IDParagraphService {

    int addDParagraph(int dTaskId,int docId,int paraId);

    int updateStatusByDocId(int userId,int docId,int taskId);

    int updateStatus(int userId,int docId,int taskId,int paraId);
}
