package com.jxxx.rhtx.view.fragment;


import androidx.recyclerview.widget.RecyclerView;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.base.BaseFragment;
import com.jxxx.rhtx.base.ResultList;
import com.jxxx.rhtx.bean.DeviceTypeListAll;
import com.jxxx.rhtx.view.adapter.HomeTwoAdapter;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeTwoFragment extends BaseFragment {


    @BindView(R.id.rv_two_list)
    RecyclerView mRvTwoList;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_two;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        RetrofitUtil.getInstance().apiService()
                .getDeviceTypeListAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultList<DeviceTypeListAll>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultList<DeviceTypeListAll> result) {
                        if(result.getStatus()==0){
                            mRvTwoList.setAdapter(new HomeTwoAdapter(result.getData()));
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



