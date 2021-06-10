package com.jxxx.rhtx.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.LoginBean;
import com.jxxx.rhtx.utils.AddressPickTask;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.HttpRequestUtils;
import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.utils.view.MatisseUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SetUserInfoActivity extends BaseActivity {
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.et_user_1)
    EditText mEtUser1;
    @BindView(R.id.tv_user_2)
    TextView mTvUser2;
    @BindView(R.id.tv_user_3)
    TextView mTvUser3;
    @BindView(R.id.tv_user_4)
    TextView mTvUser4;
    @BindView(R.id.tv_user_5)
    TextView mTvUser5;
    @BindView(R.id.tv_user_6)
    TextView mTvUser6;
    @BindView(R.id.tv_user_7)
    TextView mTvUser7;
    @BindView(R.id.tv_user_8)
    TextView mTvUser8;
    @BindView(R.id.tv_user_9)
    TextView mTvUser9;
    public static double xb,sg,tz;
    public static String rq;
    public String tx;

    @Override
    public int intiLayout() {
        return R.layout.activity_set_user_info;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        RetrofitUtil.getInstance().apiService()
                .getDetail()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<LoginBean> result) {
                        if (isDataInfoSucceed(result)) {
                            LoginBean data = result.getData();
                            tx = data.getAvatar();
                            GlideImgLoader.loadImageViewWithCirclr(SetUserInfoActivity.this,data.getAvatar(),mIvHead);
                            mTvUserName.setText(StringUtil.isBlank(data.getNickName())?"":data.getNickName());
                            xb = data.getSex();
                            sg = Double.valueOf(data.getHeight());
                            rq = data.getBirthday();
                            tz = Double.valueOf(data.getWeight());
                            mEtUser1.setText(StringUtil.isBlank(data.getNickName())?"":data.getNickName());
                            mTvUser2.setText(data.getSex()==1?"男":"女");
                            mTvUser3.setText(data.getHeight()+"cm");
                            mTvUser4.setText(data.getWeight()+"kg");
                            mTvUser5.setText(StringUtil.isBlank(data.getBirthday())?"请设置出生日期":data.getBirthday());
                            mTvUser6.setText(StringUtil.isBlank(data.getRegion())?"请设置位置":data.getRegion());
                            mTvUser7.setText(data.getMobile());
//                            let bmi = (res.weight / res.height / res.height * 10000).toFixed(1)
                            Double double8 = Double.valueOf((data.getWeight()))/Double.valueOf((data.getHeight()))/Double.valueOf((data.getHeight()))*10000;
                            mTvUser8.setText(String.format("%.1f",double8));

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

    @Override
    protected void onResume() {
        super.onResume();
        mTvUser2.setText(xb==1?"男":"女");
        mTvUser4.setText(tz+"cm");
        mTvUser3.setText(sg+"kg");
        mTvUser5.setText(StringUtil.isBlank(rq)?"":rq);
    }

    @OnClick({R.id.tv_go_xyb,R.id.iv_back,R.id.iv_head,R.id.tv_user_2, R.id.tv_user_3, R.id.tv_user_4, R.id.tv_user_5, R.id.tv_user_6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_head:
                MatisseUtils.gotoSelectPhoto(this, 1, true);
                break;
            case R.id.tv_user_2:
            case R.id.tv_user_5:
                startActivity(new Intent(this,SetUserXbActivity.class));
                break;
            case R.id.tv_user_3:
            case R.id.tv_user_4:
                startActivity(new Intent(this,SetUserSgActivity.class));
                break;
            case R.id.tv_user_6:
                onAddressPicker();
                break;
            case R.id.tv_go_xyb:
                setHeadImgPaht();
                break;
        }
    }
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    mTvUser6.setText(province.getAreaName() + "," + city.getAreaName());
                } else {
                    mTvUser6.setText(province.getAreaName() + "," + city.getAreaName() + "," + county.getAreaName());
                }
            }
        });
        task.execute("北京", "北京", "北京");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        setHeadImg(selectList.get(0).getCompressPath());
                    }
                    break;
            }
        }
    }

    public void setHeadImg(String compressPath){
        showLoading();
        HttpRequestUtils.uploadFiles(compressPath, new HttpRequestUtils.UploadFileInterface() {
            @Override
            public void succeed(String path) {
                hideLoading();
                tx = path;
                GlideImgLoader.loadImageViewRadius(SetUserInfoActivity.this, path, 5, mIvHead);
            }

            @Override
            public void failure() {
                hideLoading();
            }
        });
    }
    private void setHeadImgPaht() {
        RetrofitUtil.getInstance().apiService()
                .updateUserInfo(tx,rq, (int) xb,tz+"",sg+"",mEtUser1.getText().toString(),mTvUser6.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if(isDataInfoSucceed(result)){
                            ToastUtil.showToast("修改成功");
                            SetUserInfoActivity.this.finish();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("修改失败");
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }
}
