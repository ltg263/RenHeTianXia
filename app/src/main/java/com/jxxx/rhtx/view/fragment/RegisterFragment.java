package com.jxxx.rhtx.view.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.HttpUtils;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.base.BaseFragment;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.TimeCounter;
import com.jxxx.rhtx.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RegisterFragment extends BaseFragment {


    @BindView(R.id.et_sjh)
    EditText mEtSjh;
    @BindView(R.id.et_yzm)
    EditText mEtYzm;
    @BindView(R.id.get_sjh)
    TextView mGetSjh;
    @BindView(R.id.et_mm)
    EditText mEtMm;
    private TimeCounter mTimeCounter;

    @OnClick({R.id.get_sjh, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_sjh:
                getCode();
                break;
            case R.id.tv_ok:
                showLoading();
                getRegister();
                break;
        }
    }

    private void getRegister() {
        String sjh = mEtSjh.getText().toString();
        String yzz = mEtYzm.getText().toString();
        String mm = mEtMm.getText().toString();
        if(StringUtil.isBlank(sjh) || StringUtil.isBlank(yzz) || StringUtil.isBlank(mm)){
            ToastUtil.showLongStrToast(getActivity(),"填写不完整");
            return;
        }
        RetrofitUtil.getInstance().apiService()
                .postRegister(sjh,mm,yzz)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if(isDataInfoSucceed(result)){
                            ToastUtil.showLongStrToast(getContext(),"注册成功");
                            Log.w("token:","-->>"+result.getData().toString());
                            SharedUtils.singleton().put(result.getData().toString(),"token");

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }

    private void getCode() {
        String sjh = mEtSjh.getText().toString();
        if (!TextUtils.isEmpty(sjh) && sjh.length() == 11) {
            mTimeCounter = new TimeCounter(60 * 1000, 1000, mGetSjh);
            mTimeCounter.start();
            HttpUtils.goGetYzm(sjh,0);
        } else {
            ToastUtil.showLongStrToast(getActivity(),"请输入正确的手机号");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimeCounter != null) {
            mTimeCounter.cancel();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTimeCounter != null) {
            mTimeCounter.cancel();
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
