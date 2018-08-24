package com.padacn.xmgoing.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import com.padacn.xmgoing.R;
import com.padacn.xmgoing.adapter.MainTabListAdapter;
import com.padacn.xmgoing.model.ListActivityModel;
import com.padacn.xmgoing.util.EvenUtil;
import butterknife.BindView;


public class MainTabFragment extends BaseFragment {
    private static final String TAG = "MainTabFragment";
    public static final String ARG_PAGE = "ARG_PAGE";
    @BindView(R.id.list_activity_rl)
    RecyclerView listActivityRl;
    private int mPage;
    private String mTitle;

    private MainTabListAdapter mainTabListAdapter;

    public static MainTabFragment getInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainTabFragment sf = new MainTabFragment();
        sf.setArguments(args);
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);  //注册
        mPage = getArguments().getInt(ARG_PAGE);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.list_activity;
    }

    @Override
    protected void initView() {


        //活动横向滚动
        List<ListActivityModel> datas = new ArrayList<>();
        ListActivityModel model;
        for (int i = 0; i < 5; i++) {
            model = new ListActivityModel();
            datas.add(model);
        }
//        mainTabListAdapter = new MainTabListAdapter(R.layout.item_home_bottom, datas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        listActivityRl.setLayoutManager(layoutManager);
        listActivityRl.setAdapter(mainTabListAdapter);
    }

    // 接收函数


    @Subscribe
    public void onEventMainThread(EvenUtil event) {
        String msglog = "----onEventMainThread收到了消息：" + event.getMsg();
        Log.d(TAG, "onEventMainThread: " + msglog);
        listActivityRl.smoothScrollToPosition(0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//取消注册
    }
}