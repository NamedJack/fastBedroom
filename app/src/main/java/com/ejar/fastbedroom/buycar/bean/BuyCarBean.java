package com.ejar.fastbedroom.buycar.bean;

import java.util.List;

/**
 * Created by json on 2017/9/7.
 */

public class BuyCarBean {

    /**
     * msg : 请求成功
     * total : 2
     * code : 200
     * data : [{"summary":"小竹筷 好帮手","img":"/assets/upload/goods_image/1503050205395_1.jpg","goodsid":22,"cartId":11,"type":2,"userId":17,"content":"\t        \t\t<!-- 富文本编辑器内容 -->\r\n\t        \t<p>小竹筷 好帮手\r\n\r\n小竹筷 好帮手<\/p><p>小竹筷 好帮手 小竹筷 好帮手<\/p><p>小竹筷 好帮手 小竹筷 好帮手<\/p><p>小竹筷 好帮手 小竹筷 好帮手<\/p><p><br><\/p>","number":2,"unit":"件","shopPrice":1,"proId":20,"schoolId":30,"name":"小竹筷 ","id":22,"state":2,"stock":100},{"img":"/assets/upload/goods_image/659e8a74-d0bf-431f-a227-c3c4b791ae12.jpg","goodsid":10,"cartId":10,"type":2,"userId":17,"content":"","number":5,"unit":"个","shopPrice":10,"proId":9,"schoolId":30,"name":"冰糖糖","id":10,"state":1,"stock":100},{"summary":"牛牛牛","img":"/assets/upload/goods_image/1503048497383_1.jpg","goodsid":21,"cartId":9,"type":2,"userId":17,"content":"\t        \t\t<!-- 富文本编辑器内容 -->\r\n\t        \t<p>牛牛牛牛牛<\/p>","number":18,"unit":"件","shopPrice":5,"proId":7,"schoolId":30,"name":"牛","id":21,"state":2,"stock":1000},{"img":"/assets/upload/goods_image/659e8a74-d0bf-431f-a227-c3c4b791ae12.jpg","goodsid":2,"cartId":8,"type":2,"userId":17,"content":"","number":15,"unit":"个","shopPrice":15,"proId":7,"schoolId":30,"name":"爽歪歪","id":2,"state":2,"stock":144},{"summary":"好好好","img":"/assets/upload/goods_image/1504228770315_1.jpg","goodsid":1,"cartId":7,"type":2,"userId":17,"content":"好喝好喝，哇哈哈","number":35,"unit":"250克","shopPrice":10,"proId":6,"schoolId":30,"name":"哇哈哈","id":1,"state":2,"stock":1000},{"img":"/assets/upload/goods_image/1502934326967_1.jpg","goodsid":12,"cartId":6,"type":2,"userId":17,"content":"\t        \t\t<!-- 富文本编辑器内容 -->\r\n\t        \t<p>杀死对方<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6a/laugh.gif\"><\/p><p><br><\/p>","number":8,"unit":"个","shopPrice":20,"proId":10,"schoolId":30,"name":"辣条","id":12,"state":2,"stock":1000},{"summary":"好好好","img":"/assets/upload/goods_image/1502935122651_1.jpg","goodsid":11,"cartId":5,"type":2,"userId":17,"content":"\t        \t\t<!-- 富文本编辑器内容 -->\r\n\t        \t<p>哈哈哈哈哈哈哈哈哈哈哈哈哈<\/p>","number":14,"unit":"个","shopPrice":10,"proId":10,"schoolId":30,"name":"哈哈哈","id":11,"state":2,"stock":1000},{"img":"/assets/upload/goods_image/659e8a74-d0bf-431f-a227-c3c4b791ae12.jpg","goodsid":8,"cartId":4,"type":2,"userId":17,"content":"<p>而无法让个人王二飞个人个人<\/p>","number":15,"unit":"250克","shopPrice":10,"proId":7,"schoolId":30,"name":"冲冲冲","id":8,"state":1,"stock":100},{"summary":"好好好","img":"/assets/upload/goods_image/659e8a74-d0bf-431f-a227-c3c4b791ae12.jpg","goodsid":5,"cartId":3,"type":2,"userId":17,"content":"","number":12,"unit":"250克","shopPrice":10,"proId":10,"schoolId":30,"name":"呵呵喝","id":5,"state":1,"stock":96},{"img":"/assets/upload/goods_image/1502949048950_1.jpg","goodsid":4,"cartId":2,"type":2,"userId":17,"content":"<p><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/5c/huanglianwx_thumb.gif\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/0b/tootha_thumb.gif\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6a/laugh.gif\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/tza_thumb.gif\"><br><\/p><p><br><\/p>","number":15,"unit":"250克","shopPrice":9.8,"proId":9,"schoolId":30,"name":"哈哈哈","id":4,"state":2,"stock":42}]
     * success : true
     */

    private String msg;
    private int total;
    private String code;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * summary : 小竹筷 好帮手
         * img : /assets/upload/goods_image/1503050205395_1.jpg
         * goodsid : 22
         * cartId : 11
         * type : 2
         * userId : 17
         * content :        		<!-- 富文本编辑器内容 -->
         <p>小竹筷 好帮手

         小竹筷 好帮手</p><p>小竹筷 好帮手 小竹筷 好帮手</p><p>小竹筷 好帮手 小竹筷 好帮手</p><p>小竹筷 好帮手 小竹筷 好帮手</p><p><br></p>
         * number : 2
         * unit : 件
         * shopPrice : 1.0
         * proId : 20
         * schoolId : 30
         * name : 小竹筷
         * id : 22
         * state : 2
         * stock : 100
         */

        private String summary;
        private String img;
        private int goodsid;
        private int cartId;
        private int type;
        private int userId;
        private String content;
        private int number;
        private String unit;
        private double shopPrice;
        private int proId;
        private int schoolId;
        private String name;
        private int id;
        private int state;
        private int stock;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

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

        public int getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(int goodsid) {
            this.goodsid = goodsid;
        }

        public int getCartId() {
            return cartId;
        }

        public void setCartId(int cartId) {
            this.cartId = cartId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
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
    }
}
