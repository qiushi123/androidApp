package com.wuye.api.net;


import com.wuye.api.response.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Description: Rx对网络请求的统一处理
 */
public class RxHelper {


    /**
     * 去壳处理。
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) {
                        int code = tBaseResponse.getCode();
                        String desc = tBaseResponse.getMsg();
                        T data = tBaseResponse.getData();
                        if (code == ServiceFactory.NET_SUCCESS_CODE) {
                            return createObservable(data);
                        } else {
                            return Observable.error(new ServerException(code, desc));
                        }
                    }
                }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 创建指定数据源的Observable
     *
     * @param t
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createObservable(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) {
                if (e != null) {
                    if (t != null) {
                        e.onNext(t);
                    }
                    e.onComplete();
                }
            }
        });
    }

}
