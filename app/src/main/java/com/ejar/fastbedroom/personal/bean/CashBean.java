package com.ejar.fastbedroom.personal.bean;

/**
 * Created by json on 2017/9/27.
 */

public class CashBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 操作成功！
     * token : null
     * data : {"yue":"98669.38"}
     */

    private String code;
    private Object okurl;
    private Object failUrl;
    private String msg;
    private Object token;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * yue : 98669.38
         */

        private String yue;

        public String getYue() {
            return yue;
        }

        public void setYue(String yue) {
            this.yue = yue;
        }
    }
}
