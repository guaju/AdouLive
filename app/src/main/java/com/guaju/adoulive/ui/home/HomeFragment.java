package com.guaju.adoulive.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guaju.adoulive.MainActivity;
import com.guaju.adoulive.R;
import com.guaju.adoulive.adapter.HomeRoomInfoAdapter;

/**
 * Created by guaju on 2018/1/5.
 */

public class HomeFragment extends Fragment implements HomeContract.View {

    MainActivity mainActivity;
    public SwipeRefreshLayout srl;
    private RecyclerView rv;
    private HomeContract.Presenter presenter;
    int page;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);
        initView(view);
        initPresenter();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //加载第0页的数据
        initPullToRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(0);
    }

    private void initPullToRefresh() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                page++;
                initData(0);
            }
        });
    }

    private void initView(View view) {
        srl = view.findViewById(R.id.srl);
        rv = view.findViewById(R.id.rv);
        //设置布局样式
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置分割线
//        rv.addItemDecoration();
        mainActivity = (MainActivity) getActivity();
        mainActivity.initToolbar("首页");
    }



    private void initPresenter() {
          presenter=new HomePresenter(this);
    }

    private void initData(int page) {
        presenter.getHomeLiveList(page);
    }

    @Override
    public void setHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter) {
        if (adapter!=null){
            rv.setAdapter(adapter);
        }
    }

    @Override
    public void updataHomeRoomInfoAdapter(HomeRoomInfoAdapter adapter) {
        if (adapter!=null){
            //更新数据
            rv.setAdapter(adapter);
        }
    }
}
