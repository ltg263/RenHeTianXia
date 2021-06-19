package com.jxxx.rhtx.view.activity.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

    @Override
    public int intiLayout() {
        return R.layout.activity_device_link_2;
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        ChartHelper_1.initChart(mData1, mLineChart1, -1);
        ChartHelper.initChart(mData2, mLineChart2, -1);
        data = (DeviceDetailsBaen) getIntent().getSerializableExtra("data");
        if (data == null) {
            type_id = getIntent().getIntExtra("type_id", 0);
            setToolbar(myToolbar, getIntent().getStringExtra("type_name"), true);
            mLlState.setVisibility(View.GONE);
            mLlStop.setVisibility(View.GONE);
            ll_state_ly.setVisibility(View.VISIBLE);
            mChangeListBean = (HomeInfoBean.DeviceBean) getIntent().getSerializableExtra("mChangeListBean");
            if (mChangeListBean == null) {
                return;
            }
            mChangeList = mChangeListBean.getChangeList();
            if (mChangeList != null) {
                for (int i = 0; i < mChangeList.size(); i++) {
                    String[] resultSrt = mChangeList.get(i).getValue().replace("[", "").replace("]", "").split(",");
                    if(resultSrt.length==1){
                        ChartHelper.addEntry(mData2, mLineChart2, Float.parseFloat(resultSrt[0]));
                    }

                }
            }
            return;
        }
        setToolbar(myToolbar, data.getDeviceName(), true);
        GlideImgLoader.setViewImg(this, data.getImgUrl(), iv_icon);
        /**
         * 广播动态注册
         */
        mMyReceiver = new MyReceiver();//集成广播的类
        IntentFilter filter = new IntentFilter("com.jxxx.rhtx");// 创建IntentFilter对象
        registerReceiver(mMyReceiver, filter);// 注册Broadcast Receive
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


    @OnClick({R.id.ll_home, R.id.ll_state, R.id.ll_stop, R.id.ll_state_ly, R.id.iv_state_z})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_state_ly:
                Intent mIntent = new Intent(this, DeviceLinkActivity.class);
                mIntent.putExtra("id", type_id);
                startActivity(mIntent);
                break;
            case R.id.ll_home:
                if (data == null) {
                    finish();
                    return;
                }
                DialogUtils.showDialogHint(this, "确定要断开本次链接吗？", false, new DialogUtils.ErrorDialogInterface() {
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
                    mTvState.setText("开始");
                    state = 2;
                    mIvStateZ.setImageDrawable(getResources().getDrawable(R.mipmap.ic_jlsj_stop));
                    mTvStart.setText("开始记录");
                    isKaiShi = false;
                    state_jl = 2;
                } else if (state == 2) {
                    mIvState.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_5));
                    mTvState.setText("暂停");
                    state = 1;
                }
                break;//
            case R.id.iv_state_z:
                if(state==2){
                    ToastUtil.showToast("设备暂停中...");
                    return;
                }
                if (state_jl == 1) {
                    mIvStateZ.setImageDrawable(getResources().getDrawable(R.mipmap.ic_jlsj_stop));
                    mTvStart.setText("开始记录");
                    isKaiShi = false;
                    state_jl = 2;
                } else if (state_jl == 2) {
                    mIvStateZ.setImageDrawable(getResources().getDrawable(R.mipmap.ic_jlsj_start));
                    mTvStart.setText("暂停记录");
                    state_jl = 1;
                    startTime();
                }
                break;
            case R.id.ll_stop:
                if (changeList.size() == 0) {
                    ToastUtil.showToast("您还没有测试数据");
                    return;
                }
                showLoading();

                DialogUtils.showDialogHint(this, "确定要结束本次链接吗？", false, new DialogUtils.ErrorDialogInterface() {
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
                    if(isKaiShi) {
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

    @Override
    public void onBackPressed() {
        if (data == null) {
            super.onBackPressed();
            return;
        }
        DialogUtils.showDialogHint(this, "确定要断开本次链接吗？", false, () -> {
            endUseDevice();
            BluetoothLjUtils.ble4Util.disconnect();
            startActivity(new Intent(DeviceLink2Activity.this, MainActivity.class));
        });
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] resultData = intent.getByteArrayExtra("resultData");
            initUIData(resultData);
        }
    }

    int mixV = 0;//呼吸过程最小值
    int zuiG = 0;//一周期最大值
    int zuiD = 0;//一周期最小值
    long time = 0;//一周期的时间
    boolean isZ = true;//是否长
    int jsq = 0;//
    int state = 1;//1 开始 2停止中
    int state_jl = 2;//1 开始 2停止中

    List<AddChangeList.ChangeListBean> changeList = new ArrayList<>();

    public void initUIData(byte[] strData) {
        if (state != 1) {
            Log.w("---》》》", "暂停中:");
            return;
        }
        Log.w("---》》》", "strData:" + Arrays.toString(strData));
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

            int v = Integer.parseInt(dataLists.get(1) + dataLists.get(2), 16);
            Log.w("---》》》", "v:" + v);
            if (v < mixV || mixV == 0) {
                mixV = v;
            }
            jsq++;
            if (v < zuiG && isZ && jsq > 100) {//50  60
                jsq = 0;
                isZ = false;
                long aa = System.currentTimeMillis() - time;
                double bb = aa;
                String cc = String.format("%.2f", 1000 / bb);
                time = System.currentTimeMillis();
                tv_details.setText("呼吸频率:"+(int)(Double.valueOf(cc)*60)+"次/min");
                ChartHelper.addEntry(mData2, mLineChart2, (int) (Float.parseFloat(cc) * 60));
                dataSz.add((int) (Float.parseFloat(cc) * 100));
                if (state_jl == 1) {
                    AddChangeList.ChangeListBean bean = new AddChangeList.ChangeListBean();
                    bean.setValue(dataSz.toString());
                    bean.setChangeTime(StringUtil.getTimeToYMD(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                    changeList.add(bean);
                }
            }

            if (v > zuiD && !isZ) {//50  60
                zuiD = v;
                isZ = true;
            }
            zuiG = v;
            zuiD = v;
            Log.w("---》》》", "mixV:" + v);
            tv_drz.setText("当前电容值:"+ v+"pF");
            ChartHelper_1.addEntry(mData1, mLineChart1, v);
        }
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
                            DialogUtils.showDialogHintDc(DeviceLink2Activity.this, "是否要通过excel格式导出？",  new DialogUtils.ErrorDialogInterfaceA() {
                                @Override
                                public void btnConfirm(int index) {
                                    if(index==1){
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
                                ToastUtil.showToast("导出成功");
                                BluetoothLjUtils.ble4Util.disconnect();
                                startActivity(new Intent(DeviceLink2Activity.this, MainActivity.class));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            DialogUtils.showDialogHintDc(DeviceLink2Activity.this, "导出失败是否重新导出？",  new DialogUtils.ErrorDialogInterfaceA() {
                @Override
                public void btnConfirm(int index) {
                    if(index==1){
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
