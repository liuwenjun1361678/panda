package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.ExCouponsCenterAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.fragment.ExFragment;
import com.padacn.xmgoing.model.CouponsCenterBean;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.GlideImageLoader;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠券中心
 * Created by 46009 on 2018/5/4.
 */

public class ExCouponsCenterActivity extends BaseActivity {

    @BindView(R.id.coupons_center_bar)
    CustomToolBar couponsCenterBar;
    @BindView(R.id.coupons_center_recyclerView)
    RecyclerView couponsCenterRecyclerView;
    @BindView(R.id.coupons_center_rf)
    RefreshLayout couponsCenterRf;
    private Banner exCenterBanner;
    private ExCouponsCenterAdapter exCouponsCenterAdapter;

    private View view;

    private List<CouponsCenterBean.BannerBean> bannerBeanList;
    private List<CouponsCenterBean.DataBean> dataBeanList;
    private List<CouponsCenterBean.DataBean> dataBeanListTotal;

    private List<String> bannerImages;

    private int currpage = 0;
    private int pageCount = 0;

    private LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_coupons_center;
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
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(couponsCenterRf, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                ExCouponsCenterActivity.this.setRetryEvent(retryView);
            }
        });
        couponsCenterBar.setStyle("领券中心", Color.parseColor("#FFFFFF"));
        bannerBeanList = new ArrayList<>();
        dataBeanListTotal = new ArrayList<>();
        bannerImages = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponsCenterRecyclerView.setLayoutManager(layoutManager);
        exCouponsCenterAdapter = new ExCouponsCenterAdapter(R.layout.item_ex_coupons_center, dataBeanListTotal);
        View headerView = getHeaderView();
        exCouponsCenterAdapter.addHeaderView(headerView);
        couponsCenterRecyclerView.setAdapter(exCouponsCenterAdapter);
        exCouponsCenterAdapter.setPreLoadNumber(3);
        exCouponsCenterAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                couponsCenterRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currpage + 1 >= pageCount) {
                            exCouponsCenterAdapter.loadMoreEnd();
                        } else {
                            currpage++;
                            getData(currpage, false);

                        }
                    }
                }, 2000);
            }
        }, couponsCenterRecyclerView);


        exCouponsCenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("sellerName", dataBeanListTotal.get(position).getSellerName());
                bundle.putInt("couponType", dataBeanListTotal.get(position).getCouponType());
                bundle.putString("typeName", dataBeanListTotal.get(position).getTypeName());
                bundle.putString("reduction", String.valueOf(dataBeanListTotal.get(position).getReduction()));
                bundle.putString("term", String.valueOf(dataBeanListTotal.get(position).getTerm()));
                bundle.putBoolean("flashUse", dataBeanListTotal.get(position).isFlashUse());
                bundle.putString("useTime", String.valueOf(dataBeanListTotal.get(position).getUseTime()));
                bundle.putString("cid", String.valueOf(dataBeanListTotal.get(position).getCouponId()));
                bundle.putString("voidTime", String.valueOf(dataBeanListTotal.get(position).getVoidTime()));
                BaseActivity.navigate(ExCouponsCenterActivity.this, CouponsDetailActivity.class, bundle);
            }
        });

        exCouponsCenterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!SharePreferencesUtil.getBooleanSharePreferences(ExCouponsCenterActivity.this, Constans.isAlreadyLogin, false)) {
                    BaseActivity.navigate(ExCouponsCenterActivity.this, LoginActivity.class);
                    return;
                }

                OkGo.<String>post(ServiceApi.gain)
                        .tag(this)
                        .params("cid", String.valueOf(dataBeanListTotal.get(position).getCouponId()))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());
                                    int result = jsonObject.getInt("result");
                                    String msg = jsonObject.getString("msg");
                                    if (result == 1) {
                                        RxToast.success(msg, 500);
                                    } else {
                                        RxToast.error(msg, 500);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    private View getHeaderView() {
        view = getLayoutInflater().inflate(R.layout.activity_coupons_center_banner, (ViewGroup) couponsCenterRecyclerView.getParent(), false);
        exCenterBanner = view.findViewById(R.id.ex_center_banner);
        for (int i = 0; i < bannerBeanList.size(); i++) {
            bannerImages.add(bannerBeanList.get(i).getBannerPic());
        }
        exCenterBanner.setImages(bannerImages)
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setDelayTime(5000)
                .start();

        exCenterBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
                switch (bannerBeanList.get(position).getBannerType()) {
                    case 0:
                        bundle.putString("pid", String.valueOf(bannerBeanList.get(position).getBannerId()));
                        bundle.putBoolean("panic", false);
                        BaseActivity.navigate(ExCouponsCenterActivity.this, GoodsDetailsActivity.class, bundle);
                        break;
                    case 1:
                        bundle.putString("strateDetailId", String.valueOf(bannerBeanList.get(position).getBannerId()));
                        BaseActivity.navigate(ExCouponsCenterActivity.this, StrategdetailActivity.class, bundle);
                }
            }
        });

        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getData(0, true);
    }

    private void getData(int page, final boolean isHead) {
        dataBeanList = new ArrayList<>();
        dataBeanList.clear();
        String head;
        if (isHead) {
            head = "1";
        } else {
            head = "0";
        }
        OkGo.<String>post(ServiceApi.COUPON_LIST)
                .tag(this)
                .params("pageNum", String.valueOf(page))
                .params("pageSize", Constans.COMMONPAGE)
                .params("isHead", head)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                CouponsCenterBean couponsCenterBean = gson.fromJson(s, CouponsCenterBean.class);
                                if (couponsCenterBean.getResult() == 1) {
                                    if (!RxDataTool.isEmpty(couponsCenterBean.getData())) {
                                        mLoadingAndRetryManager.showContent();
                                        exCouponsCenterAdapter.loadMoreComplete();
                                        pageCount = couponsCenterBean.getPageCount();
                                        if (isHead) {
                                            bannerBeanList.clear();
                                            bannerBeanList.addAll(couponsCenterBean.getBanner());
                                            dataBeanListTotal.addAll(couponsCenterBean.getData());
                                            showBanner();
                                            exCouponsCenterAdapter.setNewData(dataBeanListTotal);
                                        } else {
                                            dataBeanList.addAll(couponsCenterBean.getData());
                                            exCouponsCenterAdapter.addData(dataBeanList);
                                        }
                                    } else {
                                        mLoadingAndRetryManager.showEmpty();
                                    }

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


    private void showBanner() {
        bannerImages.clear();
        exCenterBanner = view.findViewById(R.id.ex_center_banner);
        for (int i = 0; i < bannerBeanList.size(); i++) {
            bannerImages.add(bannerBeanList.get(i).getBannerPic());
        }
        exCenterBanner.setImages(bannerImages)

                .setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
                .start();

    }

    @Override
    protected void setListener() {
        super.setListener();
        couponsCenterRf.setRefreshHeader(new RefreshView(this));
        couponsCenterRf.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_25));
        couponsCenterRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currpage = 0;
                        dataBeanListTotal.clear();
                        getData(0, true);
                        couponsCenterRf.finishRefresh();
                    }
                }, 2000);

            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExCouponsCenterActivity.this.initData();
            }
        });
    }
}
