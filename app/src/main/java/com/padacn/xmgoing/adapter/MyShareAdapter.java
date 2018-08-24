package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.ShareBean;

/**
 * Created by 46009 on 2018/4/27.
 */

public class MyShareAdapter extends BaseQuickAdapter<ShareBean, BaseViewHolder> {


    public MyShareAdapter(int layoutResId, @Nullable List<ShareBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, ShareBean item) {
        helper.setText(R.id.item_my_share_title, item.getShareTitle());
        ImageView imageView = helper.getView(R.id.item_my_share_icon);
        imageView.setImageResource(item.getShareIcon());
//        helper.addOnClickListener(R.id.item_common_travel_people_del);
    }

}
