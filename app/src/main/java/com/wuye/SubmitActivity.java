package com.wuye;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wuye.api.bean.BaoXiuBean;
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

    private TextView num;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        num = findViewById(R.id.content_num);
        content = findViewById(R.id.content);

        content.addTextChangedListener(mTextWatcher);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = content.getText().toString();
                if (string == null || string.length() < 1) {
                    ToastUtils.toast("报修内容不能为空");
                    return;
                }
                submit(string);
            }
        });
    }

    //提交
    private void submit(String string) {
        UserBean userInfo = SpUtils.getUserInfo(this);

        if (userInfo == null) {
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
