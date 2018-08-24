package com.padacn.xmgoing.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class GoodSureBean implements Serializable {

    private String pId;
    private String picPath;
    private String pName;
    private String tiketName;
    private String travleTime;
    private String num;
    private String type;
    private String totalPerson;
    private String sellerId;
    private String price;
    private String totalPrice;
    private String tiketId;
    private String calenderId;
    private boolean useIdcard;
    private String travelNum;

    public boolean isUseIdcard() {
        return useIdcard;
    }

    public void setUseIdcard(boolean useIdcard) {
        this.useIdcard = useIdcard;
    }

    public String getTravelNum() {
        return travelNum;
    }

    public void setTravelNum(String travelNum) {
        this.travelNum = travelNum;
    }

    public String getCalenderId() {
        return calenderId;
    }

    public void setCalenderId(String calenderId) {
        this.calenderId = calenderId;
    }

    public String getTiketId() {
        return tiketId;
    }

    public void setTiketId(String tiketId) {
        this.tiketId = tiketId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getTiketName() {
        return tiketName;
    }

    public void setTiketName(String tiketName) {
        this.tiketName = tiketName;
    }

    public String getTravleTime() {
        return travleTime;
    }

    public void setTravleTime(String travleTime) {
        this.travleTime = travleTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(String totalPerson) {
        this.totalPerson = totalPerson;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
