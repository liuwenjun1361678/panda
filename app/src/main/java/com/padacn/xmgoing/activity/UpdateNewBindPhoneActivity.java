package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.UserBean;
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
 * Created by Administrator on 2018/6/6 0006.
 */

public class UpdateNewBindPhoneActivity extends BaseActivity {
    private static final String TAG = "BindPhoneActivity";
    @BindView(R.id.bind_password_bar)
    CustomToolBar bindPasswordBar;
    @BindView(R.id.bind_password_edit)
    MClearEditText bindPasswordEdit;
    @BindView(R.id.bind_true_password_edit)
    MClearEditText bindTruePasswordEdit;
    @BindView(R.id.login_code_text)
    TextView loginCodeText;
    @BindView(R.id.bind_password_button)
    RelativeLayout bindPasswordButton;


    private String oldPhoneNumber;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_update_bind_phone;
    }

    @Override
    protected void initView() {
        super.initView();

        bindPasswordBar.setStyle("绑定手机号", Color.parseColor("#f8d948"));
        Bundle bundle = this.getIntent().getExtras();
        oldPhoneNumber = bundle.getString("oldPhoneNumber");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bind_password_button, R.id.login_code_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_password_button:
                bindPhone();
                break;
            case R.id.login_code_text:
                goCode();
//                BaseActivity.navigate(BindPhoneActivity.this, UpdatePassActivity.class);
                break;
        }
    }

    private void bindPhone() {
        if (RxDataTool.isEmpty(bindPasswordEdit.getText().toString())) {
            RxToast.error("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(bindPasswordEdit.getText().toString())) {
            RxToast.error("请输入正确的手机号");
            return;
        }
        if (RxDataTool.isEmpty(bindTruePasswordEdit.getText().toString())) {
            RxToast.error("验证码不能为空");
            return;
        }
        OkGo.<String>post(ServiceApi.revisePhoneNumber)
                .tag(this)
                .params("type", "2")
                .params("newPhoneNumber", bindPasswordEdit.getText().toString())
                .params("confirmCode", bindTruePasswordEdit.getText().toString())
                .params("oldPhoneNumber", oldPhoneNumber)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                SharePreferencesUtil.setStringSharePreferences(UpdateNewBindPhoneActivity.this, Constans.PHONE_NUMBER, bindPasswordEdit.getText().toString());
                                SharePreferencesUtil.setStringSharePreferences(UpdateNewBindPhoneActivity.this, Constans.token, response.headers().get("TOKEN"));
                                OkGo.getInstance().addCommonHeaders(new HttpHeaders(Constans.token, response.headers().get("TOKEN")));
                                RxToast.success(msg);
                                finish();
                            } else {
                                RxToast.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


    }

    private void goCode() {
        if (RxDataTool.isEmpty(bindPasswordEdit.getText().toString())) {
            RxToast.error("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(bindPasswordEdit.getText().toString())) {
            RxToast.error("请输入正确的手机号");
            return;
        }
        getMessageCode();

    }

    //获取验证码
    private void getMessageCode() {
        OkGo.<String>post(ServiceApi.VerificationCode)
                .tag(this)
                .params("phoneNumber", bindPasswordEdit.getText().toString())
                .params("typeCode", ServiceApi.new_phone)
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
                                RxToast.error(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                loginCodeText, 60000, 1000);
        mCountDownTimerUtils.start();

    }

}
