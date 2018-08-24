package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29 0029.
 */

public class HotSearchBean {

    /**
     * msg : 热门获取成功
     * result : 1
     * data : [{"hotId":1,"name":"灵隐山","type":1,"typeName":"product","sort":1,"isshow":true},{"hotId":6,"name":"欢乐谷","type":1,"typeName":"strategy","sort":6,"isshow":true},{"hotId":7,"name":"国色天香","type":1,"typeName":"product","sort":7,"isshow":true}]
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
         * hotId : 1
         * name : 灵隐山
         * type : 1
         * typeName : product
         * sort : 1
         * isshow : true
         */

        private int hotId;
        private String name;
        private int type;
        private String typeName;
        private int sort;
        private boolean isshow;

        public int getHotId() {
            return hotId;
        }

        public void setHotId(int hotId) {
            this.hotId = hotId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public boolean isIsshow() {
            return isshow;
        }

        public void setIsshow(boolean isshow) {
            this.isshow = isshow;
        }
    }
}
