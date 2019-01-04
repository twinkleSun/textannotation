package com.annotation.model;

public class DtdItemLabel {


    private Integer dtInstId;

    private String labeltype;

    private Integer labelId;




    public Integer getDtInstId() {
        return dtInstId;
    }

    public void setDtInstId(Integer dtInstId) {
        this.dtInstId = dtInstId;
    }

    public String getLabeltype() {
        return labeltype;
    }

    public void setLabeltype(String labeltype) {
        this.labeltype = labeltype == null ? null : labeltype.trim();
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }
}