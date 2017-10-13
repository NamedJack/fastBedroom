package com.ejar.fastbedroom.campusdynamics.bean;

/**
 * Created by json on 2017/10/10.
 */

public class CampusHeadImg {

    /**
     * code : 200
     * success : true
     * msg : 请求成功
     * data : {"id":2,"image":"/assets/upload/DynamicImage/baa3c997-c363-452c-bc73-0dc7ab36c950.jpg","agentId":30}
     */

    private String code;
    private boolean success;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * image : /assets/upload/DynamicImage/baa3c997-c363-452c-bc73-0dc7ab36c950.jpg
         * agentId : 30
         */

        private int id;
        private String image;
        private int agentId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }
    }
}
