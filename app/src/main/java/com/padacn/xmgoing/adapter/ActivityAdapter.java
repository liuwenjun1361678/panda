package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.ActivityBean;
import com.padacn.xmgoing.model.BusinessDetailsBean;
import com.padacn.xmgoing.util.CommonUtil;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class ActivityAdapter extends BaseQuickAdapter<ActivityBean.DataBean.ListBean, BaseViewHolder> {

    public ActivityAdapter(int layoutResId, @Nullable List<ActivityBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ActivityBean.DataBean.ListBean item) {
        ImageView imageView = helper.getView(R.id.item_activity_image);
        CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), imageView);
        helper.setText(R.id.item_activity_price, String.valueOf(item.getPrice()));
        helper.setText(R.id.item_activity_contant, String.valueOf(item.getPName()));

        helper.setText(R.id.item_activity_num, String.valueOf(item.getSaleNum()) + "人购买");
    }
}
