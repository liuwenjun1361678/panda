package com.padacn.xmgoing.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.rollinglayout.RollingLayout;
import com.jkb.rollinglayout.RollingLayoutAction;
import com.lzy.okgo.utils.OkLogger;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.HeadlineAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 46009 on 2018/4/26.
 */

public class HomeFragment1 extends BaseFragment {

    public static String LOG_TAG="HomeFragment1";

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.item_detail_container)
    NestedScrollView itemDetailContainer;
    @BindView(R.id.quwan_image)
    ImageView quwanImage;
    @BindView(R.id.quwan_text)
    TextView quwanText;
    @BindView(R.id.yingdi_image)
    ImageView yingdiImage;
    @BindView(R.id.yingdi_text)
    TextView yingdiText;
    @BindView(R.id.yanxue_image)
    ImageView yanxueImage;
    @BindView(R.id.yanxue_text)
    TextView yanxueText;
    @BindView(R.id.jingdian_image)
    ImageView jingdianImage;
    @BindView(R.id.jingdian_text)
    TextView jingdianText;
    @BindView(R.id.xiongmao_image)
    ImageView xiongmaoImage;
    @BindView(R.id.xiongmao_text)
    TextView xiongmaoText;
    @BindView(R.id.rollingdownUp)
    RollingLayout rollingdownUp;
    Unbinder unbinder;
    @BindView(R.id.main_vp_container)
    ViewPager mainVpContainer;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"
    };
    private MyPagerAdapter mAdapter;


    private List<String> mImages = new ArrayList<>();

    //头条adapter
    private HeadlineAdapter headlineAdapter;


    public static HomeFragment1 newInstance() {
        Bundle args = new Bundle();
        HomeFragment1 fragment = new HomeFragment1();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home1;
    }

    @Override
    protected void initData() {
        mImages.add("http://desk.zol.com.cn/showpic/1024x768_63850_14.html");
        mImages.add("http://desk.zol.com.cn/showpic/1024x768_63850_14.html");
        mImages.add("http://desk.zol.com.cn/showpic/1024x768_63850_14.html");

    }

    @Override
    protected void initView() {
        banner.setBackgroundResource(R.mipmap.banner1);
//        banner.setImages(mImages)
//                .setImageLoader(new GlideImageLoader())
//                .setDelayTime(5000)
//                .start();
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float)(-verticalOffset) / banner.getHeight()*255;
                OkLogger.d(LOG_TAG,verticalOffset+"+++"+banner.getHeight()+"+++"+alpha);
                if(verticalOffset>=-banner.getHeight()){
                    toolbar.getBackground().setAlpha((int)alpha);
                }else{
                    toolbar.getBackground().setAlpha(255);
                }
            }
        });



        mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        mainVpContainer.setAdapter(mAdapter);

        mainVpContainer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener
                (toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener
                (mainVpContainer));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return MainTabFragment.getInstance(position + 1);
        }


    }
}
