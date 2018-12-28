package com.annotation.model;

public class DtdItemLabel {
    private Integer dtdItlid;

    private Integer dtInstId;

    private Integer itemId;

    private Integer labelId;

    private String itemLabel;

    public Integer getDtdItlid() {
        return dtdItlid;
    }

    public void setDtdItlid(Integer dtdItlid) {
        this.dtdItlid = dtdItlid;
    }

    public Integer getDtInstId() {
        return dtInstId;
    }

    public void setDtInstId(Integer dtInstId) {
        this.dtInstId = dtInstId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel == null ? null : itemLabel.trim();
    }
}