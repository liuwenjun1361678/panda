package com.padacn.xmgoing.model;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class CaladerDataBean {
    private String currDate;

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public String getCurrDataString() {
        return currDataString;
    }

    public void setCurrDataString(String currDataString) {
        this.currDataString = currDataString;
    }

    private String currDataString;

    private boolean isClick;


    private double currPrice;
    private int currNum;
    private int currTicketCalenderId;

    public int getCurrTicketCalenderId() {
        return currTicketCalenderId;
    }

    public void setCurrTicketCalenderId(int currTicketCalenderId) {
        this.currTicketCalenderId = currTicketCalenderId;
    }

    public double getCurrPrice() {
        return currPrice;
    }

    public void setCurrPrice(double currPrice) {
        this.currPrice = currPrice;
    }

    public int getCurrNum() {
        return currNum;
    }

    public void setCurrNum(int currNum) {
        this.currNum = currNum;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
