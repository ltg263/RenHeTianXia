package com.jxxx.rhtx.view.activity;

import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.AddOrderData;
import com.jxxx.rhtx.bean.DeviceDetailsBaen;
import com.jxxx.rhtx.lanya.Ble4_0Util;
import com.jxxx.rhtx.lanya.BluetoothLjUtils;
import com.jxxx.rhtx.utils.GlideImageLoader;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceLinkJcActivity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
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

    @Override
    public int intiLayout() {
        return R.layout.activity_device_link_jc;
    }

    @Override
    public void initView() {
        data = (DeviceDetailsBaen) getIntent().getSerializableExtra("data");
        setToolbar(myToolbar, "设备链接", true);
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.tv_tiaoguo, R.id.tv_bnt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tiaoguo:
                ll_jiao_cheng.setVisibility(View.GONE);
                ll_lianjie.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_bnt:
                showJc();
                break;
        }
    }

    private void showJc() {
        String str = tv_bnt.getText().toString();
        if (str.equals("确认完成上述操作")) {
            mTv1.setText("√");
            mTv2.setTextColor(getResources().getColor(R.color.white));
            mTv2.setBackground(getResources().getDrawable(R.drawable.shape_radius_th));
            mTv4.setText("请打开手机蓝牙功能");
            mTv5.setText("若手机已处于下方状态，直接点击确认下一步");
            mTv6.setText("1：手机打开“设置”\n2：点击“其他网络与连接”\n3：打开“蓝牙”开关");
            tv_bnt.setText("下一步");
            GlideImageLoader.loadImage(this, R.mipmap.ic_jc_2, mIv);
            if(BluetoothLjUtils.ble4Util!=null){
                BluetoothLjUtils.ble4Util.disconnect();
            }
            BluetoothLjUtils.ble4Util = new Ble4_0Util(this);
            BluetoothLjUtils.ble4Util.init();
        } else if (str.equals("下一步")) {
            if (!BluetoothLjUtils.ble4Util.getBluetoothAdapter().isEnabled()) {
                ToastUtil.showLongStrToast(this, "请先开启蓝牙");
            } else {
                ll_jiao_cheng.setVisibility(View.GONE);
                ll_lianjie.setVisibility(View.VISIBLE);
                GlideImgLoader.setImgAnimation(this, iv_lj_s);
                GlideImgLoader.setImgAnimationN(this, iv_lj_n);
                GlideImgLoader.setImgAnimation(this, iv_refresh);
                BluetoothLjUtils.sousuo(this, data.getSortName(),new BluetoothLjUtils.BluetoothLjInterface() {
                    @Override
                    public void linkState(int state) {
                        //1搜索成功//2链接成功//3链接中//4断开连接中
                        switch (state) {
                            case 0:
                                break;
                            case 1:
                                iv_refresh.clearAnimation();
                                GlideImgLoader.loadImage(DeviceLinkJcActivity.this, R.mipmap.ic_duih, iv_refresh);
                                break;
                            case 2:
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
        }
    }

    private void startActivityType() {
        Intent mIntent = null;
        switch (data.getId()){
            case 1:
                mIntent = new Intent(DeviceLinkJcActivity.this,DeviceLink1Activity.class);
                break;
            case 2:
                mIntent = new Intent(DeviceLinkJcActivity.this,DeviceLink2Activity.class);
                break;
            case 3:
                break;
        }
        if(mIntent!=null){
            mIntent.putExtra("data",data);
            startActivity(mIntent);
        }else{
            ToastUtil.showToast("暂无此设备");
        }
    }

    private void deviceAdd() {
        AddOrderData data = new AddOrderData();
        data.setDeviceName(data.getDeviceName());
        data.setDeviceNo(BluetoothLjUtils.ble4Util.getBlemac());
        data.setDeviceType(data.getId());
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .userAddDevice(data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (isDataInfoSucceed(result)) {
                            Log.w("---》》》","添加成功");
                            startUseDevice();
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

    private void startUseDevice() {
        RetrofitUtil.getInstance().apiService()
                .startUseDevice(data.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (isDataInfoSucceed(result)) {
                            startActivityType();
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
