package com.nwouapi.gads.models;

import org.json.JSONException;
import org.json.JSONObject;

public class SkillDataModel {
    public String name;
    public  int score;
    public String country;
    public  String badgeUrl;
    public SkillDataModel(String name, int score, String country, String badgeUrl){
        this.badgeUrl = badgeUrl;
        this.country = country;
        this.score = score;
        this.name = name;
    }

    public SkillDataModel(JSONObject json) throws JSONException {
        this.badgeUrl = (String) json.getString("badgeUrl");
        this.name = (String) json.getString("name");
        this.score = (int) json.getInt("score");
        this.country = (String) json.getString("country");
    }
}
