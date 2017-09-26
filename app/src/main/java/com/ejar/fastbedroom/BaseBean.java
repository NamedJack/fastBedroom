package com.ejar.fastbedroom;

import java.util.List;

/**
 * Created by json on 2017/9/6.
 */

public class BaseBean<T> {
    private String code;
    private String okurl;
    private String failUrl;
    private String msg;
    private String token;
    private List<T> data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOkurl() {
        return okurl;
    }

    public void setOkurl(String okurl) {
        this.okurl = okurl;
    }

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<T> getT() {
        return data;
    }

    public void setT(List<T> t) {
        this.data = t;
    }
}
