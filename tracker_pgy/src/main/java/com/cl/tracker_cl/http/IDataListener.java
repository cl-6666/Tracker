package com.cl.tracker_cl.http;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-21
 * 描述： 回调调用层的接口,数据返回的统一接口
 * 修订历史：
 */
public interface IDataListener<M> {
    void onSuccess(M m);

    void onFailure();
}
