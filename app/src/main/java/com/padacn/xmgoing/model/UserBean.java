package com.padacn.xmgoing.model;

/**
 * Created by Administrator on 2018/6/5 0005.
 */

public class UserBean {

    /**
     * msg : 登录成功
     * result : 1
     * data : {"uId":65,"userName":"XST_3ZV0Z88733","passWord":null,"phoneNumber":"18200396342","picAdress":null,"havePassWord":"0","userSex":null}
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
         * uId : 65
         * userName : XST_3ZV0Z88733
         * passWord : null
         * phoneNumber : 18200396342
         * picAdress : null
         * havePassWord : 0
         * userSex : null
         */

        private int uId;
        private String userName;
        private Object passWord;
        private String phoneNumber;
        private Object picAdress;
        private String havePassWord;
        private Object userSex;

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

        public Object getPicAdress() {
            return picAdress;
        }

        public void setPicAdress(Object picAdress) {
            this.picAdress = picAdress;
        }

        public String getHavePassWord() {
            return havePassWord;
        }

        public void setHavePassWord(String havePassWord) {
            this.havePassWord = havePassWord;
        }

        public Object getUserSex() {
            return userSex;
        }

        public void setUserSex(Object userSex) {
            this.userSex = userSex;
        }
    }
}
