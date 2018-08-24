package com.padacn.xmgoing.activity;


import android.support.v7.widget.Toolbar;
import android.view.View;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.view.GlideImageLoader;
import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<String> mImages = new ArrayList<>();
    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
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

}
