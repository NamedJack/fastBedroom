package com.ejar.fastbedroom.useraddr.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by json on 2017/9/8.
 */

public class AddressBean {


    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : [{"id":16,"receivename":"刚刚","receivetel":"13548754875","receivesite":"到底","dpareaid":9,"dpareaname":"南门寝室","userid":17,"state":2},{"id":17,"receivename":"呵呵","receivetel":"13548754875","receivesite":"说的","dpareaid":10,"dpareaname":"北门寝室","userid":17,"state":1},{"id":18,"receivename":"刚刚","receivetel":"13584848484","receivesite":"刚刚","dpareaid":9,"dpareaname":"南门寝室","userid":17,"state":1},{"id":20,"receivename":"你是智障","receivetel":"18081231464","receivesite":"my破傻X我现在","dpareaid":12,"dpareaname":"门清楼","userid":17,"state":1},{"id":21,"receivename":"高学历","receivetel":"18081231464","receivesite":"里咯红","dpareaid":12,"dpareaname":"门清楼","userid":17,"state":1},{"id":22,"receivename":"高学历","receivetel":"18081231464","receivesite":"里咯红","dpareaid":12,"dpareaname":"门清楼","userid":17,"state":1},{"id":26,"receivename":"何久腾","receivetel":"17711405865","receivesite":"17栋211","dpareaid":9,"dpareaname":"南门寝室","userid":17,"state":1}]
     */

    private String code;
    private Object okurl;
    private Object failUrl;
    private String msg;
    private Object token;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 16 用于修改，删除等等
         * receivename : 刚刚
         * receivetel : 13548754875
         * receivesite : 到底
         * dpareaid : 9
         * dpareaname : 南门寝室
         * userid : 17
         * state : 2
         */

        private int id;
        private String receivename;
        private String receivetel;
        private String receivesite;
        private int dpareaid;
        private String dpareaname;
        private int userid;
        private int state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReceivename() {
            return receivename;
        }

        public void setReceivename(String receivename) {
            this.receivename = receivename;
        }

        public String getReceivetel() {
            return receivetel;
        }

        public void setReceivetel(String receivetel) {
            this.receivetel = receivetel;
        }

        public String getReceivesite() {
            return receivesite;
        }

        public void setReceivesite(String receivesite) {
            this.receivesite = receivesite;
        }

        public int getDpareaid() {
            return dpareaid;
        }

        public void setDpareaid(int dpareaid) {
            this.dpareaid = dpareaid;
        }

        public String getDpareaname() {
            return dpareaname;
        }

        public void setDpareaname(String dpareaname) {
            this.dpareaname = dpareaname;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
