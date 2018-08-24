package com.padacn.xmgoing.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.activity.GoodsSureActivity;
import com.padacn.xmgoing.activity.MyFootprintActivity1;
import com.padacn.xmgoing.adapter.ShopCarAdapter1;
import com.padacn.xmgoing.adapter.ShopCarRecommonAdapter;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.callback.OnClickAddCloseListenter;
import com.padacn.xmgoing.callback.OnClickDeleteListenter;
import com.padacn.xmgoing.callback.OnClickListenterModel;
import com.padacn.xmgoing.callback.OnViewItemClickListener;
import com.padacn.xmgoing.model.CartInfo;
import com.padacn.xmgoing.model.GoodSureBean;
import com.padacn.xmgoing.model.ShopCarBean;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.util.CommonDialogUtil;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.util.even.EvenMainUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.util.spaces.SpacesItemDecoration;
import com.padacn.xmgoing.view.RefreshView;
import com.padacn.xmgoing.widget.NestedExpandaleListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

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
import butterknife.Unbinder;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;
import static com.zaaach.citypicker.adapter.GridListAdapter.SPAN_COUNT;

/**
 * Created by 46009 on 2018/4/26.
 */

public class ShopCarFragment1 extends BaseFragment {
    private static final String TAG = "ShopCarFragment";
    @BindView(R.id.cart_expandablelistview)
    NestedExpandaleListView cartExpandablelistview;
    @BindView(R.id.cart_num)
    TextView cartNum;
    @BindView(R.id.cart_money)
    TextView cartMoney;
    @BindView(R.id.cart_shopp_moular)
    Button cartShoppMoular;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.item_chlid_check)
    CheckBox itemChlidCheck;
    @BindView(R.id.fragment_ex_title)
    TextView fragmentExTitle;
    @BindView(R.id.shopCar_admin)
    TextView shopCarAdmin;
    @BindView(R.id.shopcar_del)
    TextView shopcarDel;
    @BindView(R.id.shopcar_admin_rl)
    RelativeLayout shopcarAdminRl;
    @BindView(R.id.ex_top_bar)
    Toolbar exTopBar;
    @BindView(R.id.all_select)
    LinearLayout allSelect;
    @BindView(R.id.shop_car_fagemnt_ll)
    LinearLayout shopCarFagemntLl;
    @BindView(R.id.mine_my_ex_rf)
    SmartRefreshLayout mineMyExRf;
    @BindView(R.id.error_view_image)
    ImageView errorViewImage;
    @BindView(R.id.shop_car_fagemnt_recycler)
    RecyclerView shopCarFagemntRecycler;
    @BindView(R.id.shop_car_fagemnt_bottom_ll)
    LinearLayout shopCarFagemntBottomLl;
    @BindView(R.id.shop_car_scroll)
    ScrollView shopCarScroll;

    //是否显示管理界面
    private boolean isAdmin = false;
    private boolean isSelect = false;
    private boolean isFirst = true;
    //是否用戶已經选择
    private boolean isUserSelect = false;
    private GoodSureBean goodSureBean;
    private String jsonString;
    Unbinder unbinder;
    CartInfo cartInfo;
    private ShopCarAdapter1 shopCarAdapter;
    //商品的价格
    private double price;
    //商品的数量
    private int num;
    //加载框架
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private ShopCarBean.DataBean.BuyCarBean buyCarBean;
    private List<ShopCarBean.DataBean.BuyCarBean> buyCarBeanList;
    private List<ShopCarBean.DataBean.BuyCarBean> newBuyCarBeanList;
    private List<GoodSureBean> goodSureBeanList;
    private List<SureGoodsBean> sureBuyBeanList;

    //推荐商品
    private List<ShopCarBean.DataBean.RecommondProductBean> recommondProductBeanList;
    private ShopCarRecommonAdapter shopCarRecommonAdapter;

    public static ShopCarFragment1 newInstance() {
        Bundle args = new Bundle();
        ShopCarFragment1 fragment = new ShopCarFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_shopcar;
    }

    @Override
    protected void initData() {
        super.initData();
        isFirst = false;
        showData();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: ");
        if (!isFirst && !hidden) {
//            mineMyExRf.autoRefresh();
            showData();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
        if (!isFirst) {
//            mineMyExRf.autoRefresh();
            showData();
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
//        mineMyExRf.setRefreshHeader(new RefreshView(getContext()));
//        mineMyExRf.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_30));
        mineMyExRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
//                animationDrawable.start();
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                        isAdmin = false;
                        cartShoppMoular.setClickable(true);
                        cartShoppMoular.setBackgroundResource(R.color.shopcar_colorOrder);
                        cartShoppMoular.setTextColor(getContext().getResources().getColor(R.color.common_ffffff));
                        shopCarAdmin.setText("管理");
                        shopcarAdminRl.setVisibility(View.GONE);
                        shopCarScroll.smoothScrollTo(0, 0);
                        mineMyExRf.finishRefresh();
                    }
                }, 0);
            }
        });

        cartExpandablelistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return true;
            }
        });

        cartExpandablelistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    @Override
    protected void initView() {
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(shopCarFagemntLl, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                ShopCarFragment1.this.setRetryEvent(retryView);
            }
        });

        shopCarAdapter = new ShopCarAdapter1(getContext(), cartExpandablelistview, buyCarBeanList);
        cartExpandablelistview.setAdapter(shopCarAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp_5);
        shopCarFagemntRecycler.addItemDecoration(new SpacesItemDecoration(2, spacingInPixels, true));
        shopCarFagemntRecycler.setLayoutManager(gridLayoutManager);
        shopCarRecommonAdapter = new ShopCarRecommonAdapter(R.layout.item_shopcar_recommon, recommondProductBeanList);
        shopCarFagemntRecycler.setAdapter(shopCarRecommonAdapter);
    }

    //刪除商品
    private void deleteShop(final int groupPosition, final int position) {
        CommonDialogUtil.showDialog(getContext());
        List<String> stringList = new ArrayList<>();
        stringList.add(String.valueOf(buyCarBeanList.get(groupPosition).getCars().get(position).getCarId()));
        Gson gson = new Gson();
        String json = gson.toJson(stringList, new TypeToken<List<String>>() {
        }.getType());
        OkGo.<String>post(ServiceApi.delete)
                .tag(this)
                .params("carId", json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            CommonDialogUtil.hideDialog();
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                buyCarBeanList.get(groupPosition).getCars().remove(buyCarBeanList.get(groupPosition).getCars().get(position));
                                if (buyCarBeanList.get(groupPosition).getCars().size() == 0) {
                                    buyCarBeanList.remove(buyCarBeanList.get(groupPosition));
                                }
                                showExpandData();
                                RxToast.success(msg);
                            } else {
                                RxToast.error(msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
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
     * 请求购物车数据
     */
    private void showData() {
        itemChlidCheck.setBackgroundResource(R.mipmap.round_check_selected);
        buyCarBeanList = new ArrayList<>();
        recommondProductBeanList = new ArrayList<>();
        recommondProductBeanList.clear();
        buyCarBeanList.clear();
        OkGo.<String>post(ServiceApi.userBuyCar)
                .tag(this)
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
                                ShopCarBean shopCarBean = gson.fromJson(s, ShopCarBean.class);
                                if (shopCarBean.getResult() == 1) {
                                    buyCarBeanList.addAll(shopCarBean.getData().getBuyCar());
                                    recommondProductBeanList.addAll(shopCarBean.getData().getRecommondProduct());
                                    showExpandData();
                                    showRecommond();
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
     * 展示商品
     */
    private void showRecommond() {
        shopCarRecommonAdapter.setNewData(recommondProductBeanList);
        shopCarRecommonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("pid", String.valueOf(recommondProductBeanList.get(position).getPId()));
                bundle.putBoolean("panic", false);
                BaseActivity.navigate(getContext(), GoodsDetailsActivity.class, bundle);
            }
        });
    }


    private void showExpandData() {
        if (buyCarBeanList.size() > 0) {
            cartExpandablelistview.setGroupIndicator(null);
            emptyView.setVisibility(View.GONE);
            shopCarFagemntBottomLl.setVisibility(View.VISIBLE);
            shopCarAdapter.setNewData(buyCarBeanList);
            int intgroupCount = buyCarBeanList.size();
            for (int i = 0; i < intgroupCount; i++) {
                cartExpandablelistview.expandGroup(i);

            }
            /**
             * 全选
             */
            shopCarAdapter.setOnItemClickListener(new OnViewItemClickListener() {
                @Override
                public void onItemClick(boolean isFlang, View view, int position) {
                    buyCarBeanList.get(position).setIscheck(isFlang);
                    int length = buyCarBeanList.get(position).getCars().size();
                    for (int i = 0; i < length; i++) {
                        buyCarBeanList.get(position).getCars().get(i).setIscheck(isFlang);
                    }
                    shopCarAdapter.notifyDataSetChanged();
                    showCommodityCalculation();
                }
            });


            /**
             * 单选
             */
            shopCarAdapter.setOnClickListenterModel(new OnClickListenterModel() {
                @Override
                public void onItemClick(boolean isFlang, View view, int onePosition, int position) {
                    buyCarBeanList.get(onePosition).getCars().get(position).setIscheck(isFlang);
                    int length = buyCarBeanList.get(onePosition).getCars().size();
                    for (int i = 0; i < length; i++) {
                        if (!buyCarBeanList.get(onePosition).getCars().get(i).ischeck()) {
                            if (!isFlang) {
                                buyCarBeanList.get(onePosition).setIscheck(isFlang);
                            }
                            shopCarAdapter.notifyDataSetChanged();
                            showCommodityCalculation();
                            return;
                        } else {
                            if (i == (length - 1)) {
                                buyCarBeanList.get(onePosition).setIscheck(isFlang);
                                shopCarAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    showCommodityCalculation();
                }
            });
            shopCarAdapter.setOnClickDeleteListenter(new OnClickDeleteListenter() {
                @Override
                public void onItemClick(View view, int onePosition, int position) {
                    deleteShop(onePosition, position);
                }
            });


            /***
             * 数量增加和减少
             */
            shopCarAdapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
                @Override
                public void onItemClick(View view, int index, int onePosition, int position, int num) {
                    if (index == 1) {
                        if (num > 1) {
                            buyCarBeanList.get(onePosition).getCars().get(position).setProNum((num - 1));
                            shopCarAdapter.notifyDataSetChanged();
                        }
                    } else {
                        buyCarBeanList.get(onePosition).getCars().get(position).setProNum((num + 1));
                        shopCarAdapter.notifyDataSetChanged();
                    }
                    showCommodityCalculation();
                }
            });
            showCommodityCalculation();
        } else {
            shopCarAdapter.setNewData(buyCarBeanList);
            shopCarFagemntBottomLl.setVisibility(View.GONE);
            cartExpandablelistview.setEmptyView(emptyView);
        }
    }


    private void showCommodityCalculation() {
        price = 0;
        num = 0;
        isSelect = true;
        for (int i = 0; i < buyCarBeanList.size(); i++) {
            for (int j = 0; j < buyCarBeanList.get(i).getCars().size(); j++) {
                if (buyCarBeanList.get(i).getCars().get(j).ischeck()) {
                    price += Double.valueOf((buyCarBeanList.get(i).getCars().get(j).getProNum() * Double.valueOf(buyCarBeanList.get(i).getCars().get(j).getDetailDto().getPrice())));
                    num++;
                } else {
                    isSelect = false;
                }
            }
        }

        if (isSelect) {
            itemChlidCheck.setBackgroundResource(R.mipmap.round_check_active);
        } else {
            itemChlidCheck.setBackgroundResource(R.mipmap.round_check_selected);
        }

        if (price == 0.0) {
            cartNum.setText("全选");
            cartMoney.setText(getContext().getResources().getString(R.string.moneny_string) + " " + StringUtil.replaceString("0.0"));
            return;
        }
        try {
            String money = String.valueOf(price);
//            cartNum.setText("共" + num + "件商品");
            cartNum.setText("全选");
            if (money.substring(money.indexOf("."), money.length()).length() > 2) {
//                cartMoney.setText("¥ " + money.substring(0, (money.indexOf(".") + 3)));

                cartMoney.setText(getContext().getResources().getString(R.string.moneny_string) + " " + StringUtil.replaceString(money.substring(0, (money.indexOf(".") + 3))));
                return;
            }
            cartMoney.setText(getContext().getResources().getString(R.string.moneny_string) + " " + StringUtil.replaceString(money.substring(0, (money.indexOf(".") + 2))));
//            cartMoney.setText("¥ " + money.substring(0, (money.indexOf(".") + 2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.cart_shopp_moular, R.id.all_select, R.id.shopCar_admin, R.id.shopcar_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cart_shopp_moular:
                if (!checkUseSelect()) {
                    RxToast.error("没有选中的商品");
                    return;
                }
                checkStore();
                break;
            case R.id.all_select:
                selectAll(isSelect);
                break;

            case R.id.shopCar_admin:
                if (isAdmin) {
                    isAdmin = false;
                    cartShoppMoular.setClickable(true);
                    cartShoppMoular.setBackgroundResource(R.color.shopcar_colorOrder);
                    cartShoppMoular.setTextColor(getContext().getResources().getColor(R.color.common_ffffff));
                    shopCarAdmin.setText("管理");
                    shopcarAdminRl.setVisibility(View.GONE);
                } else {
                    isAdmin = true;
                    cartShoppMoular.setClickable(false);
                    cartShoppMoular.setBackgroundResource(R.color.common_c);
                    cartShoppMoular.setTextColor(getContext().getResources().getColor(R.color.common_text_color_6));
                    shopCarAdmin.setText("完成");
                    shopcarAdminRl.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.shopcar_del:
                if (!checkUseSelect()) {
                    RxToast.error("没有选中的商品");
                    return;
                }
                clickDelete();
                break;
        }

    }

    /**
     * 管理点击删除
     */
    private void clickDelete() {
        List<String> stringList = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < buyCarBeanList.size(); i++) {
            for (int j = 0; j < buyCarBeanList.get(i).getCars().size(); j++) {
                if (buyCarBeanList.get(i).getCars().get(j).ischeck()) {
                    stringList.add(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getCarId()));
                }
            }
        }

        String json = gson.toJson(stringList, new TypeToken<List<String>>() {
        }.getType());
        OkGo.<String>post(ServiceApi.delete)
                .tag(this)
                .params("carId", json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                for (int i = buyCarBeanList.size() - 1; i > -1; i--) {
                                    for (int j = buyCarBeanList.get(i).getCars().size() - 1; j > -1; j--) {
                                        if (buyCarBeanList.get(i).getCars().get(j).ischeck()) {
                                            buyCarBeanList.get(i).getCars().remove(buyCarBeanList.get(i).getCars().get(j));
                                            if (buyCarBeanList.get(i).getCars().size() == 0) {
                                                buyCarBeanList.remove(buyCarBeanList.get(i));
                                            }
                                        }
                                    }
                                }
                                showExpandData();
                                RxToast.success(msg);
                            } else {
                                RxToast.error(msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    //是否用户有已选中的商品
    private boolean checkUseSelect() {
        isUserSelect = false;
        for (int i = 0; i < buyCarBeanList.size(); i++) {
            for (int j = 0; j < buyCarBeanList.get(i).getCars().size(); j++) {
                if (buyCarBeanList.get(i).getCars().get(j).ischeck()) {
                    isUserSelect = true;
                }
            }
        }
        return isUserSelect;
    }

    private void selectAll(boolean isSelect) {
        isSelect = !isSelect;
        if (isSelect) {
            itemChlidCheck.setBackgroundResource(R.mipmap.round_check_active);
        } else {
            itemChlidCheck.setBackgroundResource(R.mipmap.round_check_selected);
        }
        for (int i = 0; i < buyCarBeanList.size(); i++) {
            for (int j = 0; j < buyCarBeanList.get(i).getCars().size(); j++) {
                buyCarBeanList.get(i).getCars().get(j).setIscheck(isSelect);
                buyCarBeanList.get(i).setIscheck(isSelect);
            }
        }
        shopCarAdapter.notifyDataSetChanged();
        showCommodityCalculation();
    }


    private String setUpString() {
        String upString = null;
        JSONArray sellerGoods = new JSONArray();
        try {
            for (int i = 0; i < buyCarBeanList.size(); i++) {
                JSONObject object = new JSONObject();
                JSONArray jsonarray = new JSONArray();
                for (int j = 0; j < buyCarBeanList.get(i).getCars().size(); j++) {
                    if (buyCarBeanList.get(i).getCars().get(j).ischeck()) {
                        JSONObject jsonObj = new JSONObject();//商品的jsonObject
                        jsonObj.put("pid", buyCarBeanList.get(i).getCars().get(j).getProId());
                        jsonObj.put("pName", buyCarBeanList.get(i).getCars().get(j).getDetailDto().getPName());
                        jsonObj.put("pnum", buyCarBeanList.get(i).getCars().get(j).getProNum());
                        jsonObj.put("tid", buyCarBeanList.get(i).getCars().get(j).getTicketId());
                        jsonObj.put("carId", buyCarBeanList.get(i).getCars().get(j).getCarId());
                        if (!RxDataTool.isNullString(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getCalenderId()))) {
                            jsonObj.put("cid", buyCarBeanList.get(i).getCars().get(j).getCalenderId());
                        }
                        jsonarray.put(jsonObj);
                    }
                }
                object.put("sellerId", buyCarBeanList.get(i).getSellerId());
                object.put("goods", jsonarray);
                sellerGoods.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        upString = sellerGoods.toString();

        return upString;
    }

    private void setSureString() {
        sureBuyBeanList = new ArrayList<>();
        for (int i = 0; i < buyCarBeanList.size(); i++) {
            SureGoodsBean sureGoodsBean = new SureGoodsBean();
            List<SureGoodsBean.GoodsBean> goodsBeanList = new ArrayList<>();
            for (int j = 0; j < buyCarBeanList.get(i).getCars().size(); j++) {
                if (buyCarBeanList.get(i).getCars().get(j).ischeck()) {
                    SureGoodsBean.GoodsBean goodsBean = new SureGoodsBean.GoodsBean();
                    goodsBean.setPid(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getProId()));
                    goodsBean.setPName(buyCarBeanList.get(i).getCars().get(j).getDetailDto().getPName());
                    goodsBean.setTotalPrice(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getProNum() * buyCarBeanList.get(i).getCars().get(j).getDetailDto().getPrice()));
                    goodsBean.setPrice(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getDetailDto().getPrice()));
                    goodsBean.setUseIdcard(buyCarBeanList.get(i).getCars().get(j).isUseIdCard());
                    goodsBean.setTravelNum(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getTravleNum()));
                    goodsBean.setTid(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getTicketId()));
                    goodsBean.setPicPath(buyCarBeanList.get(i).getCars().get(j).getDetailDto().getPics().get(0).getPath());
                    goodsBean.setPnum(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getProNum()));
                    goodsBean.setTiketName(buyCarBeanList.get(i).getCars().get(j).getTicketName());
                    goodsBean.setCarId(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getCarId()));
                    if (!RxDataTool.isNullString(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getUseDay()))) {
                        goodsBean.setTravleTime(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getUseDay()));
                    }
                    if (!RxDataTool.isNullString(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getCalenderId()))) {
                        goodsBean.setCid(String.valueOf(buyCarBeanList.get(i).getCars().get(j).getCalenderId()));
                    }
                    goodsBeanList.add(goodsBean);
                }
            }
            sureGoodsBean.setGoods(goodsBeanList);
            sureGoodsBean.setSellerId(String.valueOf(buyCarBeanList.get(i).getSellerId()));
            sureBuyBeanList.add(sureGoodsBean);
        }
    }

    /**
     * 检查商品库存
     */
    private void checkStore() {
        CommonDialogUtil.showDialog(getContext());
        OkGo.<String>post(ServiceApi.checkStore)
                .tag(this)
                .params("sellergoods", setUpString())

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        CommonDialogUtil.hideDialog();
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("createOrderType", 2);
                                setSureString();
                                HomeThreeSelectPresenter.getSingleTon().saveSureGoodBeanList(sureBuyBeanList);
                                BaseActivity.navigate(getContext(), GoodsSureActivity.class, bundle);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonDialogUtil.hideDialog();
                    }
                });

    }

    /**
     * 首頁点击购物车刷新
     *
     * @param evenMainUtil
     */
    @Subscribe(sticky = true)
    public void onEvent(EvenMainUtil evenMainUtil) {
      /*  if (evenMainUtil.getPosition() == 2) {
//            mineMyExRf.autoRefresh();
            this.initData();
        }*/
//        showData();
    }
}
