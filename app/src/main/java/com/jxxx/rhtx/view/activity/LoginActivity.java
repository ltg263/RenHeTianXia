package com.jxxx.rhtx.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.utils.view.MagicIndicatorUtils;
import com.jxxx.rhtx.view.fragment.LoginFragment;
import com.jxxx.rhtx.view.fragment.RegisterFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private static final String[] CHANNELS = new String[]{"登录", "注册"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    List<Fragment> fragments = new ArrayList<>();


    private void initVP() {
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }
        });
        mViewPager.setCurrentItem(0);
    }

    public static void startActivityIntent(Context mContext) {
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

        MainApplication.addActivity(this);
        initVP();
        MagicIndicatorUtils.initMagicIndicator6(this, mDataList, mMagicIndicator, mViewPager);
    }

    @Override
    public void initData() {

    }
}
