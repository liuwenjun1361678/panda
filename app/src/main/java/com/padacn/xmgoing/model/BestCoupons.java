package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/10 0010.
 */

public class BestCoupons {

    /**
     * msg : 优惠卷获取成功
     * result : 1
     * useSellerCoupon : 0
     * data : {"coupon":[{"sellerId":null,"proCoupon":{"couponId":10,"couponName":"平台满减","couponType":2,"typeName":"全平台满减卷","term":4000,"reduction":100,"sellerId":null,"sellerName":null,"store":100,"iscity":true,"city":322,"cityName":"成都","explain":"全平台使用","flashUse":false,"useTime":null,"voidTime":null},"price":134900,"redution":100}],"price":[{"sellerId":2,"price":135000,"productPriceDtos":[{"pid":420,"tid":null,"cid":955,"price":4500,"pnum":30,"totailPrice":135000}]}],"totailPrice":135000}
     */

    private String msg;
    private int result;
    private int useSellerCoupon;
    private DataBean data;

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

    public int getUseSellerCoupon() {
        return useSellerCoupon;
    }

    public void setUseSellerCoupon(int useSellerCoupon) {
        this.useSellerCoupon = useSellerCoupon;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * coupon : [{"sellerId":null,"proCoupon":{"couponId":10,"couponName":"平台满减","couponType":2,"typeName":"全平台满减卷","term":4000,"reduction":100,"sellerId":null,"sellerName":null,"store":100,"iscity":true,"city":322,"cityName":"成都","explain":"全平台使用","flashUse":false,"useTime":null,"voidTime":null},"price":134900,"redution":100}]
         * price : [{"sellerId":2,"price":135000,"productPriceDtos":[{"pid":420,"tid":null,"cid":955,"price":4500,"pnum":30,"totailPrice":135000}]}]
         * totailPrice : 135000
         */

        private double totailPrice;
        private List<CouponBean> coupon;
        private List<PriceBean> price;

        public double getTotailPrice() {
            return totailPrice;
        }

        public void setTotailPrice(double totailPrice) {
            this.totailPrice = totailPrice;
        }

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public List<PriceBean> getPrice() {
            return price;
        }

        public void setPrice(List<PriceBean> price) {
            this.price = price;
        }

        public static class CouponBean {
            /**
             * sellerId : null
             * proCoupon : {"couponId":10,"couponName":"平台满减","couponType":2,"typeName":"全平台满减卷","term":4000,"reduction":100,"sellerId":null,"sellerName":null,"store":100,"iscity":true,"city":322,"cityName":"成都","explain":"全平台使用","flashUse":false,"useTime":null,"voidTime":null}
             * price : 134900
             * redution : 100
             */

            private Long sellerId;
            private ProCouponBean proCoupon;
            private double price;
            private int redution;

            public Long getSellerId() {
                return sellerId;
            }

            public void setSellerId(Long sellerId) {
                this.sellerId = sellerId;
            }

            public ProCouponBean getProCoupon() {
                return proCoupon;
            }

            public void setProCoupon(ProCouponBean proCoupon) {
                this.proCoupon = proCoupon;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getRedution() {
                return redution;
            }

            public void setRedution(int redution) {
                this.redution = redution;
            }

            public static class ProCouponBean {
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
                private int term;
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

        public static class PriceBean {
            /**
             * sellerId : 2
             * price : 135000
             * productPriceDtos : [{"pid":420,"tid":null,"cid":955,"price":4500,"pnum":30,"totailPrice":135000}]
             */

            private Long sellerId;
            private double price;
            private List<ProductPriceDtosBean> productPriceDtos;

            public Long getSellerId() {
                return sellerId;
            }

            public void setSellerId(Long sellerId) {
                this.sellerId = sellerId;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public List<ProductPriceDtosBean> getProductPriceDtos() {
                return productPriceDtos;
            }

            public void setProductPriceDtos(List<ProductPriceDtosBean> productPriceDtos) {
                this.productPriceDtos = productPriceDtos;
            }

            public static class ProductPriceDtosBean {
                /**
                 * pid : 420
                 * tid : null
                 * cid : 955
                 * price : 4500
                 * pnum : 30
                 * totailPrice : 135000
                 */

                private int pid;
                private Object tid;
                private int cid;
                private double price;
                private int pnum;
                private double totailPrice;

                public int getPid() {
                    return pid;
                }

                public void setPid(int pid) {
                    this.pid = pid;
                }

                public Object getTid() {
                    return tid;
                }

                public void setTid(Object tid) {
                    this.tid = tid;
                }

                public int getCid() {
                    return cid;
                }

                public void setCid(int cid) {
                    this.cid = cid;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getPnum() {
                    return pnum;
                }

                public void setPnum(int pnum) {
                    this.pnum = pnum;
                }

                public double getTotailPrice() {
                    return totailPrice;
                }

                public void setTotailPrice(double totailPrice) {
                    this.totailPrice = totailPrice;
                }
            }
        }
    }
}
