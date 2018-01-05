package com.guaju.adoulive.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guaju.adoulive.MainActivity;
import com.guaju.adoulive.R;
import com.guaju.adoulive.bean.AdouTimUserProfile;
import com.guaju.adoulive.ui.profile.EditProfileActivity;
import com.guaju.adoulive.utils.ImageUtils;
import com.guaju.adoulive.utils.ToastUtils;
import com.tencent.TIMFriendGenderType;

/**
 * Created by guaju on 2018/1/5.
 */

public class MineFragment extends Fragment  implements ProfileContract.View{

    private ConstraintLayout cl_profile;
    private ProfileContract.Presenter presenter;

    private ImageView iv_avatar;
    private TextView tv_nickname;
    private TextView tv_acount_id;
    private ImageView iv_gender;
    private TextView tv_grade;
    private TextView tv_my_fork;
    private TextView tv_my_fans;
    private MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initPresenter();
        mainActivity = (MainActivity) getActivity();
        mainActivity.initToolbar("我");
    }

    private void initPresenter() {
        presenter=new ProfilePresenter(this);
    }

    private void initView(View view) {
        cl_profile = view.findViewById(R.id.cl_profile);
        cl_profile = view.findViewById(R.id.cl_profile);
        iv_avatar = view.findViewById(R.id.iv_avatar);
        tv_nickname = view.findViewById(R.id.tv_nickname);
        tv_acount_id = view.findViewById(R.id.tv_acount_id);
        iv_gender = view.findViewById(R.id.iv_gender);
        tv_grade = view.findViewById(R.id.tv_grade);
        tv_my_fans = view.findViewById(R.id.tv_my_fans);
        tv_my_fork = view.findViewById(R.id.tv_my_fork);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUserProfile();
    }

    private void initListener() {
        cl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    //更新数据
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
        if (profile.getProfile().getGender()== TIMFriendGenderType.Male){
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show("拉取信息失败");
            }
        });
    }
}
