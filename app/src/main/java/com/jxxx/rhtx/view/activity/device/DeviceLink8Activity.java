package com.jxxx.rhtx.view.activity.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.utils.view.ChartHelper;
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

public class DeviceLink8Activity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.iv_dr_1)
    ImageView mIvDr1;
    @BindView(R.id.tv_dr_1)
    TextView mTvDr1;
    @BindView(R.id.ll_dr_1)
    RelativeLayout mLlDr1;
    @BindView(R.id.iv_dr_2)
    ImageView mIvDr2;
    @BindView(R.id.tv_dr_2)
    TextView mTvDr2;
    @BindView(R.id.ll_dr_2)
    RelativeLayout mLlDr2;
    @BindView(R.id.iv_dr_3)
    ImageView mIvDr3;
    @BindView(R.id.tv_dr_3)
    TextView mTvDr3;
    @BindView(R.id.ll_dr_3)
    RelativeLayout mLlDr3;
    @BindView(R.id.tv_ch_1)
    TextView mTvCh1;
    @BindView(R.id.tv_ch_2)
    TextView mTvCh2;
    @BindView(R.id.tv_ch_3)
    TextView mTvCh3;
    @BindView(R.id.tv_dr_1v)
    TextView tv_dr_1v;
    @BindView(R.id.tv_dr_2v)
    TextView tv_dr_2v;
    @BindView(R.id.tv_dr_3v)
    TextView tv_dr_3v;
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
    @BindView(R.id.ll_state_ly)
    LinearLayout ll_state_ly;

    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.iv_state_z)
    ImageView mIvStateZ;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private MyReceiver mMyReceiver;
    private List<Entry> mData1 = new ArrayList<>();
    private List<Entry> mData2 = new ArrayList<>();
    private List<Entry> mData3 = new ArrayList<>();
    private DeviceDetailsBaen data;
    boolean isSelectDr1 = true;
    boolean isSelectDr2 = true;
    boolean isSelectDr3 = true;
    private int type_id;
    String strR;

    HomeInfoBean.DeviceBean mChangeListBean;
    private List<HomeInfoBean.DeviceBean.ChangeListBean> mChangeList;

    @Override
    public int intiLayout() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ){
            strR = "???????????????";
            return R.layout.activity_device_link_8_h;
        }else {
            strR = "???????????????";
            return R.layout.activity_device_link_8;
        }
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        ChartHelper.initChart(new ArrayList<>(), mLineChart, -1);
        data = (DeviceDetailsBaen) getIntent().getSerializableExtra("data");
        if(data ==null){
            type_id = getIntent().getIntExtra("type_id",0);
            setToolbarR(myToolbar, getIntent().getStringExtra("type_name"),strR);
            mLlState.setVisibility(View.GONE);
            mLlStop.setVisibility(View.INVISIBLE);
            ll_state_ly.setVisibility(View.VISIBLE);
            mChangeListBean = (HomeInfoBean.DeviceBean) getIntent().getSerializableExtra("mChangeListBean");
            if(mChangeListBean==null){
                return;
            }
            mChangeList = mChangeListBean.getChangeList();
            if(mChangeList!=null){
                for(int i=0;i<mChangeList.size();i++){
                    String[] resultSrt = mChangeList.get(i).getValue().replace("[","").replace("]","").split(",");
                    if(resultSrt.length==3){
                        ChartHelper.addEntryYs(mData1,mData2,mData3,resultSrt,mLineChart,isSelectDr1,isSelectDr2,isSelectDr3);
                    }
                }
            }
            return;
        }
        setToolbarR(myToolbar, data.getDeviceName(),strR);
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

    public void getScreenMessage(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
        }
    }
    @OnClick({R.id.tv_xz,R.id.iv_state_z,R.id.ll_home,R.id.ll_state,R.id.ll_state_ly, R.id.ll_stop,R.id.ll_dr_1, R.id.ll_dr_2, R.id.ll_dr_3})
    public void onViewClicked(View view) {
        Intent mIntent;
        switch (view.getId()) {
            case R.id.tv_xz:
                getScreenMessage();
                break;
            case R.id.iv_state_z:
                if(data==null){
                    mIntent = new Intent(this, DeviceLinkActivity.class);
                    mIntent.putExtra("id", type_id);
                    startActivity(mIntent);
                    return;
                }
                if(state==2){
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
            case R.id.ll_state_ly:
                mIntent = new Intent(this, DeviceLinkActivity.class);
                mIntent.putExtra("id",type_id);
                startActivity(mIntent);
                break;
            case R.id.ll_dr_1:
                if(!isSelectDr1){
                    mLlDr1.setBackground(getResources().getDrawable(R.drawable.btn_shape_theme_dr1));
                    mIvDr1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_yse));
                    mTvDr1.setTextColor(getColor(R.color.white));
                }else{
                    mLlDr1.setBackground(getResources().getDrawable(R.drawable.shape_eee_line_5));
                    mIvDr1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_no));
                    mTvDr1.setTextColor(getColor(R.color.color_999999));
                }
                isSelectDr1 = !isSelectDr1;
                if(data==null){
                    ChartHelper.addEntryYs(mData1,mData2,mData3,null,mLineChart,isSelectDr1,isSelectDr2,isSelectDr3);
                }
                break;
            case R.id.ll_dr_2:
                if(!isSelectDr2){
                    mLlDr2.setBackground(getResources().getDrawable(R.drawable.btn_shape_theme_dr2));
                    mIvDr2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_yse));
                    mTvDr2.setTextColor(getColor(R.color.white));
                }else{
                    mLlDr2.setBackground(getResources().getDrawable(R.drawable.shape_eee_line_5));
                    mIvDr2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_no));
                    mTvDr2.setTextColor(getColor(R.color.color_999999));
                }
                isSelectDr2 = !isSelectDr2;
                if(data==null){
                    ChartHelper.addEntryYs(mData1,mData2,mData3,null,mLineChart,isSelectDr1,isSelectDr2,isSelectDr3);
                }
                break;
            case R.id.ll_dr_3:
                if(!isSelectDr3){
                    mLlDr3.setBackground(getResources().getDrawable(R.drawable.btn_shape_theme_dr3));
                    mIvDr3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_yse));
                    mTvDr3.setTextColor(getColor(R.color.white));
                }else{
                    mLlDr3.setBackground(getResources().getDrawable(R.drawable.shape_eee_line_5));
                    mIvDr3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_select_f_no));
                    mTvDr3.setTextColor(getColor(R.color.color_999999));
                }
                isSelectDr3 = !isSelectDr3;
                if(data==null){
                    ChartHelper.addEntryYs(mData1,mData2,mData3,null,mLineChart,isSelectDr1,isSelectDr2,isSelectDr3);
                }
                break;
            case R.id.ll_home:
                if(data==null){
                    finish();
                    return;
                }
                DialogUtils.showDialogHint(this, "?????????????????????????????????", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        endUseDevice();
                        BluetoothLjUtils.ble4Util.disconnect();
                        startActivity(new Intent(DeviceLink8Activity.this, MainActivity.class));
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
            case R.id.ll_stop:
                if (changeList.size() == 0) {
                    ToastUtil.showToast("????????????????????????");
                    return;
                }
                DialogUtils.showDialogHint(this, "?????????????????????????????????", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        endUseDevice();
                        state = 2;
                        lianJieSheBei();
                    }
                });
                break;
        }
    }
    boolean isKaiShi = true;
    int totalTime = 0;
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
        if(data==null){
            super.onBackPressed();
            return;
        }
        DialogUtils.showDialogHint(this, "?????????????????????????????????", false, new DialogUtils.ErrorDialogInterface() {
            @Override
            public void btnConfirm() {
                endUseDevice();
                BluetoothLjUtils.ble4Util.disconnect();
                startActivity(new Intent(DeviceLink8Activity.this, MainActivity.class));
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

    int state = 1;//1 ?????? 2?????????
    int state_jl = 2;//1 ?????? 2?????????

    List<AddChangeList.ChangeListBean> changeList = new ArrayList<>();

    public void initUIData(byte[] resultData) {
        if (state != 1) {
            Log.w("---?????????", "?????????:");
            return;
        }
        Log.w("---?????????", "resultData:"+Arrays.toString(resultData));
//        Log.w("---?????????", "BluetoothLjUtils:"+ Arrays.toString(BluetoothLjUtils.constructTest(resultData)));
        if(resultData.length == 15 && resultData[0]==-6 && resultData[14]==-86){
            // -6, 1, 0, 11, -86,
            // -6, 2, 0, 8, -86,
            // -6, 3, 0, 8, -86
            String[] resultSrt = getResultSrt(resultData);
            Log.w("---?????????", "resultSrt:"+ Arrays.toString(resultSrt));
            if(state_jl==1){
                AddChangeList.ChangeListBean bean = new AddChangeList.ChangeListBean();
                bean.setValue(Arrays.toString(resultSrt));
                bean.setChangeTime(StringUtil.getTimeToYMD(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                changeList.add(bean);
            }
            ChartHelper.addEntryYs(mData1,mData2,mData3,resultSrt,mLineChart,isSelectDr1,isSelectDr2,isSelectDr3);
            if(resultSrt.length==3 && state_jl==1){
                tv_dr_1v.setText(resultSrt[0]);
                tv_dr_2v.setText(resultSrt[1]);
                tv_dr_3v.setText(resultSrt[2]);
            }
            return;
        }
        String[] resultSrt = BluetoothLjUtils.constructTest(resultData);
        if(resultData.length > 10 && resultSrt.length == 3){
            if(state_jl==1) {
                AddChangeList.ChangeListBean bean = new AddChangeList.ChangeListBean();
                bean.setValue(Arrays.toString(resultSrt));
                bean.setChangeTime(StringUtil.getTimeToYMD(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                changeList.add(bean);
            }
            ChartHelper.addEntryYs(mData1,mData2,mData3,resultSrt,mLineChart,isSelectDr1,isSelectDr2,isSelectDr3);
            if(state_jl==1){
                tv_dr_1v.setText(resultSrt[0]);
                tv_dr_2v.setText(resultSrt[1]);
                tv_dr_3v.setText(resultSrt[2]);
            }
        }

    }
    private String[] getResultSrt(byte[] resultData){
        List<String> dataLists = new ArrayList<>();
        for (int i = 0; i < resultData.length; i++) {
            String stfff = Integer.toHexString(resultData[i] & 0xFF);
            if (stfff.length() == 1) {
                stfff = "0" + stfff;
            }
            dataLists.add(stfff);
        }
        int v1 = Integer.parseInt(dataLists.get(2), 16)*256+Integer.parseInt(dataLists.get(3), 16);

        int v2 = Integer.parseInt(dataLists.get(7), 16)*256+Integer.parseInt(dataLists.get(8), 16);

        int v3 = Integer.parseInt(dataLists.get(12), 16)*256+Integer.parseInt(dataLists.get(13), 16);

        return new String[]{v1+"",v2+"",v3+""};
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
                        Log.w("onError:","onSubscribe:"+d.toString());

                    }

                    @Override
                    public void onNext(Result result) {
                        hideLoading();
                        if (isDataInfoSucceed(result)) {
                            DialogUtils.showDialogHintDc(DeviceLink8Activity.this, "???????????????excel???????????????",  new DialogUtils.ErrorDialogInterfaceA() {
                                @Override
                                public void btnConfirm(int index) {
                                    if(index==1){
                                        BluetoothLjUtils.ble4Util.disconnect();
                                        startActivity(new Intent(DeviceLink8Activity.this, MainActivity.class));
                                        return;
                                    }
                                    deriveExcel(dataT);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w("onError:","onError:"+e.toString());
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
                                ToastUtil.showToast("????????????");
                                BluetoothLjUtils.ble4Util.disconnect();
                                startActivity(new Intent(DeviceLink8Activity.this, MainActivity.class));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            DialogUtils.showDialogHintDc(DeviceLink8Activity.this, "?????????????????????????????????",  new DialogUtils.ErrorDialogInterfaceA() {
                @Override
                public void btnConfirm(int index) {
                    if(index==1){
                        BluetoothLjUtils.ble4Util.disconnect();
                        startActivity(new Intent(DeviceLink8Activity.this, MainActivity.class));
                        return;
                    }
                    deriveExcel(dataT);
                }
            });
            e.printStackTrace();
        }
    }

}
