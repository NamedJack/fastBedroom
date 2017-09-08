package com.ejar.fastbedroom.personal;

/**
 * Created by json on 2017/8/28.
 */

public class ResultBean {

    /**
     * code : 200
     * msg : 输入成功
     * token : null
     * data : null
     */

    private String code;
    private String msg;
    private String token;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
