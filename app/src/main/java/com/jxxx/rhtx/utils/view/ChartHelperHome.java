package com.jxxx.rhtx.utils.view;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.jxxx.rhtx.utils.StringUtil;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * create by hj on 2020/4/25
 **/
public class ChartHelperHome {

    private static int maxCount = 9; //集合最大存储数量

    public static void addEntry(List<Entry> mData, LineChart lineChart, float yValues) {
        if (lineChart != null&& lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            int size = mData.size();
            if (size == 0) {
                Entry entry = new Entry(maxCount, yValues);
                mData.add(entry);
            } else {
                boolean needRemove = false;
                for (Entry e : mData) {
                    float x = e.getX() - 1;
                    if (x < 0) {
                        needRemove = true;
                    }
                    e.setX(x);
                }
                if (needRemove) {
                    mData.remove(0);
                }
                Entry entry = new Entry(maxCount, yValues);
                mData.add(entry);
            }
            LineData lineData = new LineData();
            lineData.addDataSet(getSet(mData));
            lineData.addDataSet(getSet(mData));
            lineData.setDrawValues(false);
            lineChart.setData(lineData);
            lineChart.invalidate();
        }
    }

    public static void initChart(List<Entry> mData, LineChart lineChart, long maxYValue) {
        initChart(mData,lineChart,maxYValue,null,null);
    }

    public static void initChart(List<Entry> mData, LineChart lineChart, long maxYValue,String color1,String color2) {

        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);

        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setAxisMinimum(0);
        axisLeft.setLabelCount(5);
        if(maxYValue>0){
            axisLeft.setAxisMaximum(maxYValue);
        }
        axisLeft.setGridColor(Color.parseColor("#ffffff"));
        axisLeft.setTextColor(Color.parseColor("#999999"));
        axisLeft.setAxisLineColor(Color.parseColor("#00000000"));

        axisLeft.setDrawLabels(false);//不显示数值
        if(StringUtil.isNotBlank(color1)){
            axisLeft.setDrawLabels(true);//不显示数值
            // X轴可以缩放，Y轴不能缩放
            lineChart.setScaleXEnabled(true);
            lineChart.setScaleYEnabled(false);
            // 可以拖动，而不影响缩放比例
            lineChart.setDragEnabled(true);
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);//不显示数值
        if(StringUtil.isNotBlank(color1)){
            xAxis.setDrawLabels(true);//不显示数值
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(maxCount);
        xAxis.setLabelCount(maxCount);
        xAxis.setTextColor(Color.parseColor("#333333"));
        Collections.sort(mData, new EntryXComparator());
        LineData lineData = new LineData(getSet(mData));


        lineData.setDrawValues(false);//不显示数值
        if(StringUtil.isNotBlank(color1)){
            lineData.setDrawValues(true);//不显示数值
        }

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private static LineDataSet getSet(List<Entry> mData) {
        int valueColor = Color.parseColor("#999999");
        LineDataSet set = new LineDataSet(mData, "");
        set.setDrawFilled(true);
        set.setFillColor(valueColor);
        set.setColor(Color.parseColor("#ffffff"));
        set.setValueTextColor(valueColor);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        return set;
    }
}