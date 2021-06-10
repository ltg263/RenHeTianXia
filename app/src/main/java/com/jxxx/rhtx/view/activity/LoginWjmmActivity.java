package com.jxxx.rhtx.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.HttpUtils;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.TimeCounter;
import com.jxxx.rhtx.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginWjmmActivity extends BaseActivity {
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.et_sjh)
    EditText mEtSjh;
    @BindView(R.id.get_code)
    TextView mGetCode;
    @BindView(R.id.et_yzm)
    EditText mEtYzm;
    @BindView(R.id.et_mm)
    EditText mEtMm;
    @BindView(R.id.tv_ok)
    TextView mTvOk;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    private TimeCounter mTimeCounter;



    @OnClick({R.id.get_code, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                getCode();
                break;
            case R.id.tv_ok:
                setPos();
                break;
        }
    }

    private void setPos() {
        showLoading();
        String sjh = mEtSjh.getText().toString();
        String yzm = mEtYzm.getText().toString();
        String mm = mEtMm.getText().toString();
        if(StringUtil.isBlank(sjh) ||StringUtil.isBlank(yzm) || StringUtil.isBlank(mm)){
            ToastUtil.showLongStrToast(LoginWjmmActivity.this,"填写不完整");
            return;
        }
        RetrofitUtil.getInstance().apiService()
                .verifyForgetPswd(sjh,mm,yzm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if(result.getStatus()==0){
                            ToastUtil.showLongStrToast(LoginWjmmActivity.this,"修改成功,重新登录");
                            finish();
                            MainApplication.getContext().AppExit();
                            startActivity(new Intent(LoginWjmmActivity.this,LoginActivity.class));
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
            mTimeCounter = new TimeCounter(60 * 1000, 1000, mGetCode);
            mTimeCounter.start();
            HttpUtils.goGetYzm(sjh,0);
        } else {
            ToastUtil.showLongStrToast(this,"请输入正确的手机号");
        }
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login_wjjmm;
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        setToolbar(myToolbar, "设置新密码", true);
    }

    @Override
    public void initData() {

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

}
