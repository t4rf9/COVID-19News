package com.java.linzexi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;

import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClusteringViewModel extends ViewModel {
    List<ClusteringModel> list = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    Context mContext = null;
    String JSONstring;

    public String getJSON() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = mContext.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("clusterK_kwV_eventV.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void loadJSON() {
        try {
            JSONArray arr = new JSONArray(JSONstring);
            for (int i = 0; i < arr.length(); i++) {
                JSONArray ar = arr.getJSONArray(i);
                JSONArray a = ar.getJSONArray(0);
                List<String> kw = new ArrayList<>();
                for (int j = 0; j < a.length(); j++)
                    kw.add(a.getString(j));
                JSONArray b = ar.getJSONArray(1);
                List<String> ev = new ArrayList<>();
                for (int j = 0; j < b.length(); j++)
                    ev.add(b.getString(j));
                list.add(new ClusteringModel(kw, ev));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public List<ClusteringModel> getList() {
        return list;
    }

    public ClusteringViewModel() {


    }

    public void run(Context m) {
        mContext = m;
        JSONstring = getJSON();
        loadJSON();
    }
}
