package com.padacn.xmgoing.util;

import android.content.Context;

import com.android.tu.loadingdialog.LoadingDailog;

/**
 * Created by Administrator on 2018/7/3 0003.
 */

public class CommonDialogUtil {
    public static LoadingDailog.Builder loadBuilder;
    public static LoadingDailog dialog;

    public static void showDialog(Context context) {
        loadBuilder = new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        dialog = loadBuilder.create();
        dialog.show();
    }

    public static void hideDialog() {
        dialog.cancel();
    }

}
