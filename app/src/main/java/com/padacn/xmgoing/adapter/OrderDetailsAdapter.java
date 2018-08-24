package com.padacn.xmgoing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.CommentActivity;
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.activity.OrderDetailsActivity;
import com.padacn.xmgoing.activity.WriteOffActivity;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.model.OrderDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class OrderDetailsAdapter extends BaseQuickAdapter<OrderDetailsBean.DataBean.ListBeanXX, BaseViewHolder> {

    private String num;
    private List<OrderDetailsBean.DataBean.ListBeanXX.ListBeanX> listBeanXES;

    private OrderDetailsItemAdapter orderDetailsItemAdapter;
    private Context context;

    public OrderDetailsAdapter(int layoutResId, @Nullable List<OrderDetailsBean.DataBean.ListBeanXX> data) {
        super(layoutResId, data);
        this.context = mContext;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final OrderDetailsBean.DataBean.ListBeanXX item) {
  /*      listBeanXES = new ArrayList<>();
        listBeanXES.clear();
        listBeanXES.addAll(item.getList());*/
        helper.setText(R.id.item_order_sellName, item.getName());
//        helper.setText(R.id.item_order_details_total_goods, "共"  + "商品");
//        helper.setText(R.id.item_order_details_total_price, String.valueOf(item.getPrice()));
        RecyclerView recyclerView = helper.getView(R.id.item_order_details_ry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        orderDetailsItemAdapter = new OrderDetailsItemAdapter(R.layout.item_item_order_details, item.getList());
        recyclerView.setAdapter(orderDetailsItemAdapter);
        orderDetailsItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_item_order_details_one_ll:
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", String.valueOf(item.getList().get(position).getProId()));
                        bundle.putBoolean("panic", false);
                        BaseActivity.navigate(mContext, GoodsDetailsActivity.class, bundle);
                        break;

                    case R.id.item_item_order_details_travle_rl:


                        break;

                    case R.id.item_bottom_right:
                        rightClick(item, position);
                        break;
                }

            }
        });
        helper.addOnClickListener(R.id.order_details_buniss);
    }

    //右边按钮点击事件
    private void rightClick(OrderDetailsBean.DataBean.ListBeanXX item, int position) {
        Bundle bundle;
        switch (item.getList().get(position).getStatu()) {
            case Constans.NOPAY://代付款
                break;
            case Constans.UNUSE://待使用
                bundle = new Bundle();
                bundle.putSerializable("data", item.getList().get(position));
              /*  bundle.putString("sellerName", String.valueOf(item.getList().get(position).getSellerName()));
                bundle.putString("typeName", String.valueOf(item.getList().get(position).getTypeName()));
                bundle.putString("excursion", item.getList().get(position).getExcursion());//出行人數
                bundle.putString("useDay", item.getList().get(position).getUseDay());//出行日期
                bundle.putString("useDay", item.getList().get(position).getVerification());//核销码
                bundle.putString("sn", String.valueOf(item.getList().get(position).getSn()));//订单号
                bundle.putString("createTime", String.valueOf(item.getList().get(position).getCreateTime()));//下單日期
                bundle.putString("price", String.valueOf(item.getList().get(position).getPrice()));
                bundle.putString("proName", item.getList().get(position).getp());*/
                BaseActivity.navigate(mContext, WriteOffActivity.class, bundle);
                break;
            case Constans.REJECTED://退货
                break;
            case Constans.EXPIRED://过期
                break;
            case Constans.NOCOMMENT://待评价
                bundle = new Bundle();
                bundle.putString("typeName", String.valueOf(item.getList().get(position).getTypeName()));
                bundle.putString("orderProductId", String.valueOf(item.getList().get(position).getOrderProductId()));
                bundle.putString("proPic", item.getList().get(position).getProPic());
                bundle.putString("proName", item.getList().get(position).getProName());
                bundle.putString("useDay", item.getList().get(position).getUseDay());
                bundle.putString("excursion", item.getList().get(position).getExcursion());
                BaseActivity.navigate(mContext, CommentActivity.class, bundle);
                break;
            case Constans.FINISH://已完成
                break;
            case Constans.REJECTCUCCESS://退款成功
                break;
        }
    }


    public void setGoodsNum(String num) {
        this.num = num;
    }

}
