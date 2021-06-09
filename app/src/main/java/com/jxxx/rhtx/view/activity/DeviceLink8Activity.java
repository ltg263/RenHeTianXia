package com.jxxx.rhtx.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jxxx.rhtx.MainActivity;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.AddChangeList;
import com.jxxx.rhtx.bean.DeviceDetailsBaen;
import com.jxxx.rhtx.lanya.BluetoothLjUtils;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.utils.view.ChartHelper;
import com.jxxx.rhtx.utils.view.ChartHelperHome;
import com.jxxx.rhtx.utils.view.DialogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceLink8Activity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.iv_dr_1)
    ImageView mIvDr1;
    @BindView(R.id.tv_dr_1)
    TextView mTvDr1;
    @BindView(R.id.ll_dr_1)
    LinearLayout mLlDr1;
    @BindView(R.id.iv_dr_2)
    ImageView mIvDr2;
    @BindView(R.id.tv_dr_2)
    TextView mTvDr2;
    @BindView(R.id.ll_dr_2)
    LinearLayout mLlDr2;
    @BindView(R.id.iv_dr_3)
    ImageView mIvDr3;
    @BindView(R.id.tv_dr_3)
    TextView mTvDr3;
    @BindView(R.id.ll_dr_3)
    LinearLayout mLlDr3;
    @BindView(R.id.tv_ch_1)
    TextView mTvCh1;
    @BindView(R.id.tv_ch_2)
    TextView mTvCh2;
    @BindView(R.id.tv_ch_3)
    TextView mTvCh3;
    @BindView(R.id.line_chart)
    LineChart mLineChart;

    @BindView(R.id.iv_state)
    ImageView mIvState;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.ll_state)
    LinearLayout mLlState;
    @BindView(R.id.ll_stop)
    LinearLayout mLlStop;
    private MyReceiver mMyReceiver;
    private List<Entry> mData1 = new ArrayList<>();
    private List<Entry> mData2 = new ArrayList<>();
    private List<Entry> mData3 = new ArrayList<>();
    private DeviceDetailsBaen data;
    boolean isSelectDr1 = true;
    boolean isSelectDr2 = true;
    boolean isSelectDr3 = true;

    @Override
    public int intiLayout() {
        return R.layout.activity_device_link_8;
    }

    @Override
    public void initView() {
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
        MainApplication.addActivity(this);
        data = (DeviceDetailsBaen) getIntent().getSerializableExtra("data");
        setToolbar(myToolbar, data.getDeviceName(), true);
        ChartHelper.initChart(new ArrayList<>(), mLineChart, 500);

    }


    @OnClick({R.id.ll_home, R.id.ll_state, R.id.ll_stop,R.id.ll_dr_1, R.id.ll_dr_2, R.id.ll_dr_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dr_1:
                if(!isSelectDr1){
                    mLlDr1.setBackground(getResources().getDrawable(R.drawable.btn_shape_theme));
                    mIvDr1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_yse));
                    mTvDr1.setTextColor(getColor(R.color.white));
                }else{
                    mLlDr1.setBackground(getResources().getDrawable(R.drawable.shape_eee_line_5));
                    mIvDr1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_no));
                    mTvDr1.setTextColor(getColor(R.color.color_999999));
                }
                isSelectDr1 = !isSelectDr1;
                break;
            case R.id.ll_dr_2:
                if(!isSelectDr2){
                    mLlDr2.setBackground(getResources().getDrawable(R.drawable.btn_shape_theme));
                    mIvDr2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_yse));
                    mTvDr2.setTextColor(getColor(R.color.white));
                }else{
                    mLlDr2.setBackground(getResources().getDrawable(R.drawable.shape_eee_line_5));
                    mIvDr2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_no));
                    mTvDr2.setTextColor(getColor(R.color.color_999999));
                }
                isSelectDr2 = !isSelectDr2;
                break;
            case R.id.ll_dr_3:
                if(!isSelectDr3){
                    mLlDr3.setBackground(getResources().getDrawable(R.drawable.btn_shape_theme));
                    mIvDr3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_yse));
                    mTvDr3.setTextColor(getColor(R.color.white));
                }else{
                    mLlDr3.setBackground(getResources().getDrawable(R.drawable.shape_eee_line_5));
                    mIvDr3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_no));
                    mTvDr3.setTextColor(getColor(R.color.color_999999));
                }
                isSelectDr3 = !isSelectDr3;
                break;
            case R.id.ll_home:
                DialogUtils.showDialogHint(this, "确定要断开本次链接吗？", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        endUseDevice();
                        BluetoothLjUtils.ble4Util.disconnect();
                        MainApplication.getContext().finishAllActivity();
                        startActivity(new Intent(DeviceLink8Activity.this, MainActivity.class));
                        finish();
                    }
                });
                break;
            case R.id.ll_state:
                if (state == 1) {
                    mIvState.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_2));
                    mTvState.setText("开始");
                    state = 2;
                } else if (state == 2) {
                    mIvState.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_5));
                    mTvState.setText("暂停");
                    state = 1;
                }
                break;//
            case R.id.ll_stop:
                if (changeList.size() == 0) {
                    ToastUtil.showToast("您还没有测试数据");
                    return;
                }
                showLoading();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (state == 2) {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                state = 2;
                                hideLoading();
//                                AddChangeList data = new AddChangeList();
//                                data.setId(data.getId());
//                                data.setChangeList(changeList);
//                                Intent intent = new Intent(DeviceLink1Activity.this, null);
//                                intent.putExtra("data", data);
//                                intent.putExtra("mixV", mixV);
//                                intent.putParcelableArrayListExtra("mData1", (ArrayList<? extends Parcelable>) mData1);
//                                startActivity(intent);
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        DialogUtils.showDialogHint(this, "确定要断开本次链接吗？", false, new DialogUtils.ErrorDialogInterface() {
            @Override
            public void btnConfirm() {
                endUseDevice();
                BluetoothLjUtils.ble4Util.disconnect();
                MainApplication.getContext().finishAllActivity();
                startActivity(new Intent(DeviceLink8Activity.this, MainActivity.class));
                finish();
            }
        });
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] resultData = intent.getByteArrayExtra("resultData");
            initUIData(resultData);
        }
    }

    int state = 1;//1 开始 2停止中

    List<AddChangeList.ChangeListBean> changeList = new ArrayList<>();

    public void initUIData(byte[] resultData) {
        if (state != 1) {
            Log.w("---》》》", "暂停中:");
            return;
        }
//        inivLine(resultData);
        Log.w("---》》》", "BluetoothLjUtils:"+ Arrays.toString(BluetoothLjUtils.constructTest(resultData)));
        String[] resultSrt = BluetoothLjUtils.constructTest(resultData);
        if(resultData.length > 10 && resultSrt!=null && resultSrt.length==3){
            ChartHelper.addEntryYs(mData1,mData2,mData3,resultSrt,mLineChart,isSelectDr1,isSelectDr2,isSelectDr3);
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

}
