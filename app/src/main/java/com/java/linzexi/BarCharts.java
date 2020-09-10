package com.java.linzexi;

import android.graphics.Matrix;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

import org.jetbrains.annotations.NotNull;

public class BarCharts {
    /**
     * @param barChart  控件
     * @param barData   数据
     * @param isSlither 用来控制是否可以滑动
     */
    public void showBarChart(@NotNull BarChart barChart, BarData barData, boolean isSlither) {
        barChart.setTouchEnabled(isSlither);
        barChart.setDragEnabled(isSlither);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.setData(barData);
        barChart.setVisibleXRangeMaximum(5);
        if (isSlither) {
            barChart.invalidate();
            Matrix mMatrix = new Matrix();
            mMatrix.postScale(2f, 1f);
            barChart.getViewPortHandler().refresh(mMatrix, barChart, false);
            barChart.animateY(1000);
        } else {
            barChart.animateY(1000);
        }
    }
}

