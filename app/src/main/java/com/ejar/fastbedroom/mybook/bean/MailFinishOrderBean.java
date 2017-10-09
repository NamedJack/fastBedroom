package com.ejar.fastbedroom.mybook.bean;

import java.util.List;

/**
 * Created by json on 2017/9/30.
 */

public class MailFinishOrderBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : [{"id":1932,"weight":0,"amount":1,"price":2,"content":null,"expressname":"菜鸟驿站","expressorder":"225","orderId":"ps20170929114623177","uid":646,"revname":"啊啊ass","revtel":"13795812390","sex":1,"agentid":52,"revaid":36,"courierId":87,"area":"15号楼-21号楼","expresstype":null,"address":"25层","orderinitial":"2017-09-29 11:46:23","orderTime":"2017-09-30 09:58:34","sendTime":"2017-09-30 09:58:41","awaitTime":"2017-09-30 10:00:03","arriveTime":"2017-09-30 10:18:17","state":4,"remarks":"","paystate":1,"courier":{"id":87,"tel":"18048170020","alipayname":null,"wechatname":null,"password":"e10adc3949ba59abbe56e057f20f883e","name":"陈力测试","img":null,"stuIDImg":null,"stuIDImgM":"assets/upload/stud/0459e6a1-340b-479b-8b57-74c3db5dff9a.png","stuIDImgV":"assets/upload/stud/f706448e-ee63-4527-a548-bff9f57d3939.png","areaid":36,"agentid":52,"intSlyear":"2010","state":2,"money":19.6,"sex":1,"courierType":1,"courierorder":0,"dparea":null,"dpareas":null,"alipaystate":0,"weChat":null,"alipay":null},"user":{"id":646,"tel":"13795812390","username":null,"password":"e10adc3949ba59abbe56e057f20f883e","name":"这周","img":"assets/upload/portrait/a081ebe7-9f42-4937-a94d-93309f06f17f.jpg","sex":1,"schoolid":52,"edittime":1,"agent":null,"userAddress":null,"endgoods":0,"money":992,"cardNumber":null,"imgs":null,"is_authentication":1,"au_img":null,"img_state":null,"wx_account":null,"wx_name":null,"api_account":null,"api_name":null,"receiptofgoods":0,"pendingpayment":0,"tobedistribution":0},"type":1,"businessID":null,"busTel":null,"busAddress":null},{"id":1934,"weight":0,"amount":1,"price":2,"content":null,"expressname":"百世","expressorder":"258","orderId":"ps20170930094542995","uid":646,"revname":"Z","revtel":"13795812390","sex":1,"agentid":52,"revaid":36,"courierId":0,"area":"15号楼-21号楼","expresstype":null,"address":"588","orderinitial":"2017-09-30 09:45:42","orderTime":"2017-09-30 09:58:43","sendTime":null,"awaitTime":"2017-09-30 09:59:58","arriveTime":null,"state":5,"remarks":"","paystate":3,"courier":null,"user":{"id":646,"tel":"13795812390","username":null,"password":"e10adc3949ba59abbe56e057f20f883e","name":"这周","img":"assets/upload/portrait/a081ebe7-9f42-4937-a94d-93309f06f17f.jpg","sex":1,"schoolid":52,"edittime":1,"agent":null,"userAddress":null,"endgoods":0,"money":992,"cardNumber":null,"imgs":null,"is_authentication":1,"au_img":null,"img_state":null,"wx_account":null,"wx_name":null,"api_account":null,"api_name":null,"receiptofgoods":0,"pendingpayment":0,"tobedistribution":0},"type":1,"businessID":null,"busTel":null,"busAddress":null}]
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
         * id : 1932
         * weight : 0.0
         * amount : 1
         * price : 2.0
         * content : null
         * expressname : 菜鸟驿站
         * expressorder : 225
         * orderId : ps20170929114623177
         * uid : 646
         * revname : 啊啊ass
         * revtel : 13795812390
         * sex : 1
         * agentid : 52
         * revaid : 36
         * courierId : 87
         * area : 15号楼-21号楼
         * expresstype : null
         * address : 25层
         * orderinitial : 2017-09-29 11:46:23
         * orderTime : 2017-09-30 09:58:34
         * sendTime : 2017-09-30 09:58:41
         * awaitTime : 2017-09-30 10:00:03
         * arriveTime : 2017-09-30 10:18:17
         * state : 4
         * remarks :
         * paystate : 1
         * courier : {"id":87,"tel":"18048170020","alipayname":null,"wechatname":null,"password":"e10adc3949ba59abbe56e057f20f883e","name":"陈力测试","img":null,"stuIDImg":null,"stuIDImgM":"assets/upload/stud/0459e6a1-340b-479b-8b57-74c3db5dff9a.png","stuIDImgV":"assets/upload/stud/f706448e-ee63-4527-a548-bff9f57d3939.png","areaid":36,"agentid":52,"intSlyear":"2010","state":2,"money":19.6,"sex":1,"courierType":1,"courierorder":0,"dparea":null,"dpareas":null,"alipaystate":0,"weChat":null,"alipay":null}
         * user : {"id":646,"tel":"13795812390","username":null,"password":"e10adc3949ba59abbe56e057f20f883e","name":"这周","img":"assets/upload/portrait/a081ebe7-9f42-4937-a94d-93309f06f17f.jpg","sex":1,"schoolid":52,"edittime":1,"agent":null,"userAddress":null,"endgoods":0,"money":992,"cardNumber":null,"imgs":null,"is_authentication":1,"au_img":null,"img_state":null,"wx_account":null,"wx_name":null,"api_account":null,"api_name":null,"receiptofgoods":0,"pendingpayment":0,"tobedistribution":0}
         * type : 1
         * businessID : null
         * busTel : null
         * busAddress : null
         */

        private int id;
        private double weight;
        private int amount;
        private double price;
        private Object content;
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
        private Object expresstype;
        private String address;
        private String orderinitial;
        private String orderTime;
        private String sendTime;
        private String awaitTime;
        private String arriveTime;
        private int state;
        private String remarks;
        private int paystate;
        private CourierBean courier;
        private UserBean user;
        private int type;
        private Object businessID;
        private Object busTel;
        private Object busAddress;

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

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
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

        public Object getExpresstype() {
            return expresstype;
        }

        public void setExpresstype(Object expresstype) {
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

        public CourierBean getCourier() {
            return courier;
        }

        public void setCourier(CourierBean courier) {
            this.courier = courier;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getBusinessID() {
            return businessID;
        }

        public void setBusinessID(Object businessID) {
            this.businessID = businessID;
        }

        public Object getBusTel() {
            return busTel;
        }

        public void setBusTel(Object busTel) {
            this.busTel = busTel;
        }

        public Object getBusAddress() {
            return busAddress;
        }

        public void setBusAddress(Object busAddress) {
            this.busAddress = busAddress;
        }

        public static class CourierBean {
            /**
             * id : 87
             * tel : 18048170020
             * alipayname : null
             * wechatname : null
             * password : e10adc3949ba59abbe56e057f20f883e
             * name : 陈力测试
             * img : null
             * stuIDImg : null
             * stuIDImgM : assets/upload/stud/0459e6a1-340b-479b-8b57-74c3db5dff9a.png
             * stuIDImgV : assets/upload/stud/f706448e-ee63-4527-a548-bff9f57d3939.png
             * areaid : 36
             * agentid : 52
             * intSlyear : 2010
             * state : 2
             * money : 19.6
             * sex : 1
             * courierType : 1
             * courierorder : 0
             * dparea : null
             * dpareas : null
             * alipaystate : 0
             * weChat : null
             * alipay : null
             */

            private int id;
            private String tel;
            private Object alipayname;
            private Object wechatname;
            private String password;
            private String name;
            private Object img;
            private Object stuIDImg;
            private String stuIDImgM;
            private String stuIDImgV;
            private int areaid;
            private int agentid;
            private String intSlyear;
            private int state;
            private double money;
            private int sex;
            private int courierType;
            private int courierorder;
            private Object dparea;
            private Object dpareas;
            private int alipaystate;
            private Object weChat;
            private Object alipay;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public Object getAlipayname() {
                return alipayname;
            }

            public void setAlipayname(Object alipayname) {
                this.alipayname = alipayname;
            }

            public Object getWechatname() {
                return wechatname;
            }

            public void setWechatname(Object wechatname) {
                this.wechatname = wechatname;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public Object getStuIDImg() {
                return stuIDImg;
            }

            public void setStuIDImg(Object stuIDImg) {
                this.stuIDImg = stuIDImg;
            }

            public String getStuIDImgM() {
                return stuIDImgM;
            }

            public void setStuIDImgM(String stuIDImgM) {
                this.stuIDImgM = stuIDImgM;
            }

            public String getStuIDImgV() {
                return stuIDImgV;
            }

            public void setStuIDImgV(String stuIDImgV) {
                this.stuIDImgV = stuIDImgV;
            }

            public int getAreaid() {
                return areaid;
            }

            public void setAreaid(int areaid) {
                this.areaid = areaid;
            }

            public int getAgentid() {
                return agentid;
            }

            public void setAgentid(int agentid) {
                this.agentid = agentid;
            }

            public String getIntSlyear() {
                return intSlyear;
            }

            public void setIntSlyear(String intSlyear) {
                this.intSlyear = intSlyear;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getCourierType() {
                return courierType;
            }

            public void setCourierType(int courierType) {
                this.courierType = courierType;
            }

            public int getCourierorder() {
                return courierorder;
            }

            public void setCourierorder(int courierorder) {
                this.courierorder = courierorder;
            }

            public Object getDparea() {
                return dparea;
            }

            public void setDparea(Object dparea) {
                this.dparea = dparea;
            }

            public Object getDpareas() {
                return dpareas;
            }

            public void setDpareas(Object dpareas) {
                this.dpareas = dpareas;
            }

            public int getAlipaystate() {
                return alipaystate;
            }

            public void setAlipaystate(int alipaystate) {
                this.alipaystate = alipaystate;
            }

            public Object getWeChat() {
                return weChat;
            }

            public void setWeChat(Object weChat) {
                this.weChat = weChat;
            }

            public Object getAlipay() {
                return alipay;
            }

            public void setAlipay(Object alipay) {
                this.alipay = alipay;
            }
        }

        public static class UserBean {
            /**
             * id : 646
             * tel : 13795812390
             * username : null
             * password : e10adc3949ba59abbe56e057f20f883e
             * name : 这周
             * img : assets/upload/portrait/a081ebe7-9f42-4937-a94d-93309f06f17f.jpg
             * sex : 1
             * schoolid : 52
             * edittime : 1
             * agent : null
             * userAddress : null
             * endgoods : 0
             * money : 992.0
             * cardNumber : null
             * imgs : null
             * is_authentication : 1
             * au_img : null
             * img_state : null
             * wx_account : null
             * wx_name : null
             * api_account : null
             * api_name : null
             * receiptofgoods : 0
             * pendingpayment : 0
             * tobedistribution : 0
             */

            private int id;
            private String tel;
            private Object username;
            private String password;
            private String name;
            private String img;
            private int sex;
            private int schoolid;
            private int edittime;
            private Object agent;
            private Object userAddress;
            private int endgoods;
            private double money;
            private Object cardNumber;
            private Object imgs;
            private int is_authentication;
            private Object au_img;
            private Object img_state;
            private Object wx_account;
            private Object wx_name;
            private Object api_account;
            private Object api_name;
            private int receiptofgoods;
            private int pendingpayment;
            private int tobedistribution;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public Object getUsername() {
                return username;
            }

            public void setUsername(Object username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getSchoolid() {
                return schoolid;
            }

            public void setSchoolid(int schoolid) {
                this.schoolid = schoolid;
            }

            public int getEdittime() {
                return edittime;
            }

            public void setEdittime(int edittime) {
                this.edittime = edittime;
            }

            public Object getAgent() {
                return agent;
            }

            public void setAgent(Object agent) {
                this.agent = agent;
            }

            public Object getUserAddress() {
                return userAddress;
            }

            public void setUserAddress(Object userAddress) {
                this.userAddress = userAddress;
            }

            public int getEndgoods() {
                return endgoods;
            }

            public void setEndgoods(int endgoods) {
                this.endgoods = endgoods;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public Object getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(Object cardNumber) {
                this.cardNumber = cardNumber;
            }

            public Object getImgs() {
                return imgs;
            }

            public void setImgs(Object imgs) {
                this.imgs = imgs;
            }

            public int getIs_authentication() {
                return is_authentication;
            }

            public void setIs_authentication(int is_authentication) {
                this.is_authentication = is_authentication;
            }

            public Object getAu_img() {
                return au_img;
            }

            public void setAu_img(Object au_img) {
                this.au_img = au_img;
            }

            public Object getImg_state() {
                return img_state;
            }

            public void setImg_state(Object img_state) {
                this.img_state = img_state;
            }

            public Object getWx_account() {
                return wx_account;
            }

            public void setWx_account(Object wx_account) {
                this.wx_account = wx_account;
            }

            public Object getWx_name() {
                return wx_name;
            }

            public void setWx_name(Object wx_name) {
                this.wx_name = wx_name;
            }

            public Object getApi_account() {
                return api_account;
            }

            public void setApi_account(Object api_account) {
                this.api_account = api_account;
            }

            public Object getApi_name() {
                return api_name;
            }

            public void setApi_name(Object api_name) {
                this.api_name = api_name;
            }

            public int getReceiptofgoods() {
                return receiptofgoods;
            }

            public void setReceiptofgoods(int receiptofgoods) {
                this.receiptofgoods = receiptofgoods;
            }

            public int getPendingpayment() {
                return pendingpayment;
            }

            public void setPendingpayment(int pendingpayment) {
                this.pendingpayment = pendingpayment;
            }

            public int getTobedistribution() {
                return tobedistribution;
            }

            public void setTobedistribution(int tobedistribution) {
                this.tobedistribution = tobedistribution;
            }
        }
    }
}
