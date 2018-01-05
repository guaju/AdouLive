package com.guaju.adoulive.httputils;

/**
 * Created by guaju on 2018/1/5.
 */


import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by guaju on 2017/10/30.
 * 2.okhttputils工具类
 */

public class OkHttpUtils {
    private static final String TAG = "OkHttpUtils";
    /**
     * 需要定义至少两个方法
     * get
     * postString
     * 去获取数据
     * 需要使用单例模式去把我这个类单例化
     */
    private static OkHttpUtils mOkUtils;
    private final OkHttpClient ok;
    public  static Gson gson;




    private OkHttpUtils() {
        //创建真正的操作类OkHttpClient
        ok = new OkHttpClient();
        gson=new Gson();

    }

    public static OkHttpUtils getInstance() {
        //双重判断+同步代码块
        if (mOkUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (mOkUtils == null) {
                    mOkUtils = new OkHttpUtils();
                }
            }
        }
        return mOkUtils;
    }
    //定义一个get方法

    /**
     * @param url
     * @param params ?username=xxxx&password=xxx
     */

    public void getString(String url, Map<String, String> params, Callback mCallBack) {
        String path = url;
        String newPath = "";
        //concat 往后边追加

        //当用户传入null或者传了一个空的map
        if (params != null && !params.isEmpty()) {
            path+=("?");
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> map : entries) {
                path+=(map.getKey() + "=" + map.getValue()).concat("&");
            }
            //把拼接好的网站去掉最后一个"&"符号
            newPath = path.substring(0, path.length() - 1);
        }
        //调用ok的get请求
        Request request = new Request.Builder()
                .url(newPath==""?url:newPath)//声明网访问的网址
                .get() //声明我是get请求，如果不写默认就是get
                .build();  //创建request

        Call call = ok.newCall(request);

//        同步execute、异步enqueue
        //这里的同步是耗时的，
        // 而且ok也没有为我们开启子线程，
        // 如果你用同步方法的话，需要开启子线程
        call.enqueue(mCallBack);


    }

    //定义一个get方法
    public void getString(String url, final StringCallBack callBack) {
        //调用ok的get请求
        final Request request = new Request.Builder()
                .url(url)//声明网访问的网址
                .get() //声明我是get请求，如果不写默认就是get
                .build();  //创建request

        final Call call = ok.newCall(request);

//        同步execute、异步enqueue
        //这里的同步是耗时的，
        // 而且ok也没有为我们开启子线程，
        // 如果你用同步方法的话，需要开启子线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = null;
                    response = call.execute();
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        //调用者只需要实现provide方法就能拿到这个String了
                        callBack.provideData(string);

                    }else{
                        callBack.provideData(response.body().string());
                    }
                }catch (IOException e){
                    Logger.e( e.getMessage() );
                }


            }
        });
        thread.start();
    }
    public  void postString(String url, Map<String,String> map, Callback callback){
        //数据的传输 区别于多媒体传输
        FormBody.Builder builder=new FormBody.Builder();

        if (map!=null&&!map.isEmpty()) {
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> m : entries) {
                builder.add(m.getKey(), m.getValue());
            }
            FormBody fb = builder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(fb)//请求体
                    .build();
            Call call = ok.newCall(request);
            call.enqueue(callback);
        } else{
            FormBody fb = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(fb)//请求体
                    .build();
            Call call = ok.newCall(request);
            call.enqueue(callback);
        }
        //使用异步方法
    }

    //获取javabean工具类
    public void  getBean(String url, Map<String, String> params,CustomCallBack mCallBack){
        getString(url,params,mCallBack);
    }

    public  void postBean(String url, Map<String, String> params,CustomCallBack mCallBack){
        postString(url,params,mCallBack);
    }

}


