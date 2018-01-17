package com.guaju.adoulive.widget.danmu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.guaju.adoulive.R;
import com.guaju.adoulive.bean.DanmuMsgInfo;

import java.util.LinkedList;

/**
 * Created by guaju on 2018/1/16.
 */

public class DanmuView extends LinearLayout {
    LayoutInflater inflater;
    private DanmuItemView danmuItem0;
    private DanmuItemView danmuItem1;
    private Animation animation;
    private int screenWidth;
    private LinkedList<DanmuMsgInfo> linkedList;

    public DanmuView(Context context) {
        super(context);
        inflater=LayoutInflater.from(context);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.danmuview, this, true);
        danmuItem0 = view.findViewById(R.id.danmuitem0);
        danmuItem1 = view.findViewById(R.id.danmuitem1);
        setDefault();
        //测量屏幕高度的方法
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        screenWidth = defaultDisplay.getWidth();

    }

    private void setDefault() {
        setOrientation(VERTICAL);
        danmuItem0.setVisibility(View.INVISIBLE);
        danmuItem1.setVisibility(View.INVISIBLE);
    }

    public DanmuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater=LayoutInflater.from(context);
        init();
    }

    //提供获得可用弹幕item的方法
    public DanmuItemView  getAvailableDanmuItem(){
        //如果第一个弹幕的状态是隐藏状态，那么他就可用
         if (danmuItem0.getVisibility()==View.INVISIBLE){
              return danmuItem0;
         }
         else if (danmuItem1.getVisibility()==View.INVISIBLE){
             return danmuItem1;
         }else{
              return null;
         }
    }
    //提供 外界添加弹幕的消息
    public void  addDanmu(DanmuMsgInfo info){
        //先拿到可用的弹幕，然后设置info
        final DanmuItemView availableDanmuItem = getAvailableDanmuItem();
        //先设置数据
        if (availableDanmuItem!=null){
            //控件可用，弹出弹幕
            danDan(info, availableDanmuItem);
        }else{
            //怎么办？缓存起来，等弹幕能用的时候再发出来   ，用什么存？  先进先出：queue
            if (linkedList==null){
                linkedList = new LinkedList<DanmuMsgInfo>();
            }
            //把消息加进来
            linkedList.add(info);
        }


    }
     //让可以用的弹幕动起来
    private void danDan(DanmuMsgInfo info, final DanmuItemView availableDanmuItem) {
        availableDanmuItem.bindData(info);
        //测量弹幕itemview的宽度
        int w = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        availableDanmuItem.measure(w, h);
        final int width = availableDanmuItem.getMeasuredWidth();
        //让弹幕动起来,让动画在主线程执行
        post(new Runnable() {
            @Override
            public void run() {
                //属性动画
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(availableDanmuItem,"translationX", screenWidth,-width);
                objectAnimator.setDuration(5000);
                //加速度差值器 ：默认是匀速差值器
                objectAnimator.setInterpolator(new LinearInterpolator());//匀速差值器
                //先要把控件展示出来
                availableDanmuItem.setVisibility(View.VISIBLE);
                //启动动画
                objectAnimator.start();

                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //通知结束 ，当动画执行完成之后，再置为可用
                        availableDanmuItem.setVisibility(View.INVISIBLE);
                        //然后开始去缓存队列中取信息，并展示
                        showCacheMsg(availableDanmuItem);

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
    }

    private void showCacheMsg(DanmuItemView availableDanmuItem) {
        if (linkedList!=null&&!linkedList.isEmpty()){
            DanmuMsgInfo danmuMsgInfo = linkedList.removeFirst();
            danDan(danmuMsgInfo,availableDanmuItem);
        }
    }

}
