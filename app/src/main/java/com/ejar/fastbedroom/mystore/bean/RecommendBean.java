package com.ejar.fastbedroom.mystore.bean;

import java.util.List;

/**
 * Created by json on 2017/8/30.
 */

public class RecommendBean {

    /**
     * msg : 请求成功
     * total : 7
     * code : 200
     * success : true
     * rows : [{"summary":"好好好","img":"/assets/upload/goods_image/1502948927023_1.jpg","agentName":"北京大学朝阳校区","type":2,"content":"<p>好喝好喝，哇哈哈<\/p><p><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/tza_thumb.gif\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6a/laugh.gif\"><br><\/p><p><br><\/p>","unit":"250克","stypeName":"手机","shopPrice":10,"proId":6,"schoolId":30,"name":"哇哈哈","id":1,"state":2,"stock":0},{"img":"/assets/upload/goods_image/659e8a74-d0bf-431f-a227-c3c4b791ae12.jpg","agentName":"北京大学朝阳校区","type":2,"content":"","unit":"个","stypeName":"大米","shopPrice":15,"proId":7,"schoolId":30,"name":"爽歪歪","id":2,"state":2,"stock":144},{"img":"/assets/upload/goods_image/1502949048950_1.jpg","agentName":"北京大学朝阳校区","type":2,"content":"<p><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/5c/huanglianwx_thumb.gif\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/0b/tootha_thumb.gif\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6a/laugh.gif\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/tza_thumb.gif\"><br><\/p><p><br><\/p>","unit":"250克","stypeName":"裤子","shopPrice":9.8,"proId":9,"schoolId":30,"name":"哈哈哈","id":4,"state":2,"stock":42},{"summary":"好好好","img":"/assets/upload/goods_image/1502935122651_1.jpg","agentName":"北京大学朝阳校区","type":2,"content":"\t        \t\t<!-- 富文本编辑器内容 -->\r\n\t        \t<p>哈哈哈哈哈哈哈哈哈哈哈哈哈<\/p>","unit":"个","stypeName":"篮球","shopPrice":10,"proId":10,"schoolId":30,"name":"哈哈哈","id":11,"state":2,"stock":1000},{"img":"/assets/upload/goods_image/1502934326967_1.jpg","agentName":"北京大学朝阳校区","type":2,"content":"\t        \t\t<!-- 富文本编辑器内容 -->\r\n\t        \t<p>杀死对方<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6a/laugh.gif\"><\/p><p><br><\/p>","unit":"个","stypeName":"篮球","shopPrice":20,"proId":10,"schoolId":30,"name":"辣条","id":12,"state":2,"stock":1000},{"summary":"牛牛牛","img":"/assets/upload/goods_image/1503048497383_1.jpg","agentName":"北京大学朝阳校区","type":2,"content":"\t        \t\t<!-- 富文本编辑器内容 -->\r\n\t        \t<p>牛牛牛牛牛<\/p>","unit":"件","stypeName":"大米","shopPrice":5,"proId":7,"schoolId":30,"name":"牛","id":21,"state":2,"stock":1000},{"summary":"小竹筷 好帮手","img":"/assets/upload/goods_image/1503050205395_1.jpg","agentName":"北京大学朝阳校区","type":2,"content":"\t        \t\t<!-- 富文本编辑器内容 -->\r\n\t        \t<p>小竹筷 好帮手\r\n\r\n小竹筷 好帮手<\/p><p>小竹筷 好帮手 小竹筷 好帮手<\/p><p>小竹筷 好帮手 小竹筷 好帮手<\/p><p>小竹筷 好帮手 小竹筷 好帮手<\/p><p><br><\/p>","unit":"件","stypeName":"肥皂","shopPrice":1,"proId":20,"schoolId":30,"name":"小竹筷 ","id":22,"state":2,"stock":100}]
     */

    private String msg;
    private int total;
    private String code;
    private boolean success;
    private List<RowsBean> rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * summary : 好好好
         * img : /assets/upload/goods_image/1502948927023_1.jpg
         * agentName : 北京大学朝阳校区
         * type : 2
         * content : <p>好喝好喝，哇哈哈</p><p><img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/tza_thumb.gif"><img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6a/laugh.gif"><br></p><p><br></p>
         * unit : 250克
         * stypeName : 手机
         * shopPrice : 10
         * proId : 6
         * schoolId : 30
         * name : 哇哈哈
         * id : 1
         * state : 2
         * stock : 0
         */

        private String summary;
        private String img;
        private String agentName;
        private int type;
        private String content;
        private String unit;
        private String stypeName;
        private String shopPrice;
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

        public String getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(String shopPrice) {
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
    }
}
