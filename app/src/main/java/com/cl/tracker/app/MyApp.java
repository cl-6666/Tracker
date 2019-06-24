package com.cl.tracker.app;

import android.app.Application;

import com.cl.tracker_cl.Tracker;
import com.cl.tracker_cl.TrackConfiguration;
import com.cl.tracker_cl.http.UPLOAD_CATEGORY;
import com.cl.tracker_cl.util.LogUtil;


public class MyApp extends Application {


    /**
     * 采集数据的地址
     */
    private final static String SA_SERVER_URL = "https://sdkdebugtest.datasink.sensorsdata.cn/sa?project=default&token=cfb8b60e42e0ae9b";


    @Override
    public void onCreate() {
        super.onCreate();
        TrackConfiguration configuration = new TrackConfiguration()
                // 开启log
                .openLog(true)
                .initializeDb(this)
                // 设置日志的上传策略
                .setUploadCategory(UPLOAD_CATEGORY.NEXT_CACHE.getValue())
                // 设置埋点信息上传的URL
                .setServerUrl(SA_SERVER_URL)
                //本地缓存的最大事件数目，当累积日志量达到阈值时发送数据，默认值 100
                .setFlushBulkSize(100)
                //设置本地缓存最多事件条数，默认为 10000 条
                .setMaxCacheSize(10000)
                // 设置上传埋点信息的公共参数
                //对于新设备的信息和公共参数，默认提供了包名，渠道，版本号，设备ID，手机品牌，手机系统版本，但在实际开发中，
                // 需要的参数可能有所差异，所以提供了自定义的功能，只需要将需要的参数以URL参数的格式进行拼接即可。
                .setCommonParameter("?channel=mi&version=1.0");
        Tracker.getInstance().init(this, configuration);


    }
}
