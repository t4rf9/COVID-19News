package com.java.linzexi;

public class ScholarsModel implements Comparable<ScholarsModel> {
    String avatar;
    String id;
    String name;
    String name_zh;
    int activity;//用来排序？
    String position;
    String introduction;
    Boolean is_passedaway;

    public ScholarsModel(String _avatar, String _id, String _name, String _name_zh, int _activity, String _position, String _introduction, Boolean _is_passedaway) {
        avatar = _avatar;
        id = _id;
        name = _name;
        name_zh = _name_zh;
        activity = _activity;
        position = _position;
        introduction = _introduction;
        is_passedaway = _is_passedaway;
    }

    @Override
    public int compareTo(ScholarsModel scholarsModel) {
        return scholarsModel.activity - this.activity;
    }
}
