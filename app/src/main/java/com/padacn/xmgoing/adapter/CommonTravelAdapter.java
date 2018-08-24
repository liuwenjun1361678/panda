package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.ContactsDataBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.model.SureGoodsBean;

/**
 * Created by 46009 on 2018/4/27.
 */

public class CommonTravelAdapter extends BaseQuickAdapter<ContactsDataBean.DataBean, BaseViewHolder> {

    private boolean isAddTravle;


    public CommonTravelAdapter(int layoutResId, @Nullable List<ContactsDataBean.DataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, ContactsDataBean.DataBean item) {
        helper.addOnClickListener(R.id.item_common_travel_people_del);
        helper.addOnClickListener(R.id.item_common_travel_people_ll);
        CheckBox checkBox = helper.getView(R.id.item_chlid_check);
        if (isAddTravle) {
            checkBox.setVisibility(View.VISIBLE);
            if (item.isCheck()) {
                checkBox.setBackgroundResource(R.drawable.round_check_active);
            } else {
                checkBox.setBackgroundResource(R.drawable.round_check_selected);
            }
        } else {
            checkBox.setVisibility(View.GONE);
        }

        if (item.getIsChildren().equals("1")) {
            helper.setText(R.id.people_type, "儿童");
        } else {
            helper.setText(R.id.people_type, "成人");
        }

        helper.setText(R.id.common_contacts_name, item.getConName());
        helper.setText(R.id.item_common_travel_people_phone, item.getConPhoneNumber());
        helper.setText(R.id.item_common_travel_people_idcard, item.getConIdcard());


    }

    public void setBoolean(boolean isAddTravle) {
        this.isAddTravle = isAddTravle;
    }
}
