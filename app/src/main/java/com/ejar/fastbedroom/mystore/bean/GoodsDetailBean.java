package com.ejar.fastbedroom.mystore.bean;

import java.util.List;

/**
 * Created by json on 2017/9/15.
 */

public class GoodsDetailBean {

    /**
     * imgs : [{"id":115,"goodsId":1,"img":"/assets/upload/goods_imgs/40a1767e-e4b3-43eb-92f7-ff3d84a193c4.png","goodsType":2},{"id":116,"goodsId":1,"img":"/assets/upload/goods_imgs/283bff16-4999-4f04-a774-35473388114f.png","goodsType":2},{"id":117,"goodsId":1,"img":"/assets/upload/goods_imgs/b0f99621-ff5a-43d6-9269-4290343b6b43.png","goodsType":2},{"id":118,"goodsId":1,"img":"/assets/upload/goods_imgs/0e4beec4-e025-4bee-87d8-eea41570807c.png","goodsType":2},{"id":119,"goodsId":1,"img":"/assets/upload/goods_imgs/c6b74668-a5f3-4c0b-9c00-c2781cf4f199.png","goodsType":2}]
     * code : 200
     * good : {"summary":"进口坚果，有利血液循环，增强大脑开发","img":"/assets/upload/goods_image/1505265137886_1.png","agentName":"北京大学朝阳校区","type":2,"content":"<p><img src=\"/XySend/assets/upload/fu_text/779556ca-d3c9-47c9-9868-bbe2f363e1f8.png\" alt=\"24\" class=\"\"><img src=\"/XySend/assets/upload/fu_text/ac8cc029-a35c-43b5-8505-061c6450a6ef.png\" alt=\"25\"><br><\/p><p><br><\/p><p><img src=\"/XySend/assets/upload/fu_text/0ffe8201-f2c3-4f3b-96cd-730b7fab80bd.png\" alt=\"26\"><img src=\"/XySend/assets/upload/fu_text/2ac4f81e-09f3-47df-950a-73762b20ab4c.png\" alt=\"27\"><img src=\"/XySend/assets/upload/fu_text/d1b2cc03-b94e-4f4a-9fa1-6576882235b9.png\" alt=\"28\"><br><\/p><p>&nbsp;&nbsp;&nbsp;&nbsp;就是好<\/p><p><br><\/p>","unit":"250克","stypeName":"手机","shopPrice":10,"proId":6,"schoolId":30,"name":"坚果","id":1,"state":2,"stock":1000}
     */

    private String code;
    private GoodBean good;
    private List<ImgsBean> imgs;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GoodBean getGood() {
        return good;
    }

    public void setGood(GoodBean good) {
        this.good = good;
    }

    public List<ImgsBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgsBean> imgs) {
        this.imgs = imgs;
    }

    public static class GoodBean {
        /**
         * summary : 进口坚果，有利血液循环，增强大脑开发
         * img : /assets/upload/goods_image/1505265137886_1.png
         * agentName : 北京大学朝阳校区
         * type : 2
         * content : <p><img src="/XySend/assets/upload/fu_text/779556ca-d3c9-47c9-9868-bbe2f363e1f8.png" alt="24" class=""><img src="/XySend/assets/upload/fu_text/ac8cc029-a35c-43b5-8505-061c6450a6ef.png" alt="25"><br></p><p><br></p><p><img src="/XySend/assets/upload/fu_text/0ffe8201-f2c3-4f3b-96cd-730b7fab80bd.png" alt="26"><img src="/XySend/assets/upload/fu_text/2ac4f81e-09f3-47df-950a-73762b20ab4c.png" alt="27"><img src="/XySend/assets/upload/fu_text/d1b2cc03-b94e-4f4a-9fa1-6576882235b9.png" alt="28"><br></p><p>&nbsp;&nbsp;&nbsp;&nbsp;就是好</p><p><br></p>
         * unit : 250克
         * stypeName : 手机
         * shopPrice : 10.0
         * proId : 6
         * schoolId : 30
         * name : 坚果
         * id : 1
         * state : 2
         * stock : 1000
         */

        private String summary;
        private String img;
        private String agentName;
        private int type;
        private String content;
        private String unit;
        private String stypeName;
        private double shopPrice;
        private int proId;
        private int schoolId;
        private String name;
        private int id;
        private int state;
        private int stock;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStypeName() {
            return stypeName;
        }

        public void setStypeName(String stypeName) {
            this.stypeName = stypeName;
        }

        public double getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(double shopPrice) {
            this.shopPrice = shopPrice;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        @Override
        public String toString() {
            return "GoodBean{" +
                    "summary='" + summary + '\'' +
                    ", img='" + img + '\'' +
                    ", agentName='" + agentName + '\'' +
                    ", type=" + type +
                    ", content='" + content + '\'' +
                    ", unit='" + unit + '\'' +
                    ", stypeName='" + stypeName + '\'' +
                    ", shopPrice=" + shopPrice +
                    ", proId=" + proId +
                    ", schoolId=" + schoolId +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    ", state=" + state +
                    ", stock=" + stock +
                    '}';
        }
    }

    public static class ImgsBean {
        /**
         * id : 115
         * goodsId : 1
         * img : /assets/upload/goods_imgs/40a1767e-e4b3-43eb-92f7-ff3d84a193c4.png
         * goodsType : 2
         */

        private int id;
        private int goodsId;
        private String img;
        private int goodsType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(int goodsType) {
            this.goodsType = goodsType;
        }

        @Override
        public String toString() {
            return "ImgsBean{" +
                    "id=" + id +
                    ", goodsId=" + goodsId +
                    ", img='" + img + '\'' +
                    ", goodsType=" + goodsType +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsDetailBean{" +
                "code='" + code + '\'' +
                ", good=" + good +
                ", imgs=" + imgs +
                '}';
    }
}
