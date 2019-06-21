package com.cl.tracker_cl.bean;

import com.cl.tracker_cl.TrackConfiguration;

import java.util.List;


/**
 * Description: 从服务器获取配置信息
 */
public class ConfigBean extends BaseProtocolBean<ConfigBean.DataBean> {

    public static class DataBean {
        public TrackConfiguration baseConfig;
        public List<EventBean> validEventList;
    }
}
