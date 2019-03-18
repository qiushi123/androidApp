package com.wuye;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.wuye.api.net.BaseModel;
import com.wuye.api.net.ServerException;
import com.wuye.api.params.LoginReqParams;
import com.wuye.api.response.LoginResponse;
import com.wuye.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 登录页
 */
public class LoginActivity extends AppCompatActivity {


    private View mProgressView;
    private View mLoginFormView;

    private View llLogin;
    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;

    private View llRegister;
    private EditText mPasswordAgain;
    private EditText addressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        llLogin = findViewById(R.id.ll_login);
        mPhoneView = findViewById(R.id.phone);
        mPasswordView = findViewById(R.id.password);

        llRegister = findViewById(R.id.ll_register);
        mPasswordAgain = findViewById(R.id.password_again);
        addressView = findViewById(R.id.address);


        findViewById(R.id.go_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLogin.setVisibility(View.GONE);
                llRegister.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(false);
            }
        });

        findViewById(R.id.go_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLogin.setVisibility(View.VISIBLE);
                llRegister.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.regist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(true);
            }
        });
    }


    /**
     * 登录
     */
    private void attemptLogin(boolean isRegister) {
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();
        mPasswordView.setError(null);
        mPhoneView.setError(null);
        //校验密码
        if (TextUtils.isEmpty(password) || password.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            mPasswordView.requestFocus();
            return;
        }
        // 校验手机号
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            mPhoneView.requestFocus();
            return;
        } else if (phone.length() != 11) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
            mPhoneView.requestFocus();
            return;
        }

        if (isRegister) {
            String passwordAgain = mPasswordAgain.getText().toString();
            String address = addressView.getText().toString();
            mPasswordAgain.setError(null);
            addressView.setError(null);
            if (TextUtils.isEmpty(passwordAgain)) {
                mPasswordView.setError("请再次输入密码");
                mPasswordView.requestFocus();
                return;
            }
            if (!password.equals(passwordAgain)) {
                mPasswordView.setError("两次密码不一致");
                mPasswordView.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(address) || address.length() < 6) {
                addressView.setError("地址必须大于6位");
                addressView.requestFocus();
                return;
            }
        }
        goLogin(phone, password);
    }


    /*
     * 请求网络接口
     * 执行登录或者注册操作
     * */
    public void goLogin(String phone, String password) {
        LoginReqParams params = new LoginReqParams();
        params.userPhone = phone;
        params.userPassword = password;
        new BaseModel().login(this, params)
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgress(true);
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        showProgress(false);

                        if (loginResponse != null && !TextUtils.isEmpty(loginResponse.userPhone)) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgress(false);
                        if (e instanceof ServerException) {
                            ServerException se = (ServerException) e;
                            ToastUtils.toast(LoginActivity.this, se.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 展示登录进度条，并隐藏登录布局
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}

