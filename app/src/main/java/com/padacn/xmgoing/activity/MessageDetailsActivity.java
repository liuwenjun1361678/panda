package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.view.CustomToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/1 0001.
 */

public class MessageDetailsActivity extends BaseActivity {

    @BindView(R.id.message_details_bar)
    CustomToolBar messageDetailsBar;
    @BindView(R.id.message_details_title)
    TextView messageDetailsTitle;
    @BindView(R.id.message_details_contant)
    TextView messageDetailsContant;
    @BindView(R.id.message_details_type)
    TextView messageDetailsType;
    @BindView(R.id.message_details_time)
    TextView messageDetailsTime;

    @Override
    protected int setLayoutId() {
        return R.layout.activirt_message_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        messageDetailsBar.setStyle("详情", Color.parseColor("#f8d948"));
        Bundle bundle = this.getIntent().getExtras();
        messageDetailsTitle.setText(bundle.getString("title"));
        messageDetailsContant.setText("\u3000\u3000" + bundle.getString("content"));
        messageDetailsTime.setText(bundle.getString("date"));
        if (bundle.getString("userType").equals("0")) {
            messageDetailsType.setText("熊猫成长季");
        }
    }

    @Override
    protected void initData() {
        super.initData();
    }

}
