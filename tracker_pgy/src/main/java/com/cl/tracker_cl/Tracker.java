package com.cl.tracker_cl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cl.tracker_cl.bean.EventBean;
import com.cl.tracker_cl.bean.ISensorsDataAPI;
import com.cl.tracker_cl.bean.SensorsDataDynamicSuperProperties;
import com.cl.tracker_cl.db.TrackerDb;
import com.cl.tracker_cl.http.BaseBean;
import com.cl.tracker_cl.http.IDataListener;
import com.cl.tracker_cl.http.PgyHttp;
import com.cl.tracker_cl.http.UPLOAD_CATEGORY;
import com.cl.tracker_cl.listener.SensorsDataPrivate;
import com.cl.tracker_cl.util.LogUtil;
import com.cl.tracker_cl.util.SensorsDataUtils;
import com.cl.tracker_cl.util.SharedPreferencesUtil;

import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 事件管理
 */
@Keep
public class Tracker implements ISensorsDataAPI {

    public static final String SDK_VERSION = "1.0.0";
    private Context mContext;
    private TrackConfiguration config;
    private SensorsDataDynamicSuperProperties mDynamicSuperProperties;
    private Timer mTimer = new Timer(true);
    private String mActivityTitle;


    //需要上传的数据
    private List<TrackerDb> mTrackerDbs = new ArrayList<>();
    private final int UPLOAD_EVENT_WHAT = 1;
    private final int TIMER_WHAT = 2;   //计时器

    //全埋点
    private static Map<String, Object> mDeviceInfo;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPLOAD_EVENT_WHAT) {
                uploadEventInfo();
                if (config.getUploadCategory() != UPLOAD_CATEGORY.NEXT_LAUNCH) {
                    handler.sendEmptyMessageDelayed(UPLOAD_EVENT_WHAT, config.getUploadCategory().getValue() * 1000);
                }
            } else if (msg.what == TIMER_WHAT) {
                LogUtil.e("计时器任务开启了");
            }
        }
    };


    //时间任务
    private TimerTask task = new TimerTask() {
        public void run() {
            Message msg = new Message();
            msg.what = TIMER_WHAT;
            handler.sendMessage(msg);
        }
    };

    private Tracker() {

    }

    public static Tracker getInstance() {
        return Singleton.instance;
    }

    @Keep
    @SuppressWarnings("UnusedReturnValue")
    public void init(Application context, TrackConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("config can't be null");
        }
        this.mContext = context;
        setTrackerConfig(config);
        mDeviceInfo = SensorsDataPrivate.getDeviceInfo(context.getApplicationContext());
        SharedPreferencesUtil.getInstance().init(context);
        uploadStrategy();
    }


    /**
     * 上传策略
     */
    private void uploadStrategy() {
        switch (config.getUploadCategory()) {
            case NEXT_15_MINUTER:
                mTimer.schedule(task, 0, 1 * 60 * 1000);
                break;

            case NEXT_30_MINUTER:
                mTimer.schedule(task, 0, 30 * 60 * 1000);
                break;

            case NEXT_KNOWN_MINUTER:
                //请求接口做处理
                break;

            case NEXT_LAUNCH:

                break;

            default:

                break;
        }
    }

    private void setTrackerConfig(TrackConfiguration config) {
        if (config != null) {
            this.config = config;
        }
    }

    /**
     * 通过后台服务实时上传埋点数据
     */
    private void commitRealTimeEvent() {

    }


    private void trackEvent(final String eventName, final JSONObject properties) {
        /**
         * 格式格式一
         * {
         *     	"type":"Button",
         *     	"userData":{
         *     		"screen_width":1080,
         *     		"lib":"Android",
         *     		"device_id":"181d7570b925d50a",
         *     		"os":"Android",
         *     		"app_version":"1.0",
         *     		"os_version":"9",
         *     		"manufacturer":"HUAWEI",
         *     		"app_name":"My Application",
         *     		"screen_height":2159,
         *     		"wifi_name":"",
         *     		"sdk_version":"1.0.0",
         *     		"model":"EML-AL00",
         *     		"sdk":"Android",
         *     		"network_type":"WIFI",
         *     		"title":"My Application",
         *     		"ProductID":123456,
         *     		"ProductCatalog":"Laptop Computer",
         *     		"IsAddedToFav":false
         *                },
         *     	"time":1561608401107
         *     }
         */
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("type", eventName);
//            JSONObject sendProperties = new JSONObject(mDeviceInfo);
//            sendProperties.put("title", mActivityTitle);
//            if (properties != null) {
//                SensorsDataPrivate.mergeJSONObject(properties, sendProperties);
//            }
//            jsonObject.put("userData", sendProperties);
//            jsonObject.put("time", System.currentTimeMillis());
//            LogUtil.i("数据:" + SensorsDataPrivate.formatJson(jsonObject.toString()));
//
//            LogUtil.i("新格式:" + SensorsDataPrivate.formatJson(sendProperties.toString()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 数据格式二
         * {
         *     	"screen_width":1080,
         *     	"lib":"Android",
         *     	"device_id":"181d7570b925d50a",
         *     	"os":"Android",
         *     	"app_version":"1.0",
         *     	"os_version":"9",
         *     	"manufacturer":"HUAWEI",
         *     	"app_name":"My Application",
         *     	"screen_height":2159,
         *     	"wifi_name":"",
         *     	"sdk_version":"1.0.0",
         *     	"model":"EML-AL00",
         *     	"sdk":"Android",
         *     	"network_type":"WIFI",
         *     	"title":"My Application",
         *     	"ProductID":123456,
         *     	"ProductCatalog":"Laptop Computer",
         *     	"IsAddedToFav":false,
         *     	"attributes":{
         *     		"type":"Button",
         *     		"time":1561608684746
         *                }
         *     }
         */
        try {
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("eventCode", eventName);
            jsonObject.put("time", System.currentTimeMillis());
            JSONObject sendProperties = new JSONObject(mDeviceInfo);
            sendProperties.put("title", mActivityTitle);
            if (properties != null) {
                SensorsDataPrivate.mergeJSONObject(properties, sendProperties);
            }
            sendProperties.put("attributes", jsonObject);
            sendProperties.put("eventCode", eventName);
            sendProperties.put("user_id", SharedPreferencesUtil.getInstance().getParam("user_id", ""));
            LogUtil.i("数据:" + SensorsDataPrivate.formatJson(sendProperties.toString()));
            addEvent(eventName, SensorsDataPrivate.formatJson(sendProperties.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (mDynamicSuperProperties != null) {
                JSONObject dynamicSuperProperties = mDynamicSuperProperties.getDynamicSuperProperties();
                if (dynamicSuperProperties != null) {
                    SensorsDataUtils.mergeJSONObject(dynamicSuperProperties, properties);
                    LogUtil.i("公共数据:" + SensorsDataPrivate.formatJson(dynamicSuperProperties.toString()));
                }
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    private void addEvent(String eventName, String s) {
        switch (config.getUploadCategory()) {
            case REAL_TIME:
                commitRealTimeEvent();
                break;

            case NEXT_CACHE:
                //存数据库
                addData(eventName, s);
                break;

            default:

                break;
        }
    }

    /**
     * 使用缓存处理方式上传
     *
     * @param eventName
     * @param s
     */
    private void addData(String eventName, String s) {
        mTrackerDbs.add(new TrackerDb(eventName, s, String.valueOf(System.currentTimeMillis())));
        //每5条入一次库
        if (mTrackerDbs.size() == 5) {
            LitePal.saveAll(mTrackerDbs);
            mTrackerDbs.removeAll(mTrackerDbs);
        }
        LogUtil.e("条数:" + mTrackerDbs.size());
        // 异步查询示例
        LitePal.findAllAsync(TrackerDb.class).listen(new FindMultiCallback<TrackerDb>() {
            @Override
            public void onFinish(List<TrackerDb> allSongs) {
                LogUtil.e("数据库条数：" + allSongs.size());
                if (allSongs.size() < 10) return;
                realUploadEventInfo(allSongs);
            }
        });

    }

    /**
     * 实时上传埋点数据
     */
    public synchronized void uploadEventInfo() {

    }

    /**
     * 上传埋点数据
     *
     * @param allSongs
     */
    private void realUploadEventInfo(List<TrackerDb> allSongs) {
        LogUtil.e("URL:" + config.getServerUrl());
        LogUtil.e("数据:" + allSongs.toString());
        PgyHttp.sendJsonRequest(allSongs.toString(), config.getServerUrl(), BaseBean.class, new IDataListener<BaseBean>() {
            @Override
            public void onSuccess(BaseBean student) {
                LogUtil.e("成功:" + student.toString());
                LitePal.deleteAll(TrackerDb.class);
            }

            @Override
            public void onFailure() {
                LogUtil.e("失败");
            }
        });
    }


    /**
     * 注册事件动态公共属性
     *
     * @param dynamicSuperProperties 事件动态公共属性回调接口
     */
    @Override
    public void registerDynamicSuperProperties(SensorsDataDynamicSuperProperties dynamicSuperProperties) {
        mDynamicSuperProperties = dynamicSuperProperties;
    }

    /**
     * 更新 GPS 位置信息
     *
     * @param latitude  纬度
     * @param longitude 经度
     */
    @Override
    public void setGPSLocation(double latitude, double longitude) {
        SharedPreferencesUtil.getInstance().saveParam("latitude", (long) (latitude * Math.pow(10, 6)));
        SharedPreferencesUtil.getInstance().saveParam("longitude", (long) (longitude * Math.pow(10, 6)));
    }

    @Override
    public void getDistinctId(String user_id) {
        if (TextUtils.isEmpty(user_id)) {
            SharedPreferencesUtil.getInstance().remove("user_id");
        } else {
            SharedPreferencesUtil.getInstance().saveParam("user_id", user_id);
        }
    }

    /**
     * Track 事件
     *
     * @param eventName  String 事件名称
     * @param properties JSONObject 事件属性
     * @param eventName  事件的名称
     * @param properties 事件的属性
     */
    @Override
    public void track(@NonNull final String eventName, @NonNull final JSONObject properties) {
        try {
            trackEvent(eventName, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void track(final String eventName) {
        try {
            trackEvent(eventName, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trackTimer(String eventName) {

    }

    @Override
    public void getActivityTitle(Activity activity) {
        this.mActivityTitle = SensorsDataUtils.getActivityTitle(activity);
    }

    @Override
    public void startRequestConfig() {

    }

    @Override
    public void submitDeviceInfo() {

    }

    @Override
    public boolean isVisualizedAutoTrackEnabled() {
        return false;
    }

    public static class Singleton {
        private final static Tracker instance = new Tracker();
    }


    /**
     * 考虑可有可无
     *
     * @param activity
     * @return
     */
    public Tracker getTitle(Activity activity) {
        getActivityTitle(activity);
        return this;
    }

}
