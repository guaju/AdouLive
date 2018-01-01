package com.guaju.adoulive.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.ui.regist.RegistActivity;
import com.guaju.adoulive.utils.ToastUtils;

public class LoginActivity extends AppCompatActivity implements LoginContract.View,View.OnClickListener {

    private EditText et_username;
    private EditText et_pass;
    private Button bt_login;
    private TextView tv_regist;

    private LoginContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter=new LoginPresenter(this);
    }

    private void initListener() {
        bt_login.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }

    private void initView() {
        et_username = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_pass);
        bt_login = findViewById(R.id.bt_regist);
        tv_regist = findViewById(R.id.tv_info);
    }

    @Override
    public void loginSuccess() {
        //登录成功
    }

    @Override
    public void loginFailed() {
        //登录失败

    }

    @Override
    public void loginInfoEmpty() {
       //账号密码信息为空
        ToastUtils.show("账号或者密码不能空~");
    }

    @Override
    public void loginInfoError() {
       //账号密码信息错误
        ToastUtils.show("账号或密码长度不足8位，请检查~");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_regist:
                String acount = et_username.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                presenter.login(acount,pass);
                break;
            case R.id.tv_info:
                Intent intent=new Intent(this,RegistActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
