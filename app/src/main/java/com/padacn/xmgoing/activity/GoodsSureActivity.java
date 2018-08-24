package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.barlibrary.OnKeyboardListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.CanUseAdapter;
import com.padacn.xmgoing.adapter.GoodSureAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.BestCoupons;
import com.padacn.xmgoing.model.ContactListBean;
import com.padacn.xmgoing.model.GoodSureBean;
import com.padacn.xmgoing.model.OrderBean;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.model.UserCoupon;
import com.padacn.xmgoing.presenter.ContactEventil;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.util.DoubleArith;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.CustomToolBar;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;
import com.zyyoona7.popup.EasyPopup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class GoodsSureActivity extends BaseActivity {
    private static final String TAG = "GoodsSureActivity";
    //    public static GoodsSureActivity instanceSure;
    @BindView(R.id.shop_sure_topbar)
    CustomToolBar shopSureTopbar;
    @BindView(R.id.goods_sure_recyclerView)
    RecyclerView goodsSureRecyclerView;
    @BindView(R.id.can_use_coupons)
    TextView canUseCoupons;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.ruduce_price)
    TextView ruducePrice;
    @BindView(R.id.real_price)
    TextView realPrice;
    @BindView(R.id.last_price)
    TextView lastPrice;
    @BindView(R.id.go_payment)
    TextView goPayment;
    @BindView(R.id.goods_sure_ll)
    LinearLayout goodsSureLl;
    @BindView(R.id.go_select_more_coupons)
    LinearLayout goSelectMoreCoupons;
    @BindView(R.id.goods_sure_name)
    MClearEditText goodsSureName;
    @BindView(R.id.goods_sure_phone)
    MClearEditText goodsSurePhone;
    @BindView(R.id.shop_buy_sure_no_coupons)
    TextView shopBuySureNoCoupons;
    @BindView(R.id.goods_bootom_ll)
    LinearLayout goodsBootomLl;
    @BindView(R.id.good_sure_meal)
    TextView goodSureMeal;
    //商品的adapter
    private GoodSureAdapter goodSureAdapter;
    private List<GoodSureBean> goodSureBeanList;

    private String jsonString;
    private JSONArray jsonArray;
    //最优优惠卷的数据
    private List<BestCoupons.DataBean.CouponBean> couponBeanList;
    //當前商品对应的价钱
    private List<BestCoupons.DataBean.PriceBean> priceBeanList;
    //用户可用的优惠卷
    private List<UserCoupon.DataBean> dataBeanList;
    //底部弹窗
    private EasyPopup easyPopup;
    private CanUseAdapter canUseAdapter;
    //当前是否是全平台卷
    private boolean isAll;
    //当前用户选择优惠卷总价
    private int currUserCouponsPrice;
    //当前优惠卷
    private int currCouponsPrice;
    //当前用户实际支付价钱
    private String userPayPrice;
    private double goodTotalPrice;
    private List<String> stringList;
    //1、是商品直接生成   2是通過訂單生成
    private int createOrderType;
    //當前生成訂單的url
    private String currUrl;
    private String upString = null;
    private List<SureGoodsBean> sureGoodsBeanList;
    private SureGoodsBean sureGoodsBean;
    private List<SureGoodsBean.GoodsBean> goodsBeanList;
    //用戶點擊購買上傳的數據
    private String listUpString;
    //用戶是否全部選擇
    private boolean isAllSeleContact = true;
    private List<SureGoodsBean.GoodsBean.ContactIdsBean> contactIdsBeanList;
    //用户选择出行人的数据list
    private List<ContactListBean> contactsDataBeanList;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                goodsBootomLl.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(MessageEvent event) {
        if (event.getCode() == EvenCode.ClearSure) {
            finish();
        }
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_shop_buy_sure;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .navigationBarColor(R.color.home_top_text_color)
                .keyboardEnable(true)
                .setOnKeyboardListener(new OnKeyboardListener() {
                    @Override
                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                        if (isPopup) {
                            goodsBootomLl.setVisibility(View.GONE);
                        } else {
                            handler.sendEmptyMessage(1);
                        }
                    }
                })
                .init();
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void initView() {
        super.initView();
//        instanceSure = this;
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        EventBus.getDefault().register(this);  //注册
        contactIdsBeanList = new ArrayList<>();
        contactsDataBeanList = new ArrayList<>();
        //清除用户选择的联系人
        HomeThreeSelectPresenter.getSingleTon().removeContactList();
        Bundle bundle = this.getIntent().getExtras();
//        jsonString = bundle.getString("jsonString");
        createOrderType = bundle.getInt("createOrderType");
        if (createOrderType == 1) {
            currUrl = ServiceApi.createOrder;
        } else if (createOrderType == 2) {
            currUrl = ServiceApi.createOrderByCars;
        }
        shopSureTopbar.setStyle("订单确认", Color.parseColor("#f8d948"));
        sureGoodsBeanList = HomeThreeSelectPresenter.getSingleTon().getSureGoodsBeanList();
        Log.e(TAG, "setGoods:444 " + String.valueOf(sureGoodsBeanList.get(0).getSellerId()));
        Log.e(TAG, "initView: " + sureGoodsBeanList.size());
        goodsBeanList = new ArrayList<>();
        for (int i = 0; i < sureGoodsBeanList.size(); i++) {
            goodsBeanList.addAll(sureGoodsBeanList.get(i).getGoods());
        }

        HomeThreeSelectPresenter.getSingleTon().InitContact(goodsBeanList.size());
        goodsSureRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        goodSureAdapter = new GoodSureAdapter(R.layout.item_good_sure, goodsBeanList);
        goodsSureRecyclerView.setAdapter(goodSureAdapter);
        goodSureAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.buy_you_know) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "购买须知");
                    bundle.putString("url", ServiceApi.clause_app);
                    BaseActivity.navigate(GoodsSureActivity.this, WebDetailsActivity.class, bundle);
                }
            }
        });
        setUpJsonString(true);
    }

    @Override
    protected void initData() {
        getCoupon();
    }

    /**
     * 获取最优优惠卷
     */
    private void getCoupon() {
        couponBeanList = new ArrayList<>();
        priceBeanList = new ArrayList<>();
        stringList = new ArrayList<>();
        //最优优惠卷
        OkGo.<String>post(ServiceApi.couponOptimal)
                .tag(this)
                .params("sellergoods", listUpString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            int useSellerCoupon = jsonObject.getInt("useSellerCoupon");
                            if (result == 1) {
                                Gson gson = new Gson();
                                BestCoupons bestCoupons = gson.fromJson(s, BestCoupons.class);
                                if (bestCoupons.getResult() == 1) {
                                    if (bestCoupons.getData().getCoupon().size() > 0) {
                                        shopBuySureNoCoupons.setVisibility(View.GONE);
                                        goSelectMoreCoupons.setVisibility(View.VISIBLE);
                                    } else {
                                        goSelectMoreCoupons.setVisibility(View.GONE);
                                        shopBuySureNoCoupons.setVisibility(View.VISIBLE);
                                    }
                                    priceBeanList.addAll(bestCoupons.getData().getPrice());
                                    couponBeanList.addAll(bestCoupons.getData().getCoupon());
                                    for (int i = 0; i < bestCoupons.getData().getCoupon().size(); i++) {
                                        if (bestCoupons.getData().getCoupon().get(i).getProCoupon().getCouponType() == 2) {
                                            if (bestCoupons.getData().getTotailPrice() > bestCoupons.getData().getCoupon().get(i).getProCoupon().getTerm()) {
                                                isAll = true;
                                                currCouponsPrice = bestCoupons.getData().getCoupon().get(i).getProCoupon().getReduction();
                                                stringList.add(String.valueOf(bestCoupons.getData().getCoupon().get(i).getProCoupon().getCouponId()));
                                                canUseCoupons.setText("￥" + String.valueOf(currCouponsPrice));
                                            }
                                        } else {
                                            stringList.add(String.valueOf(bestCoupons.getData().getCoupon().get(i).getProCoupon().getCouponId()));
                                            for (int j = 0; j < bestCoupons.getData().getPrice().size(); j++) {
                                                if (bestCoupons.getData().getPrice().get(j).getSellerId() == bestCoupons.getData().getCoupon().get(i).getSellerId()) {
                                                    if (bestCoupons.getData().getPrice().get(j).getPrice() > bestCoupons.getData().getCoupon().get(i).getProCoupon().getTerm()) {
                                                        currCouponsPrice += bestCoupons.getData().getCoupon().get(i).getProCoupon().getReduction();
                                                        canUseCoupons.setText("￥" + String.valueOf(currCouponsPrice));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    for (int i = 0; i < goodsBeanList.size(); i++) {
                                        if (createOrderType == 1) {
                                            goodTotalPrice = DoubleArith.mul(Double.valueOf(goodsBeanList.get(i).getPrice()), Double.valueOf(goodsBeanList.get(i).getPnum()));
                                        } else if (createOrderType == 2) {
                                            if (!RxDataTool.isNullString(goodsBeanList.get(i).getTotalPrice())) {
                                                goodTotalPrice = DoubleArith.add(goodTotalPrice, Double.valueOf(goodsBeanList.get(i).getTotalPrice()));
                                            }
                                        }
                                    }
                                    //商品本來價錢
                                    totalPrice.setText("￥" + goodTotalPrice);
                                    //优惠卷减免价钱
                                    ruducePrice.setText("-￥" + currCouponsPrice);
                                    //最后支付价钱
                                    realPrice.setText("￥" + String.valueOf(goodTotalPrice - currCouponsPrice));
                                    //实际价钱
                                    lastPrice.setText(String.valueOf(goodTotalPrice - currCouponsPrice));
                                    userPayPrice = String.valueOf(goodTotalPrice - currCouponsPrice);
                                    initPopView();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    /**
     * 底部分享view
     */
    private void initPopView() {
        easyPopup = new EasyPopup(this)
                .setContentView(R.layout.can_use_coupons, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setFocusAndOutsideEnable(false)
//                .setDimView(goodsSureLl)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f)
                .apply();

        RelativeLayout relativeLayout = easyPopup.findViewById(R.id.can_coupons_cancel);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringList.clear();
                currUserCouponsPrice = 0;
                for (int i = 0; i < dataBeanList.size(); i++) {
                    if (dataBeanList.get(i).isUserSelect()) {
                        currUserCouponsPrice += dataBeanList.get(i).getReduction();
                        stringList.add(String.valueOf(dataBeanList.get(i).getCouponId()));
                    }
                    //添加优惠卷数组
                }
                canUseCoupons.setText("￥" + String.valueOf(currUserCouponsPrice));
                ruducePrice.setText("-￥" + currUserCouponsPrice);
                realPrice.setText("￥" + String.valueOf(goodTotalPrice - currUserCouponsPrice));
                lastPrice.setText(String.valueOf(goodTotalPrice - currUserCouponsPrice));

                userPayPrice = String.valueOf(goodTotalPrice - currUserCouponsPrice);
                easyPopup.dismiss();
            }
        });

        getCanCoupons();

    }

    /**
     * 获取全场优惠卷
     */
    private void getCanCoupons() {
        dataBeanList = new ArrayList<>();
        OkGo.<String>post(ServiceApi.couponUseable)
                .tag(this)
                .headers("Area", Constans.Area)
                .params("sellergoods", listUpString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                UserCoupon userCoupon = gson.fromJson(s, UserCoupon.class);
                                if (userCoupon.getResult() == 1) {
                                    dataBeanList.addAll(userCoupon.getData());
                                    showCanCoupons();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    //展示可用的优惠卷
    private void showCanCoupons() {
        Log.e(TAG, "showCanCoupons: " + priceBeanList.size() + "____" + dataBeanList.size());
        for (int i = 0; i < couponBeanList.size(); i++) {//最优优惠卷
            for (int j = 0; j < dataBeanList.size(); j++) {//可用优惠卷
                Log.e(TAG, "showCanCoupons222: " + couponBeanList.get(i).getSellerId() + "____" + dataBeanList.get(j).getSellerId());
                if (couponBeanList.get(i).getSellerId() == null) {
                    if (couponBeanList.get(i).getProCoupon().getCouponId() == dataBeanList.get(j).getCouponId()) {
                        dataBeanList.get(j).setSelect(true);
                        dataBeanList.get(j).setUserSelect(true);
                        Log.e(TAG, "showCanCoupons111: " + dataBeanList.get(j).isUserSelect() + "____" + dataBeanList.get(j).isSelect());
                    }
                } else if (couponBeanList.get(i).getSellerId() == dataBeanList.get(j).getSellerId()) {
                    if (couponBeanList.get(i).getProCoupon().getCouponId() == dataBeanList.get(j).getCouponId()) {
                        dataBeanList.get(j).setSelect(true);
                        dataBeanList.get(j).setUserSelect(true);
                    }

                }
            }
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = easyPopup.findViewById(R.id.can_coupons_rc);
        recyclerView.setLayoutManager(layoutManager);
        canUseAdapter = new CanUseAdapter(R.layout.item_can_use_coupons, dataBeanList);
        recyclerView.setAdapter(canUseAdapter);
        canUseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
              /*  for (int j = 0; j < dataBeanList.size(); j++) {
                    dataBeanList.get(j).setSelect(false);
                }*/
//                canUseAdapter.setCurrSelectPosition(position);


                //如果默认使用的是全场优惠卷
                if (isAll) {
                    if (dataBeanList.get(position).getCouponType() == 1 || dataBeanList.get(position).getCouponType() == 3) {
                        //當前选中的是自营或者商家，判断所有商品是否满足优惠卷
                /*        for (int i = 0; i < goodsBeanList.size(); i++) {
                            if (dataBeanList.get(position).getTerm() > Integer.parseInt(goodsBeanList.get(i).getTotalPrice())) {
                                RxToast.error("优惠卷不可用");
                                return;
                            }
                        }*/
                        if (dataBeanList.get(position).isUserSelect()) {
                            dataBeanList.get(position).setUserSelect(false);
                            Log.e(TAG, "showCanCoupons333311111222: " + position + dataBeanList.get(position).isSelect());
                            //如果用户选中的是已选择，设置用户选中为false，刷新其他满足的卷可选中
                            for (int j = 0; j < dataBeanList.size(); j++) {//店家的商品金额
                                for (int i = 0; i < priceBeanList.size(); i++) {//店家的商品金额
                                    if (dataBeanList.get(j).getCouponType() == 2) {//如果优惠卷是其他卷
                                        dataBeanList.get(j).setSelect(true);
//                                        if (priceBeanList.get(i).getPrice() >= dataBeanList.get(j).getTerm()) {
//                                            dataBeanList.get(j).setSelect(true);
//                                        } else {
//                                            dataBeanList.get(j).setSelect(false);
//                                        }
                                    } else {
                                        if (priceBeanList.get(i).getSellerId() == dataBeanList.get(j).getSellerId()) {
                                            dataBeanList.get(j).setSelect(true);
//                                            if (priceBeanList.get(i).getPrice() >= dataBeanList.get(j).getTerm()) {
//                                                dataBeanList.get(j).setSelect(true);
//                                            } else {
//                                                dataBeanList.get(j).setSelect(false);
//                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (dataBeanList.get(position).isSelect()) {
                                dataBeanList.get(position).setUserSelect(true);
                                dataBeanList.get(position).setSelect(true);
                                for (int j = 0; j < dataBeanList.size(); j++) {
                                    if (j != position) {
                                        dataBeanList.get(j).setUserSelect(false);
                                        dataBeanList.get(j).setSelect(false);
                                    }
                                }
                                Log.e(TAG, "showCanCoupons333311111: " + position + dataBeanList.get(position).isSelect());
                            }
                        }


                    } else {
                        //当前选中的是全场卷
                        if (dataBeanList.get(position).isUserSelect()) {
                            dataBeanList.get(position).setUserSelect(false);
                            //如果用户选中的是已选择，设置用户选中为false，刷新其他满足的卷可选中
                            for (int j = 0; j < dataBeanList.size(); j++) {//店家的商品金额
                                for (int i = 0; i < priceBeanList.size(); i++) {//店家的商品金额
                                    if (dataBeanList.get(j).getCouponType() == 2) {//如果优惠卷是全场卷
                                        dataBeanList.get(j).setSelect(true);
//                                        if (priceBeanList.get(i).getPrice() >= dataBeanList.get(j).getTerm()) {
//                                            dataBeanList.get(j).setSelect(true);
//                                        } else {
//                                            dataBeanList.get(j).setSelect(false);
//                                        }
                                    } else {
                                        if (priceBeanList.get(i).getSellerId() == dataBeanList.get(j).getSellerId()) {
                                            dataBeanList.get(j).setSelect(true);
//                                            if (priceBeanList.get(i).getPrice() >= dataBeanList.get(j).getTerm()) {
//                                                dataBeanList.get(j).setSelect(true);
//                                            } else {
//                                                dataBeanList.get(j).setSelect(false);
//                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //如果选中的是其他
                            if (dataBeanList.get(position).isSelect()) {
                                dataBeanList.get(position).setUserSelect(true);
                                dataBeanList.get(position).setSelect(true);
                                for (int j = 0; j < dataBeanList.size(); j++) {
                                    if (j != position) {
                                        dataBeanList.get(j).setUserSelect(false);
                                        dataBeanList.get(j).setSelect(false);
                                    }
                                }
                                Log.e(TAG, "showCanCoupons3333: " + position + dataBeanList.get(position).isSelect());
                            }
                        }
                    }
                }
                //默认选中是自营或者商家
                else {
                    if (dataBeanList.get(position).getCouponType() == 1 || dataBeanList.get(position).getCouponType() == 3) {
                        //當前选中的是自营或者商家，判断所有商品是否满足优惠卷
                      /*  for (int i = 0; i < goodsBeanList.size(); i++) {
                            if ((int) dataBeanList.get(position).getTerm() > Double.parseDouble(goodsBeanList.get(i).getTotalPrice())) {
                                RxToast.error("优惠券不可用");
                                return;
                            }
                        }*/
                        //点击选中有默认选中的优惠卷
                        if (dataBeanList.get(position).isUserSelect()) {
                            int count = 0;
                            for (int i = 0; i < dataBeanList.size(); i++) {
                                if (dataBeanList.get(i).isUserSelect()) {
                                    count++;
                                }
                            }
                            if (count == 1) {
                                dataBeanList.get(position).setUserSelect(false);
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    dataBeanList.get(i).setUserSelect(false);
                                    dataBeanList.get(i).setSelect(true);
                                }
                            } else {
                                dataBeanList.get(position).setUserSelect(false);
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    if (dataBeanList.get(i).getSellerId() == dataBeanList.get(position).getSellerId()) {
                                        dataBeanList.get(i).setUserSelect(false);
                                        dataBeanList.get(i).setSelect(true);
                                    }
                                }
                            }
                        } else {
                            //如果当前全部为可选状态
                            if (dataBeanList.get(position).getCouponType() == 2) {//当前点击选中的是全场卷
                                dataBeanList.get(position).setUserSelect(true);
                                dataBeanList.get(position).setSelect(true);
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    if (i != position) {
                                        dataBeanList.get(i).setUserSelect(false);
                                        dataBeanList.get(i).setSelect(false);
                                    }
                                }
                            } else {//当前点击选中的是其他卷
                                dataBeanList.get(position).setUserSelect(true);
                                dataBeanList.get(position).setSelect(true);
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    if (dataBeanList.get(i).getCouponType() == 2) {//将全场卷全部设置不可选
                                        dataBeanList.get(i).setUserSelect(false);
                                        dataBeanList.get(i).setSelect(false);
                                    }
                                    if (i != position) {
                                        //当前点击的店家和其他点券店家一样，设置其他店家点券为false
                                        if (dataBeanList.get(position).getSellerId() == dataBeanList.get(i).getSellerId()) {
                                            dataBeanList.get(i).setUserSelect(false);
                                            dataBeanList.get(i).setSelect(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //默认选中商家优惠卷的时候，当前点击的点券是全场卷
                    else {
                        if (dataBeanList.get(position).isSelect()) {
                            if (dataBeanList.get(position).getCouponType() == 2) {
                                if (dataBeanList.get(position).isUserSelect()) {
                                    dataBeanList.get(position).setUserSelect(false);
                                    for (int i = 0; i < dataBeanList.size(); i++) {
                                        dataBeanList.get(i).setUserSelect(false);
                                        dataBeanList.get(i).setSelect(true);
                                    }
                                } else {
                                    dataBeanList.get(position).setUserSelect(true);
                                    dataBeanList.get(position).setSelect(true);
                                    for (int i = 0; i < dataBeanList.size(); i++) {
                                        if (i != position) {
                                            dataBeanList.get(i).setUserSelect(false);
                                            dataBeanList.get(i).setSelect(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                canUseAdapter.notifyDataSetChanged();


            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.go_payment, R.id.go_select_more_coupons, R.id.good_sure_meal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_payment:
                if (RxDataTool.isEmpty(goodsSureName.getText().toString())) {
                    RxToast.error("姓名不能为空！");
                    return;
                }

                if (RxDataTool.isEmpty(goodsSurePhone.getText().toString())) {
                    RxToast.error("手机号不能为空！");
                    return;
                }
                if (!StringUtil.isPhone(goodsSurePhone.getText().toString())) {
                    RxToast.error("请输入正确的手机号");
                    return;
                }
                /*if (!isAllSeleContact) {
                    RxToast.normal("请给商品选择出行人");
                    return;
                }*/
                createder();
                break;
            case R.id.go_select_more_coupons:
                easyPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.good_sure_meal:
                Bundle bundle = new Bundle();
                bundle.putString("title", "熊猫成长季协议");
                bundle.putString("url", ServiceApi.protocol_app);
                BaseActivity.navigate(GoodsSureActivity.this, WebDetailsActivity.class, bundle);
                break;
        }

    }

    //設置上傳服務器的數據
    private String setUpJsonString(boolean isGopay) {
        JSONArray sellIdjsonObj = new JSONArray();
        for (int i = 0; i < sureGoodsBeanList.size(); i++) {
            try {
                JSONArray toJsonArray = new JSONArray();
                JSONObject toBject = new JSONObject();
                for (int j = 0; j < sureGoodsBeanList.get(i).getGoods().size(); j++) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("pName", sureGoodsBeanList.get(i).getGoods().get(j).getPName());
                    jsonObj.put("pnum", sureGoodsBeanList.get(i).getGoods().get(j).getPnum());
                    jsonObj.put("tid", sureGoodsBeanList.get(i).getGoods().get(j).getTid());
                    jsonObj.put("pid", sureGoodsBeanList.get(i).getGoods().get(j).getPid());
                    if (!RxDataTool.isNullString(sureGoodsBeanList.get(i).getGoods().get(j).getCid())) {
                        jsonObj.put("cid", sureGoodsBeanList.get(i).getGoods().get(j).getCid());
                    }
                    if (!RxDataTool.isNullString(sureGoodsBeanList.get(i).getGoods().get(j).getCarId())) {
                        jsonObj.put("carId", sureGoodsBeanList.get(i).getGoods().get(j).getCarId());
                    }
//                    sureGoodsBeanList.get(i).getGoods().get(j).isUseIdcard()
                    if (isGopay) {
                        JSONArray contactArray = new JSONArray();

                        if (contactsDataBeanList.size() > 0) {
                            for (int k = 0; k < contactsDataBeanList.size(); k++) {
                                if (sureGoodsBeanList.get(i).getGoods().get(j).isUseIdcard() && (sureGoodsBeanList.get(i).getGoods().get(j).getTid()).equals(contactsDataBeanList.get(k).getPid())) {
                                    for (int z = 0; z < contactsDataBeanList.get(k).getContactIds().size(); z++) {
                                        if (!RxDataTool.isEmpty(contactsDataBeanList.get(k).getContactIds().get(z).getId())) {
                                            JSONObject contact = new JSONObject();
                                            contact.put("id", contactsDataBeanList.get(k).getContactIds().get(z).getId());
                                            contact.put("name", contactsDataBeanList.get(k).getContactIds().get(z).getName());
                                            contactArray.put(contact);
                                            jsonObj.put("contactIds", contactArray);
                                        }
                                    }
                                }
                               /* //如果必须出行人，但是用户无选择，则返回为false
                                if (contactArray.length() > 0) {
                                    isAllSeleContact = true;
                                } else {
                                    isAllSeleContact = false;
                                }*/
                            }
                        }

                    }
                    toJsonArray.put(jsonObj);
                }
                toBject.put("goods", toJsonArray);
                toBject.put("sellerId", sureGoodsBeanList.get(i).getSellerId());
                sellIdjsonObj.put(toBject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        upString = sellIdjsonObj.toString();
        listUpString = upString;
        Log.e(TAG, "setUpJsonString: " + isAllSeleContact + "_______" + listUpString);
        return upString;
    }


    private void createder() {
        //用戶選擇优惠卷ID
        JSONArray couponIds = new JSONArray(); //保存数组数据的JSONArray对象
        for (int i = 0; i < stringList.size(); i++) {  //依次将数组元素添加进JSONArray对象中
            couponIds.put(stringList.get(i).toString());
        }
        Log.e(TAG, "createder: " + listUpString);
        OkGo.<String>post(currUrl)
                .tag(this)
                .params("sellergoods", setUpJsonString(true))
                .params("bookedBy", goodsSureName.getText().toString())
                .params("couponIds", couponIds.toString())
                .params("tel", goodsSurePhone.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson1 = new Gson();
                                OrderBean orderBean = gson1.fromJson(s, OrderBean.class);
                                String orderId = String.valueOf(orderBean.getData().getId());
                                Bundle bundle = new Bundle();
                                bundle.putString("orderId", orderId);
                                bundle.putString("userPayPrice", userPayPrice);
                                BaseActivity.navigate(GoodsSureActivity.this, CashierActivity.class, bundle);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    //接收選擇聯繫人
    @Subscribe
    public void onEventMainThread(ContactEventil evenUtil) {
        contactsDataBeanList = HomeThreeSelectPresenter.getSingleTon().getContactList();
        setUpJsonString(true);
        goodSureAdapter.setList(contactsDataBeanList, goodsBeanList.size(), evenUtil.getPosition());
        goodSureAdapter.notifyDataSetChanged();
        Log.e(TAG, "onEventMainThread: " + contactsDataBeanList.size() + evenUtil.getPosition());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);//取消注册
//        instanceSure.finish();
    }

}
