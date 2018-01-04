package com.guaju.adoulive.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guaju.adoulive.R;

/**
 * Created by guaju on 2018/1/3.
 */

public class EditProfileDialog implements View.OnClickListener {
    OnEditChangedListener listener;
    public interface   OnEditChangedListener{
       void onChanged(String value);
       void onContentEmpty();
    }

    Context context;
    LayoutInflater inflater;
    private ImageView iv_icon;
    private TextView tv_title;
    private EditText et_content;
    private Button bt_confim;
    private TextView bt_cancel;
    private View v;
    Dialog dialog;
    AlertDialog.Builder builder;
    private WindowManager wm;
    private Display display;
    private ViewGroup.LayoutParams layoutParams;

    public EditProfileDialog(@NonNull Context context,OnEditChangedListener listener) {
        this.context = context;
        builder=new AlertDialog.Builder(context);
        this.listener= listener;
        init();
    }

    public void dismiss(){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
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




    public void show(){

        dialog.show();

    }

    private void init() {
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.dialog_edit_profile, null, false);
        iv_icon = v.findViewById(R.id.iv_icon);
        tv_title = v.findViewById(R.id.tv_title);
        et_content = v.findViewById(R.id.et_content);
        bt_confim = v.findViewById(R.id.bt_confim);
        bt_cancel = v.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);
        bt_confim.setOnClickListener(this);
        builder.setView(v);
        dialog=builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width=WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height=WindowManager.LayoutParams.WRAP_CONTENT;
//        attributes.gravity= Gravity.BOTTOM;
        window.setAttributes(attributes);
    }

    public EditProfileDialog(@NonNull Context context, int themeResId,OnEditChangedListener listener) {
        this.context=context;
        builder=new AlertDialog.Builder(context);
        this.listener=listener;
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confim:
                String value = et_content.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    //更改对应的信息
                     if (listener!=null){
                         listener.onChanged(value);
                     }
                }else{
                    //内容为空时
                    if (listener!=null){
                        listener.onContentEmpty();
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
}
