package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.view.CornerTextView;
import com.vondear.rxtools.RxDataTool;

import java.util.List;

/**
 * 店家优惠卷
 * Created by Administrator on 2018/5/26 0026.
 */

public class GoodsDetailCouponsAdapter extends BaseQuickAdapter<GoodsDtailsBean.SellerCouponBean, BaseViewHolder> {

    public GoodsDetailCouponsAdapter(int layoutResId, @Nullable List<GoodsDtailsBean.SellerCouponBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDtailsBean.SellerCouponBean item) {

        helper.setText(R.id.item_shop_details_coupons_reduce, "满" + item.getTerm() + "使用");
        helper.setText(R.id.item_shop_details_coupons_reducePrice, String.valueOf(item.getReduction()));

        if (RxDataTool.isNullString(item.getUseTime())) {
            helper.setText(R.id.item_shop_details_coupons_date, "不限时间使用");
        } else {
            helper.setText(R.id.item_shop_details_coupons_date, "有效期：" + item.getUseTime() + "至" + item.getVoidTime());
        }


    }

}
