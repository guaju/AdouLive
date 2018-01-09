package com.guaju.adoulive.ui.createlive;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.app.QiniuConfig;
import com.guaju.adoulive.engine.PicChooseHelper;
import com.guaju.adoulive.qiniu.QiniuUploadHelper;
import com.guaju.adoulive.utils.ImageUtils;
import com.guaju.adoulive.utils.ToastUtils;
import com.guaju.adoulive.widget.SelectPicDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by guaju on 2018/1/8.
 */

public class CreateLiveActivity extends AppCompatActivity implements CreateLiveContract.View, View.OnClickListener {

    private EditText et_room_name;
    private Button bt_createlive;
    private ImageView iv_cover;
    private FrameLayout fl;

    SelectPicDialog selectPicDialog;
    CreateLiveContract.Presenter presenter;

    String coverUrl, liveName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createlive);
        initView();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter = new CreateLivePresenter(this);
    }

    private void initListener() {
        fl.setOnClickListener(this);
        bt_createlive.setOnClickListener(this);
    }

    private void initView() {
        iv_cover = findViewById(R.id.iv_cover);
        fl = findViewById(R.id.fl);
        et_room_name = findViewById(R.id.et_room_name);
        bt_createlive = findViewById(R.id.bt_createlive);
    }

    @Override
    public void onCreateSuccess() {

    }

    @Override
    public void onCreateFailed() {
        ToastUtils.show("信息不能为空，创建失败~");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl:
                //设置封面
                showSelectPicDialog();
                break;
            case R.id.bt_createlive:
                //创建直播
                liveName = et_room_name.getText().toString().trim();
                presenter.createLive(coverUrl, liveName);
                break;
            default:

                break;
        }
    }

    private void showSelectPicDialog() {
        selectPicDialog = new SelectPicDialog(this, R.style.dialog_ios_style);
        selectPicDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PicChooseHelper.getInstance(this).onActivityResult(requestCode, resultCode, data, PicChooseHelper.CropType.Cover, new PicChooseHelper.OnPicReadyListener() {
            @Override
            public void onReady(Uri outUri) {
                //当图片截取完之后怎么办
                iv_cover.setVisibility(View.VISIBLE);
                ImageUtils.getInstance().load(outUri, iv_cover);
                selectPicDialog.dismiss();
                //先把图片传给七牛
                String path = outUri.getPath();
                File file = new File(path);
                if (!file.exists()) {
                    ToastUtils.show("获取本地图片失败！");
                } else {
                    try {
                        QiniuUploadHelper.uploadPic(file.getAbsolutePath(), file.getName(), new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                coverUrl= QiniuConfig.QINIU_HOST+key;
                                ToastUtils.show("封面设置成功");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }
}
