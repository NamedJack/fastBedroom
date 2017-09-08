package com.ejar.fastbedroom.mystore.bean;

/**
 * Created by json on 2017/9/6.
 * 订单提交返回
 */

public class OrderPostBean {

    /**
     * msg : {"sendPrice":0,"money":11.25,"totalManey":80,"goodsPrice":80,"goodsOrderNo":"zy2017082116340091915519"}
     * code : 200
     */

    private MsgBean msg;
    private String code;

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class MsgBean {
        /**
         * sendPrice : 0
         * money : 11.25
         * totalManey : 80
         * goodsPrice : 80
         * goodsOrderNo : zy2017082116340091915519
         */

        private int sendPrice;
        private double money;
        private int totalManey;
        private int goodsPrice;
        private String goodsOrderNo;

        public int getSendPrice() {
            return sendPrice;
        }

        public void setSendPrice(int sendPrice) {
            this.sendPrice = sendPrice;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getTotalManey() {
            return totalManey;
        }

        public void setTotalManey(int totalManey) {
            this.totalManey = totalManey;
        }

        public int getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getGoodsOrderNo() {
            return goodsOrderNo;
        }

        public void setGoodsOrderNo(String goodsOrderNo) {
            this.goodsOrderNo = goodsOrderNo;
        }
    }
}
