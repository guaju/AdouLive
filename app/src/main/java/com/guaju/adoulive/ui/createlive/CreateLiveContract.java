package com.guaju.adoulive.ui.createlive;

/**
 * Created by guaju on 2018/1/8.
 */

public interface CreateLiveContract {
    interface  View{
        void onCreateSuccess();
        void onCreateFailed();
    }
    interface Presenter{
        void createLive(String url,String liveName);
    }
}
