package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.fragment.MineCollectionFragment;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 46009 on 2018/5/5.
 */

public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.mine_my_order_bar)
    CustomToolBar mineMyOrderBar;
    @BindView(R.id.mine_my_order_tab)
    SlidingTabLayout mineMyOrderTab;
    @BindView(R.id.mine_my_order_vp)
    CustomViewPager mineMyOrderVp;


    private CollectionPagerAdapter collectionPagerAdapter;

    private final String[] mTitles = {"收藏的商品", "收藏的游记"};

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_my_collection;
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
        mineMyOrderBar.setStyle("我的收藏", "保存", Color.parseColor("#f8d948"), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        collectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager());
        mineMyOrderVp.setAdapter(collectionPagerAdapter);
        mineMyOrderVp.setScanScroll(false);
        mineMyOrderTab.setViewPager(mineMyOrderVp);

    }


    private class CollectionPagerAdapter extends FragmentPagerAdapter {
        public CollectionPagerAdapter(FragmentManager fm) {
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
            return MineCollectionFragment.getInstance();
        }


    }
}
