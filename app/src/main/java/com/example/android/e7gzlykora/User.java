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
    public String password;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String mobile, String password) {
        this.name = name;
        this.mobile = mobile;
        this.password = password;

    }


    public String getMobile() {
        return mobile;
    }
}





