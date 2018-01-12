package com.guaju.adoulive.httputil;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by guaju on 2018/1/10.
 */

public class OkHttpHelper {
    OkHttpClient client;
    public Gson gson;
    Handler mainHandler;

    public interface OnRequestComplete<T> {
        void onSuccess(T t);

        void onEmpty();

        void onError();

        void onFailed();

        //需要服务器配合，当加载的数据为最终数据，或者所有数据时，才会触发（服务器可以提供字段，或者总数量）
        void onDataComplete();

    }

    private static OkHttpHelper mHelper;

    private OkHttpHelper() {
        //初始化操作
//        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient.Builder().build();
        gson = new Gson();
        Looper mainLooper = Looper.getMainLooper();
        //主线程的handler
        mainHandler = new Handler(mainLooper);
    }

    //获取实例
    public static OkHttpHelper getInstance() {
        if (mHelper == null) {
            synchronized (OkHttpHelper.class) {
                if (mHelper == null) {
                    mHelper = new OkHttpHelper();
                }
            }
        }
        return mHelper;
    }

    //提供方法

    /**
     * @param url               网址
     * @param onRequestComplete 网络请求完成之后的回调
     */
    public void getString(String url, final OnRequestComplete<String> onRequestComplete) {
        if (!url.startsWith("http")) {
            //提示网址不对，访问失败

            if (onRequestComplete != null) {
                onRequestComplete.onFailed();
            }
            return;
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .get()
//                .header()
                .build();

        Call call = client.newCall(request);
        //异步的方法，这个方法内部自己会开启子线程去加载数据，然后通过回调去做数据获取完处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String string = response.body().string();
                    if (TextUtils.isEmpty(string)) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onSuccess(string);
                            }
                        });
                    }


                } else {
                    //虽然已经连通到这个网址了，但是由于服务器的原因可能报了301，203，50几等等一些异常页面
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onError();
                        }
                    });
                }


            }
        });
        //同步方法，需要用户自己去开启子线程去处理
//        call.execute();


    }

    /**
     * @param url               网址
     * @param map               post请求传递的参数
     * @param onRequestComplete 网络请求完成之后的回调
     */
    public void postString(String url, @NonNull HashMap<String, String> map, final OnRequestComplete<String> onRequestComplete) {
        if (!url.startsWith("http")) {
            //提示网址不对，访问失败

            if (onRequestComplete != null) {
                onRequestComplete.onFailed();
            }
            return;
        }
        //准备请求体  （由于本app上传图片使用的7牛，所以不需要准备上传mutipart类型的文件了）
        FormBody.Builder fBuilder = new FormBody.Builder();
        FormBody formBody = null;
        //遍历map，把键值对添加进formbody当中
        if (map != null && !map.isEmpty()) {
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                fBuilder.add(entry.getKey(), entry.getValue());
            }
            formBody = fBuilder.build();
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .post(formBody)
//                .header()
                .build();

        Call call = client.newCall(request);
        //异步的方法，这个方法内部自己会开启子线程去加载数据，然后通过回调去做数据获取完处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String string = response.body().string();
                    if (TextUtils.isEmpty(string)) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onSuccess(string);
                            }
                        });
                    }


                } else {
                    //虽然已经连通到这个网址了，但是由于服务器的原因可能报了301，203，50几等等一些异常页面
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onError();
                        }
                    });
                }


            }
        });
    }

    /**
     * @param url               网址
     * @param onRequestComplete 网络请求完成之后的回调
     * @param clazz             要转化成的对象的   字节码文件（.class文件）
     */
    public void getObject(String url, final OnRequestComplete onRequestComplete, final Class clazz) {
        if (!url.startsWith("http")) {
            //提示网址不对，访问失败

            if (onRequestComplete != null) {
                onRequestComplete.onFailed();
            }
            return;
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .get()
//                .header()
                .build();

        Call call = client.newCall(request);
        //异步的方法，这个方法内部自己会开启子线程去加载数据，然后通过回调去做数据获取完处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        //把返回过来的json串转成bean
                        final Object obj = gson.fromJson(string, clazz);
                        if (obj == null) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onRequestComplete.onEmpty();
                                }
                            });
                        } else {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onRequestComplete.onSuccess(obj);
                                }
                            });
                        }

                    } else {
                        //当string为空的时候
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    }


                } else {
                    //虽然已经连通到这个网址了，但是由于服务器的原因可能报了301，203，50几等等一些异常页面
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onError();
                        }
                    });
                }


            }
        });
    }

    /**
     * @param url               网址
     * @param map               post要传递的参数
     * @param onRequestComplete 网络请求完成之后的回调
     * @param clazz             要转化成的对象的   字节码文件（.class文件）
     */
    public void postObject(String url, HashMap<String, String> map, final OnRequestComplete onRequestComplete, final Class clazz) {


        if (!url.startsWith("http")) {
            //提示网址不对，访问失败

            if (onRequestComplete != null) {
                onRequestComplete.onFailed();
            }
            return;
        }
        //准备请求体  （由于本app上传图片使用的7牛，所以不需要准备上传mutipart类型的文件了）
        FormBody.Builder fBuilder = new FormBody.Builder();
        FormBody formBody = null;
        //遍历map，把键值对添加进formbody当中
        if (map != null && !map.isEmpty()) {
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                fBuilder.add(entry.getKey(), entry.getValue());
            }
            formBody = fBuilder.build();
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .post(formBody)
//                .header()
                .build();


        Call call = client.newCall(request);
        //异步的方法，这个方法内部自己会开启子线程去加载数据，然后通过回调去做数据获取完处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        //把返回过来的json串转成bean
                        final Object obj = gson.fromJson(string, clazz);
                        if (obj == null) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onRequestComplete.onEmpty();
                                }
                            });
                        } else {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onRequestComplete.onSuccess(obj);
                                }
                            });
                        }

                    } else {
                        //当string为空的时候
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    }


                } else {
                    //虽然已经连通到这个网址了，但是由于服务器的原因可能报了301，203，50几等等一些异常页面
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onError();
                        }
                    });
                }


            }
        });
    }

    /**
     *
     * @param url
     * @param map
     * @param onRequestComplete
     * @param type    类似字节码文件 ，可以通过反射类型找到类
     */
    public void postObject(String url, HashMap<String, String> map, final OnRequestComplete onRequestComplete, final Type type) {


        if (!url.startsWith("http")) {
            //提示网址不对，访问失败

            if (onRequestComplete != null) {
                onRequestComplete.onFailed();
            }
            return;
        }
        //准备请求体  （由于本app上传图片使用的7牛，所以不需要准备上传mutipart类型的文件了）
        FormBody.Builder fBuilder = new FormBody.Builder();
        FormBody formBody = null;
        //遍历map，把键值对添加进formbody当中
        if (map != null && !map.isEmpty()) {
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                fBuilder.add(entry.getKey(), entry.getValue());
            }
            formBody = fBuilder.build();
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .post(formBody)
//                .header()
                .build();


        Call call = client.newCall(request);
        //异步的方法，这个方法内部自己会开启子线程去加载数据，然后通过回调去做数据获取完处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        //把返回过来的json串转成bean
                        final Object obj = gson.fromJson(string, type);
                        if (obj == null) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onRequestComplete.onEmpty();
                                }
                            });
                        } else {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onRequestComplete.onSuccess(obj);
                                }
                            });
                        }

                    } else {
                        //当string为空的时候
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    }


                } else {
                    //虽然已经连通到这个网址了，但是由于服务器的原因可能报了301，203，50几等等一些异常页面
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onError();
                        }
                    });
                }


            }
        });
    }


}
