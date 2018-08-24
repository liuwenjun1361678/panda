package com.padacn.xmgoing.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.CashierActivity;
import com.padacn.xmgoing.activity.CommentActivity;
import com.padacn.xmgoing.activity.OrderDetailsActivity;
import com.padacn.xmgoing.adapter.OrderCommentItemAdapter;
import com.padacn.xmgoing.adapter.OrderItemAdapter1;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.callback.OnClickListenterOrder;
import com.padacn.xmgoing.model.AllOrderBean;
import com.padacn.xmgoing.model.OrderCommentBean;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 46009 on 2018/5/5.
 */

@SuppressLint("ValidFragment")
public class MineOrderCommentFragment extends BaseFragment {
    private static final String TAG = "MineOrderFragment";
    @BindView(R.id.mine_my_order_recyclerView)
    RecyclerView mineMyOrderRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.mine_my_order_rf)
    RefreshLayout mineMyOrderRf;
    private int currPage;
    private int pageCount;
    private int curTitle;
    private OrderCommentItemAdapter orderItemAdapter;

    private List<OrderCommentBean.DataBean> list;
    private List<OrderCommentBean.DataBean> listTotal;
    private String currUrl;
    LoadingAndRetryManager mLoadingAndRetryManager;
    ViewPager mineMyOrderTab;
    private boolean isFirst = false;

    public MineOrderCommentFragment(int curTitle, ViewPager mineMyOrderTab) {
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
            }
        });

        orderItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_business_bottom_left:
                        break;
                    case R.id.item_business_bottom_right:
                        Bundle bundle = new Bundle();
                        bundle.putString("typeName", String.valueOf(listTotal.get(position).getTypeName()));
                        bundle.putString("orderProductId", String.valueOf(listTotal.get(position).getOrderProductId()));
                        bundle.putString("proPic", listTotal.get(position).getProPic());
                        bundle.putString("proName", listTotal.get(position).getProName());
                        if (listTotal.get(position).isFlashUse()) {
                            bundle.putString("useDay", String.valueOf(listTotal.get(position).getPeriodOfTime()));
                        } else {
                            bundle.putString("useDay", String.valueOf(listTotal.get(position).getUseDay()));
                        }
                        bundle.putString("excursion", listTotal.get(position).getExcursion());
                        BaseActivity.navigate(getContext(), CommentActivity.class, bundle);
                        break;
                }
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
        list = new ArrayList<>();
        list.clear();
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
                                OrderCommentBean orderCommentBean = gson.fromJson(response.body(), OrderCommentBean.class);
                                pageCount = orderCommentBean.getPageCount();
                                if (orderCommentBean.getData().size() > 0) {
                                    if (isHead) {
                                        listTotal.addAll(orderCommentBean.getData());
                                        orderItemAdapter.setNewData(listTotal);
                                    } else {
                                        list.addAll(orderCommentBean.getData());
                                        orderItemAdapter.addData(list);
                                    }
                                }
                                if (listTotal.size() <= 0) {
                                    mLoadingAndRetryManager.showEmpty();
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
        mineMyOrderRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listTotal.clear();
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
                MineOrderCommentFragment.this.setRetryEvent(retryView);
            }
        });

        mLoadingAndRetryManager.showLoading();
        getCurrUrl(curTitle);
        listTotal = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mineMyOrderRecyclerView.setLayoutManager(layoutManager);
        orderItemAdapter = new OrderCommentItemAdapter(R.layout.item_order_all_coupons, listTotal);
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
                        }
                    }
                }, Constans.Refresh_UP);
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
                MineOrderCommentFragment.this.initData();
            }
        });
    }
}
