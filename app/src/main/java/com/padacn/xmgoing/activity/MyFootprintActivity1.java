package com.padacn.xmgoing.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.widget.StickyHeaderLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.FootExtendAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.fragment.MyCouponsFragment;
import com.padacn.xmgoing.model.FootBean;
import com.padacn.xmgoing.model.OrderCommentBean;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.CustomToolBar;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 46009 on 2018/5/7.
 */

public class MyFootprintActivity1 extends BaseActivity {


    @BindView(R.id.my_foot_bar)
    CustomToolBar myFootBar;
    @BindView(R.id.my_foot_rv)
    RecyclerView myFootRv;
    @BindView(R.id.sticky_layout)
    StickyHeaderLayout stickyLayout;
    FootExtendAdapter adapter;

    private LoadingAndRetryManager mLoadingAndRetryManager;
    private int currPage;
    private int pageCount;
    private List<FootBean.DataBean> dataBeanList;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_footprint1;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .navigationBarColor(R.color.home_top_text_color)
                .keyboardEnable(true)
                .init();
    }


    @Override
    protected void initView() {
        super.initView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(stickyLayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                MyFootprintActivity1.this.setRetryEvent(retryView);
            }
        });
        myFootBar.setStyle("我的足迹", Color.parseColor("#f8d948"));
        dataBeanList = new ArrayList<>();
        getData(0);
    }

    private void getData(int currpage) {
        OkGo.<String>post(ServiceApi.footprint)
                .tag(this)
                .params("pageNum", String.valueOf(currpage))
                .params("pageSize", Constans.COMMONPAGE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            mLoadingAndRetryManager.showContent();
                            if (result == 1) {
                                Gson gson = new Gson();
                                FootBean footBean = gson.fromJson(response.body(), FootBean.class);
                                pageCount = footBean.getPageCount();
                             /*   for (int i = 0; i < footBean.getData().size(); i++) {
                                    if (footBean.getData().get(i).getList().size() > 0) {
                                        dataBeanList.add(footBean.getData().get(i));
                                    }
                                }*/
                                dataBeanList.addAll(footBean.getData());
                                if (dataBeanList.size() > 0) {
                                    showData();
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
//                        orderItemAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mLoadingAndRetryManager.showRetry();
                    }
                });


    }

    private void showData() {
        myFootRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FootExtendAdapter(this, dataBeanList);
        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {
                FootExtendAdapter expandableAdapter = (FootExtendAdapter) adapter;
                if (expandableAdapter.isExpand(groupPosition)) {
                    expandableAdapter.collapseGroup(groupPosition);
                } else {
                    expandableAdapter.expandGroup(groupPosition);
                }
            }
        });
        myFootRv.setAdapter(adapter);
        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                Bundle bundle = new Bundle();
                bundle.putString("pid", String.valueOf(dataBeanList.get(groupPosition).getList().get(childPosition).getPId()));
                bundle.putBoolean("panic", false);
                BaseActivity.navigate(MyFootprintActivity1.this, GoodsDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFootprintActivity1.this.initData();
            }
        });
    }
}
