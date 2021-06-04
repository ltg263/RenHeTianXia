package com.jxxx.rhtx.view.fragment;


import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jxxx.rhtx.MainActivity;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.base.BaseFragment;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.DeviceUseLogList;
import com.jxxx.rhtx.bean.HomeInfoBean;
import com.jxxx.rhtx.bean.ParamValueBean;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.utils.view.ChartHelper;
import com.jxxx.rhtx.view.adapter.HomeBelowAdapter;
import com.jxxx.rhtx.view.adapter.HomeCenAdapter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeOneFragment extends BaseFragment {


    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_updata_info)
    TextView mTvUpdataInfo;
    @BindView(R.id.tv_user_xb)
    TextView mTvUserXb;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_notsb)
    TextView tv_notsb;
    @BindView(R.id.tv_user_sg)
    TextView mTvUserSg;
    @BindView(R.id.tv_user_nl)
    TextView mTvUserNl;
    @BindView(R.id.tv_user_tz)
    TextView mTvUserTz;
    @BindView(R.id.iv_sb)
    ImageView mIvSb;
    @BindView(R.id.tv_sb_name)
    TextView mTvSbName;
    @BindView(R.id.tv_sb_lx)
    TextView mTvSbLx;
    @BindView(R.id.tv_sb_select)
    TextView mTvSbSelect;
    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.tv_add_sb)
    TextView mTvAddSb;
    @BindView(R.id.rv_sb_list)
    RecyclerView mRvSbList;
    @BindView(R.id.rv_sbls_list)
    RecyclerView mRvSblsList;
    @BindView(R.id.ll_bj)
    LinearLayout ll_bj;
    @BindView(R.id.line_chart)
    LineChart mLineChart;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initView() {
        ChartHelper.initChart(new ArrayList<>(), mLineChart, 100);
    }

    @Override
    protected void initData() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getHome()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<HomeInfoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<HomeInfoBean> result) {
                        if (isDataInfoSucceed(result)) {
                            HomeInfoBean.UserBean userInfo = result.getData().getUser();
                            if(userInfo!=null){
                                GlideImgLoader.loadImageViewWithCirclr(getActivity(), userInfo.getAvatar(),  mIvHead);
                                SharedUtils.singleton().put(ConstValues.USER_NAME,userInfo.getNickName());
                                SharedUtils.singleton().put(ConstValues.USER_AVATAR,userInfo.getAvatar());
                                mTvUserName.setText(userInfo.getNickName());
                                mTvUserXb.setText(userInfo.getSex()==1?"性别 丨 男":"性别 丨 女");
                                mTvUserSg.setText("身高 丨 "+userInfo.getHeight()+"cm");
                                mTvUserTz.setText("体重 丨 "+userInfo.getWeight()+"kg");
                            }

                            HomeInfoBean.DeviceBean device = result.getData().getDevice();
                            if(device!=null) {
                                GlideImgLoader.loadImageAndDefault(getActivity(), device.getImgUrl(), mIvSb);
                                mTvSbName.setText(device.getTypeStr());
                                inivLine(device.getChangeList());
                            }
                            List<HomeInfoBean.HistroyDeviceBean> histroyDevice = result.getData().getHistroyDevice();
                            if(histroyDevice!=null && histroyDevice.size()>0){
                                tv_notsb.setVisibility(View.GONE);
                                mRvSbList.setVisibility(View.VISIBLE);
                                mRvSbList.setAdapter(new HomeBelowAdapter(histroyDevice));
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });

        RetrofitUtil.getInstance().apiService()
                .getDeviceUseLogList(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<DeviceUseLogList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<DeviceUseLogList> result) {
                        if (isDataInfoSucceed(result)) {
                            List<DeviceUseLogList.ListBean> useLogList = result.getData().getList();
                            if(useLogList!=null && useLogList.size()>0){
//                                tv_2.setVisibility(View.GONE);
                                mRvSblsList.setAdapter(new HomeCenAdapter(useLogList));
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

        RetrofitUtil.getInstance().apiService()
                .appointParamValue()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<ParamValueBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<ParamValueBean> result) {
                        if (isDataInfoSucceed(result)) {
                            GlideImgLoader.setViewImg(getActivity(),result.getData().getBackgeround(),ll_bj);
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

    private static LineDataSet getSet(List<Entry> mData, int pos) {
        LineDataSet set = new LineDataSet(mData, "");
        set.setDrawFilled(true);
        if(pos==0){
            set.setFillColor( Color.parseColor("#00ffffff"));
            set.setColor(Color.parseColor("#ffffff"));
//            set.setValueTextColor(Color.parseColor("#00ffffff"));
        }
        if(pos==1){
            set.setFillColor( Color.parseColor("#004A90E2"));
            set.setColor(Color.parseColor("#4A90E2"));
//            set.setValueTextColor(Color.parseColor("#00ffffff"));
        }
        if(pos==2){
            set.setFillColor( Color.parseColor("#00F65532"));
            set.setColor(Color.parseColor("#F65532"));
//            set.setValueTextColor(Color.parseColor("#00ffffff"));
        }
        if(pos==3){
            set.setFillColor( Color.parseColor("#00F65532"));
            set.setColor(Color.parseColor("#F65532"));
//            set.setValueTextColor(Color.parseColor("#00ffffff"));
        }
        if(pos==4){
            set.setFillColor( Color.parseColor("#009C51E7"));
            set.setColor(Color.parseColor("#9C51E7"));
//            set.setValueTextColor(Color.parseColor("#00ffffff"));
        }
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return set;
    }
    private void inivLine(List<HomeInfoBean.DeviceBean.ChangeListBean> changeList) {
        if(changeList==null || changeList.size()==0){
            return;
        }
        String value = changeList.get(0).getValue()
                .replace("[","").replace("]","");
        String[] arr = value.split(","); // 用,分割
        Log.w("value_arr","arr:"+ Arrays.toString(arr));
        LineData lineData = new LineData();
        for(int i=0;i<arr.length;i++){
            List<Entry> mData = new ArrayList<>();
            for (int j=0;j<changeList.size();j++){
                String value_z = changeList.get(j).getValue().replace("[", "").replace("]", "");
                String[] value_arr = value_z.split(",");
                mData.add(new Entry(j, Integer.valueOf(value_arr[i])));
            }
            lineData.addDataSet(getSet(mData,i));
        }
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
    }

    @OnClick({R.id.tv_updata_info, R.id.tv_sb_select,R.id.tv_add_sb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_updata_info:
                break;
            case R.id.tv_sb_select:

                break;
            case R.id.tv_add_sb:
                ((MainActivity)getActivity()).getBnvHomeNavigation().setSelectedItemId(R.id.menu_home_2);
                break;
        }
    }
}



