package com.wuye.gonggao;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.wuye.R;
import com.wuye.api.bean.GongGaoBean;

import java.util.List;

/*
 * 搜索结果：知识和辅食公用一个adapter
 * */
public class GongGaoAdapter extends DelegateAdapter.Adapter<GongGaoViewHolder> {
    private Context context;
    private List<GongGaoBean> contents;

    public GongGaoAdapter(Activity activity) {
        this.context = activity;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return getLayoutHelper();
    }

    @Override
    public GongGaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gonggao, parent,
                false);
        return new GongGaoViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(GongGaoViewHolder holder, int position) {
        GongGaoBean currentContent = contents.get(position);
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

    public void setListData(List<GongGaoBean> data) {
        this.contents = data;
        notifyDataSetChanged();
    }


}
