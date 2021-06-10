package com.jxxx.rhtx.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.HistroyDeviceLogBean;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.view.adapter.SetUserDeviceAdapter;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SetUserDeviceActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private SetUserDeviceAdapter mSetUserDeviceAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_set_use_device;
    }

    @Override
    public void initView() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSetUserDeviceAdapter = new SetUserDeviceAdapter(null);
        rv_list.setAdapter(mSetUserDeviceAdapter);
        mSetUserDeviceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(SharedUtils.singleton().get(ConstValues.DEFAULT_SHOW, 0)!=mSetUserDeviceAdapter.getData().get(position).getDeviceType()){
                    getDeviceDetails(mSetUserDeviceAdapter.getData().get(position).getDeviceType());
                }else{
                    getDeviceDetails(0);
                }
            }
        });
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
                            initData();
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
    @Override
    public void initData() {
        RetrofitUtil.getInstance().apiService()
                .getUseLogList(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<HistroyDeviceLogBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<HistroyDeviceLogBean> result) {
                        if (isDataInfoSucceed(result)) {
                            if(result.getData().getList()!=null && result.getData().getList().size()>0){
                                tv.setVisibility(View.GONE);
                                rv_list.setVisibility(View.VISIBLE);
                                mSetUserDeviceAdapter.setNewData(result.getData().getList());
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
