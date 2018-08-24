package com.padacn.xmgoing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.FootBean;
import com.padacn.xmgoing.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 可展开收起的Adapter。他跟普通的{@link }基本是一样的。
 * 它只是利用了{@link GroupedRecyclerViewAdapter}的
 * 删除一组里的所有子项{@link GroupedRecyclerViewAdapter#notifyChildrenRemoved(int)}} 和
 * 插入一组里的所有子项{@link GroupedRecyclerViewAdapter#notifyChildrenInserted(int)}
 * 两个方法达到列表的展开和收起的效果。
 * 这种列表类似于{@link ExpandableListView}的效果。
 * 这里我把列表的组尾去掉是为了效果上更像ExpandableListView。
 */

public class FootExtendAdapter extends GroupedRecyclerViewAdapter {

    private List<FootBean.DataBean> mGroups;

    public FootExtendAdapter(Context context, List<FootBean.DataBean> groups) {
        super(context);
        mGroups = groups;
    }

    public void setData(List<FootBean.DataBean> groups) {
        this.mGroups.addAll(groups);
        notifyDataChanged();
    }

    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //如果当前组收起，就直接返回0，否则才返回子项数。这是实现列表展开和收起的关键。
        if (!isExpand(groupPosition)) {
            return 0;
        }
        List<FootBean.DataBean.ListBean> children = mGroups.get(groupPosition).getList();
        return children == null ? 0 : children.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.item_foot_group;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_home_three_list;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        FootBean.DataBean entity = mGroups.get(groupPosition);
        holder.setText(R.id.tv_expandable_header, entity.getDate());
        ImageView ivState = holder.get(R.id.iv_state);
        if (entity.isExpand()) {
            ivState.setRotation(90);
        } else {
            ivState.setRotation(0);
        }
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {

        TextView[] textViews = new TextView[3];
        FootBean.DataBean.ListBean entity = mGroups.get(groupPosition).getList().get(childPosition);
        ImageView imageView = holder.get(R.id.item_home_three_list_image);
        CommonUtil.loadImage(mContext, entity.getPics().get(0).getPath(), imageView);


        textViews[0] = holder.get(R.id.home_three_label_1);
        textViews[1] = holder.get(R.id.home_three_label_2);
        textViews[2] = holder.get(R.id.home_three_label_3);

        TextView item_home_three_list_1 = holder.get(R.id.item_home_three_list_1);
        TextView item_home_three_list_2 = holder.get(R.id.item_home_three_list_2);
        int sizeLogin = -1;
        if (entity.getLogins().size() >= 3) {
            sizeLogin = 3;
            item_home_three_list_1.setVisibility(View.VISIBLE);
            item_home_three_list_2.setVisibility(View.VISIBLE);
        } else {
            if (entity.getLogins().size() == 2) {
                item_home_three_list_1.setVisibility(View.VISIBLE);
                item_home_three_list_2.setVisibility(View.GONE);
            }
            if (entity.getLogins().size() <= 1) {
                item_home_three_list_1.setVisibility(View.GONE);
                item_home_three_list_2.setVisibility(View.GONE);
            }
            sizeLogin = entity.getLogins().size();
        }
        for (int i = 0; i < sizeLogin; i++) {
            textViews[i].setText(entity.getLogins().get(i).getLabelName());
        }
        if (entity.isReference()) {
            holder.setText(R.id.home_three_price, String.valueOf(entity.getReferencePrice()));
        } else {
            holder.setText(R.id.home_three_price, String.valueOf(entity.getPrice()));
        }
        holder.setText(R.id.home_three_sale_num, "累计" + String.valueOf(entity.getSaleNum()) + "报名");
        holder.setText(R.id.item_home_three_list_title, entity.getPName());
    }

    /**
     * 判断当前组是否展开
     *
     * @param groupPosition
     * @return
     */
    public boolean isExpand(int groupPosition) {
        FootBean.DataBean entity = mGroups.get(groupPosition);
        return entity.isExpand();
    }

    /**
     * 展开一个组
     *
     * @param groupPosition
     */
    public void expandGroup(int groupPosition) {
        expandGroup(groupPosition, false);
    }

    /**
     * 展开一个组
     *
     * @param groupPosition
     * @param animate
     */
    public void expandGroup(int groupPosition, boolean animate) {
        FootBean.DataBean entity = mGroups.get(groupPosition);
        entity.setExpand(true);
        if (animate) {
            notifyChildrenInserted(groupPosition);
        } else {
            notifyDataChanged();
        }
    }

    /**
     * 收起一个组
     *
     * @param groupPosition
     */
    public void collapseGroup(int groupPosition) {
        collapseGroup(groupPosition, false);
    }

    /**
     * 收起一个组
     *
     * @param groupPosition
     * @param animate
     */
    public void collapseGroup(int groupPosition, boolean animate) {
        FootBean.DataBean entity = mGroups.get(groupPosition);
        entity.setExpand(false);
        if (animate) {
            notifyChildrenRemoved(groupPosition);
        } else {
            notifyDataChanged();
        }
    }
}
