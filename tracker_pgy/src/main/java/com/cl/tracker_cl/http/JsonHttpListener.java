package com.cl.tracker_cl.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-21
 * 描述：Json版本的httpListener
 * 在该类中要注意的事就是回调结果给用户时要进行线程的切换（使用Handler），
 * 并且要将返回的json字符串转换成泛型对象（该对象由用户自定义）。
 * 修订历史：
 */
public class JsonHttpListener<M> implements IHttpListener {

    Class<M> responseClass;
    private IDataListener<M> dataListener;

    public JsonHttpListener(Class<M> responseClass, IDataListener<M> dataListener) {
        this.responseClass = responseClass;
        this.dataListener = dataListener;
    }

    //用于切换线程
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onSuccess(InputStream inputStream) {
        //获取响应结果，把byte数据转换成String数据
        String content = getContent(inputStream);
        //将json字符串转换成对象
        final M response = JSON.parseObject(content, responseClass);
        //把结果传送到调用层
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null) {
                    dataListener.onSuccess(response);
                }

            }
        });

    }

    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null) {
                    dataListener.onFailure();
                }

            }
        });

    }

    /**
     * 将流转换成字符串
     *
     * @param inputStream
     * @return
     */
    private String getContent(InputStream inputStream) {
        String content = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
