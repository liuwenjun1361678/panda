package com.padacn.xmgoing.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.padacn.xmgoing.R;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxDeviceTool;

/**
 * Created by Administrator on 2018/5/17 0017.
 */

public class CommonUtil {
    private static final String TAG = "CommonUtil";

    /**
     * 搜索关键字标红
     *
     * @param title
     * @param keyword
     * @return
     */
    public static String matcherSearchTitle(String title, String keyword) {
        String content = title;
        String wordReg = "(?i)" + keyword;//用(?i)来忽略大小写
        StringBuffer sb = new StringBuffer();
        Matcher matcher = Pattern.compile(wordReg).matcher(content);
        while (matcher.find()) {
            //这样保证了原文的大小写没有发生变化
            matcher.appendReplacement(sb, "<font color=\"#ff0014\">" + matcher.group() + "</font>");
        }
        matcher.appendTail(sb);
        content = sb.toString();
        //如果匹配和替换都忽略大小写,则可以用以下方法
        //content = content.replaceAll(wordReg,"<font color=\"#ff0014\">"+keyword+"</font>");
        return content;
    }


    public static void loadRlImage(Context context, String url, final RelativeLayout relativeLayout, int w, int h) {

        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                relativeLayout.setBackground(resource);

            }
        };
        RequestOptions options = new RequestOptions()
                //异常占位图(当加载异常的时候出现的图片)
                .error(R.mipmap.default_image)
                .centerCrop()
                .dontAnimate()
                .skipMemoryCache(true)
                //禁止Glide硬盘缓存缓存
                .override(w, h)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options).into(simpleTarget);
    }


    public static void loadGif(Context context, int ImageId, ImageView imageView) {
    }

    public static void loadImage(Context context, String url, final ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.default_image)
                //异常占位图(当加载异常的时候出现的图片)
                .error(R.mipmap.default_image)
                .centerCrop()
                .skipMemoryCache(true)
                .dontAnimate()
                //禁止Glide硬盘缓存缓存
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void loadMemoryImage(Context context, String url, final ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.default_image)
                //异常占位图(当加载异常的时候出现的图片)
                .error(R.mipmap.default_image)
                .centerCrop()
                .skipMemoryCache(false)
                .dontAnimate()
                //禁止Glide硬盘缓存缓存
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * path转uri
     */
    public static Uri getUri(String path, Context context) {
        Uri uri = null;
        if (path != null) {
            path = Uri.decode(path);
            Log.d(TAG, "path2 is " + path);
            ContentResolver cr = context.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns._ID},
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
// set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
//do nothing
            } else {
                Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                Log.d(TAG, "uri_temp is " + uri_temp);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri;
    }
}
