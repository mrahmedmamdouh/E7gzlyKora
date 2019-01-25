package com.example.android.e7gzlykora;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class User {

    public String name;
    public String mobile;
    public String fromtime;
    public String totime;
    public String zone3;
    public String zone4;
    public String singletime;
    public String weeklytime;
    public String x;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)


    public User(String fromtime, String totime, String zone3, String zone4, String name, String mobile, String x) {
        this.name = name;
        this.mobile = mobile;
        this.fromtime = fromtime;
        this.totime = totime;
        this.zone3 = zone3;
        this.zone4 = zone4;
        this.x = x;


    }

    public User() {
    }
    public String getMobile() {
        return mobile;
    }
}





