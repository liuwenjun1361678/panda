package com.padacn.xmgoing.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class AutoBean {

    /**
     * msg : 签名已生成
     * result : 1
     * data : {"appid":"wx6c18bdc67e076407","noncestr":"OdJZAYJF4xjXDFLZbS0WvRKhwVv5ko3O","package":"Sign=WXPay","partnerid":"1494888052","prepayid":"wx13193928386273e9037324b62414175278","sign":"E181C4C8EDF53CFDBB28762FBAB2389C","timestamp":1528889965}
     */

    private String msg;
    private int result;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wx6c18bdc67e076407
         * noncestr : OdJZAYJF4xjXDFLZbS0WvRKhwVv5ko3O
         * package : Sign=WXPay
         * partnerid : 1494888052
         * prepayid : wx13193928386273e9037324b62414175278
         * sign : E181C4C8EDF53CFDBB28762FBAB2389C
         * timestamp : 1528889965
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private int timestamp;

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

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }
    }
}
