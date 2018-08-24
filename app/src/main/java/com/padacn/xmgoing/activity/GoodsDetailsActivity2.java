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
import com.padacn.xmgoing.model.GoodSureBean;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.ShareBean;
import com.padacn.xmgoing.model.ShopDetailsItem;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.model.TabEntity;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.util.AppManager;
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

public class GoodsDetailsActivity2 extends BaseActivity {

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

    private List<GoodsDtailsBean.DataBean.DetailDtoBean.PicsBean> picsBeanList;
    private List<String> bannerImages;
    private List<GoodsDtailsBean.DataBean.PromisesBean> promisesBeanList;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private final String[] mTitles = {"商品简介", "商品评价", "商品详情", "商品推荐"};
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
                .headers("logintype", Constans.LOGINTYPES)
                .params("pid", currPid)
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
        goodSureBean.setPicPath(goodsDtailsBeanData.getData().getDetailDto().getPics().get(0).getPath());
        goodSureBean.setSellerId(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getSellerId()));
        goodSureBean.setUseIdcard(goodsDtailsBeanData.getData().getDetailDto().isUseIdcard());
        goodSureBean.setTravelNum(goodsDtailsBeanData.getData().getDetailDto().getTravelNum());
//        goodSureBean.setType(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getPType()));

        sureGoodsBean = new SureGoodsBean();
        goodsBeanList = new ArrayList<>();
        goodsBean = new SureGoodsBean.GoodsBean();
        goodsBean.setPid(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getPId()));
        goodsBean.setPName(goodsDtailsBeanData.getData().getDetailDto().getPName());
        goodsBean.setPicPath(goodsDtailsBeanData.getData().getDetailDto().getPics().get(0).getPath());
        goodsBean.setUseIdcard(goodsDtailsBeanData.getData().getDetailDto().isUseIdcard());
        goodsBean.setTravelNum(goodsDtailsBeanData.getData().getDetailDto().getTravelNum());
        goodsBeanList.add(goodsBean);
        sureGoodsBean.setSellerId(String.valueOf(goodsDtailsBeanData.getData().getDetailDto().getSellerId()));
        sureGoodsBean.setGoods(goodsBeanList);
        shopDetailsItemAdapter.setSureGood(sureGoodsBean);

//        shopDetailsItemAdapter.setGoodSure(goodSureBean);
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
                GoodsDetailsActivity2.this.setRetryEvent(retryView);
            }
        });

        Bundle bundle = getIntent().getExtras();
        currPid = bundle.getString("pid");
        AppManager.getInstance().addActivity(this);
        goodsDetailsRecyclerview.setFocusable(false);
        goodSureBooking.setBackgroundResource(R.color.common_top_bar_bg);
        List<ShopDetailsItem> list = new ArrayList<>();
        list.add(new ShopDetailsItem(ShopDetailsItem.ONE));
        list.add(new ShopDetailsItem(ShopDetailsItem.TWO));
        list.add(new ShopDetailsItem(ShopDetailsItem.THREE));
        list.add(new ShopDetailsItem(ShopDetailsItem.FOUR));

        shopDetailsItemAdapter = new ShopDetailsItemAdapter(list);
        layoutManager1 = new ScrollSpeedLinearLayoutManger(this);
        goodsDetailsRecyclerview.setLayoutManager(layoutManager1);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float) (-verticalOffset) / banner.getHeight() * 255;
                if (verticalOffset >= -banner.getHeight() / 3) {
                    goodDetailsTopBgLl.getBackground().setAlpha((int) alpha);
                    Log.d(TAG, "onOffsetChanged: " + alpha);
                    shopDetailsTab.setAlpha(alpha / 255);
                } else {
                    goodDetailsTopBgLl.getBackground().setAlpha(255);
                    shopDetailsTab.setAlpha(1);
                }
            }
        });


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        shopDetailsTab.setTabData(mTabEntities);

        shopDetailsTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
//                        smoothMoveToPosition(goodsDetailsRecyclerview, position);
                        goodsDetailsRecyclerview.smoothScrollToPosition(position);
                        shopDetailsTab.setCurrentTab(0);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                appBar.setExpanded(true, true);
                            }
                        }, 1000);

                        break;
                    case 1:
                        shopDetailsTab.setCurrentTab(1);
                        goodsDetailsRecyclerview.smoothScrollToPosition(position);
//                        smoothMoveToPosition(goodsDetailsRecyclerview, position);
                        appBar.setExpanded(false, true);
                        break;
                    case 2:
                        shopDetailsTab.setCurrentTab(2);
                        goodsDetailsRecyclerview.smoothScrollToPosition(position);
//                        smoothMoveToPosition(goodsDetailsRecyclerview, position);
                        appBar.setExpanded(false, true);
                        break;
                    case 3:
                        shopDetailsTab.setCurrentTab(3);
                        goodsDetailsRecyclerview.smoothScrollToPosition(position);
//                        smoothMoveToPosition(goodsDetailsRecyclerview, position);
                        appBar.setExpanded(false, true);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


        goodsDetailsRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
        /*        Log.d(TAG, "onScrollStateChanged: " + false);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
//                    smoothMoveToPosition(goodsDetailsRecyclerview, mToPosition);
                }*/
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager l = (LinearLayoutManager) recyclerView.getLayoutManager();
                currScrollPosition = l.findFirstVisibleItemPosition();
                Log.d(TAG, "onScrolled: " + currScrollPosition);
                shopDetailsTab.setCurrentTab(currScrollPosition);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        Log.e(TAG, "smoothMoveToPosition: firstItem" + firstItem + "lastItem" + lastItem + "position" + position);
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
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
                BaseActivity.navigate(GoodsDetailsActivity2.this, SelectActivity.class, bundle);
                easyPopup.dismiss();
            }
        });
    }

    @OnClick({R.id.good_details_consultation, R.id.good_details_addCar, R.id.good_sure_booking, R.id.good_details_back_rl, R.id.good_details_share_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.good_details_consultation:
                diallPhone(Constans.PhoneNumber);
                break;
            case R.id.good_details_addCar:
                if (!SharePreferencesUtil.getBooleanSharePreferences(GoodsDetailsActivity2.this, Constans.isAlreadyLogin, false)) {
                    BaseActivity.navigate(GoodsDetailsActivity2.this, LoginActivity.class);
                    return;
                }
                isAddCar = true;
                easyPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.good_sure_booking:

             /*   if (!SharePreferencesUtil.getBooleanSharePreferences(GoodsDetailsActivity.this, Constans.isAlreadyLogin, false)) {
                    BaseActivity.navigate(GoodsDetailsActivity.this, LoginActivity.class);
                    return;
                }*/
                isAddCar = false;
                easyPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                break;

            case R.id.good_details_back_rl:
                finish();
                break;
            case R.id.good_details_share_rl:
                easySharePopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
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
                        ShareApp(SHARE_MEDIA.QQ);
                        break;
                    case 3:
                        ShareApp(SHARE_MEDIA.QZONE);
                        break;
                    case 4:
                        ShareApp(SHARE_MEDIA.SINA);
                        break;
                    case 5:
                        ClipboardManager cm = (ClipboardManager) GoodsDetailsActivity2.this.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(currShareUrl);
                        break;

                }
            }
        });
    }

    private void ShareApp(SHARE_MEDIA share_media) {
        easySharePopup.dismiss();
        UMWeb web = new UMWeb(currShareUrl);
        web.setTitle(goodsDtailsBeanData.getData().getDetailDto().getPName());
        web.setThumb(new UMImage(this, goodsDtailsBeanData.getData().getDetailDto().getPics().get(0).getPath()));
        web.setDescription(goodsDtailsBeanData.getData().getDetailDto().getPDetail());
        new ShareAction(GoodsDetailsActivity2.this).withMedia(web)
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
            RxToast.warning("取消了");
        }
    };

}
