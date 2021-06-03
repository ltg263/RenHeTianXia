package com.jxxx.rhtx.view.adapter;

import android.text.Html;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.bean.DeviceUseLogList;
import com.jxxx.rhtx.bean.HomeInfoBean;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.StringUtil;

import java.util.List;

public class HomeCenAdapter extends BaseQuickAdapter<DeviceUseLogList.ListBean, BaseViewHolder> {


    public HomeCenAdapter(@Nullable List<DeviceUseLogList.ListBean> data) {
        super(R.layout.item_home_cen, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, DeviceUseLogList.ListBean item) {
        GlideImgLoader.loadImageAndDefault(mContext,item.getImgUrl(),helper.getView(R.id.iv_icon));

        String sysj = "使用时间：<font color=\"#4A90E2\">"+StringUtil.getTimeToYMD(item.getStartTime(),"yyyy-MM-dd HH:mm:ss")+"</font>";
        String sysc = "使用时长：<font color=\"#4A90E2\">"+item.getUseDuration()+"</font>";
        helper.setText(R.id.tv_name,item.getDeviceName()+" "+item.getSortName()).setText(R.id.tv_adrrass,item.getMACAddress())
        .setText(R.id.tv_end_1,"暂无").setText(R.id.tv_end_2,"训练量")
                .setText(R.id.tv_sysj, Html.fromHtml(sysj))
                .setText(R.id.tv_sysc, Html.fromHtml(sysc));
    }
}
