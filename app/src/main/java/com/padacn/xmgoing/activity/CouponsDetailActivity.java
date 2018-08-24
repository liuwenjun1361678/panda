package com.padacn.xmgoing.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.view.CustomToolBar;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 46009 on 2018/5/5.
 */

public class CouponsDetailActivity extends BaseActivity {

    @BindView(R.id.coupons_details_bar)
    CustomToolBar couponsDetailsBar;
    @BindView(R.id.coupons_details_title)
    TextView couponsDetailsTitle;
    @BindView(R.id.coupons_details_time_text)
    TextView couponsDetailsTimeText;
    @BindView(R.id.coupons_details_time)
    LinearLayout couponsDetailsTime;
    @BindView(R.id.coupons_details_price_left)
    TextView couponsDetailsPriceLeft;
    @BindView(R.id.coupons_details_price_text)
    TextView couponsDetailsPriceText;
    @BindView(R.id.coupons_details_top_rl)
    RelativeLayout couponsDetailsTopRl;
    @BindView(R.id.coupons_details_ruduce_price)
    TextView couponsDetailsRuducePrice;

    private String typeName;
    private String reduction;
    private String term;
    private String useTime = null;
    private String cid;
    private String voidTime;
    private boolean flashUse;

    private String sellerName;
    private int couponType;
    private LoadingDailog.Builder loadBuilder;
    private LoadingDailog dialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_coupons_details;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    protected void initView() {
        super.initView();
        couponsDetailsBar.setStyle("优惠券详情", Color.parseColor("#f8d948"));
        Bundle bundle = this.getIntent().getExtras();
        typeName = bundle.getString("typeName");
        reduction = bundle.getString("reduction");
        term = bundle.getString("term");
        useTime = bundle.getString("useTime");
        cid = bundle.getString("cid");
        voidTime = bundle.getString("voidTime");
        couponType = bundle.getInt("couponType");
        sellerName = bundle.getString("sellerName");
        flashUse = bundle.getBoolean("flashUse");
        switch (couponType) {
            case 1:
                couponsDetailsTitle.setText(sellerName + "优惠券");
                break;
            case 2:
                couponsDetailsTitle.setText("全平台通用优惠券");
                break;
            case 3:
                couponsDetailsTitle.setText("小书童俱乐部优惠券");
                break;
        }

        couponsDetailsPriceText.setText(reduction);
        couponsDetailsRuducePrice.setText("满" + term + "可用");
        if (flashUse) {
            couponsDetailsTimeText.setText(useTime + "至" + voidTime);
        } else {
            couponsDetailsTimeText.setText("不限时间");
        }
    }

    @OnClick(R.id.gain_coupons)
    public void onViewClicked() {
        if (!SharePreferencesUtil.getBooleanSharePreferences(CouponsDetailActivity.this, Constans.isAlreadyLogin, false)) {
            BaseActivity.navigate(CouponsDetailActivity.this, LoginActivity.class);
            return;
        }

        OkGo.<String>post(ServiceApi.gain)
                .tag(this)
                .headers("Area", Constans.Area)
                .params("cid", cid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
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
                });
    }
}
