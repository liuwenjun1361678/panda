package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.AboutBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.view.CustomToolBar;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.about_us_comment_bar)
    CustomToolBar aboutUsCommentBar;
    @BindView(R.id.version_code)
    TextView versionCode;
    @BindView(R.id.about_image)
    ImageView aboutImage;
    @BindView(R.id.adbout_text)
    TextView adboutText;
    @BindView(R.id.about_marking)
    TextView aboutMarking;

    private String about;
    private String logoAdress;
    private String marking;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_about_us;
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
        aboutUsCommentBar.setStyle("关于我们", Color.parseColor("#f8d948"));
        versionCode.setText(RxDeviceTool.getAppVersionName(AboutUsActivity.this));
        getData();
    }

    private void getData() {

        OkGo.<String>post(ServiceApi.aboutMe)
                .tag(this)
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
                                AboutBean aboutBean = gson.fromJson(s, AboutBean.class);
                                about = aboutBean.getData().getAbout();
                                logoAdress = aboutBean.getData().getLogoAdress();
                                marking = aboutBean.getData().getMarking();
                                CommonUtil.loadImage(AboutUsActivity.this, logoAdress, aboutImage);
                                adboutText.setText(about);
                                aboutMarking.setText(marking);
                            } else {
                                RxToast.error(msg);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
