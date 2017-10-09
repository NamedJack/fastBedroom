package com.ejar.fastbedroom.advice.bean;

/**
 * Created by json on 2017/9/27.
 */

public class UsAppBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 提交发送成功
     * token : null
     * data : {"scondnotic":"此山是我开，此树是我栽，这里做买卖，留下代理钱！","about":"　始建于2004年3月，是一家专门提供各类软件下载及资源服务的网站，现拥有国内最大的下载中心以及游戏外挂和源码下载中心，下载中心已经成为国内大型的同类下载站之一，每日的下载量达十万次以上，提供下载的文件储量达150G以上；软件社区已有几万的用户群，并发表了数十万的技术相关帖子，网站还提供了很多专门针对网站的特色服务，几年内已为国内提供了数亿次的下载服务。"}
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

    public static class DataBean {
        /**
         * scondnotic : 此山是我开，此树是我栽，这里做买卖，留下代理钱！
         * about : 　始建于2004年3月，是一家专门提供各类软件下载及资源服务的网站，现拥有国内最大的下载中心以及游戏外挂和源码下载中心，下载中心已经成为国内大型的同类下载站之一，每日的下载量达十万次以上，提供下载的文件储量达150G以上；软件社区已有几万的用户群，并发表了数十万的技术相关帖子，网站还提供了很多专门针对网站的特色服务，几年内已为国内提供了数亿次的下载服务。
         */

        private String scondnotic;
        private String about;

        public String getScondnotic() {
            return scondnotic;
        }

        public void setScondnotic(String scondnotic) {
            this.scondnotic = scondnotic;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }
    }
}
