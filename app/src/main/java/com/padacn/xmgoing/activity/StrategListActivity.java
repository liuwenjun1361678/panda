package com.padacn.xmgoing.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.StrategiesAdapter;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.StrategyBean;
import com.padacn.xmgoing.view.GlideImageLoader;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class StrategListActivity extends BaseActivity {
    private static final String TAG = "StrategListActivity";

    @BindView(R.id.home_second_banner)
    Banner homeSecondBanner;
    @BindView(R.id.home_second_back_rl)
    RelativeLayout homeSecondBackRl;
    @BindView(R.id.home_second_top_rl)
    RelativeLayout homeSecondTopRl;
    @BindView(R.id.home_second_top_bar)
    Toolbar homeSecondTopBar;
    @BindView(R.id.home_second_appbar)
    AppBarLayout homeSecondAppbar;
    @BindView(R.id.home_second_rl)
    RecyclerView homeSecondRl;
    @BindView(R.id.home_strategy_smartRefreshLayout)
    RefreshLayout homeStrategySmartRefreshLayout;
    @BindView(R.id.home_top_bar_search_ll)
    LinearLayout homeTopBarSearchLl;

    private StrategiesAdapter strategiesAdapter;
    //底部下拉list总共頁数
    private int pageCount;
    //当前页数
    private int currPage = 0;

    //顶部banner
    private List<StrategyBean.DataBean.BannerBean> bannerBeanList;
    private List<String> bannerImages;
    private List<StrategyBean.DataBean.ListStrategyBean> listStrategyBeans;
    private List<StrategyBean.DataBean.ListStrategyBean> listStrategyBeansTotal;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_strate_list;
    }


    @Override
    protected void initData() {
        super.initData();
        getStrategData("1", 0);
    }

    @Override
    protected void setListener() {
        super.setListener();
        homeStrategySmartRefreshLayout.setRefreshHeader(new RefreshView(StrategListActivity.this));
        homeStrategySmartRefreshLayout.setHeaderHeight(getResources().getDimension(R.dimen.dp_25));
        homeStrategySmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listStrategyBeansTotal.clear();
                        currPage = 0;
                        getStrategData("1", 0);
                        homeStrategySmartRefreshLayout.finishRefresh();
                    }
                }, 2000);


            }

        });
    }

    private void getStrategData(final String isHead, int currPe) {
        listStrategyBeans = new ArrayList<>();
        bannerBeanList = new ArrayList<>();
        bannerImages = new ArrayList<>();
        bannerBeanList.clear();
        bannerImages.clear();
        listStrategyBeans.clear();
        OkGo.<String>post(ServiceApi.HOME_STRATEGY_URL)
                .tag(this)
                .params("isHead", isHead)
                .params("pageNum", String.valueOf(currPe))
                .params("pageSize", "10")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {

                                Gson gson = new Gson();
                                StrategyBean strategyBean = gson.fromJson(s, StrategyBean.class);
                                if (strategyBean.getResult() == 1) {
                                    if (isHead.equals("1")) {
                                        listStrategyBeans.clear();
                                        bannerBeanList.clear();
                                        pageCount = strategyBean.getPageCount();
                                        listStrategyBeansTotal.addAll(strategyBean.getData().getListStrategy());
                                        bannerBeanList.addAll(strategyBean.getData().getBanner());
                                        setBanner();
                                        strategiesAdapter.setNewData(listStrategyBeansTotal);
                                    }
                                    if (isHead.equals("0")) {
                                        listStrategyBeans.addAll(strategyBean.getData().getListStrategy());
                                        strategiesAdapter.addData(listStrategyBeans);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }


    private void setBanner() {

        for (int i = 0; i < bannerBeanList.size(); i++) {
            bannerImages.add(bannerBeanList.get(i).getBannerPic());
        }
        homeSecondBanner.setImages(bannerImages)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
                .start();
    }

    @Override
    protected void initView() {
        super.initView();
        homeSecondBackRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        homeSecondAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float) (-verticalOffset) / homeSecondBanner.getHeight() * 255;
                if (verticalOffset > -homeSecondBanner.getHeight() / 2) {
                    homeSecondTopRl.getBackground().setAlpha((int) alpha);
                } else {
                    homeSecondTopRl.getBackground().setAlpha(255);
                }
            }
        });
        listStrategyBeansTotal = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeSecondRl.setLayoutManager(layoutManager);
        strategiesAdapter = new StrategiesAdapter(R.layout.item_home_activity_one, listStrategyBeansTotal);
        homeSecondRl.setAdapter(strategiesAdapter);
        strategiesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("strateDetailId", String.valueOf(listStrategyBeansTotal.get(position).getStrategyId()));
                BaseActivity.navigate(StrategListActivity.this, StrategdetailActivity.class, bundle);
            }
        });

        strategiesAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                homeSecondRl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            strategiesAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getStrategData("0", currPage);
                        }
                    }
                }, 2000);
            }
        }, homeSecondRl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.home_second_back_rl, R.id.home_top_bar_search_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_second_back_rl:
                finish();
                break;
            case R.id.home_top_bar_search_ll:
                BaseActivity.navigate(StrategListActivity.this, HotSearchActivity.class);
                break;
        }
    }
}
