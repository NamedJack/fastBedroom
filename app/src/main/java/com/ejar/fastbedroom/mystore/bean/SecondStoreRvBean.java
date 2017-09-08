package com.ejar.fastbedroom.mystore.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by json on 2017/8/31.
 */

public class SecondStoreRvBean {

    /**
     * msg : 请求成功
     * total : 5
     * code : 200
     * success : true
     * rows : [{"summary":"好好好","img":"/assets/upload/goods_image/1502948927023_1.jpg","agentName":"北京大学朝阳校区","type":2,"content":"<p>好喝好喝，哇哈哈<\/p><p><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/tza_thumb.gif\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6a/laugh.gif\"><br><\/p><p><br><\/p>","stypeName":"手机","shopPrice":10,"proId":6,"schoolId":30,"name":"哇哈哈","id":1,"state":2,"stock":100},{"img":"/assets/upload/goods_image/659e8a74-d0bf-431f-a227-c3c4b791ae12.jpg","stypeName":"大米","shopPrice":15,"proId":7,"schoolId":30,"name":"爽歪歪","agentName":"北京大学朝阳校区","id":2,"state":2,"stock":150,"type":2,"content":""}]
     */

    private String msg;
    private int total;
    private String code;
    private boolean success;
    private List<RowBean> rows;

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

    public List<RowBean> getRows() {
        return rows;
    }

    public void setRows(List<RowBean> rows) {
        this.rows = rows;
    }


}
