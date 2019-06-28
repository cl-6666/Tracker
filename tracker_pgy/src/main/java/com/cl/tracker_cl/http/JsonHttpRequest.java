package com.cl.tracker_cl.http;

import com.cl.tracker_cl.util.LogUtil;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-21
 * 描述：Json版Http请求
 * 修订历史：
 */
public class JsonHttpRequest implements IHttpRequest {
    private String url;
    private byte[] requestData;
    private IHttpListener httpListener;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    //原生的网络操作在这里实现
    @Override
    public void execute() {
        httpUrlconnPost();
    }

    private HttpURLConnection urlConnection = null;

    public void httpUrlconnPost() {
        URL url = null;
        try {
            url = new URL(this.url);
            //打开http连接
            urlConnection = (HttpURLConnection) url.openConnection();
            //设置连接的超时时间
            urlConnection.setConnectTimeout(6000);
            //不使用缓存
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            //响应的超时时间
            urlConnection.setReadTimeout(3000);
            //设置这个连接是否可以写入数据
            urlConnection.setDoInput(true);
            //设置这个连接是否可以输出数据
            urlConnection.setDoOutput(true);
            //设置请求的方式
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.connect();

            //使用字节流发送数据
            OutputStream out = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            if (requestData != null) {
                //把字节数组的数据写入缓冲区
                bos.write(requestData);
            }
            //刷新缓冲区，发送数据
            bos.flush();
            out.close();
            bos.close();

            LogUtil.e("code码:" + urlConnection.getResponseCode());
            //获得服务器响应
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = urlConnection.getInputStream();
                //回调监听器的listener方法
                httpListener.onSuccess(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }
}
