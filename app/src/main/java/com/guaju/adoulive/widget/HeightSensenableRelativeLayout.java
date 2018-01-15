package com.guaju.adoulive.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by guaju on 2018/1/15.
 */

public class HeightSensenableRelativeLayout extends RelativeLayout {

    OnLayoutHeightChangedListenser mListenser;

    public HeightSensenableRelativeLayout(Context context) {
        super(context);
    }

    public HeightSensenableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param w    现在的宽度
     * @param h    现在的高度
     * @param oldw 以前的宽度
     * @param oldh 以前的高度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //现在的高度大于以前的高度
        if (h > oldh) {
            //回复到初始状态
            if (mListenser != null) {
                mListenser.showNormal();
            }

        } else if (h < oldh) {
            //切换到聊天状态
            if (mListenser != null) {
                mListenser.showChat();
            }

        }

    }

    public interface OnLayoutHeightChangedListenser {
        //回到左边是聊天 右边是关闭的页面
        void showNormal();

        //切换到聊天的底部布局
        void showChat();

    }

    public void setOnLayoutHeightChangedListenser(OnLayoutHeightChangedListenser
                                                          listenser) {
        mListenser = listenser;
    }
}
