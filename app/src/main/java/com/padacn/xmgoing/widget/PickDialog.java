package com.padacn.xmgoing.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.padacn.xmgoing.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2018/6/16 0016.
 */

public class PickDialog extends AlertDialog {


    private Context context;
    private LinearLayout layoutContent;
    private TextView pick_cancel;
    private TextView man;
    private TextView woman;

    public PickDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picker);
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();

        layoutContent = (LinearLayout) findViewById(R.id.layout_content);
        layoutContent.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.75), LinearLayout.LayoutParams.WRAP_CONTENT));

        man = (TextView) findViewById(R.id.pick_man);
        woman = (TextView) findViewById(R.id.pick_woman);
        pick_cancel = (TextView) findViewById(R.id.pick_cancel);
        pick_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setTextName(String text1, String text2) {
        man.setText(text1);
        woman.setText(text2);
    }

    public void selectMan(View.OnClickListener onClickListener) {
        man.setOnClickListener(onClickListener);
    }

    public void selectWoMan(View.OnClickListener onClickListener) {
        woman.setOnClickListener(onClickListener);
    }


}
