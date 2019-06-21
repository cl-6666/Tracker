package com.cl.tracker_cl.http;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-21
 * 描述：封装请求
 * 修订历史：
 */
public interface IHttpRequest {

    void setUrl(String url);

    void setRequestData(byte[] requestData);

    void execute();

    //需要设置请求和响应两个接口之间的关系
    void setHttpCallBack(IHttpListener httpListener);

}
