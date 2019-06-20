package com.cl.tracker_cl.permissions;

import android.support.annotation.NonNull;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-20
 * 描述：
 * 修订历史：
 */
public interface PermissionListener {

    /**
     * 通过授权
     *
     * @param permission
     */
    void permissionGranted(@NonNull String[] permission);

    /**
     * 拒绝授权
     *
     * @param permission
     */
    void permissionDenied(@NonNull String[] permission);

}
