package com.padacn.xmgoing.fragment;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.LoginActivity;
import com.padacn.xmgoing.activity.MyFootprintActivity1;
import com.padacn.xmgoing.activity.MyOrderActivity;
import com.padacn.xmgoing.activity.MyPersonlDataActivity;
import com.padacn.xmgoing.activity.MySettingActivity;
import com.padacn.xmgoing.activity.MycouponsActivity;
import com.padacn.xmgoing.activity.RegisterActivity;
import com.padacn.xmgoing.adapter.MineClomnAdapter;
import com.padacn.xmgoing.adapter.MyShareAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.MineClomnModel;
import com.padacn.xmgoing.model.OrderCountBean;
import com.padacn.xmgoing.model.ShareBean;
import com.padacn.xmgoing.util.CommonUtil;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.view.CallDialog;
import com.padacn.xmgoing.view.RecycleViewDivider;
import com.padacn.xmgoing.view.ZQRoundOvalImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;
import com.zyyoona7.popup.EasyPopup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by 46009 on 2018/4/26.
 */

public class MyFragment extends BaseFragment {

    private static final String TAG = "MyFragment";

    Unbinder unbinder;
    @BindView(R.id.mine_recyclerview)
    RecyclerView mineRecyclerview;
    @BindView(R.id.my_order_ll)
    LinearLayout myOrderLl;
    @BindView(R.id.my_nopay_ll)
    LinearLayout myNopayLl;
    @BindView(R.id.my_totravel_ll)
    LinearLayout myTotravelLl;
    @BindView(R.id.my_comments_ll)
    LinearLayout myCommentsLl;
    @BindView(R.id.my_after_sale_ll)
    LinearLayout myAfterSaleLl;
    @BindView(R.id.mine_group_ll)
    LinearLayout mineGroupLl;
    @BindView(R.id.item_chlid_image)
    ZQRoundOvalImageView itemChlidImage;
    @BindView(R.id.my_go_login_text)
    TextView myGoLoginText;
    @BindView(R.id.my_go_register_text)
    TextView myGoRegisterText;
    @BindView(R.id.my_no_user)
    LinearLayout myNoUser;
    @BindView(R.id.personal_data)
    LinearLayout personalData;
    @BindView(R.id.item_chlid_name)
    TextView itemChlidName;
    @BindView(R.id.persion_edit)
    ImageView persionEdit;
    @BindView(R.id.order_top_image1)
    ImageView orderTopImage1;
    @BindView(R.id.order_top_image2)
    ImageView orderTopImage2;
    @BindView(R.id.order_top_image3)
    ImageView orderTopImage3;
    @BindView(R.id.order_top_image4)
    ImageView orderTopImage4;
    @BindView(R.id.order_top_image5)
    ImageView orderTopImage5;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.persion_edit_rl)
    RelativeLayout persionEditRl;
    @BindView(R.id.mine_head_rl)
    RelativeLayout mineHeadRl;
//    @BindView(R.id.mine_share_recyclerview)
//    RecyclerView mineShareRecyclerview;
//    @BindView(R.id.mine_share_cancel)
//    RelativeLayout mineShareCancel;

    private int[] shareIconId = new int[]{R.mipmap.share_icon_wxhy, R.mipmap.share_icon_pyq,
            R.mipmap.share_icon_qq, R.mipmap.share_icon_qqkj, R.mipmap.share_icon_weibo, R.mipmap.share_icon_fuzhi};
    private String[] shareTitle = new String[]{"微信好友", "朋友圈", "QQ好友", "QQ空间", "新浪微博", "复制链接"};

    private List<ShareBean> shareBeanList;
    private MineClomnAdapter mineClomnAdapter;
    private EasyPopup easyPopup;
    private MyShareAdapter myShareAdapter;
    private List<Badge> badges;

    private boolean isFirst = true;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        setOderPoint();
        return rootView;
    }

    @Override
    protected void setListener() {
        super.setListener();
        if (SharePreferencesUtil.getBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, false)) {
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(final RefreshLayout refreshlayout) {
                    refreshlayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showData();
                            refreshLayout.finishRefresh();
                        }
                    }, 2000);
                }

            });
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
       /* if (!isFirst && !hidden) {
            showData();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    @Override
    protected void initView() {
        super.initView();
        List<MineClomnModel> datas = new ArrayList<>();
        String mineClomnText[] = getResources().getStringArray(R.array.mine_clome);
        int mineImageId[] = {R.mipmap.my_cloumn_card, R.mipmap.my_cloumn_footprint, R.mipmap.my_cloumn_collection,
                R.mipmap.my_cloumn_share, R.mipmap.my_cloumn_kefu, R.mipmap.my_cloumn_setting};
        MineClomnModel model;
        for (int i = 0; i < 6; i++) {
            model = new MineClomnModel();
            model.setMineClomnText(mineClomnText[i]);
            model.setMineClomnID(mineImageId[i]);
            datas.add(model);
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mineRecyclerview.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.common_f0)));
        mineRecyclerview.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.common_f0)));

        mineRecyclerview.setLayoutManager(gridLayoutManager);
        mineClomnAdapter = new MineClomnAdapter(R.layout.item_mine_column, datas);
        mineRecyclerview.setAdapter(mineClomnAdapter);

        mineClomnAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        if (!SharePreferencesUtil.getBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, false)) {
                            BaseActivity.navigate(getContext(), LoginActivity.class);
                            return;
                        }
                        BaseActivity.navigate(getContext(), MycouponsActivity.class);
                        break;
                    case 1:
                        if (!SharePreferencesUtil.getBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, false)) {
                            BaseActivity.navigate(getContext(), LoginActivity.class);
                            return;
                        }
                        BaseActivity.navigate(getContext(), MyFootprintActivity1.class);
                        break;
                    case 2:
                        RxToast.error("暂时未开放功能");
//                        BaseActivity.navigate(getContext(), MyCollectionActivity.class);
                        break;

                    case 3:
                        easyPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                        break;
                    case 4:
                        diallPhone(Constans.PhoneNumber);
                        break;
                    case 5:
                        if (!SharePreferencesUtil.getBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, false)) {
                            BaseActivity.navigate(getContext(), LoginActivity.class);
                            return;
                        }
                        BaseActivity.navigate(getContext(), MySettingActivity.class);
                        break;
                }
            }
        });

        initPopView();
    }

    private void setOderPoint() {
        badges = new ArrayList<>();
        badges.add(new QBadgeView(getContext()).bindTarget(myNopayLl).setBadgeNumber(0));
        badges.add(new QBadgeView(getContext()).bindTarget(myTotravelLl).setBadgeNumber(0));
        badges.add(new QBadgeView(getContext()).bindTarget(myCommentsLl).setBadgeNumber(0));

        for (Badge badge : badges) {
            badge.setGravityOffset(10, 0, true)
                    .setBadgeGravity(Gravity.END | Gravity.TOP)
                    .setBadgeTextSize(10, true);
        }
        badges.add(new QBadgeView(getContext()).bindTarget(myAfterSaleLl).setBadgeNumber(0).setGravityOffset(16, 0, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeTextSize(10, true));

    }

    //展示用户数据
    private void showData() {
        if (SharePreferencesUtil.getBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, false)) {
            getOrderCount();
            persionEdit.setVisibility(View.VISIBLE);
            personalData.setVisibility(View.VISIBLE);
            mineHeadRl.setVisibility(View.VISIBLE);
            myNoUser.setVisibility(View.GONE);
            String icon = SharePreferencesUtil.getStringSharePreferences(getContext(), Constans.UserIcon, "");
            if (RxDataTool.isNullString(icon)) {
                itemChlidImage.setBackgroundResource(R.mipmap.default_image);
            } else {
                CommonUtil.loadImage(getContext(), icon, itemChlidImage);
            }
            itemChlidName.setText(SharePreferencesUtil.getStringSharePreferences(getContext(), Constans.UserName, ""));

        } else {
            persionEdit.setVisibility(View.GONE);
            myNoUser.setVisibility(View.VISIBLE);
            personalData.setVisibility(View.GONE);
            mineHeadRl.setVisibility(View.GONE);
            for (Badge badge : badges) {
                badge.setBadgeNumber(0);
            }
        }
    }

    /**
     * 获取订单count
     */
    private void getOrderCount() {
        OkGo.<String>post(ServiceApi.orderCount)
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
                                OrderCountBean orderCountBean = gson.fromJson(s, OrderCountBean.class);
                                badges.get(0).setBadgeNumber(orderCountBean.getData().getNopay());
                                badges.get(1).setBadgeNumber(orderCountBean.getData().getUnuse());
                                badges.get(2).setBadgeNumber(orderCountBean.getData().getNocomment());
                                badges.get(3).setBadgeNumber(orderCountBean.getData().getRejected());
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

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            RxToast.success("分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            RxToast.error("分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            RxToast.error("分享取消");
        }
    };

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void diallPhone(final String phoneNum) {
        final CallDialog callDialog = new CallDialog(getContext());
        callDialog.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                startActivity(intent);
            }
        });
        callDialog.getNumberView().setText(phoneNum);
        callDialog.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDialog.cancel();
            }
        });

        callDialog.show();
    }


    /**
     * 底部分享view
     */
    @SuppressLint("ResourceType")
    private void initPopView() {
        shareBeanList = new ArrayList<>();
        for (int i = 0; i < shareIconId.length; i++) {
            ShareBean shareBean = new ShareBean();
            shareBean.setShareIcon(shareIconId[i]);
            shareBean.setShareTitle(shareTitle[i]);
            shareBeanList.add(shareBean);
        }
        easyPopup = new EasyPopup(getContext())
                .setContentView(R.layout.fragment_mine_share, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setFocusAndOutsideEnable(true)
//                .setDimView(mineGroupLl)
                .setAnimationStyle(R.style.dialogWindowAnim)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f)
                .apply();

        RelativeLayout relativeLayout = easyPopup.findViewById(R.id.mine_share_cancel);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyPopup.dismiss();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = easyPopup.findViewById(R.id.mine_share_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        myShareAdapter = new MyShareAdapter(R.layout.item_my_share, shareBeanList);
        recyclerView.setAdapter(myShareAdapter);
        myShareAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                easyPopup.dismiss();
                switch (position) {

                    case 0:
                        ShareApp(SHARE_MEDIA.WEIXIN);
                        break;
                    case 1:
                        ShareApp(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case 2:
                        RxToast.error("暂未开放");
//                        ShareApp(SHARE_MEDIA.QQ);
                        break;
                    case 3:
                        RxToast.error("暂未开放");
//                        ShareApp(SHARE_MEDIA.QZONE);
                        break;
                    case 4:
                        ShareApp(SHARE_MEDIA.SINA);
                        break;
                    case 5:
                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(Constans.APP_DOWNLOAD);
                        RxToast.success("复制成功");

                        break;

                }
            }
        });
    }

    private void ShareApp(SHARE_MEDIA share_media) {
        UMWeb web = new UMWeb(Constans.APP_DOWNLOAD);
        web.setTitle(Constans.APP_NAME);
        web.setThumb(new UMImage(getContext(), R.mipmap.app_icon));
        web.setDescription(Constans.APP_CONTANT);
        new ShareAction(getActivity()).withMedia(web)
                .setPlatform(share_media)//传入平台
                .setCallback(shareListener).share();
        easyPopup.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_order_ll, R.id.my_nopay_ll, R.id.my_totravel_ll,
            R.id.my_comments_ll, R.id.my_after_sale_ll, R.id.my_go_login_text,
            R.id.my_go_register_text, R.id.personal_data, R.id.persion_edit_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_order_ll:
                goOrder(0);
                break;
            case R.id.my_nopay_ll:
                goOrder(1);
                break;
            case R.id.my_totravel_ll:
                goOrder(2);
                break;
            case R.id.my_comments_ll:
                goOrder(3);
                break;
            case R.id.my_after_sale_ll:
                goOrder(4);
                break;
            case R.id.my_go_login_text:

                BaseActivity.navigate(getContext(), LoginActivity.class);
                break;
            case R.id.my_go_register_text:

                BaseActivity.navigate(getContext(), RegisterActivity.class);
                break;
            case R.id.personal_data:
                if (!SharePreferencesUtil.getBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, false)) {
                    BaseActivity.navigate(getContext(), LoginActivity.class);
                    return;
                }
                BaseActivity.navigate(getContext(), MyPersonlDataActivity.class);
                break;
            case R.id.persion_edit_rl:
                if (!SharePreferencesUtil.getBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, false)) {
                    BaseActivity.navigate(getContext(), LoginActivity.class);
                    return;
                }
                BaseActivity.navigate(getContext(), MyPersonlDataActivity.class);
                break;
        }
    }


    //跳转order页面
    private void goOrder(int position) {
        if (!SharePreferencesUtil.getBooleanSharePreferences(getContext(), Constans.isAlreadyLogin, false)) {
            BaseActivity.navigate(getContext(), LoginActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            BaseActivity.navigate(getContext(), MyOrderActivity.class, bundle);
        }
    }

}
