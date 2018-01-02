package com.guaju.adoulive.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guaju.adoulive.app.AdouApplication;

/**
 * Created by guaju on 2018/1/2.
 */

public class ImageUtils {
    static  ImageUtils   imageUtils=new ImageUtils();
    public static ImageUtils getInstance(){
        return imageUtils;
    }
    public  void loadCircle(int resid, ImageView iv) {
        Glide.with(AdouApplication.getApp())
                .load(resid)
                .apply(RequestOptions.circleCropTransform())
                .into(iv);
    }

    public  void loadCircle(String path, ImageView iv) {
        Glide.with(AdouApplication.getApp())
                .load(path)
                .apply(RequestOptions.circleCropTransform())
                .into(iv);
    }
}
