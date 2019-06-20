package com.cl.tracker_cl;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.cl.tracker_cl.bean.CommonBean;
import com.cl.tracker_cl.bean.ConfigBean;
import com.cl.tracker_cl.bean.EventBean;
import com.cl.tracker_cl.db.TrackData;
import com.cl.tracker_cl.http.BaseBean;
import com.cl.tracker_cl.http.BaseProtocolBean;
import com.cl.tracker_cl.http.HttpManager;
import com.cl.tracker_cl.http.UPLOAD_CATEGORY;
import com.cl.tracker_cl.http.UploadEventService;
import com.cl.tracker_cl.util.LogUtil;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.util.ArrayList;
import java.util.List;


/**
 * 事件管理
 */
public class Tracker {

    /**
     * 需要收集的事件列表
     */
    private List<String> validEventPathList = null;
    /**
     * 保存产生的事件
     */
    private List<EventBean> eventList = new ArrayList<>();

    private Context mContext;
    private TrackConfiguration config;
    private CommonBean commonInfo;
    private TrackData mTrackData;

    private final int UPLOAD_EVENT_WHAT = 0xff01;
    private final int MAX_EVENT_COUNT = 50;
    private final int DEFAULT_CLEAR_COUNT = 30;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPLOAD_EVENT_WHAT) {
                uploadEventInfo();
                if (config.getUploadCategory() != UPLOAD_CATEGORY.NEXT_LAUNCH) {
                    handler.sendEmptyMessageDelayed(UPLOAD_EVENT_WHAT, config.getUploadCategory().getValue() * 1000);
                }
            }
        }
    };

    private Tracker() {

    }

    public static Tracker getInstance() {
        return Singleton.instance;
    }

    public void init(Application context, TrackConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("config can't be null");
        }
        this.mContext = context;
        setTrackerConfig(config);
        //监听生命周期
//        context.registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
        commonInfo = new CommonBean(mContext);
        LogUtil.e("公共参数：" + commonInfo.getParameters("sss"));
    }

    private void setTrackerConfig(TrackConfiguration config) {
        if (config != null) {
            this.config = config;
        }
    }

    /**
     * 从服务器请求埋点的配置信息
     */
    private void startRequestConfig() {
        HttpManager.getInstance(mContext).getQuery(config.getConfigUrl(), ConfigBean.class,
                new HttpManager.OnRequestListener<ConfigBean.DataBean>() {
                    @Override
                    public void onSuccess(ConfigBean.DataBean result) {
                        if (result != null) {
                            setTrackerConfig(result.baseConfig);
                            validEventPathList = getValidEventList(result.validEventList);
                        }
                        handler.sendEmptyMessage(UPLOAD_EVENT_WHAT);
                        LogUtil.i("get config success");
                    }

                    @Override
                    public void onError(int code, String errMsg) {
                        validEventPathList = getValidEventList(null);
                        LogUtil.i("get config failed " + errMsg);
                    }
                });
    }

    /**
     * 获取需要收集的埋点路径列表
     *
     * @param validEventList
     * @return
     */
    private List<String> getValidEventList(List<EventBean> validEventList) {
        List<String> validEventPathList = new ArrayList<>();
        if (validEventList != null && validEventList.size() > 0) {
            for (EventBean event : validEventList) {
                validEventPathList.add(event.getPath());
            }
        }
        return validEventPathList;
    }

    /**
     * 添加浏览页面事件
     *
     * @param context
     * @param fragment
     * @param duration
     */
    public void addViewEvent(Context context, Fragment fragment, long duration) {
        addEvent(new EventBean(EventBean.generateViewPath(context, fragment), duration));
    }

    /**
     * 添加点击事件
     *
     * @param view
     * @param fragment
     */
    public void addClickEvent(View view, Fragment fragment) {
        addEvent(new EventBean(EventBean.generateClickedPath(view, fragment)));
    }

    private void addEvent(final EventBean eventInfo) {
        LogUtil.i("EventBean:" + eventInfo.toString());
        switch (config.getUploadCategory()) {
            case REAL_TIME:

                break;

            case NEXT_LAUNCH:

                break;

            case NEXT_15_MINUTER:

                break;

            case NEXT_30_MINUTER:

                break;

            case NEXT_KNOWN_MINUTER:

                break;

            default:

                break;

//            UploadEventService.enter(context, config.getHostName(), config.getHostPort(), null);
        }

        if (config.getUploadCategory() == UPLOAD_CATEGORY.REAL_TIME) {
            commitRealTimeEvent(eventInfo);
        } else {
            if (validEventPathList != null && validEventPathList.size() > 0
                    && !validEventPathList.contains(eventInfo.getPath())) {
                return;
            }
            eventList.add(eventInfo);

//            DatabaseManager.getInstance(mContext.getApplicationContext()).insertData(eventInfo);

            if (eventList.size() >= MAX_EVENT_COUNT) {
                eventList.remove(eventList.subList(0, DEFAULT_CLEAR_COUNT));
            }
        }
    }

    /**
     * 通过后台服务实时上传埋点数据
     *
     * @param eventInfo
     */
    private void commitRealTimeEvent(EventBean eventInfo) {
        UploadEventService.enter(mContext, eventInfo);
    }

    /**
     * 上传埋点数据
     */
    private synchronized void uploadEventInfo() {


    }

    private byte[] convertDataToJson(List<EventBean> eventList) {
        return BaseBean.toJson(eventList, new TypeToken<List<EventBean>>() {
        }.getType()).getBytes();
    }


    /**
     * 上传埋点数据
     *
     * @param data
     */
    private void realUploadEventInfo(byte[] data) {
        if (data == null || data.length == 0) {
            return;
        }
        HttpManager.getInstance(mContext).postQuery(config.getServerUrl(), data, BaseProtocolBean.class,
                new HttpManager.OnRequestListener() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtil.i("event info upload success");
                    }

                    @Override
                    public void onError(int code, String errMsg) {
                        LogUtil.i("event info upload failed " + errMsg);
                    }
                });
    }

    /**
     * 提交新设备信息到服务器
     */
    private void submitDeviceInfo() {
        String deviceInfo = config.getDeviceInfo();
        if (TextUtils.isEmpty(deviceInfo)) {
            deviceInfo = commonInfo.getParameters(config.getNewDeviceUrl().contains("?") ? "&" : "?");
        }
        HttpManager.getInstance(mContext).postQuery(config.getNewDeviceUrl(), deviceInfo,
                BaseProtocolBean.class, new HttpManager.OnRequestListener() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtil.i("deviceInfo submit success");
                    }

                    @Override
                    public void onError(int code, String errMsg) {
                        LogUtil.i("deviceInfo submit failed " + errMsg);
                    }
                });
    }

    public static class Singleton {
        private final static Tracker instance = new Tracker();
    }


    /**
     * 更新 GPS 位置信息
     *
     * @param latitude
     * @param longitude
     */
    public void setGPSLocation(double latitude, double longitude) {
        mTrackData = new TrackData();
        mTrackData.setLatitude(String.valueOf(latitude));
        mTrackData.setLongitude(String.valueOf(longitude));
        mTrackData.save();

    }


    /**
     * 标识用户登录的id
     *
     * @param logType
     */
    public void setLogType(String logType) {

    }


}
