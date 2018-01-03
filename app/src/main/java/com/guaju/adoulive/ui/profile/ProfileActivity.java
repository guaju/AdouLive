package com.guaju.adoulive.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.bean.AdouTimUserProfile;
import com.guaju.adoulive.utils.ImageUtils;
import com.guaju.adoulive.utils.ToastUtils;
import com.tencent.TIMFriendGenderType;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,ProfileContract.View{

    private ConstraintLayout cl_profile;
    private ImageView iv_avatar;
    private TextView tv_nickname;
    private TextView tv_acount_id;
    private ImageView iv_gender;
    private TextView tv_grade;
    private TextView tv_my_fork;
    private TextView tv_my_fans;

    private ProfileContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initListener();
        initToolbar();
        initPresenter();
    }

    private void initPresenter() {
        presenter=new ProfilePresenter(this);
    }



    private void initToolbar() {

    }

    private void initListener() {
        cl_profile.setOnClickListener(this);
    }

    private void initView() {
        cl_profile = findViewById(R.id.cl_profile);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_acount_id = findViewById(R.id.tv_acount_id);
        iv_gender = findViewById(R.id.iv_gender);
        tv_grade = findViewById(R.id.tv_grade);
        tv_my_fans = findViewById(R.id.tv_my_fans);
        tv_my_fork = findViewById(R.id.tv_my_fork);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getUserProfile();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.cl_profile:
            Intent intent=new Intent(this, EditProfileActivity.class);
            startActivity(intent);
               break;
           default:
               break;

       }
    }

    @Override
    public void updateProfile(AdouTimUserProfile profile) {
          //更新view,设置图片跟字段
         if (!TextUtils.isEmpty(profile.getProfile().getFaceUrl())){
             ImageUtils.getInstance().loadCircle(profile.getProfile().getFaceUrl(),iv_avatar);
         } else{
             ImageUtils.getInstance().loadCircle(R.mipmap.ic_launcher,iv_avatar);
         }


         if (!TextUtils.isEmpty(profile.getProfile().getNickName())){
              tv_nickname.setText(profile.getProfile().getNickName());
         }else {
             tv_nickname.setText("暂无昵称");
         }
        if (!TextUtils.isEmpty(profile.getProfile().getIdentifier())){
            tv_acount_id.setText("阿斗号:"+profile.getProfile().getIdentifier());
        }
        if (profile.getProfile().getGender()==TIMFriendGenderType.Male){
            iv_gender.setBackgroundResource(R.mipmap.male);
        }else if (profile.getProfile().getGender()==TIMFriendGenderType.Female){
            iv_gender.setBackgroundResource(R.mipmap.female);
        }else{
            //默认是男
            iv_gender.setBackgroundResource(R.mipmap.male);
        }
        tv_grade.setText(profile.getGrade()+"");
        tv_my_fork.setText(profile.getFork()+"");
        tv_my_fans.setText(profile.getFans()+"");

    }

    @Override
    public void updateProfileError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show("拉取信息失败");
            }
        });
    }
}
