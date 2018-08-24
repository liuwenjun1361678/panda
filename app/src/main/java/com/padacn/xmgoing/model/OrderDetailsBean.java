package com.padacn.xmgoing.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/18 0018.
 */

public class OrderDetailsBean {

    /**
     * msg : 获取订单详情成功
     * result : 1
     * data : {"id":351,"sn":"152928966697077175","price":17800,"pCount":4,"statu":2,"createTime":"2018-06-18","allProductCoupon":true,"bookedBy":"2018-06-18 00:00:00","tel":"13540157043","expireTime":"2018-06-19 10:41:16","signType":1,"list":[{"name":"csx","id":1,"price":13500,"sn":"152928966697077415","statu":2,"list":[{"orderProductId":469,"orderId":351,"proId":693,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990064118","proType":1142,"typeName":"套餐4","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966706285415","verification":"542330315528152","statu":2,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":22652,"verify":false,"verifyDate":null,"list":[{"orderId":469,"contactId":23,"name":"颜真卿"}],"allProductCoupon":true},{"orderProductId":470,"orderId":351,"proId":436,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962409928","proType":111,"typeName":"套餐3","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966723888818","verification":"076066944148271","statu":2,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":null,"verify":false,"verifyDate":null,"list":[],"allProductCoupon":true},{"orderProductId":471,"orderId":351,"proId":430,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962314897","proType":87,"typeName":"套餐3","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966736888558","verification":"821914672616655","statu":2,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":null,"verify":false,"verifyDate":null,"list":[],"allProductCoupon":true}]},{"name":"小书童自营","id":2,"price":4500,"sn":"152928966697077415","statu":2,"list":[{"orderProductId":472,"orderId":351,"proId":420,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962174169","proType":45,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-18","sn":"152928966747626166","verification":"476535327833056","statu":2,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":null,"verify":false,"verifyDate":null,"list":[],"allProductCoupon":true}]}],"sign":true}
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
         * id : 351
         * sn : 152928966697077175
         * price : 17800.0
         * pCount : 4
         * statu : 2
         * createTime : 2018-06-18
         * allProductCoupon : true
         * bookedBy : 2018-06-18 00:00:00
         * tel : 13540157043
         * expireTime : 2018-06-19 10:41:16
         * signType : 1
         * list : [{"name":"csx","id":1,"price":13500,"sn":"152928966697077415","statu":2,"list":[{"orderProductId":469,"orderId":351,"proId":693,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990064118","proType":1142,"typeName":"套餐4","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966706285415","verification":"542330315528152","statu":2,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":22652,"verify":false,"verifyDate":null,"list":[{"orderId":469,"contactId":23,"name":"颜真卿"}],"allProductCoupon":true},{"orderProductId":470,"orderId":351,"proId":436,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962409928","proType":111,"typeName":"套餐3","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966723888818","verification":"076066944148271","statu":2,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":null,"verify":false,"verifyDate":null,"list":[],"allProductCoupon":true},{"orderProductId":471,"orderId":351,"proId":430,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962314897","proType":87,"typeName":"套餐3","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966736888558","verification":"821914672616655","statu":2,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":null,"verify":false,"verifyDate":null,"list":[],"allProductCoupon":true}]},{"name":"小书童自营","id":2,"price":4500,"sn":"152928966697077415","statu":2,"list":[{"orderProductId":472,"orderId":351,"proId":420,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962174169","proType":45,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-18","sn":"152928966747626166","verification":"476535327833056","statu":2,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":null,"verify":false,"verifyDate":null,"list":[],"allProductCoupon":true}]}]
         * sign : true
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
        private int signType;
        private boolean sign;
        private List<ListBeanXX> list;

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

        public int getSignType() {
            return signType;
        }

        public void setSignType(int signType) {
            this.signType = signType;
        }

        public boolean isSign() {
            return sign;
        }

        public void setSign(boolean sign) {
            this.sign = sign;
        }

        public List<ListBeanXX> getList() {
            return list;
        }

        public void setList(List<ListBeanXX> list) {
            this.list = list;
        }

        public static class ListBeanXX {
            /**
             * name : csx
             * id : 1
             * price : 13500.0
             * sn : 152928966697077415
             * statu : 2
             * list : [{"orderProductId":469,"orderId":351,"proId":693,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990064118","proType":1142,"typeName":"套餐4","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966706285415","verification":"542330315528152","statu":2,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":22652,"verify":false,"verifyDate":null,"list":[{"orderId":469,"contactId":23,"name":"颜真卿"}],"allProductCoupon":true},{"orderProductId":470,"orderId":351,"proId":436,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962409928","proType":111,"typeName":"套餐3","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966723888818","verification":"076066944148271","statu":2,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":null,"verify":false,"verifyDate":null,"list":[],"allProductCoupon":true},{"orderProductId":471,"orderId":351,"proId":430,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962314897","proType":87,"typeName":"套餐3","sellerId":1,"sellerName":"csx","createTime":"2018-06-18","sn":"152928966736888558","verification":"821914672616655","statu":2,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":10,"userId":71,"cid":null,"verify":false,"verifyDate":null,"list":[],"allProductCoupon":true}]
             */

            private String name;
            private int id;
            private double price;
            private String sn;
            private int statu;
            private List<ListBeanX> list;

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

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX implements Serializable {
                /**
                 * orderProductId : 469
                 * orderId : 351
                 * proId : 693
                 * proName : 灵隐飞来峰
                 * proCount : 1
                 * price : 4500.0
                 * proPic : http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990064118
                 * proType : 1142
                 * typeName : 套餐4
                 * sellerId : 1
                 * sellerName : csx
                 * createTime : 2018-06-18
                 * sn : 152928966706285415
                 * verification : 542330315528152
                 * statu : 2
                 * useDay : 2018-05-27
                 * flashUse : false
                 * periodOfTime : null
                 * excursion : 1成人+2儿童
                 * useCoupon : true
                 * couponId : 10
                 * userId : 71
                 * cid : 22652
                 * verify : false
                 * verifyDate : null
                 * list : [{"orderId":469,"contactId":23,"name":"颜真卿"}]
                 * allProductCoupon : true
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
                private String useDay;
                private boolean flashUse;
                private Object periodOfTime;
                private String excursion;
                private boolean useCoupon;
                private int couponId;
                private int userId;
                private int cid;
                private boolean verify;
                private Object verifyDate;
                private boolean allProductCoupon;
                private List<ListBean> list;

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

                public String getUseDay() {
                    return useDay;
                }

                public void setUseDay(String useDay) {
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

                public int getCid() {
                    return cid;
                }

                public void setCid(int cid) {
                    this.cid = cid;
                }

                public boolean isVerify() {
                    return verify;
                }

                public void setVerify(boolean verify) {
                    this.verify = verify;
                }

                public Object getVerifyDate() {
                    return verifyDate;
                }

                public void setVerifyDate(Object verifyDate) {
                    this.verifyDate = verifyDate;
                }

                public boolean isAllProductCoupon() {
                    return allProductCoupon;
                }

                public void setAllProductCoupon(boolean allProductCoupon) {
                    this.allProductCoupon = allProductCoupon;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean implements Serializable {
                    /**
                     * orderId : 469
                     * contactId : 23
                     * name : 颜真卿
                     */

                    private int orderId;
                    private int contactId;
                    private String name;

                    public int getOrderId() {
                        return orderId;
                    }

                    public void setOrderId(int orderId) {
                        this.orderId = orderId;
                    }

                    public int getContactId() {
                        return contactId;
                    }

                    public void setContactId(int contactId) {
                        this.contactId = contactId;
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
    }
}
