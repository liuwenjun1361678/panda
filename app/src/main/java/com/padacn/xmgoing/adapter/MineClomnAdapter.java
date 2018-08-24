package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.MineClomnModel;

/**
 * Created by 46009 on 2018/4/27.
 */

public class MineClomnAdapter extends BaseQuickAdapter<MineClomnModel,BaseViewHolder>{


    public MineClomnAdapter(int layoutResId, @Nullable List<MineClomnModel> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, MineClomnModel item) {

        helper.setText(R.id.item_mine_column_text,item.getMineClomnText());
        helper.setImageResource(R.id.item_mine_column_img,item.getMineClomnID());
    }
}
