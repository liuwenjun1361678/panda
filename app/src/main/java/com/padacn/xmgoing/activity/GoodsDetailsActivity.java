package com.padacn.xmgoing.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.MyShareAdapter;
import com.padacn.xmgoing.adapter.SelectTiketAdapter;
import com.padacn.xmgoing.adapter.ShopDetailsItemAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.callback.OnGoodsShareClickListener;
import com.padacn.xmgoing.model.GoodSureBean;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.ShareBean;
import com.padacn.xmgoing.model.ShopDetailsItem;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.model.TabEntity;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.CallDialog;
import com.padacn.xmgoing.view.GlideImageLoader;
import com.padacn.xmgoing.view.ScrollSpeedLinearLayoutManger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vondear.rxtools.view.RxToast;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zyyoona7.popup.EasyPopup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 46009 on 2018/5/10.
 */

public class GoodsDetailsActivity extends BaseActivity {

    private static final String TAG = "GoodsDetailsActivity";
    @BindView(R.id.top_ll)
    CoordinatorLayout topLl;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.shop_details_tab)
    CommonTabLayout shopDetailsTab;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.goods_details_recyclerview)
    RecyclerView goodsDetailsRecyclerview;
    //    @BindView(R.id.item_detail_container)
//    NestedScrollView itemDetailContainer;
    @BindView(R.id.good_details_toolbar)
    Toolbar goodDetailsToolbar;
    @BindView(R.id.good_details_top_bg_ll)
    LinearLayout goodDetailsTopBgLl;
    @BindView(R.id.good_sure_booking)
    TextView goodSureBooking;
    @BindView(R.id.good_details_ll)
    LinearLayout goodDetailsLl;
    @BindView(R.id.good_details_back_rl)
    RelativeLayout goodDetailsBackRl;
    @BindView(R.id.good_details_share_rl)
    RelativeLayout goodDetailsShareRl;
    @BindView(R.id.good_details_addCar_text)
    TextView goodDetailsAddCarText;
    @BindView(R.id.good_details_title)
    TextView goodDetailsTitle;
    @BindView(R.id.back_top_image)
    ImageView backTopImage;
    @BindView(R.id.goods_details_back_image)
    ImageView goodsDetailsBackImage;

    private View mView;
    private List<GoodsDtailsBean.DataBean.DetailDtoBean.PicsBean> picsBeanList;
    private List<String> bannerImages;
    private List<GoodsDtailsBean.DataBean.PromisesBean> promisesBeanList;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private final String[] mTitles = {"商品", "评价", "详情", "推荐"};
    private int[] shareIconId = new int[]{R.mipmap.share_icon_wxhy, R.mipmap.share_icon_pyq,
            R.mipmap.share_icon_qq, R.mipmap.share_icon_qqkj, R.mipmap.share_icon_weibo, R.mipmap.share_icon_fuzhi};
    private String[] shareTitle = new String[]{"微信好友", "朋友圈", "QQ好友", "QQ空间", "新浪微博", "复制链接"};

    private int currScrollPosition = -1;
    private ShopDetailsItemAdapter shopDetailsItemAdapter;
    //当前商品名称
    private String currGoodsName;
    //当前商品价钱
    private String currGoodsPrice;
    private String oldGoodsPrice;
    //当前卖出份数
    String currSaleNum;
    private List<GoodsDtailsBean.SellerCouponBean> sellerCouponBeanList;

    private List<GoodsDtailsBean.DataBean.TicketTypesBean> ticketTypesBeanList;
    //商店
    private GoodsDtailsBean.DataBean.SellerBean sellerBean;
    //取消政策
    private GoodsDtailsBean.DataBean.CancelPolicyBean cancelPolicyBean;
    //預訂需知
    private GoodsDtailsBean.DataBean.NotesToBuyBean notesToBuyBean;
    private GoodsDtailsBean goodsDtailsBeanData;
    Handler mHandler = new Handler();
    ScrollSpeedLinearLayoutManger layoutManager1;
    //底部弹窗
    private EasyPopup easyPopup;
    private SelectTiketAdapter selectTiketAdapter;
    private GoodSureBean goodSureBean;
    //設置商品的bean
    private SureGoodsBean.GoodsBean goodsBean;
    private List<SureGoodsBean.GoodsBean> goodsBeanList;
    private SureGoodsBean sureGoodsBean;

    CallDialog callDialog;
    private int createOrderType;
    private String currPid;
    private boolean isAddCar;

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    private int goodsStatu;
    private LinearLayoutManager linearLayoutManager;//滑動的
    private LoadingAndRetryManager mLoadingAndRetryManager;
    //底部分享
    private List<ShareBean> shareBeanList;
    private MyShareAdapter myShareAdapter;
    private String currShareUrl;
    //商品名称
    private String goodsName;
    private String goodsContant;
    //分享弹窗
    private EasyPopup easySharePopup;
    //是否是秒杀商品
    private boolean panic;
    private String pan;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_goods_details;
    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getGoodsDetails();
    }

    /**
     * 获取商品详情
     */
    private void getGoodsDetails() {
        sellerCouponBeanList = new ArrayList<>();
        ticketTypesBeanList = new ArrayList<>();
        picsBeanList = new ArrayList<>();
        bannerImages = new ArrayList<>();
        promisesBeanList = new ArrayList<>();
        sellerBean = new GoodsDtailsBean.DataBean.SellerBean();
        cancelPolicyBean = new GoodsDtailsBean.DataBean.CancelPolicyBean();
        notesToBuyBean = new GoodsDtailsBean.DataBean.NotesToBuyBean();
        goodsDtailsBeanData = new GoodsDtailsBean();
        OkGo.<String>post(ServiceApi.PRODUCT_PRODUCTDETAIL)
                .tag(this)
                .params("pid", currPid)
                .params("panic", pan)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                mLoadingAndRetryManager.showContent();
                                Gson gson = new Gson();
                                GoodsDtailsBean goodsDtailsBean = gson.fromJson(s, GoodsDtailsBean.class);
                                if (goodsDtailsBean.getResult() == 1) {
                                    goodsStatu = goodsDtailsBean.getData().getDetailDto().getStatu();
                                    showStau(goodsStatu);
                                    //标
                                    if (goodsDtailsBean.getData().getDetailDto().getPics() != null) {
                                        picsBeanList.addAll(goodsDtailsBean.getData().getDetailDto().getPics());
                                    }
                                    if (goodsDtailsBean.getData().getPromises() != null) {
                                        promisesBeanList.addAll(goodsDtailsBean.getData().getPromises());
                                    }
                                    sellerCouponBeanList.addAll(goodsDtailsBean.getSellerCoupon());
                                    currGoodsName = goodsDtailsBean.getData().getDetailDto().getPName();
                                    currGoodsPrice = goodsDtailsBean.getData().getDetailDto().getSalePrice();
                                    oldGoodsPrice = goodsDtailsBean.getData().getDetailDto().getLinePrice();
                                    currSaleNum = String.valueOf(goodsDtailsBean.getData().getDetailDto().getSaleNum());
                                    if (goodsDtailsBean.getData().getTicketTypes() != null) {
                                        ticketTypesBeanList.addAll(goodsDtailsBean.getData().getTicketTypes());
                                    }
                                    currShareUrl = goodsDtailsBean.getData().getShareUrl();
                                    goodsDtailsBeanData = goodsDtailsBean;
                                    sellerBean = goodsDtailsBean.getData().getSeller();
                                    cancelPolicyBean = goodsDtailsBean.getData().getCancelPolicy();
                                    notesToBuyBean = goodsDtailsBean.getData().getNotesToBuy();
                                    setGoodsBanner();
                                    setGoodsOne();
                                }
                            } else if ((result == 0)) {
                                RxToast.error(jsonObject.getString("msg"));
                                finish();
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
     * 展示購買狀態
     *
     * @param
     */
    private void showStau(int goodsStatu) {
        switch (goodsStatu) {
            case 1:
                goodDetailsAddCarText.setText("加购");
                goodSureBooking.setText("立即预订");
                goodSureBooking.setBackgroundResource(R.drawable.shape_common_button);
                break;
            case 2:
                goodDetailsAddCarText.setText("售罄");
                goodSureBooking.setText("售罄");
                goodSureBooking.setBackgroundResource(R.drawable.shape_buy_false);
                break;
            case 3:
                goodDetailsAddCarText.setText("下架");
                goodSureBooking.setText("下架");
                goodSureBooking.setBackgroundResource(R.drawable.shape_buy_false);
                break;
        }


    }

    private void setGoodsOne() {

        shopDetailsItemAdapter.setDetailsOne(promisesBeanList, currGoodsName, currGoodsPrice, oldGoodsPrice, currSaleNum);
        shopDetailsItemAdapter.setDetailsTwo(ticketTypesBeanList);
        shopDetailsItemAdapter.setDetailsThree(sellerBean);
        shopDetailsItemAdapter.setDetails(goodsDtailsBeanData);
        goodsDetailsRecyclerview.setAdapter(shopDetailsItemAdapter);

        setGoods();
        initPopView();
        initPopShareView();

    }

    /**
     * 设置去下个页面传递的数据
     */
    private void setGoods() {

        goodSureBean = new GoodSureBean();
        goodSureBean.setpId(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getPId()));
        goodSureBean.setpName(goodsDtailsBeanData.getData().getDetailDto().getPName());
        if (goodsDtailsBeanData.getData().getDetailDto().getPics().size() > 0) {
            goodSureBean.setPicPath(goodsDtailsBeanData.getData().getDetailDto().getPics().get(0).getPath());
        }
        goodSureBean.setSellerId(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getSellerId()));
        goodSureBean.setUseIdcard(goodsDtailsBeanData.getData().getDetailDto().isUseIdcard());
        goodSureBean.setTravelNum(goodsDtailsBeanData.getData().getDetailDto().getTravelNum());
//        goodSureBean.setType(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getPType()));

        sureGoodsBean = new SureGoodsBean();
        goodsBeanList = new ArrayList<>();
        goodsBean = new SureGoodsBean.GoodsBean();
        goodsBean.setPid(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getPId()));
        goodsBean.setPName(goodsDtailsBeanData.getData().getDetailDto().getPName());
        if (goodsDtailsBeanData.getData().getDetailDto().getPics().size() > 0) {
            goodsBean.setPicPath(goodsDtailsBeanData.getData().getDetailDto().getPics().get(0).getPath());
        }
        goodsBean.setUseIdcard(goodsDtailsBeanData.getData().getDetailDto().isUseIdcard());
        goodsBean.setTravelNum(goodsDtailsBeanData.getData().getDetailDto().getTravelNum());
        goodsBeanList.add(goodsBean);
        sureGoodsBean.setSellerId(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getSellerId()));
        Log.e(TAG, "setGoods:111 " + String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getSellerId()));
        sureGoodsBean.setGoods(goodsBeanList);
        shopDetailsItemAdapter.setSureGood(sureGoodsBean);

//        shopDetailsItemAdapter.setGoodSure(goodSureBean);

        shopDetailsItemAdapter.setOnGoodsShareClickListener(new OnGoodsShareClickListener() {
            @Override
            public void onClick() {
//                goodDetailsShareRl.performClick();
                easySharePopup.showAtLocation(goodDetailsLl, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    /**
     * 图片的banner
     */
    private void setGoodsBanner() {
        for (int i = 0; i < picsBeanList.size(); i++) {
            bannerImages.add(picsBeanList.get(i).getPath());
        }
        banner.setImages(bannerImages)
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setDelayTime(5000)
                .start();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
    }

    @Override
    protected void initView() {
        super.initView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(goodDetailsLl, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                GoodsDetailsActivity.this.setRetryEvent(retryView);
            }
        });

        Bundle bundle = getIntent().getExtras();
        currPid = bundle.getString("pid");
        panic = bundle.getBoolean("panic");
        if (panic) {
            pan = "1";
        } else {
            pan = null;
        }
        goodsDetailsRecyclerview.setFocusable(false);
        goodSureBooking.setBackgroundResource(R.color.common_top_bar_bg);
        List<ShopDetailsItem> list = new ArrayList<>();
        list.add(new ShopDetailsItem(ShopDetailsItem.ONE));
        list.add(new ShopDetailsItem(ShopDetailsItem.TWO));
        list.add(new ShopDetailsItem(ShopDetailsItem.THREE));
        list.add(new ShopDetailsItem(ShopDetailsItem.FOUR));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        goodsDetailsRecyclerview.setLayoutManager(linearLayoutManager);
        shopDetailsItemAdapter = new ShopDetailsItemAdapter(list);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float) (-verticalOffset) / banner.getHeight() * 255;
                if (verticalOffset >= -banner.getHeight() / 3) {
                    goodDetailsTopBgLl.getBackground().setAlpha((int) alpha);
                    shopDetailsTab.setAlpha(alpha / 255);
                    goodDetailsTitle.setAlpha(alpha / 255);
                    goodsDetailsBackImage.setImageResource(R.mipmap.goods_details_back_write);
                } else {
                    goodDetailsTopBgLl.getBackground().setAlpha(255);
                    goodDetailsTitle.setAlpha(alpha / 1);
                    shopDetailsTab.setAlpha(1);
                    goodsDetailsBackImage.setImageResource(R.mipmap.goods_details_back_black);

                }
            }
        });


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        shopDetailsTab.setTabData(mTabEntities);

        shopDetailsTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(final int position) {
                switch (position) {
                    case 0:
                        MoveToPosition(linearLayoutManager, goodsDetailsRecyclerview, position);
                        shopDetailsTab.setCurrentTab(position);
                        appBar.setExpanded(true, true);
                        break;
                    case 1:
                        shopDetailsTab.setCurrentTab(position);
                        MoveToPosition(linearLayoutManager, goodsDetailsRecyclerview, position);
                        appBar.setExpanded(false, true);
                        break;
                    case 2:
                        shopDetailsTab.setCurrentTab(position);
                        MoveToPosition(linearLayoutManager, goodsDetailsRecyclerview, position);
                        appBar.setExpanded(false, true);
                        break;
                    case 3:
                        shopDetailsTab.setCurrentTab(position);
                        MoveToPosition(linearLayoutManager, goodsDetailsRecyclerview, position);
                        appBar.setExpanded(false, true);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


        goodsDetailsRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener()

        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                newState == RecyclerView.SCROLL_STATE_IDLE
//                if (currScrollPosition <= 1) {
//                    if (goodsDetailsRecyclerview.getLayerType() != View.LAYER_TYPE_SOFTWARE) {
//                        Log.e(TAG, "onScrollStateChanged: " + currScrollPosition);
//                        goodsDetailsRecyclerview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//
//                    }
//                } else {
//                    if (goodsDetailsRecyclerview.getLayerType() != View.LAYER_TYPE_HARDWARE) {
//                        Log.e(TAG, "onScrollStateChanged: " + currScrollPosition);
//                        goodsDetailsRecyclerview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                    }
//                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (currScrollPosition < 2) {
                    backTopImage.setVisibility(View.GONE);
                } else {
                    backTopImage.setVisibility(View.VISIBLE);
                }
                currScrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
                shopDetailsTab.setCurrentTab(currScrollPosition);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    MoveToPosition(linearLayoutManager, goodsDetailsRecyclerview, mToPosition);
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 滑动到指定位置
     */
    private void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.scrollToPosition(n);
            mToPosition = n;
            mShouldScroll = true;
        }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void diallPhone(final String phoneNum) {
        callDialog = new CallDialog(this);
        callDialog.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                startActivity(intent);
            }
        });
        callDialog.getNumberView().setText(phoneNum);
        callDialog.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDialog.cancel();
            }
        });

        callDialog.show();
    }

    /**
     * 底部分享view
     */
    private void initPopView() {
        easyPopup = new EasyPopup(this)
                .setContentView(R.layout.pop_select_meal, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setFocusAndOutsideEnable(false)
                .setDimView(goodDetailsLl)
                .setAnimationStyle(R.style.dialogWindowAnim)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f)
                .apply();


        RelativeLayout relativeLayout = easyPopup.findViewById(R.id.pop_select_cancel);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyPopup.dismiss();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = easyPopup.findViewById(R.id.pop_select_rc);
        recyclerView.setLayoutManager(layoutManager);
        selectTiketAdapter = new SelectTiketAdapter(R.layout.item_select_tiket, ticketTypesBeanList);
        recyclerView.setAdapter(selectTiketAdapter);


        selectTiketAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                createOrderType = 1;
                Bundle bundle = new Bundle();
                bundle.putInt("createOrderType", createOrderType);
                bundle.putSerializable("goodSureBean", goodSureBean);
                bundle.putSerializable("sureGoodsBean", sureGoodsBean);
                bundle.putBoolean("isAddCar", isAddCar);
                List<GoodsDtailsBean.DataBean.TicketTypesBean> list = new ArrayList<>();
                list.add(ticketTypesBeanList.get(position));
                HomeThreeSelectPresenter.getSingleTon().saveticketTypes(list);
                BaseActivity.navigate(GoodsDetailsActivity.this, SelectActivity.class, bundle);
                easyPopup.dismiss();
            }
        });
    }

    @OnClick({R.id.good_details_consultation, R.id.good_details_addCar, R.id.good_sure_booking,
            R.id.good_details_back_rl, R.id.good_details_share_rl, R.id.back_top_image})
    public void onViewClicked(View view) {
        mView = view;
        switch (view.getId()) {
            case R.id.back_top_image:
                backTopImage.setVisibility(View.GONE);
                MoveToPosition(linearLayoutManager, goodsDetailsRecyclerview, 0);
                shopDetailsTab.setCurrentTab(0);
                appBar.setExpanded(true, true);
                break;
            case R.id.good_details_consultation:
                diallPhone(sellerBean.getTel());
                break;
            case R.id.good_details_addCar:
                if (goodsStatu != 1) {
                    return;
                }
                if (!SharePreferencesUtil.getBooleanSharePreferences(GoodsDetailsActivity.this, Constans.isAlreadyLogin, false)) {
                    BaseActivity.navigate(GoodsDetailsActivity.this, LoginActivity.class);
                    return;
                }
                isAddCar = true;
                if (ticketTypesBeanList.size() == 1) {
                    goSelec();
                } else {
                    easyPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.good_sure_booking:
                if (goodsStatu != 1) {
                    return;
                }
                if (!SharePreferencesUtil.getBooleanSharePreferences(GoodsDetailsActivity.this, Constans.isAlreadyLogin, false)) {
                    BaseActivity.navigate(GoodsDetailsActivity.this, LoginActivity.class);
                    return;
                }
                isAddCar = false;
                if (ticketTypesBeanList.size() == 1) {
                    goSelec();
                } else {
                    easyPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                }
                break;

            case R.id.good_details_back_rl:
                finish();
                break;
            case R.id.good_details_share_rl:

                break;
        }
    }

    //套餐只有一个的时候直接跳转
    private void goSelec() {
        createOrderType = 1;
        Bundle bundle = new Bundle();
        bundle.putInt("createOrderType", createOrderType);
        bundle.putSerializable("goodSureBean", goodSureBean);
        bundle.putSerializable("sureGoodsBean", sureGoodsBean);
        bundle.putBoolean("isAddCar", isAddCar);
        List<GoodsDtailsBean.DataBean.TicketTypesBean> list = new ArrayList<>();
        list.add(ticketTypesBeanList.get(0));
        HomeThreeSelectPresenter.getSingleTon().saveticketTypes(list);
        BaseActivity.navigate(GoodsDetailsActivity.this, SelectActivity.class, bundle);
    }

    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }


    /**
     * 底部分享view
     */
    private void initPopShareView() {
        shareBeanList = new ArrayList<>();
        for (int i = 0; i < shareIconId.length; i++) {
            ShareBean shareBean = new ShareBean();
            shareBean.setShareIcon(shareIconId[i]);
            shareBean.setShareTitle(shareTitle[i]);
            shareBeanList.add(shareBean);
        }
        easySharePopup = new EasyPopup(this)
                .setContentView(R.layout.fragment_mine_share, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setFocusAndOutsideEnable(false)
                .setDimView(goodDetailsLl)
                .setAnimationStyle(R.style.dialogWindowAnim)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f)
                .apply();

        RelativeLayout relativeLayout = easySharePopup.findViewById(R.id.mine_share_cancel);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easySharePopup.dismiss();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = easySharePopup.findViewById(R.id.mine_share_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        myShareAdapter = new MyShareAdapter(R.layout.item_my_share, shareBeanList);
        recyclerView.setAdapter(myShareAdapter);
        myShareAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        ShareApp(SHARE_MEDIA.WEIXIN);
                        break;
                    case 1:
                        ShareApp(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case 2:
                        RxToast.error("暂未开放");
//                        ShareApp(SHARE_MEDIA.QQ);
                        break;
                    case 3:
                        RxToast.error("暂未开放");
//                        ShareApp(SHARE_MEDIA.QZONE);
                        break;
                    case 4:
                        ShareApp(SHARE_MEDIA.SINA);
                        break;
                    case 5:
                        ClipboardManager cm = (ClipboardManager) GoodsDetailsActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(currShareUrl);
                        RxToast.success("复制成功");
                        break;

                }
            }
        });
    }

    private void ShareApp(SHARE_MEDIA share_media) {
        easySharePopup.dismiss();
        UMWeb web = new UMWeb(currShareUrl + currPid);
        web.setTitle(goodsDtailsBeanData.getData().getDetailDto().getPName());
        web.setThumb(new UMImage(this, goodsDtailsBeanData.getData().getDetailDto().getPics().get(0).getPath()));
        web.setDescription(goodsDtailsBeanData.getData().getDetailDto().getPDetail());
        new ShareAction(GoodsDetailsActivity.this).withMedia(web)
                .setPlatform(share_media)//传入平台
                .setCallback(shareListener).share();
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            RxToast.success("分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            RxToast.error("分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            RxToast.error("取消了");
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }
}
