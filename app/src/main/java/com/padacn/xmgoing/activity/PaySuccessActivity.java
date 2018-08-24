package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.util.AppManager;
import com.padacn.xmgoing.util.EvenPayPriceUtil;
import com.padacn.xmgoing.util.even.EventBusUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.CustomToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.success_pay_bar)
    CustomToolBar successPayBar;
    @BindView(R.id.success_pay_price)
    TextView successPayPrice;
    @BindView(R.id.success_pay_button_rl)
    RelativeLayout successPayButtonRl;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_success_pay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

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
    protected void initView() {
        super.initView();
//        AppManager.getInstance().finishActivity(CashierActivity.cashierActivity);
        EventBusUtil.sendEvent(new MessageEvent(EvenCode.ClearCashier));
        successPayBar.setStyle("支付成功", Color.parseColor("#f8d948"), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.navigate(PaySuccessActivity.this, MainHomeActivity.class);
            }
        });
    }

    /**
     * 传递给支付成功页面的价格
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EvenPayPriceUtil event) {
        successPayPrice.setText(event.getPriceString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.success_pay_button_rl)
    public void onViewClicked() {
        BaseActivity.navigate(PaySuccessActivity.this, MainHomeActivity.class);
    }
}
