package com.jxxx.rhtx.view.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.jxxx.rhtx.R;
import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.utils.StatusBarUtil;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class SetUserXbActivity extends BaseActivity {
    @BindView(R.id.date_picker)
    DatePicker mDatePicker;
    @BindView(R.id.ll_nan)
    RelativeLayout mLlNan;
    @BindView(R.id.ll_nv)
    RelativeLayout mLlNv;
    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.iv_2)
    ImageView mIv2;
    @BindView(R.id.tv_nan)
    TextView mTvNan;
    @BindView(R.id.tv_nv)
    TextView mTvNv;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    int sbType = 2;//2女
    String age = "";

    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    @OnClick({R.id.ll_nan, R.id.ll_nv, R.id.tv_go_xyb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_nan:
                if (sbType == 2) {
                    sbType = 1;
                    mLlNan.setBackground(getResources().getDrawable(R.drawable.btn_shape_theme));
                    mLlNv.setBackground(getResources().getDrawable(R.drawable.shape_fff_line_5));
                    mTvNan.setTextColor(getResources().getColor(R.color.color_ffffff));
                    mTvNv.setTextColor(getResources().getColor(R.color.color_999999));
                    mIv1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_nan_2));
                    mIv2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_nv_1));
                }
                break;
            case R.id.ll_nv:
                if (sbType == 1) {
                    sbType = 2;
                    mLlNan.setBackground(getResources().getDrawable(R.drawable.shape_fff_line_5));
                    mLlNv.setBackground(getResources().getDrawable(R.drawable.btn_shape_theme));
                    mTvNan.setTextColor(getResources().getColor(R.color.color_999999));
                    mTvNv.setTextColor(getResources().getColor(R.color.color_ffffff));
                    mIv1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_nan_1));
                    mIv2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_nv_2));
                }
                break;
            case R.id.tv_go_xyb:
                  SetUserInfoActivity.xb = sbType;
                  SetUserInfoActivity.rq = age;
                  finish();
//                String sex = sbType==2?"女":"男";
//                Intent mIntent = new Intent(SetUserXbActivity.this,SetUserSgActivity.class);
//                mIntent.putExtra("sex",sex);
//                mIntent.putExtra("age",age);
//                startActivity(mIntent);
                break;
        }
    }

    /**
     * 设置时间选择器的分割线颜色
     *
     * @param datePicker
     */
    private void setDatePickerDividerColor(DatePicker datePicker) {
        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);
        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(Color.parseColor("#E6E6E6")));//设置分割线颜色
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }


    @Override
    public int intiLayout() {
//        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
        return R.layout.activity_set_user_xb;
    }

    @Override
    public void initView() {
        MainApplication.addActivity(this);
        setToolbar(myToolbar, "修改信息");
        setDatePickerDividerColor(mDatePicker);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        age = year + "年" +monthOfYear + "月" + dayOfMonth + "日";
        mDatePicker.setMaxDate(System.currentTimeMillis());
        mDatePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                String mm = month + 1+"";
                if(month + 1<10){
                    mm = "0"+mm;
                }
                String dd = day+"";
                if(month + 1<10){
                    dd = "0"+day;
                }
                age=year + "-" + mm + "-" + dd;
//                try {
//                    age = StringUtil.getAge(parse(age))+"";
//                } catch (Exception e) {
//                    age = "0";
//                    e.printStackTrace();
//                }
//                Toast.makeText(SetUserXbActivity.this, "您选择的日期是：" + year + "年" + (month + 1) + "月" + day + "日!", Toast
//                        .LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {

    }
}
