package com.padacn.xmgoing.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.CommonTravelAdapter;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.ContactListBean;
import com.padacn.xmgoing.model.ContactsDataBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.presenter.ContactEventil;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.CommonBarInter;
import com.padacn.xmgoing.view.CustomToolBar;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class CommonTravelActivity extends BaseActivity {
    @BindView(R.id.common_travel_people_topbar)
    CustomToolBar commonTravelPeopleTopbar;
    @BindView(R.id.common_travel_people_rc)
    RecyclerView commonTravelPeopleRc;
    @BindView(R.id.common_travel_people_cancel)
    TextView commonTravelPeopleCancel;
    @BindView(R.id.common_travel_people_sure)
    TextView commonTravelPeopleSure;
    @BindView(R.id.common_travel_people_bottom_ll)
    LinearLayout commonTravelPeopleBottomLl;
    List<ContactListBean.ContactIdsBean> datas;
    private CommonTravelAdapter commonTravelAdapter;

    private List<ContactsDataBean.DataBean> dataBeanList;

    //当前出行人的总数量
    private String currTravleNum;
    //用戶選擇的出行人數量
    private int useSelectNum = 0;
    private String currPid;
    private int currPosition;

    //是否是商品詳情頁面添加出行人
    private boolean isAddTravle;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_common_travel_people;
    }

    private static final String TAG = "CommonTravelActivity";

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
        EventBus.getDefault().register(this);  //注册

        commonTravelPeopleTopbar.setStyle("常用出行人", "添加+", Color.parseColor("#f8d948"), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                commonTravelAdapter.addData(datas);
                BaseActivity.navigate(CommonTravelActivity.this, CommonTravleAddActivity.class);
            }
        });
        Bundle bundle = this.getIntent().getExtras();
        isAddTravle = bundle.getBoolean("isAddTravle");
        if (isAddTravle) {
            currTravleNum = bundle.getString("travelNum");
            currPid = bundle.getString("pid");
            currPosition = bundle.getInt("position");
            commonTravelPeopleBottomLl.setVisibility(View.VISIBLE);
            useSelectNum = HomeThreeSelectPresenter.getSingleTon().getContactList().get(currPosition).getContactIds().size();
        } else {
            commonTravelPeopleBottomLl.setVisibility(View.GONE);
        }
        dataBeanList = new ArrayList<>();
        commonTravelPeopleRc.setLayoutManager(new LinearLayoutManager(this));
        commonTravelAdapter = new CommonTravelAdapter(R.layout.item_common_travel_people, dataBeanList);
        commonTravelPeopleRc.setAdapter(commonTravelAdapter);
        commonTravelAdapter.setBoolean(isAddTravle);
        commonTravelAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_common_travel_people_del:
                        deleTravelPeople(position);

                        break;
                    case R.id.item_common_travel_people_ll:

                        if (dataBeanList.get(position).isCheck()) {
                            dataBeanList.get(position).setCheck(false);
                            useSelectNum--;
                        } else {
                            if (useSelectNum <= Integer.parseInt(currTravleNum)) {
                                dataBeanList.get(position).setCheck(true);
                                useSelectNum++;
                            } else {
                                RxToast.warning("已经达到人数上线");
                            }
                        }
                        commonTravelAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });


    }

    /**
     * 删除出行人
     */
    private void deleTravelPeople(final int currPosition) {

        OkGo.<String>post(ServiceApi.delContacts)
                .tag(this)
                .params("cId", dataBeanList.get(currPosition).getCId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                commonTravelAdapter.remove(currPosition);
                                RxToast.success(msg);
                            } else {
                                RxToast.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        getCommonData();
    }

    //获取常用联系人的数据
    private void getCommonData() {
        dataBeanList.clear();
        OkGo.<String>post(ServiceApi.queryContacts)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                ContactsDataBean contactsDataBean = gson.fromJson(s, ContactsDataBean.class);
                                dataBeanList.addAll(contactsDataBean.getData());

                                if (isAddTravle) {
                                    for (int i = 0; i < dataBeanList.size(); i++) {
                                        for (int j = 0; j < HomeThreeSelectPresenter.getSingleTon().getContactList().get(currPosition).getContactIds().size(); j++) {
                                            if (String.valueOf(dataBeanList.get(i).getCId()).
                                                    equals(HomeThreeSelectPresenter.getSingleTon().
                                                            getContactList().get(currPosition).getContactIds().
                                                            get(j).getId())) {
                                                dataBeanList.get(i).setCheck(true);
                                            }
                                        }
                                    }
                                }
                                commonTravelAdapter.setNewData(dataBeanList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getCommonData();
    }
/*   @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(MessageEvent event) {
        if (event.getCode() == EvenCode.TravlePeopleAdd) {
            getCommonData();
        }
    }*/

    @OnClick({R.id.common_travel_people_topbar, R.id.common_travel_people_cancel, R.id.common_travel_people_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_travel_people_topbar:
                break;
            case R.id.common_travel_people_cancel:
                break;
            case R.id.common_travel_people_sure:
                getUseSeleData();
                finish();
                break;
        }
    }


    //得到用户选择到的出行人數據
    private void getUseSeleData() {
        datas = new ArrayList<>();
        ContactListBean contactListBean = new ContactListBean();
        for (int i = 0; i < dataBeanList.size(); i++) {
            if (dataBeanList.get(i).isCheck()) {
                ContactListBean.ContactIdsBean contactIdsBean = new ContactListBean.ContactIdsBean();
                contactIdsBean.setId(String.valueOf(dataBeanList.get(i).getCId()));
                contactIdsBean.setName(dataBeanList.get(i).getConName());
                datas.add(contactIdsBean);
//
//                SureGoodsBean.GoodsBean.ContactIdsBean contactIdsBean1=new SureGoodsBean.GoodsBean.ContactIdsBean();
//                contactIdsBean1.setId(dataBeanList.get(i).getUser_id());
//                contactIdsBean1.setName(dataBeanList.get(i).getConName());
            }
        }
        contactListBean.setPid(currPid);
        contactListBean.setContactIds(datas);
        //用户总共选择的出行人
        HomeThreeSelectPresenter.getSingleTon().saveContactList(contactListBean, currPosition);
        //用户单次点击
//        HomeThreeSelectPresenter.getSingleTon().saveOneContactList(contactIdsBeanList);
        Log.e(TAG, "getUseSeleData: " + datas.size() + "___" + HomeThreeSelectPresenter.getSingleTon().getContactList());
        EventBus.getDefault().post(new ContactEventil(currPid, currPosition));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }

    @Subscribe
    public void onEventMainThread(ContactEventil evenUtil) {
    }

}
