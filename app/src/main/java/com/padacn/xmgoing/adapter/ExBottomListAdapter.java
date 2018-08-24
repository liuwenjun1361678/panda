package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.model.PreferentialBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.DoubleArith;
import com.padacn.xmgoing.util.StringUtil;

/**
 * Created by 46009 on 2018/4/27.
 */

public class ExBottomListAdapter extends BaseQuickAdapter<PreferentialBean.DataBean.ReferenceProductBean, BaseViewHolder> {


    public ExBottomListAdapter(int layoutResId, @Nullable List<PreferentialBean.DataBean.ReferenceProductBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, PreferentialBean.DataBean.ReferenceProductBean item) {

        helper.setText(R.id.ex_goods_title, item.getPName());
        CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), (ImageView) helper.getView(R.id.item_ex_list_image));
        helper.setText(R.id.old_price, mContext.getResources().getString(R.string.moneny_string) + " " + StringUtil.replaceString(String.valueOf(item.getPrice())));
        helper.setText(R.id.lijian_price, "立减" + StringUtil.replaceString(String.valueOf(item.getReducePrice())) + "元");
        helper.setText(R.id.item_ex_list_price, StringUtil.replaceString(String.valueOf(item.getReferencePrice())));
      /*  float percent = (float) item.getSaleNum() / item.getPNum() * 100;
        ProgressBar progressBar = helper.getView(R.id.progressbar_1);
        progressBar.setProgress((int) percent);*/
        helper.setText(R.id.item_ex_list_sell_num, "爆卖" + String.valueOf(item.getSaleNum()) + "份");

    }
}
