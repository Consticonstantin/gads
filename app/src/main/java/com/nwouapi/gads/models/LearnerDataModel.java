package com.nwouapi.gads.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LearnerDataModel {
    public String name;
    public  int hours;
    public String country;
    public  String badgeUrl;
    public LearnerDataModel(String name, int hours, String country, String badgeUrl){
        this.badgeUrl = badgeUrl;
        this.country = country;
        this.hours = hours;
        this.name = name;
    }

    public LearnerDataModel(JSONObject json) throws JSONException {
        this.badgeUrl = (String) json.getString("badgeUrl");
        this.name = (String) json.getString("name");
        this.hours = (int) json.getInt("hours");
        this.country = (String) json.getString("country");
    }
}
