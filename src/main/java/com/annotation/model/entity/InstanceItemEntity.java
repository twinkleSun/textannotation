package com.annotation.model.entity;

import com.annotation.model.Item;
import com.annotation.model.Label;

import java.util.List;
import java.util.Map;

/**
 * Created by twinkleStar on 2018/12/29.
 */
public class InstanceItemEntity {

    private Integer insid;
    private String insindex;
    private String insstatus;
    private String inscomptime;
    private Integer documentid;
    private Integer labelnum;
    private List<Item> itemList;
    private List<Map<String,Object>> alreadyDone;
//    private List<Map<String,Object>> alreadyItem1;
//    private List<Map<String,Object>> alreadyItem2;

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

    public Integer getLabelnum() {
        return labelnum;
    }
    public void setLabelnum(Integer labelnum) {
        this.labelnum = labelnum;
    }


    public List<Item> getItemList(){
        return itemList;
    }
    public void setItemList(List<Item> itemList){
        this.itemList = itemList;
    }

    public List<Map<String,Object>> getAlreadyDone(){
        return alreadyDone;
    }
    public void setAlreadyDone(List<Map<String,Object>> alreadyDone){
        this.alreadyDone = alreadyDone;
    }

//    public List<Map<String,Object>> getAlreadyItem1(){
//        return alreadyItem1;
//    }
//    public void setAlreadyItem1(List<Map<String,Object>> alreadyItem1){
//        this.alreadyItem1 = alreadyItem1;
//    }
//
//    public List<Map<String,Object>> getAlreadyItem2(){
//        return alreadyItem2;
//    }
//    public void setAlreadyItem2(List<Map<String,Object>> alreadyItem2){
//        this.alreadyItem2 = alreadyItem2;
//    }

}
