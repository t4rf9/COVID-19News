package com.java.linzexi.JSONHandler;

import com.java.linzexi.database.NewsEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class NewsEntityLoader {
    private static URL url;

    public static NewsEntity getNewsEntity(final String id) {
        try {
            url = new URL("https://covid-dashboard-api.aminer.cn/event/" + id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String jsonFileString = new RemoteJSONFetcher(url).getJSONFileString();
        NewsEntity res = null;
        if (jsonFileString == null) {
            return null;
        }
        try {
            JSONObject obj = new JSONObject(jsonFileString);
            JSONObject object = obj.getJSONObject("data");

            String _id = object.getString("_id");
            String type = object.getString("type");
            String time = object.getString("time").replaceAll("-", "/");
            String source = object.getString("source");
            String title = object.getString("title");
            String content = object.getString("content");

            res = new NewsEntity(true, _id, type, time, source, title, content);

        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }

        return res;
    }
}
