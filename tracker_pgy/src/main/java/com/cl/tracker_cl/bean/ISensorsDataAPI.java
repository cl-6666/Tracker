package com.cl.tracker_cl.bean;

import android.support.annotation.NonNull;

import org.json.JSONObject;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-21
 * 描述：
 * 修订历史：
 */
public interface ISensorsDataAPI {

    /**
     * 注册事件动态公共属性
     *
     * @param dynamicSuperProperties 事件动态公共属性回调接口
     */
    void registerDynamicSuperProperties(SensorsDataDynamicSuperProperties dynamicSuperProperties);


    /**
     * 更新 GPS 位置信息
     *
     * @param latitude  纬度
     * @param longitude 经度
     */
    void setGPSLocation(double latitude, double longitude);

    /**
     * 获取当前用户的 distinctId
     *
     * @return 优先返回登录 ID，登录 ID 为空时，返回匿名 ID
     */
    void getDistinctId(String user_id);

    /**
     * 调用track接口，追踪一个带有属性的事件
     *
     * @param eventName  事件的名称
     * @param properties 事件的属性
     */
    void track(@NonNull String eventName, @NonNull JSONObject properties);

    /**
     * 与 {@link #track(String, JSONObject)} 类似，无事件属性
     *
     * @param eventName 事件的名称
     */
    void track(String eventName);

    /**
     * 初始化事件的计时器，默认计时单位为毫秒。
     * 详细用法请参考 trackTimer(String, TimeUnit)
     *
     * @param eventName 事件的名称
     */
    @Deprecated
    void trackTimer(final String eventName);


    /**
     * 从服务器请求埋点的配置信息
     */
    void startRequestConfig();


    /**
     * 提交新设备信息到服务器
     */
    void submitDeviceInfo();

    /**
     * 是否开启可视化全埋点
     *
     * @return true 代表开启了可视化全埋点， false 代表关闭了可视化全埋点
     */
    boolean isVisualizedAutoTrackEnabled();


}
