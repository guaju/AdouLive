package com.guaju.adoulive.ui.regist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.utils.ToastUtils;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener,RegistContract.View{

    private EditText et_regist_acount;
    private EditText et_regist_pass;
    private EditText et_regist_confirm;
    private TextView info;
    private Button regist;
    private Toolbar toolbar;

    RegistContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        initListener();
        //初始化toolbar
        initToolbar();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter=new RegistPresenter(this);
    }

    private void initToolbar() {
        toolbar.setTitle("注册");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        regist.setOnClickListener(this);
        info.setOnClickListener(this);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        et_regist_acount = findViewById(R.id.et_regist_acount);
        et_regist_pass = findViewById(R.id.et_regist_pass);
        et_regist_confirm = findViewById(R.id.et_regist_confirm);
        regist = findViewById(R.id.bt_regist);
        info = findViewById(R.id.tv_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_regist:
                String acount = et_regist_acount.getText().toString().trim();
                String pass = et_regist_pass.getText().toString().trim();
                String confirmPass = et_regist_confirm.getText().toString().trim();

                presenter.regist(acount,pass,confirmPass);

                break;
            case R.id.tv_info:
                break;
            default:
                break;
        }
    }

    @Override
    public void registSuccess() {
        ToastUtils.show("注册成功！请您登陆~");
        finish();
    }

    @Override
    public void registError(int errCode, String errMsg) {
        ToastUtils.show("注册失败！"+errMsg+"错误码："+errCode);
    }

    @Override
    public void registInfoEmpty() {
        ToastUtils.show("账号或者密码不能为空~");
    }

    @Override
    public void registInfoError() {
        ToastUtils.show("账号或者密码不能少于8位~");
    }

    @Override
    public void registConfirmPassError() {
        ToastUtils.show("两次密码输入不一致，请检查~");
    }
}
