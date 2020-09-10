package com.java.linzexi;

import java.util.ArrayList;
import java.util.List;

public class EpiDataModel implements Comparable<EpiDataModel>{
    String place;
    List<EpiDataDayModel> dayList = new ArrayList<>();
    public EpiDataModel(String _place, List<EpiDataDayModel> l){
        place = _place;
        dayList.addAll(l);
    }

    public EpiDataDayModel getLastData(){
        return dayList.get(dayList.size() - 1);
    }

    @Override
    public int compareTo(EpiDataModel epiDataModel) {
        return epiDataModel.getLastData().confirmed - this.getLastData().confirmed;
    }
}

