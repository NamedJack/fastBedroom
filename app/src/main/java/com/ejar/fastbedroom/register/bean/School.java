package com.ejar.fastbedroom.register.bean;

import android.support.annotation.NonNull;

import com.ejar.fastbedroom.register.view.PinyinUtils;
import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by json on 2017/8/22.
 */

public class School extends DataSupport {
    @SerializedName("id")
    private int schoolId;

    private String schollName;
    private String letters;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchollName() {
        return schollName;
    }

    public void setSchollName(String schollName) {
        this.schollName = schollName;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + schoolId +
                ", schollName='" + schollName + '\'' +
                ", letters='" + letters + '\'' +
                '}';
    }
}
