package com.ejar.fastbedroom.register.bean;

/**
 * Created by json on 2017/8/21.
 * 注册界面  获取验证码 返回
 */

public class RegisterCodeBean {

    /**
     * code : 202
     * okurl : null
     * failUrl : null
     * msg : 该账号已被注册
     * token : null
     * data : null
     */

    private String code;
    private String okurl;
    private String failUrl;
    private String msg;
    private String token;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getOkurl() {
        return okurl;
    }

    public void setOkurl(String okurl) {
        this.okurl = okurl;
    }

    public Object getFailUrl() {
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

    public Object getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
