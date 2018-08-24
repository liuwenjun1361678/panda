package com.padacn.xmgoing.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.homethree.HomeThreeFilterAdapter;
import com.padacn.xmgoing.api.ServiceApi;
import com.padacn.xmgoing.model.HomeThreeBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.presenter.Eventil;
import com.padacn.xmgoing.presenter.FinishEventUtil;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.presenter.SecondEventil;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 筛选商品属性选择的popupwindow
 */
public class FilterFragments extends FrameLayout {
    private View contentView;
    private RecyclerView selectionList;
    private TextView filterReset;
    private TextView filterSure;
    private HomeThreeFilterAdapter homeThreeFilterAdapter;
    private LinearLayoutManager linearLayoutManager;
    //筛选关键字NAME
    private List<String> filtelist;
    //筛选关键字ID
    private List<String> filterIdList;
    private List<String> newlist;

    //筛选数据
    private List<HomeThreeBean.ConditionsBean> conditionsBeanList;
    //筛选新数据
    private List<HomeThreeBean.ConditionsBean> newconditionsBeanList;
    //关键词
    String currProTypeId;

    /**
     * 商品属性选择的popupwindow
     */
    public FilterFragments(Context context, String edditx) {
        super(context);
        EventBus.getDefault().register(this);
        currProTypeId = edditx;
        init(context);
    }

    public FilterFragments(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        EventBus.getDefault().register(this);
        init(context);
    }

    public FilterFragments(@NonNull Context context, @Nullable AttributeSet attrs,
                           @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
        init(context);
    }

    private void init(final Context context) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_goods_details, null);
        selectionList = (RecyclerView) contentView.findViewById(R.id.selection_list);
        filterReset = (TextView) contentView.findViewById(R.id.filter_reset);
        filterSure = (TextView) contentView.findViewById(R.id.filter_sure);

        contentView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                }
                return true;
            }
        });
        getData();
        this.addView(contentView);
        ColorDrawable dw = new ColorDrawable(00000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        filtelist = new ArrayList<>();
        newlist = new ArrayList<>();
        filterIdList = new ArrayList<>();

        //重置
        filterReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < conditionsBeanList.size(); i++) {
                    for (int j = 0; j < conditionsBeanList.get(i).getList().size(); j++) {
                        conditionsBeanList.get(i).getList().get(j).setIscheck(false);
                    }
                }
                HomeThreeSelectPresenter.getSingleTon().clearSelect();
                homeThreeFilterAdapter.notifyDataSetChanged();
            }
        });
        //确认
        filterSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (HomeThreeSelectPresenter.getSingleTon().getLableList().size() == 0 &&
                        HomeThreeSelectPresenter.getSingleTon().getPriceRangesList().size() == 0 &&
                        HomeThreeSelectPresenter.getSingleTon().getProductAgesList().size() == 0 &&
                        HomeThreeSelectPresenter.getSingleTon().getProductDurationsList().size() == 0) {
                    RxToast.warning("至少选一个筛选条件");
                    return;
                }
                EventBus.getDefault().post(new SecondEventil(true));
                EventBus.getDefault().post(new FinishEventUtil());
            }
        });
    }

    private void getData() {
        conditionsBeanList = new ArrayList<>();
        newconditionsBeanList = new ArrayList<>();

        OkGo.<String>post(ServiceApi.HOME_SECOND_URL)
                .tag(this)
                .headers("Area", "322")
                .params("typeId", currProTypeId)
                .params("pageNum", "1")
                .params("pageSize", "10")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Gson gson = new Gson();
                                HomeThreeBean homeThreeBean = gson.fromJson(s, HomeThreeBean.class);
                                if (homeThreeBean.getConditions() != null) {
                                    conditionsBeanList.addAll(homeThreeBean.getConditions());
                                    showConditions();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    /**
     * 用来接收筛选框中的消息
     */
    @Subscribe
    public void onEventMainThread(Eventil event) {
        String filter = event.getProviceName();
        String filterId = event.getFilterId();
        System.out.println("接收到筛选框传递到fragment中的消息了==" + filter);
        if (filtelist.size() <= 0) {
            filtelist.clear();
            filterIdList.clear();
            filterIdList.add(filterId);
            filtelist.add(filter);
        } else {
            if (!filtelist.contains(filter)) {
                filtelist.add(filter);
                filterIdList.add(filterId);
            } else {
                filtelist.remove(filter);
                filterIdList.remove(filterId);
            }
        }
    }

    private void showConditions() {
        linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        selectionList.setLayoutManager(linearLayoutManager);
        homeThreeFilterAdapter = new HomeThreeFilterAdapter(R.layout.item_home_three_filter, conditionsBeanList);
        selectionList.setAdapter(homeThreeFilterAdapter);

    }


}
