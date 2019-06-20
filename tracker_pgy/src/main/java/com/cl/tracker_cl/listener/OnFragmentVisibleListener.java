package com.cl.tracker_cl.listener;

import android.support.v4.app.Fragment;

/**
 * 监听Fragment显示与否
 */
public interface OnFragmentVisibleListener {

    /**
     * Fragment的onHidden或setUserVisibleHint被调用时触发
     *
     * @param visible
     */
    void onVisibleChanged(Fragment f, boolean visible);
}
