package com.ejar.fastbedroom.mybook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by json on 2017/9/22.
 */

public class StoreOrderBean {

    /**
     * date : {"msg":"请求成功","code":"200","data":[{"id":1,"goodsOrderNo":"zy201708111335221101234","goodsPrice":190,"userId":16,"addressId":15,"type":2,"commission":10,"platform":5,"courierPrice":5,"state":1,"xdTime":"2017-08-19 11:22:33","payTime":null,"jdTime":null,"sdTime":null,"endTime":null,"payType":null,"courierid":null,"curierName":null,"sendPrice":0,"totalMoney":190,"agentId":30,"orderMid":null,"show":1,"address":{"id":15,"receivename":"YukinoshitaYukino","receivetel":"13550866814","receivesite":"0103","dpareaid":9,"dpareaname":null,"userid":16,"state":1}}],"success":true,"pagetotle":1}
     * code : 200
     */

    private DateBean date;
    private String code;

    public DateBean getDate() {
        return date;
    }

    public void setDate(DateBean date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DateBean {
        /**
         * msg : 请求成功
         * code : 200
         * data : [{"id":1,"goodsOrderNo":"zy201708111335221101234","goodsPrice":190,"userId":16,"addressId":15,"type":2,"commission":10,"platform":5,"courierPrice":5,"state":1,"xdTime":"2017-08-19 11:22:33","payTime":null,"jdTime":null,"sdTime":null,"endTime":null,"payType":null,"courierid":null,"curierName":null,"sendPrice":0,"totalMoney":190,"agentId":30,"orderMid":null,"show":1,"address":{"id":15,"receivename":"YukinoshitaYukino","receivetel":"13550866814","receivesite":"0103","dpareaid":9,"dpareaname":null,"userid":16,"state":1}}]
         * success : true
         * pagetotle : 1
         */

        private String msg;
        private String code;
        private boolean success;
        private int pagetotle;
        private List<DataBean> data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

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

        public int getPagetotle() {
            return pagetotle;
        }

        public void setPagetotle(int pagetotle) {
            this.pagetotle = pagetotle;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable{
            /**
             * id : 1
             * goodsOrderNo : zy201708111335221101234
             * goodsPrice : 190.0
             * userId : 16
             * addressId : 15
             * type : 2
             * commission : 10.0
             * platform : 5.0
             * courierPrice : 5.0
             * state : 1
             * xdTime : 2017-08-19 11:22:33
             * payTime : null
             * jdTime : null
             * sdTime : null
             * endTime : null
             * payType : null
             * courierid : null
             * curierName : null
             * sendPrice : 0.0
             * totalMoney : 190.0
             * agentId : 30
             * orderMid : null
             * show : 1
             * address : {"id":15,"receivename":"YukinoshitaYukino","receivetel":"13550866814","receivesite":"0103","dpareaid":9,"dpareaname":null,"userid":16,"state":1}
             */

            private int id;
            private String goodsOrderNo;
            private double goodsPrice;
            private int userId;
            private int addressId;
            private int type;
            private double commission;
            private double platform;
            private double courierPrice;
            private int state;
            private String xdTime;
            private String payTime;
            private String jdTime;
            private String sdTime;
            private String endTime;
            private String payType;
            private String courierid;
            private String curierName;//配送员名字
            private double sendPrice;
            private double totalMoney;
            private int agentId;
            private String orderMid;
            private int show;
            private AddressBean address;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoodsOrderNo() {
                return goodsOrderNo;
            }

            public void setGoodsOrderNo(String goodsOrderNo) {
                this.goodsOrderNo = goodsOrderNo;
            }

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getAddressId() {
                return addressId;
            }

            public void setAddressId(int addressId) {
                this.addressId = addressId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public double getCommission() {
                return commission;
            }

            public void setCommission(double commission) {
                this.commission = commission;
            }

            public double getPlatform() {
                return platform;
            }

            public void setPlatform(double platform) {
                this.platform = platform;
            }

            public double getCourierPrice() {
                return courierPrice;
            }

            public void setCourierPrice(double courierPrice) {
                this.courierPrice = courierPrice;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getXdTime() {
                return xdTime;
            }

            public void setXdTime(String xdTime) {
                this.xdTime = xdTime;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public String getJdTime() {
                return jdTime;
            }

            public void setJdTime(String jdTime) {
                this.jdTime = jdTime;
            }

            public String getSdTime() {
                return sdTime;
            }

            public void setSdTime(String sdTime) {
                this.sdTime = sdTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getCourierid() {
                return courierid;
            }

            public void setCourierid(String courierid) {
                this.courierid = courierid;
            }

            public String getCurierName() {
                return curierName;
            }

            public void setCurierName(String curierName) {
                this.curierName = curierName;
            }

            public double getSendPrice() {
                return sendPrice;
            }

            public void setSendPrice(double sendPrice) {
                this.sendPrice = sendPrice;
            }

            public double getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(double totalMoney) {
                this.totalMoney = totalMoney;
            }

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public String getOrderMid() {
                return orderMid;
            }

            public void setOrderMid(String orderMid) {
                this.orderMid = orderMid;
            }

            public int getShow() {
                return show;
            }

            public void setShow(int show) {
                this.show = show;
            }

            public AddressBean getAddress() {
                return address;
            }

            public void setAddress(AddressBean address) {
                this.address = address;
            }

            public static class AddressBean implements Serializable{
                /**
                 * id : 15
                 * receivename : YukinoshitaYukino
                 * receivetel : 13550866814
                 * receivesite : 0103
                 * dpareaid : 9
                 * dpareaname : null
                 * userid : 16
                 * state : 1
                 */

                private int id;
                private String receivename;
                private String receivetel;
                private String receivesite;
                private int dpareaid;
                private String dpareaname;
                private int userid;
                private int state;


                @Override
                public String toString() {
                    return "AddressBean{" +
                            "id=" + id +
                            ", receivename='" + receivename + '\'' +
                            ", receivetel='" + receivetel + '\'' +
                            ", receivesite='" + receivesite + '\'' +
                            ", dpareaid=" + dpareaid +
                            ", dpareaname='" + dpareaname + '\'' +
                            ", userid=" + userid +
                            ", state=" + state +
                            '}';
                }

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
    }
}
