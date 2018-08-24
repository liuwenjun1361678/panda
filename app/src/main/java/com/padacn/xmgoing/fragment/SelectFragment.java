package com.padacn.xmgoing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.SelectDateAdapter;
import com.padacn.xmgoing.model.CaladerDataBean;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.presenter.MealEventil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SelectFragment extends BaseFragment {
    private static final String TAG = "SelectFragment";
    public static final String ARG_PAGE = "ARG_PAGE";
    @BindView(R.id.select_data_rc)
    RecyclerView selectDataRc;
    Unbinder unbinder;
    private int mPage;
    private String mTitle;

    //用户选择了具體日期的月份下标
    private int userMonthPosition;
    //用户选择了的月份坐标
    private int positionMonth;

    private int lastPosition = -1;

    private int userSelect;
    //用户是否已经选择
    private boolean userSelected;
    private int currMonthDays;
    private int getFirstDayWeekPosition;
    private int currStartYear;
    //套餐开始的月份
    private int currStartMonth;
    //套餐结束的月份
    private int currEndMonth;

    private List<CaladerDataBean> caladerDataBeanList;
    private int currpositon;


    private GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean calendersBean;
    private SelectDateAdapter selectDateAdapter;

    public static SelectFragment getInstance(GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean calendersBean) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PAGE, calendersBean);
        SelectFragment sf = new SelectFragment();
        sf.setArguments(args);
        return sf;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.select_date;
    }

    @Override
    protected void initView() {
//        int s = Utils.getCurrMonthY("2018-05");
//        Bundle bundle = getArguments();
//        calendersBean = (GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean) bundle.getSerializable(ARG_PAGE);

        caladerDataBeanList = new ArrayList<>();
        calendersBean = HomeThreeSelectPresenter.getSingleTon().getSetMealData().get(positionMonth);
        currStartMonth = Utils.getCurrMonthY(String.valueOf(calendersBean.getMonth()));
        currStartYear = Utils.getCurrMonthX(String.valueOf(calendersBean.getMonth()));
        currMonthDays = Utils.getMonthDays(currStartYear, currStartMonth);
        getFirstDayWeekPosition = Utils.getFirstDayWeekPosition(currStartYear, currStartMonth, CalendarAttr.WeekArrayType.Monday);
        for (int i = 0; i < currMonthDays + getFirstDayWeekPosition; i++) {
            CaladerDataBean caladerDataBean = new CaladerDataBean();
            if (i >= getFirstDayWeekPosition) {
                caladerDataBean.setCurrDate(String.valueOf(i + 1 - getFirstDayWeekPosition));
                if ((i + 1 - getFirstDayWeekPosition) < 10) {
                    caladerDataBean.setCurrDataString(String.valueOf(calendersBean.getMonth()) + "-0" + (i + 1 - getFirstDayWeekPosition));
                } else {
                    caladerDataBean.setCurrDataString(String.valueOf(calendersBean.getMonth()) + "-" + (i + 1 - getFirstDayWeekPosition));
                }
                caladerDataBeanList.add(i, caladerDataBean);
            } else {
                caladerDataBean.setCurrDate("");
                caladerDataBean.setCurrDataString("");
                caladerDataBeanList.add(i, caladerDataBean);
            }
        }
        //設置当前月份数据
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 7);
        selectDataRc.setLayoutManager(gridLayoutManager);
        selectDateAdapter = new SelectDateAdapter(R.layout.item_select_date, caladerDataBeanList);
        selectDataRc.setAdapter(selectDateAdapter);
        //设置当前月份的下标
        selectDateAdapter.setPositionMonth(positionMonth, userMonthPosition, lastPosition);
        //传递当前本月第一天在其周的位置
        selectDateAdapter.setFirstDayWeekPosition(getFirstDayWeekPosition);
        selectDataRc.setNestedScrollingEnabled(false);
        // 设置布局管理器
        selectDateAdapter.setCanlenderData(calendersBean.getCanlenderDetails());
        selectDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (caladerDataBeanList.get(position).isClick()) {
                    selectDateAdapter.setCurrClick(position);
                    selectDateAdapter.notifyDataSetChanged();
                }
            }
        });
        selectDateAdapter.notifyDataSetChanged();
    }

  /*  private void setData(int position) {
        caladerDataBeanList.clear();
        calendersBean = HomeThreeSelectPresenter.getSingleTon().getSetMealData().get(position);
        //设置价格内容数据
        //calendersBean.getCanlenderDetails() 这里是空的？？？？嗯 你断电运行一下
        currStartMonth = Utils.getCurrMonthY(String.valueOf(calendersBean.getMonth()));
        currStartYear = Utils.getCurrMonthX(String.valueOf(calendersBean.getMonth()));
        currMonthDays = Utils.getMonthDays(currStartYear, currStartMonth);
        getFirstDayWeekPosition = Utils.getFirstDayWeekPosition(currStartYear, currStartMonth, CalendarAttr.WeekArrayType.Monday);
        Log.e(TAG, "initView: " + String.valueOf(calendersBean.getMonth()) + currStartMonth + currMonthDays + getFirstDayWeekPosition);
        for (int i = 0; i < currMonthDays + getFirstDayWeekPosition; i++) {
            CaladerDataBean caladerDataBean = new CaladerDataBean();
            if (i >= getFirstDayWeekPosition) {
                caladerDataBean.setCurrDate(String.valueOf(i + 1 - getFirstDayWeekPosition));
                if ((i + 1 - getFirstDayWeekPosition) < 10) {
                    caladerDataBean.setCurrDataString(String.valueOf(calendersBean.getMonth()) + "-0" + (i + 1 - getFirstDayWeekPosition));
                } else {
                    caladerDataBean.setCurrDataString(String.valueOf(calendersBean.getMonth()) + "-" + (i + 1 - getFirstDayWeekPosition));
                }
                caladerDataBeanList.add(i, caladerDataBean);
            } else {
                caladerDataBean.setCurrDate("");
                caladerDataBean.setCurrDataString("");
                caladerDataBeanList.add(i, caladerDataBean);
            }
        }

        //設置当前月份数据
        Log.e(TAG, "setData: " + HomeThreeSelectPresenter.getSingleTon().getSetMealData().get(position).getCanlenderDetails().size());

        // 设置布局管理器
        selectDateAdapter.setNewData(caladerDataBeanList);
        selectDateAdapter.setCanlenderData(HomeThreeSelectPresenter.getSingleTon().getSetMealData().get(position).getCanlenderDetails());
        selectDateAdapter.setFirstDayWeekPosition(getFirstDayWeekPosition);

    }*/

    /**
     * 设置当前月的原始数据
     */
    private void setCaladerData() {

    }

    // 接收函数
    @Subscribe
    public void onEventMainThread(MealEventil evenUtil) {
//        Log.e(TAG, "onEventMainThread: " + HomeThreeSelectPresenter.getSingleTon().getSetMealData().get(evenUtil.getPosition()).getMonth());
        positionMonth = evenUtil.getPosition();
        userMonthPosition = evenUtil.getUserMonthPosition();
        lastPosition = evenUtil.getLastPosition();
        if (selectDateAdapter != null) {
            selectDateAdapter.setPositionMonth(positionMonth, userMonthPosition, lastPosition);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//取消注册
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);  //注册
        return rootView;
    }
}