package com.guaju.adoulive.engine;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.guaju.adoulive.app.AdouApplication;
import com.guaju.adoulive.bean.AdouTimUserProfile;

import java.io.File;
import java.io.IOException;


/**
 * Created by guaju on 2018/1/4.
 */

public class PicChooseHelper {
    private static final int REQUEST_SELECT_PHOTO = 101;
    private static final int REQUEST_TAKE_CAMERA = 102;
    private static final int REQUEST_CROP = 103;
    static PicChooseHelper mHelper;
    private Uri createAlbumUri;
    Activity mActivity;
    //最终裁剪之后的uri
    private Uri outUri;


    public interface OnAvatarReadyListener {
        void onReady(Uri outUri);
    }

    private PicChooseHelper(Activity activity) {
        mActivity = activity;

    }

    public static PicChooseHelper getInstance(Activity activity) {
        if (mHelper == null) {
            mHelper = new PicChooseHelper(activity);
        }

        return mHelper;
    }

    /**
     * 转换 content:// uri
     */
    public Uri getImageContentUri(Uri uri) {
        String filePath = uri.getPath();
        Cursor cursor = AdouApplication.getApp().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            return AdouApplication.getApp().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    //创建选择拍照后输出文件的路径
    public Uri createAlbumUri(AdouTimUserProfile profile) {
        String dirPath = Environment.getExternalStorageDirectory() + "/" + AdouApplication.getApp().getApplicationInfo().packageName;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String id = "";
        if (profile != null) {
            //获得id号
            id = profile.getProfile().getIdentifier();
        }
        //将用户id号当做明恒，这样每次拍照后就会把之前那个文件覆盖
        String fileName = "camera_" + id + ".jpg";
        File picFile = new File(dirPath, fileName);
        if (picFile.exists()) {
            picFile.delete();
        }
        createAlbumUri = Uri.fromFile(picFile);
        return createAlbumUri;

    }

    //通过账号id 和包名生成了裁剪输出文件uri
    public Uri createCropUri(AdouTimUserProfile profile) {
        String dirPath = Environment.getExternalStorageDirectory() + "/" + AdouApplication.getApp().getApplicationInfo().packageName;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String id = "";
        if (profile != null) {
            id = profile.getProfile().getIdentifier();
        }
        //删除之前的图片
        final String finalId = id;
        File[] files = dir.listFiles();
        for (File f : files) {

            if (f.getName().startsWith(finalId)) {
                f.delete();
            }
        }
        //拿到新的图片
        String fileName = id + System.currentTimeMillis() + "_crop.jpg";
        File picFile = new File(dirPath, fileName);
        if (picFile.exists()) {
            picFile.delete();
        }
        try {
            picFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(picFile);
    }


    public Uri formatUri(Uri uri) {
        Uri rightUri = uri;
        int sdkInt = Build.VERSION.SDK_INT;//拿到当前手机版本
        if (sdkInt < 24) {
            return rightUri;
        } else {
            String scheme = uri.getScheme();//拿到uri的开头信息(格式)
            if ("content".equals(scheme)) {
                rightUri = uri;
            } else {
                rightUri = getImageContentUri(uri);
            }
        }


        return rightUri;
    }

    public Uri getAlbumUri() {
        return createAlbumUri;
    }

    //开启照相机intent
    public void startCameraIntent() {
        //得到自己设定的路径
        Uri mCameraFileUri = createAlbumUri(AdouApplication.getApp().getAdouTimUserProfile());
        //校验uri
        int sdkInt = Build.VERSION.SDK_INT;
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (sdkInt < 24) {
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, mCameraFileUri);

        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, mCameraFileUri.getPath());
            Uri uri = getImageContentUri(mCameraFileUri);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mActivity.startActivityForResult(intentCamera, REQUEST_TAKE_CAMERA);
    }

    public void startPhotoSelectIntent() {
        //选择相册
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //选择什么样的文件
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }

    //开启截图意图
    private void startCrop(Intent data) {
        Uri uri;
        if (data == null) {
            uri = getAlbumUri();
        } else {
            //拿到图片的url
            uri = data.getData();

        }
        //创建裁剪之后那个uri
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 500);
        intent.putExtra("aspectY", 500);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        // 设置为true直接返回bitmap,这里不做输出，只需要指定我们自己定义uri即可
        intent.putExtra("return-data", false);
        //设置输出图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //传入要裁剪的图像uri,但是有的android7.0手机的uri首地址不是content:这样是不识别的，所以转化
        Uri rightUri = formatUri(uri);
        //传入源uri
        intent.setDataAndType(rightUri, "image/*");
        //设置输出uri
        outUri = createCropUri(AdouApplication.getApp().getAdouTimUserProfile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        //开启
        mActivity.startActivityForResult(intent, REQUEST_CROP);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data,OnAvatarReadyListener listener) {
        if (requestCode == REQUEST_SELECT_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                startCrop(data);
            }
        } else if (requestCode == REQUEST_TAKE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                startCrop(data);
            }
        } else if (requestCode == REQUEST_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                if (listener != null) {
                    listener.onReady(outUri);
                }
            }

        }
    }
}
