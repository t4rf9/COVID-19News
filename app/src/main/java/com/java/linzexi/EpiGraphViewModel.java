package com.java.linzexi;

import androidx.lifecycle.ViewModel;

import com.java.linzexi.JSONHandler.RemoteJSONFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EpiGraphViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public EpiGraphViewModel(){

    }

    public List<SearchResultModel> getSearchResult(String search){
        List<SearchResultModel> srl = new ArrayList<>();
        String getResult = new RemoteJSONFetcher("https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=" + search).getJSONFileString();
        try{
            JSONObject obj = new JSONObject(getResult);
            JSONArray arr = obj.getJSONArray("data");
            for(int i = 0; i < arr.length(); i ++){
                JSONObject ob = arr.getJSONObject(i);
                String name = ob.getString("label");
                String url = ob.getString("url");
                String img = ob.getString("img");
                JSONObject ob_in = ob.getJSONObject("abstractInfo");
                String enwiki = ob_in.getString("enwiki");
                String baidu = ob_in.getString("baidu");
                String zhwiki = ob_in.getString("zhwiki");
                JSONObject ob_COVID = ob_in.getJSONObject("COVID");
                String properties = ob_COVID.getString("properties");
                JSONArray arr_COVID = ob_COVID.getJSONArray("relations");
                srl.add(new SearchResultModel(name, url, enwiki, baidu, zhwiki, properties, arr_COVID, img));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return srl;
    }
}