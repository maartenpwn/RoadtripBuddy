package com.example.maarten.roadtripbuddy;

public class Activities {

    private int _id;
    private String _activityname;

    //Added this empty constructor in lesson 50 in case we ever want to create the object and assign it later.
    public Activities(){

    }
    public Activities(String activityName) {
        this._activityname = activityName;
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

    public void set_activityname(String _activityname) {
        this._activityname = _activityname;
    }
}
