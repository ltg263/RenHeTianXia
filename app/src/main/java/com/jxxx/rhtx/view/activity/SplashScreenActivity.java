package com.jxxx.rhtx.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.jxxx.rhtx.MainActivity;
import com.jxxx.rhtx.R;
import com.jxxx.rhtx.app.ConstValues;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.utils.SharedUtils;


public class SplashScreenActivity extends Activity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);
        new Handler().postDelayed(() -> startUi(), 2000);
    }

    private void startUi() {
        MainApplication.addActivity(this);
        if(SharedUtils.singleton().get(ConstValues.ISLOGIN,false)){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            LoginActivity.startActivityIntent(this);
        }
        finish();
    }


}
