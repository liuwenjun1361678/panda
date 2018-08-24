package com.padacn.xmgoing.adapter.homesearch;

import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeSearchTravleBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.StringUtil;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class SearchTravelAdapter extends BaseQuickAdapter<HomeSearchTravleBean.DataBean, BaseViewHolder> {

    private String currText;

    public SearchTravelAdapter(int layoutResId, @Nullable List<HomeSearchTravleBean.DataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, HomeSearchTravleBean.DataBean item) {

        helper.addOnClickListener(R.id.item_common_travel_people_del);
        CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), (ImageView) helper.getView(R.id.item_mine_my_collection_image));
        helper.setText(R.id.item_mine_my_collection_title, Html.fromHtml(StringUtil.matcherSearchTitle(item.getStrategyName(), currText)));

        helper.setText(R.id.item_mine_my_collection_date, item.getCreateTime());
        helper.setText(R.id.item_mine_my_collection_publisher, "发布者：" + item.getPublish());
    }

    public void setText(String currSearchStr) {
        this.currText = currSearchStr;
    }
}
