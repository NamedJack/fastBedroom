package com.ejar.fastbedroom.mystore.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by json on 2017/9/4.
 */

public class RowBean extends DataSupport  implements Serializable{


    /**
     * summary : 好好好
     * img : /assets/upload/goods_image/1504228770315_1.jpg
     * agentName : 北京大学朝阳校区
     * type : 2
     * content : 好喝好喝，哇哈哈
     * unit : 250克
     * stypeName : 手机
     * shopPrice : 10.0
     * proId : 6
     * schoolId : 30
     * name : 哇哈哈
     * id : 1
     * state : 2
     * stock : 0 //库存
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
    @SerializedName("id")
    private int goodsId;
    private int state;
    private int stock;
    private int bookNumber;

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
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

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
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
