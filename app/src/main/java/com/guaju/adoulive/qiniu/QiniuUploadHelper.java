package com.guaju.adoulive.qiniu;


import android.util.Log;

import com.guaju.adoulive.httputil.OkHttpHelper;
import com.orhanobut.logger.Logger;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by guaju on 2017/11/2.
 * 操作7牛的工具类
 *
 */

public class QiniuUploadHelper {

    private static String mSpaceName;
    private static String mSecretKey;
    private static String mAccessKey;


    private static boolean isCancelled=false;
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    // static final  String SPACENAME="my-bucket";
//  static final String AccessKey="MY_ACCESS_KEY";
//  static final String SecretKey="MY_SECRET_KEY";
    private static UploadManager uploadManager;

    //初始化 uploadManager
    public  static void  init(String spaceName,String secretKey,String accessKey){
        mAccessKey=accessKey;
        mSecretKey=secretKey;
        mSpaceName=spaceName;

        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        //重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }
    //生成客户端🐘服务端传递图片的token值

    /**
     *
     * @param picName   图片名称，需要带图片后缀
     * @return
     */
    public static String calcToken(String picName) throws Exception {
        //1、构建上传策略
        String scope=mSpaceName+":"+picName;
        long deadline= (System.currentTimeMillis()+3000)/1000;
//        long deadline= 1451491200;
        //TokenBean.ReturnBody returnBody = new TokenBean.ReturnBody("$(fname)", "$(fsize)", "$(imageInfo.width)", "$(imageInfo.height)", "$(etag)");
        TokenBean tokenBean = new TokenBean(scope, deadline);
        //2、把上传策略转化成json
        String putPolicy = OkHttpHelper.getInstance().gson.toJson(tokenBean);
        //3、URL 安全的 Base64 编码
        String encodedPutPolicy = UrlSafeBase64.encodeToString(putPolicy.getBytes());
        //4、使用访问密钥（AK/SK）对上一步生成的待签名字符串计算HMAC-SHA1签名：
        byte[] sign = HmacSHA1Encrypt(encodedPutPolicy, mSecretKey);
        //5、对签名进行URL安全的Base64编码：
        String encodedSign = UrlSafeBase64.encodeToString(sign);
        //6、将访问密钥（AK/SK）、encodedSign 和 encodedPutPolicy 用英文符号 : 连接起来：
        String uploadToken =mAccessKey + ':' + encodedSign + ':' + encodedPutPolicy;
        return uploadToken;

    }
    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {


        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    /**
     *  简单的上传图片的方法
     * @param filePath  图片路径
     * @param picName   服务器上边图片的名称
     */
    public static void uploadPic(String filePath,String picName,UpCompletionHandler handler) throws Exception {
        String data =filePath;
        String key = picName;
        String token= calcToken(picName);
        uploadManager.put(data, key, token,
               handler, null);
    }

    /**
     * 可以显示图片上传进度的方法
     * @param filePath
     * @param picName
     * @param token
     */
    public  static void uploadPicWithProgress(String filePath,String picName,String token){
        uploadManager.put(filePath, picName, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if(info.isOK()) {
                            Logger.i("qiniu", "Upload Success");
                        } else {
                            Logger.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, new UploadOptions(null,null, false,
                        new UpProgressHandler(){
                            public void progress(String key, double percent){
                                Log.i("qiniu", key + ": " + percent);
                            }
                        }, null));
    }

    /**
     *  可以显示进度还可以停止上传图片的方法
     * @param filePath
     * @param picName
     * @param token
     */
    public  static void uploadPicCanCancle(String filePath,String picName,String token){
        uploadManager.put(filePath, picName, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if(info.isOK()) {
                            Logger.i("qiniu", "Upload Success");
                        } else {
                            Logger.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, new UploadOptions(null,null, false,
                        new UpProgressHandler(){
                            public void progress(String key, double percent){
                                Log.i("qiniu", key + ": " + percent);
                            }
                        }, new UpCancellationSignal(){
                    public boolean isCancelled(){
                        return isCancelled;
                    }
                }));
    }

    /**
     *
     * @param value 设置是否停止图片上传
     */
    public static void setCancle(boolean value){
        isCancelled=value;
    }




}