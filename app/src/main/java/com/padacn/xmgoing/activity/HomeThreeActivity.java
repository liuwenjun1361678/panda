package com.padacn.xmgoing.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.HomeThreeAdapter;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.view.CustomToolBar;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class HomeThreeActivity extends BaseActivity {
    @BindView(R.id.home_three_bar)
    CustomToolBar homeThreeBar;
    @BindView(R.id.home_three_zonghe_view)
    View homeThreeZongheView;
    @BindView(R.id.home_three_xiaoliang_view)
    View homeThreeXiaoliangView;
    @BindView(R.id.home_three_xiaoliang_text)
    TextView homeThreeXiaoliangText;
    @BindView(R.id.home_three_price_view)
    View homeThreePriceView;
    @BindView(R.id.home_three_shaixuan_text)
    TextView homeThreeShaixuanText;
    @BindView(R.id.home_three_shaxuan_view)
    View homeThreeShaxuanView;
    @BindView(R.id.home_three_recyclerView)
    RecyclerView homeThreeRecyclerView;


    private HomeThreeAdapter homeThreeAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home_three;
    }

    @Override
    protected void initView() {
        super.initView();
        List<ListActivityModel> datas = new ArrayList<>();
        ListActivityModel model;
        for (int i = 0; i < 10; i++) {
            model = new ListActivityModel();
            datas.add(model);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeThreeRecyclerView.setLayoutManager(layoutManager);

        homeThreeAdapter = new HomeThreeAdapter(R.layout.item_home_three_list, datas);
//        exCouponsCenterAdapter.addHeaderView(headerView);
        homeThreeRecyclerView.setAdapter(homeThreeAdapter);
        homeThreeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                BaseActivity.navigate(HomeThreeActivity.this, GoodsDetailsActivity.class);
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
