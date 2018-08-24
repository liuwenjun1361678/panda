package com.padacn.xmgoing.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.util.CommonUi;
import com.padacn.xmgoing.view.CustomToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class RufundActivity extends BaseActivity {
    @BindView(R.id.refund_bar)
    CustomToolBar refundBar;
    @BindView(R.id.order_id)
    TextView orderId;
    @BindView(R.id.write_off_id)
    TextView writeOffId;
    @BindView(R.id.tiket_name)
    TextView tiketName;
    @BindView(R.id.travle_time)
    TextView travleTime;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.refund_rl)
    RelativeLayout refundRl;


    private String sn;
    private String writeId;
    private String tiketNameString;
    private String travleDate;
    private String orderDate;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_refund;
    }


    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = this.getIntent().getExtras();
        sn = bundle.getString("sn");
        writeId = bundle.getString("writeId");
        tiketNameString = bundle.getString("tiketName");
        travleDate = bundle.getString("travleDate");
        orderDate = bundle.getString("orderDate");
        showData();
    }

    private void showData() {

        orderId.setText(sn);
        writeOffId.setText(writeId);
        tiketName.setText(tiketNameString);
        travleTime.setText(travleDate);
        orderTime.setText(orderDate);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.refund_rl)
    public void onViewClicked() {
        CommonUi.diallPhone(Constans.PhoneNumber, RufundActivity.this);

    }
}
