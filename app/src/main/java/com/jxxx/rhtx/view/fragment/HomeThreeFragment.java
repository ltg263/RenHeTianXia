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
import com.jxxx.rhtx.utils.view.DialogUtils;
import com.jxxx.rhtx.view.activity.MineSetActivity;
import com.jxxx.rhtx.view.activity.SetUserDeviceActivity;
import com.jxxx.rhtx.view.activity.SetUserInfoActivity;
import com.jxxx.rhtx.view.activity.SetUserWebActivity;

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
                startActivity(new Intent(getActivity(), SetUserDeviceActivity.class));
                break;
            case R.id.tv_bnt_2:
                startActivity(new Intent(getActivity(), SetUserInfoActivity.class));
                break;
            case R.id.tv_bnt_3:
                Intent mIntent = new Intent(getActivity(), SetUserWebActivity.class);
                mIntent.putExtra("title","帮助说明");
                mIntent.putExtra("content",SharedUtils.singleton().get(ConstValues.SB_HELP,""));
                startActivity(mIntent);
                break;
            case R.id.tv_bnt_4:
                Intent mIntent1 = new Intent(getActivity(), SetUserWebActivity.class);
                mIntent1.putExtra("title","联系方式");
                mIntent1.putExtra("content",SharedUtils.singleton().get(ConstValues.SB_CONTACT_WAY,""));
                startActivity(mIntent1);
                break;
            case R.id.tv_bnt_5:
                startActivity(new Intent(getActivity(), MineSetActivity.class));
                break;
            case R.id.tv_bnt_6:
                DialogUtils.showDialogHint(getActivity(), "企宸软件", true, null);
                break;
        }
    }
}



