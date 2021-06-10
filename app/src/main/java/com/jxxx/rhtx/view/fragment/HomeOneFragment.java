package com.jxxx.rhtx.view.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.utils.view.ChartHelperHome;
import com.jxxx.rhtx.view.activity.DeviceHistroyActivity;
import com.jxxx.rhtx.view.activity.DeviceLink1Activity;
import com.jxxx.rhtx.view.activity.DeviceLink2Activity;
import com.jxxx.rhtx.view.activity.DeviceLink8Activity;
import com.jxxx.rhtx.view.activity.DeviceLinkActivity;
import com.jxxx.rhtx.view.activity.DeviceLinkJcActivity;
import com.jxxx.rhtx.view.activity.SetUserInfoActivity;
import com.jxxx.rhtx.view.adapter.HomeBelowAdapter;
import com.jxxx.rhtx.view.adapter.HomeCenAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @BindView(R.id.tv_mrym)
    TextView tv_mrym;
    @BindView(R.id.ll_mrym)
    RelativeLayout ll_mrym;
    @BindView(R.id.line_chart)
    LineChart mLineChart;
    boolean isShouOpen = true;
    private HomeBelowAdapter mHomeBelowAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initView() {
        showLoading();
        ChartHelperHome.initChart(new ArrayList<>(), mLineChart, -1);
        mHomeBelowAdapter = new HomeBelowAdapter(null);
        mRvSbList.setAdapter(mHomeBelowAdapter);
        mHomeBelowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent(mContext, DeviceLinkActivity.class);
                mIntent.putExtra("id",mHomeBelowAdapter.getData().get(position).getDeviceType());
                mContext.startActivity(mIntent);
            }
        });
        mHomeBelowAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    protected void initData() {
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
                        hideLoading();
                        if (isDataInfoSucceed(result)) {
                            HomeInfoBean.UserBean userInfo = result.getData().getUser();
                            if(userInfo!=null){
                                GlideImgLoader.loadImageViewWithCirclr(getActivity(), userInfo.getAvatar(),  mIvHead);
                                SharedUtils.singleton().put(ConstValues.USER_NAME, StringUtil.isBlank(userInfo.getNickName())?"":userInfo.getNickName());
                                SharedUtils.singleton().put(ConstValues.USER_AVATAR,StringUtil.isBlank(userInfo.getAvatar())?"":userInfo.getAvatar());
                                SharedUtils.singleton().put(ConstValues.DEFAULT_SHOW,userInfo.getDefaultShow());
                                mTvUserName.setText(userInfo.getNickName());
                                mTvUserXb.setText(userInfo.getSex()==1?"性别 丨 男":"性别 丨 女");
                                mTvUserSg.setText("身高 丨 "+userInfo.getHeight()+"cm");
                                mTvUserTz.setText("体重 丨 "+userInfo.getWeight()+"kg");
                            }

                            HomeInfoBean.DeviceBean device = result.getData().getDevice();
                            if(device!=null) {
                                ll_mrym.setVisibility(View.VISIBLE);
                                tv_mrym.setVisibility(View.VISIBLE);
                                GlideImgLoader.loadImageAndDefault(getActivity(), device.getImgUrl(), mIvSb);
                                mTvSbName.setText(device.getTypeStr());
                                inivLine(device.getChangeList());
                                if(isShouOpen){
                                    startActivityType(device.getDeviceType(),device.getTypeStr());
                                }
                            }
                            List<HomeInfoBean.HistroyDeviceBean> histroyDevice = result.getData().getHistroyDevice();
                            if(histroyDevice!=null && histroyDevice.size()>0){
                                tv_notsb.setVisibility(View.GONE);
                                mRvSbList.setVisibility(View.VISIBLE);
                                mHomeBelowAdapter.setNewData(histroyDevice);
                            }
                            isShouOpen = false;
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
                            SharedUtils.singleton().put(ConstValues.USER_BACK_IMG,result.getData().getIndexImg());
                            SharedUtils.singleton().put(ConstValues.SB_HELP,result.getData().getHelp());
                            SharedUtils.singleton().put(ConstValues.USER_BACK_IMG_SB,result.getData().getBackgeround());
                            SharedUtils.singleton().put(ConstValues.SB_CONTACT_WAY,result.getData().getPhone());
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

    private void startActivityType(int id, String typeStr) {
        Intent mIntent = null;
        switch (id){
            case 1:
                mIntent = new Intent(getActivity(), DeviceLink1Activity.class);
                break;
            case 2:
                mIntent = new Intent(getActivity(), DeviceLink2Activity.class);
                break;
            case 8:
                mIntent = new Intent(getActivity(), DeviceLink8Activity.class);
                break;
        }
        if(mIntent!=null){
            mIntent.putExtra("type_id",id);
            mIntent.putExtra("type_name",typeStr);
            startActivity(mIntent);
        }else{
            ToastUtil.showToast("暂无此设备");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.w("--->>>","onHiddenChanged:"+hidden);
        if(!hidden){
            initData();
        }
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
                mData.add(new Entry(j, Float.valueOf(value_arr[i])));
            }
            lineData.addDataSet(getSet(mData,i));
        }
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
    }

    @OnClick({R.id.tv_updata_info, R.id.tv_sb_select,R.id.tv_add_sb,R.id.rl_sb_log})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_updata_info:
                startActivity(new Intent(getActivity(), SetUserInfoActivity.class));
                break;
            case R.id.rl_sb_log:
                startActivity(new Intent(getActivity(), DeviceHistroyActivity.class));
                break;
            case R.id.tv_sb_select:
                getDeviceDetails(0);
                break;
            case R.id.tv_add_sb:
                ((MainActivity)getActivity()).getBnvHomeNavigation().setSelectedItemId(R.id.menu_home_2);
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
                            ll_mrym.setVisibility(View.GONE);
                            tv_mrym.setVisibility(View.INVISIBLE);
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



