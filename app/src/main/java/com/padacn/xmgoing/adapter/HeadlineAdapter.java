package com.padacn.xmgoing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.view.ZQRoundOvalImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 46009 on 2018/4/27.
 */

public class HeadlineAdapter extends BaseAdapter {

    private static final String TAG = "HeadlineAdapter";
    List<HomeBean.DataBean.TopLineBean> topLineBeanList;
    private Context context;

    public HeadlineAdapter(Context context, List<HomeBean.DataBean.TopLineBean> topLineBeanList) {
        this.topLineBeanList = topLineBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return topLineBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return topLineBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_headline, viewGroup, false);
//            holder.imageView = view.findViewById(R.id.item_headline_image);
            holder.icon = view.findViewById(R.id.item_headline_icon);
            holder.textView = view.findViewById(R.id.item_headline_text);
            holder.relativeLayout = view.findViewById(R.id.item_headline_image_rl);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CommonUtil.loadImage(context, topLineBeanList.get(i).getHeadPic(), holder.icon);
//        CommonUtil.loadImage(context, topLineBeanList.get(i).getPic(), holder.imageView);
        CommonUtil.loadRlImage(context, topLineBeanList.get(i).getPic(), holder.relativeLayout, holder.relativeLayout.getWidth(), holder.relativeLayout.getHeight());
        holder.textView.setText(topLineBeanList.get(i).getContent());

        String string1 = topLineBeanList.get(i).getName() + ":  ";
        String string2 = topLineBeanList.get(i).getContent();
        String string3 = context.getResources().getString(R.string.moneny_string) + StringUtil.replaceString(topLineBeanList.get(i).getPrice());

        SpannableStringBuilder builder = new SpannableStringBuilder(string1); //创建SpannableStringBuilder，并添加前面文案
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#111111")), 0, string1.length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置前面的字体颜色
        builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, string1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE); //设置前面的字体大小

        if (string2.length() > 17) {
            Log.e(TAG, "getView: " + string2.length());
            builder.append(string2.substring(0, 17) + "...");
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), string1.length(), builder.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置后面的字体颜色
        } else {
            builder.append(string2); //追加后面文案
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), string1.length(), string1.length() + string2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置后面的字体颜色
        }

        builder.append(string3); //追加后面文案
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#d20102")), builder.length() - string3.length(), builder.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.textView.setText(builder);

       /* holder.textView.addSlice(new Slice.Builder(topLineBeanList.get(i).getName() + ":  ")
                .style(Typeface.BOLD)
                .textColor(Color.parseColor("#111111"))
                .build());
        holder.textView.addSlice(new Slice.Builder(topLineBeanList.get(i).getContent()
        )

                .textColor(Color.parseColor("#666666"))
                .build());
        holder.textView.addSlice(new Slice.Builder(context.getResources().getString(R.string.moneny_string) + topLineBeanList.get(i).getPrice())
                .textColor(Color.parseColor("#d20102"))
                .build());
        holder.textView.display();*/
        return view;
    }


    class ViewHolder {
        private ZQRoundOvalImageView icon;
        private ImageView imageView;
        private RelativeLayout relativeLayout;
        private TextView textView;
    }

    public void setData(List<HomeBean.DataBean.TopLineBean> topLineBeanList) {
        this.topLineBeanList = topLineBeanList;
        notifyDataSetChanged();
    }
}
