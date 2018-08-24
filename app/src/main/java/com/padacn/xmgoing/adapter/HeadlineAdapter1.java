package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.model.HomeBean;
import com.padacn.xmgoing.model.HomeSecondBean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/27 0027.
 */

public class HeadlineAdapter1 extends BaseQuickAdapter<HomeBean.DataBean.TopLineBean, BaseViewHolder> {


    public HeadlineAdapter1(int layoutResId, @Nullable List<HomeBean.DataBean.TopLineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.DataBean.TopLineBean item) {

    }
}
