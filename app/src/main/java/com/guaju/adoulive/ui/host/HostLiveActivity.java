package com.guaju.adoulive.ui.host;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.guaju.adoulive.R;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveManager;

/**
 * Created by guaju on 2018/1/8.
 */

public class HostLiveActivity extends Activity implements HostLiveContract.View{

    private AVRootView avRootView;
    private ImageView iv_switch_camera;

    private HostPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_live);
        initView();
        initPresenter();
        initCreateHost();

    }


    private void initCreateHost() {
        presenter.createHost();
    }

    private void initPresenter() {
        this.presenter=new HostPresenter(this);
    }

    private void initView() {
        avRootView = findViewById(R.id.arv_root);
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
        ILVLiveManager.getInstance().onDestory();
    }


}
