package com.padacn.xmgoing.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.GridImageAdapter;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.util.CommonDialogUtil;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.FullyGridLayoutManager;
import com.padacn.xmgoing.view.ratingbar.BaseRatingBar;
import com.padacn.xmgoing.view.ratingbar.ScaleRatingBar;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/4 0004.
 */

public class CommentActivity extends BaseActivity {
    @BindView(R.id.comment_bar)
    CustomToolBar commentBar;
    @BindView(R.id.comment_top_image)
    ImageView commentTopImage;
    @BindView(R.id.comment_tavle_people)
    TextView commentTavlePeople;
    @BindView(R.id.comment_date)
    TextView commentDate;
    @BindView(R.id.comment_tavle_title)
    TextView commentTavleTitle;
    @BindView(R.id.comment_recyclerView)
    RecyclerView commentRecyclerView;
    @BindView(R.id.ratingBar)
    ScaleRatingBar ratingBar;
    @BindView(R.id.login_password_edit)
    EditText loginPasswordEdit;
    @BindView(R.id.release_rl)
    RelativeLayout releaseRl;

    private String orderProductId;
    private String proPic;
    private String proName;
    private String useDay;
    private String excursion;
    private String typeName;

    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();

    private List<String> stringList = new ArrayList<>();
    private int maxSelectNum = 6;
    private int themeId = R.style.picture_default_style;
    private List<String> upStringList = new ArrayList<>();
    private int userPoint;
    private static final String TAG = "CommentActivity";
    private boolean isUpFailed;

    private LoadingDailog.Builder loadBuilder;
    private LoadingDailog dialog;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_comment;
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
        commentBar.setStyle("发表评论", Color.parseColor("#f8d948"));
        Bundle bundle = this.getIntent().getExtras();
        orderProductId = bundle.getString("orderProductId");
        proPic = bundle.getString("proPic");
        proName = bundle.getString("proName");
        useDay = bundle.getString("useDay");
        typeName = bundle.getString("typeName");
        excursion = bundle.getString("excursion");
        showData();
        loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        ratingBar.setClickable(true);
        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating) {
                userPoint = (int) rating;
            }
        });

        FullyGridLayoutManager manager = new FullyGridLayoutManager(CommentActivity.this, 3, GridLayoutManager.VERTICAL, false);
        commentRecyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(CommentActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        commentRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(CommentActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(CommentActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(CommentActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });


    }

    private void showData() {
        CommonUtil.loadImage(this, proPic, commentTopImage);
        commentTavlePeople.setText(typeName);
        commentDate.setText(useDay);
        commentTavleTitle.setText(proName);
    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = true;
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(CommentActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(6)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(3)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                        .compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                        .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        .cropCompressQuality(60)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled(true) // 裁剪是否可旋转图片
                        //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        }

    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    stringList.clear();

                    for (LocalMedia media : selectList) {
                        if (media.isCompressed()) {
                            stringList.add(media.getCompressPath());
                            Log.e("图片-----》", media.getCompressPath());
                        }
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.release_rl)
    public void onViewClicked() {
        if (RxDataTool.isNullString(loginPasswordEdit.getText().toString())) {
            RxToast.error("请输入评论内容");
            return;
        }
        updata();
    }


    private void updata() {
        upStringList.clear();
        dialog = loadBuilder.create();
        dialog.show();
        if (stringList.size() > 0) {
            for (int i = 0; i < stringList.size(); i++) {
                if (isUpFailed) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    return;
                }
                SaveUserHead("image" + i, stringList.get(i));
                Log.e(TAG, "得到的图片地址: " + "image" + i + "______" + stringList.get(i));
            }
        } else {
            upCommentData();
        }

    }

    //保存用戶照片
    private void SaveUserHead(String key, final String urlString) {

        OkGo.<String>post(ServiceApi.uploadPicToAli)
                .tag(this)
                .params(key, new File(urlString))
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
                                Log.e(TAG, "上傳地址: " + data);
                                upStringList.add(data);
                                if (upStringList.size() == stringList.size()) {
                                    upCommentData();
                                }
                            } else {
                                isUpFailed = true;
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

    private void upCommentData() {
//        Gson gson = new Gson();
//        String jsonArray = gson.toJson(upStringList, new TypeToken<List<String>>() {
//        }.getType());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < upStringList.size(); i++) {
            if (i < upStringList.size() - 1) {
                stringBuffer.append(upStringList.get(i) + ",");
            } else {
                stringBuffer.append(upStringList.get(i));
            }

        }
        OkGo.<String>post(ServiceApi.review)
                .tag(this)
                .params("reviews", loginPasswordEdit.getText().toString())
                .params("typeName", typeName)
                .params("orderProductId", orderProductId)
                .params("score", String.valueOf(userPoint))
                .params("pics", stringBuffer.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        String s = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                RxToast.success(msg);
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
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

    }
}
