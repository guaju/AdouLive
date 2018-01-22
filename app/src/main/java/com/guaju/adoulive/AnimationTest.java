package com.guaju.adoulive;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.guaju.adoulive.widget.danmu.DanmuItemView;
import com.guaju.adoulive.widget.gift.GiftItem;

/**
 * Created by guaju on 2018/1/16.
 */

public class AnimationTest extends Activity{


    private static final int FIRST_GIFT_SEND_FLAG = -1;
    public static final int REPEAT_GIFT_SEND_FLAG =1 ;
    //倒计时时间范围
    private int repeatTimeLimit=10;

    long firstSendTimeMillion;
    Handler repeatGiftTimer=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FIRST_GIFT_SEND_FLAG:
                    if (repeatTimeLimit>0){
                        repeatTimeLimit--;//开始倒数
                        sendEmptyMessageDelayed(FIRST_GIFT_SEND_FLAG,80);
                        bt.setText("发送（"+repeatTimeLimit+")");
                        //用户现在可以连发
                    }else{
                        //倒计时已经数完了，可以重新再开始
                        giftItem.setIsRepeat(false);
                        firstSendTimeMillion=0;
                        repeatTimeLimit=10;
                        bt.setText("发送");
                        //用户不能再连发了
                    }

                    break;

                case REPEAT_GIFT_SEND_FLAG:
                     //停止第一个事件 的处理


                    if (repeatTimeLimit>0){
                        repeatTimeLimit--;//开始倒数
                        sendEmptyMessageDelayed(REPEAT_GIFT_SEND_FLAG,80);
                        bt.setText("发送（"+repeatTimeLimit+")");

                        //用户现在可以连发
                    }else{
                        //倒计时已经数完了，可以重新再开始
                        giftItem.setIsRepeat(false);
                        giftItem.repeatSendWithoutAddNum();
                        firstSendTimeMillion=0;     
                        repeatTimeLimit=10;
                        bt.setText("发送");
                        //用户不能再连发了
                    }


                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }



        }
    };

    private DanmuItemView dd;
    private GiftItem giftItem;
    private ImageView imageView;
    private Button bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        giftItem = findViewById(R.id.gift);
        bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //先清空动画
//                giftItem.clearAnimation();


                //在第一次点的时候开始计时
                if (firstSendTimeMillion==0){
                    //第一次点击不是连发，设置给giftitem
                    giftItem.setIsRepeat(false);
                    //拿到第一次点的时间
                    firstSendTimeMillion=System.currentTimeMillis();
                    repeatGiftTimer.sendEmptyMessage(FIRST_GIFT_SEND_FLAG);
                    //再执行动画
                    giftItem.startAnimate();

                }
                else{//如果属于连击的话,需要把倒计时再从10开始倒数，并且增加礼物数
                    //属于连发
                    giftItem.setIsRepeat(true);
                    giftItem.repeatSend();//连发操作

                    //清空两个handler的处理
                    repeatGiftTimer.removeMessages(FIRST_GIFT_SEND_FLAG);
                    repeatGiftTimer.removeMessages(REPEAT_GIFT_SEND_FLAG);
                    repeatGiftTimer.sendEmptyMessage(REPEAT_GIFT_SEND_FLAG);
                    repeatTimeLimit=10;



                }
            }
        });

    }

}
