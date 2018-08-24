package com.padacn.xmgoing.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.padacn.xmgoing.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by Administrator on 2018/5/29 0029.
 */

public class RefreshView extends LinearLayout implements RefreshHeader {
    private static final String TAG = "RefreshView";
    private AnimationDrawable animationDrawable;
    private LayoutInflater mInflater;
    private View headView;
    private ImageView imageView;
    private TextView textView;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        imageView = new ImageView(context);
        textView = new TextView(context);
        imageView.setImageResource(R.drawable.common_top_refresh);
        addView(imageView, DensityUtil.dp2px(60), DensityUtil.dp2px(60));
        addView(new Space(context), DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(textView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        setMinimumHeight(DensityUtil.dp2px(60));
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        animationDrawable.start();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        animationDrawable.stop();
        if (success) {
            textView.setText("刷新完成");
        } else {
            textView.setText("刷新失败");
        }
        return 500;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

        switch (newState) {
            case PullDownToRefresh:
                textView.setText("下拉刷新");
                animationDrawable.stop();
                Log.e(TAG, "onStateChanged: +PullDownToRefresh");
                break;
            case Refreshing:
                textView.setText("正在刷新");
                Log.e(TAG, "onStateChanged: +Refreshing");
                animationDrawable.start();
                break;
            case ReleaseToRefresh:
                Log.e(TAG, "onStateChanged: +ReleaseToRefresh");
                textView.setText("释放刷新");
                animationDrawable.stop();
                break;
        }
    }

    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onRefreshReleased(RefreshLayout layout, int headerHeight, int extendHeight) {

    }
}
