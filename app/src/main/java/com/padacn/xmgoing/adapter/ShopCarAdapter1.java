package com.padacn.xmgoing.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.BusinessDetailActivity;
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.callback.OnClickAddCloseListenter;
import com.padacn.xmgoing.callback.OnClickDeleteListenter;
import com.padacn.xmgoing.callback.OnClickListenterModel;
import com.padacn.xmgoing.callback.OnClickShareListenter;
import com.padacn.xmgoing.callback.OnViewItemClickListener;
import com.padacn.xmgoing.model.CartInfo;
import com.padacn.xmgoing.model.ShopCarBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.FrontViewToMove;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.view.ZQRoundOvalImageView;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

/**
 * Created by 46009 on 2018/5/8.
 */
public class ShopCarAdapter1 extends BaseExpandableListAdapter {

    private Context context;
    private List<ShopCarBean.DataBean.BuyCarBean> list;


    public ShopCarAdapter1(Context context, ListView listView, List<ShopCarBean.DataBean.BuyCarBean> list) {
        super();
        this.context = context;
        this.list = list;
    }


    @Override
    public int getGroupCount() {
        return (list != null && list.size() > 0) ? list.size() : 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return (list != null && list.size() > 0) ? list.get(i).getCars().size() : 0;
    }


    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return list.get(i).getCars().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return getCombinedChildId(i, i1);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopcart_list_group, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (groupPosition == 0) {
//            viewHolder.textTopBar.setVisibility(View.GONE);
        }


        final ShopCarBean.DataBean.BuyCarBean dataBean = (ShopCarBean.DataBean.BuyCarBean) getGroup(groupPosition);
        viewHolder.textView.setText(dataBean.getName());
//        viewHolder.checkBox.setChecked(dataBean.ischeck());

        if (dataBean.ischeck()) {
            viewHolder.checkBox.setBackgroundResource(R.mipmap.round_check_active);
        } else {
            viewHolder.checkBox.setBackgroundResource(R.mipmap.round_check_selected);
        }

        viewHolder.textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bundle bundle = new Bundle();
                    bundle.putString("sellerId", String.valueOf(dataBean.getSellerId()));
                    BaseActivity.navigate(context, BusinessDetailActivity.class, bundle);
                    return true;
                }
                return false;
            }
        });
      /*  //进入店铺
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        viewHolder.check_box_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(!dataBean.ischeck(), v, groupPosition);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int position, boolean b, View view, ViewGroup viewGroup) {
        final ViewHolder1 viewHolder1;
        view = LayoutInflater.from(context).inflate(R.layout.item_shopcart_list_child, null);
        viewHolder1 = new ViewHolder1(view, groupPosition, position);

        viewHolder1.textView.setText(list.get(groupPosition).getCars().get(position).getDetailDto().getPName());
//        viewHolder1.checkBox.setChecked(list.get(groupPosition).getCars().get(position).ischeck());
        viewHolder1.tvMoney.setText(StringUtil.replaceString(String.valueOf(list.get(groupPosition).getCars().get(position).getDetailDto().getPrice())));
        viewHolder1.btnNum.setText(list.get(groupPosition).getCars().get(position).getProNum() + "");

        if (RxDataTool.isEmpty(list.get(groupPosition).getCars().get(position).getUseDay())) {
            viewHolder1.mealText.setText("出行日期: 无时间限制");
        } else {
            viewHolder1.mealText.setText("出行日期: " + list.get(groupPosition).getCars().get(position).getUseDay());
        }
        viewHolder1.tiketName.setText("套餐类型: " + list.get(groupPosition).getCars().get(position).getTicketName());

        viewHolder1.zqRoundOvalImageView.setType(ZQRoundOvalImageView.TYPE_ROUND);
        viewHolder1.zqRoundOvalImageView.setRoundRadius(0);

        CommonUtil.loadMemoryImage(context, list.get(groupPosition).getCars().get(position).getDetailDto().getPics().get(0).getPath(),
                viewHolder1.zqRoundOvalImageView);
        if (list.get(groupPosition).getCars().get(position).ischeck()) {
            viewHolder1.checkBox.setBackgroundResource(R.mipmap.round_check_active);
        } else {
            viewHolder1.checkBox.setBackgroundResource(R.mipmap.round_check_selected);
        }
        viewHolder1.item_chlid_check_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenterModel.onItemClick(!list.get(groupPosition).getCars().get(position).ischeck(), v, groupPosition, position);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void setNewData(List<ShopCarBean.DataBean.BuyCarBean> buyCarBeanList) {
        this.list = buyCarBeanList;
        notifyDataSetChanged();
    }


    class ViewHolder1 implements View.OnClickListener {
        private int groupPosition;
        private int position;
        private TextView textView;
        private View frontView;
        private Button button;
        private ImageView checkBox;
        private ZQRoundOvalImageView zqRoundOvalImageView;
        private TextView tvMoney;
        private Button btnAdd;
        private Button btnNum;
        private Button btnClose;
        private TextView tiketName;
        private TextView mealText;
        private LinearLayout item_chlid_right;
        private TextView right_menu_1;
        private TextView right_menu_2;

        private RelativeLayout item_chlid_check_rl;

        public ViewHolder1(View view, int groupPosition, int position) {
            this.groupPosition = groupPosition;
            this.position = position;
            zqRoundOvalImageView = (ZQRoundOvalImageView) view.findViewById(R.id.item_chlid_image);
            textView = (TextView) view.findViewById(R.id.item_chlid_name);
            checkBox = (ImageView) view.findViewById(R.id.item_chlid_check);
//            button = (Button) view.findViewById(R.id.btn_delete);
            frontView = view.findViewById(R.id.id_front);
            tvMoney = (TextView) view.findViewById(R.id.item_chlid_money);
            tiketName = (TextView) view.findViewById(R.id.item_chlid_content);
            btnAdd = (Button) view.findViewById(R.id.item_chlid_add);
            btnAdd.setOnClickListener(this);
            btnNum = (Button) view.findViewById(R.id.item_chlid_num);
            btnClose = (Button) view.findViewById(R.id.item_chlid_close);
            mealText = (TextView) view.findViewById(R.id.item_chlid_date);
//            right_menu_1 = (TextView) view.findViewById(R.id.right_menu_1);
            right_menu_2 = (TextView) view.findViewById(R.id.right_menu_2);
            item_chlid_check_rl = (RelativeLayout) view.findViewById(R.id.item_chlid_check_rl);
            item_chlid_right = (LinearLayout) view.findViewById(R.id.item_chlid_right);
            btnClose.setOnClickListener(this);
//            right_menu_1.setOnClickListener(this);
            right_menu_2.setOnClickListener(this);
            frontView.setOnClickListener(this);
            zqRoundOvalImageView.setOnClickListener(this);
            item_chlid_right.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            switch (v.getId()) {
                case R.id.item_chlid_right:
                    bundle.putString("pid", String.valueOf(list.get(groupPosition).getCars().get(position).getProId()));
                    bundle.putBoolean("panic", false);
                    BaseActivity.navigate(context, GoodsDetailsActivity.class, bundle);
                    break;

                case R.id.item_chlid_image:
                    bundle.putString("pid", String.valueOf(list.get(groupPosition).getCars().get(position).getProId()));
                    bundle.putBoolean("panic", false);
                    BaseActivity.navigate(context, GoodsDetailsActivity.class, bundle);
                    break;
                case R.id.item_chlid_add:
                    onClickAddCloseListenter.onItemClick(v, 2, groupPosition, position, Integer.valueOf(btnNum.getText().toString()));
                    break;
                case R.id.item_chlid_close:
                    onClickAddCloseListenter.onItemClick(v, 1, groupPosition, position, Integer.valueOf(btnNum.getText().toString()));
                    break;
              /*  case R.id.right_menu_1:
                    onClickShareListenter.onItemClick(v, groupPosition, position);
                    break;*/
                case R.id.right_menu_2:
                    onClickDeleteListenter.onItemClick(v, groupPosition, position);
            /*        list.get(groupPosition).getCars().remove(position);
                  if (list.get(groupPosition).getCars().size() < 1) {
                        list.remove(groupPosition);
                    }
                    notifyDataSetChanged();*/
                    break;
            }
        }

    }


    // CheckBox1接口的方法
    private OnViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // CheckBox2接口的方法
    private OnClickListenterModel onClickListenterModel = null;

    public void setOnClickListenterModel(OnClickListenterModel listener) {
        this.onClickListenterModel = listener;
    }

    // 删除接口的方法
    private OnClickDeleteListenter onClickDeleteListenter = null;

    public void setOnClickDeleteListenter(OnClickDeleteListenter listener) {
        this.onClickDeleteListenter = listener;
    }

    // 分享接口的方法
    private OnClickShareListenter onClickShareListenter = null;

    public void setOnClickShareListenter(OnClickShareListenter listener) {
        this.onClickShareListenter = listener;
    }


    // 数量接口的方法
    private OnClickAddCloseListenter onClickAddCloseListenter = null;

    public void setOnClickAddCloseListenter(OnClickAddCloseListenter listener) {
        this.onClickAddCloseListenter = listener;
    }

    class ViewHolder {
        ImageView checkBox;
        TextView textView;
        //        TextView textTopBar;
        private RelativeLayout check_box_rl;
        private LinearLayout shop_name_ll;


        public ViewHolder(View view) {
            shop_name_ll = (LinearLayout) view.findViewById(R.id.shop_name_ll);
            textView = (TextView) view.findViewById(R.id.shop_name);
            checkBox = (ImageView) view.findViewById(R.id.check_box);
            check_box_rl = (RelativeLayout) view.findViewById(R.id.check_box_rl);
//            textTopBar = (TextView) view.findViewById(R.id.item_group_topbar);
        }
    }
}
