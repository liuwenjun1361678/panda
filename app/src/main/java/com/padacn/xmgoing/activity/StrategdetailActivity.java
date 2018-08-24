package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.StrategdetailAdapter;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.StrategDetailBean;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.padacn.xmgoing.util.loading.OnLoadingAndRetryListener;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.X5WebView;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class StrategdetailActivity extends BaseActivity {
    @BindView(R.id.stratehy_detail_bar)
    CustomToolBar stratehyDetailBar;
    @BindView(R.id.stratehy_detail_webview)
    X5WebView stratehyDetailWebview;
    @BindView(R.id.stratehy_detail_recyclerView)
    RecyclerView stratehyDetailRecyclerView;
    @BindView(R.id.filter_reset)
    TextView filterReset;
    @BindView(R.id.strategy_detail_collect_ll)
    RelativeLayout strategyDetailCollectLl;
    @BindView(R.id.filter_sure)
    TextView filterSure;
    @BindView(R.id.strategy_detail_share_ll)
    RelativeLayout strategyDetailShareLl;
    @BindView(R.id.filter_layout)
    LinearLayout filterLayout;
    LoadingAndRetryManager mLoadingAndRetryManager;
    @BindView(R.id.recyclerView_rl)
    RelativeLayout recyclerViewRl;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private List<StrategDetailBean.DataBean.RandStrategyBean> randStrategyBeanList;
    private StrategdetailAdapter strategdetailAdapter;
    private String currDetailUrl;
    private String strateDetailId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_strateg_detail;
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
    protected void initData() {
        super.initData();


        mLoadingAndRetryManager = LoadingAndRetryManager.generate(scrollView, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                View view = retryView.findViewById(R.id.btn_retry);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stratehyDetailWebview.loadUrl(currDetailUrl);
                    }
                });
            }

        });
        mLoadingAndRetryManager.showLoading();

        Bundle bundle = this.getIntent().getExtras();
        strateDetailId = bundle.getString("strateDetailId");
        if (strateDetailId != null) {
            getStrateDetailData();
        }
    }


    private void getStrateDetailData() {
        randStrategyBeanList = new ArrayList<>();
        OkGo.<String>post(ServiceApi.STRATEGY_DETAIL)
                .tag(this)
                .headers("logintype", "android")
                .params("id", strateDetailId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                StrategDetailBean strategDetailBean = gson.fromJson(s, StrategDetailBean.class);
                                if (strategDetailBean.getResult() == 1) {
                                    currDetailUrl = strategDetailBean.getData().getStartegyUrl();
                                    randStrategyBeanList.addAll(strategDetailBean.getData().getRandStrategy());
                                    setDetail(currDetailUrl);
                                    setRandStrategy();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    private void setRandStrategy() {
        recyclerViewRl.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stratehyDetailRecyclerView.setLayoutManager(layoutManager);
        strategdetailAdapter = new StrategdetailAdapter(R.layout.item_home_activity_one, randStrategyBeanList);
        stratehyDetailRecyclerView.setAdapter(strategdetailAdapter);

        strategdetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("strateDetailId", String.valueOf(randStrategyBeanList.get(position).getStrategyId()));
                BaseActivity.navigate(StrategdetailActivity.this, StrategdetailActivity.class, bundle);
            }
        });
    }

    private void setDetail(String currDetailUrl) {
        stratehyDetailWebview.loadUrl(currDetailUrl);
        stratehyDetailWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mLoadingAndRetryManager.showContent();
                recyclerViewRl.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void initView() {
        super.initView();
        stratehyDetailBar.setStyle("攻略详情", Color.parseColor("#f8d948"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
