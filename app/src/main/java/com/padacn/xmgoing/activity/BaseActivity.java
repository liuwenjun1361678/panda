package com.padacn.xmgoing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.SlideConfig;
import com.oubowu.slideback.callbak.OnSlideListener;

import com.padacn.xmgoing.APPAplication;
import com.padacn.xmgoing.util.AppManager;
import com.padacn.xmgoing.util.even.EventBusUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by 46009 on 2018/4/25.
 */

public abstract class BaseActivity extends SupportActivity {

    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;
    private boolean hasIntentionToSlideBack; // 具有触发滑动返回的意图

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        AppManager.getInstance().addActivity(this);
        //ScreenAdapterTools.getInstance().loadView((ViewGroup) getWindow().getDecorView());
        setContentView(setLayoutId());
        //绑定控件
        unbinder = ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
        //view与数据绑定
        initView();
        //初始化数据
        initData();
        //设置监听
        setListener();
//        initializeSlideBack();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
        unbinder.unbind();
        this.imm = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁

        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    protected abstract int setLayoutId();

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.init();
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void setListener() {
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }


    public static void navigate(Context activity, Class toActivity) {
        activity.startActivity(new Intent(activity, toActivity));
    }

    public static void navigate(Context activity, Class toActivity, Bundle bundle) {
        Intent intent = new Intent(activity, toActivity);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    /**
     * 初始化滑动返回
     */
    private void initializeSlideBack() {
        SlideBackHelper.attach(
                this, // 当前Activity
                APPAplication.getActivityHelper(), // Activity栈管理工具
                new SlideConfig.Builder() // 参数的配置
                        .rotateScreen(false) // 屏幕是否旋转
                        .edgeOnly(true) // 是否侧滑
                        .lock(false) // 是否禁止侧滑
                        .edgePercent(0.2f) // 边缘滑动的响应阈值，0~1，对应屏幕宽度*percent
                        .slideOutPercent(0.5f) // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                        .create(),
                new OnSlideListener() { // 滑动的监听
                    @Override
                    public void onSlide(@FloatRange(from = 0.0, to = 1.0) float percent) {
                        if (!hasIntentionToSlideBack && percent > 0.0) {
                            hasIntentionToSlideBack = true;
                            onSlideBackIntention();
                        }
                    }

                    @Override
                    public void onOpen() {
                        hasIntentionToSlideBack = false;
                    }

                    @Override
                    public void onClose() {

                    }
                }
        );
    }

    /**
     * 当有滑动返回的意图时
     */
    protected void onSlideBackIntention() {
    }


    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(MessageEvent event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(MessageEvent event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(MessageEvent event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(MessageEvent event) {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
