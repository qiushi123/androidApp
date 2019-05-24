package com.wuye;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wuye.api.bean.BaoXiuBean;
import com.wuye.api.bean.TouSuBean;
import com.wuye.api.bean.UserBean;
import com.wuye.api.net.BaseModel;
import com.wuye.api.net.ServerException;
import com.wuye.api.params.BaoXiuReqParams;
import com.wuye.utils.SpUtils;
import com.wuye.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/*
 * 提交保修页
 * */
public class SubmitActivity extends AppCompatActivity {

    private TextView title;
    private View baoxiu_root;
    private TextView num;
    private EditText content;
    private EditText address;

    private int type;//1 修改地址，2提交维修，3投诉物业
    private UserBean userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        type = getIntent().getIntExtra("type", 0);

        userInfo = SpUtils.getUserInfo(this);

        initView();

    }

    private void initView() {
        title = findViewById(R.id.title);
        baoxiu_root = findViewById(R.id.baoxiu_root);
        num = findViewById(R.id.content_num);
        content = findViewById(R.id.content);
        address = findViewById(R.id.address);


        if (type == 3) {
            title.setText("投诉");
            baoxiu_root.setVisibility(View.VISIBLE);
            address.setVisibility(View.GONE);
        } else if (type == 2) {
            title.setText("报修");
            baoxiu_root.setVisibility(View.VISIBLE);
            address.setVisibility(View.GONE);
        } else {
            title.setText("修改地址");
            baoxiu_root.setVisibility(View.GONE);
            address.setVisibility(View.VISIBLE);
        }
        if (userInfo != null) {
            address.setText(userInfo.address);
            address.setSelection(userInfo.address.length());
        }

        content.addTextChangedListener(mTextWatcher);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 3) {
                    tousu();
                } else if (type == 2) {
                    submit();
                } else {
                    changeAddress();
                }
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void changeAddress() {
        if (userInfo == null) {
            return;
        }
        String string = address.getText().toString();
        if (TextUtils.isEmpty(string)) {
            ToastUtils.toast("地址不能为空");
            return;
        }
        if (TextUtils.equals(string, userInfo.address)) {
            ToastUtils.toast("地址没有改变");
            return;
        }

        new BaseModel().changeUserInfo(this, userInfo.userPhone, string)
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        if (userBean != null && !TextUtils.isEmpty(userBean.userPhone)) {
                            ToastUtils.toast("修改成功");
                            SpUtils.saveUserInfo(SubmitActivity.this, userBean);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof ServerException) {
                            ServerException se = (ServerException) e;
                            ToastUtils.toast(se.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //投诉
    private void tousu() {
        if (userInfo == null) {
            return;
        }
        String string = content.getText().toString();
        if (TextUtils.isEmpty(string)) {
            ToastUtils.toast("内容不能为空");
            return;
        }
        BaoXiuReqParams params = new BaoXiuReqParams();
        params.name = userInfo.userName;
        params.phone = userInfo.userPhone;
        params.content = string;
        new BaseModel().tousu(this, params)
                .subscribe(new Observer<TouSuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(TouSuBean userBean) {
                        if (userBean != null) {
                            ToastUtils.toast("提交成功");
                            finish();
                        } else {
                            ToastUtils.toast("提交失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof ServerException) {
                            ServerException se = (ServerException) e;
                            ToastUtils.toast(SubmitActivity.this, se.getMessage());
                        } else {
                            ToastUtils.toast("提交失败");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //提交
    private void submit() {
        if (userInfo == null) {
            return;
        }
        String string = content.getText().toString();
        if (TextUtils.isEmpty(string)) {
            ToastUtils.toast("报修内容不能为空");
            return;
        }
        BaoXiuReqParams params = new BaoXiuReqParams();
        params.name = userInfo.userName;
        params.phone = userInfo.userPhone;
        params.address = userInfo.address;
        params.content = string;
        new BaseModel().submit(this, params)
                .subscribe(new Observer<BaoXiuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaoXiuBean userBean) {
                        if (userBean != null) {
                            ToastUtils.toast("提交成功");
                            finish();
                        } else {
                            ToastUtils.toast("提交失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof ServerException) {
                            ServerException se = (ServerException) e;
                            ToastUtils.toast(SubmitActivity.this, se.getMessage());
                        } else {
                            ToastUtils.toast("提交失败");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    //问题描述
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            num.setText(String.valueOf(temp.length()));
        }

    };
}
