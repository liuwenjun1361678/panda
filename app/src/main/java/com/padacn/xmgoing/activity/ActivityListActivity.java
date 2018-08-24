package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.ActivityAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.ActivityBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.util.spaces.SpacesItemDecoration;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.GlideImageLoader;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * Created by Administrator on 2018/7/15 0015.
 */

public class ActivityListActivity extends BaseActivity {

    @BindView(R.id.mine_my_order_bar)
    CustomToolBar mineMyOrderBar;
    @BindView(R.id.ex_banner)
    Banner exBanner;
    @BindView(R.id.top_image_1)
    ImageView topImage1;
    @BindView(R.id.top_title_1)
    TextView topTitle1;
    @BindView(R.id.top_contant_1)
    TextView topContant1;
    @BindView(R.id.top_price_1)
    TextView topPrice1;
    @BindView(R.id.top_sell_num_1)
    TextView topSellNum1;
    @BindView(R.id.top_ll_1)
    LinearLayout topLl1;
    @BindView(R.id.top_image_2)
    ImageView topImage2;
    @BindView(R.id.top_contant_2)
    TextView topContant2;
    @BindView(R.id.top_ll_2)
    LinearLayout topLl2;
    @BindView(R.id.top_image_3)
    ImageView topImage3;
    @BindView(R.id.top_contant_3)
    TextView topContant3;
    @BindView(R.id.top_ll_3)
    LinearLayout topLl3;
    @BindView(R.id.top_image_4)
    ImageView topImage4;
    @BindView(R.id.top_contant_4)
    TextView topContant4;
    @BindView(R.id.top_ll_4)
    LinearLayout topLl4;
    @BindView(R.id.activity_recyclerView)
    RecyclerView activityRecyclerView;
    @BindView(R.id.activity_smartRefreshLayout)
    SmartRefreshLayout activitySmartRefreshLayout;


    private List<ActivityBean.DataBean.HeadListBean> headListBeans;
    private List<ActivityBean.DataBean.BannerBean> bannerBeanList;
    private List<String> bannerImages;

    private List<ActivityBean.DataBean.ListBean> list;
    private List<ActivityBean.DataBean.ListBean> listTotal;
    private ActivityAdapter activityAdapter;

    private LoadingAndRetryManager mLoadingAndRetryManager;
    private int pageCount;
    private int currPage = 0;

    private TextView[] arrText;
    private ImageView[] arrImage;

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
    protected int setLayoutId() {
        return R.layout.activity_activity_list;
    }

    @Override
    protected void initData() {
        mLoadingAndRetryManager.showLoading();
        getData(0, true);
    }

    @Override
    protected void setListener() {
        activitySmartRefreshLayout.setRefreshHeader(new RefreshView(this));
        activitySmartRefreshLayout.setHeaderHeight(getResources().getDimension(R.dimen.dp_25));
        activitySmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listTotal.clear();
                        getData(0, true);
                        activitySmartRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }

        });
    }

    @Override
    protected void initView() {
        super.initView();
        mineMyOrderBar.setStyle("活动推荐", Color.parseColor("#f8d948"));
        listTotal = new ArrayList<>();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(activitySmartRefreshLayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                ActivityListActivity.this.setRetryEvent(retryView);
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp_10);
        activityRecyclerView.addItemDecoration(new SpacesItemDecoration(2, spacingInPixels, true));
        activityRecyclerView.setLayoutManager(gridLayoutManager);
        activityAdapter = new ActivityAdapter(R.layout.item_activity_lsit, listTotal);
        activityRecyclerView.setAdapter(activityAdapter);


        activityAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                activityRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            activityAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getData(currPage, false);
                        }
                    }
                }, 2000);
            }
        }, activityRecyclerView);

        activityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goGooddetail(String.valueOf(listTotal.get(position).getPId()), false);
            }
        });
    }


    /**
     * 显示banner
     */
    private void showBanner() {
        bannerImages = new ArrayList<>();
        for (int i = 0; i < bannerBeanList.size(); i++) {
            bannerImages.add(bannerBeanList.get(i).getBannerPic());
        }
        exBanner.setImages(bannerImages)
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setDelayTime(5000)
                .start();


        exBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
                switch (bannerBeanList.get(position).getBannerType()) {
                    case 0:
                        bundle.putString("pid", String.valueOf(bannerBeanList.get(position).getBannerId()));
                        bundle.putBoolean("panic", false);
                        BaseActivity.navigate(ActivityListActivity.this, GoodsDetailsActivity.class, bundle);
                        break;
                    case 1:
                        bundle.putString("strateDetailId", String.valueOf(bannerBeanList.get(position).getBannerId()));
                        BaseActivity.navigate(ActivityListActivity.this, StrategdetailActivity.class, bundle);
                }
            }
        });
    }

    private void getData(int currPe, final boolean isHead) {
        list = new ArrayList<>();
        headListBeans = new ArrayList<>();
        bannerBeanList = new ArrayList<>();
        list.clear();
        headListBeans.clear();
        bannerBeanList.clear();
        OkGo.<String>post(ServiceApi.ActivityList)
                .tag(this)
                .params("pageNum", String.valueOf(currPe))
                .params("pageSize", Constans.COMMONPAGE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mLoadingAndRetryManager.showContent();
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                ActivityBean activityBean = gson.fromJson(s, ActivityBean.class);
                                pageCount = activityBean.getPageCount();
                                if (isHead) {
                                    if (activityBean.getData().getList().size() > 0) {
                                        listTotal.addAll(activityBean.getData().getList());
                                        activityAdapter.setNewData(listTotal);
                                    }
                                    if (activityBean.getData().getHeadList().size() > 0) {
                                        headListBeans.addAll(activityBean.getData().getHeadList());
                                        showTopData();
                                    }
                                    if (activityBean.getData().getBanner().size() > 0) {
                                        bannerBeanList.addAll(activityBean.getData().getBanner());
                                        showBanner();
                                    }

                                } else {
                                    list.addAll(activityBean.getData().getList());
                                    activityAdapter.addData(list);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mLoadingAndRetryManager.showRetry();
                    }
                });

    }

    /**
     * 展示頂部數據
     */
    private void showTopData() {
        arrImage = new ImageView[]{topImage1, topImage2, topImage3, topImage4};
        arrText = new TextView[]{topContant1, topContant2, topContant3, topContant4};
        for (int i = 0; i < headListBeans.size(); i++) {
            CommonUtil.loadImage(ActivityListActivity.this, headListBeans.get(i).getPics().get(0).getPath(), arrImage[i]);
            arrText[i].setText(headListBeans.get(i).getPName());
        }
        topPrice1.setText(String.valueOf(headListBeans.get(0).getPrice()));
        topSellNum1.setText(String.valueOf(headListBeans.get(0).getSaleNum()) + "人购买");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.top_ll_1, R.id.top_ll_2, R.id.top_ll_3, R.id.top_ll_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_ll_1:
                goGooddetail(String.valueOf(headListBeans.get(0).getPId()), false);
                break;
            case R.id.top_ll_2:
                goGooddetail(String.valueOf(headListBeans.get(1).getPId()), false);
                break;
            case R.id.top_ll_3:
                goGooddetail(String.valueOf(headListBeans.get(2).getPId()), false);
                break;
            case R.id.top_ll_4:
                goGooddetail(String.valueOf(headListBeans.get(3).getPId()), false);
                break;
        }
    }


    public void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityListActivity.this.initData();
            }
        });
    }


    /**
     * 跳轉到商品詳情頁面
     *
     * @param pid
     */
    private void goGooddetail(String pid, boolean panic) {

        Bundle bundle = new Bundle();
        bundle.putString("pid", pid);
        bundle.putBoolean("panic", panic);
        BaseActivity.navigate(ActivityListActivity.this, GoodsDetailsActivity.class, bundle);
    }
}
