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
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.activity.HomeSecondActivity;
import com.padacn.xmgoing.adapter.homesearch.SearchGoodsAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.fragment.BaseFragment;
import com.padacn.xmgoing.model.HomeSearchBean;
import com.padacn.xmgoing.util.HotSearchEven;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.RecycleViewDivider;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vondear.rxtools.view.RxToast;

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

public class SearchGoodsFragment extends BaseFragment {

    private static final String TAG = "SearchGoodsFragment";
    private static String currSearchStr;
    @BindView(R.id.mine_my_collection_recyclerView)
    RecyclerView mineMyCollectionRecyclerView;
    @BindView(R.id.mine_my_collection_rf)
    RefreshLayout mineMyCollectionRf;
    Unbinder unbinder;

    private int pageCount;
    //当前页数
    private int currPage = 0;

    private List<HomeSearchBean.DataBean.ProductsBean> productsBeanList;
    private List<HomeSearchBean.DataBean.ProductsBean> productsBeanListTotal;
    private SearchGoodsAdapter searchGoodsAdapter;
    private LoadingAndRetryManager mLoadingAndRetryManager;

    public static SearchGoodsFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("searchKey", text);
        currSearchStr = text;
        SearchGoodsFragment fragment = new SearchGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mineMyCollectionRf, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                SearchGoodsFragment.this.setRetryEvent(retryView);
            }
        });

        EventBus.getDefault().register(this);
        productsBeanListTotal = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mineMyCollectionRecyclerView.setLayoutManager(layoutManager);
        mineMyCollectionRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.main_line_view)));
        searchGoodsAdapter = new SearchGoodsAdapter(R.layout.item_home_three_list, productsBeanListTotal);
        mineMyCollectionRecyclerView.setAdapter(searchGoodsAdapter);
        searchGoodsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mineMyCollectionRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            searchGoodsAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getData(currPage, false);
                            searchGoodsAdapter.loadMoreComplete();
                        }
                    }
                }, 2000);
            }
        }, mineMyCollectionRecyclerView);

        searchGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("pid", String.valueOf(productsBeanListTotal.get(position).getPId()));
                bundle.putBoolean("panic", false);
                BaseActivity.navigate(getContext(), GoodsDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getData(0, true);

    }

    /**
     * 完成监听
     */
    @Subscribe
    public void onEventMainThread(HotSearchEven event) {
        if (event.getType().equals("0")) {
            if (currSearchStr != null) {
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
                        productsBeanListTotal.clear();
                        currPage = 0;
                        getData(0, true);
                        mineMyCollectionRf.finishRefresh();
                    }
                }, 2000);

            }


        });
    }

    private void getData(int page, final boolean isHead) {
        productsBeanList = new ArrayList<>();
        productsBeanList.clear();
        OkGo.<String>post(ServiceApi.HOME_SECOND_URL)
                .tag(this)
                .params("content", currSearchStr)
                .params("pageNum", String.valueOf(page))
                .params("pageSize", Constans.COMMONPAGE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        mLoadingAndRetryManager.showContent();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                HomeSearchBean homeSearchBean = gson.fromJson(s, HomeSearchBean.class);
                                pageCount = homeSearchBean.getPageCount();
                                if (isHead) {
                                    productsBeanListTotal.addAll(homeSearchBean.getData().getProducts());
                                    searchGoodsAdapter.setNewData(productsBeanListTotal);
                                } else {
                                    productsBeanList.addAll(homeSearchBean.getData().getProducts());
                                    searchGoodsAdapter.addData(productsBeanList);
                                }
                                searchGoodsAdapter.setText(currSearchStr);
                                if (productsBeanListTotal.size() <= 0) {
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
        searchGoodsAdapter.setText(currSearchStr);
        if (productsBeanListTotal.size() <= 0) {
            mLoadingAndRetryManager.showEmpty();
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_search_goods;
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
                SearchGoodsFragment.this.initData();
            }
        });
    }
}
