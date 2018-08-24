package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
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

public class BindPhoneActivity extends BaseActivity {
    private static final String TAG = "BindPhoneActivity";
    @BindView(R.id.bind_password_bar)
    CustomToolBar bindPasswordBar;
    @BindView(R.id.bind_password_edit)
    MClearEditText bindPasswordEdit;
    @BindView(R.id.bind_true_password_edit)
    MClearEditText bindTruePasswordEdit;
    @BindView(R.id.login_code_text)
    TextView loginCodeText;
    @BindView(R.id.login_code)
    RelativeLayout loginCode;
    @BindView(R.id.bind_password_button)
    RelativeLayout bindPasswordButton;


    private String uid;
    private String icon;
    private String name;
    private String sex;
    private String openid;
    private String userSex;
    private String thirdLoginType;

    private LoadingDailog.Builder loadBuilder;
    private LoadingDailog dialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initView() {
        super.initView();
        bindPasswordBar.setStyle("绑定手机号", Color.parseColor("#f8d948"));

        loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        Bundle bundle = this.getIntent().getExtras();
        uid = bundle.getString("openId");
        thirdLoginType = bundle.getString("thirdLoginType");
        name = bundle.getString("thirdUserName");
        icon = bundle.getString("picAdress");
        userSex = bundle.getString("userSex");
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
            RxToast.info("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(bindPasswordEdit.getText().toString())) {
            RxToast.warning("请输入正确的手机号");
            return;
        }
        if (RxDataTool.isEmpty(bindTruePasswordEdit.getText().toString())) {
            RxToast.warning("验证码不能为空");
            return;
        }
        //加载
        dialog = loadBuilder.create();
        dialog.show();

        OkGo.<String>post(ServiceApi.USER_LOGIN)
                .tag(this)
                .params("loginTypeCode", "4")
                .params("thirdLoginType", thirdLoginType)
                .params("openId", uid)
                .params("thirdUserName", name)
                .params("picAdress", icon)
                .params("userSex", userSex)

                .params("phoneNumber", bindPasswordEdit.getText().toString())
                .params("confirmCode", bindTruePasswordEdit.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        dialog.cancel();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                UserBean userBean = gson.fromJson(s, UserBean.class);
                                SharePreferencesUtil.setStringSharePreferences(BindPhoneActivity.this, Constans.UserName, userBean.getData().getUserName());
                                SharePreferencesUtil.setStringSharePreferences(BindPhoneActivity.this, Constans.UserSex, String.valueOf(userBean.getData().getUserSex()));
                                SharePreferencesUtil.setStringSharePreferences(BindPhoneActivity.this, Constans.UserIcon, String.valueOf(userBean.getData().getPicAdress()));
                                SharePreferencesUtil.setStringSharePreferences(BindPhoneActivity.this, Constans.token, response.headers().get("TOKEN"));
                                OkGo.getInstance().addCommonHeaders(new HttpHeaders("token", response.headers().get("TOKEN")));
                                SharePreferencesUtil.setBooleanSharePreferences(BindPhoneActivity.this, Constans.isAlreadyLogin, true);
                                SharePreferencesUtil.setStringSharePreferences(BindPhoneActivity.this, Constans.PHONE_NUMBER, userBean.getData().getPhoneNumber());
                                if (userBean.getData().getHavePassWord().equals("1")) {
                                    RxToast.error(jsonObject.getString("msg"));
//                                    BaseActivity.navigate(BindPhoneActivity.this, MainHomeActivity.class);
                                    finish();
                                } else if (userBean.getData().getHavePassWord().equals("0")) {
                                    SharePreferencesUtil.setBooleanSharePreferences(BindPhoneActivity.this, Constans.isSettingPas, false);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constans.PHONE_NUMBER, userBean.getData().getPhoneNumber());
                                    BaseActivity.navigate(BindPhoneActivity.this, SettingPasswordActivity.class, bundle);
                                    finish();
                                }
                            } else {
                                RxToast.error(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });


    }

    private void goCode() {
        if (RxDataTool.isEmpty(bindPasswordEdit.getText().toString())) {
            RxToast.info("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(bindPasswordEdit.getText().toString())) {
            RxToast.warning("请输入正确的手机号");
            return;
        }
        getMessageCode();

    }

    //获取验证码
    private void getMessageCode() {
        OkGo.<String>post(ServiceApi.VerificationCode)
                .tag(this)
                .params("phoneNumber", bindPasswordEdit.getText().toString())
                .params("typeCode", ServiceApi.other_login)
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
                loginCodeText, 30000, 1000);
        mCountDownTimerUtils.start();

    }

}
