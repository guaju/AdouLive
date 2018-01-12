package com.guaju.adoulive.ui.home;

import com.guaju.adoulive.adapter.HomeRoomInfoAdapter;

/**
 * Created by guaju on 2018/1/12.
 */

public interface HomeContract {
    interface View{
       void setHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter);
       void updataHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter);
    }
    interface  Presenter{
       void getHomeLiveList(int page);
    }
}
