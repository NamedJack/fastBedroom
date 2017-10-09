package com.ejar.fastbedroom.message.bean;

import java.util.List;

/**
 * Created by json on 2017/9/29.
 */

public class MessageBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : {"imgs":[{"id":16,"img":"assets/upload/img/201704/20170424132944088.jpeg","agentid":0,"content":null,"agentname":"\"平台\"","url":"mng/showpostimg"},{"id":17,"img":"assets/upload/img/201704/20170424133005895.png","agentid":0,"content":null,"agentname":"\"平台\"","url":"mng/showpostimg"}],"posts":[{"id":87,"postTitle":"","postContent":"","postTime":"2017-04-24 13:41:46","url":"mng/post","istop":1,"videomode":1,"agentplaceid":0,"agentplace":null,"state":1,"titleimg":"assets/upload/img/201704/20170424133357003.png"},{"id":97,"postTitle":"","postContent":"","postTime":"2017-04-24 13:46:57","url":"mng/post","istop":1,"videomode":1,"agentplaceid":0,"agentplace":null,"state":1,"titleimg":"assets/upload/img/201704/20170424134658760.png"},{"id":110,"postTitle":"","postContent":"","postTime":"2017-04-24 13:46:19","url":"mng/post","istop":1,"videomode":1,"agentplaceid":0,"agentplace":null,"state":1,"titleimg":"assets/upload/img/201704/20170424134355945.png"},{"id":119,"postTitle":"","postContent":"","postTime":"2017-05-11 21:03:27","url":"mng/post","istop":1,"videomode":1,"agentplaceid":0,"agentplace":null,"state":1,"titleimg":"assets/upload/img/201705/20170511210200707.jpeg"},{"id":120,"postTitle":"","postContent":"","postTime":"2017-05-11 21:27:01","url":"mng/post","istop":1,"videomode":1,"agentplaceid":0,"agentplace":null,"state":1,"titleimg":"assets/upload/img/201705/20170511212702010.png"}]}
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
        private List<ImgsBean> imgs;
        private List<PostsBean> posts;

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public List<PostsBean> getPosts() {
            return posts;
        }

        public void setPosts(List<PostsBean> posts) {
            this.posts = posts;
        }

        public static class ImgsBean {
            /**
             * id : 16
             * img : assets/upload/img/201704/20170424132944088.jpeg
             * agentid : 0
             * content : null
             * agentname : "平台"
             * url : mng/showpostimg
             */

            private int id;
            private String img;
            private int agentid;
            private Object content;
            private String agentname;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getAgentid() {
                return agentid;
            }

            public void setAgentid(int agentid) {
                this.agentid = agentid;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public String getAgentname() {
                return agentname;
            }

            public void setAgentname(String agentname) {
                this.agentname = agentname;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class PostsBean {
            /**
             * id : 87
             * postTitle :
             * postContent :
             * postTime : 2017-04-24 13:41:46
             * url : mng/post
             * istop : 1
             * videomode : 1
             * agentplaceid : 0
             * agentplace : null
             * state : 1
             * titleimg : assets/upload/img/201704/20170424133357003.png
             */

            private int id;
            private String postTitle;
            private String postContent;
            private String postTime;
            private String url;
            private int istop;
            private int videomode;
            private int agentplaceid;
            private Object agentplace;
            private int state;
            private String titleimg;

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getIstop() {
                return istop;
            }

            public void setIstop(int istop) {
                this.istop = istop;
            }

            public int getVideomode() {
                return videomode;
            }

            public void setVideomode(int videomode) {
                this.videomode = videomode;
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

            public void setAgentplace(Object agentplace) {
                this.agentplace = agentplace;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getTitleimg() {
                return titleimg;
            }

            public void setTitleimg(String titleimg) {
                this.titleimg = titleimg;
            }
        }
    }
}
