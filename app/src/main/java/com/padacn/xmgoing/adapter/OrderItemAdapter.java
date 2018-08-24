package com.padacn.xmgoing.adapter;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.OrderDetailsActivity;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.callback.OnViewClickListener;
import com.padacn.xmgoing.model.AllOrderBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.vondear.rxtools.RxDataTool;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 46009 on 2018/5/10.
 */

public class OrderItemAdapter extends BaseMultiItemQuickAdapter<AllOrderBean.DataBean, BaseViewHolder> {

    private int curPosotin;

    private OrderAllItemAdapter orderAllItemAdapter;

    private List<String> stringList;

    private List<AllOrderBean.DataBean.ListBeanX.ListBean> listBeanList;

    public OrderItemAdapter(List<AllOrderBean.DataBean> data) {
        super(data);
        addItemType(Constans.TYPE_ORDER_ALL, R.layout.item_order_all_coupons);
        addItemType(Constans.TYPE_ORDER, R.layout.item_order_business_coupons);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllOrderBean.DataBean item) {
        listBeanList = new ArrayList<>();
        for (int i = 0; i < item.getList().size(); i++) {
            for (int j = 0; j < item.getList().get(i).getList().size(); j++) {
                listBeanList.add(item.getList().get(i).getList().get(j));
            }
        }
        if (listBeanList.size() > 1) {
            item.setRecyType(true);
        }
        if (listBeanList.size() == 1) {
            item.setRecyType(true);
        }
        if (item.getItemType() == Constans.TYPE_ORDER_ALL) {
            bindAllData(helper, item);
            Log.e(TAG, "convert: TYPE_ORDER_ALL");
        }
        if (item.getItemType() == Constans.TYPE_ORDER) {
            bindData(helper, item);
            Log.e(TAG, "convert: TYPE_ORDER");
        }
    }

    //多个商品展示
    private void bindAllData(BaseViewHolder helper, final AllOrderBean.DataBean item) {
        if (item.isAllProductCoupon() == true) {
            helper.setText(R.id.item_business_name, "熊猫成长季");
        } else {
            helper.setText(R.id.item_business_name, item.getList().get(0).getName());
        }
        helper.setText(R.id.item_business_people_num, "共" + item.getPCount() + "件商品");
        helper.setText(R.id.item_business_price, String.valueOf(item.getPrice()));
        TextView left = helper.getView(R.id.item_business_bottom_left);
        TextView right = helper.getView(R.id.item_business_bottom_right);

        RelativeLayout item_order_rl = helper.getView(R.id.item_business_rl);
        item_order_rl.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        switch (item.getStatu()) {
            case Constans.NOPAY://代付款
                helper.setText(R.id.item_business_type_top, "待付款");
                right.setText("去付款");
                left.setText("取消订单");
                right.setBackgroundResource(R.drawable.shape_order_red);
                right.setTextColor(Color.parseColor("#ff3d39"));
                left.setBackgroundResource(R.drawable.shape_order_balck);
                break;
            case Constans.UNUSE://待使用
                helper.setText(R.id.item_business_type_top, "待使用");
                right.setText("去核销");
                left.setText("申请退款");
                left.setBackgroundResource(R.drawable.shape_order_balck);
                right.setTextColor(Color.parseColor("#ff3d39"));
                right.setBackgroundResource(R.drawable.shape_order_red);
                break;
            case Constans.REJECTED://退货
                helper.setText(R.id.item_business_type_top, "退款中");
                right.setText("联系客服");
                right.setBackgroundResource(R.drawable.shape_order_balck);
                left.setVisibility(View.GONE);
                break;
            case Constans.EXPIRED://过期
                helper.setText(R.id.item_business_type_top, "已过期");
                break;
            case Constans.NOCOMMENT://待评价
                helper.setText(R.id.item_business_type_top, "待评论");
                item_order_rl.setVisibility(View.GONE);
                right.setText("去评论");
                left.setVisibility(View.GONE);
                break;
            case Constans.FINISH://已完成
                helper.setText(R.id.item_business_type_top, "已完成");
                break;
            case Constans.REJECTCUCCESS://退款成功
                helper.setText(R.id.item_business_type_top, "退款成功");
                break;
        }
        stringList = new ArrayList<>();
        for (int i = 0; i < item.getList().size(); i++) {
            for (int j = 0; j < item.getList().get(i).getList().size(); j++) {
                stringList.add(item.getList().get(i).getList().get(j).getProPic());
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = helper.getView(R.id.item_order_ry1);
        recyclerView.setLayoutManager(layoutManager);
        orderAllItemAdapter = new OrderAllItemAdapter(R.layout.item_item_order_all_image, stringList);
        recyclerView.setAdapter(orderAllItemAdapter);
        helper.addOnClickListener(R.id.item_order_ry1);

        orderAllItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", String.valueOf(item.getId()));
                BaseActivity.navigate(mContext, OrderDetailsActivity.class, bundle);
            }
        });
    }


    private OnViewClickListener onViewClickListener = null;

    public void setOnViewClickListener(OnViewClickListener listener) {
        this.onViewClickListener = listener;
    }

    //只有单个商品展示
    private void bindData(BaseViewHolder helper, AllOrderBean.DataBean item) {
        ImageView imageView = helper.getView(R.id.item_business_one_image);
        RelativeLayout item_order_rl = helper.getView(R.id.item_business_rl);//显示商品个数和价钱的rl
        helper.setText(R.id.item_business_one_title, item.getList().get(0).getList().get(0).getProName());
        helper.setText(R.id.item_business_one_tiketype, "套餐类型：" + item.getList().get(0).getList().get(0).getTypeName());

        if (!RxDataTool.isNullString(String.valueOf(item.getList().get(0).getList().get(0).getUseDay()))) {
            helper.setText(R.id.item_business_one_tiketype, "出行日期：" + item.getList().get(0).getList().get(0).getTypeName());
        }
        CommonUtil.loadImage(mContext, item.getList().get(0).getList().get(0).getProPic(), imageView);
        helper.setText(R.id.item_business_name, item.getList().get(0).getName());
        helper.setText(R.id.item_business_price, "共" + item.getPCount() + "件商品");
        helper.setText(R.id.item_business_price, String.valueOf(item.getPrice()));
        TextView left = helper.getView(R.id.item_business_bottom_left);
        TextView right = helper.getView(R.id.item_business_bottom_right);

        right.setTextColor(Color.parseColor("#111111"));
        item_order_rl.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        switch (item.getStatu()) {
            case Constans.NOPAY://代付款
                helper.setText(R.id.item_business_type_top, "待付款");
                right.setText("去付款");
                left.setText("取消订单");
                right.setTextColor(Color.parseColor("#ff3d39"));
                right.setBackgroundResource(R.drawable.shape_order_red);
                left.setBackgroundResource(R.drawable.shape_order_balck);
                break;
            case Constans.UNUSE://待使用
                helper.setText(R.id.item_business_type_top, "待使用");
                right.setText("去核销");
                left.setText("申请退款");
                right.setTextColor(Color.parseColor("#ff3d39"));
                left.setBackgroundResource(R.drawable.shape_order_balck);
                right.setBackgroundResource(R.drawable.shape_order_red);
                break;
            case Constans.REJECTED://退货
                helper.setText(R.id.item_business_type_top, "退款中");
                right.setText("联系客服");
                left.setVisibility(View.GONE);
                right.setBackgroundResource(R.drawable.shape_order_balck);
                break;
            case Constans.EXPIRED://过期
                helper.setText(R.id.item_business_type_top, "已过期");
                break;
            case Constans.NOCOMMENT://待评价
                helper.setText(R.id.item_business_type_top, "待评论");
                item_order_rl.setVisibility(View.GONE);
                right.setText("去评论");
                left.setVisibility(View.GONE);
                break;
            case Constans.FINISH://已完成
                helper.setText(R.id.item_business_type_top, "已完成");
                break;
            case Constans.REJECTCUCCESS://退款成功
                helper.setText(R.id.item_business_type_top, "退款成功");
                break;
        }
    }
}
