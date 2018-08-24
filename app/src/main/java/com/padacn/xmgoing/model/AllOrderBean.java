package com.padacn.xmgoing.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.padacn.xmgoing.api.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/17 0017.
 */

public class AllOrderBean {


    /**
     * msg : 获取用户订单成功
     * result : 1
     * pageCount : 2
     * data : [{"id":313,"sn":"152921917151820884","price":4400,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 15:06:13","signType":1,"list":[{"name":"小书童自营","id":2,"price":4400,"sn":"152921917151820709","statu":1,"list":[{"orderProductId":434,"orderId":313,"proId":421,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962189085","proType":49,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-17","sn":"152921917161124709","verification":"297348427878831","statu":1,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":13,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":311,"sn":"152921524417126302","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 14:01:03","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152921524417126875","statu":1,"list":[{"orderProductId":432,"orderId":311,"proId":439,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962466451","proType":121,"typeName":"套餐1","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152921524429421875","verification":"449487308765855","statu":1,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":310,"sn":"152921478818926791","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 13:57:24","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152921478818926873","statu":1,"list":[{"orderProductId":431,"orderId":310,"proId":693,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990064118","proType":1139,"typeName":"套餐1","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152921478834235873","verification":"428332482131463","statu":1,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":309,"sn":"152921395956020251","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 13:39:29","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152921395956020250","statu":1,"list":[{"orderProductId":430,"orderId":309,"proId":693,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990064118","proType":1139,"typeName":"套餐1","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152921395965147250","verification":"733784142309228","statu":1,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":308,"sn":"152921052650468466","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 12:42:08","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152921052650468466","statu":1,"list":[{"orderProductId":429,"orderId":308,"proId":693,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990064118","proType":1140,"typeName":"套餐2","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152921052658614466","verification":"863530168842823","statu":1,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":307,"sn":"152921037221350446","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 12:39:34","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152921037221350747","statu":1,"list":[{"orderProductId":428,"orderId":307,"proId":784,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526991462476","proType":1503,"typeName":"套餐1","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152921037228615747","verification":"812912744084741","statu":1,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":306,"sn":"152921012658373845","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 12:35:28","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152921012658373702","statu":1,"list":[{"orderProductId":427,"orderId":306,"proId":784,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526991462476","proType":1503,"typeName":"套餐1","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152921012667950702","verification":"868416766091265","statu":1,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":305,"sn":"152921008385006246","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 12:34:49","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152921008385006174","statu":1,"list":[{"orderProductId":426,"orderId":305,"proId":784,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526991462476","proType":1504,"typeName":"套餐2","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152921008397869174","verification":"418731325627266","statu":1,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":304,"sn":"152920982373817012","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 12:30:29","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152920982373817057","statu":1,"list":[{"orderProductId":425,"orderId":304,"proId":693,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990064118","proType":1139,"typeName":"套餐1","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152920982382071057","verification":"138211533956576","statu":1,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true},{"id":303,"sn":"152920827542751684","price":4430,"pCount":1,"statu":1,"createTime":"2018-06-17","allProductCoupon":false,"bookedBy":"2018-06-17 00:00:00","tel":"13540157043","expireTime":"2018-06-18 12:04:39","signType":1,"list":[{"name":"csx","id":1,"price":4430,"sn":"152920827542751452","statu":1,"list":[{"orderProductId":424,"orderId":303,"proId":723,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526990506063","proType":1259,"typeName":"套餐1","sellerId":1,"sellerName":"csx","createTime":"2018-06-17","sn":"152920827549013452","verification":"136418565548517","statu":1,"useDay":"2018-05-27","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":12,"userId":71,"list":null,"allProductCoupon":false}]}],"sign":true}]
     * pageSize : 10
     * pageNum : 1
     */

    private String msg;
    private int result;
    private int pageCount;
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

    public static class DataBean implements MultiItemEntity {
        /**
         * id : 313
         * sn : 152921917151820884
         * price : 4400.0
         * pCount : 1
         * statu : 1
         * createTime : 2018-06-17
         * allProductCoupon : false
         * bookedBy : 2018-06-17 00:00:00
         * tel : 13540157043
         * expireTime : 2018-06-18 15:06:13
         * signType : 1
         * list : [{"name":"小书童自营","id":2,"price":4400,"sn":"152921917151820709","statu":1,"list":[{"orderProductId":434,"orderId":313,"proId":421,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962189085","proType":49,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-17","sn":"152921917161124709","verification":"297348427878831","statu":1,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":13,"userId":71,"list":null,"allProductCoupon":false}]}]
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
        private List<ListBeanX> list;

        public boolean isRecyType() {
            return recyType;
        }

        public void setRecyType(boolean recyType) {
            this.recyType = recyType;
        }

        private boolean recyType;


        @Override
        public int getItemType() {
            if (isRecyType()) {
                return Constans.TYPE_ORDER_ALL;
            } else {
                return Constans.TYPE_ORDER;
            }
        }


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

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }


        public static class ListBeanX {
            /**
             * name : 小书童自营
             * id : 2
             * price : 4400.0
             * sn : 152921917151820709
             * statu : 1
             * list : [{"orderProductId":434,"orderId":313,"proId":421,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962189085","proType":49,"typeName":"套餐1","sellerId":2,"sellerName":"小书童自营","createTime":"2018-06-17","sn":"152921917161124709","verification":"297348427878831","statu":1,"useDay":null,"flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":true,"couponId":13,"userId":71,"list":null,"allProductCoupon":false}]
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
                 * orderProductId : 434
                 * orderId : 313
                 * proId : 421
                 * proName : 灵隐飞来峰
                 * proCount : 1
                 * price : 4500.0
                 * proPic : http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962189085
                 * proType : 49
                 * typeName : 套餐1
                 * sellerId : 2
                 * sellerName : 小书童自营
                 * createTime : 2018-06-17
                 * sn : 152921917161124709
                 * verification : 297348427878831
                 * statu : 1
                 * useDay : null
                 * flashUse : false
                 * periodOfTime : null
                 * excursion : 1成人+2儿童
                 * useCoupon : true
                 * couponId : 13
                 * userId : 71
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
