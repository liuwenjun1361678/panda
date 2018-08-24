package com.padacn.xmgoing.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.ShopCarAdapter;
import com.padacn.xmgoing.callback.OnClickAddCloseListenter;
import com.padacn.xmgoing.callback.OnClickDeleteListenter;
import com.padacn.xmgoing.callback.OnClickListenterModel;
import com.padacn.xmgoing.callback.OnViewItemClickListener;
import com.padacn.xmgoing.model.CartInfo;
import com.padacn.xmgoing.model.DataList;
import com.padacn.xmgoing.view.CustomToolBar;
import com.padacn.xmgoing.widget.NestedExpandaleListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 46009 on 2018/4/26.
 */

public class ShopCarFragment extends BaseFragment {
    private static final String TAG = "ShopCarFragment";

    @BindView(R.id.cart_expandablelistview)
    NestedExpandaleListView cartExpandablelistview;
    @BindView(R.id.cart_num)
    TextView cartNum;
    @BindView(R.id.cart_money)
    TextView cartMoney;
    @BindView(R.id.cart_shopp_moular)
    Button cartShoppMoular;
//    @BindView(R.id.empty_view)
//    LinearLayout emptyView;

    Unbinder unbinder;
    CartInfo cartInfo;
    ShopCarAdapter shopCarAdapter;
    double price;
    int num;

    public static ShopCarFragment newInstance() {
        Bundle args = new Bundle();
        ShopCarFragment fragment = new ShopCarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_shopcar;
    }

    @Override
    protected void initView() {
        cartExpandablelistview.setGroupIndicator(null);
        showData();
        Log.d(TAG, "initView: 111");
    }

    private void showData() {
        Gson gson = new Gson();
        cartInfo = null;
        cartInfo = gson.fromJson(DataList.JSONDATA(), CartInfo.class);
        if (cartInfo != null && cartInfo.getData().size() > 0) {
            shopCarAdapter = null;
            showExpandData();
        } else {
            try {
                shopCarAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                return;
            }
        }
    }


    private void showExpandData() {
        shopCarAdapter = new ShopCarAdapter(getContext(), cartExpandablelistview, cartInfo.getData());
//        cartExpandablelistview.setEmptyView(emptyView);
        cartExpandablelistview.setAdapter(shopCarAdapter);
        int intgroupCount = cartExpandablelistview.getCount();
        for (int i = 0; i < intgroupCount; i++) {
            cartExpandablelistview.expandGroup(i);
        }

        /**
         * 全选
         */
        shopCarAdapter.setOnItemClickListener(new OnViewItemClickListener() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                cartInfo.getData().get(position).setIscheck(isFlang);
                int length = cartInfo.getData().get(position).getItems().size();
                for (int i = 0; i < length; i++) {
                    cartInfo.getData().get(position).getItems().get(i).setIscheck(isFlang);
                }
                shopCarAdapter.notifyDataSetChanged();
                showCommodityCalculation();
            }
        });


        /**
         * 单选
         */
        shopCarAdapter.setOnClickListenterModel(new OnClickListenterModel() {
            @Override
            public void onItemClick(boolean isFlang, View view, int onePosition, int position) {
                cartInfo.getData().get(onePosition).getItems().get(position).setIscheck(isFlang);
                int length = cartInfo.getData().get(onePosition).getItems().size();
                for (int i = 0; i < length; i++) {
                    if (!cartInfo.getData().get(onePosition).getItems().get(i).ischeck()) {
                        if (!isFlang) {
                            cartInfo.getData().get(onePosition).setIscheck(isFlang);
                        }
                        shopCarAdapter.notifyDataSetChanged();
                        showCommodityCalculation();
                        return;
                    } else {
                        if (i == (length - 1)) {
                            cartInfo.getData().get(onePosition).setIscheck(isFlang);
                            shopCarAdapter.notifyDataSetChanged();
                        }
                    }
                }
                showCommodityCalculation();
            }
        });
        shopCarAdapter.setOnClickDeleteListenter(new OnClickDeleteListenter() {
            @Override
            public void onItemClick(View view, int onePosition, int position) {

                //具体代码没写， 只要删除商品，刷新数据即可
                Toast.makeText(getContext(), "删除操作", Toast.LENGTH_LONG).show();
            }
        });

        /***
         * 数量增加和减少
         */
        shopCarAdapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
            @Override
            public void onItemClick(View view, int index, int onePosition, int position, int num) {
                if (index == 1) {
                    if (num > 1) {
                        cartInfo.getData().get(onePosition).getItems().get(position).setNum((num - 1));
                        shopCarAdapter.notifyDataSetChanged();
                    }
                } else {
                    cartInfo.getData().get(onePosition).getItems().get(position).setNum((num + 1));
                    shopCarAdapter.notifyDataSetChanged();
                }
                showCommodityCalculation();
            }
        });
        showCommodityCalculation();


    }


    private void showCommodityCalculation() {
        price = 0;
        num = 0;
        for (int i = 0; i < cartInfo.getData().size(); i++) {
            for (int j = 0; j < cartInfo.getData().get(i).getItems().size(); j++) {
                if (cartInfo.getData().get(i).getItems().get(j).ischeck()) {
                    price += Double.valueOf((cartInfo.getData().get(i).getItems().get(j).getNum() * Double.valueOf(cartInfo.getData().get(i).getItems().get(j).getPrice())));
                    num++;
                }
            }
        }
        if (price == 0.0) {
            cartNum.setText("共0件商品");
            cartMoney.setText("¥ 0.0");
            return;
        }
        try {
            String money = String.valueOf(price);
            cartNum.setText("共" + num + "件商品");
            if (money.substring(money.indexOf("."), money.length()).length() > 2) {
                cartMoney.setText("¥ " + money.substring(0, (money.indexOf(".") + 3)));
                return;
            }
            cartMoney.setText("¥ " + money.substring(0, (money.indexOf(".") + 2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
