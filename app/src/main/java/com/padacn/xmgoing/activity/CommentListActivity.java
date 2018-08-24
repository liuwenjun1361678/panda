package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.CommentListAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.CommentListBean;
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
 * Created by Administrator on 2018/7/6 0006.
 */

public class CommentListActivity extends BaseActivity {


    private static int SPAN_COUNT = 2;
    @BindView(R.id.comment_list_recyclerView)
    RecyclerView commentListRecyclerView;
    @BindView(R.id.comment_list_rf)
    SmartRefreshLayout commentListRf;
    @BindView(R.id.comment_list_bar)
    CustomToolBar mineMyOrderBar;

    //底部下拉list总共頁数
    private int pageCount;
    //当前页数
    private int currPage = 0;
    private CommentListAdapter commentListAdapter;
    private List<CommentListBean.DataBean> list;
    private List<CommentListBean.DataBean> totaList;

    private String pid;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_comment_list;
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
        Bundle bundle = this.getIntent().getExtras();
        pid = bundle.getString("pid");
        mineMyOrderBar.setStyle("商品评价", Color.parseColor("#f8d948"));
        totaList = new ArrayList<>();
        commentListRf.setRefreshHeader(new RefreshView(this));
        commentListRf.setHeaderHeight(getResources().getDimensionPixelSize(R.dimen.dp_25));
        commentListRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        totaList.clear();
                        currPage = 0;
                        getData(0, true);
                        commentListRf.finishRefresh();
                    }
                }, Constans.Refresh_Time);

            }

        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentListRecyclerView.setLayoutManager(layoutManager);
        commentListAdapter = new CommentListAdapter(R.layout.item_comment_list, totaList);
        commentListRecyclerView.setAdapter(commentListAdapter);
        commentListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                commentListRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currPage + 1 > pageCount) {
                            commentListAdapter.loadMoreEnd();
                        } else {
                            currPage++;
                            getData(currPage, false);
                        }
                    }
                }, 2000);
            }
        }, commentListRecyclerView);

        getData(0, true);


    }

    private void getData(int pageNum, final boolean isHead) {
        list = new ArrayList<>();
        list.clear();
        OkGo.<String>post(ServiceApi.reviewList)
                .tag(this)
                .params("pid", pid)
                .params("pageNum", pageNum)
                .params("pageSize", Constans.COMMONPAGE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                CommentListBean commentListBean = gson.fromJson(s, CommentListBean.class);
                                pageCount = commentListBean.getPageCount();
                                if (isHead) {
                                    totaList.addAll(commentListBean.getData());
                                    commentListAdapter.setNewData(totaList);
                                } else {
                                    list.addAll(commentListBean.getData());
                                    commentListAdapter.addData(list);
                                }
                                commentListAdapter.loadMoreComplete();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
