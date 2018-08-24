package com.padacn.xmgoing.wxapi;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.padacn.xmgoing.activity.BaseActivity;
import com.padacn.xmgoing.activity.MainHomeActivity;
import com.padacn.xmgoing.activity.PaySuccessActivity;
import com.padacn.xmgoing.api.Constans;
import com.padacn.xmgoing.util.SharePreferencesUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.vondear.rxtools.view.RxToast;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class WXEntryActivity extends WXCallbackActivity {

   /* @Override
    public void onResp(BaseResp resp) {
        *//*switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_USER_CANCEL: //发送取消
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        RxToast.error("微信登录失败");
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        break;
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED: //发送被拒绝
                break;
            default://发送返回

                break;
        }
*//*
    }*/
}
