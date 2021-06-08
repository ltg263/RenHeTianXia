package com.jxxx.rhtx.view.activity;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.jxxx.rhtx.MainActivity;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.lanya.BluetoothLjUtils;
import com.jxxx.rhtx.utils.SharedUtils;
import com.jxxx.rhtx.utils.view.DialogUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MineSetActivity extends BaseActivity {

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @OnClick({R.id.ll1, R.id.ll2, R.id.ll3, R.id.tv_tui})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                startActivity(new Intent(this, LoginWjmmActivity.class));
                break;
            case R.id.ll2:
                break;
            case R.id.ll3:
                break;
            case R.id.tv_tui:
                DialogUtils.showDialogHint(this, "确定要退出登录吗？", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        SharedUtils.singleton().clear();
                        MainApplication.getContext().AppExit();
                        LoginActivity.startActivityIntent(MineSetActivity.this);

                    }
                });
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
        setToolbar(myToolbar, "设置中心", true);
    }

    @Override
    public void initData() {

    }
}
