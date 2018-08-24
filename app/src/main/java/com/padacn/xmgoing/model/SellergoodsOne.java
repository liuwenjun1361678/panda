package com.padacn.xmgoing.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/10 0010.
 */

public class SellergoodsOne implements Serializable{


    /**
     * sellerId : 2
     * goods : [{"pid":"420","pName":"1","pnum":30,"tid":"45","cid":"955"}]
     */

    private String sellerId;
    private List<GoodsBean> goods;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * pid : 420
         * pName : 1
         * pnum : 30
         * tid : 45
         * cid : 955
         */

        private String pid;
        private String pName;
        private int pnum;
        private String tid;
        private String cid;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public int getPnum() {
            return pnum;
        }

        public void setPnum(int pnum) {
            this.pnum = pnum;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }
}
