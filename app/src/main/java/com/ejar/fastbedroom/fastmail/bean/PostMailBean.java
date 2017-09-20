package com.ejar.fastbedroom.fastmail.bean;

import java.io.Serializable;

/**
 * Created by json on 2017/9/13.
 */

public class PostMailBean {


    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 提交成功，请支付
     * token : null
     * data : {"id":161,"weight":0,"amount":10,"price":0.1,"content":null,"expressname":"中通","expressorder":"258,258,258,258,258,258,258,258,258,258","orderId":"ps20170913105245803","uid":17,"revname":"走着走着","revtel":"13795812390","sex":2,"agentid":30,"revaid":9,"courierId":0,"area":"南门寝室","expresstype":null,"address":"五楼m","orderinitial":"2017-09-13 10:52:45","orderTime":null,"sendTime":null,"awaitTime":null,"arriveTime":null,"state":0,"remarks":null,"paystate":0,"courier":null,"user":null,"type":null,"businessID":null,"busTel":null,"busAddress":null}
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

    public static class DataBean implements Serializable{
        /**
         * id : 161
         * weight : 0.0
         * amount : 10
         * price : 0.1
         * content : null
         * expressname : 中通
         * expressorder : 258,258,258,258,258,258,258,258,258,258
         * orderId : ps20170913105245803
         * uid : 17
         * revname : 走着走着
         * revtel : 13795812390
         * sex : 2
         * agentid : 30
         * revaid : 9
         * courierId : 0
         * area : 南门寝室
         * expresstype : null
         * address : 五楼m
         * orderinitial : 2017-09-13 10:52:45
         * orderTime : null
         * sendTime : null
         * awaitTime : null
         * arriveTime : null
         * state : 0
         * remarks : null
         * paystate : 0
         * courier : null
         * user : null
         * type : null
         * businessID : null
         * busTel : null
         * busAddress : null
         */

        private int id;
        private double weight;
        private int amount;
        private double price;
        private String content;
        private String expressname;
        private String expressorder;
        private String orderId;
        private int uid;
        private String revname;
        private String revtel;
        private int sex;
        private int agentid;
        private int revaid;
        private int courierId;
        private String area;
        private String expresstype;
        private String address;
        private String orderinitial;
        private String orderTime;
        private String sendTime;
        private String awaitTime;
        private String arriveTime;
        private int state;
        private String remarks;
        private int paystate;
        private String courier;
        private String user;
        private String type;
        private String businessID;
        private String busTel;
        private String busAddress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExpressname() {
            return expressname;
        }

        public void setExpressname(String expressname) {
            this.expressname = expressname;
        }

        public String getExpressorder() {
            return expressorder;
        }

        public void setExpressorder(String expressorder) {
            this.expressorder = expressorder;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAgentid() {
            return agentid;
        }

        public void setAgentid(int agentid) {
            this.agentid = agentid;
        }

        public int getRevaid() {
            return revaid;
        }

        public void setRevaid(int revaid) {
            this.revaid = revaid;
        }

        public int getCourierId() {
            return courierId;
        }

        public void setCourierId(int courierId) {
            this.courierId = courierId;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getExpresstype() {
            return expresstype;
        }

        public void setExpresstype(String expresstype) {
            this.expresstype = expresstype;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrderinitial() {
            return orderinitial;
        }

        public void setOrderinitial(String orderinitial) {
            this.orderinitial = orderinitial;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getAwaitTime() {
            return awaitTime;
        }

        public void setAwaitTime(String awaitTime) {
            this.awaitTime = awaitTime;
        }

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getPaystate() {
            return paystate;
        }

        public void setPaystate(int paystate) {
            this.paystate = paystate;
        }

        public String getCourier() {
            return courier;
        }

        public void setCourier(String courier) {
            this.courier = courier;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBusinessID() {
            return businessID;
        }

        public void setBusinessID(String businessID) {
            this.businessID = businessID;
        }

        public String getBusTel() {
            return busTel;
        }

        public void setBusTel(String busTel) {
            this.busTel = busTel;
        }

        public String getBusAddress() {
            return busAddress;
        }

        public void setBusAddress(String busAddress) {
            this.busAddress = busAddress;
        }
    }
}
