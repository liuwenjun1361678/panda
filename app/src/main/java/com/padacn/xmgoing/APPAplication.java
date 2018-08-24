package com.padacn.xmgoing;

import android.app.Application;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.oubowu.slideback.ActivityHelper;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.util.LoginInterceptor;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.padacn.xmgoing.util.loading.LoadingAndRetryManager;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.RxTool;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * 基类
 * Created by 46009 on 2018/4/25.
 */

public class APPAplication extends Application {
    private static APPAplication singleton;
    private ActivityHelper mActivityHelper;

    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        singleton = this;
        initBugly();
        initSlideBack();
        ARouter.init(this);
        initX5();
        initOkGo();
        initUmeng();
//        ScreenAdapterTools.init(this);
        RxTool.init(this);
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.common_error_view;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.common_loading_view;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.common_connect_error_view;
    }

    private void initUmeng() {
        UMConfigure.init(this, "5b58711d8f4a9d2774000160"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
    }

    private void initX5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {

            }
        };
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initOkGo() {


        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        LoginInterceptor loginInterceptor = new LoginInterceptor(this);
        builder.addInterceptor(loginInterceptor);

        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));
        //超时时间设置，默认60秒
        builder.readTimeout(Constans.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(Constans.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(Constans.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        HttpHeaders headers = new HttpHeaders();
        headers.put(Constans.token,
                SharePreferencesUtil.getStringSharePreferences(this, Constans.token, ""));
        headers.put("logintype", Constans.LOGINTYPES);
        headers.put("Area", SharePreferencesUtil.getStringSharePreferences(this, Constans.AREA, "322"));
        headers.put("dev_id", RxDeviceTool.getUniqueSerialNumber());

//        headers.put("Content-Type", "application/json");
        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .addCommonHeaders(headers)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                          //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0;                    //全局公共头

    }


    private void initSlideBack() {
        mActivityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(mActivityHelper);

    }

    public static ActivityHelper getActivityHelper() {
        return singleton.mActivityHelper;
    }


    public static APPAplication getInstance() {

        return singleton;
    }

    private void initBugly() {

        Beta.autoInit = true;
        /* Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        */
        Bugly.init(getApplicationContext(), "eff757ad6c", false);

        //OkGo初始化
        OkGo.getInstance().init(this);

    }

    {
        PlatformConfig.setWeixin("wx6c18bdc67e076407", "32a3d1af8be7132844d419a84eb80edc");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("97849595", "3909b460227bcc83a8c04200e49e6556", "http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("1106929758", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965", "5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy", "h7p2pjbzkkxt02a");

    }
}
