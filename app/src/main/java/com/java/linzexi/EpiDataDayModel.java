package com.java.linzexi;

public class EpiDataDayModel {
    int confirmed;
    int suspected;
    int cured;
    int dead;

    public EpiDataDayModel(int _confirmed, int _suspected, int _cured, int _dead) {
        confirmed = _confirmed;
        suspected = _suspected;
        cured = _cured;
        dead = _dead;
    }
    public int getData(int type){
        if(type == 0)
            return confirmed;
        if(type == 1)
            return suspected;
        if(type == 2)
            return cured;
        return dead;
    }
}
