package com.padacn.xmgoing.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.CommonTravelActivity;
import com.padacn.xmgoing.model.ContactListBean;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.presenter.ContactEventil;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/5/13 0013.
 */

public class GoodSurePeopleAdapter extends BaseQuickAdapter<ContactListBean.ContactIdsBean, BaseViewHolder> {

    private List<ContactListBean.ContactIdsBean> data;
    private String pid;
    private String travelNum;
    private int position;


    public GoodSurePeopleAdapter(int layoutResId, @Nullable List<ContactListBean.ContactIdsBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, ContactListBean.ContactIdsBean item) {

        TextView textView = helper.getView(R.id.people_name);
        LinearLayout linearLayout = helper.getView(R.id.add_people);
        RelativeLayout relativeLayout = helper.getView(R.id.item_item_goods_sure_rl);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.getAdapterPosition() == data.size() - 1) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isAddTravle", true);
                    bundle.putString("travelNum", travelNum);
                    bundle.putString("pid", pid);
                    bundle.putInt("position", position);
                    BaseActivity.navigate(mContext, CommonTravelActivity.class, bundle);
                } else {
                    HomeThreeSelectPresenter.getSingleTon().upDateContactList(position, helper.getAdapterPosition());
                    remove(helper.getAdapterPosition());
                    EventBus.getDefault().post(new ContactEventil(pid, position));
                }
            }
        });
        if (helper.getAdapterPosition() == data.size() - 1) {
            linearLayout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(item.getName());
        }
    }

    public void setCurrData(String travelNum, String pid, int position, List<ContactListBean.ContactIdsBean> data) {
        this.data = data;
        this.travelNum = travelNum;
        this.pid = pid;
        this.position = position;
        notifyDataSetChanged();
    }
}
