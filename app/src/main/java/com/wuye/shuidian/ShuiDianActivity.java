package com.wuye.shuidian;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.wuye.R;
import com.wuye.api.bean.ShuiDianBean;
import com.wuye.api.bean.UserBean;
import com.wuye.api.net.BaseModel;
import com.wuye.utils.SpUtils;
import com.wuye.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShuiDianActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DelegateAdapter vAdapter;
    private ShuiDianAdapter weiXiuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shui_dian);
        initView();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_content_all_list_recyclerview);
        VirtualLayoutManager vLayoutManager = new VirtualLayoutManager(this);
        vAdapter = new DelegateAdapter(vLayoutManager);
        recyclerView.setLayoutManager(vLayoutManager);
        recyclerView.setAdapter(vAdapter);
        weiXiuAdapter = new ShuiDianAdapter(this);
        vAdapter.addAdapter(weiXiuAdapter);
    }


    private void initData() {
        UserBean userInfo = SpUtils.getUserInfo(this);
        new BaseModel()
                .shuidianList(this,userInfo.userPhone)
                .subscribe(new Observer<List<ShuiDianBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ShuiDianBean> list) {

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
