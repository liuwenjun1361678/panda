package com.padacn.xmgoing.presenter;

/**
 * 筛选框当前数据
 * Created by Administrator on 2018/5/28 0028.
 */

public class Eventil {

    //筛选字段
    private String filter;
    private String filterId;
    private int position;

    public Eventil() {
    }

    public Eventil(String filter, String filterId) {
        this.filter = filter;
        this.filterId = filterId;
    }

    public String getProviceName() {
        return this.filter;
    }

    public String getFilterId() {
        return this.filterId;
    }

    public int getPosition() {
        return this.position;
    }
}
