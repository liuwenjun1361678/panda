package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.ShareBean;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class SelectTiketAdapter extends BaseQuickAdapter<GoodsDtailsBean.DataBean.TicketTypesBean, BaseViewHolder> {


    public SelectTiketAdapter(int layoutResId, @Nullable List<GoodsDtailsBean.DataBean.TicketTypesBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, GoodsDtailsBean.DataBean.TicketTypesBean item) {

        helper.setText(R.id.select_tiket_name, item.getTicketName());

        helper.setText(R.id.select_ticketTypes_price, String.valueOf(item.getPrice()));
//        helper.addOnClickListener(R.id.item_common_travel_people_del);
    }

}
