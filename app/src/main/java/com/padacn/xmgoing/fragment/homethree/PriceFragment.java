package com.padacn.xmgoing.fragment.homethree;

import android.os.Bundle;
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
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.adapter.homethree.HomeThreeComprehensiveAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.fragment.BaseFragment;
import com.padacn.xmgoing.model.HomeThreeBean;
import com.padacn.xmgoing.util.EvenPriceSortUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.RecycleViewDivider;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class PriceFragment extends BaseFragment {

    @BindView(R.id.comprehensive_rc)
    RecyclerView comprehensiveRc;
    @BindView(R.id.home_three_comprehensive_refresh)
    RefreshLayout homeThreeComprehensiveRefresh;
    Unbinder unbinder;
    //当前类别
    private String currProTypeId;
    //当前选择的价格tpye
    private int priceSortMode;
    //底部下拉list总共頁数
    private int pageCount;
    //当前页数
    private int currPage = 0;
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private List<HomeThreeBean.DataBean.ProductsBean> productsBeanList;
    private List<HomeThreeBean.DataBean.ProductsBean> productsBeanListTotal;
    private HomeThreeBean homeThreeBean;
    private HomeThreeComprehensiveAdapter homeThreeComprehensiveAdapter;
    private String curPriceSort = "0";


    public static PriceFragment newInstance(String text, int priceSortMode) {
        Bundle args = new Bundle();
        args.putString("searchKey", text);
        args.putInt("priceSortMode", priceSortMode);
        PriceFragment fragment = new PriceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_three_comprehensive;
    }


    @Override
    protected void setListener() {
        super.setListener();
        homeThreeComprehensiveRefresh.setRefreshHeader(new RefreshView(getContext()));
        homeThreeComprehensiveRefresh.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_25));
        homeThreeComprehensiveRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currPage = 0;
                        productsBeanListTotal.clear();
                        getHomeThreeData(0, curPriceSort, true);
                        homeThreeComprehensiveRefresh.finishRefresh();
                    }
                }, 1000);

            }

        });
    }

    /**
     * 完成监听
     */
    @Subscribe
    public void onEventMainThread(EvenPriceSortUtil event) {
        if (event.getMsg() == 1) {//低到高
            curPriceSort = "0";

        } else if (event.getMsg() == 2) {
            curPriceSort = "1";
        }
        productsBeanListTotal.clear();
        homeThreeComprehensiveRefresh.autoRefresh();
    }


    @Override
    protected void initData() {
        super.initData();
        getHomeThreeData(0, "0", true);
    }

    private void getHomeThreeData(int currPe, String priceSort, final boolean isHead) {
        productsBeanList = new ArrayList<>();
        productsBeanList.clear();
        OkGo.<String>post(ServiceApi.PRODUCT_SORT)
                .tag(this)
                .params("typeId", currProTypeId)
                .params("pageNum", String.valueOf(currPe))
                .params("pageSize", "10")
                .params("priceSort", priceSort)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                homeThreeComprehensiveAdapter.loadMoreComplete();
                                mLoadingAndRetryManager.showContent();
                                Gson gson = new Gson();
                                homeThreeBean = gson.fromJson(s, HomeThreeBean.class);
                                pageCount = homeThreeBean.getPageCount();
                                if (homeThreeBean.getData().getProducts() != null) {
                                    if (isHead) {
                                        productsBeanListTotal.addAll(homeThreeBean.getData().getProducts());
                                        homeThreeComprehensiveAdapter.setNewData(productsBeanListTotal);
                                    } else {
                                        productsBeanList.addAll(homeThreeBean.getData().getProducts());
                                        homeThreeComprehensiveAdapter.addData(productsBeanList);
                                    }
                                    if (productsBeanListTotal.size() < 1) {
                                        mLoadingAndRetryManager.showEmpty();
                                    }
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


    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        productsBeanListTotal = new ArrayList<>();
        currProTypeId = getArguments().getString("searchKey");

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(homeThreeComprehensiveRefresh, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                PriceFragment.this.setRetryEvent(retryView);
            }
        });
        mLoadingAndRetryManager.showLoading();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        comprehensiveRc.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.common_f0)));
        comprehensiveRc.setLayoutManager(layoutManager);


        homeThreeComprehensiveAdapter = new HomeThreeComprehensiveAdapter(R.layout.item_home_three_list, productsBeanListTotal);
//        exCouponsCenterAdapter.addHeaderView(headerView);
        comprehensiveRc.setAdapter(homeThreeComprehensiveAdapter);
        homeThreeComprehensiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("pid", String.valueOf(productsBeanListTotal.get(position).getPId()));
                bundle.putBoolean("panic", false);
                BaseActivity.navigate(getContext(), GoodsDetailsActivity.class, bundle);
            }
        });


        homeThreeComprehensiveAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                comprehensiveRc.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            homeThreeComprehensiveAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getHomeThreeData(currPage, curPriceSort, false);
                            homeThreeComprehensiveAdapter.loadMoreComplete();
                        }
                    }
                }, 1000);
            }
        }, comprehensiveRc);
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
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceFragment.this.initData();
            }
        });
    }
}
