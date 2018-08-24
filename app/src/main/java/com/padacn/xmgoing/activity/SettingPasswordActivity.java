package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.view.CustomToolBar;
import com.sina.weibo.sdk.utils.MD5;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/5 0005.
 */

public class SettingPasswordActivity extends BaseActivity {
    @BindView(R.id.setting_password_bar)
    CustomToolBar settingPasswordBar;
    @BindView(R.id.setting_password_edit)
    MClearEditText settingPasswordEdit;
    @BindView(R.id.setting_true_password_edit)
    MClearEditText settingTruePasswordEdit;
    @BindView(R.id.setting_password_button)
    RelativeLayout settingPasswordButton;
    private String phoneNumber;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting_password;
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

    @Override
    protected void initView() {
        super.initView();
        settingPasswordBar.setBackStyle("设置密码", Color.parseColor("#f8d948"), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity.navigate(SettingPasswordActivity.this, MainHomeActivity.class);
            }
        });
        Bundle bundle = this.getIntent().getExtras();
        phoneNumber = bundle.getString(Constans.PHONE_NUMBER);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.setting_password_button)
    public void onViewClicked() {

        if (!StringUtil.cheakPass(settingPasswordEdit.getText().toString())) {
            RxToast.warning("请设置8到16位数字和字母密码");
            return;
        }
        if (!settingPasswordEdit.getText().toString().equals(settingTruePasswordEdit.getText().toString())) {
            RxToast.warning("两次密码输入不一致");
            return;
        }
        goSetting();

    }

    private void goSetting() {
        OkGo.<String>post(ServiceApi.loginingSetPassWord)
                .tag(this)
                .params("phoneNumber", phoneNumber)
                .params("passWord", MD5.hexdigest(settingPasswordEdit.getText().toString() + Constans.PASSWORD).substring(7, 15))
                .params("passWordAgin", MD5.hexdigest(settingTruePasswordEdit.getText().toString() + Constans.PASSWORD).substring(7, 15))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                SharePreferencesUtil.setStringSharePreferences(SettingPasswordActivity.this, Constans.token, response.headers().get("TOKEN"));
                                SharePreferencesUtil.setBooleanSharePreferences(SettingPasswordActivity.this, Constans.isAlreadyLogin, true);
                                SharePreferencesUtil.setBooleanSharePreferences(SettingPasswordActivity.this, Constans.isSettingPas, true);
                                OkGo.getInstance().addCommonHeaders(new HttpHeaders(Constans.token, response.headers().get("TOKEN")));
//                                BaseActivity.navigate(SettingPasswordActivity.this, MainHomeActivity.class);
                                finish();
                                RxToast.success(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
