package com.ali.zhihu.ui.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2018/2/2.
 */

public class ImageLoaderUtil {
    public static void GlideImageLoader(Context context, String path, ImageView imageView){
        Glide.with(context)
                .load(path)
                .into(imageView);
    }

    public static void GlideImageLoader(Context context, int path, ImageView imageView){
        Glide.with(context)
                .load(path)
                .into(imageView);
    }
    public static class GlideBannerImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            GlideImageLoader(context,(String)path,imageView);
        }
    }
}
