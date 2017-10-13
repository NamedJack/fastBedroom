package com.ejar.fastbedroom.personal.bean;

import java.util.List;

/**
 * Created by json on 2017/9/30.
 */

public class MoneyDetailBean {

    /**
     * code : 200
     * data : [{"id":141,"uid":646,"grade":2,"eltime":"2017-09-30 09:58:34","usename":"这周","usertel":"13795812390","state":1},{"id":142,"uid":646,"grade":2,"eltime":"2017-09-30 09:58:43","usename":"这周","usertel":"13795812390","state":1},{"id":143,"uid":646,"grade":4,"eltime":"2017-09-30 09:58:59","usename":"这周","usertel":"13795812390","state":1},{"id":144,"uid":646,"grade":2,"eltime":"2017-09-30 09:59:12","usename":"这周","usertel":"13795812390","state":1},{"id":145,"uid":646,"grade":2,"eltime":"2017-09-30 10:18:56","usename":"这周","usertel":"13795812390","state":2},{"id":146,"uid":646,"grade":2,"eltime":"2017-09-30 16:02:21","usename":"这周","usertel":"13795812390","state":1}]
     * totalPage : 1
     */

    private String code;
    private int totalPage;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 141
         * uid : 646
         * grade : 2.0
         * eltime : 2017-09-30 09:58:34
         * usename : 这周
         * usertel : 13795812390
         * state : 1
         */

        private int id;
        private int uid;
        private double grade;
        private String eltime;
        private String usename;
        private String usertel;
        private int state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public double getGrade() {
            return grade;
        }

        public void setGrade(double grade) {
            this.grade = grade;
        }

        public String getEltime() {
            return eltime;
        }

        public void setEltime(String eltime) {
            this.eltime = eltime;
        }

        public String getUsename() {
            return usename;
        }

        public void setUsename(String usename) {
            this.usename = usename;
        }

        public String getUsertel() {
            return usertel;
        }

        public void setUsertel(String usertel) {
            this.usertel = usertel;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
