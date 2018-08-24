package com.padacn.xmgoing.adapter.homethree;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.model.HomeThreeBean;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.presenter.Eventil;
import com.padacn.xmgoing.presenter.HomeThreeSelectPresenter;
import com.padacn.xmgoing.presenter.SecondEventil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 46009 on 2018/4/27.
 */

public class HomeThreeFilterItemAdapter extends BaseQuickAdapter<HomeThreeBean.ConditionsBean.ListBean, BaseViewHolder> {

    private List<String> selectedlist;
    private String currTypeKey;


    public HomeThreeFilterItemAdapter(int layoutResId, @Nullable List<HomeThreeBean.ConditionsBean.ListBean> data) {
        super(layoutResId, data);
        selectedlist = HomeThreeSelectPresenter.getSingleTon().getSelectList();
    }


    public void setCurrTypeKey(String currTypeKey) {
        this.currTypeKey = currTypeKey;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeThreeBean.ConditionsBean.ListBean item) {
        helper.setOnClickListener(R.id.home_three_filter_item_text, new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        helper.setText(R.id.home_three_filter_item_text, item.getName());
        final TextView textView = helper.getView(R.id.home_three_filter_item_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.ischeck()) {
                    textView.setBackgroundResource(R.drawable.shape_screening_false);
                    item.setIscheck(false);
                } else {
                    textView.setBackgroundResource(R.drawable.shape_screening_true);
                    EventBus.getDefault().post(new Eventil(item.getName(), String.valueOf(item.getId())));
                    Log.e(TAG, "onClick: " + currTypeKey + String.valueOf(item.getId()));

//                    EventBus.getDefault().post(new SecondEventil(item.getName(), String.valueOf(item.getId())));
                    item.setIscheck(true);
                }
                HomeThreeSelectPresenter.getSingleTon().saveData(currTypeKey, String.valueOf(item.getId()));
            }
        });
//        helper.setBackgroundColor(R.id.home_three_filter_item_text, R.drawable.shape_home_three_filter_button);
    }

    private OnClickListener onClickListener;

    public void setOnClickListeners(OnClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    public interface OnClickListener {
        void itemClick(int position);
    }
}
