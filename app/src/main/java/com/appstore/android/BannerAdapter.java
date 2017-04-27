package com.appstore.android;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


public class BannerAdapter extends PagerAdapter {
    //图片数据
    private List<ImageView> mList;

    public BannerAdapter(List<ImageView> list) {
        this.mList = list;
    }

    //设定一共有多少个图片
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    //判定是否为同一个图片
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    //加载图片资源
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position % mList.size()));
        return mList.get(position % mList.size());
    }
    //删除图片数据
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position % mList.size()));
    }
}
