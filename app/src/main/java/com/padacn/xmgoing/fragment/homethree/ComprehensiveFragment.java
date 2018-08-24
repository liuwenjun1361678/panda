package com.padacn.xmgoing.fragment.homethree;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.padacn.xmgoing.model.JosnOne;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.presenter.SecondEventil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.RecycleViewDivider;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class ComprehensiveFragment extends BaseFragment {

    private static final String TAG = "ComprehensiveFragment";
    @BindView(R.id.comprehensive_rc)
    RecyclerView comprehensiveRc;
    Unbinder unbinder;
    private static String currProTypeId;
    @BindView(R.id.home_three_comprehensive_refresh)
    RefreshLayout homeThreeComprehensiveRefresh;
    @BindView(R.id.rl)
    RelativeLayout rl;
    private List<String> productAges;
    private List<String> productDurations;
    private List<String> priceRanges;
    private List<String> label;

    //底部下拉list总共頁数
    private int pageCount;
    //当前页数
    private int currPage = 0;
    private HomeThreeBean homeThreeBean;
    private List<HomeThreeBean.DataBean.ProductsBean> productsBeanList;
    private List<HomeThreeBean.DataBean.ProductsBean> productsBeanListTotal;
    private HomeThreeComprehensiveAdapter homeThreeComprehensiveAdapter;
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private boolean IsFilter = false;


    public static ComprehensiveFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("searchKey", text);
        currProTypeId = text;
        ComprehensiveFragment fragment = new ComprehensiveFragment();
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
                        if (IsFilter) {
                            getHomeThreeData(0, productAges, productDurations, priceRanges, label, true);
                        } else {
                            getThreeData(0, true);
                        }
                        homeThreeComprehensiveRefresh.finishRefresh();
                    }
                }, 1000);
            }

        });


    }

    @Override
    protected void initData() {
        super.initData();
        if (IsFilter) {
            getHomeThreeData(0, productAges, productDurations, priceRanges, label, true);
        } else {
            getThreeData(0, true);
        }
    }

    private void getThreeData(int currPe, final boolean isHead) {

        productsBeanList = new ArrayList<>();
        productsBeanList.clear();
        Log.e(TAG, "getThreeData: __________" + currPe);
        OkGo.<String>post(ServiceApi.HOME_SECOND_URL)
                .tag(this)
                .params("typeId", currProTypeId)
                .params("pageNum", String.valueOf(currPe))
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

    private void getHomeThreeData(int currPe, List<String> productAges, List<String> productDurations, List<String> priceRanges, List<String> label, final boolean isHead) {
        productsBeanList = new ArrayList<>();
        productsBeanList.clear();
        Gson gson = new Gson();
        String s1 = "";
        String s2 = "";
        String s3 = "";
        String s4 = "";
        if (productAges.size() > 0) {
            s1 = gson.toJson(productAges, new TypeToken<List<String>>() {
            }.getType());
        }
        if (productDurations.size() > 0) {
            s2 = gson.toJson(productDurations, new TypeToken<List<String>>() {
            }.getType());
        }

        if (priceRanges.size() > 0) {
            s3 = gson.toJson(priceRanges, new TypeToken<List<String>>() {
            }.getType());
        }

        if (label.size() > 0) {
            s4 = gson.toJson(label, new TypeToken<List<String>>() {
            }.getType());
        }
        OkGo.<String>post(ServiceApi.HOME_SECOND_URL)
                .tag(this)
                .params("typeId", currProTypeId)
                .params("pageNum", String.valueOf(currPe))
                .params("pageSize", Constans.COMMONPAGE)
                .params("productAges", s1)
                .params("productDurations", s2)
                .params("priceRanges", s3)
                .params("label", s4)
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
                                homeThreeBean = gson.fromJson(s, HomeThreeBean.class);
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
                        homeThreeComprehensiveAdapter.loadMoreFail();
                    }
                });
    }


    @Subscribe
    public void onEventMainThread(SecondEventil event) {
        IsFilter = event.getIsFilter();
        if (IsFilter) {
            productAges = new ArrayList<>();
            productDurations = new ArrayList<>();
            priceRanges = new ArrayList<>();
            label = new ArrayList<>();
            productAges.clear();
            productDurations.clear();
            priceRanges.clear();
            label.clear();
            priceRanges.addAll(HomeThreeSelectPresenter.getSingleTon().getPriceRangesList());
            label.addAll(HomeThreeSelectPresenter.getSingleTon().getLableList());
            productAges.addAll(HomeThreeSelectPresenter.getSingleTon().getProductAgesList());
            productDurations.addAll(HomeThreeSelectPresenter.getSingleTon().getProductDurationsList());

            currPage = 0;
            productsBeanListTotal.clear();
            getHomeThreeData(0, productAges, productDurations, priceRanges, label, true);
        } else {
            currPage = 0;
            productsBeanListTotal.clear();
            Log.e(TAG, "onEventMainThread: +" + "222");
            getThreeData(0, true);
        }

    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        productsBeanListTotal = new ArrayList<>();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(homeThreeComprehensiveRefresh, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                ComprehensiveFragment.this.setRetryEvent(retryView);
            }
        });
        mLoadingAndRetryManager.showLoading();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        comprehensiveRc.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.main_line_view)));
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
                            if (IsFilter) {
                                getHomeThreeData(currPage, productAges, productDurations, priceRanges, label, false);
                            } else {
                                getThreeData(currPage, false);
                            }
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
                ComprehensiveFragment.this.initData();
            }
        });
    }

}
