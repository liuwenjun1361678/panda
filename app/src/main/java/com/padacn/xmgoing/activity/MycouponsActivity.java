package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.fragment.MineCollectionFragment;
import com.padacn.xmgoing.fragment.MyCouponsFragment;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 46009 on 2018/5/5.
 */

public class MycouponsActivity extends BaseActivity {
    @BindView(R.id.mine_my_coupons_bar)
    CustomToolBar mineMyCouponsBar;
    @BindView(R.id.mine_my_coupons_tab)
    SlidingTabLayout mineMyCouponsTab;
    @BindView(R.id.mine_my_coupons_vp)
    ViewPager mineMyCouponsVp;
    private OrderPagerAdapter orderPagerAdapter;
    private static final int NUM = 2;
    private final String[] mTitles = {"未使用", "已失效"};

    private static final String TAG = "MycouponsActivity";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_my_coupns;
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
        mineMyCouponsBar.setStyle("我的卡券", Color.parseColor("#f8d948"));
        orderPagerAdapter = new OrderPagerAdapter(getSupportFragmentManager());
        mineMyCouponsVp.setAdapter(orderPagerAdapter);
        mineMyCouponsTab.setViewPager(mineMyCouponsVp);

    }


    private class OrderPagerAdapter extends FragmentPagerAdapter {
        public OrderPagerAdapter(FragmentManager fm) {
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
            return new MyCouponsFragment(position);
        }
    }

}
