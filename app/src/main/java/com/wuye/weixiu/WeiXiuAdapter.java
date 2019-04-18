package com.wuye.weixiu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.wuye.R;
import com.wuye.api.bean.BaoXiuBean;

import java.util.List;

/*
 * 搜索结果：知识和辅食公用一个adapter
 * */
public class WeiXiuAdapter extends DelegateAdapter.Adapter<WeiXiuViewHolder> {
    private Context context;
    private List<BaoXiuBean> contents;

    public WeiXiuAdapter(Activity activity) {
        this.context = activity;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return getLayoutHelper();
    }

    @Override
    public WeiXiuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weixiu, parent,
                false);
        return new WeiXiuViewHolder(view, context,this);
    }

    @Override
    public void onBindViewHolder(WeiXiuViewHolder holder, int position) {
        BaoXiuBean currentContent = contents.get(position);
        holder.bindItemData(currentContent);

    }

    @Override
    public int getItemCount() {
        if (contents == null) {
            return 0;
        }
        return contents.size();
    }

    public LayoutHelper getLayoutHelper() {
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        return layoutHelper;
    }

    public void setListData(List<BaoXiuBean> data) {
        this.contents = data;
        notifyDataSetChanged();
    }


}
