package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class CommentListBean {


    /**
     * msg : 商品评论列表获取成功
     * result : 1
     * pageCount : 2
     * data : [{"reviewsId":41,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"测试新版评论","reviewDate":"2018-06-15","score":4,"sellerId":3,"sellerName":"第三方店铺","pics":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061489148,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061497124,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061504747,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061511526,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061515818,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061519329","proType":"套餐3","picPath":["https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061489148","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061497124","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061504747","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061511526","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061515818","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061519329"]},{"reviewsId":40,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"测试新版评论","reviewDate":"2018-06-15","score":4,"sellerId":3,"sellerName":"第三方店铺","pics":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061271178,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061278513,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061286964,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061293865,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061297595,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061301436","proType":"套餐3","picPath":["https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061271178","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061278513","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061286964","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061293865","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061297595","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061301436"]},{"reviewsId":39,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"牛牛牛","reviewDate":"2018-06-15","score":4,"sellerId":3,"sellerName":"第三方店铺","pics":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051997230*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051998664*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529052004018*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529052008971*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529052013244*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529052018265","proType":"套餐3","picPath":["https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051997230*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051998664*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529052004018*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529052008971*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529052013244*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529052018265"]},{"reviewsId":38,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"牛牛牛","reviewDate":"2018-06-15","score":4,"sellerId":3,"sellerName":"第三方店铺","pics":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051934066*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051935704*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051944356*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051951073*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051955217*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051961983","proType":"套餐3","picPath":["https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051934066*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051935704*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051944356*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051951073*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051955217*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051961983"]},{"reviewsId":37,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"太棒了","reviewDate":"2018-06-15","score":5,"sellerId":3,"sellerName":"第三方店铺","pics":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051575054*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051581848*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051586700*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051591689*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051595571*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051596979","proType":"套餐3","picPath":["https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051575054*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051581848*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051586700*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051591689*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051595571*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051596979"]},{"reviewsId":36,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"牛牛牛","reviewDate":"2018-06-15","score":4,"sellerId":3,"sellerName":"第三方店铺","pics":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051142019*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051147594*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051152952","proType":"套餐3","picPath":["https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051142019*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051147594*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529051152952"]},{"reviewsId":35,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"非常不错！","reviewDate":"2018-06-15","score":4,"sellerId":3,"sellerName":"第三方店铺","pics":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050757009*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050762893*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050767826*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050769316*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050770864*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050772337","proType":"套餐3","picPath":["https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050757009*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050762893*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050767826*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050769316*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050770864*>*https://csx-test.oss-cn-shenzhen.aliyuncs.com/1529050772337"]},{"reviewsId":34,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"不错的购物体验，赞赞赞","reviewDate":"2018-06-15","score":5,"sellerId":3,"sellerName":"第三方店铺","pics":"","proType":"套餐3","picPath":null},{"reviewsId":33,"proId":426,"userId":71,"userName":"夏中国人","userSculpture":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611","reviews":"不错的购物体验，赞赞赞","reviewDate":"2018-06-15","score":5,"sellerId":3,"sellerName":"第三方店铺","pics":"","proType":"套餐3","picPath":null},{"reviewsId":25,"proId":426,"userId":1,"userName":"csx","userSculpture":"noPic","reviews":"挺好的","reviewDate":"2018-06-12","score":4,"sellerId":3,"sellerName":"第三方店铺","pics":null,"proType":"","picPath":null}]
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
         * reviewsId : 41
         * proId : 426
         * userId : 71
         * userName : 夏中国人
         * userSculpture : https://csx-test.oss-cn-shenzhen.aliyuncs.com/13540157043000066611
         * reviews : 测试新版评论
         * reviewDate : 2018-06-15
         * score : 4
         * sellerId : 3
         * sellerName : 第三方店铺
         * pics : https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061489148,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061497124,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061504747,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061511526,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061515818,https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061519329
         * proType : 套餐3
         * picPath : ["https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061489148","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061497124","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061504747","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061511526","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061515818","https://csx-test.oss-cn-shenzhen.aliyuncs.com/00001529061519329"]
         */

        private int reviewsId;
        private int proId;
        private int userId;
        private String userName;
        private String userSculpture;
        private String reviews;
        private String reviewDate;
        private int score;
        private int sellerId;
        private String sellerName;
        private String pics;
        private String proType;
        private List<String> picPath;

        public int getReviewsId() {
            return reviewsId;
        }

        public void setReviewsId(int reviewsId) {
            this.reviewsId = reviewsId;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserSculpture() {
            return userSculpture;
        }

        public void setUserSculpture(String userSculpture) {
            this.userSculpture = userSculpture;
        }

        public String getReviews() {
            return reviews;
        }

        public void setReviews(String reviews) {
            this.reviews = reviews;
        }

        public String getReviewDate() {
            return reviewDate;
        }

        public void setReviewDate(String reviewDate) {
            this.reviewDate = reviewDate;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
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

        public String getPics() {
            return pics;
        }

        public void setPics(String pics) {
            this.pics = pics;
        }

        public String getProType() {
            return proType;
        }

        public void setProType(String proType) {
            this.proType = proType;
        }

        public List<String> getPicPath() {
            return picPath;
        }

        public void setPicPath(List<String> picPath) {
            this.picPath = picPath;
        }
    }
}
