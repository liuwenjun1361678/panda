package com.padacn.xmgoing.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.model.OrderDetailsBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.vondear.rxtools.RxDataTool;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class OrderDetailsItemAdapter extends BaseQuickAdapter<OrderDetailsBean.DataBean.ListBeanXX.ListBeanX, BaseViewHolder> {

    private OrderDetailsItemPeopleAdapter orderDetailsItemPeopleAdapter;

    public OrderDetailsItemAdapter(int layoutResId, @Nullable List<OrderDetailsBean.DataBean.ListBeanXX.ListBeanX> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, OrderDetailsBean.DataBean.ListBeanXX.ListBeanX item) {
        ImageView imageView = helper.getView(R.id.item_item_order_details_one_image);
        CommonUtil.loadImage(mContext, item.getProPic(), imageView);
        helper.setText(R.id.item_item_order_details_one_title, item.getProName());
        helper.setText(R.id.item_item_order_details_one_tiketype, "套餐类型：" + item.getTypeName());
        if (item.isFlashUse()) {
            helper.setText(R.id.item_item_order_details_one_date, "出行日期：" + item.getPeriodOfTime());
        } else {
            helper.setText(R.id.item_item_order_details_one_date, "出行日期：" + item.getUseDay());
        }
        helper.setText(R.id.item_item_order_details_price, mContext.getResources().getString(R.string.moneny_string) + item.getPrice());
        helper.setText(R.id.item_item_order_details_num, "×" + item.getProCount());

        //出行人
        RelativeLayout travleRl = helper.getView(R.id.item_item_order_details_travle_rl);
        if (item.getList().size() == 0) {
            travleRl.setVisibility(View.GONE);
        }
        RecyclerView recyclerView = helper.getView(R.id.item_item_order_details_ry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        orderDetailsItemPeopleAdapter = new OrderDetailsItemPeopleAdapter(R.layout.item_item_order_travle_people, item.getList());
        recyclerView.setAdapter(orderDetailsItemPeopleAdapter);


        RelativeLayout bottomRl = helper.getView(R.id.order_details_bottom_rl);
        bottomRl.setVisibility(View.GONE);
        TextView right = helper.getView(R.id.item_bottom_right);

        switch (item.getStatu()) {
            case Constans.NOPAY://待付款
                break;
            case Constans.UNUSE://待使用
                bottomRl.setVisibility(View.VISIBLE);
                right.setText("去核销");
                right.setTextColor(Color.parseColor("#ff3d39"));
                right.setBackgroundResource(R.drawable.shape_order_red);
                break;
            case Constans.REJECTED://退货
                break;
            case Constans.EXPIRED://过期

                break;
            case Constans.NOCOMMENT://待评价
                bottomRl.setVisibility(View.VISIBLE);
                right.setText("去评论");
                right.setTextColor(Color.parseColor("#ff3d39"));
                right.setBackgroundResource(R.drawable.shape_order_red);
                break;
            case Constans.FINISH://已完成
                break;
            case Constans.REJECTCUCCESS://退款成功
                break;
        }
        helper.addOnClickListener(R.id.item_item_order_details_travle_rl);
        helper.addOnClickListener(R.id.item_bottom_right);
        helper.addOnClickListener(R.id.item_item_order_details_one_ll);
    }


}
