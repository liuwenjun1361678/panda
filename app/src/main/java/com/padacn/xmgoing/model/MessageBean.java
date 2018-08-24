package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/1 0001.
 */

public class MessageBean {

    /**
     * msg : 获取消息成功
     * result : 1
     * data : [{"newsId":1,"newsTitle":"熊猫成长季1.0发布啦","newsType":"1","newsDate":"2018-06-13","newsUserType":"0","newsHeaderPic":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/822091999204234097.png","newsContent":"熊猫成长记是中国领先的亲子类服务平台，是无数妈妈们口碑相传的选择，覆盖30000个国家我地区的目的地攻略，商品，点评等用户真实分享的信息，并提供16万家酒店，包括交通、当地游、门票等上万种旅游产品和内容，家庭亲子出游神器！熊猫成长季，让出游充满爱~"},{"newsId":2,"newsTitle":"系统消息","newsType":"1","newsDate":"2018-06-14","newsUserType":"0","newsHeaderPic":"https://csx-test.oss-cn-shenzhen.aliyuncs.com/822091999204234097.png","newsContent":"欢迎光临熊猫成长季app"}]
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
         * newsId : 1
         * newsTitle : 熊猫成长季1.0发布啦
         * newsType : 1
         * newsDate : 2018-06-13
         * newsUserType : 0
         * newsHeaderPic : https://csx-test.oss-cn-shenzhen.aliyuncs.com/822091999204234097.png
         * newsContent : 熊猫成长记是中国领先的亲子类服务平台，是无数妈妈们口碑相传的选择，覆盖30000个国家我地区的目的地攻略，商品，点评等用户真实分享的信息，并提供16万家酒店，包括交通、当地游、门票等上万种旅游产品和内容，家庭亲子出游神器！熊猫成长季，让出游充满爱~
         */

        private int newsId;
        private String newsTitle;
        private String newsType;
        private String newsDate;
        private String newsUserType;
        private String newsHeaderPic;
        private String newsContent;

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }

        public String getNewsType() {
            return newsType;
        }

        public void setNewsType(String newsType) {
            this.newsType = newsType;
        }

        public String getNewsDate() {
            return newsDate;
        }

        public void setNewsDate(String newsDate) {
            this.newsDate = newsDate;
        }

        public String getNewsUserType() {
            return newsUserType;
        }

        public void setNewsUserType(String newsUserType) {
            this.newsUserType = newsUserType;
        }

        public String getNewsHeaderPic() {
            return newsHeaderPic;
        }

        public void setNewsHeaderPic(String newsHeaderPic) {
            this.newsHeaderPic = newsHeaderPic;
        }

        public String getNewsContent() {
            return newsContent;
        }

        public void setNewsContent(String newsContent) {
            this.newsContent = newsContent;
        }
    }
}
