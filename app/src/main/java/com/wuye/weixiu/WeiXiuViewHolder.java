package com.wuye.weixiu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuye.R;
import com.wuye.api.bean.BaoXiuBean;


public class WeiXiuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private LinearLayout llContainer;
    private TextView tvTitle;

    private Context mContext;
    private BaoXiuBean mItem;


    public WeiXiuViewHolder(View view, Context context) {
        super(view);
        mContext = context;
        initView(itemView);
        initListener();
    }


    private void initListener() {
        llContainer.setOnClickListener(this);
    }

    private void initView(View itemView) {
        llContainer = (LinearLayout) this.itemView.findViewById(R.id.ll_article_container);
        tvTitle = (TextView) this.itemView.findViewById(R.id.tv_title);
    }

    public void bindItemData(BaoXiuBean item) {
        mItem = item;
        if (mItem == null) {
            return;
        }

        if (!TextUtils.isEmpty(mItem.content)) {
            tvTitle.setText(Html.fromHtml(mItem.content));
        }
        //        if (TextUtils.isEmpty(mItem.pageviewNum) || TextUtils.isEmpty(mItem.praiseNum)) {
        //            tvLine.setVisibility(View.GONE);
        //        }
        //        if (!TextUtils.isEmpty(mItem.pageviewNum)) {
        //            tvReadNum.setText(mItem.pageviewNum + "次浏览");
        //        }
        //        if (!TextUtils.isEmpty(mItem.praiseNum)) {
        //            tvAgreeNum.setText(mItem.praiseNum + "位宝妈认为有用");
        //        }
        //        //资源类型 1图文 2音频 3视频 4音视频混合
        //        if (mItem.nodeResourceType == 2) {
        //            ivType.setVisibility(View.VISIBLE);
        //            ivType.setImageResource(R.drawable.search_know_audio);
        //        } else if (mItem.nodeResourceType == 3 || mItem.nodeResourceType == 4) {
        //            ivType.setVisibility(View.VISIBLE);
        //            ivType.setImageResource(R.drawable.search_know_video);
        //        } else {
        //            ivType.setVisibility(View.GONE);
        //        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_article_container) {
        }
    }


}
