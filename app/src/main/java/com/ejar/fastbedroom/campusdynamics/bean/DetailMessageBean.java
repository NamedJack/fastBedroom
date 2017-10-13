package com.ejar.fastbedroom.campusdynamics.bean;

/**
 * Created by json on 2017/10/10.
 */

public class DetailMessageBean {

    /**
     * code : 200
     * okurl : mng/dynamicxs?id=10
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : null
     */

    private String code;
    private String okurl;
    private Object failUrl;
    private String msg;
    private Object token;
    private Object data;

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

    public Object getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(Object failUrl) {
        this.failUrl = failUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
