package com.ejar.fastbedroom.mystore.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by json on 2017/8/31.
 */

public class TabCenterBean {

    /**
     * msg : 请求成功
     * code : 100
     * data : [{"id":1,"name":"体育","pId":0,"image":"/assets/upload/goods_stype/97c93611-f233-4d66-a0b5-d98438b4fb4b.jpg"},{"id":2,"name":"电器","pId":0,"image":"/assets/upload/goods_stype/2e7fee1b-77ee-4a01-9eb2-01b85629185b.jpg"},{"id":3,"name":"食品","pId":0,"image":"/assets/upload/goods_stype/65b491aa-e3fd-4d1b-84b5-50545c809dfe.jpg"},{"id":4,"name":"衣物","pId":0,"image":"/assets/upload/goods_stype/2561a222-9e03-435d-b2a0-314ae51b776d.jpg"},{"id":5,"name":"日用","pId":0,"image":"/assets/upload/goods_stype/fa2532e2-d118-4ce0-83fb-6fd68763f29c.jpg"}]
     * success : true
     */

    private String msg;
    private String code;
    private boolean success;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * name : 体育
         * pId : 0
         * image : /assets/upload/goods_stype/97c93611-f233-4d66-a0b5-d98438b4fb4b.jpg
         */

        private int id;
        private String name;
        private int pId;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPId() {
            return pId;
        }

        public void setPId(int pId) {
            this.pId = pId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
