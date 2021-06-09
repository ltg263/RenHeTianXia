package com.jxxx.rhtx.view.activity;

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
import com.jxxx.rhtx.lanya.BluetoothLjUtils;
import com.jxxx.rhtx.utils.ImageUtils;
import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.utils.view.ChartHelper;
import com.jxxx.rhtx.utils.view.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceLink1Activity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    private MyReceiver mMyReceiver;
    @BindView(R.id.line_chart_1)
    LineChart mLineChart1;
    @BindView(R.id.line_chart_2)
    LineChart mLineChart2;
    @BindView(R.id.line_chart_3)
    LineChart mLineChart3;
    @BindView(R.id.line_chart_4)
    LineChart mLineChart4;
    @BindView(R.id.line_chart_5)
    LineChart mLineChart5;
    @BindView(R.id.iv_5)
    ImageView mIv5;
    @BindView(R.id.iv_4)
    ImageView mIv4;
    @BindView(R.id.iv_3)
    ImageView mIv3;
    @BindView(R.id.iv_2)
    ImageView mIv2;
    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.iv_state)
    ImageView mIvState;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.ll_state)
    LinearLayout mLlState;
    @BindView(R.id.ll_stop)
    LinearLayout mLlStop;
//    @BindView(R.id.iv_icon)
//    ImageView iv_icon;
    private List<Entry> mData1 = new ArrayList<>();
    private List<Entry> mData2 = new ArrayList<>();
    private List<Entry> mData3 = new ArrayList<>();
    private List<Entry> mData4 = new ArrayList<>();
    private List<Entry> mData5 = new ArrayList<>();
    private DeviceDetailsBaen data;

    @Override
    public int intiLayout() {
        return R.layout.activity_device_link_1;
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
        if(mMyReceiver!=null){
            unregisterReceiver(mMyReceiver);
        }
    }
    @Override
    public void initData(){
        MainApplication.addActivity(this);
        data = (DeviceDetailsBaen) getIntent().getSerializableExtra("data");
        setToolbar(myToolbar, data.getDeviceName(), true);
//        GlideImgLoader.setViewImg(this,data.getImgUrl(),iv_icon);
        ChartHelper.initChart(mData1, mLineChart1, 160);
        ChartHelper.initChart(mData2, mLineChart2, 160);
        ChartHelper.initChart(mData3, mLineChart3, 160);
        ChartHelper.initChart(mData4, mLineChart4, 160);
        ChartHelper.initChart(mData5, mLineChart5, 160);

    }


    @OnClick({R.id.ll_home, R.id.ll_state, R.id.ll_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                DialogUtils.showDialogHint(this, "确定要断开本次链接吗？", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        endUseDevice();
                        BluetoothLjUtils.ble4Util.disconnect();
                        MainApplication.getContext().finishAllActivity();
                        startActivity(new Intent(DeviceLink1Activity.this,MainActivity.class));
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
//                showLoading();
//                AddChangeList dataList = new AddChangeList();
//                dataList.setId(data.getId());
//                dataList.setChangeList(changeList);
//                Intent intent = new Intent(this, ShopActivity_1.class);
//                intent.putExtra("data", data);
//                startActivity(intent);
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
                MainApplication.getContext().finishAllActivity();
                BluetoothLjUtils.ble4Util.disconnect();
                startActivity(new Intent(DeviceLink1Activity.this,MainActivity.class));
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

    String lsStr = "";
    int state = 1;//1 开始 2停止中

    List<AddChangeList.ChangeListBean> changeList = new ArrayList<>();
    public void initUIData(byte[] strData) {
        if(state==2){
            return;
        }
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
        if (dataLists.size() == 7 &&
                dataLists.get(0).equals("fa") && dataLists.get(6).equals("aa")) {
            dataSz.add(Integer.parseInt(dataLists.get(1), 16));
            dataSz.add(Integer.parseInt(dataLists.get(2), 16));
            dataSz.add(Integer.parseInt(dataLists.get(3), 16));
            dataSz.add(Integer.parseInt(dataLists.get(4), 16));
            dataSz.add(Integer.parseInt(dataLists.get(5), 16));
        }
        if (lsStr.equals(dataSz.toString()) || dataSz.size() != 5) {
            Log.w("---》》》111", "最终角度:" + lsStr);
            return;
        }
        lsStr = dataSz.toString();
        Log.w("---》》》", "最终角度:" + lsStr);
        if (dataSz.size() == 5) {
            setImages(dataSz.get(0), dataSz.get(1), dataSz.get(2), dataSz.get(3), dataSz.get(4));
            ChartHelper.addEntry(mData1, mLineChart1, dataSz.get(0));
            ChartHelper.addEntry(mData2, mLineChart2, dataSz.get(1));
            ChartHelper.addEntry(mData3, mLineChart3, dataSz.get(2));
            ChartHelper.addEntry(mData4, mLineChart4, dataSz.get(3));
            ChartHelper.addEntry(mData5, mLineChart5, dataSz.get(4));
//            mTvAngle1.setText("弯曲角度 " + dataSz.get(0) + "°");
//            mTvAngle2.setText("弯曲角度 " + dataSz.get(1) + "°");
//            mTvAngle3.setText("弯曲角度 " + dataSz.get(2) + "°");
//            mTvAngle4.setText("弯曲角度 " + dataSz.get(3) + "°");
//            mTvAngle5.setText("弯曲角度 " + dataSz.get(4) + "°");
            String currentTime = StringUtil.getTimeToYMD(System.currentTimeMillis(), "HH:mm:ss");
//            mTvTime1.setText("当前时间  " + currentTime);
//            mTvTime2.setText("当前时间  " + currentTime);
//            mTvTime3.setText("当前时间  " + currentTime);
//            mTvTime4.setText("当前时间  " + currentTime);
//            mTvTime5.setText("当前时间  " + currentTime);

            AddChangeList.ChangeListBean bean = new AddChangeList.ChangeListBean();
            bean.setValue(dataSz.toString());
            bean.setChangeTime(StringUtil.getTimeToYMD(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
            changeList.add(bean);
        }
    }

    private void setImages(Integer integer, Integer integer1, Integer integer2, Integer integer3, Integer integer4) {
        ImageUtils.setImg1(this, mIv1, integer);
        ImageUtils.setImg2(this, mIv2, integer1);
        ImageUtils.setImg3(this, mIv3, integer2);
        ImageUtils.setImg4(this, mIv4, integer3);
        ImageUtils.setImg5(this, mIv5, integer4);
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
                        }else{
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
