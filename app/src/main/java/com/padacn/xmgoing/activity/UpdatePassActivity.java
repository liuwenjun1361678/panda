package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/1 0001.
 */

public class UpdatePassActivity extends BaseActivity {

    private static final String TAG = "UpdatePassActivity";

    @BindView(R.id.register_bar)
    CustomToolBar registerBar;
    @BindView(R.id.register_phone_edit)
    MClearEditText registerPhoneEdit;
    @BindView(R.id.register_code_edit)
    MClearEditText registerCodeEdit;
    @BindView(R.id.register_code_text)
    TextView registerCodeText;
    @BindView(R.id.register_code)
    RelativeLayout registerCode;
    @BindView(R.id.register_password_edit)
    MClearEditText registerPasswordEdit;
    @BindView(R.id.register_sure_password_edit)
    MClearEditText registerSurePasswordEdit;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_update_pass;
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
        registerBar.setStyle("找回密码", Color.parseColor("#f8d948"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.register_code_text, R.id.register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_code_text:
                getCode();
                break;
            case R.id.register_button:
                register();
                break;
        }
    }

    /**
     * 完成按鈕
     */
    private void register() {
        if (RxDataTool.isEmpty(registerPhoneEdit.getText().toString())) {
            RxToast.error("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(registerPhoneEdit.getText().toString())) {
            RxToast.error("请输入正确的手机号");
            return;
        }

        if (!StringUtil.cheakPass(registerPasswordEdit.getText().toString())) {
            RxToast.error("请设置8到16位数字和字母密码");
            return;
        }
        if (!registerPasswordEdit.getText().toString().equals(registerSurePasswordEdit.getText().toString())) {
            RxToast.error("新密码不能与旧密码一致！");
            return;
        }
        String mdString1 = MD5.hexdigest(registerPasswordEdit.getText().toString() + Constans.PASSWORD);
        String mdString2 = MD5.hexdigest(registerSurePasswordEdit.getText().toString() + Constans.PASSWORD);
        OkGo.<String>post(ServiceApi.setPassWord)
                .tag(this)
                .params("setPassWordType", "2")
                .params("phoneNumber", registerPhoneEdit.getText().toString())
                .params("confirmCode", registerCodeEdit.getText().toString())
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
                                SharePreferencesUtil.setStringSharePreferences(UpdatePassActivity.this, Constans.token, response.headers().get("TOKEN"));
                                OkGo.getInstance().addCommonHeaders(new HttpHeaders(Constans.token, response.headers().get("TOKEN")));
                                SharePreferencesUtil.setBooleanSharePreferences(UpdatePassActivity.this, Constans.isSettingPas, true);
                                RxToast.success(jsonObject.getString("msg"));
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

    /**
     * 获取验证码
     */
    private void getCode() {
        if (RxDataTool.isEmpty(registerPhoneEdit.getText().toString())) {
            RxToast.error("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(registerPhoneEdit.getText().toString())) {
            RxToast.error("请输入正确的手机号");
            return;
        }

        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                registerCodeText, 60000, 1000);
        mCountDownTimerUtils.start();
        getMessageCode();
    }


    private void getMessageCode() {
        OkGo.<String>post(ServiceApi.VerificationCode)
                .tag(this)
                .params("phoneNumber", registerPhoneEdit.getText().toString())
                .params("typeCode", ServiceApi.setting_pass)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                RxToast.success(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
