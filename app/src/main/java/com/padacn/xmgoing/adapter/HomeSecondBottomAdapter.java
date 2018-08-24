package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeSecondBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.StringUtil;

/**
 * Created by 46009 on 2018/4/27.
 */

public class HomeSecondBottomAdapter extends BaseQuickAdapter<HomeSecondBean.DataBean.ProductsBean, BaseViewHolder> {

    private HomeSecondBottomTypeAdapter homeSecondBottomTypeAdapter;

    public HomeSecondBottomAdapter(int layoutResId, @Nullable List<HomeSecondBean.DataBean.ProductsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeSecondBean.DataBean.ProductsBean item) {
        RelativeLayout relativeLayout = helper.getView(R.id.item_home_second_bottom_rl);
        RelativeLayout rl = helper.getView(R.id.item_home_second_bottom_type_rl);
        ImageView image = helper.getView(R.id.item_home_second_bottom_image);
        if (item.getPics().size() > 0) {
//            CommonUtil.loadRlImage(mContext, item.getPics().get(0).getPath(), relativeLayout, relativeLayout.getWidth(), relativeLayout.getHeight());
            CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), image);
        }
        helper.setText(R.id.item_home_second_bottom_type, item.getTypeName());
        helper.setText(R.id.item_home_second_bottom_title, item.getPName());
        helper.setText(R.id.item_home_second_bottom_content, item.getPDetail());
        if (item.isReference()) {
            helper.setText(R.id.item_home_second_bottom_price, StringUtil.replaceString(String.valueOf(item.getReferencePrice())));
        } else {
            helper.setText(R.id.item_home_second_bottom_price, StringUtil.replaceString(String.valueOf(item.getPrice())));
        }
        if (item.getPType() == 6 || item.getPType() == 10 || item.getPType() == 14 || item.getPType() == 18) {
            rl.setBackgroundResource(R.mipmap.home_second_left_1);
        }
        if (item.getPType() == 7 || item.getPType() == 11 || item.getPType() == 15 || item.getPType() == 19) {
            rl.setBackgroundResource(R.mipmap.home_second_left_2);
        }
        if (item.getPType() == 8 || item.getPType() == 12 || item.getPType() == 16 || item.getPType() == 20) {
            rl.setBackgroundResource(R.mipmap.home_second_left_3);
        }
        if (item.getPType() == 9 || item.getPType() == 13 || item.getPType() == 17 || item.getPType() == 21) {
            rl.setBackgroundResource(R.mipmap.home_second_left_4);
        }
        RecyclerView recyclerView = helper.getView(R.id.item_home_second_bottom_recycler);
        homeSecondBottomTypeAdapter = new HomeSecondBottomTypeAdapter(R.layout.item_home_bottom_type, item.getLogins());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeSecondBottomTypeAdapter);
    }

}
