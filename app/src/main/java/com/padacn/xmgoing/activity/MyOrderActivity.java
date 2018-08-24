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
import com.padacn.xmgoing.fragment.BaseFragment;
import com.padacn.xmgoing.fragment.MineOrderCommentFragment;
import com.padacn.xmgoing.fragment.MineOrderFragment;
import com.padacn.xmgoing.util.EvenOrderTabUtil;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.CustomViewPagerTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 46009 on 2018/5/5.
 */

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.mine_my_order_bar)
    CustomToolBar mineMyOrderBar;
    @BindView(R.id.mine_my_order_tab)
    SlidingTabLayout mineMyOrderTab;
    @BindView(R.id.mine_my_order_vp)
    ViewPager mineMyOrderVp;
    private OrderPagerAdapter orderPagerAdapter;
    List<BaseFragment> list_fragment;
    private String[] mTitles;

    private int currPosition;
    private static final String TAG = "MyOrderActivity";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_my_order;
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
    protected void setListener() {
        super.setListener();
        mineMyOrderTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                mineMyOrderVp.resetHeight(position);
            }

            @Override
            public void onTabReselect(int position) {

            }

        });
    }

    @Override
    protected void initView() {
        super.initView();
        mineMyOrderBar.setStyle("我的订单", Color.parseColor("#f8d948"));
        mTitles = getResources().getStringArray(R.array.mine_order);
        Bundle bundle = this.getIntent().getExtras();
        currPosition = bundle.getInt("position");
        orderPagerAdapter = new OrderPagerAdapter(getSupportFragmentManager());
        mineMyOrderVp.setAdapter(orderPagerAdapter);
        mineMyOrderTab.setViewPager(mineMyOrderVp);
        mineMyOrderTab.setCurrentTab(currPosition);
        mineMyOrderVp.setCurrentItem(currPosition);
        mineMyOrderVp.setOffscreenPageLimit(5);
        Log.e(TAG, "initView: " + currPosition);
//        orderPagerAdapter.setCurrItem(currPosition);
    }

    private class OrderPagerAdapter extends FragmentPagerAdapter {

        public OrderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 3) {
                return new MineOrderCommentFragment(position, mineMyOrderVp);
            } else {
                return new MineOrderFragment(position, mineMyOrderVp);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
