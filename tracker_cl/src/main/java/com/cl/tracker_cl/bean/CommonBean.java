package com.cl.tracker_cl.bean;

import android.content.Context;

import com.cl.tracker_cl.util.AppUtil;

import java.io.Serializable;


/**
 * 埋点统计的公共数据
 */
public class CommonBean implements Serializable {

    /**
     * 包名
     */
    private String packageName;
    /**
     * 渠道号
     */
    private String channel;
    /**
     * APP版本号
     */
    private String version;
    /**
     * 位置信息，格式：经度_维度
     */
    private String position;
    /**
     * 手机唯一识别码，为了避免用户不授权，使用自己生成的唯一码
     */
    private String deviceId;
    /**
     * 手机品牌
     */
    private String phoneBrand;
    /**
     * 手机系统版本
     */
    private String OSVersion;
    /**
     * SDK版本
     */
    private String sdkVersion;

    public String getPackageName() {
        return packageName;
    }

    public String getChannel() {
        return channel;
    }

    public String getVersion() {
        return version;
    }

    public String getPosition() {
        return position;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public CommonBean(Context context) {
        initData(context);
    }

    private void initData(Context context) {
        packageName = AppUtil.getPackageName(context);
        channel = AppUtil.getChannel(context);
        version = AppUtil.getAppVersionName(context);
        deviceId = AppUtil.getDeviceId(context);
        phoneBrand = AppUtil.getPhoneBrand(context);
        OSVersion = AppUtil.getOSVersion();
        sdkVersion = "100";
    }

    public String getParameters(String sign) {
        StringBuilder builder = new StringBuilder();
        builder.append(sign)
                .append("packageName=").append(packageName)
                .append("&channel=").append(channel)
                .append("&version=").append(version)
                .append("&position=").append(position)
                .append("&deviceId=").append(deviceId)
                .append("&phoneBrand=").append(phoneBrand)
                .append("&sdkVersion=").append(sdkVersion)
                .append("&OSVersion=").append(OSVersion);
        return builder.toString();
    }
}
