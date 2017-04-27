package com.appstore.android;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String APP_NAME = "app_name";

    private List<ImageView> mList;

    String appName;

    private UpdateViewReceiver receiver;

    FloatingActionButton startDownload;
    FloatingActionButton pauseDownload;
    FloatingActionButton cancelDownload;
    FloatingActionButton continueDownload;

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        appName = intent.getStringExtra(APP_NAME);//获取名字
        App app = new App(appName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        ImageView appImageView = (ImageView) findViewById(R.id.app_image);//标题图片
        TextView appContentText = (TextView) findViewById(R.id.app_text);//简介
        ViewPager appPics = (ViewPager) findViewById(R.id.app_pics);//介绍图片
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(app.getAppName());
        Glide.with(this).load(app.getAppIcon()).into(appImageView);
        appContentText.setText(app.getAppText());
        int[] appPicsId = app.getAppPic();
        mList = new ArrayList<>();
        for (int i = 0; i < appPicsId.length; i++) {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(appPicsId[i]).into(imageView);
            mList.add(imageView);
        }
        BannerAdapter adapter = new BannerAdapter(mList);
        appPics.setAdapter(adapter);
        startDownload = (FloatingActionButton) findViewById(R.id.download);//下载按钮相关
        pauseDownload = (FloatingActionButton) findViewById(R.id.pause);
        cancelDownload = (FloatingActionButton) findViewById(R.id.cancel);
        continueDownload = (FloatingActionButton) findViewById(R.id.continue_download);
        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);
        continueDownload.setOnClickListener(this);
        Intent download = new Intent(this, DownloadService.class);
        startService(download);
        bindService(download, connection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.//运行时权限
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailActivity.this, new
                    String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.appstore.android.UPDATE_VIEW");
        receiver = new UpdateViewReceiver();
        registerReceiver(receiver, intentFilter);
    }

    class UpdateViewReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            startDownload.setVisibility(View.VISIBLE);
            pauseDownload.setVisibility(View.GONE);
            cancelDownload.setVisibility(View.GONE);
            continueDownload.setVisibility(View.GONE);
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void onClick(View v) {
        if (downloadBinder == null) {
            return;
        }
        App app = new App(appName);
        String url = getString(app.getAppUrl());
        switch (v.getId()) {
            case R.id.download:
                downloadBinder.startDownload(url);
                startDownload.setVisibility(View.GONE);
                pauseDownload.setVisibility(View.VISIBLE);
                cancelDownload.setVisibility(View.VISIBLE);
                break;
            case R.id.pause:
                downloadBinder.pauseDownload();
                pauseDownload.setVisibility(View.GONE);
                continueDownload.setVisibility(View.VISIBLE);
                break;
            case R.id.cancel:
                downloadBinder.cancelDownload();
                cancelDownload.setVisibility(View.GONE);
                pauseDownload.setVisibility(View.GONE);
                continueDownload.setVisibility(View.GONE);
                startDownload.setVisibility(View.VISIBLE);
                break;
            case R.id.continue_download:
                downloadBinder.startDownload(url);
                continueDownload.setVisibility(View.GONE);
                pauseDownload.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case  1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.
                        PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
