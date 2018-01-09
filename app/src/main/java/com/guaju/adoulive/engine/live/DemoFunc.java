package com.guaju.adoulive.engine.live;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xkazerzhang on 2017/6/22.
 */
public class DemoFunc {

    public static String getTimeStr(long uTime){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return formatter.format(new Date(uTime));
    }

    public static int getIntValue(String strData, int defValue){
        try{
            return Integer.parseInt(strData);
        }catch (NumberFormatException e){
            return defValue;
        }
    }

    // 字符串截断
    public static String getLimitString(String source, int length){
        if (null == source){
            return "";
        }else if (source.length()>length){
            return source.substring(0, length)+"...";
        }else {
            return source;
        }
    }

    public static int getIntValue(String strData){
        return getIntValue(strData, 0);
    }
}
