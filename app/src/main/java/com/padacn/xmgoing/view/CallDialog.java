package com.padacn.xmgoing.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vondear.rxtools.view.dialog.RxDialog;

import com.padacn.xmgoing.R;

/**
 * Created by 46009 on 2018/5/9.
 */

public class CallDialog extends RxDialog{
    private TextView textView;
    private TextView textViewSure;
    private TextView textViewCancel;


    public CallDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();

    }

    public CallDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }
    public CallDialog(Context context) {
        super(context);
        initView();
    }

    public CallDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_call_number, null);
        textView = (TextView) dialogView.findViewById(R.id.mine_call_number);
        textViewSure= (TextView) dialogView.findViewById(R.id.call_sure);
        textViewCancel = (TextView) dialogView.findViewById(R.id.call_cancel);
        setContentView(dialogView);
    }


    public void setNumber(String number) {
        textView.setText(number);
    }

    public TextView getNumberView() {
        return textView;
    }

    public TextView getSureView() {
        return textViewSure;
    }
    public TextView getCancelView() {
        return textViewCancel;
    }


    public void setSureListener(View.OnClickListener sureListener) {
        textViewSure.setOnClickListener(sureListener);
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        textViewCancel.setOnClickListener(cancelListener);
    }
}
