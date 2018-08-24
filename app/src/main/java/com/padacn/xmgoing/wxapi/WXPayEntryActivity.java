package com.padacn.xmgoing.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.PaySuccessActivity;
import com.padacn.xmgoing.api.Constans;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.view.RxToast;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constans.APP_ID, false);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                RxToast.success("支付成功");
                BaseActivity.navigate(WXPayEntryActivity.this, PaySuccessActivity.class);
            } else {
                RxToast.error("支付失败");
            }
            finish();
        }
    }
}
