package com.jxxx.rhtx.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.jxxx.rhtx.MainActivity;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.utils.StatusBarUtil;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.utils.view.RulerView_xz;
import com.zkk.view.rulerview.RulerView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SetUserSgActivity extends BaseActivity {


    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.tv_sg)
    TextView mTvSg;
    @BindView(R.id.tv_tz)
    TextView mTvTz;
    @BindView(R.id.ruler_weight)
    RulerView mRulerWeight;
    @BindView(R.id.ruler_height)
    RulerView_xz mRulerHeight;
    String sex = "";
    String age = "";
    

    @OnClick({R.id.tv_go_xyb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go_xyb:
                SetUserInfoActivity.tz = Double.parseDouble(mTvTz.getText().toString());
                SetUserInfoActivity.sg = Double.parseDouble(mTvSg.getText().toString());
//                setUserInfo();
                finish();
                break;
        }
    }

    private void setUserInfo() {
        showLoading();//0未知 1 男 2 女
//
//        RetrofitUtil.getInstance().apiService()
//                .updateUserInfo(null,sex.equals("男")?"1":"2",age,mTvTz.getText().toString(),mTvSg.getText().toString()+".0")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<Result>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Result result) {
//                        if(isDataInfoSucceed(result)){
//                            ToastUtil.showLongStrToast(SetUserSgActivity.this, "修改成功");
//                            Intent mIntent = new Intent(SetUserSgActivity.this, MainActivity.class);
//                            startActivity(mIntent);
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        hideLoading();
//                    }
//                });
    }

    @Override
    public int intiLayout() {
//        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
        return R.layout.activity_set_user_sg;
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        setToolbar(myToolbar, "修改信息");
        sex = getIntent().getStringExtra("sex");
        age = getIntent().getStringExtra("age");
        mRulerWeight.setOnValueChangeListener(value -> mTvTz.setText(value + ""));
        /**
         *
         * @param selectorValue 未选择时 默认的值 滑动后表示当前中间指针正在指着的值
         * @param minValue   最大数值
         * @param maxValue   最小的数值
         * @param per   最小单位  如 1:表示 每2条刻度差为1.   0.1:表示 每2条刻度差为0.1 在demo中 身高mPerValue为1  体重mPerValue 为0.1
         */
        mRulerWeight.setValue(60, 40, 300, 0.1f);

        mRulerHeight.setOnValueChangeListener(value -> mTvSg.setText((int) value + ""));
        /**
         *
         * @param selectorValue 未选择时 默认的值 滑动后表示当前中间指针正在指着的值
         * @param minValue   最大数值
         * @param maxValue   最小的数值
         * @param per   最小单位  如 1:表示 每2条刻度差为1.   0.1:表示 每2条刻度差为0.1 在demo中 身高mPerValue为1  体重mPerValue 为0.1
         */
        mRulerHeight.setValue(165, 40, 250, 1f);
    }

    @Override
    public void initData() {

    }
}
