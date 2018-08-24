package com.padacn.xmgoing.activity;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.callback.OnClickSureListenter;
import com.padacn.xmgoing.fragment.ExFragment;
import com.padacn.xmgoing.fragment.HomeFragment;
import com.padacn.xmgoing.fragment.MyFragment;
import com.padacn.xmgoing.fragment.ShopCarFragment1;
import com.padacn.xmgoing.util.AppManager;
import com.padacn.xmgoing.util.GlideUtil;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.even.EvenMainUtil;
import com.padacn.xmgoing.view.dialog.ComonSureDialog;
import com.padacn.xmgoing.widget.CustomDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by 46009 on 2018/4/26.
 */

public class MainHomeActivity extends SupportActivity {

    private static final String TAG = "MainHomeActivity";
    @BindView(R.id.ll_home_image)
    ImageView llHomeImage;
    @BindView(R.id.ll_home_text)
    TextView llHomeText;
    @BindView(R.id.ll_category_image)
    ImageView llCategoryImage;
    @BindView(R.id.ll_category_text)
    TextView llCategoryText;
    @BindView(R.id.ll_service_iamge)
    ImageView llServiceIamge;
    @BindView(R.id.ll_service_text)
    TextView llServiceText;
    @BindView(R.id.ll_mine_image)
    ImageView llMineImage;
    @BindView(R.id.ll_mine_text)
    TextView llMineText;
    private boolean isFirst = true;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_category)
    LinearLayout llCategory;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;
    @BindView(R.id.ll)
    LinearLayout ll;


    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    private boolean flag_exit = false;

    private SupportFragment[] mFragments = new SupportFragment[4];
    private ImmersionBar mImmersionBar;

    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        EventBus.getDefault().register(this);
        AppManager.getInstance().addActivity(this);
        //是否需要重新登錄。系统的弹窗会有权限问题，暂时到主页去dialog
        if (getIntent().getBooleanExtra(Constans.isAgainLogin, false)) {
            showLoginDialog();
        }
//        ScreenAdapterTools.getInstance().loadView((ViewGroup) getWindow().getDecorView());
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
        //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景色未被隐藏的问题
        if (OSUtils.isEMUI3_1()) {
            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    (NAVIGATIONBAR_IS_MIN), true, mNavigationStatusObserver);
            //第二种,禁止对导航栏的设置
            //mImmersionBar.navigationBarEnable(false).init();
        }
        SupportFragment homeFiveFragment = findFragment(HomeFragment.class);
        tabSelected(llHome);
        llHomeImage.setImageResource(R.mipmap.home_selected);
        llHomeText.setTextColor(getResources().getColor(R.color.common_text_color_1));
        if (homeFiveFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = ExFragment.newInstance();
            mFragments[THIRD] = ShopCarFragment1.newInstance();
            mFragments[FOURTH] = MyFragment.newInstance();
//            loadRootFragment(R.id.content, mFragments[FIRST]);
            loadMultipleRootFragment(R.id.content, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = homeFiveFragment;
            mFragments[SECOND] = findFragment(ExFragment.class);
            mFragments[THIRD] = findFragment(ShopCarFragment1.class);
            mFragments[FOURTH] = findFragment(MyFragment.class);
        }
    }

    private void showLoginDialog() {
        final ComonSureDialog comonSureDialog = new ComonSureDialog(MainHomeActivity.this);
        comonSureDialog.onCreateView();
        comonSureDialog.setUiBeforShow();
        comonSureDialog.setCanceledOnTouchOutside(false);
        comonSureDialog.show();
        comonSureDialog.setOnClickSureListenter(new OnClickSureListenter() {
            @Override
            public void onSureClick() {
                comonSureDialog.dismiss();
                BaseActivity.navigate(MainHomeActivity.this, LoginActivity.class);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlideUtil.clearImageDiskCache(this);
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        EventBus.getDefault().unregister(this);
    }

    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                mImmersionBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                mImmersionBar.navigationBarColor(android.R.color.white) //隐藏前导航栏的颜色
                        .fullScreen(false)
                        .init();
            }
        }
    };

    @OnClick({R.id.ll_home, R.id.ll_category, R.id.ll_service, R.id.ll_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                showHideFragment(mFragments[FIRST]);
                tabSelected(llHome);
                llHomeImage.setImageResource(R.mipmap.home_selected);
                llHomeText.setTextColor(getResources().getColor(R.color.common_text_color_1));
                break;
            case R.id.ll_category:
                showHideFragment(mFragments[SECOND]);
                tabSelected(llCategory);
                llCategoryImage.setImageResource(R.mipmap.ex_selected);
                llCategoryText.setTextColor(getResources().getColor(R.color.common_text_color_1));
                break;
            case R.id.ll_service:
                if (SharePreferencesUtil.getBooleanSharePreferences(getBaseContext(), Constans.isAlreadyLogin, false)) {
                    showHideFragment(mFragments[THIRD]);
                    tabSelected(llService);
                    llServiceIamge.setImageResource(R.mipmap.shop_selected);
                    llServiceText.setTextColor(getResources().getColor(R.color.common_text_color_1));
                    EventBus.getDefault().postSticky(new EvenMainUtil(THIRD));
                } else {
                    BaseActivity.navigate(MainHomeActivity.this, LoginActivity.class);
                }

                break;
            case R.id.ll_mine:
                showHideFragment(mFragments[FOURTH]);
                tabSelected(llMine);
                llMineImage.setImageResource(R.mipmap.mine_selected);
                llMineText.setTextColor(getResources().getColor(R.color.common_text_color_1));
                break;
        }
    }

    //tab被选中
    private void tabSelected(LinearLayout linearLayout) {
        llHomeImage.setImageResource(R.mipmap.home_selected_false);
        llHomeText.setTextColor(getResources().getColor(R.color.common_text_color_6));
        llCategoryImage.setImageResource(R.mipmap.ex_selected_false);
        llCategoryText.setTextColor(getResources().getColor(R.color.common_text_color_6));
        llServiceIamge.setImageResource(R.mipmap.shop_selected_false);
        llServiceText.setTextColor(getResources().getColor(R.color.common_text_color_6));
        llMineImage.setImageResource(R.mipmap.mine_selected_false);
        llMineText.setTextColor(getResources().getColor(R.color.common_text_color_6));

        llHome.setSelected(false);
        llCategory.setSelected(false);
        llService.setSelected(false);
        llMine.setSelected(false);
        linearLayout.setSelected(true);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick();
        }
        return false;
    }

    private void exitByDoubleClick() {

        CustomDialog customDialog = new CustomDialog(MainHomeActivity.this, "是否退出程序") {
            @Override
            public void ok() {
                AppManager.getInstance().AppExit();
            }

            @Override
            public void cancel() {
                super.cancel();
            }
        };
        customDialog.show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EvenMainUtil event) {

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
