package com.padacn.xmgoing.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.OrderCommentBean;
import com.padacn.xmgoing.model.UserCoupon;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.widget.SlantedTextView;
import com.vondear.rxtools.RxDataTool;

import java.util.List;

/**
 * Created by Administrator on 2018/6/10 0010.
 */

public class OrderCommentItemAdapter extends BaseQuickAdapter<OrderCommentBean.DataBean, BaseViewHolder> {


    public OrderCommentItemAdapter(int layoutResId, @Nullable List<OrderCommentBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCommentBean.DataBean item) {
        if (item.isAllProductCoupon() == true) {
            helper.setText(R.id.item_business_name, "熊猫成长季");
        } else {
            helper.setText(R.id.item_business_name, item.getSellerName());
        }

        helper.setText(R.id.item_business_people_num, "共" + item.getProCount() + "件商品");
        helper.setText(R.id.item_business_price, String.valueOf(item.getPrice()));
        TextView left = helper.getView(R.id.item_business_bottom_left);
        TextView right = helper.getView(R.id.item_business_bottom_right);
        RelativeLayout item_order_rl = helper.getView(R.id.item_business_rl);
        item_order_rl.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        helper.setText(R.id.item_business_type_top, "待评论");
        item_order_rl.setVisibility(View.GONE);
        right.setText("去评论");
        right.setTextColor(Color.parseColor("#d20102"));
        right.setBackgroundResource(R.drawable.shape_order_red);
        left.setVisibility(View.GONE);

        LinearLayout linearLayout = helper.getView(R.id.item_business_one_ll);
        RecyclerView recyclerView = helper.getView(R.id.item_order_ry1);
        linearLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        linearLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        helper.setText(R.id.item_business_one_title, item.getProName());
        helper.setText(R.id.item_business_one_tiketype, "套餐类型：" + item.getTypeName());

        if (item.isFlashUse()) {
            helper.setText(R.id.item_business_one_date, "出行日期：" + item.getPeriodOfTime());
        } else {
            helper.setText(R.id.item_business_one_date, "出行日期：" + item.getUseDay());
        }
        ImageView imageView = helper.getView(R.id.item_business_one_image);
        CommonUtil.loadImage(mContext, item.getProPic(), imageView);

        helper.addOnClickListener(R.id.item_business_bottom_right);
    }
}
