package com.padacn.xmgoing.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.MineCollectionAdapter;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.view.RecycleViewDivider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 46009 on 2018/5/5.
 */

public class MineCollectionFragment extends BaseFragment {
    @BindView(R.id.load_image_gif)
    ImageView loadImageGif;
    @BindView(R.id.mine_my_collection_recyclerView)
    RecyclerView mineMyCollectionRecyclerView;
    @BindView(R.id.mine_my_collection_rf)
    SmartRefreshLayout mineMyCollectionRf;
    Unbinder unbinder;

    private MineCollectionAdapter mineClomnAdapter;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine_collection;
    }

    public static MineCollectionFragment getInstance() {
        MineCollectionFragment sf = new MineCollectionFragment();
        return sf;
    }

    @Override
    protected void initView() {
        List<ListActivityModel> datas = new ArrayList<>();
        ListActivityModel model;
        for (int i = 0; i < 10; i++) {
            model = new ListActivityModel();
            datas.add(model);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mineMyCollectionRecyclerView.setLayoutManager(layoutManager);
        mineMyCollectionRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.divide_gray_color)));
        mineClomnAdapter = new MineCollectionAdapter(R.layout.item_mine_collection_travel, datas);
        mineMyCollectionRecyclerView.setAdapter(mineClomnAdapter);
        mineMyCollectionRf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
//                Glide.with(getContext()).load(R.drawable.common_top_refresh_image).asGif()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(loadImageGif);

            }
        });

//        mineClomnAdapter.setEmptyView(R.layout.common_error_view, (ViewGroup) mineMyCollectionRecyclerView.getParent());

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
    }
}
