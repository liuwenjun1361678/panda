package com.padacn.xmgoing.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.X5WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class WebDetailsActivity extends BaseActivity {
    @BindView(R.id.web_detalis_bar)
    CustomToolBar webDetalisBar;
    @BindView(R.id.web_detalis_webview)
    X5WebView webDetalisWebview;

    private String title;
    private String url;

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
    protected int setLayoutId() {
        return R.layout.activity_web_details;
    }

    @Override
    protected void initView() {
        super.initView();

        Bundle bundle = this.getIntent().getExtras();
        title = bundle.getString("title");
        url = bundle.getString("url");
        webDetalisBar.setStyle(title, Color.parseColor("#f8d948"));
        webDetalisWebview.loadUrl(url);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
