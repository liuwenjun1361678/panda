package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.model.PreferentialBean;

/**
 * Created by 46009 on 2018/4/27.
 */

public class ExCouponsAdapter extends BaseQuickAdapter<PreferentialBean.DataBean.CouponsBean, BaseViewHolder> {


    public ExCouponsAdapter(int layoutResId, @Nullable List<PreferentialBean.DataBean.CouponsBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, PreferentialBean.DataBean.CouponsBean item) {
        RelativeLayout relativeLayout = helper.getView(R.id.item_ex_coupons_parent);
        switch (item.getCouponType()) {
            case 1:
                relativeLayout.setBackgroundResource(R.mipmap.ex_item_red_bg);
                helper.setText(R.id.sellerName, item.getSellerName());
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.mipmap.ex_item_yellow_bg);
                helper.setText(R.id.sellerName, item.getTypeName());
                break;
            case 3:
                relativeLayout.setBackgroundResource(R.mipmap.ex_item_orange_bg);
                helper.setText(R.id.sellerName, item.getTypeName());
                break;
        }

        helper.setText(R.id.item_ex_coupons_price, String.valueOf(item.getReduction()));
        helper.setText(R.id.item_ex_coupons_content, "满" + String.valueOf(item.getTerm()) + "可用");

    }

}
