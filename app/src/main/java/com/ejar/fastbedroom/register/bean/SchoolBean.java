package com.ejar.fastbedroom.register.bean;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ejar.fastbedroom.register.view.PinyinUtils;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by json on 2017/8/16.
 */

public class SchoolBean {

    /**
     *  code : 200
     * okurl : null
     * failUrl : null
     * msg : 成功
     * token : null
     */


    private String code;
    private Object okurl;
    private Object failUrl;
    private String msg;
    private Object token;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getOkurl() {
        return okurl;
    }

    public void setOkurl(Object okurl) {
        this.okurl = okurl;
    }

    public Object getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(Object failUrl) {
        this.failUrl = failUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private boolean mes;
        private String time;
        private List<SchollBean> scholl;

        public boolean isMes() {
            return mes;
        }

        public void setMes(boolean mes) {
            this.mes = mes;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<SchollBean> getScholl() {
            return scholl;
        }

        public void setScholl(List<SchollBean> scholl) {
            this.scholl = scholl;
        }

        public static class SchollBean extends DataSupport implements Serializable, Comparable<SchollBean>{
            /**
             * id : 1
             * schollName : 北京大学
             * headChar: 名字首字母
             */
            public SchollBean(int id, String schollName) {
                this.id = id;
                this.schollName = schollName;
            }

            private int id;
            private String schollName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSchollName() {
                return schollName;
            }

            public void setSchollName(String schollName) {
                this.schollName = schollName;
            }

            @Override
            public int compareTo(@NonNull SchollBean o) {
                return 0;
            }


//            @Override
//            public boolean equals(Object object) {
//
//                if (this == object) {
//                    return true;
//                }
//                if (object == null || getClass() != object.getClass()) {
//                    return false;
//                }
//                SchollBean that = (SchollBean) object;
//                return getSchollName().equals(that.getSchollName()) && getId() == that.getId();
//
//
//            }

//            @Override
//            public int compareTo(@NonNull Object object) {
//                if (! (object instanceof SchollBean)){
//                    throw new ClassCastException();
//                }
////                if (! (object instanceof SchollBean)) {
//                    SchollBean that = (SchollBean) object;
//                    if (getHeadChar() == '#') {
//                        if (that.getHeadChar() == '#') {
//                            return 0;
//                        }
//                        return 1;
//                    }
//                    if (that.getHeadChar() == '#') {
//                        return -1;
//                    } else if (that.getHeadChar() > getHeadChar()) {
//                        return -1;
//                    } else if (that.getHeadChar() == getHeadChar()) {
//                        return 0;
//                    }
//                    return 1;
//            }


        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "mes=" + mes +
                    ", time='" + time + '\'' +
                    ", scholl=" + scholl +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "SchoolBean{" +
                "code='" + code + '\'' +
                ", okurl=" + okurl +
                ", failUrl=" + failUrl +
                ", msg='" + msg + '\'' +
                ", token=" + token +
                ", data=" + data +
                '}';
    }
}
