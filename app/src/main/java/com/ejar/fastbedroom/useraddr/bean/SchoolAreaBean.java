package com.ejar.fastbedroom.useraddr.bean;

import java.util.List;

/**
 * Created by json on 2017/9/11.
 */

public class SchoolAreaBean {


    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : [{"id":9,"areaname":"南门寝室","courierqty":0,"remarks":"1","areabelong":"北京大学朝阳校区","agentid":30,"fee":0.01},{"id":10,"areaname":"北门寝室","courierqty":0,"remarks":"1","areabelong":"北京大学朝阳校区","agentid":30,"fee":0.01},{"id":11,"areaname":"虚楚斋","courierqty":0,"remarks":"1","areabelong":"北京大学朝阳校区","agentid":30,"fee":0.01},{"id":12,"areaname":"门清楼","courierqty":0,"remarks":"1","areabelong":"北京大学朝阳校区","agentid":30,"fee":0.01}]
     */

    private String code;
    private String okurl;
    private String failUrl;
    private String msg;
    private String token;
    private List<DataBean> data;

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

    public Object getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 9
         * areaname : 南门寝室
         * courierqty : 0
         * remarks : 1
         * areabelong : 北京大学朝阳校区
         * agentid : 30
         * fee : 0.01
         */

        private int id;
        private String areaname;
        private int courierqty;
        private String remarks;
        private String areabelong;
        private int agentid;
        private double fee;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }

        public int getCourierqty() {
            return courierqty;
        }

        public void setCourierqty(int courierqty) {
            this.courierqty = courierqty;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getAreabelong() {
            return areabelong;
        }

        public void setAreabelong(String areabelong) {
            this.areabelong = areabelong;
        }

        public int getAgentid() {
            return agentid;
        }

        public void setAgentid(int agentid) {
            this.agentid = agentid;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }
    }
}
