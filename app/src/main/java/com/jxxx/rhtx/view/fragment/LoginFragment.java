package com.jxxx.rhtx.view.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jxxx.rhtx.MainActivity;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.HttpUtils;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.base.BaseFragment;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.LoginBean;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.TimeCounter;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.view.activity.LoginWjmmActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.ll_mm)
    LinearLayout mLlMm;
    @BindView(R.id.ll_yzm)
    LinearLayout mLlYzm;
    @BindView(R.id.tv_fs)
    TextView mTvFs;
    @BindView(R.id.et_sjh)
    EditText mEtSjh;
    @BindView(R.id.et_pos)
    EditText mEtPos;
    @BindView(R.id.et_yzm)
    EditText mEtYzm;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.tv_wjmm)
    TextView mTvWjmm;
    @BindView(R.id.btn_login)
    TextView mBtnLogin;
    @BindView(R.id.iv_select)
    ImageView iv_select;
    private TimeCounter mTimeCounter;

    @OnClick({R.id.iv_select,R.id.btn_login, R.id.tv_fs, R.id.tv_wjmm,R.id.tv_get_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if(iv_select.isSelected()){
                    ToastUtil.showToast("请先阅读《韧和天下协议》");
                    return;
                }
                goLogin();
                break;
            case R.id.tv_fs:
                if (mTvFs.getText().toString().equals("验证码登录")) {
                    mTvFs.setText("密码登录");
                    mLlMm.setVisibility(View.GONE);
                    mLlYzm.setVisibility(View.VISIBLE);
                } else {
                    mTvFs.setText("验证码登录");
                    mLlMm.setVisibility(View.VISIBLE);
                    mLlYzm.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_wjmm:
                startActivity(new Intent(getActivity(), LoginWjmmActivity.class));
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.iv_select:
                iv_select.setSelected(!iv_select.isSelected());
                break;
        }
    }
    private void getCode() {
        String sjh = mEtSjh.getText().toString();
        if (!TextUtils.isEmpty(sjh) && sjh.length() == 11) {
            mTimeCounter = new TimeCounter(60 * 1000, 1000, mTvGetCode);
            mTimeCounter.start();
            HttpUtils.goGetYzm(sjh,0);
        } else {
            ToastUtil.showLongStrToast(getActivity(),"请输入正确的手机号");
        }
    }

    private void goLogin() {
        showLoading();
        if (mTvFs.getText().toString().equals("验证码登录")) {
            mmLogin();
        } else {
            yzmLogin();
        }

    }

    private void yzmLogin() {
        String sjh = mEtSjh.getText().toString();
        String yzm = mEtYzm.getText().toString();
        if(StringUtil.isBlank(sjh) || StringUtil.isBlank(yzm)){
            ToastUtil.showLongStrToast(getActivity(),"手机号或验证码不能为空");
            return;
        }
        RetrofitUtil.getInstance().apiService()
                .postLogin(sjh,yzm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<LoginBean> result) {
                        if(isDataInfoSucceed(result)){
                            ToastUtil.showLongStrToast(getActivity(),"登录成功");
                            Log.w("App-Token:","App-Token:"+ SharedUtils.getToken());
                            SharedUtils.singleton().put(ConstValues.TOKEN,result.getData().getTokenId());
                            SharedUtils.singleton().put(ConstValues.ISLOGIN,true);
                            startActivity(new Intent(getActivity(), MainActivity.class));
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

    private void mmLogin() {
        String sjh = mEtSjh.getText().toString();
        String pos = mEtPos.getText().toString();
        if(StringUtil.isBlank(sjh) || StringUtil.isBlank(pos)){
            ToastUtil.showLongStrToast(getActivity(), "手机号或密码不能为空");
            return;
        }
        RetrofitUtil.getInstance().apiService()
                .postLogin(sjh,pos)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<LoginBean> result) {
                        if(isDataInfoSucceed(result)){
                            ToastUtil.showLongStrToast(getActivity(),"登录成功");
                            Log.w("App-Token:","App-Token:"+ SharedUtils.getToken());
                            SharedUtils.singleton().put(ConstValues.TOKEN,result.getData().getTokenId());
                            SharedUtils.singleton().put(ConstValues.ISLOGIN,true);
                            startActivity(new Intent(getActivity(), MainActivity.class));
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
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {
        iv_select.setSelected(true);
    }

    @Override
    protected void initData() {

    }
}
