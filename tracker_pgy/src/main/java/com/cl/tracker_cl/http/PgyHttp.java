package com.cl.tracker_cl.http;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-21
 * 描述：用户请求的统一接口
 * 修订历史：
 */
public class PgyHttp {
    public static <T, M> void sendJsonRequest(T requestInfo, String url, Class<M> response, IDataListener<M> dataListener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        IHttpListener httpListener = new JsonHttpListener<>(response, dataListener);
        HttpTask<T> httpTask = new HttpTask<T>(requestInfo, url, httpRequest, httpListener);
        ThreadPoolManager.getSingleIntance().execute(httpTask);
    }
}
