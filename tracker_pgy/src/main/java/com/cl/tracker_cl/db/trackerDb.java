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
public class trackerDb extends LitePalSupport {

    private int type;
    private String eventsData;
    private String eventTime;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEventsData() {
        return eventsData;
    }

    public void setEventsData(String eventsData) {
        this.eventsData = eventsData;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
