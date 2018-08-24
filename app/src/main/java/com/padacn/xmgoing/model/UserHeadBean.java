package com.padacn.xmgoing.model;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class UserHeadBean {

    /**
     * msg : 用户资料编辑成功
     * result : 1
     * data : {"uId":80,"userName":"INnoVition_","phoneNumber":"18200396342","picAdress":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/1820039634200000077445","loginTime":"2018-07-01 10:29:56","userSex":"2","birthday":null,"city":null}
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
         * uId : 80
         * userName : INnoVition_
         * phoneNumber : 18200396342
         * picAdress : https://csx-test.oss-cn-shenzhen.aliyuncs.com/1820039634200000077445
         * loginTime : 2018-07-01 10:29:56
         * userSex : 2
         * birthday : null
         * city : null
         */

        private int uId;
        private String userName;
        private String phoneNumber;
        private String picAdress;
        private String loginTime;
        private String userSex;
        private Object birthday;
        private Object city;

        public int getUId() {
            return uId;
        }

        public void setUId(int uId) {
            this.uId = uId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPicAdress() {
            return picAdress;
        }

        public void setPicAdress(String picAdress) {
            this.picAdress = picAdress;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }
    }
}
