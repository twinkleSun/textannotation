package com.annotation.model.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2019/1/7.
 */
public class ContentLabelEntity {
    private Integer cid;
    private String paracontent;
    private Integer paraindex;
    private String parastatus;
    private String paracomptime;
    private Integer documentid;

    private String dotime;
    private String comptime;
    private String dtstatus;
    private List<Map<String,Object>> alreadyDone;

    private static final long serialVersionUID = 1L;

    public Integer getCid() {
        return cid;
    }
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getParacontent() {
        return paracontent;
    }
    public void setParacontent(String paracontent) {
        this.paracontent = paracontent == null ? null : paracontent.trim();
    }

    public Integer getParaindex() {
        return paraindex;
    }
    public void setParaindex(Integer paraindex) {
        this.paraindex = paraindex;
    }

    public String getParastatus() {
        return parastatus;
    }
    public void setParastatus(String parastatus) {
        this.parastatus = parastatus == null ? null : parastatus.trim();
    }

    public String getParacomptime() {
        return paracomptime;
    }
    public void setParacomptime(String paracomptime) {
        this.paracomptime = paracomptime == null ? null : paracomptime.trim();}

    public Integer getDocumentid() {
        return documentid;
    }
    public void setDocumentid(Integer documentid) {
        this.documentid = documentid;
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
