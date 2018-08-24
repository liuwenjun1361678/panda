package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.MessageBean;
import com.padacn.xmgoing.model.UserDataBean;
import com.padacn.xmgoing.util.CommonUtil;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class MessageAdapter extends BaseQuickAdapter<MessageBean.DataBean, BaseViewHolder> {


    public MessageAdapter(int layoutResId, @Nullable List<MessageBean.DataBean> data) {

        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.DataBean item) {
        helper.setText(R.id.message_title, item.getNewsTitle());
        helper.setText(R.id.message_time, item.getNewsDate());
        helper.setText(R.id.message_content, item.getNewsContent());
        ImageView imageView = helper.getView(R.id.message_image);
        CommonUtil.loadImage(mContext, item.getNewsHeaderPic(), imageView);
    }
}
