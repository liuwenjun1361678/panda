package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.GoodsDtailsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/13 0013.
 */

public class GoodServiceSecurityAdapter extends BaseQuickAdapter<GoodsDtailsBean.DataBean.PromisesBean, BaseViewHolder> {

    public GoodServiceSecurityAdapter(int layoutResId, @Nullable List<GoodsDtailsBean.DataBean.PromisesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDtailsBean.DataBean.PromisesBean item) {

        helper.setText(R.id.item_good_security_title, item.getTittle());
        helper.setText(R.id.item_good_security_contant, item.getContent());
    }
}
