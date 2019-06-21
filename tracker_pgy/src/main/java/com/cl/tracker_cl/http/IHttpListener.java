package com.cl.tracker_cl.http;

import java.io.InputStream;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-21
 * 描述：封装响应
 * 修订历史：
 */
public interface IHttpListener {

    //接受上一个接口的结果
    void onSuccess(InputStream inputStream);

    void onFailure();

}
