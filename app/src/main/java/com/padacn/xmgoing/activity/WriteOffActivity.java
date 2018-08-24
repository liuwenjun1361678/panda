package com.padacn.xmgoing.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.model.OrderDetailsBean;
import com.padacn.xmgoing.util.DoubleArith;
import com.padacn.xmgoing.view.CustomToolBar;
import com.vondear.rxtools.RxDataTool;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/4 0004.
 */

public class WriteOffActivity extends BaseActivity {
    @BindView(R.id.write_off_bar)
    CustomToolBar writeOffBar;
    @BindView(R.id.write_off_sellname)
    TextView writeOffSellname;
    @BindView(R.id.write_off_title)
    TextView writeOffTitle;
    @BindView(R.id.titke_type)
    TextView titkeType;
    @BindView(R.id.travle_people_num)
    TextView travlePeopleNum;
    @BindView(R.id.travle_data)
    TextView travleData;
    @BindView(R.id.write_off_code)
    TextView writeOffCode;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_date)
    TextView orderDate;
    @BindView(R.id.order_price)
    TextView orderPrice;
    @BindView(R.id.service_number)
    TextView serviceNumber;
    @BindView(R.id.travle_people_num_rl)
    LinearLayout travlePeopleNumRl;
    private OrderDetailsBean.DataBean.ListBeanXX.ListBeanX data;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_write_off;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        writeOffBar.setStyle("核销详情", Color.parseColor("#f8d948"));
        Intent intent = this.getIntent();
        data = (OrderDetailsBean.DataBean.ListBeanXX.ListBeanX) intent.getSerializableExtra("data");
        writeOffSellname.setText(data.getSellerName());
        writeOffTitle.setText(data.getProName());
        titkeType.setText(data.getTypeName());
        if (RxDataTool.isNullString(data.getExcursion())) {
            travlePeopleNumRl.setVisibility(View.GONE);
        } else {
            travlePeopleNumRl.setVisibility(View.VISIBLE);
            travlePeopleNum.setText(data.getExcursion());
        }
        if (RxDataTool.isEmpty(data.getUseDay())) {
            travleData.setText("无时间限制");
        } else {
            travleData.setText(data.getUseDay());
        }
        writeOffCode.setText(data.getVerification());
        orderNum.setText(data.getSn());
        orderDate.setText(data.getCreateTime());
        orderPrice.setText(String.valueOf(DoubleArith.mul(data.getPrice(), (double) data.getProCount())));
        serviceNumber.setText(Constans.PhoneNumber);
    }
}
