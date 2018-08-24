package com.padacn.xmgoing.fragment.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.padacn.xmgoing.activity.OrderDetailsActivity;
import com.padacn.xmgoing.adapter.OrderItemAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.fragment.BaseFragment;
import com.padacn.xmgoing.model.AllOrderBean;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.CustomViewPagerTab;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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

public class NoPayOrderFragment extends BaseFragment {
    private static final String TAG = "MineOrderFragment";
    @BindView(R.id.mine_my_order_recyclerView)
    RecyclerView mineMyOrderRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.mine_my_order_rf)
    RefreshLayout mineMyOrderRf;

    private int currPage;
    private int pageCount;

    private int curTitle;
    private OrderItemAdapter orderItemAdapter;

    private List<AllOrderBean.DataBean> orderBeanListTotal;
    private List<AllOrderBean.DataBean> orderBeanList;
    private String currUrl;
    LoadingAndRetryManager mLoadingAndRetryManager;

    private CustomViewPagerTab vp;

    public static NoPayOrderFragment getInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        NoPayOrderFragment sf = new NoPayOrderFragment();
        sf.setArguments(args);
        return sf;

    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine_order;
    }


    @Override
    protected void initView() {

        super.initView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mineMyOrderRf, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                NoPayOrderFragment.this.setRetryEvent(retryView);
            }
        });
        orderBeanListTotal = new ArrayList<>();

        curTitle = getArguments().getInt("position");
        getCurrUrl(curTitle);
        orderBeanList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mineMyOrderRecyclerView.setLayoutManager(layoutManager);
        orderItemAdapter = new OrderItemAdapter(orderBeanListTotal);
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
                }, 2000);
            }
        }, mineMyOrderRecyclerView);
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
                bundle.putString("orderId", String.valueOf(orderBeanList.get(position).getId()));
                BaseActivity.navigate(getContext(), OrderDetailsActivity.class, bundle);
            }
        });


    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getData(0, true);

    }

    private void getData(int currPe, final boolean isHead) {
        orderBeanList = new ArrayList<>();
        orderBeanList.clear();
        OkGo.<String>post(currUrl)
                .tag(this)
                .headers("token", "58efd7dc-72f0-4385-8af9-1e7e57ca60d3")
                .headers("Area", Constans.Area)
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
                                orderBeanList.addAll(allOrderBean.getData());
                                if (orderBeanList.size() > 0) {
                                    if (isHead) {
                                        orderBeanListTotal.addAll(allOrderBean.getData());
                                        orderItemAdapter.setNewData(orderBeanListTotal);
                                    } else {
                                        orderBeanList.addAll(allOrderBean.getData());
                                        orderItemAdapter.addData(orderBeanList);
                                    }
                                } else {
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
                NoPayOrderFragment.this.initData();
            }
        });
    }
}
