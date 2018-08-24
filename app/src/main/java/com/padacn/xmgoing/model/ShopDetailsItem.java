package com.padacn.xmgoing.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by 46009 on 2018/5/10.
 */

public class ShopDetailsItem implements MultiItemEntity {
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    private int itemType;

    public ShopDetailsItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
