package com.jxxx.rhtx.view.adapter;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.bean.HomeInfoBean;
import com.jxxx.rhtx.utils.GlideImgLoader;

import java.util.List;

public class HomeBelowAdapter extends BaseQuickAdapter<HomeInfoBean.HistroyDeviceBean, BaseViewHolder> {


    public HomeBelowAdapter(@Nullable List<HomeInfoBean.HistroyDeviceBean> data) {
        super(R.layout.item_home_below, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeInfoBean.HistroyDeviceBean item) {
        GlideImgLoader.loadImageAndDefault(mContext,item.getImgUrl(),helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_sb_name,item.getTypeStr()).setText(R.id.tv_sb_st,item.getDeviceName());
    }
}
