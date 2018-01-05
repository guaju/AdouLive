package com.guaju.adoulive.httputils;


import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by guaju on 2017/10/31.
 * 自定义回调
 */

public abstract  class CustomCallBack implements Callback {
    private static final String TAG = "CustomCallBack";
    Class mClazz;

    public CustomCallBack(Class mClazz) {
        this.mClazz = mClazz;
    }


    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "onFailure: "+e.getMessage() );
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
         onMyResponse(response,mClazz);
    }

    public abstract void onMyResponse(Response response,Class clazz);
}