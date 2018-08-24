package com.padacn.xmgoing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.GoodsDetailsActivity;


public class MyWebView extends WebView {
    private static final String TAG = "X5WebView";
    private Context context;
    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };


    public MyWebView(Context arg0, AttributeSet attrs) {
        super(arg0, attrs);
        this.context = arg0;
        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        addJavascriptInterface(new AndroidtoJs(), "Android");
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private Handler handler = new Handler();
    private Runnable runnable;

    private class AndroidtoJs {
        @JavascriptInterface
        public void bodyHeigh(String bodyHeight) {
           /* Log.e(TAG, "bodyHeigh:11 " + bodyHeight);
            final int newHeight = (int) (Integer.parseInt(bodyHeight) * getResources().getDisplayMetrics().density);
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (getLayoutParams() instanceof LinearLayout.LayoutParams) {
                        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) getLayoutParams();
                        linearParams.height = newHeight;
                        setLayoutParams(linearParams);
                    }
                }
            };
            if (null != handler) {
                handler.postDelayed(runnable, 50);
            }*/

        }

        @JavascriptInterface
        public void seeGoodDetail(String goodsId) {
            Log.e(TAG, "goodsId: " + goodsId);
            Bundle bundle = new Bundle();
            bundle.putString("pid", goodsId);
            bundle.putBoolean("panic", false);
            BaseActivity.navigate(context, GoodsDetailsActivity.class, bundle);
        }
    }

}
