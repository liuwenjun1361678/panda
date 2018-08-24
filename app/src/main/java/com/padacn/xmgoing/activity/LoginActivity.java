package com.padacn.xmgoing.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.flyco.tablayout.SlidingTabLayout;

import com.gyf.barlibrary.OnKeyboardListener;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.fragment.LoginAccountFragment;
import com.padacn.xmgoing.fragment.LoginSmsFragment;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.CustomViewPager;
import com.umeng.socialize.UMShareAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by 46009 on 2018/4/28.
 */

public class LoginActivity extends BaseActivity {

    private final String[] mTitles = {
            "账号登录", "短信验证码登录"
    };
    @BindView(R.id.login_bar)
    CustomToolBar loginBar;
    @BindView(R.id.login_tab)
    SlidingTabLayout loginTab;
    @BindView(R.id.login_vp)
    ViewPager loginVp;

    private LoginAdapter loginAdapter;

    private SupportFragment[] mFragments = new SupportFragment[2];

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
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

        SharePreferencesUtil.setBooleanSharePreferences(this, Constans.isAlreadyLogin, false);
        initFragment();
        loginBar.setStyle("登录", Color.parseColor("#f8d948"));
        loginAdapter = new LoginAdapter(getSupportFragmentManager());
        loginVp.setAdapter(loginAdapter);
//        loginVp.setScanScroll(false);
        loginTab.setViewPager(loginVp);
    }

    private void initFragment() {

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
    }


    private class LoginAdapter extends FragmentPagerAdapter {
        public LoginAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return LoginAccountFragment.newInstance();
            } else {
                return LoginSmsFragment.newInstance();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
