package com.guaju.adoulive.ui.host;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.guaju.adoulive.R;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveManager;

/**
 * Created by guaju on 2018/1/8.
 */

public class HostLiveActivity extends Activity implements HostLiveContract.View{

    private AVRootView avRootView;
    private ImageView iv_switch_camera,iv_close;

    private HostPresenter presenter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_live);
        initView();
        initToolbar();
        initListener();
        initPresenter();
        initCreateHost();


    }

    private void initListener() {
        //iv_close
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭
                //退出直播，然后关闭
                finish();
            }
        });
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭
                //退出直播然后关闭
                finish();

            }
        });

    }


    private void initCreateHost() {
        presenter.createHost();
    }

    private void initPresenter() {
        this.presenter=new HostPresenter(this);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        avRootView = findViewById(R.id.arv_root);
        iv_close = findViewById(R.id.iv_close);
        iv_switch_camera = findViewById(R.id.iv_switch_camera);
        //将avrootview添加
        ILVLiveManager.getInstance().setAvVideoView(avRootView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ILVLiveManager.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILVLiveManager.getInstance().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出直播

        ILVLiveManager.getInstance().onDestory();
    }


}
