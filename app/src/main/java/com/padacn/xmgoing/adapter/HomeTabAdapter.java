package com.padacn.xmgoing.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;

/**
 * Created by 46009 on 2018/4/27.
 */

public class HomeTabAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public HomeTabAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, String item) {

//        helper.addOnClickListener(R.id.item_common_travel_people_del);
        helper.setText(R.id.item_home_tab_title, item);
      /*  TextView textUnder = helper.getView(R.id.item_home_tab_under);

            textUnder.setBackgroundColor(Color.parseColor("#f8d748"));
        }*/
        ImageView imageView = helper.getView(R.id.item_home_tab_under);
        if (helper.getAdapterPosition() == 0) {
            imageView.setBackgroundResource(R.drawable.shape_item_home_bottom_price);
        }
    }

}
