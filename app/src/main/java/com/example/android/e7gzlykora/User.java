package com.example.android.e7gzlykora;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class User {

    public String Area ;
    public String Date ;
    public String Zone ;
    public String FromTime ;
    public String ToTime ;
    public String UserGUID ;
    public String SingleTime;
    public String WeeklyTime;


    public User(){


    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }

    public String getSingleTime() {
        return SingleTime;
    }

    public void setSingleTime(String singleTime) {
        SingleTime = singleTime;
    }

    public String getWeeklyTime() {
        return WeeklyTime;
    }

    public void setWeeklyTime(String weeklyTime) {
        WeeklyTime = weeklyTime;
    }

    public User(String area, String date, String zone, String from, String to, String userGUID, String SingleTime, String WeeklyTime) {
        Area = area;
        Date = date;
        Zone = zone;
        FromTime = from;
        ToTime = to;
        UserGUID = userGUID;
        this.SingleTime=SingleTime;
        this.WeeklyTime=WeeklyTime;
    }





    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getFrom() {
        return FromTime;
    }

    public void setFrom(String from) {
        FromTime = from;
    }

    public String getTo() {
        return ToTime;
    }

    public void setTo(String to) {
        ToTime = to;
    }

    public String getUserGUID() {
        return UserGUID;
    }

    public void setUserGUID(String userGUID) {
        UserGUID = userGUID;
    }
}



