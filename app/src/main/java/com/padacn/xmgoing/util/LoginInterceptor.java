package com.padacn.xmgoing.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.utils.IOUtils;
import com.lzy.okgo.utils.OkLogger;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.LoginActivity;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/19 0019.
 */

public class LoginInterceptor implements Interceptor {
    private static final String TAG = "LoginInterceptor";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private Context context;

    public LoginInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Headers headers = clone.headers();
            String auth;
            String TOKEN;
            for (int i = 0, count = headers.size(); i < count; i++) {
                if (headers.name(i).equals("AUTH-FAILURE")) {
                    auth = headers.value(i);
                    if (auth.equals("1")) {
                        //启用广播
                        Intent intent = new Intent("LOGIN");
                        //发送广播--标准广播
                        context.sendBroadcast(intent);

                       /* Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);*/
                    }
                }
            }

        } catch (Exception e) {

            throw e;
        }

        return response;
    }
}
