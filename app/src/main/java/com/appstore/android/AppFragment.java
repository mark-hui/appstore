package com.appstore.android;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AppFragment extends Fragment {
    private List<App> appList = new ArrayList<>();
    private App[] apps = {new App("baidu"), new App("iqiyi"), new App("qq"),
            new App("taobao"), new App("uc"), new App("wechat"),
            new App("weibo"), new App("youku")};
    private SwipeRefreshLayout swipeRefresh;
    private AppAdapter adapter;
    private View view;

    private void refreshApps() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initApps();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.app_list, container, false);
        initApps();
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppAdapter(/*getActivity(), */appList);
        mRecyclerView.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshApps();
            }
        });
        return view;
    }


    private void initApps() {
        appList.clear();
        for (int i = 0; i < 15; i++) {
            Random random = new Random();
            int index = random.nextInt(apps.length);
            appList.add(apps[index]);
        }
    }
}
