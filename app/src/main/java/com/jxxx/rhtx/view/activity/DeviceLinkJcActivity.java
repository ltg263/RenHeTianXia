package com.jxxx.rhtx.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.AddOrderData;
import com.jxxx.rhtx.bean.DeviceDetailsBaen;
import com.jxxx.rhtx.lanya.Ble4_0Util;
import com.jxxx.rhtx.lanya.BluetoothLjUtils;
import com.jxxx.rhtx.utils.GlideImageLoader;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.utils.view.MatisseUtils;
import com.jxxx.rhtx.view.activity.device.DeviceLink1Activity;
import com.jxxx.rhtx.view.activity.device.DeviceLink2Activity;
import com.jxxx.rhtx.view.activity.device.DeviceLink8Activity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceLinkJcActivity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.tv_tiaoguo)
    TextView tv_tiaoguo;
    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.tv_2)
    TextView mTv2;
    @BindView(R.id.tv_3)
    TextView mTv3;
    @BindView(R.id.tv_4)
    TextView mTv4;
    @BindView(R.id.tv_5)
    TextView mTv5;
    @BindView(R.id.tv_6)
    TextView mTv6;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.tv_bnt)
    TextView tv_bnt;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.ll_jiao_cheng)
    LinearLayout ll_jiao_cheng;
    @BindView(R.id.ll_lianjie)
    LinearLayout ll_lianjie;
    @BindView(R.id.iv_refresh)
    ImageView iv_refresh;
    @BindView(R.id.iv_refresh_lj)
    ImageView iv_refresh_lj;
    @BindView(R.id.iv_lj_s)
    ImageView iv_lj_s;
    @BindView(R.id.iv_lj_n)
    ImageView iv_lj_n;

    DeviceDetailsBaen data;
    int lianjieTime = 30;

    @Override
    public int intiLayout() {
        return R.layout.activity_device_link_jc;
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        MatisseUtils.filePermissions(this);
        data = (DeviceDetailsBaen) getIntent().getSerializableExtra("data");
        setToolbar(myToolbar, "????????????");
    }

    @Override
    public void initData() {
        tv_time.setText(lianjieTime+"s");
    }


    @OnClick({R.id.tv_tiaoguo, R.id.tv_bnt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tiaoguo:
                if(BluetoothLjUtils.ble4Util!=null){
                    BluetoothLjUtils.ble4Util.disconnect();
                }
                BluetoothLjUtils.ble4Util = new Ble4_0Util(this);
                BluetoothLjUtils.ble4Util.init();
                jinxingLj();
                break;
            case R.id.tv_bnt:
                showJc();
                break;
        }
    }

    private void showJc() {
        String str = tv_bnt.getText().toString();
        if (str.equals("????????????????????????")) {
            mTv1.setText("???");
            mTv2.setTextColor(getResources().getColor(R.color.white));
            mTv2.setBackground(getResources().getDrawable(R.drawable.shape_radius_th));
            mTv4.setText("???????????????????????????");
            mTv5.setText("????????????????????????????????????????????????????????????");
            mTv6.setText("1???????????????????????????\n2????????????????????????????????????\n3???????????????????????????");
            tv_bnt.setText("?????????");
            GlideImageLoader.loadImage(this, R.mipmap.ic_jc_2, mIv);
            if(BluetoothLjUtils.ble4Util!=null){
                BluetoothLjUtils.ble4Util.disconnect();
            }
            BluetoothLjUtils.ble4Util = new Ble4_0Util(this);
            BluetoothLjUtils.ble4Util.init();
        } else if (str.equals("?????????") || str.equals("????????????")) {
            jinxingLj();
        }
    }

    private void jinxingLj() {
        if(!MatisseUtils.filePermissions(this)){
            return;
        }
        if (!BluetoothLjUtils.ble4Util.getBluetoothAdapter().isEnabled()) {
            ToastUtil.showLongStrToast(this, "??????????????????");
        } else {
            tv_tiaoguo.setVisibility(View.GONE);
            ll_jiao_cheng.setVisibility(View.GONE);
            ll_lianjie.setVisibility(View.VISIBLE);
            tv_bnt.setVisibility(View.INVISIBLE);
            iv_refresh.setImageDrawable(getResources().getDrawable(R.mipmap.ic_refresh));
            GlideImgLoader.setImgAnimation(this, iv_lj_s);
            GlideImgLoader.setImgAnimationN(this, iv_lj_n);
            GlideImgLoader.setImgAnimation(this, iv_refresh);
            tv_time.setText(lianjieTime+"s");
            setTime();
            BluetoothLjUtils.sousuo(this, data.getSortName(),new BluetoothLjUtils.BluetoothLjInterface() {
                @Override
                public void linkState(int state) {
                    //1????????????//2????????????//3?????????//4???????????????
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (state) {
                                case 0:
                                    isLianJie = false;
                                    if(BluetoothLjUtils.ble4Util!=null){
                                        BluetoothLjUtils.ble4Util.disconnect();
                                    }
                                    lianjieTime = 30;
                                    iv_lj_s.clearAnimation();
                                    iv_lj_n.clearAnimation();
                                    iv_refresh.clearAnimation();
                                    iv_refresh_lj.clearAnimation();
                                    tv_bnt.setText("????????????");
                                    tv_time.setText("????????????...");
                                    tv_bnt.setVisibility(View.VISIBLE);
                                    break;
                                case 1:
                                    iv_refresh.clearAnimation();
                                    GlideImgLoader.loadImage(DeviceLinkJcActivity.this, R.mipmap.ic_duih, iv_refresh);
                                    break;
                                case 2:
                                    isLianJie = false;
                                    deviceAdd();
                                    break;
                                case 3:
                                    GlideImgLoader.setImgAnimation(DeviceLinkJcActivity.this, iv_refresh_lj);
                                    break;
                                case 4:
                                    break;
                            }

                        }
                    });
                }
            });
        }
    }
    boolean isLianJie = true;
    private void setTime() {
        isLianJie = true;
        new Thread(() -> {
            try {
                while (isLianJie){
                    Thread.sleep(1000);
                    runOnUiThread(() -> {
                        lianjieTime--;
                        if(lianjieTime==0){
                            BluetoothLjUtils.ble4Util.disconnect();
                            lianjieTime = 30;
                            iv_lj_s.clearAnimation();
                            iv_lj_n.clearAnimation();
                            iv_refresh.clearAnimation();
                            iv_refresh_lj.clearAnimation();
                            tv_bnt.setText("????????????");
                            tv_time.setText("????????????...");
                            tv_bnt.setVisibility(View.VISIBLE);
                            isLianJie = false;
                        }
                        if(isLianJie){
                            tv_time.setText(lianjieTime+"s");
                        }
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startActivityType(int id) {
        Intent mIntent = null;
        switch (data.getId()){
            case 1:
                mIntent = new Intent(DeviceLinkJcActivity.this, DeviceLink1Activity.class);
                break;
            case 2:
                mIntent = new Intent(DeviceLinkJcActivity.this, DeviceLink2Activity.class);
                break;
            case 8:
                mIntent = new Intent(DeviceLinkJcActivity.this, DeviceLink8Activity.class);
                break;
        }
        if(mIntent!=null){
            data.setId(id);
            mIntent.putExtra("data",data);
            startActivity(mIntent);
        }else{
            ToastUtil.showToast("???????????????");
        }
    }

    private void deviceAdd() {
        AddOrderData mData = new AddOrderData();
        mData.setDeviceName(data.getDeviceName());
        mData.setDeviceNo(BluetoothLjUtils.ble4Util.getBlemac());
        mData.setDeviceType(data.getId());
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .userAddDevice(mData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<AddOrderData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<AddOrderData> result) {
                        if (isDataInfoSucceed(result)) {
                            Log.w("---?????????","????????????");
                            startUseDevice(result.getData().getId());
                        }else{
                            hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void startUseDevice(int id) {
        RetrofitUtil.getInstance().apiService()
                .startUseDevice(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (isDataInfoSucceed(result)) {
                            startActivityType(id);
                        }else{
                            hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
