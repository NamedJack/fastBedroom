package com.ejar.fastbedroom.pay.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by json on 2017/9/13.
 */

public class SignWxBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 获取成功
     * token : null
     * data : {"sign":{"appid":"wx5cb4da2827891470","noncestr":"Sz2MXJ176NYngCnO","package":"Sign=io.dcloud.H591CB5F0","partnerid":"1458672702","prepayid":"wx20170413192743cba7b7658b0808959302","sign":"EEE44889902526872D1AA4A59CB77143","timestamp":1492082745}}
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
        /**
         * sign : {"appid":"wx5cb4da2827891470","noncestr":"Sz2MXJ176NYngCnO","package":"Sign=io.dcloud.H591CB5F0","partnerid":"1458672702","prepayid":"wx20170413192743cba7b7658b0808959302","sign":"EEE44889902526872D1AA4A59CB77143","timestamp":1492082745}
         */

        private SignBean sign;

        public SignBean getSign() {
            return sign;
        }

        public void setSign(SignBean sign) {
            this.sign = sign;
        }

        public static class SignBean {
            /**
             * appid : wx5cb4da2827891470
             * noncestr : Sz2MXJ176NYngCnO
             * package : Sign=io.dcloud.H591CB5F0
             * partnerid : 1458672702
             * prepayid : wx20170413192743cba7b7658b0808959302
             * sign : EEE44889902526872D1AA4A59CB77143
             * timestamp : 1492082745
             */

            private String appid;
            private String noncestr;
            @SerializedName("package")
            private String packageX;
            private String partnerid;
            private String prepayid;
            private String sign;
            private String timestamp;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
