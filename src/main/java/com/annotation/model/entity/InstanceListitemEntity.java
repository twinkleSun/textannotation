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

}
