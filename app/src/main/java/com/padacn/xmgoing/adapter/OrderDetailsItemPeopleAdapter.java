package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.OrderDetailsBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.vondear.rxtools.RxDataTool;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class OrderDetailsItemPeopleAdapter extends BaseQuickAdapter<OrderDetailsBean.DataBean.ListBeanXX.ListBeanX.ListBean, BaseViewHolder> {


    public OrderDetailsItemPeopleAdapter(int layoutResId, @Nullable List<OrderDetailsBean.DataBean.ListBeanXX.ListBeanX.ListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, OrderDetailsBean.DataBean.ListBeanXX.ListBeanX.ListBean item) {
        helper.setText(R.id.name, item.getName());
    }

}
