package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class JosnOne {


    /**
     * goods : [{"pName":"灵隐飞来峰","pnum":1,"tid":"71","pid":"426","carId":"64"}]
     * sellerId : 3
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
         * pName : 灵隐飞来峰
         * pnum : 1
         * tid : 71
         * pid : 426
         * carId : 64
         */

        private String pName;
        private String pnum;
        private String tid;
        private String pid;
        private String carId;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        private String cid;

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public String getPnum() {
            return pnum;
        }

        public void setPnum(String pnum) {
            this.pnum = pnum;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }
    }
}
