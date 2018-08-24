package com.padacn.xmgoing.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.padacn.xmgoing.api.Constans;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class OrderBean {


    /**
     * msg : 获取订单详情成功
     * result : 1
     * data : {"id":224,"sn":"152888167715634492","price":13330,"pCount":3,"statu":1,"createTime":"2018-06-13","allProductCoupon":false,"bookedBy":"2018-06-13 17:21:17.157","tel":"182","expireTime":"2018-06-14 00:00:00","list":[{"name":"csx","id":1,"price":4430,"sn":"152888167715634348","statu":1,"list":[{"orderProductId":346,"orderId":224,"proId":437,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962425399","proType":114,"typeName":"套餐2","sellerId":1,"sellerName":"csx","createTime":"2018-06-13","sn":"152888167721948348","verification":"467938636348951","statu":1,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":1,"list":null,"allProductCoupon":false}]},{"name":"小书童自营","id":2,"price":8900,"sn":"152888167715634348","statu":1,"list":[{"orderProductId":347,"orderId":224,"proId":420,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962174169","proType":45,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-13","sn":"152888167731617881","verification":"674374524138826","statu":1,"useDay":"2018-06-11","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":13,"userId":1,"list":null,"allProductCoupon":false},{"orderProductId":348,"orderId":224,"proId":420,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962174169","proType":45,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-13","sn":"152888167737339455","verification":"915431314342587","statu":1,"useDay":"2018-06-21","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":13,"userId":1,"list":null,"allProductCoupon":false}]}]}
     */

    private String msg;
    private int result;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * id : 224
         * sn : 152888167715634492
         * price : 13330
         * pCount : 3
         * statu : 1
         * createTime : 2018-06-13
         * allProductCoupon : false
         * bookedBy : 2018-06-13 17:21:17.157
         * tel : 182
         * expireTime : 2018-06-14 00:00:00
         * list : [{"name":"csx","id":1,"price":4430,"sn":"152888167715634348","statu":1,"list":[{"orderProductId":346,"orderId":224,"proId":437,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962425399","proType":114,"typeName":"套餐2","sellerId":1,"sellerName":"csx","createTime":"2018-06-13","sn":"152888167721948348","verification":"467938636348951","statu":1,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":1,"list":null,"allProductCoupon":false}]},{"name":"小书童自营","id":2,"price":8900,"sn":"152888167715634348","statu":1,"list":[{"orderProductId":347,"orderId":224,"proId":420,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962174169","proType":45,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-13","sn":"152888167731617881","verification":"674374524138826","statu":1,"useDay":"2018-06-11","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":13,"userId":1,"list":null,"allProductCoupon":false},{"orderProductId":348,"orderId":224,"proId":420,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962174169","proType":45,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-13","sn":"152888167737339455","verification":"915431314342587","statu":1,"useDay":"2018-06-21","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":13,"userId":1,"list":null,"allProductCoupon":false}]}]
         */

        private int id;
        private String sn;
        private double price;
        private int pCount;
        private int statu;
        private String createTime;
        private boolean allProductCoupon;
        private String bookedBy;
        private String tel;
        private String expireTime;
        private List<ListBeanX> list;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getPCount() {
            return pCount;
        }

        public void setPCount(int pCount) {
            this.pCount = pCount;
        }

        public int getStatu() {
            return statu;
        }

        public void setStatu(int statu) {
            this.statu = statu;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isAllProductCoupon() {
            return allProductCoupon;
        }

        public void setAllProductCoupon(boolean allProductCoupon) {
            this.allProductCoupon = allProductCoupon;
        }

        public String getBookedBy() {
            return bookedBy;
        }

        public void setBookedBy(String bookedBy) {
            this.bookedBy = bookedBy;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public static class ListBeanX {
            /**
             * name : csx
             * id : 1
             * price : 4430
             * sn : 152888167715634348
             * statu : 1
             * list : [{"orderProductId":346,"orderId":224,"proId":437,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962425399","proType":114,"typeName":"套餐2","sellerId":1,"sellerName":"csx","createTime":"2018-06-13","sn":"152888167721948348","verification":"467938636348951","statu":1,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":1,"list":null,"allProductCoupon":false}]
             */

            private String name;
            private int id;
            private double price;
            private String sn;
            private int statu;
            private List<ListBean> list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public int getStatu() {
                return statu;
            }

            public void setStatu(int statu) {
                this.statu = statu;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * orderProductId : 346
                 * orderId : 224
                 * proId : 437
                 * proName : 灵隐飞来峰
                 * proCount : 1
                 * price : 4500
                 * proPic : http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962425399
                 * proType : 114
                 * typeName : 套餐2
                 * sellerId : 1
                 * sellerName : csx
                 * createTime : 2018-06-13
                 * sn : 152888167721948348
                 * verification : 467938636348951
                 * statu : 1
                 * useDay : null
                 * flashUse : false
                 * periodOfTime : null
                 * excursion : 1成人+2儿童
                 * useCoupon : true
                 * couponId : 12
                 * userId : 1
                 * list : null
                 * allProductCoupon : false
                 */

                private int orderProductId;
                private int orderId;
                private int proId;
                private String proName;
                private int proCount;
                private double price;
                private String proPic;
                private int proType;
                private String typeName;
                private int sellerId;
                private String sellerName;
                private String createTime;
                private String sn;
                private String verification;
                private int statu;
                private Object useDay;
                private boolean flashUse;
                private Object periodOfTime;
                private String excursion;
                private boolean useCoupon;
                private int couponId;
                private int userId;
                private Object list;
                private boolean allProductCoupon;

                public int getOrderProductId() {
                    return orderProductId;
                }

                public void setOrderProductId(int orderProductId) {
                    this.orderProductId = orderProductId;
                }

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public int getProId() {
                    return proId;
                }

                public void setProId(int proId) {
                    this.proId = proId;
                }

                public String getProName() {
                    return proName;
                }

                public void setProName(String proName) {
                    this.proName = proName;
                }

                public int getProCount() {
                    return proCount;
                }

                public void setProCount(int proCount) {
                    this.proCount = proCount;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public String getProPic() {
                    return proPic;
                }

                public void setProPic(String proPic) {
                    this.proPic = proPic;
                }

                public int getProType() {
                    return proType;
                }

                public void setProType(int proType) {
                    this.proType = proType;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
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

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getSn() {
                    return sn;
                }

                public void setSn(String sn) {
                    this.sn = sn;
                }

                public String getVerification() {
                    return verification;
                }

                public void setVerification(String verification) {
                    this.verification = verification;
                }

                public int getStatu() {
                    return statu;
                }

                public void setStatu(int statu) {
                    this.statu = statu;
                }

                public Object getUseDay() {
                    return useDay;
                }

                public void setUseDay(Object useDay) {
                    this.useDay = useDay;
                }

                public boolean isFlashUse() {
                    return flashUse;
                }

                public void setFlashUse(boolean flashUse) {
                    this.flashUse = flashUse;
                }

                public Object getPeriodOfTime() {
                    return periodOfTime;
                }

                public void setPeriodOfTime(Object periodOfTime) {
                    this.periodOfTime = periodOfTime;
                }

                public String getExcursion() {
                    return excursion;
                }

                public void setExcursion(String excursion) {
                    this.excursion = excursion;
                }

                public boolean isUseCoupon() {
                    return useCoupon;
                }

                public void setUseCoupon(boolean useCoupon) {
                    this.useCoupon = useCoupon;
                }

                public int getCouponId() {
                    return couponId;
                }

                public void setCouponId(int couponId) {
                    this.couponId = couponId;
                }

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }

                public Object getList() {
                    return list;
                }

                public void setList(Object list) {
                    this.list = list;
                }

                public boolean isAllProductCoupon() {
                    return allProductCoupon;
                }

                public void setAllProductCoupon(boolean allProductCoupon) {
                    this.allProductCoupon = allProductCoupon;
                }
            }
        }
    }
}
