package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/10 0010.
 */

public class UserCoupon {


    /**
     * msg : 用户优惠卷获取成功
     * result : 1
     * data : [{"couponId":10,"couponName":"平台满减","couponType":2,"typeName":"全平台满减卷","term":4000,"reduction":100,"sellerId":null,"sellerName":null,"store":100,"iscity":true,"city":322,"cityName":"成都","explain":"全平台使用","flashUse":false,"useTime":null,"voidTime":null},{"couponId":11,"couponName":"平台满减","couponType":2,"typeName":"全平台满减卷","term":3500,"reduction":60,"sellerId":null,"sellerName":null,"store":100,"iscity":true,"city":322,"cityName":"成都","explain":"全平台使用","flashUse":true,"useTime":"2018-06-01","voidTime":"2018-06-30"},{"couponId":7,"couponName":"自营满减","couponType":3,"typeName":"自营满减卷","term":1000,"reduction":30,"sellerId":2,"sellerName":"小书童自营","store":100,"iscity":true,"city":322,"cityName":"成都","explain":"小书童可用","flashUse":false,"useTime":null,"voidTime":null}]
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
         * couponId : 10
         * couponName : 平台满减
         * couponType : 2
         * typeName : 全平台满减卷
         * term : 4000
         * reduction : 100
         * sellerId : null
         * sellerName : null
         * store : 100
         * iscity : true
         * city : 322
         * cityName : 成都
         * explain : 全平台使用
         * flashUse : false
         * useTime : null
         * voidTime : null
         */

        private int couponId;
        private String couponName;
        private int couponType;
        private String typeName;
        private double term;
        private int reduction;
        private Long sellerId;
        private Object sellerName;
        private int store;
        private boolean iscity;
        private int city;
        private String cityName;
        private String explain;
        private boolean flashUse;
        private Object useTime;
        private Object voidTime;

        //是否可选
        private boolean isSelect;
        //是否用户选中
        private boolean isUserSelect;

        public boolean isUserSelect() {
            return isUserSelect;
        }

        public void setUserSelect(boolean userSelect) {
            isUserSelect = userSelect;
        }


        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

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

        public double getTerm() {
            return term;
        }

        public void setTerm(double term) {
            this.term = term;
        }

        public int getReduction() {
            return reduction;
        }

        public void setReduction(int reduction) {
            this.reduction = reduction;
        }

        public Long getSellerId() {
            return sellerId;
        }

        public void setSellerId(Long sellerId) {
            this.sellerId = sellerId;
        }

        public Object getSellerName() {
            return sellerName;
        }

        public void setSellerName(Object sellerName) {
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
