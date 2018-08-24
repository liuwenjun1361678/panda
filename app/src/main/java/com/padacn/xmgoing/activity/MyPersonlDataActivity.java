package com.padacn.xmgoing.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ldf.calendar.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.ChildrenAdapter;
import com.padacn.xmgoing.adapter.HomeSecondBottomAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.callback.OnClickSureListenter;
import com.padacn.xmgoing.callback.OnGetChildrenDataLinster;
import com.padacn.xmgoing.model.Childrenbean;
import com.padacn.xmgoing.model.UserDataBean;
import com.padacn.xmgoing.model.UserHeadBean;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.common.GlideCacheUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.ZQRoundOvalImageView;
import com.padacn.xmgoing.view.dialog.ComonSureDialog;
import com.padacn.xmgoing.widget.PickDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.RxToast;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.ysr.citypicker.CityPickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DatePicker;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;
import static com.vondear.rxtools.view.RxToast.showToast;

/**
 * Created by Administrator on 2018/6/16 0016.
 */

public class MyPersonlDataActivity extends BaseActivity {
    @BindView(R.id.item_chlid_image)
    ZQRoundOvalImageView itemChlidImage;
    @BindView(R.id.personal_name)
    MClearEditText personalName;
    @BindView(R.id.personal_sex)
    TextView personalSex;
    @BindView(R.id.personal_brith)
    TextView personalBrith;
    @BindView(R.id.personal_city)
    TextView personalCity;
    @BindView(R.id.children_num)
    TextView childrenNum;
    @BindView(R.id.mine_chilren_add_text)
    TextView mineChilrenAddText;
    @BindView(R.id.mine_data_recyclerview)
    RecyclerView mineDataRecyclerview;
    @BindView(R.id.personal_toolbar)
    CustomToolBar personalToolbar;
    //個人中心定位城市页面的回调
    private static final int PICK_CITY = 234;
    private String currCity = "";
    private int currChildrenSize = 0;
    private ChildrenAdapter childrenAdapter;
    private Uri resultUri;


    private List<UserDataBean.ChildBean> childBeanList;
    //    private List<Childrenbean> childrenbeanList;
    private String currIconUrl = "";
    private String currUserName = "";
    private String currUserSex = "";
    private String currUserBrith = "";
    private String currUserCity = "";

    private boolean isUserPermiss = false;
    LoadingDailog.Builder loadBuilder;
    LoadingDailog dialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_data;
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
/*
    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(MessageEvent event) {
        if(event.getCode()== EvenCode.ChildrenCode){
//            HomeThreeSelectPresenter.getSingleTon().getContactList()
        }
    }
*/

    @Override
    protected void initView() {
        super.initView();
        childBeanList = new ArrayList<>();
        getPersonData();
        personalToolbar.setStyle("个人资料", "保存", Color.parseColor("#f8d948"), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SavaUserData();
                createDialog();
                if (resultUri != null) {
                    SaveUserHead();
                } else {
                    SavaUserData(currIconUrl);
                }
                dialog.cancel();
            }
        });
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) isUserPermiss = true;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }

    //保存用戶頭像
    private void SaveUserHead() {
        OkGo.<String>post(ServiceApi.uploadPicToAli)
                .tag(this)
                .params("userHead", new File(RxPhotoTool.getImageAbsolutePath(this, resultUri)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            String data = jsonObject.getString("data");
                            if (result == 1) {
                                SavaUserData(data);
                            } else {
                                RxToast.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


    }

    //保存用戶信息
    private void SavaUserData(final String pics) {
        for (int i = 0; i < childBeanList.size(); i++) {
            childrenAdapter.setChildrenDataLinster(new OnGetChildrenDataLinster() {
                @Override
                public void onItemData(int position, String childName) {
                    childBeanList.get(position).setChildName(childName);
                }
            });
        }

        JSONArray jsonArray = new JSONArray();
        if (childBeanList.size() > 0) {
            for (int i = 0; i < childBeanList.size(); i++) {
                if (RxDataTool.isEmpty(childBeanList.get(i).getChildName())) {
                    RxToast.error("请完善孩子信息");
                    return;
                }
                if (RxDataTool.isEmpty(childBeanList.get(i).getChildBirthday())) {
                    RxToast.error("请完善孩子信息");
                    return;
                }
                if (RxDataTool.isEmpty(childBeanList.get(i).getChildSex())) {
                    RxToast.error("请完善孩子信息");
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("childName", childBeanList.get(i).getChildName());
                    jsonObject.put("childBirthday", childBeanList.get(i).getChildBirthday());
                    jsonObject.put("childSex", childBeanList.get(i).getChildSex());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonArray.put(jsonObject);
        }

        currUserName = personalName.getText().toString();

        OkGo.<String>post(ServiceApi.SaveUserInfo)
                .tag(this)
                .params("userName", currUserName)
                .params("userSex", currUserSex)
                .params("birthday", currUserBrith)
                .params("city", currCity)
                .params("childs", jsonArray.toString() + "")
                .params("pics", pics)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                RxToast.success(msg);
                                SharePreferencesUtil.setStringSharePreferences(MyPersonlDataActivity.this, Constans.UserIcon, pics);
                                SharePreferencesUtil.setStringSharePreferences(MyPersonlDataActivity.this, Constans.UserName, currUserName);
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
                    }
                });
    }

    private void createDialog() {
        loadBuilder = new LoadingDailog.Builder(MyPersonlDataActivity.this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        dialog = loadBuilder.create();
        dialog.show();

    }

    //獲取用戶基本信息
    private void getPersonData() {
        OkGo.<String>post(ServiceApi.UserInfo)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                UserDataBean userDataBean = gson.fromJson(s, UserDataBean.class);
                                currIconUrl = String.valueOf(userDataBean.getData().getPicAdress());
                                SharePreferencesUtil.setStringSharePreferences(MyPersonlDataActivity.this, Constans.UserIcon, currIconUrl);
                                if (!RxDataTool.isNullString(userDataBean.getData().getUserSex())) {
                                    currUserSex = String.valueOf(userDataBean.getData().getUserSex());
                                }
                                currUserName = String.valueOf(userDataBean.getData().getUserName());
                                if (!RxDataTool.isEmpty(userDataBean.getData().getBirthday())) {
                                    currUserBrith = String.valueOf(userDataBean.getData().getBirthday());
                                }
                                if (!RxDataTool.isEmpty(userDataBean.getData().getCity())) {
                                    currUserCity = String.valueOf(userDataBean.getData().getCity());
                                }
                                childBeanList.addAll(userDataBean.getChild());
                                currChildrenSize = childBeanList.size();

                                ShowUserData();
                                ShowChildData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    //展示孩子的数据
    private void ShowChildData() {
        for (int i = 0; i < childBeanList.size(); i++) {
            childrenAdapter.setChildrenDataLinster(new OnGetChildrenDataLinster() {
                @Override
                public void onItemData(int position, String childName) {
                    childBeanList.get(position).setChildName(childName);
                }
            });
        }

        if (childBeanList.size() > 0) {
            childrenAdapter.setNewData(childBeanList);
        }


    }

    //展示用户数据
    private void ShowUserData() {
        CommonUtil.loadImage(this, currIconUrl, itemChlidImage);
        if (currUserSex.equals("1")) {
            personalSex.setText("男");
        } else {
            personalSex.setText("女");
        }
        childrenNum.setText(String.valueOf("(" + childBeanList.size()) + ")个孩子");
        personalName.setText(currUserName);
        personalBrith.setText(currUserBrith);
        personalCity.setText(currUserCity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.personal_name_rl, R.id.personal_sex_rl, R.id.personal_brith_rl, R.id.personal_city_rl, R.id.mine_chilren_add_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_name_rl:
                ShowHead();
                break;
            case R.id.personal_sex_rl:
                ShowSexPcicker(true, 0);
                break;
            case R.id.personal_brith_rl:
                onYearMonthDayPicker(true, 0);
//                showDatePickerDialog();
                break;
            case R.id.personal_city_rl:

                startActivityForResult(new Intent(MyPersonlDataActivity.this, CityPickerActivity.class),
                        PICK_CITY);
                break;
            case R.id.mine_chilren_add_text:
                currChildrenSize++;
                UserDataBean.ChildBean childBean = new UserDataBean.ChildBean();
                childBean.setChildBirthday("");
                childBean.setChildName("");
                childBean.setChildSex("1");
                childBeanList.add(childBean);
                for (int i = 0; i < childBeanList.size(); i++) {
                    childrenAdapter.setChildrenDataLinster(new OnGetChildrenDataLinster() {
                        @Override
                        public void onItemData(int position, String childName) {
                            childBeanList.get(position).setChildName(childName);
                        }
                    });
                }

                childrenAdapter.setNewData(childBeanList);
                childrenNum.setText(String.valueOf("(" + currChildrenSize) + ")个孩子");
                break;
        }
    }

    /**
     * 展示头像
     */
    private void ShowHead() {
        final PickDialog pickDialog = new PickDialog(this, R.style.AlertDialogStyle);
        pickDialog.show();
        pickDialog.setTextName("从手机相册选择", "相机");
        pickDialog.selectMan(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDialog.cancel();
                RxPhotoTool.openLocalImage(MyPersonlDataActivity.this);
            }
        });
        pickDialog.selectWoMan(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDialog.cancel();
                if (!isUserPermiss) {
                    RxToast.error("请打开相机权限");
                    return;
                }
                RxPhotoTool.openCameraImage(MyPersonlDataActivity.this);
            }
        });

    }


    private void ShowSexPcicker(final boolean isParent, final int position) {
        final PickDialog pickDialog = new PickDialog(this, R.style.AlertDialogStyle);
        pickDialog.show();
        pickDialog.setTextName("男", "女");
        pickDialog.selectMan(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDialog.cancel();
                if (isParent) {
                    currUserSex = "1";
                    personalSex.setText("男");

                } else {
                    childBeanList.get(position).setChildSex("1");
                    childrenAdapter.notifyDataSetChanged();
                }
            }
        });
        pickDialog.selectWoMan(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDialog.cancel();

                if (isParent) {
                    currUserSex = "2";
                    personalSex.setText("女");
                } else {
                    childBeanList.get(position).setChildSex("2");
                    childrenAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    //选择出生日期
    private void onYearMonthDayPicker(final boolean isParent, final int position) {

        final DatePicker picker = new DatePicker(this);
        picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(Constans.startYear, Constans.startMonth, Constans.startDay);
        picker.setRangeEnd(Utils.getYear(), Utils.getMonth(), Utils.getDay());
//        picker.setSelectedItem(Utils.getYear(), 1, 1);
        picker.setWeightEnable(true);
        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if (isParent) {
                    personalBrith.setText(year + "-" + month + "-" + day);
                    currUserBrith = year + "-" + month + "-" + day;
                } else {
                    childBeanList.get(position).setChildBirthday(year + "-" + month + "-" + day);
                    childrenAdapter.notifyDataSetChanged();
                }

            }
        });
        picker.show();
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_CITY && resultCode == RESULT_OK) {
//
//        }
//    }

    @Override
    protected void initData() {
        super.initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mineDataRecyclerview.setLayoutManager(layoutManager);
        childrenAdapter = new ChildrenAdapter(R.layout.item_mine_children_data, childBeanList);
        mineDataRecyclerview.setAdapter(childrenAdapter);

        childrenAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.item_children_del_rl:
                        childrenAdapter.remove(position);
                        childBeanList = childrenAdapter.getData();
                        currChildrenSize--;
                        break;
                    case R.id.item_personal_name:
                        break;
                    case R.id.item_chlid_sex_rl:
                        ShowSexPcicker(false, position);
                        break;
                    case R.id.item_chlid_brith_rl:
                        onYearMonthDayPicker(false, position);
                        break;
                }

                childrenNum.setText(String.valueOf("(" + childBeanList.size()) + ")个孩子");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_CITY:
                if (data != null) {
                    currCity = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                    personalCity.setText(currCity);
                }
                break;
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    initUCrop(data.getData());
                }

                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.default_image)
                        //异常占位图(当加载异常的时候出现的图片)
                        .error(R.mipmap.default_image)
                        //禁止Glide硬盘缓存缓存
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                Glide.with(mContext).
                        load(RxPhotoTool.cropImageUri).
                        apply(options).
                        thumbnail(0.5f).
                        into(itemChlidImage);
//                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    roadImageView(resultUri, itemChlidImage);
                    RxSPTool.putContent(MyPersonlDataActivity.this, "AVATAR", resultUri.toString());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        Glide.with(MyPersonlDataActivity.this).
                load(uri).
                thumbnail(0.5f).
                into(imageView);

        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    private void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        isFinish();
    }

    private void isFinish() {
        final ComonSureDialog comonSureDialog = new ComonSureDialog(MyPersonlDataActivity.this, "是否保存用户信息？");
        comonSureDialog.onCreateView();
        comonSureDialog.setUiBeforShow();
        comonSureDialog.setCanceledOnTouchOutside(false);
        comonSureDialog.show();
        comonSureDialog.setOnClickSureListenter(new OnClickSureListenter() {
            @Override
            public void onSureClick() {
                finish();
                comonSureDialog.dismiss();
            }
        });


    }


}
