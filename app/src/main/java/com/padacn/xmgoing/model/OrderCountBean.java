package com.padacn.xmgoing.model;

/**
 * Created by Administrator on 2018/7/16 0016.
 */

public class OrderCountBean {

    /**
     * msg : 用户订单统计成功
     * result : 1
     * userData : {"uId":80,"userName":"INnoVition_","passWord":null,"phoneNumber":"18200396342","picAdress":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001530495315527","havePassWord":null,"userSex":"2"}
     * data : {"nopay":0,"rejected":1,"unuse":0,"nocomment":0}
     */

    private String msg;
    private int result;
    private UserDataBean userData;
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

    public UserDataBean getUserData() {
        return userData;
    }

    public void setUserData(UserDataBean userData) {
        this.userData = userData;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class UserDataBean {
        /**
         * uId : 80
         * userName : INnoVition_
         * passWord : null
         * phoneNumber : 18200396342
         * picAdress : https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001530495315527
         * havePassWord : null
         * userSex : 2
         */

        private int uId;
        private String userName;
        private Object passWord;
        private String phoneNumber;
        private String picAdress;
        private Object havePassWord;
        private String userSex;

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

        public Object getPassWord() {
            return passWord;
        }

        public void setPassWord(Object passWord) {
            this.passWord = passWord;
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

        public Object getHavePassWord() {
            return havePassWord;
        }

        public void setHavePassWord(Object havePassWord) {
            this.havePassWord = havePassWord;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }
    }

    public static class DataBean {
        /**
         * nopay : 0
         * rejected : 1
         * unuse : 0
         * nocomment : 0
         */

        private int nopay;
        private int rejected;
        private int unuse;
        private int nocomment;

        public int getNopay() {
            return nopay;
        }

        public void setNopay(int nopay) {
            this.nopay = nopay;
        }

        public int getRejected() {
            return rejected;
        }

        public void setRejected(int rejected) {
            this.rejected = rejected;
        }

        public int getUnuse() {
            return unuse;
        }

        public void setUnuse(int unuse) {
            this.unuse = unuse;
        }

        public int getNocomment() {
            return nocomment;
        }

        public void setNocomment(int nocomment) {
            this.nocomment = nocomment;
        }
    }
}
