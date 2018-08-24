package com.padacn.xmgoing.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.padacn.xmgoing.view.CallDialog;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class CommonUi {
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void diallPhone(final String phoneNum, final Context context) {
        final CallDialog callDialog = new CallDialog(context);
        callDialog.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                context.startActivity(intent);
            }
        });
        callDialog.getNumberView().setText(phoneNum);
        callDialog.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDialog.cancel();
            }
        });

        callDialog.show();
    }

}
