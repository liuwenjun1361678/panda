package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.model.ListActivityModel;

/**
 * Created by 46009 on 2018/4/27.
 */

public class MineCollectionAdapter extends BaseQuickAdapter<ListActivityModel,BaseViewHolder>{


    public MineCollectionAdapter(int layoutResId, @Nullable List<ListActivityModel> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ListActivityModel item) {

    }
}
