package com.ejar.fastbedroom.buycar.bean;

import java.io.Serializable;

/**
 * Created by json on 2017/9/8.
 */

public class PayOrder {
    /**
     * code : 200
     * data : {"sendPrice":0,"address":"地下室","totalManey":114,"goodsPrices":114,"revtel":"18081231464","dpareaname":"南门寝室","goodsOrderNo":"zy20170924103147243","revname":"啊啊啊"}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * sendPrice : 0.0
         * address : 地下室
         * totalManey : 114.0
         * goodsPrices : 114.0
         * revtel : 18081231464
         * dpareaname : 南门寝室
         * goodsOrderNo : zy20170924103147243
         * revname : 啊啊啊
         */

        private double sendPrice;
        private String address;
        private double totalManey;
        private double goodsPrices;
        private String revtel;
        private String dpareaname;
        private String goodsOrderNo;
        private String revname;

        public double getSendPrice() {
            return sendPrice;
        }

        public void setSendPrice(double sendPrice) {
            this.sendPrice = sendPrice;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getTotalManey() {
            return totalManey;
        }

        public void setTotalManey(double totalManey) {
            this.totalManey = totalManey;
        }

        public double getGoodsPrices() {
            return goodsPrices;
        }

        public void setGoodsPrices(double goodsPrices) {
            this.goodsPrices = goodsPrices;
        }

        public String getRevtel() {
            return revtel;
        }

        public void setRevtel(String revtel) {
            this.revtel = revtel;
        }

        public String getDpareaname() {
            return dpareaname;
        }

        public void setDpareaname(String dpareaname) {
            this.dpareaname = dpareaname;
        }

        public String getGoodsOrderNo() {
            return goodsOrderNo;
        }

        public void setGoodsOrderNo(String goodsOrderNo) {
            this.goodsOrderNo = goodsOrderNo;
        }

        public String getRevname() {
            return revname;
        }

        public void setRevname(String revname) {
            this.revname = revname;
        }
    }

}
