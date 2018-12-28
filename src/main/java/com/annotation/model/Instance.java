package com.annotation.model;

public class Instance {
    private Integer insid;

    private String insindex;

    private String insstatus;

    private String inscomptime;

    private Integer documentid;

    private Integer labelid;

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

    public Integer getLabelid() {
        return labelid;
    }

    public void setLabelid(Integer labelid) {
        this.labelid = labelid;
    }
}