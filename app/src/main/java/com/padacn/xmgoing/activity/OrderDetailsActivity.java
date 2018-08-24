package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ldf.calendar.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.OrderDetailsAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.OrderDetailsBean;
import com.padacn.xmgoing.util.CommonDialogUtil;
import com.padacn.xmgoing.util.CommonUi;
import com.padacn.xmgoing.view.CustomToolBar;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/18 0018.
 */

public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.order_details_bar)
    CustomToolBar orderDetailsBar;
    @BindView(R.id.order_stute)
    TextView orderStute;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.order_details_recyclerView)
    RecyclerView orderDetailsRecyclerView;
    @BindView(R.id.order_details_num)
    TextView orderDetailsNum;
    @BindView(R.id.order_details_reservations)
    TextView orderDetailsReservations;
    @BindView(R.id.order_details_phone)
    TextView orderDetailsPhone;
    @BindView(R.id.order_details_time)
    TextView orderDetailsTime;
    @BindView(R.id.order_details_left)
    TextView orderDetailsLeft;
    @BindView(R.id.order_details_right)
    TextView orderDetailsRight;
    @BindView(R.id.order_details_time_text)
    TextView orderDetailsTimeText;
    @BindView(R.id.order_stute_image)
    ImageView orderStuteImage;
    @BindView(R.id.item_order_details_total_goods)
    TextView itemOrderDetailsTotalGoods;
    @BindView(R.id.item_order_details_total_price)
    TextView itemOrderDetailsTotalPrice;

    private OrderDetailsAdapter orderDetailsAdapter;
    private List<OrderDetailsBean.DataBean.ListBeanXX> orderBeanList;
    private String orderId;

    private String userPayPrice;
    private String currOrderGoodsNum;
    private int currStus;

    private CountDownTimer timer;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.common_ffffff)
                .navigationBarColor(R.color.home_top_text_color)
                .keyboardEnable(true)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        super.initView();
        orderDetailsBar.setStyle("订单详情", Color.parseColor("#ffffff"));
        orderBeanList = new ArrayList<>();
        Bundle bundle = this.getIntent().getExtras();
        orderId = bundle.getString("orderId");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderDetailsRecyclerView.setLayoutManager(layoutManager);
        orderDetailsAdapter = new OrderDetailsAdapter(R.layout.item_order_details, orderBeanList);
        orderDetailsRecyclerView.setAdapter(orderDetailsAdapter);
        orderDetailsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.order_details_buniss:
                        Bundle bundle = new Bundle();
                        bundle.putString("sellerId", String.valueOf(orderBeanList.get(position).getId()));
                        BaseActivity.navigate(OrderDetailsActivity.this, BusinessDetailActivity.class, bundle);
                        break;
                }
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();

        getOrderDetails();
    }

    //获取订单详情
    private void getOrderDetails() {
        OkGo.<String>post(ServiceApi.orderDetail)
                .tag(this)
                .params("orderId", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                Gson gson = new Gson();
                                OrderDetailsBean orderDetailsBean = gson.fromJson(s, OrderDetailsBean.class);
                                orderBeanList.addAll(orderDetailsBean.getData().getList());
                                userPayPrice = String.valueOf(orderDetailsBean.getData().getPrice());
                                currStus = orderDetailsBean.getData().getStatu();
                                orderDetailsNum.setText(orderDetailsBean.getData().getSn());
                                orderDetailsReservations.setText(orderDetailsBean.getData().getBookedBy());
                                orderDetailsPhone.setText(orderDetailsBean.getData().getTel());
                                orderDetailsTime.setText(orderDetailsBean.getData().getCreateTime());
                                currOrderGoodsNum = String.valueOf(orderDetailsBean.getData().getPCount());
                                itemOrderDetailsTotalGoods.setText("共" + String.valueOf(orderDetailsBean.getData().getPCount()) + "商品");
                                itemOrderDetailsTotalPrice.setText(String.valueOf(orderDetailsBean.getData().getPrice()));

                                switch (orderDetailsBean.getData().getStatu()) {
                                    case Constans.NOPAY:
                                        orderStute.setText("等待买家付款");
                                        orderStuteImage.setBackgroundResource(R.mipmap.order_details_top_time);
                                        if (!RxDataTool.isNullString(orderDetailsBean.getData().getExpireTime())) {
                                            setNopayTime(orderDetailsBean.getData().getExpireTime());
                                        }
                                        orderDetailsLeft.setText("取消订单");
                                        orderDetailsRight.setText("去付款");
                                        orderDetailsLeft.setBackgroundResource(R.drawable.shape_order_balck);
                                        orderDetailsRight.setTextColor(Color.parseColor("#ff3d39"));
                                        orderDetailsRight.setBackgroundResource(R.drawable.shape_order_red);

                                        break;
                                    case Constans.UNUSE:
                                        orderStute.setText("等待买家出行");
                                        orderStuteImage.setBackgroundResource(R.mipmap.order_details_top_time);
                                        orderTime.setText("");
//                                        orderDetailsLeft.setText("申请退款");
//                                        orderDetailsRight.setText("去核销");
//                                        orderDetailsRight.setBackgroundResource(R.drawable.shape_order_red);
                                        break;
                                    case Constans.EXPIRED:
                                        orderStute.setText("已过期");
                                        orderStuteImage.setBackgroundResource(R.mipmap.order_details_top_time);
                                        orderDetailsRight.setText("删除订单");
                                        orderDetailsRight.setBackgroundResource(R.drawable.shape_order_balck);
                                        orderTime.setText("");


                                        break;
                                    case Constans.REJECTED:
                                        orderStute.setText("退货中");
                                        orderStuteImage.setBackgroundResource(R.mipmap.order_details_top_time);
                                        orderTime.setText("");
                                        orderDetailsRight.setText("联系客服");
                                        orderDetailsRight.setBackgroundResource(R.drawable.shape_order_balck);
                                        orderDetailsLeft.setVisibility(View.GONE);
                                        break;
                                    case Constans.NOCOMMENT:
                                        orderStute.setText("付款成功");
                                        orderStuteImage.setBackgroundResource(R.mipmap.order_details_top_success);
                                        orderDetailsRight.setVisibility(View.GONE);
                                        orderDetailsLeft.setVisibility(View.GONE);
                                        break;

                                    case Constans.FINISH:

                                        orderStute.setText("已完成");
                                        orderStuteImage.setBackgroundResource(R.mipmap.order_details_top_success);
                                        orderTime.setText("");
                                        break;
                                    case Constans.REJECTCUCCESS:
                                        orderStute.setText("退款成功");
                                        orderStuteImage.setBackgroundResource(R.mipmap.order_details_top_success);
                                        orderTime.setText("");
                                        break;
                                }
                                if (orderDetailsBean.getData().getStatu() == Constans.REJECTCUCCESS) {
                                    orderDetailsTimeText.setText("申请退款时间：");
                                } else {
                                    orderDetailsTimeText.setText("下单时间：");
                                }
                                showData();
                            } else {
                                RxToast.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //设置等待买家付款时间
    private void setNopayTime(final String time) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (Utils.getTime(time) > 0) {
            long timeLong = Utils.getTime(time);
            timer = new CountDownTimer(timeLong, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long temp = millisUntilFinished / 1000;
                    long hours = temp / 3600;
                    long minutes = (temp - (3600 * hours)) / 60;
                    long seconds = temp - (3600 * hours) - (60 * minutes);
                    orderTime.setText(hours + "小时" + minutes + "分后自动关闭");
                }

                @Override
                public void onFinish() {
                    orderStute.setText("已经过期");
                }
            }.start();
        }
    }

    //展示數據
    private void showData() {
        orderDetailsAdapter.setNewData(orderBeanList);
        orderDetailsAdapter.setGoodsNum(currOrderGoodsNum);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @OnClick({R.id.order_details_left, R.id.order_details_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_details_left:
                leftClick();
                break;
            case R.id.order_details_right:
                rightClick();
                break;
        }
    }

    private void leftClick() {
        switch (currStus) {
            case Constans.NOPAY://代付款
                deleteOrder(orderId);
                break;
        }
    }

    //右方点击事件
    private void rightClick() {
        Bundle bundle;
        switch (currStus) {
            case Constans.NOPAY://代付款
                bundle = new Bundle();
                bundle.putString("orderId", orderId);
                bundle.putString("userPayPrice", userPayPrice);
                BaseActivity.navigate(OrderDetailsActivity.this, CashierActivity.class, bundle);
                break;
            case Constans.UNUSE://待使用

                break;
            case Constans.REJECTED://退货
                CommonUi.diallPhone(Constans.PhoneNumber, OrderDetailsActivity.this);
                break;
            case Constans.EXPIRED://过期
                deleteOrder(orderId);
                break;
            case Constans.NOCOMMENT://待评价
                break;
            case Constans.FINISH://已完成
                deleteOrder(orderId);
                break;
            case Constans.REJECTCUCCESS://退款成功
                deleteOrder(orderId);
                break;
        }


    }

    //刪除訂單
    private void deleteOrder(String orderId) {
        CommonDialogUtil.showDialog(OrderDetailsActivity.this);
        OkGo.<String>post(ServiceApi.deleteOrders)
                .tag(this)
                .params("orderId", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        CommonDialogUtil.hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                RxToast.success(msg);
                                finish();
                            } else {
                                RxToast.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonDialogUtil.hideDialog();
                    }
                });
    }

}
