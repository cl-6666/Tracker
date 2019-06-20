package com.cl.tracker_cl.http;


/**
 * 网络应答数据协议格式
 *
 * @param <T>
 */
public class BaseProtocolBean<T> extends BaseBean {

    public int code;
    public String msg;
    public T data;
}
