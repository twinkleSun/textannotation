package com.annotation.model;

public class Listitem {
    private Integer liid;

    private String litemcontent;

    private String listindex;

    private String litemindex;

    private String litemstatus;

    private String litemcomptime;

    private Integer instanceid;

    public Integer getLiid() {
        return liid;
    }

    public void setLiid(Integer liid) {
        this.liid = liid;
    }

    public String getLitemcontent() {
        return litemcontent;
    }

    public void setLitemcontent(String litemcontent) {
        this.litemcontent = litemcontent == null ? null : litemcontent.trim();
    }

    public String getListindex() {
        return listindex;
    }

    public void setListindex(String listindex) {
        this.listindex = listindex == null ? null : listindex.trim();
    }

    public String getLitemindex() {
        return litemindex;
    }

    public void setLitemindex(String litemindex) {
        this.litemindex = litemindex == null ? null : litemindex.trim();
    }

    public String getLitemstatus() {
        return litemstatus;
    }

    public void setLitemstatus(String litemstatus) {
        this.litemstatus = litemstatus == null ? null : litemstatus.trim();
    }

    public String getLitemcomptime() {
        return litemcomptime;
    }

    public void setLitemcomptime(String litemcomptime) {
        this.litemcomptime = litemcomptime == null ? null : litemcomptime.trim();
    }

    public Integer getInstanceid() {
        return instanceid;
    }

    public void setInstanceid(Integer instanceid) {
        this.instanceid = instanceid;
    }
}