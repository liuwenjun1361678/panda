package com.padacn.xmgoing.presenter;

import android.util.Log;

import com.padacn.xmgoing.model.Childrenbean;
import com.padacn.xmgoing.model.ContactListBean;
import com.padacn.xmgoing.model.GoodSureBean;
import com.padacn.xmgoing.model.GoodsDtailsBean;
import com.padacn.xmgoing.model.HomeThreeBean;
import com.padacn.xmgoing.model.SureGoodsBean;
import com.padacn.xmgoing.model.UserDataBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class HomeThreeSelectPresenter {


    private static final String TAG = "HomeThreeSelectPresente";

    //用户选择联系人的list
    private List<ContactListBean> dataContactList;
    //商品聯繫人總共數量
    private int size;

    private List<SureGoodsBean.GoodsBean.ContactIdsBean> contactIdsBeanList;
    //筛选框的数据
    private List<HomeThreeBean.ConditionsBean.ListBean> conditionsBeanList;

    private List<GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean> calendersBeanList;

    private Map<String, List<HomeThreeBean.ConditionsBean.ListBean>> map;

    private List<GoodsDtailsBean.DataBean.TicketTypesBean> ticketTypesBeanList;


    private List<GoodSureBean> goodSureBeanList;

    private List<SureGoodsBean> sureGoodsBeanList;

    //用户孩子
    private List<Childrenbean> childBeanList;

    private static HomeThreeSelectPresenter singleTon = null;

    //價格選中
    private List<String> priceRangesList;
    //年齡
    private List<String> productAgesList;
    //時間
    private List<String> productDurationsList;
    //類型
    private List<String> lableList;


    public static HomeThreeSelectPresenter getSingleTon() {
        if (singleTon == null) {
            singleTon = new HomeThreeSelectPresenter();
        }
        return singleTon;
    }

    //用來记录被选中的出行人
    private List<String> IdList;

    private List<String> selectList;
    //用来记录筛选框中被选中的标签

    private HomeThreeSelectPresenter() {
        childBeanList = new ArrayList<>();
        dataContactList = new ArrayList<>();
        contactIdsBeanList = new ArrayList<>();
        goodSureBeanList = new ArrayList<>();
        sureGoodsBeanList = new ArrayList<>();
        ticketTypesBeanList = new ArrayList<>();
        calendersBeanList = new ArrayList<>();
        priceRangesList = new ArrayList<>();
        productAgesList = new ArrayList<>();
        productDurationsList = new ArrayList<>();
        lableList = new ArrayList<>();
        conditionsBeanList = new ArrayList<>();
        map = new HashMap<>();
        selectList = new ArrayList<>();
        IdList = new ArrayList<>();
    }


    public void clearSelect() {
        priceRangesList.clear();
        productAgesList.clear();
        productDurationsList.clear();
        lableList.clear();
    }

    /**
     * 用来记录被选中的item
     */

    public void saveSelect(String s) {
        if (selectList.size() <= 0) {
            selectList.clear();
            selectList.add(s);
        } else {
            if (!selectList.contains(s)) {
                selectList.add(s);
            } else {
                selectList.remove(s);
            }
        }
    }


    public String[] getPrice() {
        String[] strings = new String[priceRangesList.size()];
        priceRangesList.toArray(strings);
        return strings;
    }

    public String[] getAge() {
        String[] strings = new String[productAgesList.size()];
        priceRangesList.toArray(strings);
        return strings;
    }

    public String[] getDurations() {
        String[] strings = new String[productDurationsList.size()];
        priceRangesList.toArray(strings);
        return strings;
    }

    public String[] getLable() {
        String[] strings = new String[lableList.size()];
        priceRangesList.toArray(strings);
        return strings;
    }

    public void saveData(String currTypeKey, String currTypeId) {
        switch (currTypeKey) {
            case "priceRanges":
                saveListData(priceRangesList, currTypeId);
                break;
            case "productAges":
                saveListData(productAgesList, currTypeId);
                break;
            case "productDurations":
                saveListData(productDurationsList, currTypeId);
                break;
            case "lable":
                saveListData(lableList, currTypeId);
                break;
        }
    }

    /**
     * 用来记录首頁三級筛选框中的数据
     */

    public void saveListData(List<String> ListString, String currTypeId) {
        if (ListString.size() <= 0) {
            ListString.clear();
            ListString.add(currTypeId);
        } else {
            if (!ListString.contains(currTypeId)) {
                ListString.add(currTypeId);
            } else {
                ListString.remove(currTypeId);
            }
        }
    }

    //保存商品信息到确认页面
    public void saveSureGoodBeanList(List<SureGoodsBean> sureGoodsBeanList) {
        this.sureGoodsBeanList.clear();
        this.sureGoodsBeanList.addAll(sureGoodsBeanList);
    }

    public List<SureGoodsBean> getSureGoodsBeanList() {
        return sureGoodsBeanList;
    }


    public void removeContactList() {
        this.dataContactList.clear();
    }

    //保存用户选择出行人信息到确认页面
    public void saveContactList(ContactListBean dataContact, int currPosition) {
        if (dataContact.getPid() != null) {
            for (int i = 0; i < dataContactList.size(); i++) {
                if (currPosition == i) {
                    dataContactList.set(i, dataContact);
                }
            }
        }
    }

    //修改用户选中的联系人信息
    public void upDateContactList(int parent, int children) {
        dataContactList.get(parent).getContactIds().remove(children);
    }


    public List<Childrenbean> getChildBeanList() {
        return childBeanList;
    }

    public void saveChildBeanList(List<Childrenbean> childBeanList) {
        this.childBeanList.clear();
        this.childBeanList = childBeanList;
    }

    public List<ContactListBean> getContactList() {
        return dataContactList;
    }

    public void saveOneContactList(List<SureGoodsBean.GoodsBean.ContactIdsBean> contactIdsBeanList) {

        this.contactIdsBeanList.clear();
        this.contactIdsBeanList = contactIdsBeanList;
    }

    public List<SureGoodsBean.GoodsBean.ContactIdsBean> getOneContcactList() {
        return contactIdsBeanList;
    }

    //保存商品信息到确认页面
    public void saveGoodSureBeanList(List<GoodSureBean> goodSureBeanList) {
        this.goodSureBeanList.clear();
        this.goodSureBeanList.addAll(goodSureBeanList);
    }

    public List<GoodSureBean> getGoodSureBeanList() {
        return goodSureBeanList;
    }


    //保存详情跳转到套餐类型
    public void saveticketTypes(List<GoodsDtailsBean.DataBean.TicketTypesBean> ticketTypesBeanList) {
        this.ticketTypesBeanList.clear();
        this.ticketTypesBeanList.addAll(ticketTypesBeanList);
    }

    public List<GoodsDtailsBean.DataBean.TicketTypesBean> getticketTypes() {
        return this.ticketTypesBeanList;
    }


    //套餐的日期信息
    public void saveSetMealData(List<GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean> calendersBeanList) {
        this.calendersBeanList.clear();
        this.calendersBeanList.addAll(calendersBeanList);
    }

    public List<GoodsDtailsBean.DataBean.TicketTypesBean.CalendersBean> getSetMealData() {
        return calendersBeanList;
    }

    /**
     * 用来记录首頁三級筛选框中的数据
     */
    /*public void saveData(HomeThreeBean.ConditionsBean.ListBean conditionsBeanData, String currTypeKey) {
        if (conditionsBeanList.size() <= 0) {
            conditionsBeanList.clear();
            conditionsBeanList.add(conditionsBeanData);
        } else {
            if (!conditionsBeanList.contains(conditionsBeanData)) {
                conditionsBeanList.add(conditionsBeanData);

            } else {
                conditionsBeanList.remove(conditionsBeanData);
            }
            map.put(currTypeKey, conditionsBeanList);
        }

    }*/
    //获取价格
    public List<String> getPriceRangesList() {
        return priceRangesList;
    }

    //获取类型
    public List<String> getLableList() {
        return lableList;
    }

    //获取年龄
    public List<String> getProductAgesList() {
        return productAgesList;
    }

    //获取日期
    public List<String> getProductDurationsList() {
        return productDurationsList;
    }

    public List<HomeThreeBean.ConditionsBean.ListBean> getData() {
        return conditionsBeanList;
    }


    public List<String> getSelectList() {
        return selectList;
    }


    public List<String> getIdList() {
        return IdList;
    }

    public void InitContact(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            ContactListBean contactListBean = new ContactListBean();
            List<ContactListBean.ContactIdsBean> contactIdsBeanList = new ArrayList<>();
            contactIdsBeanList.add(new ContactListBean.ContactIdsBean());
            contactListBean.setContactIds(contactIdsBeanList);
            dataContactList.add(contactListBean);
        }
    }
}
