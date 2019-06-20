package com.cl.tracker.app;

import android.app.Application;

import com.cl.tracker_cl.Tracker;
import com.cl.tracker_cl.TrackConfiguration;
import com.cl.tracker_cl.http.UPLOAD_CATEGORY;
import com.cl.tracker_cl.util.LogUtil;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TrackConfiguration configuration = new TrackConfiguration()
                // 开启log
                .openLog(true)
                // 设置日志的上传策略
                .setUploadCategory(UPLOAD_CATEGORY.REAL_TIME.getValue())
                // 设置需要提交的新设备信息
                .setDeviceInfo("?deviceId=123456&osVersion=8.0")
                // 设置埋点信息上传的URL
                .setPgyServerUrl("http://m.baidu.com")
                // 设置上传埋点信息的公共参数
                //对于新设备的信息和公共参数，默认提供了包名，渠道，版本号，设备ID，手机品牌，手机系统版本，但在实际开发中，
                // 需要的参数可能有所差异，所以提供了自定义的功能，只需要将需要的参数以URL参数的格式进行拼接即可。
                .setCommonParameter("?channel=mi&version=1.0");
        Tracker.getInstance().init(this, configuration);


    }
}