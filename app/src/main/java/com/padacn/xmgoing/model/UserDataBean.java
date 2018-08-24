package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/29 0029.
 */

public class UserDataBean {

    /**
     * msg : 获取信息成功
     * result : 1
     * data : {"uId":70,"userName":"XST_cFkTx82807","picAdress":null,"userSex":null,"birthday":null,"city":null}
     * child : [{"childId":1,"childName":"夏大娃","childBirthday":1528300800000,"childSex":"1","createTime":1528338013000,"user_id":70},{"childId":2,"childName":"夏二妹","childBirthday":1528387200000,"childSex":"2","createTime":1528424444000,"user_id":70}]
     */
    private String msg;
    private int result;
    private DataBean data;
    private List<ChildBean> child;

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

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class DataBean {
        /**
         * uId : 70
         * userName : XST_cFkTx82807
         * picAdress : null
         * userSex : null
         * birthday : null
         * city : null
         */

        private int uId;
        private String userName;
        private String picAdress;
        private String userSex;
        private String birthday;
        private String city;

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

        public String getPicAdress() {
            return picAdress;
        }

        public void setPicAdress(String picAdress) {
            this.picAdress = picAdress;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    public static class ChildBean {
        /**
         * childId : 1
         * childName : 夏大娃
         * childBirthday : 1528300800000
         * childSex : 1
         * createTime : 1528338013000
         * user_id : 70
         */

        private int childId;
        private String childName;
        private String childBirthday;
        private String childSex;
        private int user_id;

        public int getChildId() {
            return childId;
        }

        public void setChildId(int childId) {
            this.childId = childId;
        }

        public String getChildName() {
            return childName;
        }

        public void setChildName(String childName) {
            this.childName = childName;
        }

        public String getChildBirthday() {
            return childBirthday;
        }

        public void setChildBirthday(String childBirthday) {
            this.childBirthday = childBirthday;
        }

        public String getChildSex() {
            return childSex;
        }

        public void setChildSex(String childSex) {
            this.childSex = childSex;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
