package com.padacn.xmgoing.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.animation.Attention.Swing;
import com.flyco.animation.BounceEnter.BounceEnter;
import com.flyco.dialog.widget.base.BaseDialog;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.callback.OnClickBuyListenter;
import com.padacn.xmgoing.callback.OnClickSureListenter;
import com.vondear.rxtools.RxDataTool;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

public class ComonSureDialog extends BaseDialog<ComonSureDialog> {
    private Context mContext;
    private RelativeLayout SureRl;
    private RelativeLayout cacelRl;
    private TextView contantText;
    private String contant;

    public ComonSureDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ComonSureDialog(Context context, String contant) {
        super(context);
        this.mContext = context;
        this.contant = contant;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new BounceEnter());
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_common_sure, null);
        SureRl = inflate.findViewById(R.id.dialog_ccommon_sure);
        cacelRl = inflate.findViewById(R.id.dialog_ccommon_cancel);
        contantText = inflate.findViewById(R.id.dialog_ccommon_contant);
        if (!RxDataTool.isEmpty(contant)) {
            contantText.setText(contant);
        }
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
        SureRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSureListenter.onSureClick();
            }
        });
    }

    // 删除接口的方法
    private OnClickSureListenter onClickSureListenter = null;

    public void setOnClickSureListenter(OnClickSureListenter listener) {
        this.onClickSureListenter = listener;
    }
}
