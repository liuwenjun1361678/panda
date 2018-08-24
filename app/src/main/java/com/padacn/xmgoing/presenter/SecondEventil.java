package com.padacn.xmgoing.presenter;

/**
 * 筛选框当前数据
 * Created by Administrator on 2018/5/28 0028.
 */

public class SecondEventil {

    //筛选字段
    private String filter;
    private String filterId;
    private int position;
    private boolean isFilter=false;//是否选择筛选

    public SecondEventil() {
    }

    public SecondEventil(boolean isFilter) {
        this.isFilter = isFilter;
    }
    public boolean getIsFilter() {
        return this.isFilter;
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
