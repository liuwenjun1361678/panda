package com.padacn.xmgoing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

@SuppressLint("AppCompatCustomView")
public class TopRefreshImage extends ImageView {

    private AnimationDrawable animationDrawable;

    public TopRefreshImage(Context context) {
        super(context);
        initView();
    }

    public TopRefreshImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TopRefreshImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        animationDrawable = (AnimationDrawable) this.getBackground();
        startAnim();
    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            super.setVisibility(visibility);
            if (visibility == GONE || visibility == INVISIBLE) {
                stopAnim();
            } else {
                startAnim();
            }
        }
    }

    private void startAnim() {
        if (animationDrawable == null) {
            initView();
        }
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    private void stopAnim() {
        if (animationDrawable == null) {
            initView();
        }
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }
}
