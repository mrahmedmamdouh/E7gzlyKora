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

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User(String name, String mobile, String fieldname, String address, String cost, String zone1, String zone2) {
    }

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }


    public String getMobile() {
        return mobile;
    }
}





