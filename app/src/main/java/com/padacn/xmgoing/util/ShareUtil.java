package com.padacn.xmgoing.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.padacn.xmgoing.R;
import com.padacn.xmgoing.activity.GoodsDetailsActivity;
import com.padacn.xmgoing.adapter.MineClomnAdapter;
import com.padacn.xmgoing.adapter.MyShareAdapter;
import com.padacn.xmgoing.model.ShareBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vondear.rxtools.RxDataTool;
import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class ShareUtil {
    private static Handler mHandler = new Handler();
    private static ShareUtil mInstance;
    private static Object mLock = new Object();
    private static Context mContext;

    private int[] shareIconId = new int[]{R.mipmap.share_icon_wxhy, R.mipmap.share_icon_pyq,
            R.mipmap.share_icon_qq, R.mipmap.share_icon_qqkj, R.mipmap.share_icon_weibo, R.mipmap.share_icon_fuzhi};
    private String[] shareTitle = new String[]{"微信好友", "朋友圈", "QQ好友", "QQ空间", "新浪微博", "复制链接"};

    private List<ShareBean> shareBeanList;
    private MineClomnAdapter mineClomnAdapter;
    private EasyPopup easySharePopup;
    private MyShareAdapter myShareAdapter;

    public static ShareUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new ShareUtil();
                    mContext = context;
                }
            }
        }
        return mInstance;
    }


    public void ShareApp(SHARE_MEDIA share_media, String shareTitle, String imageUrl, String contant, String currShareUrl) {
        UMWeb web = new UMWeb(currShareUrl);
        if (!RxDataTool.isEmpty(shareTitle)) {
            web.setTitle(shareTitle);
        }

        if (!RxDataTool.isEmpty(imageUrl)) {
            web.setThumb(new UMImage(mContext, imageUrl));
        }
        if (!RxDataTool.isEmpty(contant)) {
            web.setDescription(contant);
        }
        new ShareAction((Activity) mContext).withMedia(web)
                .setPlatform(share_media)//传入平台
                .setCallback(shareListener).share();
    }

    private static UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();

        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };
}
