package com.jxxx.rhtx.view.activity.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.jxxx.rhtx.MainActivity;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.AddChangeList;
import com.jxxx.rhtx.bean.DeviceDetailsBaen;
import com.jxxx.rhtx.bean.HomeInfoBean;
import com.jxxx.rhtx.lanya.BluetoothLjUtils;
import com.jxxx.rhtx.utils.ExcelUtil;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.utils.view.ChartHelper;
import com.jxxx.rhtx.utils.view.ChartHelper_1;
import com.jxxx.rhtx.utils.view.DialogUtils;
import com.jxxx.rhtx.view.activity.DeviceLinkActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceLink2Activity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.iv_state)
    ImageView mIvState;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_time_1)
    TextView mTvTime1;
    @BindView(R.id.tv_time_2)
    TextView mTvTime2;
    @BindView(R.id.tv_time_3)
    TextView mTvTime3;
    private MyReceiver mMyReceiver;
    @BindView(R.id.line_chart1)
    LineChart mLineChart1;
    @BindView(R.id.line_chart2)
    LineChart mLineChart2;
    @BindView(R.id.iv_state_z)
    ImageView mIvStateZ;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_details)
    TextView tv_details;
    @BindView(R.id.tv_drz)
    TextView tv_drz;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.ll_state)
    LinearLayout mLlState;
    @BindView(R.id.ll_stop)
    LinearLayout mLlStop;
    @BindView(R.id.ll_state_ly)
    LinearLayout ll_state_ly;
    private List<Entry> mData1 = new ArrayList<>();
    private List<Entry> mData2 = new ArrayList<>();
    private DeviceDetailsBaen data;
    private int type_id = 0;
    HomeInfoBean.DeviceBean mChangeListBean;
    private List<HomeInfoBean.DeviceBean.ChangeListBean> mChangeList;
    int totalTime = 0;
    int totalTimeS = 0;
    String strR;

    @Override
    public int intiLayout() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            strR = "???????????????";
            return R.layout.activity_device_link_2_h;
        } else {
            strR = "???????????????";
            return R.layout.activity_device_link_2;
        }
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        ChartHelper_1.initChart(mData1, mLineChart1, -1);
        ChartHelper.initChart(mData2, mLineChart2, -1);
        data = (DeviceDetailsBaen) getIntent().getSerializableExtra("data");
        if (data == null) {
            type_id = getIntent().getIntExtra("type_id", 0);
            setToolbarR(myToolbar, getIntent().getStringExtra("type_name"), strR);
            mLlState.setVisibility(View.GONE);
            mLlStop.setVisibility(View.INVISIBLE);
            ll_state_ly.setVisibility(View.VISIBLE);
            mChangeListBean = (HomeInfoBean.DeviceBean) getIntent().getSerializableExtra("mChangeListBean");
            if (mChangeListBean == null) {
                return;
            }
            mChangeList = mChangeListBean.getChangeList();
            if (mChangeList != null) {
                for (int i = 0; i < mChangeList.size(); i++) {
                    String[] resultSrt = mChangeList.get(i).getValue().replace("[", "").replace("]", "").split(",");
                    if (resultSrt.length == 1) {
                        if(StringUtil.isNotBlank(resultSrt[0])){
                            ChartHelper.addEntry(mData1, mLineChart1, Float.parseFloat(resultSrt[0]));
                        }
                    }

                }
            }
            return;
        }
        setToolbarR(myToolbar, data.getDeviceName(), strR);
        GlideImgLoader.setViewImg(this, data.getImgUrl(), iv_icon);
        /**
         * ??????????????????
         */
        mMyReceiver = new MyReceiver();//??????????????????
        IntentFilter filter = new IntentFilter("com.jxxx.rhtx");// ??????IntentFilter??????
        registerReceiver(mMyReceiver, filter);// ??????Broadcast Receive
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyReceiver != null) {
            unregisterReceiver(mMyReceiver);
        }
    }

    @Override
    public void initData() {

    }

    public void getScreenMessage() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @OnClick({R.id.tv_xz, R.id.ll_home, R.id.ll_state, R.id.ll_stop, R.id.ll_state_ly, R.id.iv_state_z})
    public void onViewClicked(View view) {
        Intent mIntent;
        switch (view.getId()) {
            case R.id.tv_xz:
                getScreenMessage();
                break;
            case R.id.ll_state_ly:
                mIntent = new Intent(this, DeviceLinkActivity.class);
                mIntent.putExtra("id", type_id);
                startActivity(mIntent);
                break;
            case R.id.ll_home:
                if (data == null) {
                    finish();
                    return;
                }
                DialogUtils.showDialogHint(this, "?????????????????????????????????", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        endUseDevice();
                        BluetoothLjUtils.ble4Util.disconnect();
                        startActivity(new Intent(DeviceLink2Activity.this, MainActivity.class));
                    }
                });
                break;
            case R.id.ll_state:
                if (state == 1) {
                    mIvState.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_2));
                    mTvState.setText("??????");
                    state = 2;
                    mIvStateZ.setImageDrawable(getResources().getDrawable(R.mipmap.ic_jlsj_stop));
                    mTvStart.setText("????????????");
                    isKaiShi = false;
                    state_jl = 2;
                } else if (state == 2) {
                    mIvState.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_5));
                    mTvState.setText("??????");
                    state = 1;
                }
                break;//
            case R.id.iv_state_z:
                if (data == null) {
                    mIntent = new Intent(this, DeviceLinkActivity.class);
                    mIntent.putExtra("id", type_id);
                    startActivity(mIntent);
                    return;
                }
                if (state == 2) {
                    ToastUtil.showToast("???????????????...");
                    return;
                }
                if (state_jl == 1) {
                    mIvStateZ.setImageDrawable(getResources().getDrawable(R.mipmap.ic_jlsj_stop));
                    mTvStart.setText("????????????");
                    isKaiShi = false;
                    state_jl = 2;
                } else if (state_jl == 2) {
                    mIvStateZ.setImageDrawable(getResources().getDrawable(R.mipmap.ic_jlsj_start));
                    mTvStart.setText("????????????");
                    state_jl = 1;
                    startTime();
                }
                break;
            case R.id.ll_stop:
                if (changeList.size() == 0) {
                    ToastUtil.showToast("????????????????????????");
                    return;
                }
                showLoading();

                DialogUtils.showDialogHint(this, "?????????????????????????????????", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        state = 2;
                        endUseDevice();
                        lianJieSheBei();
                    }
                });

                break;
        }
    }

    boolean isKaiShi = true;

    private void startTime() {
        isKaiShi = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isKaiShi) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isKaiShi) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                totalTime++;
                                mTvTime.setText(StringUtil.formatDateTime(totalTime));
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void startTimeS() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (js<1001) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    totalTimeS++;
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (data == null) {
            super.onBackPressed();
            return;
        }
        DialogUtils.showDialogHint(this, "?????????????????????????????????", false, () -> {
            endUseDevice();
            BluetoothLjUtils.ble4Util.disconnect();
            startActivity(new Intent(DeviceLink2Activity.this, MainActivity.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] resultData = intent.getByteArrayExtra("resultData");
            initUIData(resultData);
        }
    }

    int mixV = 0;//?????????????????????
    int zuiG = 0;//??????????????????
    int zuiD = 0;//??????????????????
    long time = 0;//??????????????????
    boolean isZ = true;//?????????
    int jsq = 0;//
    int state = 1;//1 ?????? 2?????????
    int state_jl = 2;//1 ?????? 2?????????
    boolean isStart = true;
    List<AddChangeList.ChangeListBean> changeList = new ArrayList<>();

    public void initUIData(byte[] strData) {
        if (state != 1) {
            Log.w("---?????????", "?????????:");
            return;
        }
        Log.w("---?????????", "strData:" + Arrays.toString(strData));
        List<String> dataLists = new ArrayList<>();
        for (int i = 0; i < strData.length; i++) {
            String stfff = Integer.toHexString(strData[i] & 0xFF);
            if (stfff.length() == 1) {
                stfff = "0" + stfff;
            }
            dataLists.add(stfff);
            if (stfff.equals("aa")) {
                break;
            }
        }
        List<Integer> dataSz = new ArrayList<>();

        if (dataLists.size() == 4 &&
                dataLists.get(0).equals("fa") && dataLists.get(3).equals("aa")) {
            if(isStart){
                isStart = false;
                startTimeS();
            }
            int v = Integer.parseInt(dataLists.get(1) + dataLists.get(2), 16);
            tv_drz.setText("???????????????:" + v + "pF");
            ChartHelper_1.addEntry(mData1, mLineChart1, v);
            Log.w("---?????????", "v:" + getX(v));

            if (state_jl == 1) {
                AddChangeList.ChangeListBean bean = new AddChangeList.ChangeListBean();
                bean.setValue(v+"");
                bean.setChangeTime(StringUtil.getTimeToYMD(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                changeList.add(bean);
            }
            v = getX(v);
            if (v < mixV || mixV == 0) {
                mixV = v;
            }
            jsq++;
            if (v < zuiG && isZ && jsq > 5) {//50  60
                jsq = 0;
                isZ = false;
                long aa = System.currentTimeMillis() - time;
                double bb = aa;
                String cc = String.format("%.2f", bb / 1000);
                time = System.currentTimeMillis();
                int hxpl = (int) (60 / Double.valueOf(cc));
                if(hxpl < 50){
                    tv_details.setText("????????????:" +hxpl + "???/min");
                    ChartHelper.addEntry(mData2, mLineChart2, (int) (60 / Float.parseFloat(cc)));
                    dataSz.add(hxpl);
                }
            }

            if (v > zuiD && !isZ) {//50  60
                zuiD = v;
                isZ = true;
            }
            zuiG = v;
            zuiD = v;
            if(js<1001){
                js++;
            }
            Log.w("---?????????"+totalTimeS, "mixV:" + mTvTime1.getText().toString());
            if(mTvTime2.getText().toString().equals("0s")&& js==500){
                mTvTime2.setText(totalTimeS+"s");
            }
            if(mTvTime1.getText().toString().equals("0s")&& js==1000){
                mTvTime1.setText(totalTimeS+"s");
            }
        }
    }
    int js = 0;
    public  int getX(int x){
        if(x%10 != 0)
            x = x + (10 - x%10);

        return x;
    }
    private void endUseDevice() {
        RetrofitUtil.getInstance().apiService()
                .endUseDevice(data.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (isDataInfoSucceed(result)) {
                        } else {
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

    private void lianJieSheBei() {
        showLoading();
        AddChangeList dataT = new AddChangeList();
        dataT.setId(data.getId());
        dataT.setChangeList(changeList);
        RetrofitUtil.getInstance().apiService()
                .addChangeList(dataT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        hideLoading();
                        if (isDataInfoSucceed(result)) {
                            DialogUtils.showDialogHintDc(DeviceLink2Activity.this, "???????????????excel???????????????", new DialogUtils.ErrorDialogInterfaceA() {
                                @Override
                                public void btnConfirm(int index) {
                                    if (index == 1) {
                                        BluetoothLjUtils.ble4Util.disconnect();
                                        startActivity(new Intent(DeviceLink2Activity.this, MainActivity.class));
                                        return;
                                    }
                                    deriveExcel(dataT);
                                }
                            });
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

    private void deriveExcel(AddChangeList dataT) {
        showLoading();
        // TODO Auto-generated method stub
        try {
            ExcelUtil.writeExcel(this, dataT, data.getDeviceName());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoading();
                                ToastUtil.showToast("");
                                BluetoothLjUtils.ble4Util.disconnect();
                                DialogUtils.showDialogHint(DeviceLink2Activity.this, "????????????\n??????????????????\n????????????/Android/data/com.jxxx.rhtx/files/RenHeTianXia",
                                        true, new DialogUtils.ErrorDialogInterface() {
                                            @Override
                                            public void btnConfirm() {
                                                startActivity(new Intent(DeviceLink2Activity.this, MainActivity.class));
                                            }
                                        });
//
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            DialogUtils.showDialogHintDc(DeviceLink2Activity.this, "?????????????????????????????????", new DialogUtils.ErrorDialogInterfaceA() {
                @Override
                public void btnConfirm(int index) {
                    if (index == 1) {
                        BluetoothLjUtils.ble4Util.disconnect();
                        startActivity(new Intent(DeviceLink2Activity.this, MainActivity.class));
                        return;
                    }
                    deriveExcel(dataT);
                }
            });
            e.printStackTrace();
        }
    }
}
