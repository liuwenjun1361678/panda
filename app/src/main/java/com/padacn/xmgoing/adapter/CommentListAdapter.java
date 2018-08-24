package com.padacn.xmgoing.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.CommentListBean;
import com.padacn.xmgoing.model.ContactsDataBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.GlideImageLoader;
import com.padacn.xmgoing.view.FullyGridLayoutManager;
import com.padacn.xmgoing.view.ZQRoundOvalImageView;
import com.padacn.xmgoing.view.ratingbar.BaseRatingBar;
import com.padacn.xmgoing.view.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class CommentListAdapter extends BaseQuickAdapter<CommentListBean.DataBean, BaseViewHolder> {

    public CommentListAdapter(int layoutResId, @Nullable List<CommentListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CommentListBean.DataBean item) {
        ZQRoundOvalImageView zqRoundOvalImageView = helper.getView(R.id.comment_head);
        CommonUtil.loadImage(mContext, item.getUserSculpture(), zqRoundOvalImageView);
        helper.setText(R.id.comment_name, item.getUserName());
        helper.setText(R.id.comment_contant, item.getReviews());
        helper.setText(R.id.comment_time, item.getReviewDate());
        BaseRatingBar scaleRatingBar = helper.getView(R.id.comment_list_ratingBar);
        scaleRatingBar.setClickable(false);
        scaleRatingBar.setRating((float) item.getScore());
//        scaleRatingBar.setNumStars(item.getScore());
        NineGridView nineGrid = helper.getView(R.id.nineGrid);

        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        if (item.getPicPath() != null) {

            for (int i = 0; i < item.getPicPath().size(); i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(item.getPicPath().get(i));
                info.setBigImageUrl(item.getPicPath().get(i));
                imageInfo.add(info);
            }
        }
        NineGridView.setImageLoader(new GlideImageLoader());
        nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
    }

}
