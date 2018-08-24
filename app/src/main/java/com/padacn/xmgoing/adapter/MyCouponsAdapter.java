package com.padacn.xmgoing.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.MyCouponsBean;
import com.padacn.xmgoing.view.CornerTextView;

import java.util.List;

/**
 * Created by Administrator on 2018/6/28 0028.
 */


public class MyCouponsAdapter extends BaseQuickAdapter<MyCouponsBean.DataBean, BaseViewHolder> {

    private int aVoid;

    public MyCouponsAdapter(int layoutResId, @Nullable List<MyCouponsBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCouponsBean.DataBean item) {

        RelativeLayout relativeLayout = helper.getView(R.id.coupons_center_bg);
        switch (item.getCouponType()) {
            case 1:
                if (aVoid == 0) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_business_true);
                } else if (aVoid == 1) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_business_false);
                }
                helper.setText(R.id.coupons_typeTitle, item.getSellerName() + "(除特殊商品外)");

                break;
            case 2:
                if (aVoid == 0) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_all_true);
                } else if (aVoid == 1) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_all_false);
                }
                helper.setText(R.id.coupons_typeTitle, "全平台" + "(除特殊商品外)");
                break;
            case 3:
                if (aVoid == 0) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_myself_true);
                } else if (aVoid == 1) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_myself_false);
                }
                helper.setText(R.id.coupons_typeTitle, "小书童俱乐部" + "(除特殊商品外)");
                break;
        }
        CornerTextView cornerTextView = helper.getView(R.id.coupons_center_city);
        cornerTextView.setText(item.getCityName());
        cornerTextView.setBackColor(Color.parseColor("#999999"));
        helper.setText(R.id.coupons_term, "满" + item.getTerm() + "使用");
        helper.setText(R.id.coupons_reduction, String.valueOf(item.getReduction()));

        helper.addOnClickListener(R.id.coupons_sure);
    }

    public void setIsVoid(int aVoid) {
        this.aVoid = aVoid;
    }
}