package com.ejar.fastbedroom.mystore.bean;

import java.util.List;

/**
 * Created by json on 2017/8/28.
 */

public class BannerBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : [{"id":3,"img":"assets/upload/shoping/87618602-d695-4062-9376-c93c43ae0b51.jpg","state":3},{"id":4,"img":"assets/upload/shoping/5b7e67bf-f2b7-40ca-a9b3-a255941eed69.jpg","state":3},{"id":5,"img":"assets/upload/shoping/63e2c196-8c23-489e-9a14-3050822cb8ef.jpg","state":3},{"id":6,"img":"assets/upload/shoping/d12c7083-bc6c-4d48-9a32-f75491084ce0.jpg","state":3},{"id":8,"img":"assets/upload/shoping/e71c0466-557a-4d2b-98c3-1dfbaab278d6.jpg","state":3}]
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

    public String getFailUrl() {
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
         * id : 3
         * img : assets/upload/shoping/87618602-d695-4062-9376-c93c43ae0b51.jpg
         * state : 3
         */

        private int id;
        private String img;
        private int state;

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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
