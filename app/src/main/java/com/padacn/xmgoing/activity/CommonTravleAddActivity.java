package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.ldf.calendar.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.CommonTravleAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.util.even.EventBusUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.widget.PickDialog;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxRegTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DatePicker;

/**
 * Created by Administrator on 2018/6/17 0017.
 */

public class CommonTravleAddActivity extends BaseActivity {


    @BindView(R.id.common_travel_people_add_topbar)
    CustomToolBar commonTravelPeopleAddTopbar;
    @BindView(R.id.personal_name)
    MClearEditText personalName;
    @BindView(R.id.common_travle_sex)
    TextView commonTravleSex;
    @BindView(R.id.common_travle_sex_rl)
    RelativeLayout commonTravleSexRl;
    @BindView(R.id.common_travle_brith)
    TextView commonTravleBrith;
    @BindView(R.id.common_travle_brith_rl)
    RelativeLayout commonTravleBrithRl;
    @BindView(R.id.common_travle_phone_sex)
    MClearEditText commonTravlePhoneSex;
    @BindView(R.id.common_travle_phone_rl)
    RelativeLayout commonTravlePhoneRl;
    @BindView(R.id.common_travle_idCard)
    MClearEditText commonTravleIdCard;
    @BindView(R.id.common_travle_idCard_rl)
    RelativeLayout commonTravleIdCardRl;
    private CommonTravleAdapter commonTravleAdapter;

    private String currSex = "1";
    private String currDate = "";

    private LoadingDailog.Builder loadBuilder;
    private LoadingDailog dialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_common_travel_people_add;
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


        commonTravelPeopleAddTopbar.setStyle("常用出行人", "保存", Color.parseColor("#f8d948"), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!RxRegTool.isIDCard18(commonTravleIdCard.getText().toString())) {
                    RxToast.info("身份证号码格式不正确！");
                    return;
                }

                if (RxDataTool.isEmpty(commonTravleIdCard.getText().toString())) {
                    RxToast.info("身份证号码不能为空！");
                    return;
                }

                if (!RxRegTool.isChz(personalName.getText().toString())) {
                    RxToast.info("姓名格式不正确！");
                    return;
                }
                if (RxDataTool.isEmpty(personalName.getText().toString())) {
                    RxToast.info("姓名不能为空！");
                    return;
                }
                if (RxDataTool.isEmpty(commonTravlePhoneSex.getText().toString())) {
                    RxToast.info("手机号不能为空！");
                    return;
                }
                if (!StringUtil.isPhone(commonTravlePhoneSex.getText().toString())) {
                    RxToast.normal("请输入正确的手机号");
                    return;
                }
                saveData();
            }
        });

    }


    //性别选择
    private void ShowSexPcicker() {
        final PickDialog pickDialog = new PickDialog(this, R.style.AlertDialogStyle);
        pickDialog.show();
        pickDialog.setTextName("男", "女");
        pickDialog.selectMan(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDialog.cancel();
                commonTravleSex.setText("男");
                currSex = "1";

            }
        });
        pickDialog.selectWoMan(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDialog.cancel();
                currSex = "2";
                commonTravleSex.setText("女");
            }
        });
    }


    //选择出生日期
    private void onYearMonthDayPicker() {

        final DatePicker picker = new DatePicker(this);
        picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(Constans.startYear, Constans.startMonth, Constans.startDay);
        picker.setRangeEnd(Utils.getYear(), Utils.getMonth(), Utils.getDay());
//        picker.setSelectedItem(Utils.getYear(), Utils.getMonth(), Utils.getDay());
        picker.setWeightEnable(true);
        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                commonTravleBrith.setText(year + "-" + month + "-" + day);
                currDate = year + "-" + month + "-" + day;

            }
        });
        picker.show();
    }

    private void saveData() {
        createDialog();
        OkGo.<String>post(ServiceApi.addContacts)
                .tag(this)
                .params("conName", personalName.getText().toString())
                .params("conPhoneNumber", commonTravlePhoneSex.getText().toString())
                .params("conIdcard", commonTravleIdCard.getText().toString())
                .params("conSex", currSex)
                .params("conBirthday", currDate)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dialog.cancel();
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                RxToast.success(msg);
                                EventBusUtil.sendEvent(new MessageEvent(EvenCode.TravlePeopleAdd));
                                finish();
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
                        dialog.cancel();
                    }
                });


    }

    //不可点击
    private void createDialog() {
        loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.common_travle_sex_rl, R.id.common_travle_brith_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_travle_sex_rl:
                ShowSexPcicker();
                break;
            case R.id.common_travle_brith_rl:
                onYearMonthDayPicker();
                break;
        }
    }
}
