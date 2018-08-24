package com.padacn.xmgoing.util;

/**
 * Created by Administrator on 2018/5/18 0018.
 */

public class HotSearchEven {
    private String msg;
    //1、商品页面，2、游记页面
    private String type;


    public HotSearchEven(String msg, String type) {
        this.msg = msg;
        this.type = type;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getType() {
        return this.type;
    }
}
