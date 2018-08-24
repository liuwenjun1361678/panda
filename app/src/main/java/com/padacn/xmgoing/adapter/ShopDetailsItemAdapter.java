package com.padacn.xmgoing.adapter;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.BusinessDetailActivity;
import com.padacn.xmgoing.activity.CommentListActivity;
import com.padacn.xmgoing.activity.CouponsDetailActivity;
import com.padacn.xmgoing.activity.ExCouponsCenterActivity;
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.activity.LoginActivity;
import com.padacn.xmgoing.activity.SelectActivity;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.callback.OnClickBuyListenter;
import com.padacn.xmgoing.callback.OnGoodsShareClickListener;
import com.padacn.xmgoing.model.GoodSureBean;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.model.ShopDetailsItem;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.MyWebView;
import com.padacn.xmgoing.view.X5WebView;
import com.padacn.xmgoing.view.ZQRoundOvalImageView;
import com.padacn.xmgoing.view.dialog.GoodsExplainDialog;
import com.vondear.rxtools.view.RxToast;
import com.zyyoona7.popup.EasyPopup;

import jaydenxiao.com.expandabletextview.ExpandableTextView;

/**
 * Created by 46009 on 2018/5/10.
 */

public class ShopDetailsItemAdapter extends BaseMultiItemQuickAdapter<ShopDetailsItem, BaseViewHolder> {
    //套餐
    private List<GoodsDtailsBean.DataBean.TicketTypesBean> ticketTypesBeanList;
    private List<GoodsDtailsBean.DataBean.PromisesBean> detailsOne;

    private List<GoodsDtailsBean.SellerCouponBean> sellerCouponBeanList;
    //当前商品名称
    private String currGoodsName;
    //当前商品价钱
    private String currGoodsPrice;
    private String oldGoodsPrice;
    //当前卖出份数
    String currSaleNum;

    private GoodsDtailsBean dataDtails;
    private GoodSureBean goodSureBean;

    private SureGoodsBean sureGoodsBean;

    private GoodsDtailsBean.DataBean.ReviewsBean reviewsBean;
    //商店信息
    private GoodsDtailsBean.DataBean.SellerBean sellerBean;

    public ShopDetailsItemAdapter(List data) {
        super(data);
        sellerCouponBeanList = new ArrayList<>();
        detailsOne = new ArrayList<>();
        ticketTypesBeanList = new ArrayList<>();
        sureGoodsBean = new SureGoodsBean();

        goodSureBean = new GoodSureBean();
        dataDtails = new GoodsDtailsBean();
        addItemType(ShopDetailsItem.ONE, R.layout.shop_details_one);
        addItemType(ShopDetailsItem.TWO, R.layout.shop_details_two);
        addItemType(ShopDetailsItem.
                THREE, R.layout.shop_details_three);
        addItemType(ShopDetailsItem.FOUR, R.layout.shop_details_four);

    }


    public void setDetailsOne(List<GoodsDtailsBean.DataBean.PromisesBean> detailsOne, String currGoodsName,
                              String currGoodsPrice, String oldGoodsPrice, String currSaleNum) {
        this.detailsOne = detailsOne;
        this.currGoodsName = currGoodsName;
        this.currGoodsPrice = currGoodsPrice;
        this.oldGoodsPrice = oldGoodsPrice;
        this.currSaleNum = currSaleNum;

    }

    public void setDetailsTwo(List<GoodsDtailsBean.DataBean.TicketTypesBean> ticketTypesBeanList) {
        this.ticketTypesBeanList = ticketTypesBeanList;
    }

    public void setDetailsThree(GoodsDtailsBean.DataBean.SellerBean sellerBean) {
        this.sellerBean = sellerBean;
    }

    public void setDetails(GoodsDtailsBean dataDtails) {
        this.dataDtails = dataDtails;
    }


    @Override
    protected void convert(BaseViewHolder helper, ShopDetailsItem item) {

        switch (helper.getItemViewType()) {

            case ShopDetailsItem.ONE:
                bindShowDetailsTopData(helper);
                break;
            case ShopDetailsItem.TWO:
                bindShowTwo(helper);
                break;
            case ShopDetailsItem.THREE:
                bindShowThree(helper);
                break;
            case ShopDetailsItem.FOUR:
                bindShowFour(helper);
                break;

        }
    }

    private void bindShowTwo(BaseViewHolder helper) {
        RelativeLayout relativeLayout = helper.getView(R.id.shop_details_no_comment);
        LinearLayout linearLayout = helper.getView(R.id.shop_details_comment_ll);
        ZQRoundOvalImageView imageViewHead = helper.getView(R.id.shop_details_comment_head);
        TextView textViewMore = helper.getView(R.id.shop_details_comment_more);
        //评论
        if (dataDtails.getData().getReviewsCount() > 0) {
            relativeLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            helper.setText(R.id.shop_details_comment_num, "(" + dataDtails.getData().getReviewsCount() + ")");
            CommonUtil.loadImage(mContext, String.valueOf(dataDtails.getData().getReviews().getUserSculpture()), imageViewHead);
            textViewMore.setVisibility(View.VISIBLE);
            helper.setText(R.id.shop_details_comment_name, dataDtails.getData().getReviews().getUserName());
            helper.setText(R.id.shop_details_comment_time, dataDtails.getData().getReviews().getReviewDate());
            helper.setText(R.id.shop_details_comment_content, dataDtails.getData().getReviews().getReviews());

            textViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("pid", String.valueOf(dataDtails.getData().getDetailDto().getPId()));
                    BaseActivity.navigate(mContext, CommentListActivity.class, bundle);
                }
            });

        } else {
            helper.setText(R.id.shop_details_comment_num, "(0)");
            textViewMore.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }

        //商店
        helper.setText(R.id.seller_name, sellerBean.getSellerName());
        helper.setText(R.id.seller_contant, sellerBean.getSellerIntro());
        helper.setText(R.id.seller_score, String.valueOf(sellerBean.getScore()));
        CommonUtil.loadImage(mContext, sellerBean.getSellerPic(), (ZQRoundOvalImageView) helper.getView(R.id.seller_image));

        LinearLayout businessGo = helper.getView(R.id.business_go_rl);
        businessGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sellerId", String.valueOf(sellerBean.getSellerId()));
                BaseActivity.navigate(mContext, BusinessDetailActivity.class, bundle);
            }
        });

        ImageView sellImage1 = helper.getView(R.id.seller_image_1);
        ImageView sellImage2 = helper.getView(R.id.seller_image_2);

        if (sellerBean.isSellerAuthen()) {
            sellImage1.setBackgroundResource(R.mipmap.business_image1);
        }
        if (sellerBean.isCreditGuarantee()) {
            sellImage2.setBackgroundResource(R.mipmap.business_image2);
        }
    }

    private void bindShowFour(BaseViewHolder helper) {

        MyWebView webView = helper.getView(R.id.shop_details_recommon_webview);
        webView.loadUrl(dataDtails.getData().getRecommonUrl());
    }

    private void bindShowThree(BaseViewHolder helper) {
//        helper.setIsRecyclable(false);
        final MyWebView x5WebView = helper.getView(R.id.shop_details_webview);
        x5WebView.setNestedScrollingEnabled(false);
//        x5WebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        final LoadingAndRetryManager mLoadingAndRetryManager = LoadingAndRetryManager.generate(x5WebView, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                View view = retryView.findViewById(R.id.btn_retry);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        x5WebView.loadUrl(dataDtails.getData().getIntroUrl());
                    }
                });
            }

            @Override
            public View generateLoadingLayout() {
                View view = LayoutInflater.from(mContext).inflate(R.layout.common_loading_view_web, null);
                return view;
            }
        });
        mLoadingAndRetryManager.showLoading();
        x5WebView.loadUrl(dataDtails.getData().getIntroUrl());
        x5WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    mLoadingAndRetryManager.showContent();
                }
            }
        });

        x5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mLoadingAndRetryManager.showRetry();
            }
        });
        helper.setText(R.id.buy_cancel_know_name, dataDtails.getData().getCancelPolicy().getName());
        helper.setText(R.id.buy_must_know_name, dataDtails.getData().getNotesToBuy().getName());
        ExpandableTextView expandableTextView1 = helper.getView(R.id.buy_must_know);
        ExpandableTextView expandableTextView2 = helper.getView(R.id.buy_cancel_know);
        expandableTextView1.setText(dataDtails.getData().getNotesToBuy().getContent());
        expandableTextView2.setText(dataDtails.getData().getCancelPolicy().getContent());
    }

    private void bindShowDetailsTopData(BaseViewHolder helper) {
        helper.setText(R.id.good_details_one_number, "月销" + currSaleNum + "笔");
        helper.setText(R.id.good_details_one_title, currGoodsName);
        helper.setText(R.id.good_details_one_price, currGoodsPrice);
        TextView oldPrice = helper.getView(R.id.good_details_one_old_rice);
        helper.setText(R.id.good_details_one_old_rice, mContext.getResources().getString(R.string.moneny_string) + String.valueOf(oldGoodsPrice));
        oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
        RecyclerView reStoreCouponsRl = helper.getView(R.id.good_details_security_recyclerView);
        reStoreCouponsRl.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        GoodSecurityAdapter goodSecurityAdapter = new GoodSecurityAdapter(R.layout.item_good_security, detailsOne);
        reStoreCouponsRl.setAdapter(goodSecurityAdapter);

        LinearLayout share_ll = helper.getView(R.id.good_details_share_ll);
        share_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoodsShareClickListener.onClick();
            }
        });

        final EasyPopup easyPopup = new EasyPopup(mContext)
                .setContentView(R.layout.service_description_pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setFocusAndOutsideEnable(true)
                .setAnimationStyle(R.style.dialogWindowAnim)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f)
                .apply();

        RelativeLayout right = helper.getView(R.id.good_details_security_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyPopup.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        });

        RecyclerView serviceRecy = easyPopup.findViewById(R.id.service_recycler);
        serviceRecy.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        GoodServiceSecurityAdapter goodServiceSecurityAdapter = new GoodServiceSecurityAdapter(R.layout.item_service_descrption, detailsOne);
        serviceRecy.setAdapter(goodServiceSecurityAdapter);

        RelativeLayout cancel = easyPopup.findViewById(R.id.service_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyPopup.dismiss();
            }
        });

        helper.setText(R.id.ticket_number, "(" + ticketTypesBeanList.size() + "个)");

        RecyclerView rePackageRl = helper.getView(R.id.shop_details_two_rc);
        rePackageRl.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        GoodPackageAdapter goodPackageAdapter = new GoodPackageAdapter(R.layout.item_shop_details_two, ticketTypesBeanList);
        rePackageRl.setAdapter(goodPackageAdapter);
        goodPackageAdapter.setStatu(dataDtails.getData().getDetailDto().getStatu());
        //点击购买套餐商品
        goodPackageAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (dataDtails.getData().getDetailDto().getStatu() != 1) {
                    return;
                }
                if (!SharePreferencesUtil.getBooleanSharePreferences(mContext, Constans.isAlreadyLogin, false)) {
                    BaseActivity.navigate(mContext, LoginActivity.class);
                    return;
                }
                if (view.getId() == R.id.ticketTypes_buy) {
                    int createOrderType = 1;
                    Bundle bundle = new Bundle();
                    bundle.putInt("createOrderType", createOrderType);
                    bundle.putSerializable("sureGoodsBean", sureGoodsBean);
                    bundle.putBoolean("isAddCar", false);
                    List<GoodsDtailsBean.DataBean.TicketTypesBean> list = new ArrayList<>();
                    list.add(ticketTypesBeanList.get(position));
                    HomeThreeSelectPresenter.getSingleTon().saveticketTypes(list);
                    BaseActivity.navigate(mContext, SelectActivity.class, bundle);
                }
                if (view.getId() == R.id.ticketTypes_instructions) {
                    final GoodsExplainDialog goodsExplainDialog = new GoodsExplainDialog(mContext, ticketTypesBeanList.get(position).getTicketName(),
                            ticketTypesBeanList.get(position).getTicketExplain(), StringUtil.replaceString(String.valueOf(ticketTypesBeanList.get(position).getPrice())));
                    goodsExplainDialog.onCreateView();
                    goodsExplainDialog.setUiBeforShow();
                    goodsExplainDialog.setCanceledOnTouchOutside(false);
                    goodsExplainDialog.show();
                    goodsExplainDialog.setOnClickBuyListenter(new OnClickBuyListenter() {
                        @Override
                        public void onItemClick() {
                            goodsExplainDialog.cancel();
                            int createOrderType = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("createOrderType", createOrderType);
                            bundle.putSerializable("sureGoodsBean", sureGoodsBean);
                            bundle.putBoolean("isAddCar", false);
                            List<GoodsDtailsBean.DataBean.TicketTypesBean> list = new ArrayList<>();
                            list.add(ticketTypesBeanList.get(position));
                            HomeThreeSelectPresenter.getSingleTon().saveticketTypes(list);
                            BaseActivity.navigate(mContext, SelectActivity.class, bundle);
                        }
                    });
                }

            }
        });

        //店铺优惠卷
        LinearLayout linearLayout = helper.getView(R.id.shop_details_store_coupons_ll);
        sellerCouponBeanList.addAll(dataDtails.getSellerCoupon());
        //如果优惠卷为空就隐藏
        if (sellerCouponBeanList.size() <= 0) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }
        RecyclerView rl = helper.getView(R.id.shop_details_store_coupons_rl);
        rl.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        GoodsDetailCouponsAdapter goodsDetailCouponsAdapter = new GoodsDetailCouponsAdapter(R.layout.item_shop_details_store_coupns, sellerCouponBeanList);
        rl.setAdapter(goodsDetailCouponsAdapter);

        //领取优惠卷
        goodsDetailCouponsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("sellerName", sellerCouponBeanList.get(position).getSellerName());
                bundle.putInt("couponType", sellerCouponBeanList.get(position).getCouponType());
                bundle.putString("typeName", sellerCouponBeanList.get(position).getTypeName());
                bundle.putString("reduction", String.valueOf(sellerCouponBeanList.get(position).getReduction()));
                bundle.putString("term", String.valueOf(sellerCouponBeanList.get(position).getTerm()));
                bundle.putBoolean("flashUse", sellerCouponBeanList.get(position).isFlashUse());
                bundle.putString("useTime", String.valueOf(sellerCouponBeanList.get(position).getUseTime()));
                bundle.putString("cid", String.valueOf(sellerCouponBeanList.get(position).getCouponId()));
                bundle.putString("voidTime", String.valueOf(sellerCouponBeanList.get(position).getVoidTime()));

                BaseActivity.navigate(mContext, CouponsDetailActivity.class, bundle);

            }
        });
    }

    public void setGoodSure(GoodSureBean goodSureBean) {
        this.goodSureBean = goodSureBean;

    }

    public void setSureGood(SureGoodsBean sureGoodsBean) {
        this.sureGoodsBean = sureGoodsBean;

    }


    // 数量接口的方法
    private OnGoodsShareClickListener onGoodsShareClickListener = null;

    public void setOnGoodsShareClickListener(OnGoodsShareClickListener listener) {
        this.onGoodsShareClickListener = listener;
    }
}
