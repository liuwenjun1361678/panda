package com.padacn.xmgoing.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by 46009 on 2018/5/10.
 */

public class OrderItem implements MultiItemEntity {
    public static final int ONE = 1;
    public static final int TWO = 2;
    private int itemType;

    public OrderItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
