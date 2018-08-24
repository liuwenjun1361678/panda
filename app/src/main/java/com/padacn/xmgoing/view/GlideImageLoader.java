package com.padacn.xmgoing.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.padacn.xmgoing.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by geyifeng on 2017/6/4.
 */

public class GlideImageLoader extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.default_image)
                //异常占位图(当加载异常的时候出现的图片)
                .error(R.mipmap.default_image)
                //禁止Glide硬盘缓存缓存
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }

}
