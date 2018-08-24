package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.StringUtil;

/**
 * Created by 46009 on 2018/4/27.
 */

public class MainTabListAdapter extends BaseQuickAdapter<HomeBean.DataBean.ListProductBean, BaseViewHolder> {


    private HomeBottomTypeAdapter homeBottomTypeAdapter;
    private List<HomeBean.DataBean.ListProductBean.LoginsBeanXX> listType;

    public MainTabListAdapter(int layoutResId, @Nullable List<HomeBean.DataBean.ListProductBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeBean.DataBean.ListProductBean item) {
        listType = new ArrayList<>();
        ImageView imageBg = helper.getView(R.id.item_home_bottom_image);
        ImageView imageView = helper.getView(R.id.item_home_bottom_typeImage);
        View view = helper.getView(R.id.mian_line_view);
        view.setVisibility(View.GONE);
        if (helper.getAdapterPosition() == 0) {
            view.setVisibility(View.VISIBLE);
        }
//        RelativeLayout relativeLayout = helper.getView(R.id.item_home_bottom_Rl);
//        CommonUtil.loadRlImage(mContext, item.getPics().get(0).getPath(), relativeLayout, relativeLayout.getWidth(), relativeLayout.getHeight());

        if (item.getPics().size() > 0) {
            CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), imageBg);
        }
        switch (item.getHomeMark()) {
            case 0:
                imageView.setImageResource(R.mipmap.home_channel_1);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.home_channel_2);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.home_channel_3);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.home_channel_4);
                break;
            case 4:
                imageView.setImageResource(R.mipmap.home_channel_5);
                break;
        }
        helper.setText(R.id.item_home_bottom_title, item.getPName());
        helper.setText(R.id.item_home_bottom_content, item.getPDetail());
        if (item.isReference()) {
            helper.setText(R.id.item_home_bottom_price, StringUtil.replaceString(String.valueOf(item.getReferencePrice())));
        } else {
            helper.setText(R.id.item_home_bottom_price, StringUtil.replaceString(String.valueOf(item.getPrice())));
        }
        listType.addAll(item.getLogins());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        RecyclerView recyclerView = helper.getView(R.id.item_home_bottom_recycler);
        recyclerView.setLayoutManager(layoutManager);
        homeBottomTypeAdapter = new HomeBottomTypeAdapter(R.layout.item_home_bottom_type, item.getLogins());
        recyclerView.setAdapter(homeBottomTypeAdapter);

        CardView cardView = helper.getView(R.id.main_bottom_cardView);
        helper.addOnClickListener(R.id.main_bottom_cardView);
    }
}
