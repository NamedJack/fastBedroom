package com.ejar.fastbedroom.register.bean;

/**
 * Created by json on 2017/8/21.
 * 验证   验证码 返回实体
 */

public class CodeBean {

    /**
     * code : 200
     * msg : 提交成功
     * token : null
     * data : null
     */

    private String code;
    private String msg;
    private Object token;
    private Object data;

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
