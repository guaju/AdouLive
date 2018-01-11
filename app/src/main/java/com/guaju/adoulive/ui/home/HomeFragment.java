package com.guaju.adoulive.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guaju.adoulive.MainActivity;
import com.guaju.adoulive.R;

/**
 * Created by guaju on 2018/1/5.
 */

public class HomeFragment extends Fragment {

    MainActivity mainActivity;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        srl = view.findViewById(R.id.srl);
        rv = view.findViewById(R.id.rv);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        mainActivity.initToolbar("首页");
    }
}
