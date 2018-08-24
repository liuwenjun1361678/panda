package com.padacn.xmgoing.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.util.AppManager;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.widget.CountDownView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.vondear.rxtools.RxDeviceTool;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;

    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;
    @BindView(R.id.countdown)
    CountDownView countdown;
    @BindView(R.id.splash_rl)
    RelativeLayout splashRl;
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    //是否自动进入
    private boolean isClickGo = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);
        if (RxDeviceTool.getAppVersionNo(this) > SharePreferencesUtil.getIntSharePreferences(this, Constans.Version_code, 0)) {
            SharePreferencesUtil.setIntSharePreferences(this, Constans.Version_code, RxDeviceTool.getAppVersionNo(this));
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
        } else {

            //初始化沉浸式
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
            mImmersionBar.fitsSystemWindows(true)
                    .statusBarColor(R.color.transparent)
                    .navigationBarColor(R.color.home_top_text_color)
                    .keyboardEnable(true)
                    .init();
            initView();
        }


    }

    private void initView() {

        countdown.start();
        countdown.setOnLoadingFinishListener(new CountDownView.OnLoadingFinishListener() {
            @Override
            public void finish() {
                if (isClickGo) {
                    countdown.setVisibility(View.GONE);
                    startActivity(new Intent(SplashActivity.this, MainHomeActivity.class));
                    SplashActivity.this.finish();
                }
            }
        });
        countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClickGo = false;
                countdown.setVisibility(View.GONE);
                startActivity(new Intent(SplashActivity.this, MainHomeActivity.class));
                SplashActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().currentActivity();
        unbinder.unbind();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
