package com.annotation.model.entity;

import com.annotation.model.Listitem;

import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/29.
 */
public class InstanceListitemEntity {

    private Integer insid;
    private String insindex;
    private String insstatus;
    private String inscomptime;
    private Integer documentid;

    private String dotime;
    private String comptime;
    private String dtstatus;

    private List<Listitem> listitems;
    private List<Map<String,Object>> alreadyDone;

    public Integer getInsid() {
        return insid;
    }
    public void setInsid(Integer insid) {
        this.insid = insid;
    }

    public String getInsindex() {
        return insindex;
    }
    public void setInsindex(String insindex) {
        this.insindex = insindex == null ? null : insindex.trim();
    }

    public String getInsstatus() {
        return insstatus;
    }
    public void setInsstatus(String insstatus) {
        this.insstatus = insstatus == null ? null : insstatus.trim();
    }

    public String getInscomptime() {
        return inscomptime;
    }
    public void setInscomptime(String inscomptime) {
        this.inscomptime = inscomptime == null ? null : inscomptime.trim();
    }

    public Integer getDocumentid() {
        return documentid;
    }
    public void setDocumentid(Integer documentid) {
        this.documentid = documentid;
    }

    public List<Listitem> getListitems(){
        return listitems;
    }
    public void setListitems(List<Listitem> listitems){
        this.listitems = listitems;
    }

    public List<Map<String,Object>> getAlreadyDone(){
        return alreadyDone;
    }
    public void setAlreadyDone(List<Map<String,Object>> alreadyDone){
        this.alreadyDone = alreadyDone;
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

}
