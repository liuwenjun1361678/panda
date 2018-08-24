package com.padacn.xmgoing.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.google.gson.Gson;
import com.jkb.rollinglayout.RollingLayout;
import com.jkb.rollinglayout.RollingLayoutAction;
import com.ldf.calendar.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.ActivityListActivity;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.activity.HomeSecondActivity;
import com.padacn.xmgoing.activity.HotSearchActivity;
import com.padacn.xmgoing.activity.MessageActivity;
import com.padacn.xmgoing.activity.StrategListActivity;
import com.padacn.xmgoing.activity.StrategdetailActivity;
import com.padacn.xmgoing.adapter.HeadlineAdapter;
import com.padacn.xmgoing.adapter.HomeStrategiesAdapter;
import com.padacn.xmgoing.adapter.HomeTabAdapter;
import com.padacn.xmgoing.adapter.ListActivityAdapter;
import com.padacn.xmgoing.adapter.MainTabListAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.HomeBean;
import com.padacn.xmgoing.model.TabEntity;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.EvenUtil;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.GlideImageLoader;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vondear.rxtools.RxDataTool;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.ysr.citypicker.CityPickerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.home_smartRefreshLayout)
    RefreshLayout homeSmartRefreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
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
    @BindView(R.id.main_tab)
    RecyclerView mainTab;
    @BindView(R.id.list_activity_rl)
    RecyclerView listActivityRl;
    @BindView(R.id.city_picker_ll)
    LinearLayout cityPickerLl;
    Unbinder unbinder;
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.home_top_bar_search)
    EditText homeTopBarSearch;
    @BindView(R.id.home_top_bar_city)
    TextView homeTopBarCity;
    @BindView(R.id.seconds_kill_title_1)
    TextView secondsKillTitle1;
    @BindView(R.id.seconds_kill_rl_1)
    RelativeLayout secondsKillRl1;
    @BindView(R.id.seconds_kill_price_1)
    TextView secondsKillPrice1;
    @BindView(R.id.seconds_kill_old_price_1)
    TextView secondsKillOldPrice1;
    @BindView(R.id.seconds_kill_title_2)
    TextView secondsKillTitle2;
    @BindView(R.id.seconds_kill_rl_2)
    RelativeLayout secondsKillRl2;
    @BindView(R.id.seconds_kill_price_2)
    TextView secondsKillPrice2;
    @BindView(R.id.seconds_kill_old_price_2)
    TextView secondsKillOldPrice2;
    @BindView(R.id.seconds_kill_title_4)
    TextView secondsKillTitle4;
    @BindView(R.id.seconds_kill_rl_4)
    RelativeLayout secondsKillRl4;
    @BindView(R.id.seconds_kill_price_4)
    TextView secondsKillPrice4;
    @BindView(R.id.seconds_kill_old_price_4)
    TextView secondsKillOldPrice4;
    @BindView(R.id.seconds_kill_title_3)
    TextView secondsKillTitle3;
    @BindView(R.id.seconds_kill_rl_3)
    RelativeLayout secondsKillRl3;
    @BindView(R.id.seconds_kill_price_3)
    TextView secondsKillPrice3;
    @BindView(R.id.seconds_kill_old_price_3)
    TextView secondsKillOldPrice3;
    @BindView(R.id.seconds_kill_title_5)
    TextView secondsKillTitle5;
    @BindView(R.id.seconds_kill_rl_5)
    RelativeLayout secondsKillRl5;
    @BindView(R.id.seconds_kill_price_5)
    TextView secondsKillPrice5;
    @BindView(R.id.seconds_kill_old_price_5)
    TextView secondsKillOldPrice5;
    Unbinder unbinder1;
    @BindView(R.id.recommonProduct_image_1)
    ImageView recommonProductImage1;
    @BindView(R.id.recommonProduct_title_1)
    TextView recommonProductTitle1;
    @BindView(R.id.recommonProduct_price_1)
    TextView recommonProductPrice1;
    @BindView(R.id.recommonProduct_old_price_1)
    TextView recommonProductOldPrice1;
    @BindView(R.id.recommonProduct_image_2)
    ImageView recommonProductImage2;
    @BindView(R.id.recommonProduct_title_2)
    TextView recommonProductTitle2;
    @BindView(R.id.recommonProduct_price_2)
    TextView recommonProductPrice2;
    @BindView(R.id.recommonProduct_old_price_2)
    TextView recommonProductOldPrice2;
    @BindView(R.id.recommonProduct_image_3)
    ImageView recommonProductImage3;
    @BindView(R.id.recommonProduct_title_3)
    TextView recommonProductTitle3;
    @BindView(R.id.recommonProduct_price_3)
    TextView recommonProductPrice3;
    @BindView(R.id.recommonProduct_old_price_3)
    TextView recommonProductOldPrice3;
    @BindView(R.id.back_top_image)
    ImageView backTopImage;
    @BindView(R.id.advert_image)
    ImageView advertImage;
    @BindView(R.id.main_bottom_recyclerView)
    RecyclerView mainBottomRecyclerView;
    @BindView(R.id.home_strategies_title_1)
    TextView homeStrategiesTitle1;
    @BindView(R.id.home_strategies_1ike_1)
    TextView homeStrategies1ike1;
    @BindView(R.id.home_strategies_Image_1)
    ImageView homeStrategiesImage1;
    @BindView(R.id.home_strategies_city_2)
    TextView homeStrategiesCity2;
    @BindView(R.id.home_strategies_title_2)
    TextView homeStrategiesTitle2;
    @BindView(R.id.home_strategies_city_3)
    TextView homeStrategiesCity3;
    @BindView(R.id.home_strategies_title_3)
    TextView homeStrategiesTitle3;
    @BindView(R.id.home_strategies_city_4)
    TextView homeStrategiesCity4;
    @BindView(R.id.home_strategies_title_4)
    TextView homeStrategiesTitle4;
    @BindView(R.id.home_strategies_Image_2)
    RelativeLayout homeStrategiesImage2;
    @BindView(R.id.home_strategies_Image_3)
    RelativeLayout homeStrategiesImage3;
    @BindView(R.id.home_strategies_Image_4)
    RelativeLayout homeStrategiesImage4;
    @BindView(R.id.home_gonglue_recyclerView)
    RecyclerView homeGonglueRecyclerView;
    @BindView(R.id.home_coordinatorLayout)
    CoordinatorLayout homeCoordinatorLayout;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recommonProduct_go_more_ll)
    LinearLayout recommonProductGoMoreLl;
    @BindView(R.id.spike_time_hour)
    TextView spikeTimeHour;
    @BindView(R.id.spike_time_minute)
    TextView spikeTimeMinute;
    @BindView(R.id.spike_time_seconds)
    TextView spikeTimeSeconds;
    @BindView(R.id.kill_over_text)
    TextView killOverText;
    @BindView(R.id.kill_time_ll)
    LinearLayout killTimeLl;
    @BindView(R.id.home_top_bar_search_ll)
    LinearLayout homeTopBarSearchLl;
    @BindView(R.id.kill_ll_bg)
    LinearLayout killLlBg;
    @BindView(R.id.kill_ll)
    LinearLayout killLl;

    private CountDownTimer timer;
    private boolean sIsScrolling;
    private String currCity;
    private String currCityID;
    //底部下拉list总共頁数
    private int pageCount;
    //当前页数
    private int currPage = 0;
    private ArrayList<HomeBean.DataBean.PanicBuyingBean.LoginsBean> listPanicBuy;

    private RelativeLayout[] arrPanicBuyRl;
    private TextView[] arrPanicBuytitle;
    private TextView[] arrPanicBuyPrice;
    private TextView[] arrPanicBuyOldPrice;

    //recommonProduct  推荐活动
    private ArrayList<HomeBean.DataBean.RecommonProductBean> listRecommonProduct;

    //recommonProduct  推荐活动上方
    private ArrayList<HomeBean.DataBean.RecommonProductBean> listRecommonProductTop;
    private ArrayList<HomeBean.DataBean.RecommonProductBean> listRecommonProductBottom;


    private ImageView[] arrRecommonProductImage;
    private TextView[] arrRecommonProducttitle;
    private TextView[] arrRecommonProductprice;
    private TextView[] arrRecommonProductOldPirce;


    private ImageView[] arrHomeStrategiesRandom;
    //   strategiesRand 随机攻虐
    private ArrayList<HomeBean.DataBean.StrategiesRandBean> listStrategiesRand;
    // strategies 推荐攻虐
    private ArrayList<HomeBean.DataBean.StrategiesBean> listStrategies;
    private ArrayList<HomeBean.DataBean.StrategiesRandBean.PicsBeanX> picsBeanXArrayList;
    // listProduct 下拉列表
    private ArrayList<HomeBean.DataBean.ListProductBean> listProduct;
    private ArrayList<HomeBean.DataBean.ListProductBean> listProductTotal;


    private List<HomeBean.DataBean.TopLineBean> topLineBeanList;

    private ArrayList<HomeBean.DataBean.TypiesBean> listTypies;
    //banner
    private ArrayList<HomeBean.DataBean.BannersBean> listBanner;
    //广告
    private ArrayList<HomeBean.DataBean.AdvertBean> listAdver;
    private HomeBean.DataBean.AdvertBean advertBeanObject;
    private List<String> mTitles;
    private List<String> bannerImages;
    private HomeTabAdapter homeTabAdapter;
    private MainTabListAdapter mainTabListAdapter;
    private HomeStrategiesAdapter homeStrategiesAdapter;
    private ArrayList<CustomTabEntity> mTabEntities;
    //头条adapter
    private HeadlineAdapter headlineAdapter;
    private ListActivityAdapter listActivityAdapter;

    //首页定位城市页面的回调
    private static final int REQUEST_CODE_PICK_CITY = 233;
    //加載manager
    private LoadingAndRetryManager mLoadingAndRetryManager;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        listProductTotal = new ArrayList<>();
        listPanicBuy = new ArrayList<>();
        listBanner = new ArrayList<>();
        bannerImages = new ArrayList<>();
        listRecommonProduct = new ArrayList<>();
        listStrategiesRand = new ArrayList<>();
        listStrategies = new ArrayList<>();
        listProduct = new ArrayList<>();
        listTypies = new ArrayList<>();
        listAdver = new ArrayList<>();
        topLineBeanList = new ArrayList<>();
        mLoadingAndRetryManager.showLoading();
        getHomeData(true, 0);

    }

    private void getHomeData(final boolean isHead, int pageNum) {


        String head = null;
        if (isHead) {
            head = "1";
        } else {
            head = "0";
        }
        OkGo.<String>post(ServiceApi.HOME_URL)
                .tag(this)
                .params("isHead", head)
                .params("pageNum", String.valueOf(currPage))
                .params("pageNum", String.valueOf(pageNum))
                .params("pageSize", "10")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                if (isHead) {
                                    clearList();
                                    HomeBean homeBean = gson.fromJson(s, HomeBean.class);
                                    if (homeBean.getResult() == 1) {
                                        mLoadingAndRetryManager.showContent();
                                        pageCount = homeBean.getPageCount();
                                        if (homeBean.getData().getListProduct().size() > 0) {
                                            listProductTotal.addAll(homeBean.getData().getListProduct());
                                            setBootomList();
                                        }
                                        if (!RxDataTool.isEmpty(homeBean.getData().getTypies())) {
                                            listTypies.addAll(homeBean.getData().getTypies());
                                            setTab();
                                        }
                                        if (!RxDataTool.isEmpty(homeBean.getData().getPanicBuying())) {
                                            if (homeBean.getData().getPanicBuying().getLogins().size() > 0) {
                                                killLl.setVisibility(View.VISIBLE);
                                                listPanicBuy.addAll(homeBean.getData().getPanicBuying().getLogins());
                                                showPanicBuy(homeBean.getData().getPanicBuying());
                                                setPanicBuy();
                                            } else {
                                                killLl.setVisibility(View.GONE);
                                            }
                                        } else {
                                            killLl.setVisibility(View.GONE);
                                        }
                                        if (!RxDataTool.isEmpty(homeBean.getData().getBanners())) {
                                            listBanner.addAll(homeBean.getData().getBanners());
                                            setBanner();
                                        }
                                        if (!RxDataTool.isEmpty(homeBean.getData().getRecommonProduct())) {
                                            listRecommonProduct.addAll(homeBean.getData().getRecommonProduct());
                                            setRecommonProduct();
                                        }

                                        if (!RxDataTool.isEmpty(homeBean.getData().getStrategiesRand())) {
                                            listStrategiesRand.addAll(homeBean.getData().getStrategiesRand());
                                            setListStrategies();
                                        }
                                        if (homeBean.getData().getStrategies().size() > 0) {
                                            listStrategies.addAll(homeBean.getData().getStrategies());
                                            setStrategies();
                                        }
                                        if (!RxDataTool.isEmpty(homeBean.getData().getTopLine())) {
                                            topLineBeanList.addAll(homeBean.getData().getTopLine());
                                            setRoll();
                                        }
                                        if (!RxDataTool.isEmpty(homeBean.getData().getAdvert())) {
                                            String advertPic = homeBean.getData().getAdvert().getAdvertPic();
                                            String advertPid = String.valueOf(homeBean.getData().getAdvert().getAdvertId());
                                            setAdvert(advertPic, advertPid);
                                        }
                                    }
                                } else {
                                    mainTabListAdapter.loadMoreComplete();
                                    HomeBean homeBean1 = gson.fromJson(s, HomeBean.class);
                                    if (homeBean1.getResult() == 1) {
                                        if (homeBean1.getData().getListProduct().size() > 0) {
                                            listProduct.clear();
                                            listProduct.addAll(homeBean1.getData().getListProduct());
                                            mainTabListAdapter.addData(listProduct);
                                        }
                                    }
                                }
                            } else {
                                mLoadingAndRetryManager.showRetry();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onSuccess: " + isHead);

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mLoadingAndRetryManager.showRetry();
                    }
                });


    }

    /**
     * //底部list
     */
    private void setBootomList() {
        mainTabListAdapter = new MainTabListAdapter(R.layout.item_home_bottom, listProductTotal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainBottomRecyclerView.setLayoutManager(layoutManager);
        mainBottomRecyclerView.setAdapter(mainTabListAdapter);
        mainTabListAdapter.setPreLoadNumber(3);
        mainTabListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mainBottomRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            mainTabListAdapter.loadMoreEnd();
                            homeSmartRefreshLayout.resetNoMoreData();
                        } else {
                            currPage++;
                            getHomeData(false, currPage);
                            mainTabListAdapter.loadMoreComplete();
                        }
                    }
                }, 2000);
            }
        }, mainBottomRecyclerView);

        mainTabListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mainTabListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.main_bottom_cardView) {
                    goGooddetail(String.valueOf(listProductTotal.get(position).getPId()), false);
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (rollingdownUp != null) {
            if (hidden) {
                rollingdownUp.stopRolling();
            } else {
                rollingdownUp.startRolling();
            }
        }

    }

    /**
     * 设置滚动消息
     */
    private void setRoll() {
        //实时垂直滚动
        headlineAdapter = new HeadlineAdapter(getContext(), topLineBeanList);
        rollingdownUp.setAdapter(headlineAdapter);
        rollingdownUp.startRolling();
        rollingdownUp.addOnRollingChangedListener(new RollingLayoutAction.OnRollingChangedListener() {
            @Override
            public void onRollingChanged(View rollingLayout, int currentPosition, int sumPosition) {
            }
        });
    }

    private void clearList() {
        listProduct.clear();
        listTypies.clear();
        listPanicBuy.clear();
        listBanner.clear();
        bannerImages.clear();
        listRecommonProduct.clear();
        listStrategiesRand.clear();
        listStrategies.clear();
        topLineBeanList.clear();
    }


    private void setStrategies() {
        picsBeanXArrayList = new ArrayList<>();
        if (listStrategies.size() < 0) {
            return;
        }
        if (listStrategies.size() > 0) {
            homeStrategiesTitle1.setText(listStrategies.get(0).getStrategyName());
            homeStrategies1ike1.setText("发布者：" + listStrategies.get(0).getPublish());
            CommonUtil.loadImage(getContext(), listStrategies.get(0).getPics().get(0).getPath(), homeStrategiesImage1);
        }

        homeStrategiesImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goStrategdetail(String.valueOf(listStrategies.get(0).getStrategyId()));
            }
        });

        if (listStrategies.size() > 1) {
            homeStrategiesTitle2.setText(listStrategies.get(1).getStrategyName());
            homeStrategiesCity2.setText(listStrategies.get(1).getCity());
            CommonUtil.loadRlImage(getContext(), listStrategies.get(1).getPics().get(0).getPath(), homeStrategiesImage2, homeStrategiesImage2.getWidth(), homeStrategiesImage2.getHeight());
        }

        if (listStrategies.size() > 2) {
            homeStrategiesTitle3.setText(listStrategies.get(2).getStrategyName());
            homeStrategiesCity3.setText(listStrategies.get(2).getCity());
            CommonUtil.loadRlImage(getContext(), listStrategies.get(2).getPics().get(0).getPath(), homeStrategiesImage3, homeStrategiesImage3.getWidth(), homeStrategiesImage3.getHeight());
        }
        if (listStrategies.size() > 3) {
            homeStrategiesTitle4.setText(listStrategies.get(3).getStrategyName());
            homeStrategiesCity4.setText(listStrategies.get(3).getCity());
            CommonUtil.loadRlImage(getContext(), listStrategies.get(3).getPics().get(0).getPath(), homeStrategiesImage4, homeStrategiesImage4.getWidth(), homeStrategiesImage4.getHeight());
        }


    }

    /**
     * 攻略推荐
     */
    private void setListStrategies() {

        homeStrategiesAdapter = new HomeStrategiesAdapter(R.layout.item_home_activity_one, listStrategiesRand);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeGonglueRecyclerView.setLayoutManager(layoutManager);
        homeGonglueRecyclerView.setAdapter(homeStrategiesAdapter);
        homeStrategiesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goStrategdetail(String.valueOf(listStrategiesRand.get(position).getStrategyId()));
            }
        });

    }


    private void setTab() {
        mTabEntities = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("附近活动");
        for (int i = 0; i < listTypies.size() - 1; i++) {
            mTitles.add(listTypies.get(i).getTypeName());
        }
        //底部垂直滑动
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }

        homeTabAdapter = new HomeTabAdapter(R.layout.item_home_tab, mTitles);
        LinearLayoutManager layoutManagerTab = new LinearLayoutManager(getActivity());
        layoutManagerTab.setOrientation(LinearLayoutManager.HORIZONTAL);
        mainTab.setLayoutManager(layoutManagerTab);
        mainTab.setAdapter(homeTabAdapter);
        homeTabAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("parentId", String.valueOf(listTypies.get(position - 1).getProTypeId()));
                    bundle.putString("parentName", String.valueOf(listTypies.get(position - 1).getTypeName()));
                    BaseActivity.navigate(getContext(), HomeSecondActivity.class, bundle);
                }
            }
        });

    }

    /**
     * 广告
     */
    private void setAdvert(String url, final String advertPid) {
        CommonUtil.loadImage(getContext(), url, advertImage);
        advertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                goGooddetail(advertPid);
            }
        });

    }

    /**
     * 活動推薦
     */
    private void setRecommonProduct() {
        arrRecommonProductImage = new ImageView[]{recommonProductImage1, recommonProductImage2, recommonProductImage3};
        arrRecommonProducttitle = new TextView[]{recommonProductTitle1, recommonProductTitle2, recommonProductTitle3};
        arrRecommonProductprice = new TextView[]{recommonProductPrice1, recommonProductPrice2, recommonProductPrice3};
        arrRecommonProductOldPirce = new TextView[]{recommonProductOldPrice1, recommonProductOldPrice2, recommonProductOldPrice3};
        listRecommonProductBottom = new ArrayList<>();
        listRecommonProductTop = new ArrayList<>();

        if (listRecommonProduct.size() < 3) {
            return;
        }
        for (int i = 0; i < listRecommonProduct.size() - 3; i++) {
            listRecommonProductTop.add(listRecommonProduct.get(i));
        }
        for (int i = listRecommonProduct.size() - 3; i < listRecommonProduct.size(); i++) {
            listRecommonProductBottom.add(listRecommonProduct.get(i));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listActivityRl.setLayoutManager(layoutManager);
        listActivityAdapter = new ListActivityAdapter(R.layout.list_activity_item, listRecommonProductTop);
        listActivityRl.setAdapter(listActivityAdapter);
        listActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goGooddetail(String.valueOf(listRecommonProductTop.get(position).getPId()), false);
            }
        });


        for (int i = 0; i < listRecommonProductBottom.size(); i++) {
            CommonUtil.loadImage(getContext(), listRecommonProductBottom.get(i).getPics().get(0).getPath(), arrRecommonProductImage[i]);
            arrRecommonProducttitle[i].setText(listRecommonProductBottom.get(i).getPName());

            if (listRecommonProductBottom.get(i).isReference()) {
                arrRecommonProductprice[i].setText(getResources().getString(R.string.moneny_string) + "" + StringUtil.replaceString(String.valueOf(listRecommonProductBottom.get(i).getReferencePrice())));
            } else {
                arrRecommonProductprice[i].setText(getResources().getString(R.string.moneny_string) + "" + StringUtil.replaceString(String.valueOf(listRecommonProductBottom.get(i).getPrice())));
            }
            arrRecommonProductOldPirce[i].setText(listRecommonProductBottom.get(i).getSaleNum() + "人购买");
            final int finalI = i;
            arrRecommonProductImage[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goGooddetail(String.valueOf(listRecommonProductBottom.get(finalI).getPId()), false);
                }
            });
        }

    }


    private void showPanicBuy(HomeBean.DataBean.PanicBuyingBean panicBuying) {
        String time = panicBuying.getEndTime();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (Utils.getTime(time) > 0) {
            killOverText.setVisibility(View.GONE);
            killTimeLl.setVisibility(View.VISIBLE);
            killLlBg.setBackgroundResource(R.mipmap.home_second_kill);
            long timeLong = Utils.getTime(time);
            timer = new CountDownTimer(timeLong, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long temp = millisUntilFinished / 1000;
                    long hours = temp / 3600;
                    long minutes = (temp - (3600 * hours)) / 60;
                    long seconds = temp - (3600 * hours) - (60 * minutes);
                    spikeTimeHour.setText(hours > 9 ? "" + hours : "0" + hours);
                    spikeTimeMinute.setText(minutes > 9 ? "" + minutes : "0" + minutes);
                    spikeTimeSeconds.setText(seconds > 9 ? "" + seconds : "0" + seconds);
                }

                @Override
                public void onFinish() {
                    killOverText.setVisibility(View.VISIBLE);
                    killTimeLl.setVisibility(View.GONE);
                    killLlBg.setBackgroundResource(R.mipmap.home_second_kill_2);
                }
            }.start();
        } else {
            killOverText.setVisibility(View.VISIBLE);
            killTimeLl.setVisibility(View.GONE);
            killLlBg.setBackgroundResource(R.mipmap.home_second_kill_2);
        }

    }

    /**
     * 秒殺
     */
    private void setPanicBuy() {

        arrPanicBuyRl = new RelativeLayout[]{
                secondsKillRl1, secondsKillRl2, secondsKillRl3, secondsKillRl4, secondsKillRl5
        };
        arrPanicBuytitle = new TextView[]{secondsKillTitle1, secondsKillTitle2, secondsKillTitle3, secondsKillTitle4, secondsKillTitle5};
        arrPanicBuyPrice = new TextView[]{
                secondsKillPrice1, secondsKillPrice2, secondsKillPrice3, secondsKillPrice4, secondsKillPrice5
        };
        arrPanicBuyOldPrice = new TextView[]{
                secondsKillOldPrice1, secondsKillOldPrice2, secondsKillOldPrice3, secondsKillOldPrice4, secondsKillOldPrice5
        };
        for (int i = 0; i < listPanicBuy.size(); i++) {
            CommonUtil.loadRlImage(getContext(), listPanicBuy.get(i).getProPic(), arrPanicBuyRl[i], arrPanicBuyRl[i].getWidth(), arrPanicBuyRl[i].getHeight());
            arrPanicBuytitle[i].setText(listPanicBuy.get(i).getProName() + "");
            arrPanicBuyPrice[i].setText(StringUtil.replaceString(String.valueOf(listPanicBuy.get(i).getNewPrice())));
            arrPanicBuyOldPrice[i].setText(getResources().getString(R.string.moneny_string) + StringUtil.replaceString(String.valueOf(listPanicBuy.get(i).getOldPrice())));
            arrPanicBuyOldPrice[i].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
            final int finalI = i;
            arrPanicBuyRl[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goGooddetail(String.valueOf(listPanicBuy.get(finalI).getProId()), true);
                }
            });
        }

    }

    /**
     * 跳轉到攻略詳情
     *
     * @param strategyId
     */
    private void goStrategdetail(String strategyId) {

        Bundle bundle = new Bundle();
        bundle.putString("strateDetailId", strategyId);
        BaseActivity.navigate(getContext(), StrategdetailActivity.class, bundle);
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
        BaseActivity.navigate(getContext(), GoodsDetailsActivity.class, bundle);
    }


    //設置banner
    private void setBanner() {

        for (int i = 0; i < listBanner.size(); i++) {
            bannerImages.add(listBanner.get(i).getBannerPic());
        }
        banner.setImages(bannerImages)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
                switch (listBanner.get(position).getBannerType()) {
                    case 0:
                        bundle.putString("pid", String.valueOf(listBanner.get(position).getBannerId()));
                        bundle.putBoolean("panic", false);
                        BaseActivity.navigate(getContext(), GoodsDetailsActivity.class, bundle);
                        break;
                    case 1:
                        bundle.putString("strateDetailId", String.valueOf(listBanner.get(position).getBannerId()));
                        BaseActivity.navigate(getContext(), StrategdetailActivity.class, bundle);
                }
            }
        });

    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    protected void initView() {
        //显示默认城市
        homeTopBarCity.setText(Constans.AreaCity);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(homeSmartRefreshLayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                HomeFragment.this.setRetryEvent(retryView);
            }
        });

        homeTopBarSearchLl.getBackground().setAlpha(150);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float) (-verticalOffset) / banner.getHeight() * 255;
                if (verticalOffset >= -banner.getHeight()) {
                    toolbar.getBackground().setAlpha((int) alpha);
                } else {
                    toolbar.getBackground().setAlpha(255);
                }

                if (verticalOffset > -appBar.getHeight() / 2) {
                    backTopImage.setVisibility(View.GONE);
                } else {
                    backTopImage.setVisibility(View.VISIBLE);
                }
            }
        });


 /*       CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();

        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return true;
            }
        });*/


    }

    @Override
    protected void setListener() {
        super.setListener();
        homeSmartRefreshLayout.setRefreshHeader(new RefreshView(getContext()));
        homeSmartRefreshLayout.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_25));
        homeSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listProductTotal.clear();
                        currPage = 0;
                        getHomeData(true, 0);
                        homeSmartRefreshLayout.finishRefresh();
                    }
                }, 2000);


            }


        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
        if (timer != null) {
            timer.cancel();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);  //注册
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.recommonProduct_go_more_ll, R.id.main_message, R.id.city_picker_ll, R.id.quwan_rl, R.id.yingdi_rl, R.id.yanxue_rl, R.id.jingdian_rl,
            R.id.xiongmao_rl, R.id.home_top_bar_search, R.id.back_top_image, R.id.home_strategies_Image_1,
            R.id.home_strategies_Image_2, R.id.home_strategies_Image_3, R.id.home_strategies_Image_4, R.id.home_right_gn_go_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recommonProduct_go_more_ll:
                BaseActivity.navigate(getContext(), ActivityListActivity.class);
                break;

            case R.id.main_message:
                BaseActivity.navigate(getContext(), MessageActivity.class);
                break;

            //城市选择
            case R.id.city_picker_ll:
                startActivityForResult(new Intent((Activity) getContext(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
                break;
            case R.id.home_top_bar_search:
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                BaseActivity.navigate(getContext(), HotSearchActivity.class);
                break;
            case R.id.quwan_rl:
                goToHomeSecond(0);
                break;
            case R.id.yingdi_rl:
                goToHomeSecond(1);
                break;
            case R.id.yanxue_rl:
                goToHomeSecond(2);
                break;
            case R.id.jingdian_rl:
                goToHomeSecond(3);
                break;
            case R.id.xiongmao_rl:
                BaseActivity.navigate(getContext(), StrategListActivity.class);
                break;

            case R.id.home_strategies_Image_1:
                break;
            case R.id.home_strategies_Image_2:
                goStrategdetail(String.valueOf(listStrategies.get(1).getStrategyId()));
                break;
            case R.id.home_strategies_Image_3:
                goStrategdetail(String.valueOf(listStrategies.get(2).getStrategyId()));
                break;
            case R.id.home_strategies_Image_4:
                goStrategdetail(String.valueOf(listStrategies.get(3).getStrategyId()));
                break;

            case R.id.back_top_image:
                appBar.setExpanded(true, false);
                appBar.setFocusable(true);
                appBar.setFocusableInTouchMode(true);
                appBar.requestFocus();
                mainBottomRecyclerView.smoothScrollToPosition(0);
//                EventBus.getDefault().post(new EvenUtil("activity2发送消息"));
                break;
            case R.id.home_right_gn_go_text:
                BaseActivity.navigate(getContext(), StrategListActivity.class);
                break;

        }
    }

    /**
     * 跳转到二级
     */
    private void goToHomeSecond(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("parentId", String.valueOf(listTypies.get(position).getProTypeId()));
        bundle.putString("parentName", String.valueOf(listTypies.get(position).getTypeName()));
        BaseActivity.navigate(getContext(), HomeSecondActivity.class, bundle);
    }


    @Subscribe
    public void onEventMainThread(EvenUtil event) {
        String msglog = "----onEventMainThread收到了消息：" + event.getMsg();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                currCity = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                currCityID = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY_ID);
                Constans.Area = currCityID;
                homeTopBarCity.setText(currCity);
                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.Area, currCityID);
                OkGo.getInstance().addCommonHeaders(new HttpHeaders("Area", currCityID));
                homeSmartRefreshLayout.autoRefresh();
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }


    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.this.initData();
            }
        });


    }

}
