package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.StringUtil;

/**
 * Created by 46009 on 2018/4/27.
 */

public class ListActivityAdapter extends BaseQuickAdapter<HomeBean.DataBean.RecommonProductBean, BaseViewHolder> {


    public ListActivityAdapter(int layoutResId, @Nullable List<HomeBean.DataBean.RecommonProductBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeBean.DataBean.RecommonProductBean item) {
        ImageView imageView = helper.getView(R.id.recommonProductTop_image);
        CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), imageView);
        helper.setText(R.id.recommonProductTop_buy_title, item.getPName());
        helper.setText(R.id.recommonProductTop_buy_nub, item.getSaleNum() + "人购买");
        if (item.isReference()) {
            helper.setText(R.id.recommonProductTop_buy_price, StringUtil.replaceString(String.valueOf(item.getReferencePrice())));
        } else {
            helper.setText(R.id.recommonProductTop_buy_price, StringUtil.replaceString(String.valueOf(item.getPrice())));
        }

    }
}
