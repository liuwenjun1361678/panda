package com.padacn.xmgoing.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.CaladerDataBean;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.presenter.SeleDateEventil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by 46009 on 2018/4/27.
 */

public class SelectDateAdapter extends BaseQuickAdapter<CaladerDataBean, BaseViewHolder> {
    private List<GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean.CanlenderDetailsBean> canlenderDetailsBeanList;
    //    private List<CaladerDataBean> caladerDataBeanList;
    private int getFirstDayWeekPosition;
    private static String currDate;
    private int currClick;
    private boolean isReset;
    //用户点击月份的下标
    private int positionMonth;
    //用户选择了具體日期的月份下标
    private int userMonthPosition;

    private int lastPosition = -1;

    public SelectDateAdapter(int layoutResId, @Nullable List<CaladerDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CaladerDataBean item) {

        helper.setText(R.id.item_price, "");
        LinearLayout linearLayout = helper.getView(R.id.background);
        linearLayout.setBackgroundResource(0);
        if (helper.getAdapterPosition() >= getFirstDayWeekPosition) {
            helper.setText(R.id.item_date, item.getCurrDate());
            for (int i = 0; i < canlenderDetailsBeanList.size(); i++) {
                if (item.getCurrDataString().equals(canlenderDetailsBeanList.get(i).getUseDay())) {
                    if (canlenderDetailsBeanList.get(i).getNumber() == 0) {
                        helper.setText(R.id.item_price, "已售罄");
                    } else {
                        helper.setText(R.id.item_price, "￥" + String.valueOf(canlenderDetailsBeanList.get(i).getPrice()));
                        TextView price = helper.getView(R.id.item_price);
                        TextView data = helper.getView(R.id.item_date);
                        data.setTextColor(Color.parseColor("#111111"));
                        price.setTextColor(Color.parseColor("#111111"));
                        item.setClick(true);
                        item.setCurrNum(canlenderDetailsBeanList.get(i).getNumber());
                        item.setCurrPrice(canlenderDetailsBeanList.get(i).getPrice());
                        item.setCurrTicketCalenderId(canlenderDetailsBeanList.get(i).getTicketCalenderId());
                    }
                }
            }

        } else {
            helper.setText(R.id.item_date, "");
            helper.setText(R.id.item_price, "");
            linearLayout.setBackgroundResource(0);
        }
        Log.e(TAG, "setPositionMonth: " + positionMonth + "___" + userMonthPosition + "___" + currClick);
        linearLayout.setBackgroundResource(0);


        if (item.isClick()) {
            if (currClick == helper.getAdapterPosition()) {
                linearLayout.setBackgroundResource(R.drawable.shape_login_button);
                currDate = item.getCurrDataString();
                EventBus.getDefault().post(new SeleDateEventil(item.getCurrDataString(), item.getCurrPrice(), item.getCurrNum(), item.getCurrTicketCalenderId(), positionMonth, currClick));
            } else {
                linearLayout.setBackgroundResource(0);
            }
        }
        if (positionMonth == userMonthPosition) {
            if (lastPosition == helper.getAdapterPosition()) {
                linearLayout.setBackgroundResource(R.drawable.shape_login_button);
            }
        }

        if (isReset) {
            linearLayout.setBackgroundResource(0);
        }

    }

    public void setFirstDayWeekPosition(int getFirstDayWeekPosition) {
        this.getFirstDayWeekPosition = getFirstDayWeekPosition;

    }

    public void setCanlenderData(List<GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean.CanlenderDetailsBean> canlenderDetailsBeanList) {
        this.canlenderDetailsBeanList = new ArrayList<>();
        this.canlenderDetailsBeanList.clear();
        this.canlenderDetailsBeanList.addAll(canlenderDetailsBeanList);
        notifyDataSetChanged();
    }

    public void setCurrClick(int position) {
        this.currClick = position;
    }

    public void isReset(boolean isReset) {
        this.isReset = isReset;
    }

    public void setPositionMonth(int positionMonth, int userMonthPosition, int lastPosition) {
        this.positionMonth = positionMonth;
        this.userMonthPosition = userMonthPosition;
        this.lastPosition = lastPosition;
        if (positionMonth != userMonthPosition) {
            currClick = -1;
        }
        notifyDataSetChanged();
    }
}
