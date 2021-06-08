package com.jxxx.rhtx.view.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.DeviceDetailsBaen;
import com.jxxx.rhtx.utils.GlideImageLoader;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceLinkActivity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.iv_bj)
    ImageView iv_bj;
    @BindView(R.id.tv_sb_name)
    TextView mTvSbName;
    @BindView(R.id.tv_sort_Name)
    TextView mTvSortName;
    @BindView(R.id.iv_sb_img)
    ImageView mIvSbImg;
    @BindView(R.id.tv_sb_sm)
    TextView mTvSbSm;
    @BindView(R.id.tv_szmr)
    TextView mTvSzmr;
    @BindView(R.id.tv_sb_type)
    TextView mTvSbType;
    @BindView(R.id.tv_details)
    TextView mTvDetails;
    int id,defaultShowId;
    private DeviceDetailsBaen data;

    @Override
    public int intiLayout() {
        return R.layout.activity_device_link;
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        setToolbar(myToolbar, "智能设备", true);
    }

    @Override
    public void initData() {
        id = getIntent().getIntExtra("id", 0);
        defaultShowId = SharedUtils.singleton().get(ConstValues.DEFAULT_SHOW, 0);
        GlideImageLoader.loadImageAndDefault(this, SharedUtils.singleton().get(ConstValues.USER_BACK_IMG, ""), iv_bj);
        RetrofitUtil.getInstance().apiService()
                .getDeviceDetails(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<DeviceDetailsBaen>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<DeviceDetailsBaen> result) {
                        if (isDataInfoSucceed(result)) {
                            data = result.getData();
                            GlideImageLoader.loadImageAndDefault(DeviceLinkActivity.this,result.getData().getImgUrl(),mIvSbImg);
                            mTvSbType.setText(result.getData().getDeviceName());
                            mTvSortName.setText(result.getData().getSortName());
                            if(StringUtil.isNotBlank(result.getData().getRemark())){
                                mTvSbSm.setText(result.getData().getRemark());
                            }
                            mTvSzmr.setText(defaultShowId==id?"取消默认":"设为默认");
                            if(StringUtil.isNotBlank(result.getData().getDetails())) {
                                mTvDetails.setText(Html.fromHtml(result.getData().getDetails()));
                            }

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

    @OnClick({R.id.tv_szmr, R.id.tv_bnt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_szmr:
                if(mTvSzmr.getText().toString().equals("设为默认")){
                    getDeviceDetails(id);
                }else{
                    getDeviceDetails(0);
                }
                break;
            case R.id.tv_bnt:
                Intent mIntent = new Intent(this,DeviceLinkJcActivity.class);
                mIntent.putExtra("data",data);
                startActivity(mIntent);
                break;
        }
    }

    private void getDeviceDetails(int id) {
        RetrofitUtil.getInstance().apiService()
                .updateDefaultShow(id+"")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (isDataInfoSucceed(result)) {
                            SharedUtils.singleton().put(ConstValues.DEFAULT_SHOW,id);
                            defaultShowId = id;
                            if(defaultShowId==0){
                                mTvSzmr.setText("设为默认");
                            }else{
                                mTvSzmr.setText("取消默认");
                            }
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
