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
import com.padacn.xmgoing.widget.CountDownTimerUtils;
import com.sina.weibo.sdk.utils.MD5;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/1 0001.
 */

public class UpdateNewPassActivity extends BaseActivity {

    private static final String TAG = "UpdatePassActivity";
    @BindView(R.id.setting_password_bar)
    CustomToolBar settingPasswordBar;
    @BindView(R.id.setting_curr_password_edit)
    MClearEditText settingCurrPasswordEdit;
    @BindView(R.id.setting_password_edit)
    MClearEditText settingPasswordEdit;
    @BindView(R.id.setting_true_password_edit)
    MClearEditText settingTruePasswordEdit;
    @BindView(R.id.setting_password_button)
    RelativeLayout settingPasswordButton;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting_new_password;
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
        settingPasswordBar.setStyle("修改密码", Color.parseColor("#f8d948"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 完成按鈕
     */
    private void register() {
        if (RxDataTool.isEmpty(settingCurrPasswordEdit.getText().toString())) {
            RxToast.error("当前密码不能为空！");
            return;
        }

        if (!StringUtil.cheakPass(settingPasswordEdit.getText().toString())) {
            RxToast.error("请设置8到16位数字和字母密码");
            return;
        }
        if (!settingPasswordEdit.getText().toString().equals(settingTruePasswordEdit.getText().toString())) {
            RxToast.error("新密码输入不一致！");
            return;
        }
        String oldPass = MD5.hexdigest(settingCurrPasswordEdit.getText().toString() + Constans.PASSWORD);
        String mdString1 = MD5.hexdigest(settingPasswordEdit.getText().toString() + Constans.PASSWORD);
        String mdString2 = MD5.hexdigest(settingTruePasswordEdit.getText().toString() + Constans.PASSWORD);
        OkGo.<String>post(ServiceApi.revisePassWord)
                .tag(this)
                .params("oldPassWord", oldPass.substring(7, 15))
                .params("passWord", mdString1.substring(7, 15))
                .params("passWordAgin", mdString2.substring(7, 15))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                SharePreferencesUtil.setStringSharePreferences(UpdateNewPassActivity.this, Constans.token, response.headers().get("TOKEN"));
                                OkGo.getInstance().addCommonHeaders(new HttpHeaders(Constans.token, response.headers().get("TOKEN")));
                                RxToast.success("新密碼設置成功");
                                finish();
                            } else {
                                RxToast.error(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @OnClick(R.id.setting_password_button)
    public void onViewClicked() {
        register();
    }
}
