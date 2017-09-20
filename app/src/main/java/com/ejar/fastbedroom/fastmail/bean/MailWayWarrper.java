package com.ejar.fastbedroom.fastmail.bean;

import java.util.List;

/**
 * Created by json on 2017/9/12.
 */

public class MailWayWarrper {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : [{"id":1,"expressname":"天天"},{"id":2,"expressname":"中通"},{"id":4,"expressname":"汇通"}]
     */

    private String code;
    private Object okurl;
    private Object failUrl;
    private String msg;
    private Object token;
    private List<MailWayBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getOkurl() {
        return okurl;
    }

    public void setOkurl(Object okurl) {
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

    public List<MailWayBean> getData() {
        return data;
    }

    public void setData(List<MailWayBean> data) {
        this.data = data;
    }

}
