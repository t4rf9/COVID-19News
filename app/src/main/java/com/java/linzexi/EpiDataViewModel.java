package com.java.linzexi;

import androidx.lifecycle.ViewModel;

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
    List<String> keyListChinese = new ArrayList<>();
    List<String> keyListInternational = new ArrayList<>();
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
                        dayList.add(new EpiDataDayModel(confirmed, cured, dead));
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
                        String cured_s = InternationalArr.getJSONArray(j).getString(1);
                        int cured = 0;
                        if (!cured_s.equals("null")) {
                            cured = Integer.parseInt(cured_s);
                        }
                        String dead_s = InternationalArr.getJSONArray(j).getString(2);
                        int dead = 0;
                        if (!dead_s.equals("null")) {
                            dead = Integer.parseInt(dead_s);
                        }
                        dayList.add(new EpiDataDayModel(confirmed, cured, dead));
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

    public void updateData(int type, EpiDataXRecyclerViewAdapter adapter) {
        if (type == 0) {
            adapter.upDateList(epiDataModelInternational);
        } else {
            adapter.upDateList(epiDataModelChinese);
        }
    }
}