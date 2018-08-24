package com.padacn.xmgoing.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.MainHomeActivity;
import com.padacn.xmgoing.activity.SettingPasswordActivity;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.UserBean;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.widget.CountDownTimerUtils;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 46009 on 2018/4/28.
 */

public class LoginSmsFragment extends BaseFragment {

    private static final String TAG = "LoginSmsFragment";
    @BindView(R.id.sms_login_account_edit)
    MClearEditText smsLoginAccountEdit;
    @BindView(R.id.login_password_edit)
    MClearEditText loginPasswordEdit;
    @BindView(R.id.sms_go_login)
    RelativeLayout smsGoLogin;
    Unbinder unbinder;
    @BindView(R.id.login_code_text)
    TextView loginCodeText;
    Unbinder unbinder1;

    public static LoginSmsFragment newInstance() {
        Bundle args = new Bundle();
        LoginSmsFragment fragment = new LoginSmsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_login_sms;
    }


    @OnClick({R.id.sms_go_login, R.id.login_code_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_code_text:
                goCode();
                break;
            case R.id.sms_go_login:
                smsGoLogin();
                break;
        }
    }

    private void goCode() {
        if (RxDataTool.isEmpty(smsLoginAccountEdit.getText().toString())) {
            RxToast.error("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(smsLoginAccountEdit.getText().toString())) {
            RxToast.error("请输入正确的手机号");
            return;
        }
        getMessageCode();


    }

    //获取验证码
    private void getMessageCode() {
        OkGo.<String>post(ServiceApi.VerificationCode)
                .tag(this)
                .params("phoneNumber", smsLoginAccountEdit.getText().toString())
                .params("typeCode", ServiceApi.Phone_code_login)
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

    private void smsGoLogin() {
        if (RxDataTool.isEmpty(smsLoginAccountEdit.getText().toString())) {
            RxToast.error("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(smsLoginAccountEdit.getText().toString())) {
            RxToast.error("请输入正确的手机号");
            return;
        }
        if (RxDataTool.isEmpty(loginPasswordEdit.getText().toString())) {
            RxToast.error("验证码不能为空");
            return;
        }

        OkGo.<String>post(ServiceApi.USER_LOGIN)
                .tag(this)
                .params("phoneNumber", smsLoginAccountEdit.getText().toString())
                .params("confirmCode", loginPasswordEdit.getText().toString())
                //登录类型，驗證碼登錄
                .params("loginTypeCode", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.PHONE_NUMBER, smsLoginAccountEdit.getText().toString());
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.token, response.headers().get("token"));
                                OkGo.getInstance().addCommonHeaders(new HttpHeaders("token", response.headers().get("TOKEN")));
                                UserBean userBean = gson.fromJson(s, UserBean.class);
                                SharePreferencesUtil.setIntSharePreferences(getContext(), Constans.UserUid, userBean.getData().getUId());
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserName, userBean.getData().getUserName());
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserSex, String.valueOf(userBean.getData().getUserSex()));
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserIcon, String.valueOf(userBean.getData().getPicAdress()));
                                SharePreferencesUtil.setBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, true);
                                if (userBean.getData().getHavePassWord().equals("1")) {
//                                    BaseActivity.navigate(getContext(), MainHomeActivity.class);
                                    getActivity().finish();
                                } else if (userBean.getData().getHavePassWord().equals("0")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constans.PHONE_NUMBER, userBean.getData().getPhoneNumber());
                                    BaseActivity.navigate(getContext(), SettingPasswordActivity.class, bundle);
                                    getActivity().finish();
                                }
                            } else {
                                RxToast.error(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    @OnClick(R.id.login_code_text)
    public void onViewClicked() {
    }
}
