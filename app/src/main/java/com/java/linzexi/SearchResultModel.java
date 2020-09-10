package com.java.linzexi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultModel {
    String name;
    String url;
    String wikiInfo;
    String img;
    String properties;
    List<SearchResultRelationModel> srl = new ArrayList<>();
    public SearchResultModel(String _name, String _url, String enwiki, String baidu, String zhwiki, String _properties, JSONArray arr, String _img){
        name = _name;
        url = _url;
        wikiInfo = "";
        if(!enwiki.equals("")){
            wikiInfo = enwiki;
        }
        else if(!baidu.equals("")) {
            wikiInfo = baidu;
        }
        else if(!zhwiki.equals("")){
            wikiInfo = zhwiki;
        }
        properties = _properties;
        img = _img;
        try{
            for(int i = 0; i < arr.length(); i ++){
                JSONObject ob = arr.getJSONObject(i);
                String relationOB = ob.getString("relation");
                String urlOB = ob.getString("url");
                String labelOB = ob.getString("label");
                Boolean forwardOB = ob.getBoolean("forward");
                srl.add(new SearchResultRelationModel(relationOB, urlOB, labelOB, forwardOB));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
