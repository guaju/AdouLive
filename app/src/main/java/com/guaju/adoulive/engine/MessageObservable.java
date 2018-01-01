package com.guaju.adoulive.engine;

import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVText;

import java.util.LinkedList;

/**
 * 消息观察者
 */
public class MessageObservable implements ILVLiveConfig.ILVLiveMsgListener{
    // 消息监听链表
    private LinkedList<ILVLiveConfig.ILVLiveMsgListener> listObservers = new LinkedList<>();
    // 句柄
    private static MessageObservable instance;


    public static MessageObservable getInstance(){
        if (null == instance){
            synchronized (MessageObservable.class){
                if (null == instance){
                    instance = new MessageObservable();
                }
            }
        }
        return instance;
    }


    // 添加观察者
    public void addObserver(ILVLiveConfig.ILVLiveMsgListener listener){
        if (!listObservers.contains(listener)){
            listObservers.add(listener);
        }
    }

    // 移除观察者
    public void deleteObserver(ILVLiveConfig.ILVLiveMsgListener listener){
        listObservers.remove(listener);
    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        // 拷贝链表
        LinkedList<ILVLiveConfig.ILVLiveMsgListener> tmpList = new LinkedList<>(listObservers);
        for (ILVLiveConfig.ILVLiveMsgListener listener : tmpList){
            listener.onNewTextMsg(text, SenderId, userProfile);
        }
    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {
        // 拷贝链表
        LinkedList<ILVLiveConfig.ILVLiveMsgListener> tmpList = new LinkedList<>(listObservers);
        for (ILVLiveConfig.ILVLiveMsgListener listener : tmpList){
            listener.onNewCustomMsg(cmd, id, userProfile);
        }
    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {
        // 拷贝链表
        LinkedList<ILVLiveConfig.ILVLiveMsgListener> tmpList = new LinkedList<>(listObservers);
        for (ILVLiveConfig.ILVLiveMsgListener listener : tmpList){
            listener.onNewOtherMsg(message);
        }
    }
}
