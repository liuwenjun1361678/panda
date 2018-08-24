package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
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

public class RegisterActivity extends BaseActivity {

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
    @BindView(R.id.register_button)
    RelativeLayout registerButton;
    @BindView(R.id.login_agreement)
    TextView loginAgreement;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
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
        registerBar.setStyle("手机注册", Color.parseColor("#f8d948"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.register_code_text, R.id.register_button, R.id.login_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_code_text:
                getCode();
                break;
            case R.id.register_button:

                register();
                break;
            case R.id.login_agreement:
                Bundle bundle = new Bundle();
                bundle.putString("title", "熊猫成长季协议");
                bundle.putString("url", ServiceApi.protocol_app);
                BaseActivity.navigate(RegisterActivity.this, WebDetailsActivity.class, bundle);
                break;
        }
    }

    /**
     * 註冊按鈕
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
            RxToast.error("两次密码输入不一致");
            return;
        }

        OkGo.<String>post(ServiceApi.LOGIN__REGISTER)
                .tag(this)
                .params("phoneNumber", registerPhoneEdit.getText().toString())
                .params("confirmCode", registerCodeEdit.getText().toString())
                //登录类型，用户验证码登录
                .params("loginTypeCode", "1")
                .params("passWord", MD5.hexdigest(registerPasswordEdit.getText().toString() + Constans.PASSWORD).substring(7, 15))
                .params("passWordAgin", MD5.hexdigest(registerSurePasswordEdit.getText().toString() + Constans.PASSWORD).substring(7, 15))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                RxToast.success(jsonObject.getString("msg"));
                                BaseActivity.navigate(RegisterActivity.this, LoginActivity.class);
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
                .params("typeCode", ServiceApi.Phone_register)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                RxToast.success(jsonObject.getString("msg"));
                            } else {
                                RxToast.normal(jsonObject.getString("msg"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
