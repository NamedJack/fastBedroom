package com.ejar.fastbedroom.pay.bean;

import java.io.Serializable;

/**
 * Created by json on 2017/9/22.
 */

public class YuEBean  implements Serializable{
    private int tag; //使用那个接口 -1： 1期 -2： 2期
    private int id; //订单号
    private String orderId; //订单编号
    private String area; //区域
    private String door; //门牌号
    private String totalMoney; //总金额

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
