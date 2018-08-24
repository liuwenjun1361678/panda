package com.padacn.xmgoing.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.util.AppManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.banner_guide_background)
    BGABanner bannerGuideBackground;

    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        unbinder = ButterKnife.bind(this);
//        AppManager.getInstance().addActivity(this);
        //初始化沉浸式
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
        mImmersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.transparent)
                .navigationBarColor(R.color.home_top_text_color)
                .keyboardEnable(true)
                .init();
        initView();

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }

    private void initView() {

        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        bannerGuideBackground.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.welcome_1,
                R.mipmap.welcome_2,
                R.mipmap.welcome_3,
                R.mipmap.welcome_4
        );
        bannerGuideBackground.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                if (position == 3) {
                    BaseActivity.navigate(WelcomeActivity.this, MainHomeActivity.class);
                    finish();
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        bannerGuideBackground.setBackgroundResource(android.R.color.white);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
        unbinder.unbind();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
