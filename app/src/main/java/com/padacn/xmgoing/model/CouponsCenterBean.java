package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31 0031.
 */

public class CouponsCenterBean {

    /**
     * msg : 列表获取成功
     * result : 1
     * pageCount : 1
     * data : [{"couponId":7,"couponName":"自营满减","couponType":3,"typeName":"自营满减卷","term":1000,"reduction":30,"sellerId":2,"sellerName":"小书童自营","store":100,"iscity":true,"city":322,"cityName":"成都","explain":"小书童可用","flashUse":false,"useTime":null,"voidTime":null},{"couponId":6,"couponName":"商家满减","couponType":1,"typeName":"商家满减卷","term":1500,"reduction":60,"sellerId":1,"sellerName":"商家1","store":100,"iscity":true,"city":322,"cityName":"成都","explain":"指定商家使用，特惠商品不能使用","flashUse":true,"useTime":"2018-05-08","voidTime":"2018-06-20"},{"couponId":5,"couponName":"商家满减","couponType":1,"typeName":"商家满减卷","term":3000,"reduction":150,"sellerId":1,"sellerName":"商家1","store":150,"iscity":true,"city":322,"cityName":"成都","explain":"指定商家使用","flashUse":false,"useTime":null,"voidTime":null},{"couponId":4,"couponName":"平台满减","couponType":2,"typeName":"平台满减卷","term":4000,"reduction":200,"sellerId":null,"sellerName":"","store":200,"iscity":true,"city":322,"cityName":"成都","explain":"全场使用，满4000减免100","flashUse":false,"useTime":null,"voidTime":null}]
     * pageSize : 10
     * banner : [{"bannerId":35,"bannerType":0,"typeName":"product","linkId":420,"bannerPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1527037399576","asyniden":"lll3SsrHiW","statu":1,"picKey":"1527037399576","city":"成都","cityId":322,"bannerTittle":"灵隐飞来峰","subheading":"杭州三天两夜"},{"bannerId":38,"bannerType":0,"typeName":"product","linkId":420,"bannerPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1527037459541","asyniden":"RH6QNIDfcv","statu":1,"picKey":"1527037459541","city":"成都","cityId":322,"bannerTittle":"","subheading":""},{"bannerId":44,"bannerType":0,"typeName":"product","linkId":420,"bannerPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1527037487815","asyniden":"LJC021yx3i","statu":1,"picKey":"1527037487815","city":"成都","cityId":322,"bannerTittle":"灵隐飞来峰","subheading":"杭州三天两夜"},{"bannerId":43,"bannerType":0,"typeName":"product","linkId":420,"bannerPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1527037487200","asyniden":"LQ2UeWkQTn","statu":1,"picKey":"1527037487200","city":"成都","cityId":322,"bannerTittle":"灵隐飞来峰","subheading":"杭州三天两夜"},{"bannerId":40,"bannerType":0,"typeName":"product","linkId":420,"bannerPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1527037461147","asyniden":"zZtvuClt8m","statu":1,"picKey":"1527037461147","city":"成都","cityId":322,"bannerTittle":"","subheading":""}]
     * pageNum : 1
     */

    private String msg;
    private int result;
    private int pageCount;
    private int pageSize;
    private int pageNum;
    private List<DataBean> data;
    private List<BannerBean> banner;

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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
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

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public static class DataBean {
        /**
         * couponId : 7
         * couponName : 自营满减
         * couponType : 3
         * typeName : 自营满减卷
         * term : 1000
         * reduction : 30
         * sellerId : 2
         * sellerName : 小书童自营
         * store : 100
         * iscity : true
         * city : 322
         * cityName : 成都
         * explain : 小书童可用
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

    public static class BannerBean {
        /**
         * bannerId : 35
         * bannerType : 0
         * typeName : product
         * linkId : 420
         * bannerPic : http://csx-test.oss-cn-shenzhen.aliyuncs.com/1527037399576
         * asyniden : lll3SsrHiW
         * statu : 1
         * picKey : 1527037399576
         * city : 成都
         * cityId : 322
         * bannerTittle : 灵隐飞来峰
         * subheading : 杭州三天两夜
         */

        private int bannerId;
        private int bannerType;
        private String typeName;
        private int linkId;
        private String bannerPic;
        private String asyniden;
        private int statu;
        private String picKey;
        private String city;
        private int cityId;
        private String bannerTittle;
        private String subheading;

        public int getBannerId() {
            return bannerId;
        }

        public void setBannerId(int bannerId) {
            this.bannerId = bannerId;
        }

        public int getBannerType() {
            return bannerType;
        }

        public void setBannerType(int bannerType) {
            this.bannerType = bannerType;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getLinkId() {
            return linkId;
        }

        public void setLinkId(int linkId) {
            this.linkId = linkId;
        }

        public String getBannerPic() {
            return bannerPic;
        }

        public void setBannerPic(String bannerPic) {
            this.bannerPic = bannerPic;
        }

        public String getAsyniden() {
            return asyniden;
        }

        public void setAsyniden(String asyniden) {
            this.asyniden = asyniden;
        }

        public int getStatu() {
            return statu;
        }

        public void setStatu(int statu) {
            this.statu = statu;
        }

        public String getPicKey() {
            return picKey;
        }

        public void setPicKey(String picKey) {
            this.picKey = picKey;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getBannerTittle() {
            return bannerTittle;
        }

        public void setBannerTittle(String bannerTittle) {
            this.bannerTittle = bannerTittle;
        }

        public String getSubheading() {
            return subheading;
        }

        public void setSubheading(String subheading) {
            this.subheading = subheading;
        }
    }
}
