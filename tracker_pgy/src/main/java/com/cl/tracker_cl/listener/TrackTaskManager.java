package com.cl.tracker_cl.listener;

import com.cl.tracker_cl.util.LogUtil;

import java.util.LinkedList;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-26
 * 描述：
 * 修订历史：
 */
public class TrackTaskManager {

    /**
     * 请求线程队列
     */
    private final LinkedList<Runnable> mTrackEventTasks;
    private final LinkedList<Runnable> mEventDBTasks;
    private static TrackTaskManager trackTaskManager;

    public static synchronized TrackTaskManager getInstance() {
        try {
            if (null == trackTaskManager) {
                trackTaskManager = new TrackTaskManager();
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return trackTaskManager;
    }

    public TrackTaskManager() {
        mTrackEventTasks = new LinkedList<>();
        mEventDBTasks = new LinkedList<>();
    }

    public void addTrackEventTask(Runnable trackEvenTask) {
        try {
            synchronized (mTrackEventTasks) {
                mTrackEventTasks.addLast(trackEvenTask);
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    public Runnable getTrackEventTask() {
        try {
            synchronized (mTrackEventTasks) {
                if (mTrackEventTasks.size() > 0) {
                    return mTrackEventTasks.removeFirst();
                }
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public void addEventDBTask(Runnable evenDBTask) {
        try {
            synchronized (mEventDBTasks) {
                mEventDBTasks.addLast(evenDBTask);
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    public Runnable getEventDBTask() {
        try {
            synchronized (mEventDBTasks) {
                if (mEventDBTasks.size() > 0) {
                    return mEventDBTasks.removeFirst();
                }
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

}
