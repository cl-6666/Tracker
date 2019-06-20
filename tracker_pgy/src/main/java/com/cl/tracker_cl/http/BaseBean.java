package com.cl.tracker_cl.http;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * 实体类的基类
 */
public class BaseBean implements Serializable {

    public String toJson() {
        return toJson(this);
    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static String toJson(Object object, Type type) {
        return new Gson().toJson(object, type);
    }

    public static <T> T fromJson(String jsonStr, Class<T> tClass) {
        return new Gson().fromJson(jsonStr, tClass);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
