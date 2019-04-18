package com.wuye.weixiu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.wuye.R;
import com.wuye.api.bean.BaoXiuBean;
import com.wuye.api.net.BaseModel;
import com.wuye.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description:我的维修列表
 */

public class WeiXiuFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private DelegateAdapter vAdapter;
    private WeiXiuAdapter weiXiuAdapter;
    private int weixiuType = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        weixiuType = arguments.getInt("weixiu_type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_weixiu, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_content_all_list_recyclerview);
        VirtualLayoutManager vLayoutManager = new VirtualLayoutManager(getActivity());
        vAdapter = new DelegateAdapter(vLayoutManager);
        recyclerView.setLayoutManager(vLayoutManager);
        recyclerView.setAdapter(vAdapter);
        weiXiuAdapter = new WeiXiuAdapter(getActivity());
        vAdapter.addAdapter(weiXiuAdapter);
    }


    private void initData() {
        new BaseModel()
                .baoxiuList(getActivity(), weixiuType)
                .subscribe(new Observer<List<BaoXiuBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<BaoXiuBean> list) {
                        if (list != null && list.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            weiXiuAdapter.setListData(list);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.toast("请求失败，请检查网络");
                        recyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
