package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.homethree.HomeThreeFilterItemAdapter;
import com.padacn.xmgoing.callback.OnGetChildrenDataLinster;
import com.padacn.xmgoing.model.Childrenbean;
import com.padacn.xmgoing.model.HomeThreeBean;
import com.padacn.xmgoing.model.UserDataBean;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class ChildrenAdapter extends BaseQuickAdapter<UserDataBean.ChildBean, BaseViewHolder> {

    List<Childrenbean> childrenbeanList;

    public ChildrenAdapter(int layoutResId, @Nullable List<UserDataBean.ChildBean> data) {
        super(layoutResId, data);
        Log.e(TAG, "ChildrenAdapter: " + data.size());
        childrenbeanList = new ArrayList<>();
    }


    @Override
    protected void convert(BaseViewHolder helper, UserDataBean.ChildBean item) {
        MClearEditText name = helper.getView(R.id.item_personal_name);
        RelativeLayout del = helper.getView(R.id.item_children_del_rl);
        helper.addOnClickListener(R.id.item_personal_name);
        helper.addOnClickListener(R.id.item_chlid_sex_rl);
        helper.addOnClickListener(R.id.item_chlid_brith_rl);
        helper.addOnClickListener(R.id.item_children_del_rl);
        helper.setText(R.id.item_personal_name, item.getChildName());
        if (item.getChildSex().equals("1")) {
            helper.setText(R.id.item_chlid_sex, "男");
        } else if (item.getChildSex().equals("2")) {
            helper.setText(R.id.item_chlid_sex, "女");
        }
        helper.setText(R.id.item_chlid_brith, item.getChildBirthday());
        name.addTextChangedListener(new TextSwitcher(helper));
        name.setTag(helper.getAdapterPosition());
        if (getData().size() <= 1) {
            del.setVisibility(View.GONE);
        } else {
            del.setVisibility(View.VISIBLE);
        }
//        Childrenbean childrenbean = new Childrenbean();
//        childrenbean.setBrith(item.getChildBirthday());
//        childrenbean.setSex(item.getChildSex());
//        childrenbean.setName(name.getText().toString());
////        childrenbeanList.add(helper.getAdapterPosition(), childrenbean);
//        HomeThreeSelectPresenter.getSingleTon().saveChildBeanList(childrenbeanList);
//        onGetChildrenDataLinster.onItemData(helper.getAdapterPosition(), name.getText().toString());
    }

    private OnGetChildrenDataLinster onGetChildrenDataLinster = null;

    public void setChildrenDataLinster(OnGetChildrenDataLinster listener) {
        onGetChildrenDataLinster = listener;
    }

    class TextSwitcher implements TextWatcher {
        private BaseViewHolder baseViewHolder;

        public TextSwitcher(BaseViewHolder baseViewHolder) {
            this.baseViewHolder = baseViewHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null) {
                onGetChildrenDataLinster.onItemData(baseViewHolder.getAdapterPosition(), s.toString());
            }
        }
    }
}
