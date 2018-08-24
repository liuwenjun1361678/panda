package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.Childrenbean;
import com.padacn.xmgoing.util.CommonUtil;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class OrderAllItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public OrderAllItemAdapter(int layoutResId, @Nullable List<String> data) {

        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView imageView = helper.getView(R.id.item_item_order_all_imageView);
        CommonUtil.loadImage(mContext, item.toString(), imageView);
    }

}
