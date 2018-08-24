package com.padacn.xmgoing.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.animation.Attention.Swing;
import com.flyco.animation.BounceEnter.BounceEnter;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.callback.OnClickBuyListenter;
import com.padacn.xmgoing.callback.OnClickDeleteListenter;
import com.vondear.rxtools.view.RxToast;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

public class GoodsExplainDialog extends BaseDialog<GoodsExplainDialog> {
    private Context mContext;
    private RelativeLayout buyRl;
    private RelativeLayout cacelRl;
    private TextView titleText;
    private TextView contantText;
    private TextView priceText;

    private String title;
    private String contant;
    private String price;

    public GoodsExplainDialog(Context context, String title, String contant, String price) {
        super(context);
        this.mContext = context;
        this.contant = contant;
        this.price = price;
        this.title = title;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new BounceEnter());
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_goods_explain, null);
        buyRl = inflate.findViewById(R.id.dialog_titke_buy);
        cacelRl = inflate.findViewById(R.id.dialog_titke_cancle);
        titleText = inflate.findViewById(R.id.dialog_titke_name);
        contantText = inflate.findViewById(R.id.dialog_titke_contant);
        priceText = inflate.findViewById(R.id.dialog_titke_price);
        titleText.setText(title);
        contantText.setText(contant);
        priceText.setText(price);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        cacelRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        buyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBuyListenter.onItemClick();
            }
        });
    }

    // 删除接口的方法
    private OnClickBuyListenter onClickBuyListenter = null;

    public void setOnClickBuyListenter(OnClickBuyListenter listener) {
        this.onClickBuyListenter = listener;
    }
}
