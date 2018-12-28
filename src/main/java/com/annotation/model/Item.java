package com.annotation.model;

public class Item {
    private Integer itid;

    private String itemcontent;

    private String itemindex;

    private Integer instanceid;

    private Integer labelid;

    public Integer getItid() {
        return itid;
    }

    public void setItid(Integer itid) {
        this.itid = itid;
    }

    public String getItemcontent() {
        return itemcontent;
    }

    public void setItemcontent(String itemcontent) {
        this.itemcontent = itemcontent == null ? null : itemcontent.trim();
    }

    public String getItemindex() {
        return itemindex;
    }

    public void setItemindex(String itemindex) {
        this.itemindex = itemindex == null ? null : itemindex.trim();
    }

    public Integer getInstanceid() {
        return instanceid;
    }

    public void setInstanceid(Integer instanceid) {
        this.instanceid = instanceid;
    }

    public Integer getLabelid() {
        return labelid;
    }

    public void setLabelid(Integer labelid) {
        this.labelid = labelid;
    }
}