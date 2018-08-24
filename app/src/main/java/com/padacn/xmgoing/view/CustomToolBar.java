package com.padacn.xmgoing.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padacn.xmgoing.R;

/**
 * Created by 46009 on 2018/5/1.
 */

public class CustomToolBar extends LinearLayout {

    private TextView rightText;
    private ImageView headerBack;
    private RelativeLayout headerBackRl;
    private TextView customToolbarTitle;
    //    private ImageView headerBack;
    //    private TextView headerTitle, headerMenuText;
    private View headView;
    private LayoutInflater mInflater;
    private RelativeLayout custom_toolbar_bg;
    private CommonBarInter mClickListener;


    //    private LinearLayout llSearch;
    public CustomToolBar(Context context) {
        super(context);
        init(context);
        addOnClick();
    }


    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        addOnClick();
    }


    private void addOnClick() {
        rightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onRightClick(view);
            }
        });
    }

    public void setTopClickListener(CommonBarInter mListener) {
        this.mClickListener = mListener;
    }

    public void setBackClickListener(CommonBarInter mListener) {
        this.mClickListener = mListener;
    }


    public void init(Context context) {
        mInflater = LayoutInflater.from(context);
        headView = mInflater.inflate(R.layout.customtoolbar, null);
        headerBack = headView.findViewById(R.id.header_Back);
        headerBackRl = headView.findViewById(R.id.header_Back_Rl);
        rightText = headView.findViewById(R.id.custom_toolbar_right_title);
        custom_toolbar_bg = headView.findViewById(R.id.custom_toolbar_bg);
        customToolbarTitle = headView.findViewById(R.id.custom_toolbar_title);
        addView(headView);
        initView();
    }


    private void initView() {
        headerBackRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }


    public void setStyle(String title) {
        if (title != null)
            customToolbarTitle.setText(title);
    }

    public void setStyle(String title, int color) {
        setStyle(title);
        if (color != 0)
            custom_toolbar_bg.setBackgroundColor(color);
    }


    public void setBackStyle(String title, int color, OnClickListener onClickListener) {
        headerBack.setOnClickListener(onClickListener);
        setStyle(title);
        if (color != 0)
            custom_toolbar_bg.setBackgroundColor(color);
    }


    /**
     * 标题加文字菜单
     *
     * @param title
     * @param menuText
     * @param
     */
    public void setStyle(String title, String menuText, int color, OnClickListener listener) {
        setStyle(title);
        rightText.setOnClickListener(listener);
        if (title != null)
            rightText.setText(menuText);
        if (color != 0)
            custom_toolbar_bg.setBackgroundColor(color);
    }

    /**
     * 只有右边字体
     *
     * @param menuText
     * @param listener
     */
    public void setStyle(String menuText, OnClickListener listener) {
        rightText.setText(menuText);
        rightText.setOnClickListener(listener);
        headerBack.setVisibility(GONE);
    }

    /**
     * 标题加图标菜单
     *
     * @param title
     * @param menuImgResource
     * @param listener
     */
    public void setStyle(String title, int menuImgResource, OnClickListener listener) {
        setStyle(title);
    }

    public void setStyle(boolean hasSearch) {
        if (hasSearch) {
        }
    }

    /**
     * 将默认的返回按钮功能去掉
     */
    public void setStyleNoBack(String title) {
        setStyle(title);
        headerBack.setVisibility(GONE);
    }

}
