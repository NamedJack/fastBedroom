package com.ejar.baseframe.utils.rx;

/**
 * Created by json on 2017/8/10.
 */

public class Message {
    private int code;
    private Object object;

    public Message() {
    }

    public Message(int code, Object o) {
        this.code = code;
        this.object = o;
    }
    //getter and setter

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
