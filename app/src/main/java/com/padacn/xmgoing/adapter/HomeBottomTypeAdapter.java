package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeBean;

/**
 * Created by 46009 on 2018/4/27.
 */

public class HomeBottomTypeAdapter extends BaseQuickAdapter<HomeBean.DataBean.ListProductBean.LoginsBeanXX, BaseViewHolder> {


    public HomeBottomTypeAdapter(int layoutResId, @Nullable List<HomeBean.DataBean.ListProductBean.LoginsBeanXX> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, HomeBean.DataBean.ListProductBean.LoginsBeanXX item) {

//        helper.addOnClickListener(R.id.item_common_travel_people_del);
        helper.setText(R.id.item_home_bottom_type_text, item.getLabelName());
    }

}
