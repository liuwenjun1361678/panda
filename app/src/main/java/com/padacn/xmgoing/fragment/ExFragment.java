package com.padacn.xmgoing.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.CouponsDetailActivity;
import com.padacn.xmgoing.activity.ExCouponsCenterActivity;
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.activity.StrategdetailActivity;
import com.padacn.xmgoing.adapter.ExBottomListAdapter;
import com.padacn.xmgoing.adapter.ExCouponsAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.PreferentialBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.GlideImageLoader;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
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
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 46009 on 2018/4/26.
 */

public class ExFragment extends BaseFragment {

    private static final String TAG = "ExFragment";
    @BindView(R.id.ex_banner)
    Banner exBanner;
    @BindView(R.id.ex_top_bar)
    Toolbar exTopBar;
    @BindView(R.id.ex_toolbar_layout)
    CollapsingToolbarLayout exToolbarLayout;
    @BindView(R.id.ex_app_bar)
    AppBarLayout exAppBar;
    @BindView(R.id.fragment_ex_title)
    TextView fragmentExTitle;
    Unbinder unbinder;
    @BindView(R.id.ex_coupons_rl)
    RecyclerView exCouponsRl;
    @BindView(R.id.ex_list_rl)
    RecyclerView exListRl;
    @BindView(R.id.look_more_coupons)
    RelativeLayout lookMoreCoupons;
    //    @BindView(R.id.item_detail_container)
//    NestedScrollView itemDetailContainer;
    @BindView(R.id.ex_shop_image_1)
    ImageView exShopImage1;
    @BindView(R.id.ex_shop_title_1)
    TextView exShopTitle1;
    @BindView(R.id.ex_shop_price_1)
    TextView exShopPrice1;
    @BindView(R.id.ex_shop_num_1)
    TextView exShopNum1;
    @BindView(R.id.ex_shop_image_2)
    ImageView exShopImage2;
    @BindView(R.id.ex_shop_title_2)
    TextView exShopTitle2;
    @BindView(R.id.ex_shop_price_2)
    TextView exShopPrice2;
    @BindView(R.id.ex_shop_num_2)
    TextView exShopNum2;
    @BindView(R.id.ex_shop_image_3)
    ImageView exShopImage3;
    @BindView(R.id.ex_shop_title_3)
    TextView exShopTitle3;
    @BindView(R.id.ex_shop_price_3)
    TextView exShopPrice3;
    @BindView(R.id.ex_shop_num_3)
    TextView exShopNum3;
    @BindView(R.id.ex_refreshLayout)
    RefreshLayout exRefreshLayout;

    private RelativeLayout item_ex_coupons_parent;
    //banner
    private List<PreferentialBean.DataBean.BannersBean> bannersBeanList;
    //优惠卷
    private List<PreferentialBean.DataBean.CouponsBean> couponsBeanList;

    private List<PreferentialBean.DataBean.ReferenceProductBean> referenceProductBeanList;
    private List<PreferentialBean.DataBean.ReferenceProductBean> referenceProductBeanListTop;

    private List<PreferentialBean.DataBean.ReferenceProductBean> referenceTop;

    private List<PreferentialBean.DataBean.ReferenceProductBean> referenceProductBeanListBottom;
    private List<PreferentialBean.DataBean.ReferenceProductBean> referenceProductBeanListBottomTotal;
    private int pageCount;
    //当前页数
    private int currPage = 0;
    //秒杀
    private List<String> bannerImages;
    private ExCouponsAdapter exCouponsAdapter;
    private ExBottomListAdapter exBottomListAdapter;

    private ImageView[] arrExImage;
    private TextView[] arrExTitle;
    private TextView[] arrExPrice;
    private TextView[] arrExNum;


    private LoadingAndRetryManager mLoadingAndRetryManager;

    public static ExFragment newInstance() {
        Bundle args = new Bundle();
        ExFragment fragment = new ExFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_ex;
    }


    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getData(0, true);
    }


    //获取特惠数据
    private void getData(int page, final boolean isHead) {
        referenceProductBeanList = new ArrayList<>();
        referenceProductBeanList.clear();
        referenceProductBeanListBottom = new ArrayList<>();
        referenceProductBeanListBottom.clear();
        String head;
        if (isHead) {
            head = "1";
        } else {
            head = "0";
        }
        OkGo.<String>post(ServiceApi.PRODUCT_REFERENCE)
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
                            mLoadingAndRetryManager.showContent();
                            if (result == 1) {
                                Gson gson = new Gson();
                                PreferentialBean preferentialBean = gson.fromJson(s, PreferentialBean.class);
                                if (preferentialBean.getResult() == 1) {
                                    pageCount = preferentialBean.getPageCount();
                                    exBottomListAdapter.loadMoreComplete();
                                    if (isHead) {
                                        bannersBeanList.clear();
                                        couponsBeanList.clear();
                                        bannerImages.clear();
                                        bannersBeanList.addAll(preferentialBean.getData().getBanners());
                                        couponsBeanList.addAll(preferentialBean.getData().getCoupons());
                                        referenceProductBeanList.addAll(preferentialBean.getData().getReferenceProduct());
                                        showBanner();
                                        showCoupons();
                                    } else {
                                        referenceProductBeanList.addAll(preferentialBean.getData().getReferenceProduct());
                                    }
                                    showData(isHead);
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

    private void showCoupons() {
        exCouponsAdapter.setNewData(couponsBeanList);
    }

    /**
     * 显示banner
     */
    private void showBanner() {
        for (int i = 0; i < bannersBeanList.size(); i++) {
            bannerImages.add(bannersBeanList.get(i).getBannerPic());
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
                switch (bannersBeanList.get(position).getBannerType()) {
                    case 0:
                        bundle.putString("pid", String.valueOf(bannersBeanList.get(position).getBannerId()));
                        bundle.putBoolean("panic", false);
                        BaseActivity.navigate(getContext(), GoodsDetailsActivity.class, bundle);
                        break;
                    case 1:
                        bundle.putString("strateDetailId", String.valueOf(bannersBeanList.get(position).getBannerId()));
                        BaseActivity.navigate(getContext(), StrategdetailActivity.class, bundle);
                }
            }
        });
    }

    //展示数据
    private void showData(boolean isHead) {
        if (isHead) {
            referenceTop = new ArrayList<>();
            referenceTop.clear();
            for (int i = 0; i < 3; i++) {
                referenceTop.add(referenceProductBeanList.get(i));
                CommonUtil.loadImage(getContext(), referenceProductBeanList.get(i).getPics().get(0).getPath(), arrExImage[i]);
                arrExTitle[i].setText(referenceProductBeanList.get(i).getPName());
                if (referenceProductBeanList.get(i).isReference()) {
                    arrExPrice[i].setText(getResources().getString(R.string.moneny_string) + " " + StringUtil.replaceString(String.valueOf(referenceProductBeanList.get(i).getReferencePrice())));
                } else {
                    arrExPrice[i].setText(getResources().getString(R.string.moneny_string) + " " + StringUtil.replaceString(String.valueOf(referenceProductBeanList.get(i).getPrice())));
                }
                arrExNum[i].setText(referenceProductBeanList.get(i).getSaleNum() + "人购买");

                final int finalI = i;
                arrExImage[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goGooddetail(String.valueOf(referenceTop.get(finalI).getPId()));
                    }
                });
            }

            for (int i = 3; i < referenceProductBeanList.size(); i++) {
                referenceProductBeanListBottom.add(referenceProductBeanList.get(i));
            }
            exBottomListAdapter.setNewData(referenceProductBeanListBottom);
            referenceProductBeanListBottomTotal.addAll(referenceProductBeanListBottom);
        } else {
            referenceProductBeanListBottomTotal.addAll(referenceProductBeanList);
            exBottomListAdapter.addData(referenceProductBeanList);

        }
        exBottomListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goGooddetail(String.valueOf(referenceProductBeanListBottomTotal.get(position).getPId()));
            }
        });


        exBottomListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                exListRl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            exBottomListAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getData(currPage, false);
                        }
                    }
                }, 1000);
            }
        }, exListRl);

    }


    /**
     * 跳轉到商品詳情頁面
     *
     * @param pid
     */
    private void goGooddetail(String pid) {

        Bundle bundle = new Bundle();
        bundle.putString("pid", pid);
        bundle.putBoolean("panic", false);
        BaseActivity.navigate(getContext(), GoodsDetailsActivity.class, bundle);
    }

    @Override
    protected void initView() {
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(exRefreshLayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                ExFragment.this.setRetryEvent(retryView);
            }
        });
        arrExImage = new ImageView[]{exShopImage1, exShopImage2, exShopImage3};
        arrExTitle = new TextView[]{exShopTitle1, exShopTitle2, exShopTitle3};
        arrExPrice = new TextView[]{exShopPrice1, exShopPrice2, exShopPrice3};
        arrExNum = new TextView[]{exShopNum1, exShopNum2, exShopNum3};
        bannersBeanList = new ArrayList<>();
        couponsBeanList = new ArrayList<>();
        referenceProductBeanListTop = new ArrayList<>();
        referenceProductBeanListBottomTotal = new ArrayList<>();
        bannerImages = new ArrayList<>();
        //活动横向滚动
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        exCouponsRl.setLayoutManager(layoutManager);
        exCouponsAdapter = new ExCouponsAdapter(R.layout.item_ex_coupons, couponsBeanList);
        exCouponsRl.setAdapter(exCouponsAdapter);


        //底部垂直滑动
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        exListRl.setLayoutManager(layoutManager1);
        exBottomListAdapter = new ExBottomListAdapter(R.layout.item_ex_list, referenceProductBeanListTop);
        exListRl.setAdapter(exBottomListAdapter);

    }


    @Override
    protected void setListener() {
        super.setListener();
        exAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, final int verticalOffset) {
                exAppBar.post(new Runnable() {
                    @Override
                    public void run() {
                        float alpha = (float) (-verticalOffset) / exBanner.getHeight() * 255;
                        if (verticalOffset > -exBanner.getHeight() / 2) {
                            exTopBar.setAlpha(alpha / 255);
                        } else {
                            fragmentExTitle.setText("特惠");
                            exTopBar.setAlpha(1);
                        }
                    }
                });

            }
        });

        exCouponsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("sellerName", couponsBeanList.get(position).getSellerName());
                bundle.putInt("couponType", couponsBeanList.get(position).getCouponType());
                bundle.putString("typeName", couponsBeanList.get(position).getTypeName());
                bundle.putString("reduction", String.valueOf(couponsBeanList.get(position).getReduction()));
                bundle.putString("term", String.valueOf(couponsBeanList.get(position).getTerm()));
                bundle.putBoolean("flashUse", couponsBeanList.get(position).isFlashUse());
                bundle.putString("useTime", String.valueOf(couponsBeanList.get(position).getUseTime()));
                bundle.putString("cid", String.valueOf(couponsBeanList.get(position).getCouponId()));
                bundle.putString("voidTime", String.valueOf(couponsBeanList.get(position).getVoidTime()));
                BaseActivity.navigate(getContext(), CouponsDetailActivity.class, bundle);

            }
        });


        exRefreshLayout.setRefreshHeader(new RefreshView(getContext()));
        exRefreshLayout.setHeaderHeight(getResources().getDimension(R.dimen.dp_25));
        exRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currPage = 0;
                        referenceProductBeanListBottomTotal.clear();
                        getData(0, true);
                        exRefreshLayout.finishRefresh();
                    }
                }, 1000);

            }

        });
     /*   exRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage > pageCount) {
                            exRefreshLayout.finishLoadmoreWithNoMoreData();
                            exRefreshLayout.finishRefresh();
                        } else {
                            currPage++;
                            getData(currPage, false);

                        }
                    }
                }, 1000);
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.look_more_coupons)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.look_more_coupons:
                BaseActivity.navigate(getContext(), ExCouponsCenterActivity.class);
                break;
        }
    }


    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExFragment.this.initData();
            }
        });
    }
}
