package com.padacn.xmgoing.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/23 0023.
 */

public class SureGoodsBean implements Serializable {

    /**
     * goods : [{"pName":"灵隐飞来峰","pnum":"26","tid":"45","pid":"420","cid":"952","contactIds":[{"id":1,"name":"1"}]}]
     * sellerId : 2
     */

    //卖家ID
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

    public static class GoodsBean implements Serializable {
        /**
         * pName : 灵隐飞来峰
         * pnum : 26
         * tid : 45
         * pid : 420
         * cid : 952
         * contactIds : [{"id":1,"name":"1"}]
         */

        //商品名称
        private String pName;
        //商品数量
        private String pnum;
        //套餐ID
        private String tid;
        //商品ID
        private String pid;
        //套餐日期ID
        private String cid;
        //联系人

        private String picPath; //商品图片路径
        private String tiketName;//套餐名称
        private String travleTime; //出行时间
        private String totalPerson; //总共人数
        private String price;       //当前商品的单价
        private String totalPrice;   //商品总价
        private boolean useIdcard;   //是否出行人
        private String travelNum;    //出行人数量

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        private String carId;//购物车ID


        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }

        public String getTiketName() {
            return tiketName;
        }

        public void setTiketName(String tiketName) {
            this.tiketName = tiketName;
        }

        public String getTravleTime() {
            return travleTime;
        }

        public void setTravleTime(String travleTime) {
            this.travleTime = travleTime;
        }

        public String getTotalPerson() {
            return totalPerson;
        }

        public void setTotalPerson(String totalPerson) {
            this.totalPerson = totalPerson;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public boolean isUseIdcard() {
            return useIdcard;
        }

        public void setUseIdcard(boolean useIdcard) {
            this.useIdcard = useIdcard;
        }

        public String getTravelNum() {
            return travelNum;
        }

        public void setTravelNum(String travelNum) {
            this.travelNum = travelNum;
        }

        private List<ContactIdsBean> contactIds;

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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public List<ContactIdsBean> getContactIds() {
            return contactIds;
        }

        public void setContactIds(List<ContactIdsBean> contactIds) {
            this.contactIds = contactIds;
        }

        public static class ContactIdsBean {
            /**
             * id : 1
             * name : 1
             */

            private int id;
            private String name;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
