package com.ejar.fastbedroom.personal.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by json on 2017/8/24.
 */

public class UserInfoBean {


    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : {"id":17,"tel":"18081231464","username":null,"password":"","name":"我也是","img":"assets/upload/img/201708/20170804115516653.jpeg","sex":2,"schoolid":30,"edittime":1,"agent":{"id":30,"schoolid":1,"agentname":"北京大学朝阳校区","adminid":56,"schoolname":"北京大学","adminname":"123","couriernum":0,"agentremarks":"2","courierbonus":0.6,"agentbonus":0.2,"superbonus":0.2},"userAddress":[{"id":16,"receivename":"刚刚","receivetel":"13548754875","receivesite":"到底","dpareaid":9,"dpareaname":null,"userid":17,"state":2},{"id":17,"receivename":"呵呵","receivetel":"13548754875","receivesite":"说的","dpareaid":10,"dpareaname":null,"userid":17,"state":1},{"id":18,"receivename":"刚刚","receivetel":"13584848484","receivesite":"刚刚","dpareaid":9,"dpareaname":null,"userid":17,"state":1},{"id":20,"receivename":"你是智障","receivetel":"18081231464","receivesite":"my破傻X我现在","dpareaid":12,"dpareaname":null,"userid":17,"state":1},{"id":21,"receivename":"高学历","receivetel":"18081231464","receivesite":"里咯红","dpareaid":12,"dpareaname":null,"userid":17,"state":1},{"id":22,"receivename":"高学历","receivetel":"18081231464","receivesite":"里咯红","dpareaid":12,"dpareaname":null,"userid":17,"state":1},{"id":26,"receivename":"何久腾","receivetel":"17711405865","receivesite":"17栋211","dpareaid":9,"dpareaname":null,"userid":17,"state":1}],"endgoods":9,"money":121.25,"is_authentication":4,"au_img":"assets/upload/img/201708/20170804115516653.jpeg","img_state":1,"wx_account":"888888","wx_name":"必须发","api_account":"666666666","api_name":"就是牛","tobedistribution":5,"receiptofgoods":6,"pendingpayment":0}
     */

    private String code;
    private String okurl;
    private String failUrl;
    private String msg;
    private String token;
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

    public void setOkurl(String okurl) {
        this.okurl = okurl;
    }

    public Object getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
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

    public void setToken(String token) {
        this.token = token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 17
         * tel : 18081231464
         * username : null
         * password :
         * name : 我也是
         * img : assets/upload/img/201708/20170804115516653.jpeg
         * sex : 2
         * schoolid : 30
         * edittime : 1
         * agent : {"id":30,"schoolid":1,"agentname":"北京大学朝阳校区","adminid":56,"schoolname":"北京大学","adminname":"123","couriernum":0,"agentremarks":"2","courierbonus":0.6,"agentbonus":0.2,"superbonus":0.2}
         * userAddress : [{"id":16,"receivename":"刚刚","receivetel":"13548754875","receivesite":"到底","dpareaid":9,"dpareaname":null,"userid":17,"state":2},{"id":17,"receivename":"呵呵","receivetel":"13548754875","receivesite":"说的","dpareaid":10,"dpareaname":null,"userid":17,"state":1},{"id":18,"receivename":"刚刚","receivetel":"13584848484","receivesite":"刚刚","dpareaid":9,"dpareaname":null,"userid":17,"state":1},{"id":20,"receivename":"你是智障","receivetel":"18081231464","receivesite":"my破傻X我现在","dpareaid":12,"dpareaname":null,"userid":17,"state":1},{"id":21,"receivename":"高学历","receivetel":"18081231464","receivesite":"里咯红","dpareaid":12,"dpareaname":null,"userid":17,"state":1},{"id":22,"receivename":"高学历","receivetel":"18081231464","receivesite":"里咯红","dpareaid":12,"dpareaname":null,"userid":17,"state":1},{"id":26,"receivename":"何久腾","receivetel":"17711405865","receivesite":"17栋211","dpareaid":9,"dpareaname":null,"userid":17,"state":1}]
         * endgoods : 9
         * money : 121.25
         * is_authentication : 4
         * au_img : assets/upload/img/201708/20170804115516653.jpeg
         * img_state : 1
         * wx_account : 888888
         * wx_name : 必须发
         * api_account : 666666666
         * api_name : 就是牛
         * tobedistribution : 5
         * receiptofgoods : 6
         * pendingpayment : 0
         */

        private int id;
        private String tel;
        private String username;
        private String password;
        private String name;
        private String img;
        private int sex;
        private int schoolid;
        private int edittime;
        private AgentBean agent;
        private int endgoods;
        private double money;
        private int is_authentication;
        private String au_img;
        private int img_state;
        private String wx_account;
        private String wx_name;
        private String api_account;
        private String api_name;
        private int tobedistribution;
        private int receiptofgoods;
        private int pendingpayment;
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

        public Object getUsername() {
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

        public int getIs_authentication() {
            return is_authentication;
        }

        public void setIs_authentication(int is_authentication) {
            this.is_authentication = is_authentication;
        }

        public String getAu_img() {
            return au_img;
        }

        public void setAu_img(String au_img) {
            this.au_img = au_img;
        }

        public int getImg_state() {
            return img_state;
        }

        public void setImg_state(int img_state) {
            this.img_state = img_state;
        }

        public String getWx_account() {
            return wx_account;
        }

        public void setWx_account(String wx_account) {
            this.wx_account = wx_account;
        }

        public String getWx_name() {
            return wx_name;
        }

        public void setWx_name(String wx_name) {
            this.wx_name = wx_name;
        }

        public String getApi_account() {
            return api_account;
        }

        public void setApi_account(String api_account) {
            this.api_account = api_account;
        }

        public String getApi_name() {
            return api_name;
        }

        public void setApi_name(String api_name) {
            this.api_name = api_name;
        }

        public int getTobedistribution() {
            return tobedistribution;
        }

        public void setTobedistribution(int tobedistribution) {
            this.tobedistribution = tobedistribution;
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

        public List<UserAddressBean> getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(List<UserAddressBean> userAddress) {
            this.userAddress = userAddress;
        }

        public static class AgentBean implements Serializable{
            /**
             * id : 30
             * schoolid : 1
             * agentname : 北京大学朝阳校区
             * adminid : 56
             * schoolname : 北京大学
             * adminname : 123
             * couriernum : 0
             * agentremarks : 2
             * courierbonus : 0.6
             * agentbonus : 0.2
             * superbonus : 0.2
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

        public static class UserAddressBean implements Serializable{
            /**
             * id : 16
             * receivename : 刚刚
             * receivetel : 13548754875
             * receivesite : 到底
             * dpareaid : 9
             * dpareaname : null
             * userid : 17
             * state : 2
             */

            private int id;
            private String receivename;
            private String receivetel;
            private String receivesite;
            private int dpareaid;
            private Object dpareaname;
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

            public Object getDpareaname() {
                return dpareaname;
            }

            public void setDpareaname(Object dpareaname) {
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
