package com.padacn.xmgoing.fragment.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.StrategdetailActivity;
import com.padacn.xmgoing.adapter.homesearch.SearchTravelAdapter;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.fragment.BaseFragment;
import com.padacn.xmgoing.model.HomeSearchBean;
import com.padacn.xmgoing.model.HomeSearchTravleBean;
import com.padacn.xmgoing.util.HotSearchEven;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.RecycleViewDivider;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
 * Created by Administrator on 2018/5/29 0029.
 */

public class SearchTravleFragment extends BaseFragment {
    private static final String TAG = "SearchTravleFragment";
    @BindView(R.id.mine_my_collection_recyclerView)
    RecyclerView mineMyCollectionRecyclerView;
    @BindView(R.id.mine_my_collection_rf)
    SmartRefreshLayout mineMyCollectionRf;
    Unbinder unbinder;
    private SearchTravelAdapter searchTravelAdapter;


    private List<HomeSearchTravleBean.DataBean> dataBeanList;
    private List<HomeSearchTravleBean.DataBean> dataBeanListTotal;
    private int pageCount;
    //当前页数
    private int currPage = 0;
    private static String currSearchStr;

    private LoadingAndRetryManager mLoadingAndRetryManager;

    public static SearchTravleFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("searchKey", text);
        currSearchStr = text;
        SearchTravleFragment fragment = new SearchTravleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getData(0, true);
    }

    @Override
    protected void initView() {
        super.initView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mineMyCollectionRf, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                SearchTravleFragment.this.setRetryEvent(retryView);
            }
        });

        EventBus.getDefault().register(this);
        dataBeanListTotal = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mineMyCollectionRecyclerView.setLayoutManager(layoutManager);
        mineMyCollectionRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.main_line_view)));
        searchTravelAdapter = new SearchTravelAdapter(R.layout.item_mine_collection_travel, dataBeanListTotal);
        mineMyCollectionRecyclerView.setAdapter(searchTravelAdapter);

        searchTravelAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mineMyCollectionRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            searchTravelAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getData(currPage, false);
                        }
                    }
                }, 2000);
            }
        }, mineMyCollectionRecyclerView);

        searchTravelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("strateDetailId", String.valueOf(dataBeanListTotal.get(position).getStrategyId()));
                BaseActivity.navigate(getContext(), StrategdetailActivity.class, bundle);
            }
        });
    }


    /**
     * 完成监听
     */
    @Subscribe
    public void onEventMainThread(HotSearchEven event) {
        if (event.getType().equals("1")) {
            if (currSearchStr != null) {
                Log.e(TAG, "onEventMainThread: " + currSearchStr);
                currSearchStr = event.getMsg();
                getData(0, true);
            }
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mineMyCollectionRf.setRefreshHeader(new RefreshView(getContext()));
        mineMyCollectionRf.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_30));
        mineMyCollectionRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataBeanListTotal.clear();
                        getData(0, true);
                        mineMyCollectionRf.finishRefresh();
                    }
                }, 2000);

            }


        });
    }


    private void getData(int page, final boolean isHead) {
        dataBeanList = new ArrayList<>();
        dataBeanList.clear();
        OkGo.<String>post(ServiceApi.STRATEGY_SEARCH)
                .tag(this)
                .params("content", currSearchStr)
                .params("pageNum", String.valueOf(page))
                .params("pageSize", "10")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mLoadingAndRetryManager.showContent();
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                HomeSearchTravleBean homeSearchTravleBean = gson.fromJson(s, HomeSearchTravleBean.class);
                                pageCount = homeSearchTravleBean.getPageCount();
                                if (isHead) {
                                    dataBeanListTotal.addAll(homeSearchTravleBean.getData());
                                    searchTravelAdapter.setNewData(dataBeanListTotal);
                                } else {
                                    dataBeanList.addAll(homeSearchTravleBean.getData());
                                    searchTravelAdapter.addData(dataBeanList);
                                }
                                if (dataBeanListTotal.size() <= 0) {
                                    mLoadingAndRetryManager.showEmpty();
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

    private void showData() {
        searchTravelAdapter.setText(currSearchStr);
        if (dataBeanListTotal.size() <= 0) {
            mLoadingAndRetryManager.showEmpty();
        }
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_search_travle;
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
        EventBus.getDefault().unregister(this);
    }

    public void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchTravleFragment.this.initData();
            }
        });
    }
}
