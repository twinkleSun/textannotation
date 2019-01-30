package com.annotation.model.entity;

/**
 * Created by twinkleStar on 2019/1/15.
 */
public class MyPubTaskByDoing {

    private Integer userid;
    private String dotime;
    private String comptime;

    private String dtstatus;
    private Integer tid;
    private String username;
    private String lastLogInTime;
    private String type;





    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getDotime() {
        return dotime;
    }
    public void setDotime(String dotime) {
        this.dotime = dotime == null ? null : dotime.trim();
    }

    public String getComptime() {
        return comptime;
    }
    public void setComptime(String comptime) {
        this.comptime = comptime == null ? null : comptime.trim();
    }

    public String getDtstatus() {
        return dtstatus;
    }
    public void setDtstatus(String dtstatus) {
        this.dtstatus = dtstatus == null ? null : dtstatus.trim();
    }

    public Integer getTid(){
        return tid;
    }
    public void setTid(Integer tid){
        this.tid=tid;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }

    public String getLastLogInTime(){
        return lastLogInTime;
    }
    public void setLastLogInTime(String lastLogInTime){
        this.lastLogInTime=lastLogInTime;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type=type;
    }
}
