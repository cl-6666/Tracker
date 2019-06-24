package com.cl.tracker_cl;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cl.tracker_cl.bean.CommonBean;
import com.cl.tracker_cl.bean.EventBean;
import com.cl.tracker_cl.bean.ISensorsDataAPI;
import com.cl.tracker_cl.bean.SensorsDataDynamicSuperProperties;
import com.cl.tracker_cl.db.TrackerDb;
import com.cl.tracker_cl.http.BaseBean;
import com.cl.tracker_cl.http.IDataListener;
import com.cl.tracker_cl.http.PgyHttp;
import com.cl.tracker_cl.http.UPLOAD_CATEGORY;
import com.cl.tracker_cl.util.LogUtil;
import com.cl.tracker_cl.util.SharedPreferencesUtil;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * 事件管理
 */
public class Tracker implements ISensorsDataAPI {

    private Context mContext;
    private TrackConfiguration config;
    private CommonBean commonInfo;
    private SensorsDataDynamicSuperProperties mDynamicSuperProperties;

    //需要上传的数据
    private List<TrackerDb> mTrackerDbs = new ArrayList<>();

    private final int UPLOAD_EVENT_WHAT = 0xff01;


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
        commonInfo = new CommonBean(mContext);
        LogUtil.e("公共参数：" + commonInfo.getParameters("sss"));
        SharedPreferencesUtil.getInstance().init(context);
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
     * @param duration
     */
    public void addViewEvent(Context context, long duration) {
        addEvent(new EventBean(EventBean.generateViewPath(context), duration));
    }

    /**
     * 添加点击事件
     *
     * @param view
     */
    public void addClickEvent(View view) {
        addEvent(new EventBean(EventBean.generateClickedPath(view)));
    }

    private void addEvent(final EventBean eventInfo) {

        switch (config.getUploadCategory()) {
            case REAL_TIME:
                commitRealTimeEvent(eventInfo);
                break;

            case NEXT_LAUNCH:

                break;

            case NEXT_15_MINUTER:

                break;

            case NEXT_30_MINUTER:

                break;

            case NEXT_KNOWN_MINUTER:

                break;

            case NEXT_CACHE:
                //存数据库
                addData(eventInfo);
                break;

            default:

                break;
        }

    }


    /**
     * 使用缓存处理方式上传
     *
     * @param eventInfo
     */
    private void addData(EventBean eventInfo) {
        String str_json = JSON.toJSONString(commonInfo.toString()); //
        LogUtil.e("实体转化为Json" + str_json);
        LogUtil.e("EventBean:" + eventInfo.toString());
        mTrackerDbs.add(new TrackerDb("点击测试事件", str_json, String.valueOf(eventInfo.getEventTime())));
        //每10条入一次库
        if (mTrackerDbs.size() == 10) {
            LitePal.saveAll(mTrackerDbs);
            mTrackerDbs.removeAll(mTrackerDbs);
        }
        LogUtil.e("条数:" + mTrackerDbs.size());
        // 异步查询示例
        LitePal.findAllAsync(TrackerDb.class).listen(new FindMultiCallback<TrackerDb>() {
            @Override
            public void onFinish(List<TrackerDb> allSongs) {
                LogUtil.e("数据库条数：" + allSongs.size());
//                if (allSongs.size() < 100) return;
                realUploadEventInfo(allSongs);
            }
        });

    }

    /**
     * 通过后台服务实时上传埋点数据
     *
     * @param eventInfo
     */
    private void commitRealTimeEvent(EventBean eventInfo) {

    }

    /**
     * 实时上传埋点数据
     */
    private synchronized void uploadEventInfo() {


    }


    /**
     * 上传埋点数据
     *
     * @param allSongs
     */
    private void realUploadEventInfo(List<TrackerDb> allSongs) {
        LogUtil.e("URL:" + config.getServerUrl());
        //Student为自己定义的javaBean
        PgyHttp.sendJsonRequest(allSongs, config.getServerUrl(), BaseBean.class, new IDataListener<BaseBean>() {
            @Override
            public void onSuccess(BaseBean student) {
                LogUtil.e("成功");

            }

            @Override
            public void onFailure() {
                LogUtil.e("失败");
            }
        });

    }

    /**
     * 提交新设备信息到服务器
     */
    private void submitDeviceInfo() {

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

    @Override
    public boolean isVisualizedAutoTrackEnabled() {
        return false;
    }

    public static class Singleton {
        private final static Tracker instance = new Tracker();
    }


}
