package com.padacn.xmgoing.adapter.homesearch;

import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeSearchBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.StringUtil;

import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class SearchGoodsAdapter extends BaseQuickAdapter<HomeSearchBean.DataBean.ProductsBean, BaseViewHolder> {

    private String currText;
    private TextView[] textViews = new TextView[3];

    public SearchGoodsAdapter(int layoutResId, @Nullable List<HomeSearchBean.DataBean.ProductsBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, HomeSearchBean.DataBean.ProductsBean item) {

        textViews[0] = helper.getView(R.id.home_three_label_1);
        textViews[1] = helper.getView(R.id.home_three_label_2);
        textViews[2] = helper.getView(R.id.home_three_label_3);
        TextView item_home_three_list_1 = helper.getView(R.id.item_home_three_list_1);
        TextView item_home_three_list_2 = helper.getView(R.id.item_home_three_list_2);
        int sizeLogin = -1;
        if (item.getLogins().size() >= 3) {
            sizeLogin = 3;
            item_home_three_list_1.setVisibility(View.VISIBLE);
            item_home_three_list_2.setVisibility(View.VISIBLE);
        } else {
            if (item.getLogins().size() == 2) {
                item_home_three_list_1.setVisibility(View.VISIBLE);
                item_home_three_list_2.setVisibility(View.GONE);
            }
            if (item.getLogins().size() == 1) {
                item_home_three_list_1.setVisibility(View.GONE);
                item_home_three_list_2.setVisibility(View.GONE);
            }
            sizeLogin = item.getLogins().size();
        }
        for (int i = 0; i < sizeLogin; i++) {
            textViews[i].setText(item.getLogins().get(i).getLabelName());
        }

        CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), (ImageView) helper.getView(R.id.item_home_three_list_image));
        helper.setText(R.id.home_three_price, StringUtil.replaceString(String.valueOf(item.getPrice())));
        helper.setText(R.id.home_three_sale_num, "累计" + String.valueOf(item.getSaleNum()) + "报名");
        helper.setText(R.id.item_home_three_list_title, Html.fromHtml(StringUtil.matcherSearchTitle(item.getPName(), currText)));
    }

    public void setText(String text) {
        this.currText = text;
    }
}
