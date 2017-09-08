//package com.ejar.fastbedroom.register.bean;
//
//import android.support.annotation.NonNull;
//
//import com.ejar.fastbedroom.register.view.PinyinUtils;
//
//import org.litepal.crud.DataSupport;
//
//import java.io.Serializable;
//
///**
// * Created by json on 2017/8/22.
// */
//
//public class RvSchoolBean extends DataSupport implements Serializable, Comparable {
//    private int id;
//    private String schoolName;
//    private char headChar;
//
//    public RvSchoolBean(int id, String schoolName) {
//        this.id = id;
//        this.schoolName = schoolName;
//        headChar = PinyinUtils.getHeadChar(schoolName);
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getSchoolName() {
//        return schoolName;
//    }
//
//    public void setSchoolName(String schoolName) {
//        this.schoolName = schoolName;
//    }
//
//    public char getHeadChar() {
//        return headChar;
//    }
//
//    public void setHeadChar(char headChar) {
//        this.headChar = headChar;
//    }
//
//    @Override
//    public int compareTo(@NonNull Object object) {
//        if (!(object instanceof RvSchoolBean)) {
//            throw new ClassCastException();
//        }
//        RvSchoolBean that = (RvSchoolBean) object;
//        if (getHeadChar() == '#') {
//            if (that.getHeadChar() == '#') {
//                return 0;
//            }
//            return 1;
//        }
//        if (that.getHeadChar() == '#') {
//            return -1;
//        } else if (that.getHeadChar() > getHeadChar()) {
//            return -1;
//        } else if (that.getHeadChar() == getHeadChar()) {
//            return 0;
//        }
//        return 1;
//    }
//
//
//    @Override
//    public boolean equals(Object object) {
//        if (this == object) {
//            return true;
//        }
//        if (object == null || getClass() != object.getClass()) {
//            return false;
//        }
//        RvSchoolBean that = (RvSchoolBean) object;
//        return getSchoolName().equals(that.getSchoolName()) && getId() == that.getId();
//    }
//}
