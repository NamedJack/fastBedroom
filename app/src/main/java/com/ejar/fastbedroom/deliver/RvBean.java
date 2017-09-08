package com.ejar.fastbedroom.deliver;

import java.util.List;

/**
 * Created by json on 2017/8/24.
 * 下面消息列表实体类
 */

public class RvBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : [{"id":29,"postTitle":"12321","postContent":"4124412321","postTime":"2017-03-03 16:17:26","istop":1,"adminid":1,"isshow":2,"agentplaceid":0,"agentplace":null,"agent":null},{"id":28,"postTitle":"21312","postContent":"123123","postTime":"2017-03-03 15:15:06","istop":1,"adminid":1,"isshow":2,"agentplaceid":0,"agentplace":null,"agent":null},{"id":25,"postTitle":"sdfsadfsafsa","postContent":"111111111111111111111111111111111","postTime":"2017-03-01 16:22:53","istop":1,"adminid":1,"isshow":2,"agentplaceid":0,"agentplace":null,"agent":null}]
     */

    private String code;
    private String okurl;
    private String failUrl;
    private String msg;
    private String token;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 29
         * postTitle : 12321
         * postContent : 4124412321
         * postTime : 2017-03-03 16:17:26
         * istop : 1
         * adminid : 1
         * isshow : 2
         * agentplaceid : 0
         * agentplace : null
         * agent : null
         */

        private int id;
        private String postTitle;
        private String postContent;
        private String postTime;
        private int istop;
        private int adminid;
        private int isshow;
        private int agentplaceid;
        private String agentplace;
        private String agent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public void setPostTitle(String postTitle) {
            this.postTitle = postTitle;
        }

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }

        public String getPostTime() {
            return postTime;
        }

        public void setPostTime(String postTime) {
            this.postTime = postTime;
        }

        public int getIstop() {
            return istop;
        }

        public void setIstop(int istop) {
            this.istop = istop;
        }

        public int getAdminid() {
            return adminid;
        }

        public void setAdminid(int adminid) {
            this.adminid = adminid;
        }

        public int getIsshow() {
            return isshow;
        }

        public void setIsshow(int isshow) {
            this.isshow = isshow;
        }

        public int getAgentplaceid() {
            return agentplaceid;
        }

        public void setAgentplaceid(int agentplaceid) {
            this.agentplaceid = agentplaceid;
        }

        public Object getAgentplace() {
            return agentplace;
        }

        public void setAgentplace(String agentplace) {
            this.agentplace = agentplace;
        }

        public Object getAgent() {
            return agent;
        }

        public void setAgent(String agent) {
            this.agent = agent;
        }
    }
}
