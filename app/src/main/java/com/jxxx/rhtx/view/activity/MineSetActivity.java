package com.jxxx.rhtx.view.activity;

import android.content.Intent;
import android.view.View;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.utils.SharedUtils;

import butterknife.OnClick;

public class MineSetActivity extends BaseActivity {

    @OnClick({R.id.ll1, R.id.ll2, R.id.ll3, R.id.tv_tui,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                startActivity(new Intent(this, LoginWjmmActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll2:
                break;
            case R.id.ll3:
                break;
            case R.id.tv_tui:
                SharedUtils.singleton().clear();
                MainApplication.getContext().AppExit();
                LoginActivity.startActivityIntent(this);
                break;
        }
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mine_set;
    }

    @Override
    public void initView() {

        MainApplication.addActivity(this);
    }

    @Override
    public void initData() {

    }
}
