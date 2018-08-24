package com.padacn.xmgoing.util;

/**
 * Created by Administrator on 2018/5/18 0018.
 */

public class EvenOrderTabUtil {
    private int positionString;
    private int position;


    public EvenOrderTabUtil(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getPositionString() {
        return this.positionString;
    }
}
