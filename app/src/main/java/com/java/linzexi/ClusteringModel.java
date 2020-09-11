package com.java.linzexi;

import java.util.ArrayList;
import java.util.List;

public class ClusteringModel {
    //List<String> keyWords = new ArrayList<>();
    String keyWords = "";
    List<String> events = new ArrayList<>();

    public ClusteringModel(List<String> keys, List<String> _events) {
        keyWords = keys.get(0);
        int m = keys.size() / 2;
        for (int i = 1; i < m; i++) {
            keyWords += "  " + keys.get(i);
        }
        keyWords += "\n" + keys.get(m);
        for (int i = m + 1; i < keys.size(); i++) {
            keyWords += "  " + keys.get(i);
        }
        events.addAll(_events);
    }

    public ClusteringModel(String keys, List<String> _events) {
        keyWords = keys;
        events.addAll(_events);
    }
}
