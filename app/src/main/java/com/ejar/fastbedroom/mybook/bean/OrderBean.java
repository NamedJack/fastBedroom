package com.ejar.fastbedroom.mybook.bean;

import java.util.List;

/**
 * Created by json on 2017/9/14.
 */

public class OrderBean {
    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : [{"id":4,"weight":0,"amount":3,"price":250,"content":null,"expressname":"汇通","expressorder":"2313,6435,34544","orderId":"20170118181245552","uid":1,"revname":"爱的不要不要的","revtel":"13111111111","revaid":1,"courierId":0,"area":"大西门","expresstype":null,"address":"学生公寓1栋12楼124号","orderinitial":"2017/01/18 18:12:45","orderTime":"2017/01/18 18:49:17","sendTime":null,"awaitTime":null,"arriveTime":null,"state":1,"remarks":"快点！急急急！！！！","paystate":1,"courier":null,"user":{"id":1,"tel":"123","username":"倪大业","password":"","name":"倪大业","img":null,"sex":1,"intSlyear":"2016.7","schoolid":1,"edittime":0,"agent":null,"userAddress":null}}]
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
         * id : 4
         * weight : 0
         * amount : 3
         * price : 250
         * content : null
         * expressname : 汇通
         * expressorder : 2313,6435,34544
         * orderId : 20170118181245552
         * uid : 1
         * revname : 爱的不要不要的
         * revtel : 13111111111
         * revaid : 1
         * courierId : 0
         * area : 大西门
         * expresstype : null
         * address : 学生公寓1栋12楼124号
         * orderinitial : 2017/01/18 18:12:45
         * orderTime : 2017/01/18 18:49:17
         * sendTime : null
         * awaitTime : null
         * arriveTime : null
         * state : 1
         * remarks : 快点！急急急！！！！
         * paystate : 1
         * courier : null
         * user : {"id":1,"tel":"123","username":"倪大业","password":"","name":"倪大业","img":null,"sex":1,"intSlyear":"2016.7","schoolid":1,"edittime":0,"agent":null,"userAddress":null}
         */

        private int id;
        private int weight;
        private int amount;
        private double price;
        private String content;
        private String expressname;
        private String expressorder;
        private String orderId;
        private int uid;
        private String revname;
        private String revtel;
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
        private UserBean user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 1
             * tel : 123
             * username : 倪大业
             * password :
             * name : 倪大业
             * img : null
             * sex : 1
             * intSlyear : 2016.7
             * schoolid : 1
             * edittime : 0
             * agent : null
             * userAddress : null
             */

            private int id;
            private String tel;
            private String username;
            private String password;
            private String name;
            private String img;
            private int sex;
            private String intSlyear;
            private int schoolid;
            private int edittime;
            private String agent;
            private String userAddress;

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

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
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

            public String getIntSlyear() {
                return intSlyear;
            }

            public void setIntSlyear(String intSlyear) {
                this.intSlyear = intSlyear;
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

            public String getAgent() {
                return agent;
            }

            public void setAgent(String agent) {
                this.agent = agent;
            }

            public String getUserAddress() {
                return userAddress;
            }

            public void setUserAddress(String userAddress) {
                this.userAddress = userAddress;
            }
        }
    }

}
