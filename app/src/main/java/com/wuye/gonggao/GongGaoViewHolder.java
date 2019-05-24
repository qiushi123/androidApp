package com.wuye.gonggao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wuye.R;
import com.wuye.api.bean.GongGaoBean;


public class GongGaoViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle;
    private TextView tv_time;


    private Context mContext;

    public GongGaoViewHolder(View view, Context context) {
        super(view);
        mContext = context;
        initView(itemView);
    }


    private void initView(View itemView) {
        tvTitle = (TextView) this.itemView.findViewById(R.id.tv_title);
        tv_time = (TextView) this.itemView.findViewById(R.id.tv_time);
    }

    public void bindItemData(GongGaoBean item) {
        if (item == null) {
            return;
        }
        tvTitle.setText(item.content);
        tv_time.setText(item.createTime);
    }


}
