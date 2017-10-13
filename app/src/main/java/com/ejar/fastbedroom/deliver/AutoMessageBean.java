package com.ejar.fastbedroom.deliver;

import java.util.List;

/**
 * Created by json on 2017/10/13.
 */

public class AutoMessageBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : [{"id":11,"title":"洗澡","time":"2017-08-22 17:33:14","content":"<p>&nbsp;多少人公司当然个&nbsp;<\/p>","agentid":0,"subtitle":"适当广东人发过的 ","agentname":null},{"id":10,"title":"发的规划如图","time":"2017-08-22 17:32:56","content":"<p>发的呵呵有点同样7 和他人人&nbsp;<\/p>","agentid":0,"subtitle":"负担和规范","agentname":null},{"id":9,"title":"风格的话","time":"2017-08-22 17:32:50","content":"<p>&nbsp;符合合同估计他预计从V型好像大概<\/p>","agentid":0,"subtitle":"发的规划 ","agentname":null},{"id":8,"title":"对方过后","time":"2017-08-22 17:32:43","content":"<p>放大镜看发条活动投入电梯入户 他<\/p>","agentid":0,"subtitle":"发达国家","agentname":null},{"id":7,"title":"动态优化","time":"2017-08-22 17:32:37","content":"<p>梵蒂冈和健康没有门槛vcxhgf然后他乳房 &nbsp;<\/p>","agentid":0,"subtitle":"都发给好友推进地方规划","agentname":null}]
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
         * id : 11
         * title : 洗澡
         * time : 2017-08-22 17:33:14
         * content : <p>&nbsp;多少人公司当然个&nbsp;</p>
         * agentid : 0
         * subtitle : 适当广东人发过的
         * agentname : null
         */

        private int id;
        private String title;
        private String time;
        private String content;
        private int agentid;
        private String subtitle;
        private Object agentname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAgentid() {
            return agentid;
        }

        public void setAgentid(int agentid) {
            this.agentid = agentid;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Object getAgentname() {
            return agentname;
        }

        public void setAgentname(Object agentname) {
            this.agentname = agentname;
        }
    }
}
