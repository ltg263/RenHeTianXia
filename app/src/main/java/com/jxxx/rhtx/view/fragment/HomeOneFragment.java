package com.jxxx.rhtx.view.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.base.BaseFragment;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.DeviceUseLogList;
import com.jxxx.rhtx.bean.HomeInfoBean;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.view.adapter.HomeBelowAdapter;
import com.jxxx.rhtx.view.adapter.HomeCenAdapter;

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

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initView() {

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
                            }
                            List<HomeInfoBean.HistroyDeviceBean> histroyDevice = result.getData().getHistroyDevice();
                            mRvSbList.setAdapter(new HomeBelowAdapter(histroyDevice));
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
                                tv_2.setVisibility(View.GONE);
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

    }

    @OnClick({R.id.tv_updata_info, R.id.tv_sb_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_updata_info:
                break;
            case R.id.tv_sb_select:
                break;
        }
    }
}



