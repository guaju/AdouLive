package com.guaju.adoulive.widget.gift;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.bean.GiftMsgInfo;
import com.guaju.adoulive.utils.ImageUtils;

/**
 * Created by guaju on 2018/1/19.
 */

public class GiftItem extends FrameLayout {


    //发送礼物的数量
    private int sendNum = 0;
    //判断是否连发
    private boolean isRepeat=false;

    LayoutInflater inflater;
    private RelativeLayout rl_gift_normal;
    private ImageView iv_sender;
    private TextView tv_sender_id;
    private TextView tv_gift_info;
    private ImageView iv_gift_icon;
    private TextView tv_gift_num;
    private Animation gift_left_in_anim, gift_icon_left_in_anim, gift_num_scale_anim, gift_left_out_anim;
    private View v;
    private FrameLayout fl;

    public GiftItem(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }


    public GiftItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();

    }

    private void init() {
        v = inflater.inflate(R.layout.item_send_gift, this, true);
        {
            fl = v.findViewById(R.id.fl);
            rl_gift_normal = v.findViewById(R.id.rl_gift_normal);
            iv_sender = v.findViewById(R.id.iv_sender);
            tv_sender_id = v.findViewById(R.id.tv_sender_id);
            tv_gift_num = v.findViewById(R.id.tv_gift_num);
            tv_gift_info = v.findViewById(R.id.tv_gift_info);
            iv_gift_icon = v.findViewById(R.id.iv_gift_icon);

            rl_gift_normal.setVisibility(View.INVISIBLE);
            iv_gift_icon.setVisibility(View.INVISIBLE);
            tv_gift_num.setVisibility(View.INVISIBLE);
        }
        {
            iv_gift_icon = v.findViewById(R.id.iv_gift_icon);
            iv_gift_icon.setVisibility(View.INVISIBLE);
        }
        {

            tv_gift_num = v.findViewById(R.id.tv_gift_num);
            tv_gift_num.setVisibility(View.INVISIBLE);
        }
        {
            //导入动画
            gift_left_in_anim = AnimationUtils.loadAnimation(getContext(), R.anim.gift_left_in);
            gift_icon_left_in_anim = AnimationUtils.loadAnimation(getContext(), R.anim.gift_icon_left_in);
            gift_num_scale_anim = AnimationUtils.loadAnimation(getContext(), R.anim.gift_num_scale);
            gift_left_out_anim = AnimationUtils.loadAnimation(getContext(), R.anim.gift_left_out);
            //记住动画执行后的效果
//            gift_left_out_anim.setFillAfter(true);
//            gift_icon_left_in_anim.setFillAfter(true);
//            gift_num_scale_anim.setFillAfter(true);

            gift_left_in_anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    rl_gift_normal.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //给礼物的图标做了动画的设置
                    iv_gift_icon.startAnimation(gift_icon_left_in_anim);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            gift_icon_left_in_anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //把当前的view置为可见
                    iv_gift_icon.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    //给礼物数做动画
                    tv_gift_num.startAnimation(gift_num_scale_anim);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            //数字的动画
            gift_num_scale_anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    tv_gift_num.setVisibility(View.VISIBLE);
                    tv_gift_num.setText("x"+sendNum);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //所有进入动画执行完成，消失
                    //判断用户发送礼物数,如果礼物数大于零，并且是连发
                    if (sendNum>1&&isRepeat){
                        //再让动画执行
                        gift_num_scale_anim.start();

                    }else{
                       //退出动画
                       rl_gift_normal.startAnimation(gift_left_out_anim);
                    }


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            gift_left_out_anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    iv_gift_icon.setVisibility(View.INVISIBLE);
                    tv_gift_num.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    rl_gift_normal.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

    }

    //绑定数据
    public void bindData(GiftMsgInfo info) {
        if (info != null) {
            if (TextUtils.isEmpty(info.getAvatar())) {
                ImageUtils.getInstance().loadCircle(R.mipmap.ic_launcher, iv_sender);
            } else {
                ImageUtils.getInstance().loadCircle(info.getAvatar(), iv_sender);
            }
            if (!TextUtils.isEmpty(info.getAdouID())) {
                tv_sender_id.setText(info.getAdouID());
            }
            if (info.getGift() != null) {
                tv_gift_info.setText("送出了" + info.getGift().getName());
                iv_gift_icon.setBackgroundResource(info.getGift().getResId());
            }
            if (info.getGiftNum() >= 1) {
                tv_gift_num.setText("x" + info.getGiftNum());
            }


        }
    }

    //设置数量
    public void setGiftNum(int num) {
        if (num > 1) {
            tv_gift_num.setText("x" + num);
        }
    }

    //让礼物执行动画
    public void startAnimate() {
        //给第一个要发生动画的view设置了动画
        rl_gift_normal.startAnimation(gift_left_in_anim);
        //礼物数计数为1
        sendNum=1;
    }

    //用户连击礼物，增加礼物
    public void repeatSend(){
        //让礼物数递增
        sendNum++;
        //需要执行数字增加的动画
        gift_num_scale_anim.start();
    }

    //用户连击礼物，增加礼物
    public void repeatSendWithoutAddNum(){
        //让礼物数递增
        //退出动画执行
        rl_gift_normal.setAnimation(gift_num_scale_anim);
       gift_left_out_anim.start();
    }
    //让调用者传入是否连发
    public void setIsRepeat(boolean isRepeat){
        this.isRepeat=isRepeat;
    }


}
