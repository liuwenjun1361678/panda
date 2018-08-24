package com.padacn.xmgoing.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.ExCouponsCenterAdapter;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.view.CustomDayView;
import com.padacn.xmgoing.view.CustomToolBar;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 46009 on 2018/5/7.
 */

public class MyFootprintActivity extends BaseActivity {
    @BindView(R.id.mine_my_order_bar)
    CustomToolBar mineMyOrderBar;
    @BindView(R.id.mine_my_footprint_left)
    ImageView mineMyFootprintLeft;
    @BindView(R.id.mine_my_footprint_time)
    TextView mineMyFootprintTime;
    @BindView(R.id.mine_my_footprint_right)
    ImageView mineMyFootprintRight;
    @BindView(R.id.calendar_view)
    MonthPager calendarView;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.mine_my_footprint_content)
    CoordinatorLayout mineMyFootprintContent;

    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarDate currentDate;

    private ExCouponsCenterAdapter exCouponsCenterAdapter;


    private Context context;

    private Banner exCenterBanner;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_footprint;
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
        context = this;
        mineMyOrderBar.setStyle("足迹", Color.parseColor("#f8d948"));
        initCurrentDate();
        initCalendarView();
        calendarView.setViewHeight(Utils.dpi2px(context, 270));

        list.setHasFixedSize(true);
        //这里用线性显示 类似于listview

        list.setLayoutManager(new LinearLayoutManager(this));
        List<ListActivityModel> datas = new ArrayList<>();
        ListActivityModel model;
        for (int i = 0; i < 10; i++) {
            model = new ListActivityModel();
            datas.add(model);
        }
//        exCouponsCenterAdapter = new ExCouponsCenterAdapter(R.layout.item_ex_coupons_center, datas);
        View headerView = getHeaderView();
//        exCouponsCenterAdapter.addHeaderView(headerView);
//        list.setAdapter(exCouponsCenterAdapter);

    }


    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.activity_coupons_center_banner, (ViewGroup) list.getParent(), false);
        exCenterBanner = view.findViewById(R.id.ex_center_banner);
        exCenterBanner.setBackgroundResource(R.mipmap.banner1);
        return view;
    }

    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        mineMyFootprintTime.setText(currentDate.getYear() + "年" + currentDate.getMonth() + "");
    }

    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.activity_mine_footprint_customday);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
                list.scrollToPosition(0);
            }
        });
        initMarkData();
        initMonthPager();
    }


    //使用此方法回调日历点击事件
    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                //your code
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示上一个月 ， 1表示下一个月
                calendarView.selectOtherMonth(offset);
            }
        };
    }


    //使用此方法初始化日历标记数据

    private void initMarkData() {
        HashMap markData = new HashMap<>();
        //1表示红点，0表示灰点
        markData.put("2017-8-9", "1");
        markData.put("2017-7-9", "0");
        markData.put("2017-6-9", "1");
        markData.put("2017-6-10", "0");
        calendarAdapter.setMarkData(markData);
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        calendarView.setAdapter(calendarAdapter);
        calendarView.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        calendarView.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        calendarView.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    mineMyFootprintTime.setText(date.getYear() + date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @OnClick({R.id.mine_my_footprint_left, R.id.mine_my_footprint_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_my_footprint_left:
                calendarView.setCurrentItem(calendarView.getCurrentPosition() - 1);
                break;
            case R.id.mine_my_footprint_right:
                calendarView.setCurrentItem(calendarView.getCurrentPosition() + 1);
                break;
        }
    }
}
