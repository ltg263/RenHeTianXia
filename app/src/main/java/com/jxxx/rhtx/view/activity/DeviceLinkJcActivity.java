package com.jxxx.rhtx.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.utils.GlideImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceLinkJcActivity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.tv_2)
    TextView mTv2;
    @BindView(R.id.tv_3)
    TextView mTv3;
    @BindView(R.id.tv_4)
    TextView mTv4;
    @BindView(R.id.tv_5)
    TextView mTv5;
    @BindView(R.id.tv_6)
    TextView mTv6;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.tv_bnt)
    TextView tv_bnt;
    @BindView(R.id.ll_jiao_cheng)
    LinearLayout ll_jiao_cheng;
    @BindView(R.id.ll_lianjie)
    LinearLayout ll_lianjie;

    int id;
    @Override
    public int intiLayout() {
        return R.layout.activity_device_link_jc;
    }

    @Override
    public void initView() {
        id = getIntent().getIntExtra("id", 0);
        setToolbar(myToolbar, "设备链接", true);
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.tv_tiaoguo, R.id.tv_bnt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tiaoguo:
                ll_jiao_cheng.setVisibility(View.GONE);
                ll_lianjie.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_bnt:
                showJc();
                break;
        }
    }

    private void showJc() {
        String str = tv_bnt.getText().toString();
        if(str.equals("确认完成上述操作")){
            mTv1.setText("√");
            mTv2.setTextColor(getResources().getColor(R.color.white));
            mTv2.setBackground(getResources().getDrawable(R.drawable.shape_radius_th));
            mTv4.setText("请打开手机蓝牙功能");
            mTv5.setText("若手机已处于下方状态，直接点击确认下一步");
            mTv6.setText("1：手机打开“设置”\n2：点击“其他网络与连接”\n3：打开“蓝牙”开关");
            tv_bnt.setText("下一步");
            GlideImageLoader.loadImage(this,R.mipmap.ic_jc_2,mIv);
        }else{
            ll_jiao_cheng.setVisibility(View.GONE);
            ll_lianjie.setVisibility(View.VISIBLE);
        }
    }
}
