package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/11 0011.
 */

public class BusinessDetailsBean {

    /**
     * msg : 商家信息获取成功
     * result : 1
     * pageCount : 1
     * data : {"seller":{"sellerId":52,"sellerName":"金榜题名夏令营","sellerIntro":"专注于中小学生体验式教育产品研发和接待服务；让学生在旅行中探索知识为品牌价值，秉着教育活动化，活动教育化，把教育元素镶嵌在旅行过程中的每个环节为产品设计理念。经过多年的发展，公司汇集了国内外诸多体验式教育专家、户外教育专家、营地教育专家、心理学专家等。","sellerPic":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531292000447","backPic":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531292000666","sellerAuthen":true,"creditGuarantee":true,"score":0,"tel":"15801246291"},"sellerShareUrl":"http://app.padacn.com/html/shop.html?sellerId=52","products":[{"pId":1019,"pSn":"153130289999458096","pName":"在北京就可以体验到的纯正美式艺术课堂，就在\u201c金榜题名\u201d！","pNum":3000,"saleNum":0,"price":5680,"pIntro":"","isrecommend":false,"destination":"北京","recommendSort":null,"city":"成都","place":"北京","pType":14,"typeParentId":3,"typeName":"研学旅行","useTheDay":true,"isbanner":false,"sellerId":52,"sellerName":"金榜题名夏令营","statu":1,"startDay":null,"endDay":null,"cityId":322,"dayRemarks":null,"viewCount":0,"columnId":null,"columnName":null,"useIdcard":false,"pDetail":"金榜题名，ACEP 根据美国中小学艺术课程内容进行教学设计。以艺术为主导，全方位提高学生的英文听、说、读、写能力。课堂内容包括梦想天地、绘画天地、歌曲与表演、创意写作、美国手工制作、集体舞、体育运动板块。","startPlace":"成都","travelNum":"","ageMark":2,"maxBuy":3,"proTheme":null,"themeName":null,"typeMark":0,"logins":[{"proId":1019,"labelId":2,"labelName":"人际"},{"proId":1019,"labelId":3,"labelName":"性格"},{"proId":1019,"labelId":4,"labelName":"习惯养成"}],"pics":[{"proPicId":2523,"proName":"在北京就可以体验到的纯正美式艺术课堂，就在\u201c金榜题名\u201d！","proId":1019,"path":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531300826416","picKey":"1531300826416","height":1000,"width":1500,"picCreateTime":"2018-07-11","asynIdent":null},{"proPicId":2524,"proName":"在北京就可以体验到的纯正美式艺术课堂，就在\u201c金榜题名\u201d！","proId":1019,"path":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531300826822","picKey":"1531300826822","height":1000,"width":1500,"picCreateTime":"2018-07-11","asynIdent":null}],"homeMark":2,"referencePrice":null,"refundTime":null,"tripMode":null,"saleTime":null,"ticketTypes":null,"salePrice":"5680","linePrice":"6580","subheading":null,"durationId":3,"durationName":"5-9天","priceRangeId":3,"rangeName":"5000以上","excursion":"","flashUse":null,"periodOfTime":null,"reference":false,"personData":false}]}
     * pageSize : 10
     * productCount : 1
     * pageNum : 1
     */

    private String msg;
    private int result;
    private int pageCount;
    private DataBean data;
    private int pageSize;
    private int productCount;
    private int pageNum;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public static class DataBean {
        /**
         * seller : {"sellerId":52,"sellerName":"金榜题名夏令营","sellerIntro":"专注于中小学生体验式教育产品研发和接待服务；让学生在旅行中探索知识为品牌价值，秉着教育活动化，活动教育化，把教育元素镶嵌在旅行过程中的每个环节为产品设计理念。经过多年的发展，公司汇集了国内外诸多体验式教育专家、户外教育专家、营地教育专家、心理学专家等。","sellerPic":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531292000447","backPic":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531292000666","sellerAuthen":true,"creditGuarantee":true,"score":0,"tel":"15801246291"}
         * sellerShareUrl : http://app.padacn.com/html/shop.html?sellerId=52
         * products : [{"pId":1019,"pSn":"153130289999458096","pName":"在北京就可以体验到的纯正美式艺术课堂，就在\u201c金榜题名\u201d！","pNum":3000,"saleNum":0,"price":5680,"pIntro":"","isrecommend":false,"destination":"北京","recommendSort":null,"city":"成都","place":"北京","pType":14,"typeParentId":3,"typeName":"研学旅行","useTheDay":true,"isbanner":false,"sellerId":52,"sellerName":"金榜题名夏令营","statu":1,"startDay":null,"endDay":null,"cityId":322,"dayRemarks":null,"viewCount":0,"columnId":null,"columnName":null,"useIdcard":false,"pDetail":"金榜题名，ACEP 根据美国中小学艺术课程内容进行教学设计。以艺术为主导，全方位提高学生的英文听、说、读、写能力。课堂内容包括梦想天地、绘画天地、歌曲与表演、创意写作、美国手工制作、集体舞、体育运动板块。","startPlace":"成都","travelNum":"","ageMark":2,"maxBuy":3,"proTheme":null,"themeName":null,"typeMark":0,"logins":[{"proId":1019,"labelId":2,"labelName":"人际"},{"proId":1019,"labelId":3,"labelName":"性格"},{"proId":1019,"labelId":4,"labelName":"习惯养成"}],"pics":[{"proPicId":2523,"proName":"在北京就可以体验到的纯正美式艺术课堂，就在\u201c金榜题名\u201d！","proId":1019,"path":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531300826416","picKey":"1531300826416","height":1000,"width":1500,"picCreateTime":"2018-07-11","asynIdent":null},{"proPicId":2524,"proName":"在北京就可以体验到的纯正美式艺术课堂，就在\u201c金榜题名\u201d！","proId":1019,"path":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531300826822","picKey":"1531300826822","height":1000,"width":1500,"picCreateTime":"2018-07-11","asynIdent":null}],"homeMark":2,"referencePrice":null,"refundTime":null,"tripMode":null,"saleTime":null,"ticketTypes":null,"salePrice":"5680","linePrice":"6580","subheading":null,"durationId":3,"durationName":"5-9天","priceRangeId":3,"rangeName":"5000以上","excursion":"","flashUse":null,"periodOfTime":null,"reference":false,"personData":false}]
         */

        private SellerBean seller;
        private String sellerShareUrl;
        private List<ProductsBean> products;

        public SellerBean getSeller() {
            return seller;
        }

        public void setSeller(SellerBean seller) {
            this.seller = seller;
        }

        public String getSellerShareUrl() {
            return sellerShareUrl;
        }

        public void setSellerShareUrl(String sellerShareUrl) {
            this.sellerShareUrl = sellerShareUrl;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class SellerBean {
            /**
             * sellerId : 52
             * sellerName : 金榜题名夏令营
             * sellerIntro : 专注于中小学生体验式教育产品研发和接待服务；让学生在旅行中探索知识为品牌价值，秉着教育活动化，活动教育化，把教育元素镶嵌在旅行过程中的每个环节为产品设计理念。经过多年的发展，公司汇集了国内外诸多体验式教育专家、户外教育专家、营地教育专家、心理学专家等。
             * sellerPic : https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531292000447
             * backPic : https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531292000666
             * sellerAuthen : true
             * creditGuarantee : true
             * score : 0.0
             * tel : 15801246291
             */

            private int sellerId;
            private String sellerName;
            private String sellerIntro;
            private String sellerPic;
            private String backPic;
            private boolean sellerAuthen;
            private boolean creditGuarantee;
            private double score;
            private String tel;

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

            public String getSellerIntro() {
                return sellerIntro;
            }

            public void setSellerIntro(String sellerIntro) {
                this.sellerIntro = sellerIntro;
            }

            public String getSellerPic() {
                return sellerPic;
            }

            public void setSellerPic(String sellerPic) {
                this.sellerPic = sellerPic;
            }

            public String getBackPic() {
                return backPic;
            }

            public void setBackPic(String backPic) {
                this.backPic = backPic;
            }

            public boolean isSellerAuthen() {
                return sellerAuthen;
            }

            public void setSellerAuthen(boolean sellerAuthen) {
                this.sellerAuthen = sellerAuthen;
            }

            public boolean isCreditGuarantee() {
                return creditGuarantee;
            }

            public void setCreditGuarantee(boolean creditGuarantee) {
                this.creditGuarantee = creditGuarantee;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }
        }

        public static class ProductsBean {
            /**
             * pId : 1019
             * pSn : 153130289999458096
             * pName : 在北京就可以体验到的纯正美式艺术课堂，就在“金榜题名”！
             * pNum : 3000
             * saleNum : 0
             * price : 5680.0
             * pIntro :
             * isrecommend : false
             * destination : 北京
             * recommendSort : null
             * city : 成都
             * place : 北京
             * pType : 14
             * typeParentId : 3
             * typeName : 研学旅行
             * useTheDay : true
             * isbanner : false
             * sellerId : 52
             * sellerName : 金榜题名夏令营
             * statu : 1
             * startDay : null
             * endDay : null
             * cityId : 322
             * dayRemarks : null
             * viewCount : 0
             * columnId : null
             * columnName : null
             * useIdcard : false
             * pDetail : 金榜题名，ACEP 根据美国中小学艺术课程内容进行教学设计。以艺术为主导，全方位提高学生的英文听、说、读、写能力。课堂内容包括梦想天地、绘画天地、歌曲与表演、创意写作、美国手工制作、集体舞、体育运动板块。
             * startPlace : 成都
             * travelNum :
             * ageMark : 2
             * maxBuy : 3
             * proTheme : null
             * themeName : null
             * typeMark : 0
             * logins : [{"proId":1019,"labelId":2,"labelName":"人际"},{"proId":1019,"labelId":3,"labelName":"性格"},{"proId":1019,"labelId":4,"labelName":"习惯养成"}]
             * pics : [{"proPicId":2523,"proName":"在北京就可以体验到的纯正美式艺术课堂，就在\u201c金榜题名\u201d！","proId":1019,"path":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531300826416","picKey":"1531300826416","height":1000,"width":1500,"picCreateTime":"2018-07-11","asynIdent":null},{"proPicId":2524,"proName":"在北京就可以体验到的纯正美式艺术课堂，就在\u201c金榜题名\u201d！","proId":1019,"path":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531300826822","picKey":"1531300826822","height":1000,"width":1500,"picCreateTime":"2018-07-11","asynIdent":null}]
             * homeMark : 2
             * referencePrice : null
             * refundTime : null
             * tripMode : null
             * saleTime : null
             * ticketTypes : null
             * salePrice : 5680
             * linePrice : 6580
             * subheading : null
             * durationId : 3
             * durationName : 5-9天
             * priceRangeId : 3
             * rangeName : 5000以上
             * excursion :
             * flashUse : null
             * periodOfTime : null
             * reference : false
             * personData : false
             */

            private int pId;
            private String pSn;
            private String pName;
            private int pNum;
            private int saleNum;
            private double price;
            private String pIntro;
            private boolean isrecommend;
            private String destination;
            private Object recommendSort;
            private String city;
            private String place;
            private int pType;
            private int typeParentId;
            private String typeName;
            private boolean useTheDay;
            private boolean isbanner;
            private int sellerId;
            private String sellerName;
            private int statu;
            private Object startDay;
            private Object endDay;
            private int cityId;
            private Object dayRemarks;
            private int viewCount;
            private Object columnId;
            private Object columnName;
            private boolean useIdcard;
            private String pDetail;
            private String startPlace;
            private String travelNum;
            private int ageMark;
            private int maxBuy;
            private Object proTheme;
            private Object themeName;
            private int typeMark;
            private int homeMark;
            private Object referencePrice;
            private Object refundTime;
            private Object tripMode;
            private Object saleTime;
            private Object ticketTypes;
            private String salePrice;
            private String linePrice;
            private Object subheading;
            private int durationId;
            private String durationName;
            private int priceRangeId;
            private String rangeName;
            private String excursion;
            private Object flashUse;
            private Object periodOfTime;
            private boolean reference;
            private boolean personData;
            private List<LoginsBean> logins;
            private List<PicsBean> pics;

            public int getPId() {
                return pId;
            }

            public void setPId(int pId) {
                this.pId = pId;
            }

            public String getPSn() {
                return pSn;
            }

            public void setPSn(String pSn) {
                this.pSn = pSn;
            }

            public String getPName() {
                return pName;
            }

            public void setPName(String pName) {
                this.pName = pName;
            }

            public int getPNum() {
                return pNum;
            }

            public void setPNum(int pNum) {
                this.pNum = pNum;
            }

            public int getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(int saleNum) {
                this.saleNum = saleNum;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getPIntro() {
                return pIntro;
            }

            public void setPIntro(String pIntro) {
                this.pIntro = pIntro;
            }

            public boolean isIsrecommend() {
                return isrecommend;
            }

            public void setIsrecommend(boolean isrecommend) {
                this.isrecommend = isrecommend;
            }

            public String getDestination() {
                return destination;
            }

            public void setDestination(String destination) {
                this.destination = destination;
            }

            public Object getRecommendSort() {
                return recommendSort;
            }

            public void setRecommendSort(Object recommendSort) {
                this.recommendSort = recommendSort;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public int getPType() {
                return pType;
            }

            public void setPType(int pType) {
                this.pType = pType;
            }

            public int getTypeParentId() {
                return typeParentId;
            }

            public void setTypeParentId(int typeParentId) {
                this.typeParentId = typeParentId;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public boolean isUseTheDay() {
                return useTheDay;
            }

            public void setUseTheDay(boolean useTheDay) {
                this.useTheDay = useTheDay;
            }

            public boolean isIsbanner() {
                return isbanner;
            }

            public void setIsbanner(boolean isbanner) {
                this.isbanner = isbanner;
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

            public int getStatu() {
                return statu;
            }

            public void setStatu(int statu) {
                this.statu = statu;
            }

            public Object getStartDay() {
                return startDay;
            }

            public void setStartDay(Object startDay) {
                this.startDay = startDay;
            }

            public Object getEndDay() {
                return endDay;
            }

            public void setEndDay(Object endDay) {
                this.endDay = endDay;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public Object getDayRemarks() {
                return dayRemarks;
            }

            public void setDayRemarks(Object dayRemarks) {
                this.dayRemarks = dayRemarks;
            }

            public int getViewCount() {
                return viewCount;
            }

            public void setViewCount(int viewCount) {
                this.viewCount = viewCount;
            }

            public Object getColumnId() {
                return columnId;
            }

            public void setColumnId(Object columnId) {
                this.columnId = columnId;
            }

            public Object getColumnName() {
                return columnName;
            }

            public void setColumnName(Object columnName) {
                this.columnName = columnName;
            }

            public boolean isUseIdcard() {
                return useIdcard;
            }

            public void setUseIdcard(boolean useIdcard) {
                this.useIdcard = useIdcard;
            }

            public String getPDetail() {
                return pDetail;
            }

            public void setPDetail(String pDetail) {
                this.pDetail = pDetail;
            }

            public String getStartPlace() {
                return startPlace;
            }

            public void setStartPlace(String startPlace) {
                this.startPlace = startPlace;
            }

            public String getTravelNum() {
                return travelNum;
            }

            public void setTravelNum(String travelNum) {
                this.travelNum = travelNum;
            }

            public int getAgeMark() {
                return ageMark;
            }

            public void setAgeMark(int ageMark) {
                this.ageMark = ageMark;
            }

            public int getMaxBuy() {
                return maxBuy;
            }

            public void setMaxBuy(int maxBuy) {
                this.maxBuy = maxBuy;
            }

            public Object getProTheme() {
                return proTheme;
            }

            public void setProTheme(Object proTheme) {
                this.proTheme = proTheme;
            }

            public Object getThemeName() {
                return themeName;
            }

            public void setThemeName(Object themeName) {
                this.themeName = themeName;
            }

            public int getTypeMark() {
                return typeMark;
            }

            public void setTypeMark(int typeMark) {
                this.typeMark = typeMark;
            }

            public int getHomeMark() {
                return homeMark;
            }

            public void setHomeMark(int homeMark) {
                this.homeMark = homeMark;
            }

            public Object getReferencePrice() {
                return referencePrice;
            }

            public void setReferencePrice(Object referencePrice) {
                this.referencePrice = referencePrice;
            }

            public Object getRefundTime() {
                return refundTime;
            }

            public void setRefundTime(Object refundTime) {
                this.refundTime = refundTime;
            }

            public Object getTripMode() {
                return tripMode;
            }

            public void setTripMode(Object tripMode) {
                this.tripMode = tripMode;
            }

            public Object getSaleTime() {
                return saleTime;
            }

            public void setSaleTime(Object saleTime) {
                this.saleTime = saleTime;
            }

            public Object getTicketTypes() {
                return ticketTypes;
            }

            public void setTicketTypes(Object ticketTypes) {
                this.ticketTypes = ticketTypes;
            }

            public String getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(String salePrice) {
                this.salePrice = salePrice;
            }

            public String getLinePrice() {
                return linePrice;
            }

            public void setLinePrice(String linePrice) {
                this.linePrice = linePrice;
            }

            public Object getSubheading() {
                return subheading;
            }

            public void setSubheading(Object subheading) {
                this.subheading = subheading;
            }

            public int getDurationId() {
                return durationId;
            }

            public void setDurationId(int durationId) {
                this.durationId = durationId;
            }

            public String getDurationName() {
                return durationName;
            }

            public void setDurationName(String durationName) {
                this.durationName = durationName;
            }

            public int getPriceRangeId() {
                return priceRangeId;
            }

            public void setPriceRangeId(int priceRangeId) {
                this.priceRangeId = priceRangeId;
            }

            public String getRangeName() {
                return rangeName;
            }

            public void setRangeName(String rangeName) {
                this.rangeName = rangeName;
            }

            public String getExcursion() {
                return excursion;
            }

            public void setExcursion(String excursion) {
                this.excursion = excursion;
            }

            public Object getFlashUse() {
                return flashUse;
            }

            public void setFlashUse(Object flashUse) {
                this.flashUse = flashUse;
            }

            public Object getPeriodOfTime() {
                return periodOfTime;
            }

            public void setPeriodOfTime(Object periodOfTime) {
                this.periodOfTime = periodOfTime;
            }

            public boolean isReference() {
                return reference;
            }

            public void setReference(boolean reference) {
                this.reference = reference;
            }

            public boolean isPersonData() {
                return personData;
            }

            public void setPersonData(boolean personData) {
                this.personData = personData;
            }

            public List<LoginsBean> getLogins() {
                return logins;
            }

            public void setLogins(List<LoginsBean> logins) {
                this.logins = logins;
            }

            public List<PicsBean> getPics() {
                return pics;
            }

            public void setPics(List<PicsBean> pics) {
                this.pics = pics;
            }

            public static class LoginsBean {
                /**
                 * proId : 1019
                 * labelId : 2
                 * labelName : 人际
                 */

                private int proId;
                private int labelId;
                private String labelName;

                public int getProId() {
                    return proId;
                }

                public void setProId(int proId) {
                    this.proId = proId;
                }

                public int getLabelId() {
                    return labelId;
                }

                public void setLabelId(int labelId) {
                    this.labelId = labelId;
                }

                public String getLabelName() {
                    return labelName;
                }

                public void setLabelName(String labelName) {
                    this.labelName = labelName;
                }
            }

            public static class PicsBean {
                /**
                 * proPicId : 2523
                 * proName : 在北京就可以体验到的纯正美式艺术课堂，就在“金榜题名”！
                 * proId : 1019
                 * path : https://pandagroup.oss-cn-shenzhen.aliyuncs.com/1531300826416
                 * picKey : 1531300826416
                 * height : 1000
                 * width : 1500
                 * picCreateTime : 2018-07-11
                 * asynIdent : null
                 */

                private int proPicId;
                private String proName;
                private int proId;
                private String path;
                private String picKey;
                private int height;
                private int width;
                private String picCreateTime;
                private Object asynIdent;

                public int getProPicId() {
                    return proPicId;
                }

                public void setProPicId(int proPicId) {
                    this.proPicId = proPicId;
                }

                public String getProName() {
                    return proName;
                }

                public void setProName(String proName) {
                    this.proName = proName;
                }

                public int getProId() {
                    return proId;
                }

                public void setProId(int proId) {
                    this.proId = proId;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }

                public String getPicKey() {
                    return picKey;
                }

                public void setPicKey(String picKey) {
                    this.picKey = picKey;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public String getPicCreateTime() {
                    return picCreateTime;
                }

                public void setPicCreateTime(String picCreateTime) {
                    this.picCreateTime = picCreateTime;
                }

                public Object getAsynIdent() {
                    return asynIdent;
                }

                public void setAsynIdent(Object asynIdent) {
                    this.asynIdent = asynIdent;
                }
            }
        }
    }
}
