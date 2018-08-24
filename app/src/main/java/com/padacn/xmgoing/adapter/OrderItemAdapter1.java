package com.padacn.xmgoing.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.callback.OnClickListenterModel;
import com.padacn.xmgoing.callback.OnClickListenterOrder;
import com.padacn.xmgoing.model.AllOrderBean;
import com.padacn.xmgoing.model.UserCoupon;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.widget.SlantedTextView;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/10 0010.
 */

public class OrderItemAdapter1 extends BaseQuickAdapter<AllOrderBean.DataBean, BaseViewHolder> {
    private List<AllOrderBean.DataBean.ListBeanX.ListBean> listBeanList;
    private OrderAllItemAdapter orderAllItemAdapter;
    private List<String> stringList;

    public OrderItemAdapter1(int layoutResId, @Nullable List<AllOrderBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, AllOrderBean.DataBean item) {
        listBeanList = new ArrayList<>();
        stringList = new ArrayList<>();
        listBeanList.clear();
        stringList.clear();
        for (int i = 0; i < item.getList().size(); i++) {
            for (int j = 0; j < item.getList().get(i).getList().size(); j++) {
                listBeanList.add(item.getList().get(i).getList().get(j));
                stringList.add(item.getList().get(i).getList().get(j).getProPic());
            }
        }
        if (item.isAllProductCoupon() == true) {
            helper.setText(R.id.item_business_name, "熊猫成长季");
        } else {
            if (item.getList().size() > 1) {
                helper.setText(R.id.item_business_name, "熊猫成长季");
            } else {
                helper.setText(R.id.item_business_name, item.getList().get(0).getName());
            }
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
                right.setBackgroundResource(R.drawable.shape_order_balck);
                right.setText("删除订单");
                left.setVisibility(View.GONE);
                break;
            case Constans.NOCOMMENT://待评价
                helper.setText(R.id.item_business_type_top, "待评论");
                right.setText("去评论");
                right.setTextColor(Color.parseColor("#ff3d39"));
                right.setBackgroundResource(R.drawable.shape_order_red);
                left.setVisibility(View.GONE);
                break;
            case Constans.FINISH://已完成
                helper.setText(R.id.item_business_type_top, "已完成");
                right.setBackgroundResource(R.drawable.shape_order_balck);
                right.setText("删除订单");
                left.setVisibility(View.GONE);
                break;
            case Constans.REJECTCUCCESS://退款成功
                helper.setText(R.id.item_business_type_top, "退款成功");
                right.setBackgroundResource(R.drawable.shape_order_balck);
                right.setText("删除订单");
                left.setVisibility(View.GONE);
                break;
        }

        LinearLayout linearLayout = helper.getView(R.id.item_business_one_ll);
        RecyclerView recyclerView = helper.getView(R.id.item_order_ry1);
        linearLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        if (listBeanList.size() > 1) {
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            orderAllItemAdapter = new OrderAllItemAdapter(R.layout.item_item_order_all_image, stringList);
            recyclerView.setAdapter(orderAllItemAdapter);
            helper.addOnClickListener(R.id.item_order_ry1);

            orderAllItemAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    onClickListenterOrder.onItemClick(view, helper.getAdapterPosition());
                }
            });
        }
        if (listBeanList.size() == 1) {
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            helper.setText(R.id.item_business_one_title, item.getList().get(0).getList().get(0).getProName());
            helper.setText(R.id.item_business_one_tiketype, "套餐类型：" + item.getList().get(0).getList().get(0).getTypeName());
            if (item.getList().get(0).getList().get(0).isFlashUse()) {
                helper.setText(R.id.item_business_one_date, "出行日期：" + item.getList().get(0).getList().get(0).getPeriodOfTime());
            } else {
                helper.setText(R.id.item_business_one_date, "出行日期：" + item.getList().get(0).getList().get(0).getUseDay());
            }
            ImageView imageView = helper.getView(R.id.item_business_one_image);
            CommonUtil.loadImage(mContext, item.getList().get(0).getList().get(0).getProPic(), imageView);
        }
        //下方点击事件
        helper.addOnClickListener(R.id.item_business_bottom_left);
        helper.addOnClickListener(R.id.item_business_bottom_right);
    }


    // CheckBox2接口的方法
    private OnClickListenterOrder onClickListenterOrder = null;

    public void setOnClickListenterOrder(OnClickListenterOrder listener) {
        this.onClickListenterOrder = listener;
    }

}
