package com.padacn.xmgoing.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.fragment.BaseFragment;


/**
 * 创建者
 * 创建时间   2017/6/16 17:24
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class HomeThreeViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mBaseFragmentList;
    private List<String> tablist;
    private Context mContext;
    private int[] imageResId = {
            0, 0,
            R.mipmap.home_three_price_default,
    };

    public HomeThreeViewPagerAdapter(Context context, FragmentManager fm, List<BaseFragment> fragmentList, List<String> tablist) {
        super(fm);
        this.mContext = context;
        this.mBaseFragmentList = fragmentList;
        this.tablist = tablist;
    }

    @Override
    public Fragment getItem(int position) {
        return mBaseFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mBaseFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tablist.get(position % tablist.size());
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_tabtitle, null);
        TextView txt_title = (TextView) view.findViewById(R.id.title_tv);
        txt_title.setText(tablist.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.title_iv);

        View txt_title1 = (View) view.findViewById(R.id.home_three_tab_underline);
        img_title.setImageResource(imageResId[position]);
        if (position == 0) {
            txt_title1.setBackgroundColor(Color.parseColor("#f8d748"));
            img_title.setImageResource(imageResId[position]);
        } else {
            txt_title1.setBackgroundColor(Color.parseColor("#ffffff"));
            img_title.setImageResource(imageResId[position]);
        }
        return view;
    }
}
