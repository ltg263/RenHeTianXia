package com.jxxx.rhtx.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.bean.DeviceTypeListAll;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.view.activity.DeviceLinkActivity;

import java.util.List;

public class HomeTwoAdapter extends BaseQuickAdapter<DeviceTypeListAll, BaseViewHolder> {


    public HomeTwoAdapter(@Nullable List<DeviceTypeListAll> data) {
        super(R.layout.item_home_two, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, DeviceTypeListAll item) {
        helper.setText(R.id.tv_sb_type,item.getDeviceName());
        RecyclerView rv_list = helper.getView(R.id.rv_list);
        rv_list.setAdapter(new HomeTwoAdapterB(item.getChildren()));

    }

}

class HomeTwoAdapterB extends BaseQuickAdapter<DeviceTypeListAll.ChildrenBean, BaseViewHolder> {

    int[] str = {1,2,8};
    public HomeTwoAdapterB(@Nullable List<DeviceTypeListAll.ChildrenBean> data) {
        super(R.layout.item_home_two_b, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, DeviceTypeListAll.ChildrenBean item) {
        GlideImgLoader.loadImageAndDefault(mContext,item.getImgUrl(),helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_name,item.getDeviceName());
        helper.getView(R.id.ll_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0;i<str.length;i++){
                    if(str[i]==item.getId()){
                        Intent mIntent = new Intent(mContext, DeviceLinkActivity.class);
                        mIntent.putExtra("id",item.getId());
                        mContext.startActivity(mIntent);
                        return;
                    }
                }
                ToastUtil.showToast("??????????????????");
            }
        });
    }
}

