package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.model.ListActivityModel;

/**
 * Created by Administrator on 2018/5/13 0013.
 */

public class GoodStoreCouponsAdapter extends BaseQuickAdapter<ListActivityModel, BaseViewHolder> {

    public GoodStoreCouponsAdapter(int layoutResId, @Nullable List<ListActivityModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListActivityModel item) {

    }
}
