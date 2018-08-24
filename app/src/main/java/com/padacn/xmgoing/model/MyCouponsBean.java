package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class MyCouponsBean {

    /**
     * msg : 列表获取成功
     * result : 1
     * voidCoupon : 0
     * pageCount : 0
     * data : [{"couponId":12,"couponName":"商家满减","couponType":1,"typeName":"商家满减卷","term":2000,"reduction":70,"sellerId":1,"sellerName":"商家1","store":91,"iscity":true,"city":322,"cityName":"成都","explain":"指定商家可使用","flashUse":false,"useTime":null,"voidTime":null}]
     * couponCount : 1
     * pageSize : 10
     * pageNum : 1
     */

    private String msg;
    private int result;
    private int voidCoupon;
    private int pageCount;
    private int couponCount;
    private int pageSize;
    private int pageNum;
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

    public int getVoidCoupon() {
        return voidCoupon;
    }

    public void setVoidCoupon(int voidCoupon) {
        this.voidCoupon = voidCoupon;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * couponId : 12
         * couponName : 商家满减
         * couponType : 1
         * typeName : 商家满减卷
         * term : 2000
         * reduction : 70
         * sellerId : 1
         * sellerName : 商家1
         * store : 91
         * iscity : true
         * city : 322
         * cityName : 成都
         * explain : 指定商家可使用
         * flashUse : false
         * useTime : null
         * voidTime : null
         */

        private int couponId;
        private String couponName;
        private int couponType;
        private String typeName;
        private int term;
        private int reduction;
        private int sellerId;
        private String sellerName;
        private int store;
        private boolean iscity;
        private int city;
        private String cityName;
        private String explain;
        private boolean flashUse;
        private Object useTime;
        private Object voidTime;

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getTerm() {
            return term;
        }

        public void setTerm(int term) {
            this.term = term;
        }

        public int getReduction() {
            return reduction;
        }

        public void setReduction(int reduction) {
            this.reduction = reduction;
        }

        public int getSellerId() {
            return sellerId;
        }

        public void setSellerId(int sellerId) {
            this.sellerId = sellerId;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public int getStore() {
            return store;
        }

        public void setStore(int store) {
            this.store = store;
        }

        public boolean isIscity() {
            return iscity;
        }

        public void setIscity(boolean iscity) {
            this.iscity = iscity;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public boolean isFlashUse() {
            return flashUse;
        }

        public void setFlashUse(boolean flashUse) {
            this.flashUse = flashUse;
        }

        public Object getUseTime() {
            return useTime;
        }

        public void setUseTime(Object useTime) {
            this.useTime = useTime;
        }

        public Object getVoidTime() {
            return voidTime;
        }

        public void setVoidTime(Object voidTime) {
            this.voidTime = voidTime;
        }
    }
}
