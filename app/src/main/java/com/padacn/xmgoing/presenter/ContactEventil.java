package com.padacn.xmgoing.presenter;

/**
 * 筛选框当前数据
 * Created by Administrator on 2018/5/28 0028.
 */

public class ContactEventil {

    //當前選擇的商品id
    private String pid;
    private int position;
    private int contact;

    public ContactEventil(String pid, int position) {
        this.pid = pid;
        this.position = position;
    }

    public String getPid() {
        return this.pid;
    }

    public int getPosition() {
        return this.position;
    }
}
