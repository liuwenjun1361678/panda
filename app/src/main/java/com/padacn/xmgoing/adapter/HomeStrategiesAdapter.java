package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeBean;
import com.padacn.xmgoing.util.CommonUtil;

/**
 * Created by 46009 on 2018/4/27.
 */

public class HomeStrategiesAdapter extends BaseQuickAdapter<HomeBean.DataBean.StrategiesRandBean, BaseViewHolder> {


    public HomeStrategiesAdapter(int layoutResId, @Nullable List<HomeBean.DataBean.StrategiesRandBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeBean.DataBean.StrategiesRandBean item) {
        RelativeLayout oneRl = helper.getView(R.id.home_strategiesRandom_ll_1);
        ImageView oneImage = helper.getView(R.id.home_strategiesRandom_image_1);

        LinearLayout twoLL = helper.getView(R.id.home_strategiesRandom_ll_2);
        ImageView twoImage1 = helper.getView(R.id.home_strategiesRandom_image2_1);
        ImageView twoImage2 = helper.getView(R.id.home_strategiesRandom_image2_2);

        LinearLayout threeLL = helper.getView(R.id.home_strategiesRandom_ll_3);
        ImageView threeImage1 = helper.getView(R.id.home_strategiesRandom_image3_1);
        ImageView threeImage2 = helper.getView(R.id.home_strategiesRandom_image3_2);
        ImageView threeImage3 = helper.getView(R.id.home_strategiesRandom_image3_3);

        oneRl.setVisibility(View.GONE);
        twoLL.setVisibility(View.GONE);
        threeLL.setVisibility(View.GONE);
        switch (item.getPics().size()) {
            case 1:
                oneRl.setVisibility(View.VISIBLE);
                helper.setText(R.id.home_strategiesRandom_title_1, item.getStrategyName());
                helper.setText(R.id.home_strategiesRandom_publisher_1, "发布者：" + item.getPublish());
                helper.setText(R.id.home_strategiesRandom_look_1, "");
                helper.setText(R.id.home_strategiesRandom_like_1, String.valueOf(item.getLikes()));
                CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), oneImage);
                break;
            case 2:
                twoLL.setVisibility(View.VISIBLE);
                helper.setText(R.id.home_strategiesRandom_title_2, item.getStrategyName());
                helper.setText(R.id.home_strategiesRandom_publisher_2, "发布者：" + item.getPublish());
                helper.setText(R.id.home_strategiesRandom_look_2, "");
                helper.setText(R.id.home_strategiesRandom_like_2, String.valueOf(item.getLikes()));
                CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), twoImage1);
                CommonUtil.loadImage(mContext, item.getPics().get(1).getPath(), twoImage2);
                break;
            case 3:
                threeLL.setVisibility(View.VISIBLE);
                helper.setText(R.id.home_strategiesRandom_title_3, item.getStrategyName());
                helper.setText(R.id.home_strategiesRandom_publisher_3, "发布者：" + item.getPublish());
                helper.setText(R.id.home_strategiesRandom_look_3, "");
                helper.setText(R.id.home_strategiesRandom_like_3, String.valueOf(item.getLikes()));
                CommonUtil.loadImage(mContext, item.getPics().get(0).getPath(), threeImage1);
                CommonUtil.loadImage(mContext, item.getPics().get(1).getPath(), threeImage2);
                CommonUtil.loadImage(mContext, item.getPics().get(2).getPath(), threeImage3);
                break;
        }
    }
}
