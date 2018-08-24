package com.padacn.xmgoing.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.maning.library.MClearEditText;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.EvenCode;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.bean.HotSearch;
import com.padacn.xmgoing.dao.HotSearchDao;
import com.padacn.xmgoing.db.DBManager;
import com.padacn.xmgoing.db.HotSearchDaoOpe;
import com.padacn.xmgoing.model.HotSearchBean;
import com.padacn.xmgoing.util.StringUtil;
import com.padacn.xmgoing.util.common.GlideCacheUtil;
import com.padacn.xmgoing.util.even.MessageEvent;
import com.padacn.xmgoing.view.flow.FlowLayout;
import com.padacn.xmgoing.view.flow.TagAdapter;
import com.padacn.xmgoing.view.flow.TagFlowLayout;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/17 0017.
 */

public class HotSearchActivity extends BaseActivity {
    private static final String TAG = "HotSearchActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_page_flowlayout)
    TagFlowLayout searchPageFlowlayout;
    @BindView(R.id.search_history_flowlayout)
    TagFlowLayout searchHistoryFlowlayout;
    @BindView(R.id.hot_search_ll)
    LinearLayout hotSearchLl;
    @BindView(R.id.history_search_ll)
    LinearLayout historySearchLl;
    @BindView(R.id.history_del_image)
    ImageView historyDelImage;
    @BindView(R.id.hot_back_rl)
    RelativeLayout hotBackRl;
    @BindView(R.id.home_top_bar_search_ll)
    LinearLayout homeTopBarSearchLl;
    @BindView(R.id.hot_search_text_rl)
    RelativeLayout hotSearchTextRl;
    @BindView(R.id.hot_search_del_Rl)
    RelativeLayout hotSearchDelRl;
    @BindView(R.id.hot_search_input)
    MClearEditText hotSearchInput;
    private TagAdapter<String> adapter;

    private HotSearchDao hotSearchDao;

    private List<HotSearch> hotSearchList;
    private List<HotSearchBean.DataBean> hotSearchBeanList;
    private List<String> mVals;
    private List<String> mHistory;


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(MessageEvent event) {
        if (event.getCode() == EvenCode.ClearHot) {
            finish();
        }
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_hot_search;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.common_ffffff)
                .navigationBarColor(R.color.home_top_text_color)
                .keyboardEnable(true)
                .init();
    }


    @Override
    protected void initView() {
        super.initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        showHistorySearch();
        getData();


        //将输入换成搜索
        hotSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    goSearch();
                }
                return false;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void getData() {
        super.initData();
        hotSearchBeanList = new ArrayList<>();
        OkGo.<String>post(ServiceApi.HOTSEARCH_LIST)
                .tag(this)
                .params("type", "1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                HotSearchBean hotSearchBean = gson.fromJson(s, HotSearchBean.class);
                                if (hotSearchBean.getResult() == 1) {
                                    if (hotSearchBean.getData().size() > 0) {
                                        hotSearchLl.setVisibility(View.VISIBLE);
                                        hotSearchBeanList.addAll(hotSearchBean.getData());
                                        showHotSearch();
                                    } else {
                                        hotSearchLl.setVisibility(View.GONE);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void showHistorySearch() {
        hotSearchDao = DBManager.getDaoSession(this).getHotSearchDao();
        mHistory = new ArrayList<>();
        //查詢數據庫
        hotSearchList = new ArrayList<>();
        hotSearchList.addAll(HotSearchDaoOpe.queryAll(this));
        if (hotSearchList.size() <= 0) {
            hotSearchDelRl.setVisibility(View.GONE);
        } else {
            hotSearchDelRl.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < hotSearchList.size(); i++) {
            mHistory.add(i, hotSearchList.get(i).getName());
        }

        final LayoutInflater mInflaterHistory = LayoutInflater.from(HotSearchActivity.this);
        searchHistoryFlowlayout.setAdapter(new TagAdapter<String>(mHistory) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflaterHistory.inflate(R.layout.item_hot_search,
                        searchHistoryFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });

        searchHistoryFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                getSearchData(mHistory.get(position));
                return true;
            }
        });
    }

    private void showHotSearch() {
        mVals = new ArrayList<>();
        for (int i = 0; i < hotSearchBeanList.size(); i++) {
            mVals.add(i, hotSearchBeanList.get(i).getName());
        }
        if (mVals.size() <= 0) {
            hotSearchLl.setVisibility(View.GONE);
        } else {
            hotSearchLl.setVisibility(View.VISIBLE);
        }
        final LayoutInflater mInflater = LayoutInflater.from(HotSearchActivity.this);
        searchPageFlowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_hot_search,
                        searchPageFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        searchPageFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                getSearchData(mVals.get(position));
                return true;
            }
        });
        searchPageFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                HotSearchActivity.this.setTitle("choose:" + selectPosSet.toString());
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.history_del_image, R.id.hot_back_rl, R.id.hot_search_text_rl, R.id.hot_search_del_Rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hot_search_text_rl:
                goSearch();

                break;
            case R.id.hot_back_rl:
                finish();
                break;

            case R.id.history_del_image:
                HotSearchDaoOpe.deleteAllData(HotSearchActivity.this);
                showHistorySearch();
                break;
        }
    }

    //点击搜索
    private void goSearch() {
        if (RxDataTool.isEmpty(hotSearchInput.getText().toString())) {
            RxToast.error("请输入搜索内容");
        } else {
            if (hotSearchList.size() < 15) {
                List<HotSearch> list =
                        DBManager.getDaoSession(this).getHotSearchDao().queryBuilder().where(HotSearchDao
                                .Properties.Name.eq(StringUtil.removeSpace(hotSearchInput.getText().toString()))).build().list();
                hotSearchDao.deleteInTx(list);
                HotSearchDaoOpe.saveData(this, new HotSearch(null, StringUtil.removeSpace(hotSearchInput.getText().toString())));
                getSearchData(StringUtil.removeSpace(hotSearchInput.getText().toString()));
            } else {
                //删除第一条数据，用于替换最后一条搜索
                hotSearchDao.delete(hotSearchDao.queryBuilder().list().get(0));
                List<HotSearch> list1 =
                        DBManager.getDaoSession(this).getHotSearchDao().queryBuilder().where(HotSearchDao
                                .Properties.Name.eq(StringUtil.removeSpace(hotSearchInput.getText().toString()))).build().list();
                hotSearchDao.deleteInTx(list1);
                HotSearchDaoOpe.saveData(this, new HotSearch(null, StringUtil.removeSpace(hotSearchInput.getText().toString())));
            }

        }
    }

    //获取搜索内容
    private void getSearchData(String serachString) {
        Bundle bundle = new Bundle();
        bundle.putString("searchString", serachString);
        BaseActivity.navigate(HotSearchActivity.this, SearchResultActivity.class, bundle);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
