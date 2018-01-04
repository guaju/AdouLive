package com.guaju.adoulive.widget;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guaju.adoulive.R;

/**
 * Created by guaju on 2018/1/4.
 */

public class EditProfileDialog2 implements View.OnClickListener {
   public interface  OnProfileChangedListener{
         //改变内容成功
     void   onChangeSuccess(String value);
     void   onChangeError();

    }


    Activity activity;
    Dialog dialog;
    LayoutInflater inflater;
    private ImageView iv_icon;
    private TextView tv_title;
    private EditText et_content;
    private Button bt_confim;
    private TextView bt_cancel;
    private View v;
    private  WindowManager windowManager;
    private  int screenWidth;

    private EditProfileDialog2.OnProfileChangedListener mListener;

    public EditProfileDialog2(Activity activity, EditProfileDialog2.OnProfileChangedListener listener) {
        this.activity = activity;
        //把dialog实例化
        dialog=new Dialog(activity);
        mListener=listener;
        init();
    }
    public EditProfileDialog2(Activity activity,int styleid,EditProfileDialog2.OnProfileChangedListener listener) {
        this.activity = activity;
        //把dialog实例化
        dialog=new Dialog(activity,styleid);
        mListener=listener;
        init();
    }
    public void init(){
        inflater=LayoutInflater.from(activity);
        windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        //把dialog布局填充进来
        v = inflater.inflate(R.layout.dialog_edit_profile, null, false);
        iv_icon = v.findViewById(R.id.iv_icon);
        tv_title = v.findViewById(R.id.tv_title);
        et_content = v.findViewById(R.id.et_content);
        bt_confim = v.findViewById(R.id.bt_confim);
        bt_cancel = v.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);
        bt_confim.setOnClickListener(this);
        dialog.setContentView(v);
        //在这给dialog设置宽高
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width=screenWidth*80/100;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    public void show(){
        if (dialog!=null&&!dialog.isShowing()){
            dialog.show();
        }
    }
    public void hide(){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public void setCancelable(boolean value){
        dialog.setCancelable(value);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confim:
                String value = et_content.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    //更改对应的信息
                    if (mListener!=null){
                        mListener.onChangeSuccess(value);
                    }
                }else{
                    //内容为空时
                    if (mListener!=null){
                        mListener.onChangeError();
                    }
                }
                break;
            case R.id.bt_cancel:
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    public void setTitleAndIcon(String title,int resId){
        if(!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
        if (resId>0){
            iv_icon.setBackgroundResource(resId);
        }


    }
}
