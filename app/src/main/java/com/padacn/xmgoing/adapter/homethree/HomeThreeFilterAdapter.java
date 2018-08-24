package com.padacn.xmgoing.adapter.homethree;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeThreeBean;
import com.padacn.xmgoing.model.ListActivityModel;

/**
 * Created by 46009 on 2018/4/27.
 */

public class HomeThreeFilterAdapter extends BaseQuickAdapter<HomeThreeBean.ConditionsBean, BaseViewHolder> {

    private List<HomeThreeBean.ConditionsBean.ListBean> listBeans;

    public HomeThreeFilterAdapter(int layoutResId, @Nullable List<HomeThreeBean.ConditionsBean> data) {

        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeThreeBean.ConditionsBean item) {
        listBeans = new ArrayList<>();
        helper.setText(R.id.shaixuan_title, item.getName());
        listBeans.addAll(item.getList());
        RecyclerView recyclerView = helper.getView(R.id.home_three_filter_rc);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        HomeThreeFilterItemAdapter homeThreeFilterItemAdapter = new HomeThreeFilterItemAdapter(R.layout.item_home_three_filter_item, listBeans);
        homeThreeFilterItemAdapter.setCurrTypeKey(item.getKey());
        recyclerView.setAdapter(homeThreeFilterItemAdapter);
    }
}
