package com.java.linzexi;

import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.java.linzexi.JSONHandler.RemoteJSONFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EpiDataViewModel extends ViewModel {
    List<String> keyList = new ArrayList<>();
    List<EpiDataModel> epiDataModelChinese = new ArrayList<>();
    List<EpiDataModel> epiDataModelInternational = new ArrayList<>();

    private int calc(String s) {
        String finalStr = s.replace("|", "");
        return s.length() - finalStr.length();
    }

    public String debug() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < epiDataModelInternational.size(); i++) {
            s.append(epiDataModelInternational.get(i).place);
            s.append(" ");
        }
        return s.toString();
    }

    private void loadJSONData(String jsonFileString) {
        try {
            JSONObject obj = new JSONObject(jsonFileString);
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                keyList.add(String.valueOf(keys.next()));
            }
            for (int i = 0; i < keyList.size(); i++) {
                if (keyList.get(i).length() >= 6 && keyList.get(i).substring(0, 6).equals("China|") && calc(keyList.get(i)) == 1) {
                    JSONArray ChineseArr = obj.getJSONObject(keyList.get(i)).getJSONArray("data");
                    List<EpiDataDayModel> dayList = new ArrayList<>();
                    for (int j = 0; j < ChineseArr.length(); j++) {
                        String confirmed_s = ChineseArr.getJSONArray(j).getString(0);
                        int confirmed = 0;
                        if (!confirmed_s.equals("null")) {
                            confirmed = Integer.parseInt(confirmed_s);
                        }
                        String suspected_s = ChineseArr.getJSONArray(j).getString(1);
                        int suspected = 0;
                        if (!suspected_s.equals("null")) {
                            suspected = Integer.parseInt(suspected_s);
                        }
                        String cured_s = ChineseArr.getJSONArray(j).getString(2);
                        int cured = 0;
                        if (!cured_s.equals("null")) {
                            cured = Integer.parseInt(cured_s);
                        }
                        String dead_s = ChineseArr.getJSONArray(j).getString(3);
                        int dead = 0;
                        if (!dead_s.equals("null")) {
                            dead = Integer.parseInt(dead_s);
                        }
                        dayList.add(new EpiDataDayModel(confirmed, suspected, cured, dead));
                    }
                    epiDataModelChinese.add(new EpiDataModel(keyList.get(i).substring(6, keyList.get(i).length()), dayList));
                } else if (!keyList.get(i).contains("|")) {
                    JSONArray InternationalArr = obj.getJSONObject(keyList.get(i)).getJSONArray("data");
                    List<EpiDataDayModel> dayList = new ArrayList<>();
                    for (int j = 0; j < InternationalArr.length(); j++) {
                        String confirmed_s = InternationalArr.getJSONArray(j).getString(0);
                        int confirmed = 0;
                        if (!confirmed_s.equals("null")) {
                            confirmed = Integer.parseInt(confirmed_s);
                        }
                        String suspected_s = InternationalArr.getJSONArray(j).getString(1);
                        int suspected = 0;
                        if (!suspected_s.equals("null")) {
                            suspected = Integer.parseInt(suspected_s);
                        }
                        String cured_s = InternationalArr.getJSONArray(j).getString(2);
                        int cured = 0;
                        if (!cured_s.equals("null")) {
                            cured = Integer.parseInt(cured_s);
                        }
                        String dead_s = InternationalArr.getJSONArray(j).getString(3);
                        int dead = 0;
                        if (!dead_s.equals("null")) {
                            dead = Integer.parseInt(dead_s);
                        }
                        dayList.add(new EpiDataDayModel(confirmed, suspected, cured, dead));
                    }
                    epiDataModelInternational.add(new EpiDataModel(keyList.get(i), dayList));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public EpiDataViewModel() {
        loadJSONData(new RemoteJSONFetcher("https://covid-dashboard.aminer.cn/api/dist/epidemic.json").getJSONFileString());
        sortData();
    }

    private void sortData() {
        Collections.sort(epiDataModelChinese);
        Collections.sort(epiDataModelInternational);
    }

    public BarData findData(int place, int type, BarChart bar) {
        List<IBarDataSet> sets = new ArrayList<>();
        List<BarEntry> values = new ArrayList<>();
        final List<String> label_name = new ArrayList<>();
        int length = 0;
        int count = 1;
        if (place == 0) {
            length = epiDataModelInternational.size();
            for (int i = 0; i < epiDataModelInternational.size(); i++) {
                if (epiDataModelInternational.get(i).place.equals("United States of America"))
                    label_name.add("USA");
                else
                    label_name.add(epiDataModelInternational.get(i).place);
                values.add(new BarEntry(count++, epiDataModelInternational.get(i).getLastData().getData(type)));
            }
        } else {
            length = epiDataModelChinese.size();
            for (int i = 0; i < epiDataModelChinese.size(); i++) {
                label_name.add(epiDataModelChinese.get(i).place);
                values.add(new BarEntry(count++, epiDataModelChinese.get(i).getLastData().getData(type)));
            }
        }
        XAxis xAxis = bar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // 设置x轴显示在下方，默认在上方
        xAxis.setDrawGridLines(false); // 将此设置为true，绘制该轴的网格线。
        xAxis.setLabelCount(length);  // 设置x轴上的标签个数
        xAxis.setGranularity(1);
        // 设置x轴显示的值的格式
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value < label_name.size()) {
                    return label_name.get((int) value);
                } else {
                    return "";
                }
            }
        });

        YAxis yAxis_left = bar.getAxisLeft();
        yAxis_left.setAxisMinimum(0f);  // 设置y轴的最小值
        yAxis_left.setValueFormatter(new LargeValueFormatter());
        BarDataSet barDataSet = null;
        if (place == 0) {
            if (type == 0)
                barDataSet = new BarDataSet(values, "各国确诊人数");
            else if (type == 2)
                barDataSet = new BarDataSet(values, "各国治愈人数");
            else if (type == 3)
                barDataSet = new BarDataSet(values, "各国死亡人数");
        } else {
            if (type == 0)
                barDataSet = new BarDataSet(values, "各地确诊人数");
            else if (type == 2)
                barDataSet = new BarDataSet(values, "各地治愈人数");
            else if (type == 3)
                barDataSet = new BarDataSet(values, "各地死亡人数");
        }

//        barDataSet.setColor(Color.parseColor(color[0]));
        barDataSet.setDrawValues(true);
        sets.add(barDataSet);
        return new BarData(sets);
    }

}