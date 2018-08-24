package com.padacn.xmgoing.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/9 0009.
 */

public class SureBuyBean implements Serializable {


    /**
     * sellergoods : {"goods":[{"carId":"11","cid":"1","pName":"22","pid":"693","tid":"1139","pnum":"1"}],"sellerId":"1"}
     */

    private SellergoodsBean sellergoods;

    public SellergoodsBean getSellergoods() {
        return sellergoods;
    }

    public void setSellergoods(SellergoodsBean sellergoods) {
        this.sellergoods = sellergoods;
    }

    public static class SellergoodsBean {
        /**
         * goods : [{"carId":"11","cid":"1","pName":"22","pid":"693","tid":"1139","pnum":"1"}]
         * sellerId : 1
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
             * carId : 11
             * cid : 1
             * pName : 22
             * pid : 693
             * tid : 1139
             * pnum : 1
             */

            private String carId;
            private String cid;
            private String pName;
            private String pid;
            private String tid;
            private String pnum;

            public String getCarId() {
                return carId;
            }

            public void setCarId(String carId) {
                this.carId = carId;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getPName() {
                return pName;
            }

            public void setPName(String pName) {
                this.pName = pName;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getPnum() {
                return pnum;
            }

            public void setPnum(String pnum) {
                this.pnum = pnum;
            }
        }
    }
}
