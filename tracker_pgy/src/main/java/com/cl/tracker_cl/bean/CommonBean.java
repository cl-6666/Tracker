package com.cl.tracker_cl.bean;

import android.content.Context;

import com.cl.tracker_cl.util.AppUtil;
import com.cl.tracker_cl.util.NetworkUtils;
import com.cl.tracker_cl.util.SharedPreferencesUtil;

import java.io.Serializable;


/**
 * 埋点统计的公共数据
 */
public class CommonBean implements Serializable {

    /**
     * 设备ID（有权限时候直接获取，没有权限获取硬件码生成唯一id，使用uuid）
     */
    private String device_id;
    /**
     * 例如 WIFI、4G等
     */
    private String network_type;
    /**
     * wifi名称（没有的话为空串）
     */
    private String wifi_name;
    /**
     * 屏幕宽度 例如 1080
     */
    private String screen_width;
    /**
     * 屏幕高度 例如 2160
     */
    private String screen_height;
    /**
     * 操作系统，例如 ios、android
     */
    private String os;
    /**
     * 操作系统版本，例如 8.1.0
     */
    private String os_version;
    /**
     * 设备制造商，例如 Xiaomi
     */
    private String manufacturer;
    /**
     * 设备型号，例如 MI MAX 3
     */
    private String model;
    /**
     * 经度
     */
    private long longitude;
    /**
     * 纬度
     */
    private long latitude;

    /**
     * SDK类型，例如android，ios，java，javascript等
     */
    private String sdk;

    /**
     * SDK版本，例如3.1.5
     */
    private String sdk_version;

    /**
     * 应用的版本，1.3.0
     */
    private String app_version;

    /**
     * 应用的名称
     */
    private String app_name;

    /**
     * 渠道编码
     */
    private String channel;

    /**
     * 页面的标题（以Android为示例 首先读取 activity.getTitle()，如果使用 actionBar，
     * 并且 actionBar.getTitle() 不为空，则actionBar.getTitle() 覆盖 activity.getTitle()，
     * 如果以上两步都没有读到 title，则获取 activity 的 android:label 属性。若还是没有则为空串）
     */
    private String title;

    /**
     * Activity 的包名.类名
     */
    private String screen_name;


    /**
     * 用户ID（已经登录则传userId，未登录则为空串）
     */
    private String user_id = null;


    public CommonBean(Context context) {
        SharedPreferencesUtil.getInstance().init(context);
        initData(context);
    }

    private void initData(Context context) {
        screen_name = AppUtil.getPackageName(context);
        channel = AppUtil.getChannel(context);
        app_version = AppUtil.getAppVersionName(context);
        device_id = AppUtil.getDeviceId(context);
        manufacturer = AppUtil.getPhoneBrand(context);
        os_version = AppUtil.getOSVersion();
        network_type = NetworkUtils.networkType(context);
        model = AppUtil.getSystemModel();
        wifi_name = NetworkUtils.getSSID(context);
        sdk_version = "1.0.0";
        sdk = "android";
        app_name = AppUtil.getAppName(context);
        longitude = (long) SharedPreferencesUtil.getInstance().getParam("latitude", (long) 0);
        latitude = (long) SharedPreferencesUtil.getInstance().getParam("latitude", (long) 0);
        user_id = (String) SharedPreferencesUtil.getInstance().getParam("user_id", "");
    }

    public String getParameters(String sign) {
        StringBuilder builder = new StringBuilder();
        builder.append(sign)
                .append("screen_name=").append(screen_name)
                .append("&channel=").append(channel)
                .append("&app_version=").append(app_version)
                .append("&longitude=").append(longitude)
                .append("&latitude=").append(latitude)
                .append("&user_id=").append(user_id)
                .append("&sdk=").append(sdk)
                .append("&app_name=").append(app_name)
                .append("&model=").append(model)
                .append("&wifi_name=").append(wifi_name)

                .append("&network_type=").append(network_type)
                .append("&device_id=").append(device_id)
                .append("&manufacturer=").append(manufacturer)
                .append("&sdk_version=").append(sdk_version)
                .append("&os_version=").append(os_version);
        return builder.toString();
    }
}
