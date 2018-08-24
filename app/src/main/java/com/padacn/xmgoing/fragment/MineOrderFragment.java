package com.padacn.xmgoing.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.CashierActivity;
import com.padacn.xmgoing.activity.GoodsSureActivity;
import com.padacn.xmgoing.activity.HomeSecondActivity;
import com.padacn.xmgoing.activity.OrderDetailsActivity;
import com.padacn.xmgoing.activity.RufundActivity;
import com.padacn.xmgoing.adapter.OrderItemAdapter;
import com.padacn.xmgoing.adapter.OrderItemAdapter1;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.callback.OnClickListenterOrder;
import com.padacn.xmgoing.callback.OnViewClickListener;
import com.padacn.xmgoing.model.AllOrderBean;
import com.padacn.xmgoing.model.OrderBean;
import com.padacn.xmgoing.model.OrderCommentBean;
import com.padacn.xmgoing.util.CommonDialogUtil;
import com.padacn.xmgoing.util.CommonUi;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.CustomViewPagerTab;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.ExCouponsCenterAdapter;
import com.padacn.xmgoing.model.ListActivityModel;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 46009 on 2018/5/5.
 */

@SuppressLint("ValidFragment")
public class MineOrderFragment extends BaseFragment {
    private static final String TAG = "MineOrderFragment";
    @BindView(R.id.mine_my_order_recyclerView)
    RecyclerView mineMyOrderRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.mine_my_order_rf)
    RefreshLayout mineMyOrderRf;
    private int currPage;
    private int pageCount;
    private int curTitle;
    private OrderItemAdapter1 orderItemAdapter;
    private List<AllOrderBean.DataBean> orderBeanList;
    private List<AllOrderBean.DataBean> orderBeanListTotal;


    private List<OrderCommentBean.DataBean> list;
    private String currUrl;
    LoadingAndRetryManager mLoadingAndRetryManager;
    ViewPager mineMyOrderTab;
    private boolean isFirst = false;

    public MineOrderFragment(int curTitle, ViewPager mineMyOrderTab) {
        this.curTitle = curTitle;
        this.mineMyOrderTab = mineMyOrderTab;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine_order;
    }


    @Override
    protected void initView() {
        super.initView();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser + curTitle);
    }

    private void getCurrUrl(int curTitle) {
        switch (curTitle) {
            case 0:
                currUrl = ServiceApi.userOrders;
                break;
            case 1:
                currUrl = ServiceApi.noPayOrders;
                break;
            case 2:
                currUrl = ServiceApi.unuseOrders;
                break;
            case 3:
                currUrl = ServiceApi.noCommentOrders;
                break;
            case 4:
                currUrl = ServiceApi.refundOrders;
                break;
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        orderItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", String.valueOf(orderBeanListTotal.get(position).getId()));
                BaseActivity.navigate(getContext(), OrderDetailsActivity.class, bundle);
            }
        });

        orderItemAdapter.setOnClickListenterOrder(new OnClickListenterOrder() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", String.valueOf(orderBeanListTotal.get(position).getId()));
                BaseActivity.navigate(getContext(), OrderDetailsActivity.class, bundle);
            }
        });

        orderItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_business_bottom_left:
                        leftClick(position);
                        break;

                    case R.id.item_business_bottom_right:
                        rightClick(position);
                        break;
                }
            }
        });
    }

    //右方点击事件
    private void rightClick(int position) {
        Bundle bundle;
        switch (orderBeanListTotal.get(position).getStatu()) {
            case Constans.NOPAY://代付款
                bundle = new Bundle();
                bundle.putString("orderId", String.valueOf(orderBeanListTotal.get(position).getId()));
                bundle.putString("userPayPrice", String.valueOf(orderBeanListTotal.get(position).getPrice()));
                BaseActivity.navigate(getContext(), CashierActivity.class, bundle);
                break;
            case Constans.UNUSE://待使用
                bundle = new Bundle();
                bundle.putString("orderId", String.valueOf(orderBeanListTotal.get(position).getId()));
                BaseActivity.navigate(getContext(), OrderDetailsActivity.class, bundle);
                break;
            case Constans.REJECTED://退货
                CommonUi.diallPhone(Constans.PhoneNumber, getContext());
                break;
            case Constans.EXPIRED://过期
                deleteOrder(String.valueOf(orderBeanListTotal.get(position).getId()), position);
                break;
            case Constans.NOCOMMENT://待评价
                bundle = new Bundle();
                bundle.putString("orderId", String.valueOf(orderBeanListTotal.get(position).getId()));
                BaseActivity.navigate(getContext(), OrderDetailsActivity.class, bundle);
                break;
            case Constans.FINISH://已完成
                deleteOrder(String.valueOf(orderBeanListTotal.get(position).getId()), position);
                break;
            case Constans.REJECTCUCCESS://退款成功
                deleteOrder(String.valueOf(orderBeanListTotal.get(position).getId()), position);
                break;
        }


    }

    //删除订单
    private void deleteOrder(String orderId, final int position) {
//        CommonDialogUtil.showDialog(getContext());
        OkGo.<String>post(ServiceApi.deleteOrders)
                .tag(this)
                .params("orderId", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
//                        CommonDialogUtil.hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                orderItemAdapter.remove(position);
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
//                        CommonDialogUtil.hideDialog();
                    }
                });
    }

    //下方左边点击事件
    private void leftClick(int position) {
        switch (orderBeanListTotal.get(position).getStatu()) {
            case Constans.NOPAY://代付款
                deleteOrder(String.valueOf(orderBeanListTotal.get(position).getId()), position);
                break;
            case Constans.UNUSE://待使用
                rufund(position);
                break;
            case Constans.REJECTED://退货
                break;
            case Constans.EXPIRED://过期
                break;
            case Constans.NOCOMMENT://待评价
                break;
            case Constans.FINISH://已完成
                break;
            case Constans.REJECTCUCCESS://退款成功
                break;
        }


    }

    //申请退款
    private void rufund(final int position) {
        OkGo.<String>post(ServiceApi.refundOrder)
                .tag(this)
                .params("orderId", String.valueOf(orderBeanListTotal.get(position).getId()))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                orderItemAdapter.remove(position);
                                RxToast.success(msg);
                            } else {
                                RxToast.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst) {
            mineMyOrderRf.autoRefresh();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        isFirst = true;
        getData(0, true);
    }

    private void getData(int currPe, final boolean isHead) {
        orderBeanList = new ArrayList<>();
        orderBeanList.clear();
        OkGo.<String>post(currUrl)
                .tag(this)
                .params("pageNum", String.valueOf(currPe))
                .params("pageSize", Constans.COMMONPAGE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                mLoadingAndRetryManager.showContent();
                                Gson gson = new Gson();
                                AllOrderBean allOrderBean = gson.fromJson(response.body(), AllOrderBean.class);
                                pageCount = allOrderBean.getPageCount();
                                if (allOrderBean.getData() != null) {
                                    if (isHead) {
                                        orderBeanListTotal.addAll(allOrderBean.getData());
                                        orderItemAdapter.setNewData(orderBeanListTotal);
                                    } else {
                                        orderBeanList.addAll(allOrderBean.getData());
                                        orderItemAdapter.addData(orderBeanList);
                                    }

                                    if (orderBeanListTotal.size() <= 0) {
                                        mLoadingAndRetryManager.showEmpty();
                                    }
                                }


                            } else {
                                RxToast.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        orderItemAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mLoadingAndRetryManager.showRetry();
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
//        mineMyOrderTab.setObjectForPosition(rootView, curTitle);

//        mineMyOrderRf.setRefreshHeader(new RefreshView(getContext()));
//        mineMyOrderRf.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_20));
        mineMyOrderRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        orderBeanListTotal.clear();
                        currPage = 0;
                        getData(currPage, true);
                        mineMyOrderRf.finishRefresh();
                    }
                }, 0);
            }
        });

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mineMyOrderRf, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                MineOrderFragment.this.setRetryEvent(retryView);
            }
        });

        mLoadingAndRetryManager.showLoading();
        getCurrUrl(curTitle);
        orderBeanListTotal = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mineMyOrderRecyclerView.setLayoutManager(layoutManager);
        orderItemAdapter = new OrderItemAdapter1(R.layout.item_order_all_coupons, orderBeanListTotal);
        mineMyOrderRecyclerView.setAdapter(orderItemAdapter);


        orderItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mineMyOrderRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            orderItemAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getData(currPage, false);
                            orderItemAdapter.loadMoreComplete();
                        }
                    }
                }, Constans.Refresh_Time);
            }
        }, mineMyOrderRecyclerView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineOrderFragment.this.onResume();
            }
        });
    }


}
