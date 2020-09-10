package com.java.linzexi.JSONHandler;

import com.java.linzexi.AppExecutors;
import com.java.linzexi.database.NewsEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class AllNewsImporter {
    private static URL url;

    static {
        try {
            url = new URL("https://covid-dashboard.aminer.cn/api/dist/events.json");
        } catch (MalformedURLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static List<NewsEntity> getAllNewsList() {
        List<NewsEntity> list = new LinkedList<>();
        String jsonFileString = new RemoteJSONFetcher(url).getJSONFileString();
        try {
            JSONObject obj = new JSONObject(jsonFileString);
            JSONArray jsonArray = obj.getJSONArray("datas");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                String _id = object.getString("_id");
                String type = object.getString("type");
                String title = object.getString("title");
                String time = object.getString("time");

                list.add(new NewsEntity(false, _id, type, time, null, title, null));
            }
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }
}
