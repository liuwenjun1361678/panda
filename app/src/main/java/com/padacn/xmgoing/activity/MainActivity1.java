package com.padacn.xmgoing.activity;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.view.GlideImageLoader;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity1 extends BaseActivity {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.item_detail_container)
    NestedScrollView itemDetailContainer;
    private List<String> mImages = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main1;
    }


    @Override
    protected void initData() {
        mImages.add("http://desk.zol.com.cn/showpic/1024x768_63850_14.html");
        mImages.add("http://desk.zol.com.cn/showpic/1024x768_63850_14.html");
        mImages.add("http://desk.zol.com.cn/showpic/1024x768_63850_14.html");
        mImages.add("http://desk.zol.com.cn/showpic/1024x768_63850_14.html");
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(toolbar).init();
    }

    @Override
    protected void initView() {
        super.initView();

        banner.setImages(mImages)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
                .start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
