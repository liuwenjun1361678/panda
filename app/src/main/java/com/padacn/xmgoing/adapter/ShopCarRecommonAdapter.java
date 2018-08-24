package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.ShopCarBean;
import com.padacn.xmgoing.model.UserDataBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.StringUtil;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class ShopCarRecommonAdapter extends BaseQuickAdapter<ShopCarBean.DataBean.RecommondProductBean, BaseViewHolder> {


    public ShopCarRecommonAdapter(int layoutResId, @Nullable List<ShopCarBean.DataBean.RecommondProductBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ShopCarBean.DataBean.RecommondProductBean item) {
        ImageView imageView = helper.getView(R.id.item_shopcar_recommon_image);
        CommonUtil.loadMemoryImage(mContext, item.getPics().get(0).getPath(), imageView);
        helper.setText(R.id.item_shopcar_recommon_title, item.getPName());
        helper.setText(R.id.item_shopcar_recommon_price, mContext.getResources().getString(R.string.moneny_string) + " " + StringUtil.replaceString(String.valueOf(item.getPrice())));
        helper.setText(R.id.item_shopcar_recommon_num, item.getSaleNum() + "人购买");
    }
}
