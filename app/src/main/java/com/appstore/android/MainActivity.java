package com.appstore.android;

import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<ImageView> mList;
    private TextView mTextView;
    private LinearLayout points;
    private DrawerLayout mDrawerLayout;
    //图片id
    private int[] bannerImages = { R.drawable.apple, R.drawable.banana,
            R.drawable.cherry, R.drawable.grape, R.drawable.mango,
            R.drawable.strawberry, R.drawable.watermelon};
    //文字
    private String[] bannerTexts = { "这是苹果" , "这是香蕉" ,
            "这是樱桃" , "这是葡萄" , "这是芒果" , "这是草莓" , "这是西瓜"};
    //监听器
    private BannerAdapter mAdapter;
    private BannerListener bannerListener;
    //圆点标志位
    private int pointIndex = 0;
    //线程开关标志
    private boolean isStop = false;

    private ViewPager appList;
    private TabLayout mTabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_home);
        navView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_exit:
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        appList = (ViewPager) findViewById(R.id.app_list);
        initAppList();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTextView = (TextView) findViewById(R.id.banner_text);
        points = (LinearLayout) findViewById(R.id.points);
        initData();
        initAction();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    try {
                    Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();

    }



    private void initAppList() {
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        List<String> titles = new ArrayList<>();
        titles.add("游戏");
        titles.add("应用");

        for (int i = 0; i < titles.size(); i++) {//加载标签文字
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new GamesFragment());
        fragments.add(new AppFragment());//加载每个标签下内容

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),
                fragments, titles);
        appList.setAdapter(mFragmentAdapter);//标签和标签页匹配
        mTabLayout.setupWithViewPager(appList);
    }

    //设置圆圈跟文字跟随图片改变
    private void initAction() {
        bannerListener = new BannerListener();
        mViewPager.addOnPageChangeListener(bannerListener);
        int index = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % mList.size());
        mViewPager.setCurrentItem(index);
        points.getChildAt(pointIndex).setEnabled(true);
    }

    //初始化图片与圆点
    private void initData() {
        mList = new ArrayList<>();
        View view;
        LayoutParams params;
        for (int i = 0; i < bannerImages.length; i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            imageView.setBackgroundResource(bannerImages[i]);
            mList.add(imageView);
            view = new View(MainActivity.this);
            params = new LayoutParams(15, 15);
            params.leftMargin = 10;
            view.setBackgroundResource(R.drawable.points);
            view.setLayoutParams(params);
            view.setEnabled(false);//一开始为非激活状态
            points.addView(view);
        }
        mAdapter = new BannerAdapter(mList);
        mViewPager.setAdapter(mAdapter);
    }


    //侧滑菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }


    //搜索按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("App name");
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    //监听圆圈和文字
    private class BannerListener implements ViewPager.OnPageChangeListener {

        //设置文字跟随图片改变
        @Override
        public void onPageSelected(int position) {
            int newPosition = position % bannerImages.length;
            mTextView.setText(bannerTexts[newPosition]);
            points.getChildAt(newPosition).setEnabled(true);
            points.getChildAt(pointIndex).setEnabled(false);
            pointIndex = newPosition;//更新标志位
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
    }
}
