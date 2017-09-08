package com.ejar.fastbedroom.register.bean;

import java.util.List;

/**
 * Created by json on 2017/9/6.
 * 获取代理点返回
 */

public class PointBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 有代理
     * token : null
     * data : [{"id":37,"schoolid":1738,"agentname":"四川信息职业技术学院","adminid":41,"schoolname":"四川信息职业技术学院","adminname":"111111","couriernum":0,"agentremarks":"2","courierbonus":0.7,"agentbonus":0.2,"superbonus":0.1},{"id":54,"schoolid":1738,"agentname":"川职院","adminid":66,"schoolname":"四川信息职业技术学院","adminname":"倪大业","couriernum":0,"agentremarks":"2","courierbonus":0.7,"agentbonus":0.2,"superbonus":0.1}]
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

    public static class DataBean {
        /**
         * id : 37
         * schoolid : 1738
         * agentname : 四川信息职业技术学院
         * adminid : 41
         * schoolname : 四川信息职业技术学院
         * adminname : 111111
         * couriernum : 0
         * agentremarks : 2
         * courierbonus : 0.7
         * agentbonus : 0.2
         * superbonus : 0.1
         */

        private int id;
        private int schoolid;
        private String agentname;
        private int adminid;
        private String schoolname;
        private String adminname;
        private int couriernum;
        private String agentremarks;
        private double courierbonus;
        private double agentbonus;
        private double superbonus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(int schoolid) {
            this.schoolid = schoolid;
        }

        public String getAgentname() {
            return agentname;
        }

        public void setAgentname(String agentname) {
            this.agentname = agentname;
        }

        public int getAdminid() {
            return adminid;
        }

        public void setAdminid(int adminid) {
            this.adminid = adminid;
        }

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getAdminname() {
            return adminname;
        }

        public void setAdminname(String adminname) {
            this.adminname = adminname;
        }

        public int getCouriernum() {
            return couriernum;
        }

        public void setCouriernum(int couriernum) {
            this.couriernum = couriernum;
        }

        public String getAgentremarks() {
            return agentremarks;
        }

        public void setAgentremarks(String agentremarks) {
            this.agentremarks = agentremarks;
        }

        public double getCourierbonus() {
            return courierbonus;
        }

        public void setCourierbonus(double courierbonus) {
            this.courierbonus = courierbonus;
        }

        public double getAgentbonus() {
            return agentbonus;
        }

        public void setAgentbonus(double agentbonus) {
            this.agentbonus = agentbonus;
        }

        public double getSuperbonus() {
            return superbonus;
        }

        public void setSuperbonus(double superbonus) {
            this.superbonus = superbonus;
        }
    }
}
