package com.java.linzexi;

import androidx.lifecycle.ViewModel;

import com.java.linzexi.JSONHandler.RemoteJSONFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScholarsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    List<ScholarsModel> list = new ArrayList<>();

    private void loadJSONData(String s){
        try{
            JSONArray arr = new JSONObject(s).getJSONArray("data");
            for(int i = 0; i < arr.length(); i ++){
                String avatar = arr.getJSONObject(i).getString("avatar");
                String id = arr.getJSONObject(i).getString("id");
                String name = arr.getJSONObject(i).getString("name");
                String name_zh = arr.getJSONObject(i).getString("name_zh");
                int activity = arr.getJSONObject(i).getJSONObject("indices").getInt("activity");
                String position = arr.getJSONObject(i).getJSONObject("profile").getString("position");
                String introduction = arr.getJSONObject(i).getJSONObject("profile").getString("bio");
                if(arr.getJSONObject(i).getJSONObject("profile").has("edu"))
                      introduction += arr.getJSONObject(i).getJSONObject("profile").getString("edu");
                Boolean is_passedaway = arr.getJSONObject(i).getBoolean("is_passedaway");
                list.add(new ScholarsModel(avatar, id, name, name_zh, activity, position, introduction, is_passedaway));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public ScholarsViewModel(){
        loadJSONData(new RemoteJSONFetcher("https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2").getJSONFileString());
        Collections.sort(list);
    }
}