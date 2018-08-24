package com.padacn.xmgoing.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.GoodSureBean;
import com.padacn.xmgoing.model.UserCoupon;
import com.padacn.xmgoing.view.CornerTextView;
import com.padacn.xmgoing.widget.SlantedTextView;

import java.util.List;

/**
 * Created by Administrator on 2018/6/10 0010.
 */

public class CanUseAdapter extends BaseQuickAdapter<UserCoupon.DataBean, BaseViewHolder> {

    private int curroPsition = -1;

    public CanUseAdapter(int layoutResId, @Nullable List<UserCoupon.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCoupon.DataBean item) {
        RelativeLayout relativeLayout = helper.getView(R.id.coupons_center_bg);

        Log.e(TAG, "convert: 33333333333333" + item.isSelect());
//        setBackground();
        switch (item.getCouponType()) {
            case 1:
                if (item.isSelect()) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_business_true);
                } else {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_business_false);
                }
                helper.setText(R.id.coupons_typeTitle, item.getSellerName() + "(除特殊商品外)");
                break;
            case 2:
                if (item.isSelect()) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_all_true);
                } else {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_all_false);
                }
                helper.setText(R.id.coupons_typeTitle,  "全平台(除特殊商品外)");
                break;
            case 3:
                if (item.isSelect()) {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_myself_true);
                } else {
                    relativeLayout.setBackgroundResource(R.mipmap.coupons_center_myself_false);
                }
                helper.setText(R.id.coupons_typeTitle,  "小书童俱乐部(除特殊商品外)");
                break;
        }

        SlantedTextView cornerTextView = helper.getView(R.id.coupons_center_city);
        cornerTextView.setText(item.getCityName());
        if (item.isSelect()) {
            cornerTextView.setSlantedBackgroundColor(Color.parseColor("#d20102"));
        } else {
            cornerTextView.setSlantedBackgroundColor(R.color.shadow25);
        }

        helper.setText(R.id.coupons_term, "满" + item.getTerm() + "使用");
        helper.setText(R.id.coupons_reduction, String.valueOf(item.getReduction()));
        CheckBox checkBox = helper.getView(R.id.item_chlid_check);
        //初始化可点击状态
        if (item.isUserSelect()) {
            checkBox.setBackgroundResource(R.drawable.round_check_active);
        } else {
            checkBox.setBackgroundResource(R.drawable.round_check_selected);
        }


    }

    private void setBackground() {
    }

    //当前用户选择的位置
    public void setCurrSelectPosition(int position) {
        this.curroPsition = position;
        notifyDataSetChanged();
    }
}
