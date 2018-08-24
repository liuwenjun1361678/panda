package com.padacn.xmgoing.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.callback.OnClickSureListenter;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.common.GlideCacheUtil;
import com.padacn.xmgoing.util.even.EventBusUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.dialog.ComonSureDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 46009 on 2018/5/9.
 */

public class MySettingActivity extends BaseActivity {
    @BindView(R.id.mine_setting_bar)
    CustomToolBar mineSettingBar;
    @BindView(R.id.mine_setting_phone)
    TextView mineSettingPhone;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.mine_setting_cache)
    TextView mineSettingCache;
    @BindView(R.id.imageView_cache)
    ImageView imageViewCache;
    @BindView(R.id.update_phone_rl)
    RelativeLayout updatePhoneRl;
    @BindView(R.id.update_password_rl)
    RelativeLayout updatePasswordRl;
    @BindView(R.id.common_contacts_rl)
    RelativeLayout commonContactsRl;
    @BindView(R.id.clean_rl)
    RelativeLayout cleanRl;
    @BindView(R.id.abount_us_rl)
    RelativeLayout abountUsRl;
    @BindView(R.id.cancel_login_rl)
    RelativeLayout cancelLoginRl;
    @BindView(R.id.setting_password_text)
    TextView settingPasswordText;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_setting;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .navigationBarColor(R.color.home_top_text_color)
                .keyboardEnable(true)
                .init();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mineSettingCache.setText(GlideCacheUtil.getInstance().getCacheSize(MySettingActivity.this));
            }
        }
    };

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(MessageEvent event) {
        if (event.getCode() == EvenCode.ClearCache) {
            mineSettingCache.setText(GlideCacheUtil.getInstance().getCacheSize(MySettingActivity.this));
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mineSettingBar.setStyle("设置", Color.parseColor("#f8d948"));
//        String s1 = RxFileTool.getFileSize(RxFileTool.getCecheFolder(this));
//        String s2 = RxFileTool.getFileSize(RxFileTool.getDiskCacheDir(this));
        mineSettingCache.setText(GlideCacheUtil.getInstance().getCacheSize(MySettingActivity.this));
        mineSettingPhone.setText(SharePreferencesUtil.getStringSharePreferences(MySettingActivity.this, Constans.PHONE_NUMBER, ""));
        if (SharePreferencesUtil.getBooleanSharePreferences(MySettingActivity.this, Constans.isSettingPas, false)) {
            settingPasswordText.setText("修改登录密码");
        } else {
            settingPasswordText.setText("设置登录密码");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.initView();
    }

    @OnClick({R.id.update_phone_rl, R.id.update_password_rl, R.id.common_contacts_rl, R.id.clean_rl, R.id.abount_us_rl, R.id.cancel_login_rl})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.update_phone_rl:
                BaseActivity.navigate(MySettingActivity.this, UpdateBindPhoneActivity.class);
                break;
            case R.id.update_password_rl:
                if (SharePreferencesUtil.getBooleanSharePreferences(MySettingActivity.this, Constans.isSettingPas, false)) {
                    BaseActivity.navigate(MySettingActivity.this, UpdateNewPassActivity.class);
                } else {
                    bundle.putString(Constans.PHONE_NUMBER, SharePreferencesUtil.getStringSharePreferences(MySettingActivity.this, Constans.PHONE_NUMBER, ""));
                    BaseActivity.navigate(MySettingActivity.this, SettingPasswordActivity.class, bundle);
                }


                break;
            case R.id.common_contacts_rl:
                bundle.putBoolean("isAddTravle", false);
                BaseActivity.navigate(MySettingActivity.this, CommonTravelActivity.class, bundle);
                break;
            case R.id.clean_rl:
                final ComonSureDialog comonSureDialog = new ComonSureDialog(MySettingActivity.this, "是否清除缓存？");
                comonSureDialog.onCreateView();
                comonSureDialog.setUiBeforShow();
                comonSureDialog.setCanceledOnTouchOutside(false);
                comonSureDialog.show();
                comonSureDialog.setOnClickSureListenter(new OnClickSureListenter() {
                    @Override
                    public void onSureClick() {
                        GlideCacheUtil.getInstance().clearImageAllCache(MySettingActivity.this);

//                        handler.sendEmptyMessage(1);
                        comonSureDialog.dismiss();
                    }
                });

                break;
            case R.id.abount_us_rl:
                BaseActivity.navigate(MySettingActivity.this, AboutUsActivity.class);
                break;

            case R.id.cancel_login_rl:
                if (UMShareAPI.get(MySettingActivity.this).isAuthorize(this, SHARE_MEDIA.WEIXIN)) {
                    UMShareAPI.get(MySettingActivity.this).deleteOauth(this, SHARE_MEDIA.WEIXIN, authListener);
                }
                if (UMShareAPI.get(MySettingActivity.this).isAuthorize(this, SHARE_MEDIA.SINA)) {
                    UMShareAPI.get(MySettingActivity.this).deleteOauth(this, SHARE_MEDIA.SINA, authListener);
                }
                SharePreferencesUtil.setBooleanSharePreferences(MySettingActivity.this, Constans.isAlreadyLogin, false);
                finish();
                break;
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };
}
