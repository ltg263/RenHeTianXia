package com.jxxx.rhtx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jxxx.rhtx.api.RetrofitUtil;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.DeviceUseLogList;
import com.jxxx.rhtx.bean.LoginBean;
import com.jxxx.rhtx.utils.ToastUtil;
import com.jxxx.rhtx.view.adapter.HomeCenAdapter;
import com.jxxx.rhtx.view.fragment.HomeOneFragment;
import com.jxxx.rhtx.view.fragment.HomeThreeFragment;
import com.jxxx.rhtx.view.fragment.HomeTwoFragment;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bnv_home_navigation)
    BottomNavigationView mBnvHomeNavigation;
    private Fragment mFragment;
    private HomeTwoFragment mHomeTwoFragment;
    private HomeOneFragment mHomeOneFragment;
    private HomeThreeFragment mHomeThreeFragment;

    public BottomNavigationView getBnvHomeNavigation() {
        return mBnvHomeNavigation;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        initBottomBar();
    }

    @Override
    public void initData() {

    }
    private void initBottomBar() {
        openLocation();
        mHomeOneFragment = new HomeOneFragment();
        mHomeTwoFragment = new HomeTwoFragment();
        mHomeThreeFragment = new HomeThreeFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout, mHomeOneFragment).commit();

        mFragment = mHomeOneFragment;

        // ???????????????????????????
        mBnvHomeNavigation.setItemIconTintList(null);
        mBnvHomeNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_home_1:
                    switchFragment(mHomeOneFragment);
                    break;
                case R.id.menu_home_2:
                    switchFragment(mHomeTwoFragment);
                    break;
                case R.id.menu_home_3:
                    switchFragment(mHomeThreeFragment);
                    break;
            }
            return true;
        });
        mBnvHomeNavigation.setSelectedItemId(R.id.menu_home_1);

    }

    public void switchFragment(Fragment fragment) {
        //?????????????????????Fragment??????????????????Fragment
        if (mFragment != fragment) {
            if (!fragment.isAdded()) {
                //?????????????????????????????????Fragment?????????????????????Fragment?????????
                getSupportFragmentManager().beginTransaction().hide(mFragment).add(R.id.frameLayout, fragment).commit();
            } else {
                //??????????????????????????????????????????Fragment?????????????????????Fragment????????????
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????
     */
    private void openLocation() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//?????????????????????
            Log.w("requestCode:", "requestCode:" );
            //??????????????????,200????????????
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            Log.w("requestCode:", "requestCode-----:");
            //????????????
//            show(getActivity());
//            ZsnaviManager.getInstance(getActivity()).setOnLocationCallback(locationCallback);//??????????????????
//            ZsnaviManager.getInstance(getActivity()).startLocation();//???????????????????????????????????????????????????????????????????????????????????????????????????
        }

    }
    @Override
    public void onBackPressed() {
        ToastUtil.showToast("????????????????????????");
        if (isSlowDoubleClick()) {
            this.finish();
            System.exit(0);
        } else {

        }
    }
    private static long lastClickTime = 0;
    public static boolean isSlowDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 2000) {
            return true;
        }

        lastClickTime = time;

        return false;
    }


}