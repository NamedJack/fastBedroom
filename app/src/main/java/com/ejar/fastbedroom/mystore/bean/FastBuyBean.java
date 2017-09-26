package com.ejar.fastbedroom.mystore.bean;

import java.io.Serializable;

/**
 * Created by json on 2017/9/18.
 */

public class FastBuyBean {

    /**
     * code : 200
     * success : true
     * msg : 订单生成成功！
     * data : {"msg":"请求成功","sendPrice":1.2,"code":"200","totalManey":13.2,"goodsPrices":12,"success":true,"goodsOrderNo":"zy20170905104155283"}
     */

    private String code;
    private boolean success;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * msg : 请求成功
         * sendPrice : 1.2
         * code : 200
         * totalManey : 13.2
         * goodsPrices : 12
         * success : true
         * goodsOrderNo : zy20170905104155283
         */

        private String dpareaname;
        private String revname;
        private String revtel;
        private String address;
        private String msg;
        private double sendPrice;
        private String code;
        private double totalManey;
        private int goodsPrices;
        private boolean success;
        private String goodsOrderNo;

        public String getDpareaname() {
            return dpareaname;
        }

        public void setDpareaname(String dpareaname) {
            this.dpareaname = dpareaname;
        }

        public String getRevname() {
            return revname;
        }

        public void setRevname(String revname) {
            this.revname = revname;
        }

        public String getRevtel() {
            return revtel;
        }

        public void setRevtel(String revtel) {
            this.revtel = revtel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public double getSendPrice() {
            return sendPrice;
        }

        public void setSendPrice(double sendPrice) {
            this.sendPrice = sendPrice;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getTotalManey() {
            return totalManey;
        }

        public void setTotalManey(double totalManey) {
            this.totalManey = totalManey;
        }

        public int getGoodsPrices() {
            return goodsPrices;
        }

        public void setGoodsPrices(int goodsPrices) {
            this.goodsPrices = goodsPrices;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getGoodsOrderNo() {
            return goodsOrderNo;
        }

        public void setGoodsOrderNo(String goodsOrderNo) {
            this.goodsOrderNo = goodsOrderNo;
        }
    }
}
