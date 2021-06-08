package com.jxxx.rhtx.view.adapter;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.bean.HistroyDeviceLogBean;
import com.jxxx.rhtx.bean.HomeInfoBean;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.SharedUtils;

import java.util.List;

public class SetUserDeviceAdapter extends BaseQuickAdapter<HistroyDeviceLogBean.HistroyDeviceBean, BaseViewHolder> {

    public SetUserDeviceAdapter(@Nullable List<HistroyDeviceLogBean.HistroyDeviceBean> data) {
        super(R.layout.item_user_device_log, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HistroyDeviceLogBean.HistroyDeviceBean item) {
        helper.setText(R.id.tv_name,item.getTypeStr());
        helper.addOnClickListener(R.id.iv_select);
        helper.setImageResource(R.id.iv_select,R.mipmap.ic_select_dev_no);
        if(SharedUtils.singleton().get(ConstValues.DEFAULT_SHOW, 0)==item.getId()){
            helper.setImageResource(R.id.iv_select,R.mipmap.ic_select_dev_yes);
        }
    }
}
