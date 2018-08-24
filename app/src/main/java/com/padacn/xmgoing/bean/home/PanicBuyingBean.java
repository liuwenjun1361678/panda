package com.padacn.xmgoing.bean.home;

import com.padacn.xmgoing.model.HomeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/10 0010.
 *
 *
 */

public class PanicBuyingBean {

    /**
     * panicBuyingId : 1
     * startTime : 2018-05-18 15:43:21
     * endTime : 1526629401000
     * isshow : true
     * city : 322
     * cityName : 成都
     * logins : [{"proId":478,"panicId":1,"proName":"美国原版音乐舞台剧《爱探险的朵拉-玩具迷城》","proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526524814324","oldPrice":6500,"newPrice":5000},{"proId":495,"panicId":1,"proName":"美国原版音乐舞台剧《爱探险的朵拉-玩具迷城》","proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526524814324","oldPrice":6500,"newPrice":4500},{"proId":560,"panicId":1,"proName":"美国原版音乐舞台剧《爱探险的朵拉-玩具迷城》","proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526524814324","oldPrice":6500,"newPrice":4321},{"proId":645,"panicId":1,"proName":"美国原版音乐舞台剧《爱探险的朵拉-玩具迷城》","proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526524814324","oldPrice":6500,"newPrice":3600},{"proId":495,"panicId":1,"proName":"美国原版音乐舞台剧《爱探险的朵拉-玩具迷城》","proPic":"http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526524814324","oldPrice":6500,"newPrice":4821}]
     */

    private int panicBuyingId;
    private String startTime;
    private long endTime;
    private boolean isshow;
    private int city;
    private String cityName;
    private List<HomeBean.DataBean.PanicBuyingBean.LoginsBean> logins;

    public int getPanicBuyingId() {
        return panicBuyingId;
    }

    public void setPanicBuyingId(int panicBuyingId) {
        this.panicBuyingId = panicBuyingId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isIsshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;
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

    public List<HomeBean.DataBean.PanicBuyingBean.LoginsBean> getLogins() {
        return logins;
    }

    public void setLogins(List<HomeBean.DataBean.PanicBuyingBean.LoginsBean> logins) {
        this.logins = logins;
    }

    public static class LoginsBean {
        /**
         * proId : 478
         * panicId : 1
         * proName : 美国原版音乐舞台剧《爱探险的朵拉-玩具迷城》
         * proPic : http://csx-test.oss-cn-shenzhen.aliyuncs.com/1526524814324
         * oldPrice : 6500
         * newPrice : 5000
         */

        private int proId;
        private int panicId;
        private String proName;
        private String proPic;
        private int oldPrice;
        private int newPrice;

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getPanicId() {
            return panicId;
        }

        public void setPanicId(int panicId) {
            this.panicId = panicId;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getProPic() {
            return proPic;
        }

        public void setProPic(String proPic) {
            this.proPic = proPic;
        }

        public int getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(int oldPrice) {
            this.oldPrice = oldPrice;
        }

        public int getNewPrice() {
            return newPrice;
        }

        public void setNewPrice(int newPrice) {
            this.newPrice = newPrice;
        }
    }
}
