package com.ejar.fastbedroom.pay.bean;

/**
 * Created by json on 2017/9/13.
 */

public class SignBean {

    /**
     * code : 200
     * okurl : null
     * failUrl : null
     * msg : 提交成功
     * token : null
     * data : {"signatureorder":"app_id=2017033006478366&biz_content=%7B%22out_trade_no%22%3A%22ps20170913162531175%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22%E5%BF%AB%E5%88%B0%E5%AF%9D%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%22%2C%22timeout_express%22%3A%2230m%22%2C%22body%22%3A%22%E5%BF%AB%E5%88%B0%E5%AF%9D%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22seller_id%22%3A%222088621208345438%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F116.62.181.152%3A9009%2FgetPayNotif&sign_type=RSA&timestamp=2017-09-13+16%3A25%3A46&version=1.0&sign=ev8UPKUg9nRmBlpd5%2FWXiOdUouKXVtnrc%2FYmHbDp8N6djLyr7dSO28UhDlMsWIX9Q7gzQ96gZRtOA4hbqA2%2BPqutNGDN60GYiwCn%2Fqc8goCwQyMtB1SimvIG8FhLklQ9IumY3l%2FmENaMU4ijvLs%2F0wrQvJSMK6GTJFCAu0D7cIzBOUD2l4UMYARwYvqkyhqFCarnhWcB%2F89tRPXsmP%2B4xvZq7DH6kLFOPPTbGJyYC7MdfUNG%2F55VoaqS8yWK5nPti1VEQduiRWDukvEWkkMgmLkDVdMzaRlHOaSxwG%2FlMg9uUdF4UZcnzlFzy%2BkPokgP84M5CFmAO%2Bg3iLZah4Ww8A%3D%3D"}
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
         * signatureorder : app_id=2017033006478366&biz_content=%7B%22out_trade_no%22%3A%22ps20170913162531175%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22%E5%BF%AB%E5%88%B0%E5%AF%9D%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%22%2C%22timeout_express%22%3A%2230m%22%2C%22body%22%3A%22%E5%BF%AB%E5%88%B0%E5%AF%9D%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22seller_id%22%3A%222088621208345438%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F116.62.181.152%3A9009%2FgetPayNotif&sign_type=RSA&timestamp=2017-09-13+16%3A25%3A46&version=1.0&sign=ev8UPKUg9nRmBlpd5%2FWXiOdUouKXVtnrc%2FYmHbDp8N6djLyr7dSO28UhDlMsWIX9Q7gzQ96gZRtOA4hbqA2%2BPqutNGDN60GYiwCn%2Fqc8goCwQyMtB1SimvIG8FhLklQ9IumY3l%2FmENaMU4ijvLs%2F0wrQvJSMK6GTJFCAu0D7cIzBOUD2l4UMYARwYvqkyhqFCarnhWcB%2F89tRPXsmP%2B4xvZq7DH6kLFOPPTbGJyYC7MdfUNG%2F55VoaqS8yWK5nPti1VEQduiRWDukvEWkkMgmLkDVdMzaRlHOaSxwG%2FlMg9uUdF4UZcnzlFzy%2BkPokgP84M5CFmAO%2Bg3iLZah4Ww8A%3D%3D
         */

        private String signatureorder;

        public String getSignatureorder() {
            return signatureorder;
        }

        public void setSignatureorder(String signatureorder) {
            this.signatureorder = signatureorder;
        }
    }
}
