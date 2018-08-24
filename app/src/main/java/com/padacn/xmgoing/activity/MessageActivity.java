package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.MessageAdapter;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.MessageBean;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.RefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/1 0001.
 */

public class MessageActivity extends BaseActivity {
    @BindView(R.id.mine_my_message_recyclerView)
    RecyclerView mineMyMessageRecyclerView;
    @BindView(R.id.mine_my_message_rf)
    SmartRefreshLayout mineMyMessageRf;
    @BindView(R.id.mine_my_message_bar)
    CustomToolBar mineMyMessageBar;
    private LoadingAndRetryManager mLoadingAndRetryManager;

    private int page;
    private int pageCount;

    private List<MessageBean.DataBean> list;
    private MessageAdapter messageAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        mineMyMessageBar.setStyle("我的消息", Color.parseColor("#f8d948"));
        list = new ArrayList<>();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mineMyMessageRf, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                MessageActivity.this.setRetryEvent(retryView);
            }
        });
        mineMyMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messageAdapter = new MessageAdapter(R.layout.item_activity_message, list);
        mineMyMessageRecyclerView.setAdapter(messageAdapter);
    }

    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageActivity.this.initData();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mLoadingAndRetryManager.showLoading();
        getData();
    }

    private void getData() {
        list.clear();
        OkGo.<String>post(ServiceApi.listAllNews)
                .tag(this)
                .params("pageNum", String.valueOf(page))
                .params("pageSize", "10")
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
                                MessageBean messageBean = gson.fromJson(s, MessageBean.class);
                                list.addAll(messageBean.getData());
                                if (list.size() > 0) {
                                    messageAdapter.setNewData(list);
                                } else {
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
    protected void setListener() {
        super.setListener();
        mineMyMessageRf.setRefreshHeader(new RefreshView(this));
        mineMyMessageRf.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_25));
        mineMyMessageRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        getHomeData(true, 0);
                        mineMyMessageRf.finishRefresh();
                    }
                }, 2000);

            }

        });
        messageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title", list.get(position).getNewsTitle());
                bundle.putString("content", list.get(position).getNewsContent());
                bundle.putString("date", list.get(position).getNewsDate());
                bundle.putString("userType", list.get(position).getNewsUserType());
                BaseActivity.navigate(MessageActivity.this, MessageDetailsActivity.class, bundle);

            }
        });
    }
}
