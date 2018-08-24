package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/25 0025.
 */

public class ContactsDataBean {

    /**
     * msg : 获取联系人列表成功
     * result : 1
     * data : [{"cId":12,"user_id":70,"conName":"?????","conIdcard":"511623199109203953","conPhoneNumber":"12212345123","isChildren":"0","conSex":"2","conBirthday":"1991-09-20"},{"cId":13,"user_id":70,"conName":"打的费单独q","conIdcard":"511623199109203953","conPhoneNumber":"12212345123","isChildren":"0","conSex":"2","conBirthday":"1991-09-20"}]
     */

    private String msg;
    private int result;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cId : 12
         * user_id : 70
         * conName : ?????
         * conIdcard : 511623199109203953
         * conPhoneNumber : 12212345123
         * isChildren : 0
         * conSex : 2
         * conBirthday : 1991-09-20
         */

        private int cId;
        private int user_id;
        private String conName;
        private String conIdcard;
        private String conPhoneNumber;
        private String isChildren;
        private String conSex;
        private String conBirthday;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getCId() {
            return cId;
        }

        public void setCId(int cId) {
            this.cId = cId;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getConName() {
            return conName;
        }

        public void setConName(String conName) {
            this.conName = conName;
        }

        public String getConIdcard() {
            return conIdcard;
        }

        public void setConIdcard(String conIdcard) {
            this.conIdcard = conIdcard;
        }

        public String getConPhoneNumber() {
            return conPhoneNumber;
        }

        public void setConPhoneNumber(String conPhoneNumber) {
            this.conPhoneNumber = conPhoneNumber;
        }

        public String getIsChildren() {
            return isChildren;
        }

        public void setIsChildren(String isChildren) {
            this.isChildren = isChildren;
        }

        public String getConSex() {
            return conSex;
        }

        public void setConSex(String conSex) {
            this.conSex = conSex;
        }

        public String getConBirthday() {
            return conBirthday;
        }

        public void setConBirthday(String conBirthday) {
            this.conBirthday = conBirthday;
        }
    }
}
