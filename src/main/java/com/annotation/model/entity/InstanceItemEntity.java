package com.annotation.model.entity;

import com.annotation.model.Item;

import java.util.List;

/**
 * Created by twinkleStar on 2018/12/29.
 */
public class InstanceItemEntity {

    private Integer insid;

    private String insindex;

    private String insstatus;

    private String inscomptime;

    private Integer documentid;

    private Integer labelid;
    private String labelname;


    private List<Item> itemList;


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

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }
    public String getLabelname() {
        return labelname;
    }
    public List<Item> getItemList(){
        return itemList;
    }
    public void setItemList(List<Item> itemList){
        this.itemList = itemList;
    }
}
