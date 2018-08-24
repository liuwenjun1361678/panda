package com.padacn.xmgoing.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.BindPhoneActivity;
import com.padacn.xmgoing.activity.MainHomeActivity;
import com.padacn.xmgoing.activity.RegisterActivity;
import com.padacn.xmgoing.activity.SettingPasswordActivity;
import com.padacn.xmgoing.activity.UpdatePassActivity;
import com.padacn.xmgoing.activity.WebDetailsActivity;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.UserBean;
import com.padacn.xmgoing.model.UserDataBean;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.StringUtil;
import com.sina.weibo.sdk.utils.MD5;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 46009 on 2018/5/9.
 */

public class LoginAccountFragment extends BaseFragment {

    private static final String TAG = "LoginAccountFragment";
    @BindView(R.id.login_account_edit)
    MClearEditText loginAccountEdit;
    @BindView(R.id.login_password_edit)
    MClearEditText loginPasswordEdit;
    @BindView(R.id.login_forget_pas)
    TextView loginForgetPas;
    @BindView(R.id.login_weixin)
    ImageView loginWeixin;
    @BindView(R.id.login_qq)
    ImageView loginQq;
    @BindView(R.id.login_weibo)
    ImageView loginWeibo;
    @BindView(R.id.phone_login_rl)
    RelativeLayout phoneLoginRl;
    @BindView(R.id.login_tab_type_nume)
    LinearLayout loginTabTypeNume;
    Unbinder unbinder;
    @BindView(R.id.login_go_login)
    RelativeLayout loginGoLogin;
    @BindView(R.id.isShow_password_rl)
    RelativeLayout isShowPasswordRl;
    @BindView(R.id.isShow_password_image)
    ImageView isShowPasswordImage;

    ImmersionBar mImmersionBar;
    //是否显示密码
    private boolean isShowPassWord = true;
    private LoadingDailog.Builder loadBuilder;
    private LoadingDailog dialog;

    private String uid;
    private String icon;
    private String name;
    private String sex;
    private String openid;
    private String userSex;

    //    openId;//第三方登录的uId
//    weixinOpenId://微信的openid
//    thirdUserName;//第三方获取的用户名
//    picAdress;//第三方登录的头像
//    userSex;//第三方性别 1为男   2为女
    public static LoginAccountFragment newInstance() {
        Bundle args = new Bundle();
        LoginAccountFragment fragment = new LoginAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                phoneLoginRl.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_login_account;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_forget_pas, R.id.login_weixin, R.id.login_qq, R.id.login_weibo,
            R.id.phone_login_rl, R.id.login_go_login, R.id.agreement_text, R.id.isShow_password_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_go_login:
                goLogin();
                break;
            case R.id.login_forget_pas:
                BaseActivity.navigate(getContext(), UpdatePassActivity.class);
                break;
            case R.id.login_weixin:
                otherLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.login_qq:
                otherLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.login_weibo:
                otherLogin(SHARE_MEDIA.SINA);
                break;
            case R.id.phone_login_rl:
                BaseActivity.navigate(getContext(), RegisterActivity.class);
                break;
            case R.id.agreement_text:

                Bundle bundle = new Bundle();
                bundle.putString("title", "熊猫成长季协议");
                bundle.putString("url", ServiceApi.protocol_app);
                BaseActivity.navigate(getContext(), WebDetailsActivity.class, bundle);
                break;

            case R.id.isShow_password_rl:
                if (isShowPassWord) {//显示密码
                    isShowPasswordImage.setBackgroundResource(R.mipmap.password_true);
                    loginPasswordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    loginPasswordEdit.setSelection(loginPasswordEdit.getText().toString().length());
                    isShowPassWord = !isShowPassWord;
                } else {
                    isShowPasswordImage.setBackgroundResource(R.mipmap.password_false);
                    loginPasswordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    loginPasswordEdit.setSelection(loginPasswordEdit.getText().toString().length());
                    isShowPassWord = !isShowPassWord;
                }
                break;
        }
    }

    private void otherLogin(SHARE_MEDIA share_media) {
        dialog = loadBuilder.create();
        dialog.show();
        UMShareAPI.get(getContext()).getPlatformInfo(getActivity(), share_media, umAuthListener);
    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            dialog.cancel();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Set<String> set = data.keySet();
            for (String string : set) {
                if (string.equals("uid")) {
                    uid = data.get(string);
                }
                if (string.equals("name")) {
                    name = data.get(string);
                }
                if (string.equals("gender")) {
                    sex = data.get(string);
                }
                if (string.equals("iconurl")) {
                    icon = data.get(string);
                }
                if (string.equals("openid")) {
                    openid = data.get(string);
                }
            }
            switch (platform) {
                case WEIXIN:
                    goOtherLogin("1");
                    break;
                case QQ:
                    goOtherLogin("2");
                    break;
                case SINA:
                    goOtherLogin("3");
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            RxToast.error("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };

    /**
     * 第三方登录
     */
    private void goOtherLogin(final String thirdLoginType) {
        if (RxDataTool.isNullString(sex)) {
            userSex = "1";
        }
        if (sex.equals("男")) {
            userSex = "1";
        } else if (sex.equals("女")) {
            userSex = "2";
        }
        OkGo.<String>post(ServiceApi.USER_LOGIN)
                .tag(this)
                .params("loginTypeCode", "3")
                .params("thirdLoginType", thirdLoginType)
                .params("openId", uid)
                .params("thirdUserName", name)
                .params("picAdress", icon)
                .params("userSex", userSex)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            dialog.cancel();
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            int havePhoneNumber = jsonObject.getInt("havePhoneNumber");
                            String msg = jsonObject.getString("msg");
                            if (result == 0) {
                                if (havePhoneNumber == 0) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("thirdLoginType", thirdLoginType);
                                    bundle.putString("openId", uid);
                                    bundle.putString("thirdUserName", name);
                                    bundle.putString("picAdress", icon);
                                    bundle.putString("userSex", userSex);
                                    BaseActivity.navigate(getContext(), BindPhoneActivity.class, bundle);
                                    getActivity().finish();
                                } else {
                                    RxToast.error(msg);
                                }
                            } else {
                                Gson gson = new Gson();
                                UserBean userBean = gson.fromJson(s, UserBean.class);
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.PHONE_NUMBER, userBean.getData().getPhoneNumber());
//                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserUid, String.valueOf(userDataBean.getData().getUId()));
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserName, userBean.getData().getUserName());
                                if (RxDataTool.isNullString(String.valueOf(userBean.getData().getUserSex()))) {
                                    SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserSex, "");
                                } else {
                                    if (userBean.getData().getUserSex().equals("1")) {
                                        SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserSex, "男");
                                    } else if (userBean.getData().getUserSex().equals("2")) {
                                        SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserSex, "女");
                                    }
                                }

                                if (!RxDataTool.isEmpty(userBean.getData().getPicAdress())) {
                                    SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserIcon, String.valueOf(userBean.getData().getPicAdress()));
                                }
//                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.PHONE_NUMBER, userDataBean.getData().get);

                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.token, response.headers().get("TOKEN"));
                                SharePreferencesUtil.setBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, true);
                                OkGo.getInstance().addCommonHeaders(new HttpHeaders(Constans.token, response.headers().get("TOKEN")));
                                if (userBean.getData().getHavePassWord().equals("0")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constans.PHONE_NUMBER, userBean.getData().getPhoneNumber());
                                    BaseActivity.navigate(getContext(), SettingPasswordActivity.class, bundle);
                                    getActivity().finish();
                                } else {
                                    getActivity().finish();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        RxToast.error("服务器出错");
                        dialog.cancel();
                    }
                });

    }


    //验证登录
    private void goLogin() {
        if (RxDataTool.isEmpty(loginAccountEdit.getText().toString())) {
            RxToast.error("手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(loginAccountEdit.getText().toString())) {
            RxToast.error("请输入正确的手机号");
            return;
        }
        goPassWordLogin();
    }

    /**
     * 用户密码登录
     */
    private void goPassWordLogin() {
        OkGo.<String>post(ServiceApi.USER_LOGIN)
                .tag(this)
                .params("phoneNumber", loginAccountEdit.getText().toString())
                .params("passWord", MD5.hexdigest(loginPasswordEdit.getText().toString() + Constans.PASSWORD).substring(7, 15))
                //登录类型，驗證碼登錄
                .params("loginTypeCode", "1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                Gson gson = new Gson();
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.token, response.headers().get("token"));
                                UserBean userBean = gson.fromJson(s, UserBean.class);
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserUid, String.valueOf(userBean.getData().getUId()));
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserName, userBean.getData().getUserName());
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserSex, String.valueOf(userBean.getData().getUserSex()));
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.UserIcon, String.valueOf(userBean.getData().getPicAdress()));
                                SharePreferencesUtil.setStringSharePreferences(getContext(), Constans.token, response.headers().get("TOKEN"));
                                OkGo.getInstance().addCommonHeaders(new HttpHeaders("token", response.headers().get("TOKEN")));
                                SharePreferencesUtil.setBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, true);
                                BaseActivity.navigate(getContext(), MainHomeActivity.class);
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
    protected void initView() {
        super.initView();
        loadBuilder = new LoadingDailog.Builder(getContext())
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);

        loginPasswordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        loginPasswordEdit.setSelection(loginPasswordEdit.getText().toString().length());
    }


}
