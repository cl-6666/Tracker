package com.cl.tracker.app;

import android.app.Application;

import com.cl.tracker_cl.Tracker;
import com.cl.tracker_cl.TrackConfiguration;
import com.cl.tracker_cl.http.UPLOAD_CATEGORY;


public class MyApp extends Application {


    /**
     * 采集数据的地址
     */
    private final static String SA_SERVER_URL = "http://t1pvuv.lechuangtec.com/api/point/insert";


    @Override
    public void onCreate() {
        super.onCreate();
        TrackConfiguration configuration = new TrackConfiguration()
                // 开启log
                .openLog(true)
                // 设置日志的上传策略
                .setUploadCategory(UPLOAD_CATEGORY.TIME_MINUTER.getValue())
                // 设置埋点信息上传的URL
                .setServerUrl(SA_SERVER_URL)
                //本地缓存的最大事件数目，当累积日志量达到阈值时发送数据，默认值 10
                .setFlushBulkSize(10)
                //设置本地缓存最多事件条数，默认为 10000 条
                .setMaxCacheSize(10000)
                //设置多少分钟上传一次默认5分钟最大60分钟
                .setMinutes(1)
                .initializeDb(this);

        Tracker.getInstance().init(this, configuration);


    }
}
