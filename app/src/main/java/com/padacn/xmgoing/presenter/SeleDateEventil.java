package com.padacn.xmgoing.presenter;

/**
 * 當前選擇日期
 * Created by Administrator on 2018/5/28 0028.
 */

public class SeleDateEventil {

    private String date;
    private double datePrice;
    private int ticketCalenderId;
    private int num;
    private int positionMonth;
    private int lastPosition;


    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public SeleDateEventil(String date, double datePrice, int num, int ticketCalenderId, int positionMonth, int lastPosition) {
        this.date = date;
        this.datePrice = datePrice;
        this.num = num;
        this.ticketCalenderId = ticketCalenderId;
        this.positionMonth = positionMonth;
        this.lastPosition = lastPosition;
    }

    public int getPositionMonth() {
        return positionMonth;
    }

    public void setPositionMonth(int positionMonth) {
        this.positionMonth = positionMonth;
    }

    public String getDate() {
        return this.date;
    }

    public double getDatePrice() {
        return this.datePrice;
    }

    public int getNum() {
        return this.num;
    }

    public int getId() {
        return this.ticketCalenderId;
    }
}
