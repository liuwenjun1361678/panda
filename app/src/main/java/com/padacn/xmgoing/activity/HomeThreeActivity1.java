package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.fragment.BaseFragment;
import com.padacn.xmgoing.fragment.homethree.ComprehensiveFragment;
import com.padacn.xmgoing.fragment.homethree.SalesFragment;
import com.padacn.xmgoing.fragment.homethree.PriceFragment;
import com.padacn.xmgoing.model.HomeThreeBean;
import com.padacn.xmgoing.presenter.FinishEventUtil;
import com.padacn.xmgoing.util.EvenPriceSortUtil;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.FilterFragments;
import com.padacn.xmgoing.view.HomeThreeViewPagerAdapter;
import com.padacn.xmgoing.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class HomeThreeActivity1 extends BaseActivity {
    private static final String TAG = "HomeThreeActivity1";
    @BindView(R.id.home_three_bar)
    CustomToolBar homeThreeBar;
    @BindView(R.id.topic_tabLayout)
    TabLayout topicTabLayout;
    @BindView(R.id.textsearch_filter)
    TextView textsearchFilter;
    @BindView(R.id.home_three_filter_out)
    RelativeLayout homeThreeFilterOut;
    @BindView(R.id.vp_home_three)
    NoScrollViewPager vpHomeThree;
    @BindView(R.id.nav_view)
    LinearLayout navView;
    @BindView(R.id.hone_three_drawer_layout)
    DrawerLayout honeThreeDrawerLayout;

    private HomeThreeViewPagerAdapter adapter;
    List<BaseFragment> list_fragment;
    List<String> list_table;
    //查询的关键key
    private String mSearchkey;
    private int priceSortMode = 0;
    private ImageView img_title;
    private FilterFragments filterFragment;
    private List<HomeThreeBean.ConditionsBean> conditionsBeanList;

    private String currProTypeId;
    private String currTypeName;
    private boolean IsFilter = false;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home_three1;
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
    protected void initData() {
        super.initData();
        getThreeData();
    }

    //获取默认销量排序
    private void getThreeData() {

    }

    @Override
    protected void initView() {
        super.initView();

        EventBus.getDefault().register(this);
        Bundle bundle = this.getIntent().getExtras();
        currProTypeId = bundle.getString("proTypeId");
        currTypeName = bundle.getString("typeName");
        homeThreeBar.setStyle(currTypeName, Color.parseColor("#f8d748"));
        initTab();
        initEvent();
//        vpHomeThree.setOffscreenPageLimit(4);
        vpHomeThree.setNoScroll(true);
        homeThreeFilterOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                honeThreeDrawerLayout.openDrawer(GravityCompat.END);
                textsearchFilter.setTextColor(Color.parseColor("#ff3d39"));
            }
        });

        honeThreeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        filterFragment = new FilterFragments(this, currProTypeId);
        navView.addView(filterFragment);
    }

    /**
     * 完成监听
     */
    @Subscribe
    public void onEventMainThread(FinishEventUtil event) {
        honeThreeDrawerLayout.closeDrawer(GravityCompat.END);
        vpHomeThree.setCurrentItem(0);
        IsFilter = true;
        textsearchFilter.setTextColor(Color.parseColor("#ff3d39"));
    }


    private void initTab() {
        list_fragment = new ArrayList<>();
        ComprehensiveFragment comprehensiveFragment = ComprehensiveFragment.newInstance(currProTypeId);
        SalesFragment price1Fragment = SalesFragment.newInstance(currProTypeId);
        PriceFragment priceFragment = PriceFragment.newInstance(currProTypeId, priceSortMode);
        list_fragment.add(comprehensiveFragment);
        list_fragment.add(price1Fragment);
        list_fragment.add(priceFragment);

        list_table = new ArrayList<>();
        list_table.add("综合");
        list_table.add("销量");
        list_table.add("价格");

        topicTabLayout.setTabMode(TabLayout.MODE_FIXED);
        topicTabLayout.setSelectedTabIndicatorHeight(0);
        adapter = new HomeThreeViewPagerAdapter(this, getSupportFragmentManager(), list_fragment, list_table);
        vpHomeThree.setAdapter(adapter);
        topicTabLayout.setupWithViewPager(vpHomeThree);


        /**
         * 调用本方法中的gettabview
         */
        for (int i = 0; i < topicTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = topicTabLayout.getTabAt(i);
            if (tab != null) {
                /**
                 * 调用adapter中的gettabview
                 */
                tab.setCustomView(adapter.getTabView(i));
                if (tab.getCustomView() != null) {
                    View tabView = (View) tab.getCustomView().getParent();
                    tabView.setTag(i);
                    tabView.setOnClickListener(mTabOnClickListener);
                }
            }
        }
//        vpHomeThree.setCurrentItem(1);
        vpHomeThree.setCurrentItem(0);
    }


    private void initEvent() {
        topicTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
//                IsFilter = false;
                vpHomeThree.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = (ImageView) view.findViewById(R.id.title_iv);
        TextView txt_title = (TextView) view.findViewById(R.id.title_tv);
        View view1 = (View) view.findViewById(R.id.home_three_tab_underline);
        view1.setBackgroundColor(Color.parseColor("#f8d748"));
        if (txt_title.getText().toString().equals("综合")) {
            vpHomeThree.setCurrentItem(0);
            Log.e(TAG, "changeTabSelect:11111111111111111111111 " + IsFilter);
            priceSortMode = 0;
            textsearchFilter.setTextColor(Color.parseColor("#111111"));
        } else if (txt_title.getText().toString().equals("销量")) {
            vpHomeThree.setCurrentItem(1);
            priceSortMode = 0;
            textsearchFilter.setTextColor(Color.parseColor("#111111"));
        } else if (txt_title.getText().toString().equals("价格")) {
            vpHomeThree.setCurrentItem(2);
            textsearchFilter.setTextColor(Color.parseColor("#111111"));
        } else {
            priceSortMode = 0;
        }
    }

    //tab 未选择
    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = (ImageView) view.findViewById(R.id.title_iv);
        TextView txt_title = (TextView) view.findViewById(R.id.title_tv);
        txt_title.setTextColor(Color.BLACK);
        View view1 = (View) view.findViewById(R.id.home_three_tab_underline);
        view1.setBackgroundColor(Color.parseColor("#ffffff"));
        if (txt_title.getText().toString().equals("价格")) {

        } else {

        }
    }

    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            if (pos == 2) {
                TabLayout.Tab tab = topicTabLayout.getTabAt(pos);
                /**
                 * 设置升序和降序的数据
                 */
                setImage(tab);
            } else {
                TabLayout.Tab tab = topicTabLayout.getTabAt(pos);
                if (tab != null) {
                    tab.select();
                }
                if (img_title != null)
                    img_title.setImageResource(R.mipmap.home_three_price_default);
            }
        }
    };

    private void setImage(TabLayout.Tab tab) {

        switch (priceSortMode) {
            /** 从低到高的排序 */
            case 0:
                priceSortMode = 1;
                break;
            /** 当mode为1说明其已被选中，那么选择2，及从高到低 */
            case 1:
                priceSortMode = 2;
                break;
            /** 当mode为2说明其处于从高到低，那么选择1，从低到高 */
            case 2:
                priceSortMode = 1;
                break;
        }
        EventBus.getDefault().post(new EvenPriceSortUtil(priceSortMode));
        Log.e(TAG, "setImage: ___________" + priceSortMode);
        setPriceSortDrawable(priceSortMode, tab);
    }

    private void setPriceSortDrawable(int priceSortMode, TabLayout.Tab tab) {
        View view = tab.getCustomView();
        img_title = (ImageView) view.findViewById(R.id.title_iv);
        switch (priceSortMode) {
            case 0:
                img_title.setImageResource(R.mipmap.home_three_price_1);
                break;
            case 1:
                img_title.setImageResource(R.mipmap.home_three_price_2);
                break;
            case 2:
                img_title.setImageResource(R.mipmap.home_three_price_1);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
