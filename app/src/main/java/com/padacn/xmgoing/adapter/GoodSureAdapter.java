package com.padacn.xmgoing.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.ContactListBean;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.vondear.rxtools.RxDataTool;

/**
 * Created by Administrator on 2018/5/20 0020.
 */

public class GoodSureAdapter extends BaseQuickAdapter<SureGoodsBean.GoodsBean, BaseViewHolder> {


    private List<SureGoodsBean.GoodsBean.ContactIdsBean> dataContact;
    private int currPosition = -1;
    private GoodSurePeopleAdapter goodSurePeopleAdapter;
    private boolean isClick;

    private int currNum;//当前商品的数量
    private List<ContactListBean> contactsDataBeanList;
    private List<ContactListBean.ContactIdsBean> contactIdsBeanList;

    public GoodSureAdapter(int layoutResId, @Nullable List<SureGoodsBean.GoodsBean> data) {
        super(layoutResId, data);
        dataContact = new ArrayList<>();
        dataContact.add(new SureGoodsBean.GoodsBean.ContactIdsBean());
        contactsDataBeanList = new ArrayList<>();
        contactIdsBeanList = new ArrayList<>();
        contactIdsBeanList.add(new ContactListBean.ContactIdsBean());
        for (int i = 0; i < data.size(); i++) {
            ContactListBean contactListBean = new ContactListBean();
            contactListBean.setContactIds(contactIdsBeanList);
            contactListBean.setPid("");
            contactsDataBeanList.add(contactListBean);
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SureGoodsBean.GoodsBean item) {

        ImageView imageView = helper.getView(R.id.good_sure_image);
        CommonUtil.loadImage(mContext, item.getPicPath(), imageView);
        helper.setText(R.id.shop_buy_sure_title, item.getPName());
        helper.setText(R.id.meal_name, item.getTiketName());
        helper.setText(R.id.price, "￥" + item.getPrice());
        helper.setText(R.id.number, "×" + item.getPnum());
        helper.setText(R.id.good_num, "共" + item.getPnum() + "件商品");
        if (RxDataTool.isNullString(item.getTravleTime())) {
            helper.setText(R.id.tarvle_date, "无时间限制");
        } else {
            helper.setText(R.id.tarvle_date, item.getTravleTime());
        }
        helper.setText(R.id.total_price, item.getTotalPrice());
        LinearLayout useIdCard = helper.getView(R.id.good_sure_travle_data_ll);

        //出行人
        if (!item.isUseIdcard()) {
            useIdCard.setVisibility(View.GONE);
        } else {
            useIdCard.setVisibility(View.VISIBLE);
            helper.setText(R.id.item_travle_people_num, item.getTravelNum());
        }
        RecyclerView recyclerView = helper.getView(R.id.shop_buy_sure_rc);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        goodSurePeopleAdapter = new GoodSurePeopleAdapter(R.layout.item_item_goods_sure, contactsDataBeanList.get(helper.getAdapterPosition()).getContactIds());
        recyclerView.setAdapter(goodSurePeopleAdapter);
        goodSurePeopleAdapter.setCurrData(item.getTravelNum(), item.getTid(), helper.getAdapterPosition(), contactsDataBeanList.get(helper.getAdapterPosition()).getContactIds());
     /*   if (helper.getAdapterPosition() == currPosition) {
            dataContact.add(new SureGoodsBean.GoodsBean.ContactIdsBean());
            Log.e(TAG, "convert:111111 " + dataContact.size());
            goodSurePeopleAdapter.setNewData(dataContact);
        }*/

        helper.addOnClickListener(R.id.buy_you_know);
    }


    public void setTravle(List<SureGoodsBean.GoodsBean.ContactIdsBean> contactIdsBeanList, int currPosition, boolean isClick) {
        this.dataContact = contactIdsBeanList;
        this.currPosition = currPosition;
        this.isClick = isClick;
        notifyItemChanged(currPosition);
//        notifyDataSetChanged();
    }

    public void setList(List<ContactListBean> contactsDataBeanList, int curNum, int currPosition) {
        //给每次用户选中的item增加一个尾部固定添加
        ContactListBean.ContactIdsBean contactIdsBean = new ContactListBean.ContactIdsBean();
        contactIdsBean.setId("");
        contactIdsBean.setName("");
        contactsDataBeanList.get(currPosition).getContactIds().add(contactIdsBean);
        this.contactsDataBeanList = contactsDataBeanList;
        notifyDataSetChanged();
    }
}