package com.jxxx.rhtx.view.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.DeviceUseLogList;
import com.jxxx.rhtx.view.adapter.HomeCenAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceHistroyActivity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.rv_sbls_list)
    RecyclerView mRvSblsList;
    @Override
    public int intiLayout() {
        return R.layout.activity_device_histroy;
    }

    @Override
    public void initView() {
        setToolbar(myToolbar, "历史设备", true);
    }

    @Override
    public void initData() {
        RetrofitUtil.getInstance().apiService()
                .getDeviceUseLogList(100)
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
    }
}
