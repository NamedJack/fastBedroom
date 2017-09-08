package com.ejar.fastbedroom.register.bean;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ejar.fastbedroom.register.view.PinyinUtils;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by json on 2017/8/16.
 */

public class SchoolBean {

    /**
     *  code : 200
     * okurl : null
     * failUrl : null
     * msg : 成功
     * token : null
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


        private boolean mes;
        private String time;
        private List<School> scholl;

        public boolean isMes() {
            return mes;
        }

        public void setMes(boolean mes) {
            this.mes = mes;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<School> getScholl() {
            return scholl;
        }

        public void setScholl(List<School> scholl) {
            this.scholl = scholl;
        }




        @Override
        public String toString() {
            return "DataBean{" +
                    "mes=" + mes +
                    ", time='" + time + '\'' +
                    ", scholl=" + scholl +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "SchoolBean{" +
                "code='" + code + '\'' +
                ", okurl=" + okurl +
                ", failUrl=" + failUrl +
                ", msg='" + msg + '\'' +
                ", token=" + token +
                ", data=" + data +
                '}';
    }
}
