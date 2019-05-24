package com.wuye.gonggao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.wuye.R;
import com.wuye.api.bean.GongGaoBean;
import com.wuye.api.net.BaseModel;
import com.wuye.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GongGaoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DelegateAdapter vAdapter;
    private GongGaoAdapter weiXiuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_gao);
        initView();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_content_all_list_recyclerview);
        VirtualLayoutManager vLayoutManager = new VirtualLayoutManager(this);
        vAdapter = new DelegateAdapter(vLayoutManager);
        recyclerView.setLayoutManager(vLayoutManager);
        recyclerView.setAdapter(vAdapter);
        weiXiuAdapter = new GongGaoAdapter(this);
        vAdapter.addAdapter(weiXiuAdapter);
    }


    private void initData() {
        new BaseModel()
                .gonggaoList(this)
                .subscribe(new Observer<List<GongGaoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GongGaoBean> list) {

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
