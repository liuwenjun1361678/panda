package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/4 0004.
 */

public class OrderCommentBean {

    /**
     * msg : 获取未评论商品成功
     * result : 1
     * pageCount : 2
     * data : [{"orderProductId":559,"orderId":453,"proId":963,"proName":"灵隐飞来峰1","proCount":1,"price":2344,"proPic":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/1530695218063","proType":2321,"typeName":"套餐4","sellerId":3,"sellerName":"第三方店铺","createTime":null,"sn":"153070529151635737","verification":"397752552116416","statu":5,"useDay":"2018-07-19","flashUse":false,"periodOfTime":null,"excursion":"1成人+2儿童","useCoupon":false,"couponId":null,"userId":80,"cid":null,"verify":true,"verifyDate":"2018-07-04","list":null,"product":null,"allProductCoupon":false},{"orderProductId":539,"orderId":428,"proId":427,"proName":"灵隐飞来峰","proCount":1,"price":4500,"proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526962272031","proType":73,"typeName":"套餐1","sellerId":3,"sellerName":"第三方店铺","createTime":null,"sn":"153060990840086213","verification":"202184208543091","statu":5,"useDay":null,"flashUse":true,"periodOfTime":"六月1号至9号","excursion":"1成人+2儿童","useCoupon":false,"couponId":null,"userId":80,"cid":null,"verify":true,"verifyDate":"2018-07-04","list":null,"product":null,"allProductCoupon":false}]
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

    public static class DataBean {
        /**
         * orderProductId : 559
         * orderId : 453
         * proId : 963
         * proName : 灵隐飞来峰1
         * proCount : 1
         * price : 2344
         * proPic : https://csx-test.oss-cn-shenzhen.aliyuncs.com/1530695218063
         * proType : 2321
         * typeName : 套餐4
         * sellerId : 3
         * sellerName : 第三方店铺
         * createTime : null
         * sn : 153070529151635737
         * verification : 397752552116416
         * statu : 5
         * useDay : 2018-07-19
         * flashUse : false
         * periodOfTime : null
         * excursion : 1成人+2儿童
         * useCoupon : false
         * couponId : null
         * userId : 80
         * cid : null
         * verify : true
         * verifyDate : 2018-07-04
         * list : null
         * product : null
         * allProductCoupon : false
         */

        private int orderProductId;
        private int orderId;
        private int proId;
        private String proName;
        private int proCount;
        private int price;
        private String proPic;
        private int proType;
        private String typeName;
        private int sellerId;
        private String sellerName;
        private Object createTime;
        private String sn;
        private String verification;
        private int statu;
        private String useDay;
        private boolean flashUse;
        private Object periodOfTime;
        private String excursion;
        private boolean useCoupon;
        private Object couponId;
        private int userId;
        private Object cid;
        private boolean verify;
        private String verifyDate;
        private Object list;
        private Object product;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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

        public Object getCouponId() {
            return couponId;
        }

        public void setCouponId(Object couponId) {
            this.couponId = couponId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getCid() {
            return cid;
        }

        public void setCid(Object cid) {
            this.cid = cid;
        }

        public boolean isVerify() {
            return verify;
        }

        public void setVerify(boolean verify) {
            this.verify = verify;
        }

        public String getVerifyDate() {
            return verifyDate;
        }

        public void setVerifyDate(String verifyDate) {
            this.verifyDate = verifyDate;
        }

        public Object getList() {
            return list;
        }

        public void setList(Object list) {
            this.list = list;
        }

        public Object getProduct() {
            return product;
        }

        public void setProduct(Object product) {
            this.product = product;
        }

        public boolean isAllProductCoupon() {
            return allProductCoupon;
        }

        public void setAllProductCoupon(boolean allProductCoupon) {
            this.allProductCoupon = allProductCoupon;
        }
    }
}
