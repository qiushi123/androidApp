package com.wuye.shuidian;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.wuye.R;
import com.wuye.api.bean.ShuiDianBean;

import java.util.List;

/*
 * 搜索结果：知识和辅食公用一个adapter
 * */
public class ShuiDianAdapter extends DelegateAdapter.Adapter<ShuiDianViewHolder> {
    private Context context;
    private List<ShuiDianBean> contents;

    public ShuiDianAdapter(Activity activity) {
        this.context = activity;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return getLayoutHelper();
    }

    @Override
    public ShuiDianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shuidian, parent,
                false);
        return new ShuiDianViewHolder(view, context,this);
    }

    @Override
    public void onBindViewHolder(ShuiDianViewHolder holder, int position) {
        ShuiDianBean currentContent = contents.get(position);
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

    public void setListData(List<ShuiDianBean> data) {
        this.contents = data;
        notifyDataSetChanged();
    }


}
