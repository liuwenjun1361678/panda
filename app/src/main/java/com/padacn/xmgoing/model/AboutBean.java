package com.padacn.xmgoing.model;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class AboutBean {

    /**
     * msg : 成功
     * result : 1
     * data : {"about":"熊猫成长季是一款专注于青少年\u201c体验式素质教育\u201d优质资源聚合的教育信息类App，集研学旅行、社会实践、亲子活动、素质拓展、景点票务等创新型素质教育类产品为一体，为培育并提升青少年核心素养提供全品类信息和服务，探索打造中国青少年素质教育新IP。","nowEdition":"当前版本1.0","logoAdress":"https://pandagroup.oss-cn-shenzhen.aliyuncs.com/856343895552384138.jpg","marking":"Copyright © 2017-2018"}
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
         * about : 熊猫成长季是一款专注于青少年“体验式素质教育”优质资源聚合的教育信息类App，集研学旅行、社会实践、亲子活动、素质拓展、景点票务等创新型素质教育类产品为一体，为培育并提升青少年核心素养提供全品类信息和服务，探索打造中国青少年素质教育新IP。
         * nowEdition : 当前版本1.0
         * logoAdress : https://pandagroup.oss-cn-shenzhen.aliyuncs.com/856343895552384138.jpg
         * marking : Copyright © 2017-2018
         */

        private String about;
        private String nowEdition;
        private String logoAdress;
        private String marking;

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getNowEdition() {
            return nowEdition;
        }

        public void setNowEdition(String nowEdition) {
            this.nowEdition = nowEdition;
        }

        public String getLogoAdress() {
            return logoAdress;
        }

        public void setLogoAdress(String logoAdress) {
            this.logoAdress = logoAdress;
        }

        public String getMarking() {
            return marking;
        }

        public void setMarking(String marking) {
            this.marking = marking;
        }
    }
}
