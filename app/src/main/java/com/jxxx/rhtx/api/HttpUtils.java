package com.jxxx.rhtx.api;

import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HttpUtils {
    public static void goGetYzm(String sjh,int verifyType) {
        RetrofitUtil.getInstance().apiService()
                .getVerifyCode(sjh,verifyType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if(result.getStatus()==0){
                            ToastUtil.showLongStrToast(MainApplication.getContext(),"发送成功");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
