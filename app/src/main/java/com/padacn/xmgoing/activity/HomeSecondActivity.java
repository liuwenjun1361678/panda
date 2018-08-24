package com.padacn.xmgoing.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ldf.calendar.Const;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.HomeSecondBottomAdapter;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.HomeSecondBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.view.GlideImageLoader;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class HomeSecondActivity extends BaseActivity {

    private static final String TAG = "HomeSecondActivity";
    @BindView(R.id.home_second_banner)
    Banner homeSecondBanner;
    @BindView(R.id.home_second_title)
    TextView homeSecondTitle;
    @BindView(R.id.home_second_top_bar)
    Toolbar homeSecondTopBar;
    @BindView(R.id.home_second_appbar)
    AppBarLayout homeSecondAppbar;
    @BindView(R.id.home_second_image_1)
    ImageView homeSecondImage1;
    @BindView(R.id.home_second_text_1)
    TextView homeSecondText1;
    @BindView(R.id.home_second_image_2)
    ImageView homeSecondImage2;
    @BindView(R.id.home_second_text_2)
    TextView homeSecondText2;
    @BindView(R.id.home_second_image_3)
    ImageView homeSecondImage3;
    @BindView(R.id.home_second_text_3)
    TextView homeSecondText3;
    @BindView(R.id.home_second_image_4)
    ImageView homeSecondImage4;
    @BindView(R.id.home_second_text_4)
    TextView homeSecondText4;
    @BindView(R.id.home_second_rl)
    RecyclerView homeSecondRl;
    @BindView(R.id.home_second_top_rl)
    RelativeLayout homeSecondTopRl;
    @BindView(R.id.home_second_back_rl)
    RelativeLayout homeSecondBackRl;
    @BindView(R.id.home_second_r1)
    RelativeLayout homeSecondR1;
    @BindView(R.id.home_second_r2)
    RelativeLayout homeSecondR2;
    @BindView(R.id.home_second_r3)
    RelativeLayout homeSecondR3;
    @BindView(R.id.home_second_r4)
    RelativeLayout homeSecondR4;
    @BindView(R.id.home_second_smartRefreshLayout)
    RefreshLayout homeSecondSmartRefreshLayout;


    //底部下拉list总共頁数
    private int pageCount;
    //当前页数
    private int currPage = 0;
    private HomeSecondBottomAdapter homeSecondBottomAdapter;
    private String currParentId;
    private String getCurrParentName;

    //頂部二級菜单栏
    private List<HomeSecondBean.DataBean.TypesBean> typesBeanList;

    //顶部banner
    private List<HomeSecondBean.DataBean.BannerBean> bannerBeanList;
    private List<String> bannerImages;
    private TextView[] arrSecond;
    private ImageView[] arrSecondImage;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private List<HomeSecondBean.DataBean.ProductsBean> productsBeanList;

    private List<HomeSecondBean.DataBean.ProductsBean> productsBeanListTotal;

    private int[] arr1 = new int[]{R.mipmap.quwan_qinzihuodong, R.mipmap.quwan_kexueshiyan, R.mipmap.quwan_tiyanshijian, R.mipmap.quwan_yizhikeji};

    private int[] arr2 = new int[]{R.mipmap.yingdi_xingqu_peiyang, R.mipmap.yingdi_junshi_shengcun, R.mipmap.yingdi_huwai_tuozhan, R.mipmap.yingdi_tineng_yundong};

    private int[] arr3 = new int[]{R.mipmap.yanxue_yanxue_lvxing, R.mipmap.yanxue_lvyou_dujia, R.mipmap.yanxue_qinzi_jiudian, R.mipmap.yanxue_haiwai};

    private int[] arr4 = new int[]{R.mipmap.jingdian_youle, R.mipmap.jingdian_jingdian_mingshen, R.mipmap.jingdian_yanchu_zhanlan, R.mipmap.jingdian_bowuguan};

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home_second;
    }

    @Override
    protected void setListener() {
        super.setListener();
        homeSecondSmartRefreshLayout.setRefreshHeader(new RefreshView(this));
        homeSecondSmartRefreshLayout.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_25));
        homeSecondSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currPage = 0;
                        productsBeanListTotal.clear();
                        getHomeSecondData("1", 0);
                        homeSecondSmartRefreshLayout.finishRefresh();

                    }
                }, 2000);
            }

        });
    }

    @Override
    protected void initView() {
        super.initView();
        bannerImages = new ArrayList<>();
        typesBeanList = new ArrayList<>();
        bannerBeanList = new ArrayList<>();
        productsBeanListTotal = new ArrayList<>();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(homeSecondSmartRefreshLayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                HomeSecondActivity.this.setRetryEvent(retryView);
            }
        });

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
                    homeSecondTitle.setAlpha(alpha / 255);
                    homeSecondTopRl.getBackground().setAlpha((int) alpha);
                } else {
                    homeSecondTitle.setAlpha(1);
                    homeSecondTopRl.getBackground().setAlpha(255);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeSecondRl.setLayoutManager(layoutManager);
        homeSecondBottomAdapter = new HomeSecondBottomAdapter(R.layout.item_home_second_bottom, productsBeanListTotal);
//        exCouponsCenterAdapter.addHeaderView(headerView);
        homeSecondRl.setAdapter(homeSecondBottomAdapter);

        homeSecondBottomAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                homeSecondRl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            homeSecondBottomAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getHomeSecondData("0", currPage);
                        }
                    }
                }, 2000);
            }
        }, homeSecondRl);
    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        Bundle bundle = this.getIntent().getExtras();
        currParentId = bundle.getString("parentId");
        getCurrParentName = bundle.getString("parentName");
        homeSecondTitle.setText(getCurrParentName);
        getHomeSecondData("1", 0);
    }

    private void getHomeSecondData(final String isHead, int currPe) {
        productsBeanList = new ArrayList<>();
        if (currParentId != null) {
            OkGo.<String>post(ServiceApi.HOME_SECOND_URL)
                    .tag(this)
                    .headers("Area", Constans.Area)
                    .params("typeParentId", currParentId)
                    .params("isHead", isHead)
                    .params("pageNum", String.valueOf(currPe))
                    .params("pageSize", Constans.COMMONPAGE)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String s = response.body();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int result = jsonObject.getInt("result");
                                if (result == 1) {
                                    Gson gson = new Gson();
                                    HomeSecondBean homeSecondBean = gson.fromJson(s, HomeSecondBean.class);
                                    if (homeSecondBean.getResult() == 1) {
                                        mLoadingAndRetryManager.showContent();
                                        if (isHead.equals("1")) {
                                            bannerImages.clear();
                                            typesBeanList.clear();
                                            bannerBeanList.clear();
                                            productsBeanList.clear();
                                            if (homeSecondBean.getData().getTypes() != null) {
                                                typesBeanList.addAll(homeSecondBean.getData().getTypes());
                                            }
                                            if (homeSecondBean.getData().getBanner() != null) {
                                                bannerBeanList.addAll(homeSecondBean.getData().getBanner());
                                            }
                                            if (homeSecondBean.getData().getProducts() != null) {
                                                productsBeanList.addAll(homeSecondBean.getData().getProducts());
                                                productsBeanListTotal.addAll(homeSecondBean.getData().getProducts());
                                            }
                                            if (homeSecondBean.getPageCount() != -1) {
                                                pageCount = homeSecondBean.getPageCount();
                                            }
                                            setBanner();
                                            setSecond();
                                            setProduct();
                                        } else if (isHead.equals("0")) {
                                            homeSecondBottomAdapter.loadMoreComplete();
                                            productsBeanList.addAll(homeSecondBean.getData().getProducts());
                                            pageCount = homeSecondBean.getPageCount();
                                            homeSecondBottomAdapter.addData(productsBeanList);
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

    }

    /**
     * 底部商品列表
     */
    private void setProduct() {
        homeSecondBottomAdapter.setNewData(productsBeanListTotal);
        homeSecondBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("pid", String.valueOf(productsBeanListTotal.get(position).getPId()));
                bundle.putBoolean("panic", false);
                BaseActivity.navigate(HomeSecondActivity.this, GoodsDetailsActivity.class, bundle);
            }
        });
    }


    private void setBanner() {
        for (int i = 0; i < bannerBeanList.size(); i++) {
            bannerImages.add(bannerBeanList.get(i).getBannerPic());
        }
        homeSecondBanner.setImages(bannerImages)
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setDelayTime(5000)
                .start();

        homeSecondBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
                switch (bannerBeanList.get(position).getBannerType()) {
                    case 0:
                        bundle.putString("pid", String.valueOf(bannerBeanList.get(position).getBannerId()));
                        bundle.putBoolean("panic", false);
                        BaseActivity.navigate(HomeSecondActivity.this, GoodsDetailsActivity.class, bundle);
                        break;
                    case 1:
                        bundle.putString("strateDetailId", String.valueOf(bannerBeanList.get(position).getBannerId()));
                        BaseActivity.navigate(HomeSecondActivity.this, StrategdetailActivity.class, bundle);
                }
            }
        });
    }

    /**
     * 设置二级
     */
    private void setSecond() {
        arrSecondImage = new ImageView[]{homeSecondImage1, homeSecondImage2, homeSecondImage3, homeSecondImage4};
        arrSecond = new TextView[]{homeSecondText1, homeSecondText2, homeSecondText3, homeSecondText4};
        for (int i = 0; i < arrSecond.length; i++) {
            arrSecond[i].setText(typesBeanList.get(i).getTypeName());
        }
        switch (getCurrParentName) {
            case "趣玩·亲子":
                showTopTitle(arr1);
                break;
            case "营地·教育":
                showTopTitle(arr2);
                break;
            case "研学·旅行":
                showTopTitle(arr3);
                break;
            case "景点·乐园":
                showTopTitle(arr4);
                break;
        }

    }

    private void showTopTitle(int[] arr) {
        for (int i = 0; i < arrSecond.length; i++) {
            arrSecondImage[i].setImageResource(arr[i]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.home_second_r1, R.id.home_second_r2, R.id.home_second_r3, R.id.home_second_r4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_second_r1:
                goToHomeThree(0);
                break;
            case R.id.home_second_r2:
                goToHomeThree(1);
                break;
            case R.id.home_second_r3:
                goToHomeThree(2);
                break;
            case R.id.home_second_r4:
                goToHomeThree(3);
                break;
        }
    }

    /**
     * 跳转到三级
     */
    private void goToHomeThree(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("proTypeId", typesBeanList.get(position).getProTypeId() + "");
        bundle.putString("typeName", typesBeanList.get(position).getTypeName());
        BaseActivity.navigate(HomeSecondActivity.this, HomeThreeActivity1.class, bundle);
    }


    public void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeSecondActivity.this.initData();
            }
        });
    }
}
