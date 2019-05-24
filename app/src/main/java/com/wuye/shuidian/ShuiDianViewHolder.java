package com.wuye.shuidian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wuye.R;
import com.wuye.api.bean.ShuiDianBean;
import com.wuye.api.net.BaseModel;
import com.wuye.api.net.ServerException;
import com.wuye.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class ShuiDianViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle;
    private TextView tv_time;
    private TextView tvPay;

    private ShuiDianBean mItem;
    private Context mContext;
    private ShuiDianAdapter shuiDianAdapter;

    public ShuiDianViewHolder(View view, Context context, ShuiDianAdapter shuiDianAdapter) {
        super(view);
        mContext = context;
        this.shuiDianAdapter = shuiDianAdapter;
        initView(itemView);
    }


    private void initView(View itemView) {
        tvTitle = (TextView) this.itemView.findViewById(R.id.tv_title);
        tv_time = (TextView) this.itemView.findViewById(R.id.tv_time);
        tvPay = (TextView) this.itemView.findViewById(R.id.go_pay);

        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit("请您支付" + mItem.shuidian + "元", 1);
            }
        });
    }

    public void bindItemData(ShuiDianBean item) {
        if (item == null) {
            return;
        }
        mItem = item;
        tvTitle.setText(item.shuidian + "元");
        tv_time.setText(item.createTime);
        if (item.type == 0) {
            tvPay.setVisibility(View.VISIBLE);
        } else {
            tvPay.setVisibility(View.GONE);
        }
    }


    private void alert_edit(String msg, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setTitle(msg)
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        changeBaoXiuType();
                    }
                }).setNegativeButton("取消", null);
        builder.show();
    }

    private void changeBaoXiuType() {
        new BaseModel().shuidianChange(mContext, mItem.id)
                .subscribe(new Observer<ShuiDianBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ShuiDianBean bean) {
                        if (bean != null && bean.type == 1) {
                            ToastUtils.toast("缴费成功");
                            mItem.type = 1;
                            shuiDianAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.toast("操作失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof ServerException) {
                            ServerException se = (ServerException) e;
                            ToastUtils.toast(se.getMessage());
                        } else {
                            ToastUtils.toast("操作失败");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
