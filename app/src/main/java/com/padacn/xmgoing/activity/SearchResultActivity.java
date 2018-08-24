package com.padacn.xmgoing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.fragment.BaseFragment;
import com.padacn.xmgoing.fragment.search.SearchGoodsFragment;
import com.padacn.xmgoing.fragment.search.SearchTravleFragment;
import com.padacn.xmgoing.util.AppManager;
import com.padacn.xmgoing.util.HotSearchEven;
import com.padacn.xmgoing.util.even.EventBusUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/29 0029.
 */

public class SearchResultActivity extends BaseActivity {
    private static final String TAG = "SearchResultActivity";
    @BindView(R.id.hot_search_cancel)
    RelativeLayout hotSearchCancel;
    @BindView(R.id.hot_search_input)
    EditText hotSearchInput;
    @BindView(R.id.mine_my_order_tab)
    SlidingTabLayout mineMyOrderTab;
    @BindView(R.id.mine_my_order_vp)
    ViewPager mineMyOrderVp;

    private final String[] mTitles = {"商品", "攻略"};
    @BindView(R.id.home_top_bar_search_ll)
    LinearLayout homeTopBarSearchLl;
    private List<BaseFragment> list_fragment;
    private String currSearchStr;
    private SearchPagerAdapter searchPagerAdapter;
    private String currPageType;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_search_result;
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.common_ffffff)
                .navigationBarColor(R.color.home_top_text_color)
                .keyboardEnable(true)
                .init();
    }


    @Override
    protected void initView() {
        super.initView();

        EventBus.getDefault().register(this);
        Bundle bundle = this.getIntent().getExtras();
        currSearchStr = bundle.getString("searchString");
        list_fragment = new ArrayList<>();
        SearchGoodsFragment searchGoodsFragment = SearchGoodsFragment.newInstance(currSearchStr);
        SearchTravleFragment searchTravleFragment = SearchTravleFragment.newInstance(currSearchStr);
        list_fragment.add(searchGoodsFragment);
        list_fragment.add(searchTravleFragment);
        hotSearchInput.setText(currSearchStr);
        searchPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager());
        mineMyOrderVp.setAdapter(searchPagerAdapter);
        mineMyOrderTab.setViewPager(mineMyOrderVp);

    }

    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.hot_search_cancel, R.id.hot_search_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hot_search_cancel:
//                AppManager.getInstance().finishActivity(HotSearchActivity.hotSearchActivity);
                EventBusUtil.sendEvent(new MessageEvent(EvenCode.ClearHot));

                finish();
                break;
            case R.id.hot_search_input:
                BaseActivity.navigate(this, HotSearchActivity.class);
                break;
        }
    }


    /**
     * 完成监听
     */
    @Subscribe
    public void onEventMainThread(HotSearchEven event) {

    }

    private class SearchPagerAdapter extends FragmentPagerAdapter {
        public SearchPagerAdapter(FragmentManager fm) {
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
            return list_fragment.get(position);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
