package com.cl.tracker_cl.db;

import org.litepal.crud.LitePalSupport;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-20
 * 描述：
 * 修订历史：
 */
public class TrackData extends LitePalSupport {

    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;

    /**
     * 用户ID
     */
    private String user_id;


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
