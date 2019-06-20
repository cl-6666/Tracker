package com.cl.tracker_cl;

import android.text.TextUtils;

import com.cl.tracker_cl.http.DATA_PROTOCOL;
import com.cl.tracker_cl.http.UPLOAD_CATEGORY;
import com.cl.tracker_cl.util.LogUtil;


/**
 * 配置信息
 */
public class TrackConfiguration {

    private boolean openLog;

    /**
     * 上传策略，详见{@link com.cl.tracker_cl.http.UPLOAD_CATEGORY}
     */
    private int uploadCategory;
    /**
     * 获取配置信息的URL
     * 暂时预留
     */
    private String configUrl;
    /**
     * 上传新设备信息的URL
     * 暂时预留
     */
    private String newDeviceUrl;

    /**
     * 上传统计数据的URL
     */
    private String pgyServerUrl;

    /**
     * 上传日志信息的公共参数, URL参数的形式
     */
    private String commonParameter;
    /**
     * 保存新设备的信息，将需要上传的设备信息以URL参数的形式拼接，如"deviceId=12345&os_version=7.0"
     */
    private String deviceInfo;

    private UPLOAD_CATEGORY _uploadCategory;
    private DATA_PROTOCOL _dataProtocol;

    public TrackConfiguration() {
        openLog = false;
        _uploadCategory = UPLOAD_CATEGORY.NEXT_LAUNCH;
        _dataProtocol = DATA_PROTOCOL.PROTOCOL_BUFFER;
    }

    public TrackConfiguration openLog(boolean openLog) {
        this.openLog = openLog;
        LogUtil.openLog(openLog);
        return this;
    }

    public String getConfigUrl() {
        return configUrl;
    }

    public TrackConfiguration setConfigUrl(String configUrl) {
        this.configUrl = configUrl;
        return this;
    }

    public String getNewDeviceUrl() {
        return newDeviceUrl;
    }

    public TrackConfiguration setNewDeviceUrl(String newDeviceUrl) {
        this.newDeviceUrl = newDeviceUrl;
        return this;
    }

    public String getPgyServerUrl() {
        return pgyServerUrl;
    }


    public TrackConfiguration setPgyServerUrl(String uploadUrl) {
        this.pgyServerUrl = uploadUrl;
        return this;
    }

    public TrackConfiguration setUploadCategory(int uploadCategory) {
        this.uploadCategory = uploadCategory;
        this._uploadCategory = UPLOAD_CATEGORY.getCategory(uploadCategory);
        return this;
    }

    public TrackConfiguration setDataProtocol(int dataProtocol) {
        this._dataProtocol = DATA_PROTOCOL.getDataProtocol(dataProtocol);
        return this;
    }

    public boolean isOpenLog() {
        return openLog;
    }

    public String getCommonParameter() {
        return commonParameter;
    }

    public TrackConfiguration setCommonParameter(String commonParameter) {
        this.commonParameter = commonParameter;
        if (!TextUtils.isEmpty(pgyServerUrl)) {
            pgyServerUrl += commonParameter;
        }
        return this;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public TrackConfiguration setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public UPLOAD_CATEGORY getUploadCategory() {
        return _uploadCategory;
    }

    public DATA_PROTOCOL getDataProtocol() {
        return _dataProtocol;
    }
}
