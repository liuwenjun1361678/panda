package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.ListActivityModel;

import java.util.List;

/**
 * Created by Administrator on 2018/5/13 0013.
 */

public class GoodSecurityAdapter extends BaseQuickAdapter<GoodsDtailsBean.DataBean.PromisesBean, BaseViewHolder> {

    public GoodSecurityAdapter(int layoutResId, @Nullable List<GoodsDtailsBean.DataBean.PromisesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDtailsBean.DataBean.PromisesBean item) {

        helper.setText(R.id.item_good_security_title,item.getTittle());
    }
}
