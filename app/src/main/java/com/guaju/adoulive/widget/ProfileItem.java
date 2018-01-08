package com.guaju.adoulive.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.utils.ImageUtils;

/**
 * Created by guaju on 2018/1/1.
 */

public class EditProfileItem extends FrameLayout {


    private ItemType type=ItemType.TYPE_NORMAL;

    private LayoutInflater inflater;
    private TextView tv_editprofile_name;
    private TextView tv_editprofile_value,tv_seperater;
    private ImageView iv_editprofile_avatar,iv_right_arraw,iv_icon;
    private RelativeLayout rl;
    //在java代码中new 这个组件的时候使用
    public EditProfileItem(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(getContext());
        initView();
    }

    //在布局xml只要使用这个组件就会自动调用这个构造方法创建出来
    public EditProfileItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(getContext());
        initView();
        //先通过上下文拿到一个typedArray
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditProfileItem);
        int itemtype = typedArray.getInt(R.styleable.EditProfileItem_itemtype, 101);
        String itemtitle = typedArray.getString(R.styleable.EditProfileItem_itemtitle);
        int leftIcon = typedArray.getInt(R.styleable.EditProfileItem_lefticon, 0);

        if (!TextUtils.isEmpty(itemtitle)) {
            tv_editprofile_name.setText(itemtitle);
        }

        if (itemtype==ItemType.TYPE_NORMAL.getValue()) {
            setType(ItemType.TYPE_NORMAL);
            boolean isendline = typedArray.getBoolean(R.styleable.EditProfileItem_isendline, false);
            String itemvalue = typedArray.getString(R.styleable.EditProfileItem_itemvalue);

            if (!TextUtils.isEmpty(itemvalue)) {
                tv_editprofile_value.setText(itemvalue);
            }
            if (isendline){
                tv_seperater.setVisibility(View.GONE);
            }
        }
        else if (itemtype==ItemType.TYPE_AVATAR.getValue()){
            setType(ItemType.TYPE_AVATAR);
        }
        if (leftIcon!=0){
            iv_icon.setBackgroundResource(leftIcon);
            iv_icon.setVisibility(View.VISIBLE);

        }else{
            iv_icon.setVisibility(View.GONE);
        }


        //是否可编辑
        boolean value = typedArray.getBoolean(R.styleable.EditProfileItem_iseditable, true);
        if (!value){
            iv_right_arraw.setVisibility(View.INVISIBLE);
        }


    }

    private void initView() {
        View view = inflater.inflate(R.layout.edit_profile_item, null, false);
        rl = view.findViewById(R.id.rl);
        //为了避免布局发生变化，使用layoutparams重新设定view 的宽高
        int height = getResources().getDimensionPixelOffset(R.dimen.item_edit_profile_height);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        rl.setLayoutParams(layoutParams);

        tv_editprofile_name = view.findViewById(R.id.tv_editprofile_name);
        tv_editprofile_value = view.findViewById(R.id.tv_editprofile_value);
        iv_editprofile_avatar = view.findViewById(R.id.iv_editprofile_avatar);
        tv_seperater = view.findViewById(R.id.tv_seperater);
        iv_right_arraw = view.findViewById(R.id.iv_right_arraw);
        iv_icon = view.findViewById(R.id.iv_icon);
        addView(view);
    }
    //通过java去设置name和value
   public void  setText(String name,String value){
        tv_editprofile_name.setText(name);
        tv_editprofile_value.setText(value);
   }
   //设置条目的类型
   public void setType(ItemType type){
       this.type=type;
       if (type==ItemType.TYPE_AVATAR){
           tv_editprofile_value.setVisibility(View.INVISIBLE);
           iv_right_arraw.setVisibility(View.INVISIBLE);
           iv_editprofile_avatar.setVisibility(View.VISIBLE);
       }else if (type==ItemType.TYPE_NORMAL){
           tv_editprofile_value.setVisibility(View.VISIBLE);
           iv_right_arraw.setVisibility(View.VISIBLE);
           iv_editprofile_avatar.setVisibility(View.INVISIBLE);
       }


   }

   public void setAvatar(String path){
       ImageUtils.getInstance().loadCircle(path,iv_editprofile_avatar);

   }
   public void setAvatar(Uri path){
       ImageUtils.getInstance().loadCircle(path,iv_editprofile_avatar);

   }
     public  enum ItemType{
         TYPE_AVATAR(102),TYPE_NORMAL(101);
         private int value;

         ItemType(int value) {
            this.value=value;
         }
         public int getValue(){
             return value;
         }
     }
     public void setValue(String str){
         tv_editprofile_value.setText(str);
     }
    
}
