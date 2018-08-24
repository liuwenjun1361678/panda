package com.padacn.xmgoing.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.BusinessAdapter;
import com.padacn.xmgoing.adapter.MyShareAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.BusinessDetailsBean;
import com.padacn.xmgoing.model.BusinessDetailsListBean;
import com.padacn.xmgoing.model.ShareBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.ShareUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.RecycleViewDivider;
import com.padacn.xmgoing.view.RefreshView;
import com.padacn.xmgoing.view.ZQRoundOvalImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;
import com.zyyoona7.popup.EasyPopup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jaydenxiao.com.expandabletextview.ExpandableTextView;

/**
 * Created by Administrator on 2018/7/11 0011.
 */

public class BusinessDetailActivity extends BaseActivity {
    @BindView(R.id.business_top_rl)
    RelativeLayout businessTopRl;
    @BindView(R.id.business_top_head)
    ZQRoundOvalImageView businessTopHead;
    @BindView(R.id.buy_must_know)
    ExpandableTextView buyMustKnow;
    @BindView(R.id.fragment_business_title)
    TextView fragmentBusinessTitle;
    @BindView(R.id.business_details_share)
    ImageView businessDetailsShare;
    @BindView(R.id.business_top_bar)
    Toolbar businessTopBar;
    @BindView(R.id.business_toolbar_layout)
    CollapsingToolbarLayout businessToolbarLayout;
    @BindView(R.id.business_app_bar)
    AppBarLayout businessAppBar;
    @BindView(R.id.business_list_rl)
    RecyclerView businessListRl;
    @BindView(R.id.business_refreshLayout)
    SmartRefreshLayout businessRefreshLayout;
    @BindView(R.id.seller_name)
    TextView sellerName;
    @BindView(R.id.seller_image_1)
    ImageView sellerImage1;
    @BindView(R.id.seller_image_2)
    ImageView sellerImage2;
    @BindView(R.id.seller_good_num)
    TextView sellerGoodNum;
    @BindView(R.id.seller_goods_ll)
    LinearLayout sellerGoodsLl;
    @BindView(R.id.header_Back)
    ImageView headerBack;
    @BindView(R.id.business_top_bar_rl)
    RelativeLayout businessTopBarRl;
    @BindView(R.id.header_Back_rl)
    RelativeLayout headerBackRl;
    @BindView(R.id.dpbusiness_details_share_rl)
    RelativeLayout dpbusinessDetailsShareRl;


    private int[] shareIconId = new int[]{R.mipmap.share_icon_wxhy, R.mipmap.share_icon_pyq,
            R.mipmap.share_icon_qq, R.mipmap.share_icon_qqkj, R.mipmap.share_icon_weibo, R.mipmap.share_icon_fuzhi};
    private String[] shareTitle = new String[]{"微信好友", "朋友圈", "QQ好友", "QQ空间", "新浪微博", "复制链接"};

    private List<ShareBean> shareBeanList;
    private EasyPopup easySharePopup;
    private MyShareAdapter myShareAdapter;
    private String title;
    private String imageUrl;
    private String contant;
    private String shareUrl;

    private LoadingAndRetryManager mLoadingAndRetryManager;
    private List<BusinessDetailsBean.DataBean.ProductsBean> productsBeanList;
    private List<BusinessDetailsBean.DataBean.ProductsBean> productsBeanListTotal;

    private List<BusinessDetailsListBean.DataBean.ProductsBean> productsBeans;
    private BusinessAdapter businessAdapter;
    private String sellerId;
    private int pageCount;
    //当前页数
    private int currPage = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_business_details;
    }

    @Override
    protected void initView() {
        super.initView();
        initPopShareView();
        Bundle bundle = this.getIntent().getExtras();
        sellerId = bundle.getString("sellerId");
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(businessRefreshLayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                BusinessDetailActivity.this.setRetryEvent(retryView);
            }
        });
        productsBeanListTotal = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        businessListRl.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.main_line_view)));
        businessListRl.setLayoutManager(layoutManager);
        businessAdapter = new BusinessAdapter(R.layout.item_home_three_list, productsBeanListTotal);
        businessListRl.setAdapter(businessAdapter);


        businessAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("pid", String.valueOf(productsBeanListTotal.get(position).getPId()));
                bundle.putBoolean("panic", false);
                BaseActivity.navigate(BusinessDetailActivity.this, GoodsDetailsActivity.class, bundle);
            }
        });
        businessAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                businessListRl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            businessAdapter.loadMoreEnd();

                        } else {
                            currPage++;
                            getListData(currPage);
                            businessAdapter.loadMoreComplete();
                        }
                    }
                }, 2000);
            }
        }, businessListRl);


        businessRefreshLayout.setRefreshHeader(new RefreshView(BusinessDetailActivity.this));
        businessRefreshLayout.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_25));
        businessRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currPage = 0;
                        productsBeanListTotal.clear();
                        getData(0);
                        businessRefreshLayout.finishRefresh();
                    }
                }, Constans.Refresh_Time);

            }

        });

    }

    private void getListData(int currPage) {
        productsBeanList = new ArrayList<>();
        productsBeanList.clear();

        OkGo.<String>post(ServiceApi.sellerList)
                .tag(this)
                .params("sellerId", sellerId)
                .params("pageNum", String.valueOf(currPage))
                .params("pageSize", Constans.COMMONPAGE)
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
                                BusinessDetailsBean businessDetailsBean = gson.fromJson(s, BusinessDetailsBean.class);
                                productsBeanList.addAll(businessDetailsBean.getData().getProducts());
                                businessAdapter.addData(productsBeanList);
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
                .setFocusAndOutsideEnable(true)
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
                easySharePopup.dismiss();
                switch (position) {
                    case 0:
                        ShareUtil.getInstance(BusinessDetailActivity.this).ShareApp((SHARE_MEDIA.WEIXIN), title, imageUrl, contant, shareUrl);
                        break;
                    case 1:
                        ShareUtil.getInstance(BusinessDetailActivity.this).ShareApp((SHARE_MEDIA.WEIXIN_CIRCLE), title, imageUrl, contant, shareUrl);
                        break;
                    case 2:
                        RxToast.error("暂未开放");
//                        ShareUtil.getInstance(BusinessDetailActivity.this).ShareApp((SHARE_MEDIA.QQ), title, imageUrl, contant, shareUrl);
                        break;
                    case 3:
                        RxToast.error("暂未开放");
//                        ShareUtil.getInstance(BusinessDetailActivity.this).ShareApp((SHARE_MEDIA.QZONE), title, imageUrl, contant, shareUrl);
                        break;
                    case 4:
                        ShareUtil.getInstance(BusinessDetailActivity.this).ShareApp((SHARE_MEDIA.SINA), title, imageUrl, contant, shareUrl);
                        break;
                    case 5:
                        ClipboardManager cm = (ClipboardManager) BusinessDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(shareUrl);
                        RxToast.success("复制成功");
                        break;

                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getData(0);
    }

    private void getData(int page) {
        productsBeanList = new ArrayList<>();
        productsBeanList.clear();
        OkGo.<String>post(ServiceApi.sellerDetail)
                .tag(this)
                .params("sellerId", sellerId)
                .params("pageNum", String.valueOf(page))
                .params("pageSize", Constans.COMMONPAGE)
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
                                BusinessDetailsBean businessDetailsBean = gson.fromJson(s, BusinessDetailsBean.class);
                                CommonUtil.loadRlImage(BusinessDetailActivity.this, businessDetailsBean.getData().getSeller().getBackPic(), businessTopRl, businessTopRl.getWidth(), businessTopRl.getHeight());
                                CommonUtil.loadImage(BusinessDetailActivity.this, businessDetailsBean.getData().getSeller().getSellerPic(), businessTopHead);
                                buyMustKnow.setText(businessDetailsBean.getData().getSeller().getSellerIntro());
                                sellerName.setText(businessDetailsBean.getData().getSeller().getSellerName());
                                fragmentBusinessTitle.setText(businessDetailsBean.getData().getSeller().getSellerName());
                                if (businessDetailsBean.getData().getSeller().isSellerAuthen()) {
                                    sellerImage1.setBackgroundResource(R.mipmap.business_image1);
                                }
                                if (businessDetailsBean.getData().getSeller().isCreditGuarantee()) {
                                    sellerImage2.setBackgroundResource(R.mipmap.business_image2);
                                }
                                if (!RxDataTool.isEmpty(businessDetailsBean.getData().getProducts())) {
                                    productsBeanList.addAll(businessDetailsBean.getData().getProducts());
                                    sellerGoodNum.setText("(" + businessDetailsBean.getProductCount() + ")");
                                    showList();
                                }
                                pageCount = businessDetailsBean.getPageCount();
                                title = businessDetailsBean.getData().getSeller().getSellerName();
                                imageUrl = businessDetailsBean.getData().getSeller().getSellerPic();
                                contant = businessDetailsBean.getData().getSeller().getSellerIntro();
                                shareUrl = businessDetailsBean.getData().getSellerShareUrl();

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

    private void showList() {
        productsBeanListTotal.addAll(productsBeanList);
        businessAdapter.setNewData(productsBeanListTotal);
    }

    @Override
    protected void setListener() {
        businessAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, final int verticalOffset) {
                businessAppBar.post(new Runnable() {
                    @Override
                    public void run() {
                        float alpha = (float) (-verticalOffset) / businessTopRl.getHeight() * 255;
                        if (verticalOffset > -businessTopRl.getHeight() / 3) {
                            businessTopBarRl.getBackground().setAlpha((int) alpha);
                            fragmentBusinessTitle.setAlpha(alpha / 255);
                        } else {

                            fragmentBusinessTitle.setAlpha(1);
                            businessTopBarRl.getBackground().setAlpha(255);
                        }
                    }
                });

            }
        });


    }


    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessDetailActivity.this.initData();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.header_Back_rl, R.id.dpbusiness_details_share_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_Back_rl:
                finish();
                break;
            case R.id.dpbusiness_details_share_rl:
                easySharePopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
