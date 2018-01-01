package com.guaju.adoulive.utils;

import android.widget.Toast;

import com.guaju.adoulive.app.AdouApplication;

/**
 * Created by guaju on 2018/1/1.
 */

public class ToastUtils {
    static Toast toast;

    public static void show(String str) {
        if (toast == null) {
            toast = Toast.makeText(AdouApplication.getApp().getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }

}
