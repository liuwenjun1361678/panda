package com.padacn.xmgoing.presenter;

/**
 * 當前選擇日期的下标
 * Created by Administrator on 2018/5/28 0028.
 */

public class MealEventil {
    //用户点击月份的下标
    private int position;

    //用户选择了具體日期的月份下标
    private int userMonthPosition;

    private int lastPosition;


    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public MealEventil(int position, int userMonthPosition, int lastPosition) {
        this.position = position;
        this.userMonthPosition = userMonthPosition;
        this.lastPosition = lastPosition;
    }

    public int getUserMonthPosition() {
        return userMonthPosition;
    }

    public void setUserMonthPosition(int userMonthPosition) {
        this.userMonthPosition = userMonthPosition;
    }

    public int getPosition() {
        return this.position;
    }
}
