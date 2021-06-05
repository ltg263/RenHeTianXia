package com.jxxx.rhtx.view.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.base.BaseFragment;
import com.jxxx.rhtx.utils.GlideImgLoader;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.view.activity.LoginActivity;
import com.jxxx.rhtx.view.activity.SetUserXbActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeThreeFragment extends BaseFragment {


    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.iv_head)
    ImageView mIvHead;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView() {
        mTvUserName.setText(SharedUtils.singleton().get(ConstValues.USER_NAME,""));
        GlideImgLoader.loadImageViewWithCirclr(getActivity(),
                SharedUtils.singleton().get(ConstValues.USER_AVATAR,""),  mIvHead);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_bnt_1, R.id.tv_bnt_2, R.id.tv_bnt_3, R.id.tv_bnt_4, R.id.tv_bnt_5, R.id.tv_bnt_6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bnt_1:
                LoginActivity.startActivityIntent(getActivity());
                break;
            case R.id.tv_bnt_2:
                startActivity(new Intent(getActivity(),SetUserXbActivity.class));
                break;
            case R.id.tv_bnt_3:
                break;
            case R.id.tv_bnt_4:
                break;
            case R.id.tv_bnt_5:
                break;
            case R.id.tv_bnt_6:
                break;
        }
    }
}



