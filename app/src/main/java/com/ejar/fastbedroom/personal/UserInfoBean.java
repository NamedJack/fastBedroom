package com.ejar.fastbedroom.personal;

import java.util.List;

/**
 * Created by json on 2017/8/24.
 */

public class UserInfoBean {

    /**
     * code : 200
     * msg : 获取成功
     * token : null
     * data : {"id":1,"tel":"123","username":"nidaye","password":"","name":" 碧二","img":null,"sex":1,"intSlyear":"2016.6","schoolid":1,"edittime":0,"agent":{"id":1,"agentname":"屎粑粑","adminid":1,"schoolname":null,"adminname":"倪大爷","couriernum":2,"agentremarks":"1","courierbonus":0.7,"agentbonus":0.2,"superbonus":0.1},"userAddress":[{"id":1,"receivename":"倪大爷","receivetel":"13990909896","receivesite":"畜生公寓3楼318号","dpareaid":1,"userid":1,"state":1}],"money":250,"tobedistribution":1,"pendingpayment":1,"receiptofgoods":1}
     */

    private String code;
    private String msg;
    private String token;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public void setToken(String token) {
        this.token = token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * tel : 123
         * username : nidaye
         * password :
         * name :  碧二
         * img : null
         * sex : 1
         * intSlyear : 2016.6
         * schoolid : 1
         * edittime : 0
         * agent : {"id":1,"agentname":"屎粑粑","adminid":1,"schoolname":null,"adminname":"倪大爷","couriernum":2,"agentremarks":"1","courierbonus":0.7,"agentbonus":0.2,"superbonus":0.1}
         * userAddress : [{"id":1,"receivename":"倪大爷","receivetel":"13990909896","receivesite":"畜生公寓3楼318号","dpareaid":1,"userid":1,"state":1}]
         * money : 250
         * tobedistribution : 1
         * pendingpayment : 1
         * receiptofgoods : 1
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
        private AgentBean agent;
        private int money;
        private int tobedistribution;
        private int pendingpayment;
        private int receiptofgoods;
        private List<UserAddressBean> userAddress;

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

        public Object getImg() {
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

        public AgentBean getAgent() {
            return agent;
        }

        public void setAgent(AgentBean agent) {
            this.agent = agent;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getTobedistribution() {
            return tobedistribution;
        }

        public void setTobedistribution(int tobedistribution) {
            this.tobedistribution = tobedistribution;
        }

        public int getPendingpayment() {
            return pendingpayment;
        }

        public void setPendingpayment(int pendingpayment) {
            this.pendingpayment = pendingpayment;
        }

        public int getReceiptofgoods() {
            return receiptofgoods;
        }

        public void setReceiptofgoods(int receiptofgoods) {
            this.receiptofgoods = receiptofgoods;
        }

        public List<UserAddressBean> getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(List<UserAddressBean> userAddress) {
            this.userAddress = userAddress;
        }

        public static class AgentBean {
            /**
             * id : 1
             * agentname : 屎粑粑
             * adminid : 1
             * schoolname : null
             * adminname : 倪大爷
             * couriernum : 2
             * agentremarks : 1
             * courierbonus : 0.7
             * agentbonus : 0.2
             * superbonus : 0.1
             */

            private int id;
            private String agentname;
            private int adminid;
            private Object schoolname;
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

            public Object getSchoolname() {
                return schoolname;
            }

            public void setSchoolname(Object schoolname) {
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

        public static class UserAddressBean {
            /**
             * id : 1
             * receivename : 倪大爷
             * receivetel : 13990909896
             * receivesite : 畜生公寓3楼318号
             * dpareaid : 1
             * userid : 1
             * state : 1
             */

            private int id;
            private String receivename;
            private String receivetel;
            private String receivesite;
            private int dpareaid;
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
