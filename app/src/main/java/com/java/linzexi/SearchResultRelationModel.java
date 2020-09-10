package com.java.linzexi;

public class SearchResultRelationModel {
    String url;
    String relation;
    String label;
    Boolean forward;
    public SearchResultRelationModel(String _relation, String _url, String _label, Boolean _forward){
        url = _url;
        relation = _relation;
        label = _label;
        forward = _forward;
    }
}
