package com.padacn.xmgoing.service;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.LoginActivity;
import com.padacn.xmgoing.activity.MainHomeActivity;
import com.padacn.xmgoing.activity.MySettingActivity;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.callback.OnClickSureListenter;
import com.padacn.xmgoing.util.AppManager;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.common.GlideCacheUtil;
import com.padacn.xmgoing.view.dialog.ComonSureDialog;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;

/**
 * Created by Administrator on 2018/6/19 0019.
 */

public class LoginReceiver extends BroadcastReceiver {
    private static final String TAG = "LoginReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        //对话框
//        AppManager.getInstance().AppExit();
        Intent intent1 = new Intent(context, MainHomeActivity.class);
        //在广播中启动活动，需要添加如下代码
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra(Constans.isAgainLogin, true);
        context.startActivity(intent1);
        SharePreferencesUtil.setBooleanSharePreferences(context, Constans.isAlreadyLogin, false);
    }
}
