package com.annotation.model;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/23.
 */
public class TaskInfoEntity {

    private Integer tid;
    private String title;
    private String description;
    private String createtime;
    private String deadline;
    private String taskcomptime;
    private String taskcompstatus;
    private String otherinfo;
    private Integer userid;
    private String type;

    private static final long serialVersionUID = 1L;

    public Integer getTid() {
        return tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline == null ? null : deadline.trim();
    }

    public String getTaskcomptime() {
        return taskcomptime;
    }
    public void setTaskcomptime(String taskcomptime) {
        this.taskcomptime = taskcomptime == null ? null : taskcomptime.trim();
    }

    public String getTaskcompstatus() {
        return taskcompstatus;
    }
    public void setTaskcompstatus(String taskcompstatus) {
        this.taskcompstatus = taskcompstatus == null ? null : taskcompstatus.trim();
    }
    public String getOtherinfo() {
        return otherinfo;
    }
    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo == null ? null : otherinfo.trim();
    }

    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }


//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getClass().getSimpleName());
//        sb.append(" [");
//        sb.append("Hash = ").append(hashCode());
//        sb.append(", tid=").append(tid);
//        sb.append(", title=").append(title);
//        sb.append(", description=").append(description);
//        sb.append(", createtime=").append(createtime);
//        sb.append(", deadline=").append(deadline);
//        sb.append(", taskcomptime=").append(taskcomptime);
//        sb.append(", taskcompstatus=").append(taskcompstatus);
//        sb.append(", otherinfo=").append(otherinfo);
//        sb.append(", userid=").append(userid);
//        sb.append(", type=").append(type);
//        sb.append(", serialVersionUID=").append(serialVersionUID);
//        sb.append("]");
//        return sb.toString();
//    }


    private List<Label> labelList;
    public List<Label> getLabelList(){
        return labelList;
    }
    public void setLabelList(List<Label> labelList){
        this.labelList = labelList;
    }

    private List<Document> documentList;
    public List<Document> getDocumentList(){return documentList;}
    public void setDocumentList(List<Document> documentList){this.documentList=documentList;}
}
