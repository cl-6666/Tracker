package com.cl.tracker_cl.http;


/**
 * 实体类的基类
 */
public class BaseBean {

    /**
     * code : 0
     * msg : 成功
     * data : null
     */

    private int code;
    private String msg;
    private String data;


    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
