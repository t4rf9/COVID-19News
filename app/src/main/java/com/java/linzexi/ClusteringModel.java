package com.java.linzexi;

import java.util.ArrayList;
import java.util.List;

public class ClusteringModel {
    //List<String> keyWords = new ArrayList<>();
    String keyWords = "";
    List<String> events = new ArrayList<>();

    public ClusteringModel(List<String> keys, List<String> _events) {
        for (int i = 0; i < keys.size(); i++) {
            keyWords += keys.get(i) + ",";
        }
        events.addAll(_events);
    }

    public ClusteringModel(String keys, List<String> _events) {
        keyWords = keys;
        events.addAll(_events);
    }
}
