package com.padacn.xmgoing.fragment;

import android.annotation.SuppressLint;
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
import com.padacn.xmgoing.activity.GoodsSureActivity;
import com.padacn.xmgoing.activity.MycouponsActivity;
import com.padacn.xmgoing.adapter.MyCouponsAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.MyCouponsBean;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.util.CouponsEven;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
 * Created by Administrator on 2018/6/27 0027.
 */

@SuppressLint("ValidFragment")
public class MyCouponsFragment extends BaseFragment {
    @BindView(R.id.mine_my_coupons_recyclerView)
    RecyclerView mineMyCouponsRecyclerView;
    @BindView(R.id.mine_my_coupons_rf)
    SmartRefreshLayout mineMyCouponsRf;
    Unbinder unbinder;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private MyCouponsAdapter myCouponsAdapter;
    private List<MyCouponsBean.DataBean> dataBeanList;
    private List<MyCouponsBean.DataBean> dataBeanListTotal;
    //是否过期
    private int Void;
    private int currPage;
    private int pageCount;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine_coupons;
    }

    public MyCouponsFragment(int aVoid) {
        Void = aVoid;
    }

    @Override
    protected void initView() {
        super.initView();
        dataBeanListTotal = new ArrayList<>();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mineMyCouponsRf, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                MyCouponsFragment.this.setRetryEvent(retryView);
            }
        });
        mineMyCouponsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myCouponsAdapter = new MyCouponsAdapter(R.layout.item_my_coupons, dataBeanListTotal);
        mineMyCouponsRecyclerView.setAdapter(myCouponsAdapter);
        myCouponsAdapter.setIsVoid(Void);
        myCouponsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mineMyCouponsRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 >= pageCount) {
                            myCouponsAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getData(currPage, false);
                            myCouponsAdapter.loadMoreComplete();
                        }
                    }
                }, 2000);
            }
        }, mineMyCouponsRecyclerView);


    }

    @Override
    protected void setListener() {
        super.setListener();

        mineMyCouponsRf.setRefreshHeader(new RefreshView(getContext()));
        mineMyCouponsRf.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_25));
        mineMyCouponsRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataBeanListTotal.clear();
                        currPage = 0;
                        getData(0, true);
                        mineMyCouponsRf.finishRefresh();

                    }
                }, 2000);

            }

        });
    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getData(currPage, true);
    }

    public void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCouponsFragment.this.initData();
            }
        });
    }

    private void getData(int page, final boolean isHead) {
        dataBeanList = new ArrayList<>();
        dataBeanList.clear();
        String isVoid = null;
        if (Void == 0) {
            isVoid = "0";
        } else {
            isVoid = "1";
        }
        OkGo.<String>post(ServiceApi.coupons_user)
                .tag(this)
                .params("isVoid", isVoid)
                .params("pageNum", String.valueOf(page))
                .params("pageSize", Constans.COMMONPAGE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                mLoadingAndRetryManager.showContent();
                                Gson gson = new Gson();
                                MyCouponsBean myCouponsBean = gson.fromJson(s, MyCouponsBean.class);
                                pageCount = myCouponsBean.getPageCount();
                                if (isHead) {
                                    dataBeanListTotal.addAll(myCouponsBean.getData());
                                    myCouponsAdapter.setNewData(dataBeanListTotal);
                                } else {
                                    dataBeanList.addAll(myCouponsBean.getData());
                                    myCouponsAdapter.addData(dataBeanList);
                                }
                                if (myCouponsBean.getCouponCount() <= 0) {
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
