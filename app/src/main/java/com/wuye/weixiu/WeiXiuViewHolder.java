package com.wuye.weixiu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wuye.R;
import com.wuye.api.bean.BaoXiuBean;
import com.wuye.api.net.BaseModel;
import com.wuye.api.net.ServerException;
import com.wuye.api.params.BaoXiuReqParams;
import com.wuye.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class WeiXiuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvTitle;
    private TextView tvAddress;
    private TextView tvType;
    private TextView tvPrice;
    private TextView tvPay;
    private TextView tvComment;
    private View pingjiaRoot;
    private TextView tvPingjia;

    private Context mContext;
    private BaoXiuBean mItem;
    private WeiXiuAdapter weiXiuAdapter;

    public WeiXiuViewHolder(View view, Context context, WeiXiuAdapter adapter) {
        super(view);
        mContext = context;
        weiXiuAdapter = adapter;
        initView(itemView);
        initListener();
    }


    private void initListener() {
        tvPay.setOnClickListener(this);
        tvComment.setOnClickListener(this);
    }

    private void initView(View itemView) {
        tvTitle = (TextView) this.itemView.findViewById(R.id.tv_title);
        tvAddress = (TextView) this.itemView.findViewById(R.id.tv_address);
        tvType = (TextView) this.itemView.findViewById(R.id.tv_type);
        tvPrice = (TextView) this.itemView.findViewById(R.id.tv_price);
        tvPay = (TextView) this.itemView.findViewById(R.id.go_pay);
        tvComment = (TextView) this.itemView.findViewById(R.id.go_comment);

        pingjiaRoot = this.itemView.findViewById(R.id.pingjia_root);
        tvPingjia = (TextView) this.itemView.findViewById(R.id.tv_pingjia);
    }

    public void bindItemData(BaoXiuBean item) {
        mItem = item;
        if (mItem == null) {
            return;
        }

        if (!TextUtils.isEmpty(mItem.content)) {
            tvTitle.setText(Html.fromHtml(mItem.content));
        }
        if (!TextUtils.isEmpty(mItem.address)) {
            tvAddress.setText(Html.fromHtml(mItem.address));
        }

        tvPay.setVisibility(View.GONE);
        tvComment.setVisibility(View.GONE);
        pingjiaRoot.setVisibility(View.GONE);
        tvPrice.setText(mItem.price + "元");
        //0待维修，1已接单，2已处理待支付，3已支付待评价，4已完成
        if (mItem.baoxiuType == 0) {
            tvType.setText("待接单");
            tvType.setTextColor(mContext.getResources().getColor(R.color.base_FF2266));
            tvPrice.setText("待定价");
        } else if (mItem.baoxiuType == 1) {
            tvType.setText("已接单待上门维修");
            tvType.setTextColor(mContext.getResources().getColor(R.color.base_FF7C19));
        } else if (mItem.baoxiuType == 2) {
            tvType.setText("已维修待支付");
            tvType.setTextColor(mContext.getResources().getColor(R.color.base_blueGreen));
            tvPay.setVisibility(View.VISIBLE);
            tvComment.setVisibility(View.GONE);
        } else if (mItem.baoxiuType == 3) {
            tvType.setText("已支付待评价");
            tvType.setTextColor(mContext.getResources().getColor(R.color.base_38adff));
            tvPay.setVisibility(View.GONE);
            tvComment.setVisibility(View.VISIBLE);
        } else if (mItem.baoxiuType == 4) {
            tvType.setText("已完成");
            tvType.setTextColor(mContext.getResources().getColor(R.color.base_black66_222));
            pingjiaRoot.setVisibility(View.VISIBLE);
            tvPingjia.setText(mItem.comment);
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.go_pay) {
            alert_edit("请您支付" + mItem.price + "元", 1);
        } else if (id == R.id.go_comment) {
            alert_edit("请填写您的评价", 2);
        }
    }

    private void alert_edit(String msg, final int type) {
        final EditText et = new EditText(mContext);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setTitle(msg)
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (type == 2) {
                            String comment = et.getText().toString();
                            changeBaoXiuType(type, comment);
                        } else {
                            changeBaoXiuType(type, "");
                        }
                    }
                }).setNegativeButton("取消", null);
        if (type == 2) {
            builder.setView(et);
        }
        builder.show();
    }

    private void changeBaoXiuType(final Integer type, final String comment) {
        //0待维修，1已接单，2已处理待支付，3已支付待评价，4已完成
        if (type == 2 && TextUtils.isEmpty(comment)) {
            ToastUtils.toast("评论内容不能为空");
            return;
        }
        BaoXiuReqParams params = new BaoXiuReqParams();
        params.baoxiuId = mItem.baoxiuId;
        params.baoxiuType = type == 2 ? 4 : 3;
        params.comment = comment;

        new BaseModel().change(mContext, params)
                .subscribe(new Observer<BaoXiuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaoXiuBean bean) {
                        if (bean != null) {
                            if (bean.baoxiuType == 4) {
                                mItem.baoxiuType = 4;
                                mItem.comment = comment;
                                ToastUtils.toast("评价成功");
                            } else if (bean.baoxiuType == 3) {
                                mItem.baoxiuType = 3;
                                ToastUtils.toast("支付成功");
                            }
                            weiXiuAdapter.notifyDataSetChanged();
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
