package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.Childrenbean;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class CommonTravleAdapter extends BaseQuickAdapter<Childrenbean, BaseViewHolder> {


    public CommonTravleAdapter(int layoutResId, @Nullable List<Childrenbean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Childrenbean item) {
        helper.addOnClickListener(R.id.item_personal_name);
        helper.addOnClickListener(R.id.item_chlid_sex_rl);
        helper.addOnClickListener(R.id.item_chlid_brith_rl);
        helper.addOnClickListener(R.id.item_children_del_rl);


        helper.setText(R.id.item_chlid_sex, item.getSex());
        helper.setText(R.id.item_chlid_brith, item.getBrith());
    }
}
