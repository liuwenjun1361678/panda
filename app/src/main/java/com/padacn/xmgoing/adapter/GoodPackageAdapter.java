package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.util.StringUtil;

/**
 * Created by Administrator on 2018/5/13 0013.
 */

public class GoodPackageAdapter extends BaseQuickAdapter<GoodsDtailsBean.DataBean.TicketTypesBean, BaseViewHolder> {

    private int statu;

    public GoodPackageAdapter(int layoutResId, @Nullable List<GoodsDtailsBean.DataBean.TicketTypesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDtailsBean.DataBean.TicketTypesBean item) {
        if (item.isUseCalender()) {
            helper.setText(R.id.ticketTypes_contant, "日期：" + String.valueOf(item.getTicketDates()));
        } else {
            helper.setText(R.id.ticketTypes_contant, "日期：" + String.valueOf(item.getPeriodOfTime()));
        }

        TextView textView = helper.getView(R.id.ticketTypes_buy);
        helper.addOnClickListener(R.id.ticketTypes_instructions);
        helper.addOnClickListener(R.id.ticketTypes_buy);
        helper.setText(R.id.ticketTypes_title, item.getTicketName());
        helper.setText(R.id.ticketTypes_price, StringUtil.replaceString(String.valueOf(item.getPrice())));
        switch (statu) {
            case 1:
                textView.setText("立即购买");
                textView.setBackgroundResource(R.drawable.shape_home_bottom_price_bg);
                break;
            case 2:
                textView.setText("售罄");
                textView.setBackgroundResource(R.drawable.shape_home_bottom_price_bg_false);
                break;
            case 3:
                textView.setText("下架");
                textView.setBackgroundResource(R.drawable.shape_home_bottom_price_bg_false);
                break;
        }
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }
}
