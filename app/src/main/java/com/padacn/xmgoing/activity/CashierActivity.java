package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.AutoBean;
import com.padacn.xmgoing.util.AppManager;
import com.padacn.xmgoing.util.EvenPayPriceUtil;
import com.padacn.xmgoing.util.even.EventBusUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.CustomToolBar;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.module.alipay.PayResult;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class CashierActivity extends BaseActivity {
    //    public static CashierActivity cashierActivity;
    @BindView(R.id.register_bar)
    CustomToolBar registerBar;
    @BindView(R.id.zfb_rl)
    RelativeLayout zfbRl;
    @BindView(R.id.weixin_rl)
    RelativeLayout weixinRl;
    @BindView(R.id.go_payment_rl)
    RelativeLayout goPaymentRl;
    @BindView(R.id.item_chlid_weixin_check)
    CheckBox itemChlidWeixinCheck;
    @BindView(R.id.item_chlid_check)
    CheckBox itemChlidCheck;
    @BindView(R.id.userPayPrice_text)
    TextView userPayPriceText;


    //當前支付方式1、支付寶2、微信
    private int payType = 1;
    private boolean isZhibao;
    private boolean isWeiXin;
    private String userPayPrice;
    private String orderId;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI iwxapi; //微信支付api

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    PayResult payResult = new PayResult((Map) msg.obj);
                    switch (payResult.getResultStatus()) {
                        case "9000":
                            RxToast.showToast("支付成功");
                            Bundle bundle = new Bundle();
                            BaseActivity.navigate(CashierActivity.this, PaySuccessActivity.class);
                            break;
                        case "8000":
                            RxToast.showToast("正在处理中");
                            break;
                        case "4000":
                            RxToast.showToast("订单支付失败");

                            break;
                     /*   case "5000":
                            GMToastUtil.showToast("重复请求");
                            break;
                        case "6001":
                            GMToastUtil.showToast("已取消支付");
                            break;
                        case "6002":
                            GMToastUtil.showToast("网络连接出错");
                            break;
                        case "6004":
                            GMToastUtil.showToast("正在处理中");
                            break;*/
                        default:
                            RxToast.showToast("支付失败");
                            break;
                    }
                    break;
            }

        }

    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_cashier;
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .navigationBarColor(R.color.home_top_text_color)
                .keyboardEnable(true)
                .init();
    }

    @Override
    protected void initView() {
        super.initView();
//        cashierActivity = this;
//        AppManager.getInstance().finishActivity(SelectActivity.instanceSele);
//        AppManager.getInstance().finishActivity(GoodsSureActivity.instanceSure);
        EventBusUtil.sendEvent(new MessageEvent(EvenCode.ClearSele));
        EventBusUtil.sendEvent(new MessageEvent(EvenCode.ClearSure));
//        EventBus.getDefault().register(this);
        registerBar.setStyle("收银台", Color.parseColor("#f8d948"));
        Bundle bundle = this.getIntent().getExtras();
        userPayPrice = bundle.getString("userPayPrice");
        orderId = bundle.getString("orderId");

        userPayPriceText.setText(userPayPrice);
        if (payType == 1) {
            itemChlidCheck.setBackgroundResource(R.drawable.round_check_active);
            itemChlidWeixinCheck.setBackgroundResource(R.drawable.round_check_selected);
        }
        EventBus.getDefault().postSticky(new EvenPayPriceUtil(userPayPrice));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EvenPayPriceUtil event) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @OnClick({R.id.zfb_rl, R.id.weixin_rl, R.id.go_payment_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zfb_rl:
                itemChlidCheck.setBackgroundResource(R.drawable.round_check_active);
                itemChlidWeixinCheck.setBackgroundResource(R.drawable.round_check_selected);
                payType = 1;
                break;
            case R.id.weixin_rl:
                if (payType == 1) {
                    itemChlidWeixinCheck.setBackgroundResource(R.drawable.round_check_active);
                    itemChlidCheck.setBackgroundResource(R.drawable.round_check_selected);
                    payType = 2;
                } else {
                    itemChlidWeixinCheck.setBackgroundResource(R.drawable.round_check_active);
                    itemChlidCheck.setBackgroundResource(R.drawable.round_check_selected);
                }
                break;
            case R.id.go_payment_rl:
                goPayment();
                break;
        }
    }

    //去支付
    private void goPayment() {
        String url = null;
        if (payType == 2) {
            url = ServiceApi.wechat;
        } else if (payType == 1) {
            url = ServiceApi.ali;
        }
        OkGo.<String>post(url)
                .tag(this)
//                .upJson(jsonString)
                .params("orderId", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                if (payType == 2) {
                                    Gson gson = new Gson();
                                    AutoBean autoBean = gson.fromJson(s, AutoBean.class);
                                    iwxapi = WXAPIFactory.createWXAPI(CashierActivity.this, null); //初始化微信api
                                    iwxapi.registerApp(autoBean.getData().getAppid()); //注册appid  appid可以在开发平台获取
                                    PayReq request = new PayReq(); //调起微信APP的对象
                                    //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                                    request.appId = autoBean.getData().getAppid();
                                    request.partnerId = autoBean.getData().getPartnerid();
                                    request.prepayId = autoBean.getData().getPrepayid();
                                    request.packageValue = "Sign=WXPay";
                                    request.nonceStr = autoBean.getData().getNoncestr();
                                    request.timeStamp = String.valueOf(autoBean.getData().getTimestamp());
                                    request.sign = autoBean.getData().getSign();
                                    iwxapi.sendReq(request);//发送调起微信的请求

                                } else if (payType == 1) {
                                    JSONObject signObject = jsonObject.getJSONObject("data");
                                    final String sign = signObject.getString("sign");
                                    Runnable payRunnable = new Runnable() {
                                        public void run() {
                                            PayTask alipay = new PayTask(CashierActivity.this);
                                            Map<String, String> resultData = alipay.payV2
                                                    (sign, true);//这里的orderInfo就是上面说的orderInfo
                                            Message msg = new Message();
                                            msg.what = SDK_PAY_FLAG;
                                            msg.obj = resultData;
                                            mHandler.sendMessage(msg);
                                        }
                                    };
                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//        cashierActivity.finish();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(MessageEvent event) {
        if (event.getCode() == EvenCode.ClearCashier) {
            finish();
        }
    }
}
