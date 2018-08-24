package com.padacn.xmgoing.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.fragment.SelectFragment;
import com.padacn.xmgoing.model.GoodSureBean;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.SureBuyBean;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.presenter.MealEventil;
import com.padacn.xmgoing.presenter.SeleDateEventil;
import com.padacn.xmgoing.util.CommonDialogUtil;
import com.padacn.xmgoing.util.DoubleArith;
import com.padacn.xmgoing.util.EvenUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.view.CustomViewPager;
import com.padacn.xmgoing.view.flow.FlowLayout;
import com.padacn.xmgoing.view.flow.TagAdapter;
import com.padacn.xmgoing.view.flow.TagFlowLayout;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/2 0002.
 */

public class SelectActivity extends BaseActivity {
    private static final String TAG = "SelectActivity";
    //    public static SelectActivity instanceSele;
    @BindView(R.id.register_bar)
    CustomToolBar registerBar;
    @BindView(R.id.selectt_set_date)
    TextView selecttSetDate;
    @BindView(R.id.select_date_vp)
    CustomViewPager selectDateVp;
    @BindView(R.id.select_date_tab)
    SlidingTabLayout selectDateTab;
    @BindView(R.id.select_flowlayout)
    TagFlowLayout selectFlowlayout;
    @BindView(R.id.reduce_button)
    TextView reduceButton;
    @BindView(R.id.reduce_number)
    TextView reduceNumber;
    @BindView(R.id.plus_button)
    TextView plusButton;
    @BindView(R.id.price_text)
    TextView priceText;
    @BindView(R.id.now_go_bug_rl)
    RelativeLayout nowGoBugRl;
    @BindView(R.id.use_calander)
    LinearLayout useCalander;
    @BindView(R.id.go_payment_text)
    TextView goPaymentText;

    private GoodsDtailsBean goodsDtailsBean;
    private List<GoodsDtailsBean.DataBean.TicketTypesBean> ticketTypesBeanList;

    private List<GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean> calendersBeanList;
    private List<String> mVals;
    private SelectAdapter selectAdapter;

    private String[] mTitles;


    private int reduceNum = 1;

    //当前选择的价钱
    private double currSelePrice;
    //当前选择套餐商品的总数量
    private int currNum;
    //当前选择的日期
    private String currDate = null;
    //當前套餐名字
    private String currTikectName;

    //當前套餐ID
    private String currTikectId;

    private String currTotalPrice;
    private int currGoodsNum;
    //上传到服务器的bean
    private SureGoodsBean sureGoodsBean;

    //传递到确认页面的商品details
    private SureGoodsBean.GoodsBean goodsBean;
    private List<SureGoodsBean.GoodsBean> goodsBeanList;

    private String jsonString;
    private SureBuyBean sureBuyBean;
    //是否是添加购物车
    private boolean isAddCar;
    //当前套餐日期ID
    private String ticketCalenderId;
    private int createOrderType;
    //當前套餐是是否用日期
    private boolean isUseCalader;
    private String currPNmae;
    private String currPicPath;
    private boolean currUseIdCard;
    private String currTraveNum;
    private String currPid;
    private String currCid;
    private String sellerId;
    //用户选择具体日期的月份下标
    private int userMonthPosition = 0;
    private int lstPosition = -1;


    public LoadingDailog.Builder loadBuilder;
    public LoadingDailog dialog;


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(MessageEvent event) {
        if (event.getCode() == EvenCode.ClearSele) {
            finish();
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_select_set_meal;
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
    protected void onStop() {
        super.onStop();
        if(isFinishing()){
            //TODO ondestory
        }
    }

    @Override
    protected void initView() {
        super.initView();
//        instanceSele = this;
        ticketTypesBeanList = new ArrayList<>();
        calendersBeanList = new ArrayList<>();
        goodsBeanList = new ArrayList<>();
        mVals = new ArrayList<>();
        registerBar.setStyle("选择套餐", Color.parseColor("#f8d948"));
        Intent intent = this.getIntent();
        isAddCar = intent.getBooleanExtra("isAddCar", false);
        Bundle bundle = new Bundle();
        createOrderType = bundle.getInt("createOrderType");
        sureGoodsBean = (SureGoodsBean) intent.getSerializableExtra("sureGoodsBean");
        currPNmae = sureGoodsBean.getGoods().get(0).getPName();
        currPicPath = sureGoodsBean.getGoods().get(0).getPicPath();
        currUseIdCard = sureGoodsBean.getGoods().get(0).isUseIdcard();
        currTraveNum = sureGoodsBean.getGoods().get(0).getTravelNum();
        currPid = sureGoodsBean.getGoods().get(0).getPid();
        currCid = sureGoodsBean.getGoods().get(0).getCid();
        sellerId = String.valueOf(sureGoodsBean.getSellerId());
        Log.e(TAG, "setGoods:222 " + String.valueOf(sureGoodsBean.getSellerId()));
        if (isAddCar) {
            goPaymentText.setText("立即加入");
        } else {
            goPaymentText.setText("立即预订");
        }
//        goodsDtailsBean = (GoodsDtailsBean) intent.getSerializableExtra("goodsDtails");
        ticketTypesBeanList.addAll(HomeThreeSelectPresenter.getSingleTon().getticketTypes());
        setTicket();

    }

    private void setTab(int position) {
        calendersBeanList.clear();
        calendersBeanList.addAll(ticketTypesBeanList.get(position).getCalenders());
        Log.e(TAG, "setTab: " + "+++++" + position);
        mTitles = new String[calendersBeanList.size()];
        for (int i = 0; i < calendersBeanList.size(); i++) {
            mTitles[i] = calendersBeanList.get(i).getMonth();
        }
        selectAdapter = new SelectAdapter(getSupportFragmentManager());
        selectDateVp.setScanScroll(false);
        selectDateVp.setOffscreenPageLimit(mTitles.length - 1);
        selectDateVp.setAdapter(selectAdapter);
        selectDateTab.setViewPager(selectDateVp);
        HomeThreeSelectPresenter.getSingleTon().saveSetMealData(calendersBeanList);
        EventBus.getDefault().post(new MealEventil(0, userMonthPosition, lstPosition));
        selectDateTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                EventBus.getDefault().post(new MealEventil(position, userMonthPosition, lstPosition));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 設置套餐
     */
    private void setTicket() {
        mVals = new ArrayList<>();
        for (int i = 0; i < ticketTypesBeanList.size(); i++) {
            mVals.add(i, ticketTypesBeanList.get(i).getTicketName());
        }

        final LayoutInflater mInflater = LayoutInflater.from(SelectActivity.this);
        TagAdapter tagAdapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_select_ticket,
                        selectFlowlayout, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                currTikectId = String.valueOf(ticketTypesBeanList.get(position).getTicketTypeId());
                currTikectName = ticketTypesBeanList.get(position).getTicketName();
                currNum = ticketTypesBeanList.get(position).getTicketNum();
                currSelePrice = ticketTypesBeanList.get(position).getPrice();
                currTotalPrice = String.valueOf(DoubleArith.mul((double) reduceNum, currSelePrice));
                priceText.setText(currTotalPrice);
                view.setBackgroundResource(R.drawable.shape_select_meal_true);
                if (ticketTypesBeanList.get(position).isUseCalender()) {//是否顯示日歷
                    setTab(position);
                    isUseCalader = ticketTypesBeanList.get(position).isUseCalender();
                    Log.e(TAG, "onSelected: " + currTikectId);
                    useCalander.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void unSelected(int position, View view) {
                view.setBackgroundResource(R.drawable.shape_select_meal_flase);
                super.unSelected(position, view);
            }
        };
        //预先设置选中
        tagAdapter.setSelectedList(0);
        selectFlowlayout.setAdapter(tagAdapter);

        selectFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Toast.makeText(SelectActivity.this, mVals.get(position), Toast.LENGTH_SHORT).show();
                calendersBeanList.clear();
                calendersBeanList.addAll(ticketTypesBeanList.get(position).getCalenders());
                return true;
            }
        });
        selectFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                Toast.makeText(SelectActivity.this, selectPosSet.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Subscribe
    public void onEventMainThread(EvenUtil event) {
        String msglog = "----onEventMainThread收到了消息：" + event.getMsg();
    }

    @Subscribe
    public void onEventMainThread(SeleDateEventil seleDateEventil) {
        Log.e(TAG, "onEventMainThread: " + String.valueOf(seleDateEventil.getId()));
        currDate = seleDateEventil.getDate();
        selecttSetDate.setText(seleDateEventil.getDate());
        currNum = seleDateEventil.getNum();
        currSelePrice = seleDateEventil.getDatePrice();
        ticketCalenderId = String.valueOf(seleDateEventil.getId());
        currTotalPrice = String.valueOf(DoubleArith.mul((double) reduceNum, currSelePrice));
        priceText.setText(currTotalPrice);
        userMonthPosition = seleDateEventil.getPositionMonth();
        lstPosition = seleDateEventil.getLastPosition();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
//        EventBus.getDefault().register(this);  //注册
        ButterKnife.bind(this);
    }

    @OnClick({R.id.reduce_button, R.id.reduce_number, R.id.plus_button, R.id.price_text, R.id.now_go_bug_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reduce_button:
                if (reduceNum > 1) {
                    reduceNum--;
                }
                priceText.setText(String.valueOf(DoubleArith.mul((double) reduceNum, currSelePrice)));
                currTotalPrice = String.valueOf(DoubleArith.mul((double) reduceNum, currSelePrice));
                reduceNumber.setText(String.valueOf(reduceNum));
                break;
            case R.id.reduce_number:

                break;
            case R.id.plus_button:
                if (reduceNum <= currNum) {
                    reduceNum++;
                }
                priceText.setText(String.valueOf(DoubleArith.mul((double) reduceNum, currSelePrice)));
                currTotalPrice = String.valueOf(DoubleArith.mul((double) reduceNum, currSelePrice));
                reduceNumber.setText(String.valueOf(reduceNum));
                break;
            case R.id.now_go_bug_rl:
                if (RxDataTool.isNullString(currDate) && isUseCalader) {
                    RxToast.warning("亲，请选择出行日期！");
                    return;
                }

                goodsBean = new SureGoodsBean.GoodsBean();
                sureGoodsBean = new SureGoodsBean();
                goodsBean.setPid(currPid);
                goodsBean.setPName(currPNmae);
                goodsBean.setPicPath(currPicPath);
                goodsBean.setUseIdcard(currUseIdCard);
                goodsBean.setTravelNum(currTraveNum);
                Log.e(TAG, "onSelected: " + currTikectId);
                goodsBean.setTiketName(currTikectName);
                goodsBean.setPnum(String.valueOf(reduceNum));
                goodsBean.setTravleTime(currDate);
                goodsBean.setCid(ticketCalenderId);
                goodsBean.setTid(currTikectId);
                Log.e(TAG, "onSelected1: " + currTikectId);
                if (isUseCalader) {
                    goodsBean.setTravleTime(currDate);
                }
                goodsBean.setPrice(String.valueOf(currSelePrice));
                goodsBean.setTotalPrice(currTotalPrice);
                goodsBeanList.clear();
                goodsBeanList.add(goodsBean);
                sureGoodsBean.setGoods(goodsBeanList);
                sureGoodsBean.setSellerId(sellerId);
                checkStore();
                break;
        }
    }


    private void addCar() {
        OkGo.<String>post(ServiceApi.addBuyCard)
                .tag(this)
                .params("pId", currPid)
                .params("pNum", reduceNum)
                .params("ticketId", currTikectId)
                .params("ticketName", currTikectName)
                .params("useDay", currDate)
                .params("calenderId", ticketCalenderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
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


    //设置上传服务器的jsonString
    public String setSellergoods() {
        String jsonString;
        JSONArray sellerGoods = new JSONArray();
        JSONArray jsonarray = new JSONArray();
        JSONObject object = new JSONObject();
        JSONObject jsonObj = new JSONObject();//商品的jsonObject
        try {
            jsonObj.put("pid", sureGoodsBean.getGoods().get(0).getPid());
            jsonObj.put("pName", sureGoodsBean.getGoods().get(0).getPName());
            jsonObj.put("pnum", String.valueOf(reduceNum));
            jsonObj.put("tid", currTikectId);
            if (!RxDataTool.isNullString(ticketCalenderId)) {
                jsonObj.put("cid", ticketCalenderId);
            }
            jsonarray.put(jsonObj);
            object.put("sellerId", sureGoodsBean.getSellerId());
            object.put("goods", jsonarray);
            sellerGoods.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = sellerGoods.toString();
        return jsonString;
    }

    /**
     * 检查商品库存
     */
    private void checkStore() {
        showDialog();
        OkGo.<String>post(ServiceApi.checkStore)
                .tag(this)
                .params("sellergoods", setSellergoods())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        dialog.cancel();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            if (result == 1) {
                                if (isAddCar) {
                                    addCar();
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("createOrderType", 1);
                                    List<SureGoodsBean> sureGoodsBeanList = new ArrayList<>();
                                    sureGoodsBeanList.add(sureGoodsBean);
                                    HomeThreeSelectPresenter.getSingleTon().saveSureGoodBeanList(sureGoodsBeanList);
                                    Log.e(TAG, "onSuccess: " + sureGoodsBeanList.size());
                                    BaseActivity.navigate(SelectActivity.this, GoodsSureActivity.class, bundle);
                                }
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

    private void showDialog() {
        loadBuilder = new LoadingDailog.Builder(SelectActivity.this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        dialog = loadBuilder.create();
        dialog.show();
    }


    private class SelectAdapter extends FragmentPagerAdapter {
        public SelectAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Log.e(TAG, "getItem: " + position);
            return SelectFragment.getInstance(calendersBeanList.get(position));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);//取消注册
//        instanceSele.finish();
    }
}
