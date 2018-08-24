package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.CouponsCenterBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.view.CornerTextView;

/**
 * Created by 46009 on 2018/4/27.
 */

public class ExCouponsCenterAdapter extends BaseQuickAdapter<CouponsCenterBean.DataBean, BaseViewHolder> {


    public ExCouponsCenterAdapter(int layoutResId, @Nullable List<CouponsCenterBean.DataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CouponsCenterBean.DataBean item) {
        RelativeLayout relativeLayout = helper.getView(R.id.coupons_center_bg);
        switch (item.getCouponType()) {
            case 1:
                relativeLayout.setBackgroundResource(R.mipmap.coupons_business);
                helper.setText(R.id.coupons_typeTitle, item.getSellerName() + "(除特殊商品外)");
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.mipmap.coupons_all);
                helper.setText(R.id.coupons_typeTitle, "全平台(除特殊商品外)");
                break;
            case 3:
                relativeLayout.setBackgroundResource(R.mipmap.coupons_myself);
                helper.setText(R.id.coupons_typeTitle, "小书童俱乐部(除特殊商品外)");
                break;
        }
        CornerTextView cornerTextView = helper.getView(R.id.coupons_center_city);
        cornerTextView.setText(item.getCityName());

        helper.setText(R.id.coupons_typeName, item.getTypeName());
        helper.setText(R.id.coupons_term, "满" + item.getTerm() + "使用");
        helper.setText(R.id.coupons_reduction, String.valueOf(item.getReduction()));


        helper.addOnClickListener(R.id.coupons_sure);
    }
}
