package com.guaju.adoulive;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;

import com.guaju.adoulive.bean.Gift;
import com.guaju.adoulive.bean.GiftMsgInfo;
import com.guaju.adoulive.timcustom.CustomTimConstant;
import com.guaju.adoulive.widget.gift.GiftFullScreenItem;
import com.guaju.adoulive.widget.gift.GiftFullScreenView;
import com.guaju.adoulive.widget.gift.GiftSendDialog;

/**
 * Created by guaju on 2018/1/16.
 */

public class FullScreenGiftTest extends Activity {


    private GiftFullScreenItem giftfullscreen;
    private Animation animationout;
    private Animation animationstay;
    private Animation animationin;
    private GiftFullScreenView giftfullscreen1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        giftfullscreen1 = findViewById(R.id.giftfullscreen);
        ViewGroup.LayoutParams layoutParams = giftfullscreen1.getLayoutParams();

        WindowManager wm=getWindowManager();
        Display defaultDisplay = wm.getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        layoutParams.width=width;
        layoutParams.height=height;
        giftfullscreen1.setLayoutParams(layoutParams);
        GiftSendDialog giftSendDialog = new GiftSendDialog(this, R.style.custom_dialog, new GiftSendDialog.OnGiftSendListener() {
            @Override
            public void onSend(Gift selectedGift) {
                if (selectedGift.getType() == Gift.GiftType.FullScreen) {
                    String text = CustomTimConstant.TYPE_GIFT_FULL + "送了一个" + selectedGift.getName();
                    //展示全屏礼物动画
                    GiftMsgInfo giftMsgInfo = new GiftMsgInfo();

                    giftfullscreen1.showFullScreenGift(giftMsgInfo);
                }
            }
        });
        giftSendDialog.show();
    }
}
