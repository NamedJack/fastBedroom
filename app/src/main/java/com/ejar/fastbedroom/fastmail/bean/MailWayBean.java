package com.ejar.fastbedroom.fastmail.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by json on 2017/9/12.
 */

public class MailWayBean extends DataSupport{

    /**
     * id : 1
     * expressname : 天天
     */

    @SerializedName("id")
    private int mailId;

    private String expressname;
    private String letters;

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public int getMaidId() {
        return mailId;
    }

    public void setMaidId(int mailId) {
        this.mailId = mailId;
    }

    public String getExpressname() {
        return expressname;
    }

    public void setExpressname(String expressname) {
        this.expressname = expressname;
    }


}
