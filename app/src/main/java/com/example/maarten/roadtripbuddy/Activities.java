package com.example.maarten.roadtripbuddy;

public class Activities {

    private int _id;
    private String _activityname;
    private String _cityname;

    public Activities(String activityName, String cityName) {
        this._activityname = activityName;
        this._cityname = cityName;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_activityname() {
        return _activityname;
    }
    public String get_cityname() {
        return _cityname;
    }

    public void set_activityname(String _activityname) {
        this._activityname = _activityname;
    }
    public void set_cityname(String _cityname) {
        this._cityname = _cityname;
    }

}
