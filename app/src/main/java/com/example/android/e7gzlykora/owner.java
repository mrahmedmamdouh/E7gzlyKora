package com.example.android.e7gzlykora;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class owner {

    public String name;
    public String mobile;
    public String fieldname;
    public String address;
    public String cost;
    public String zone1;
    public String zone2;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public owner(){}


    public owner(String name, String mobile,String fieldname,String address, String cost, String zone1, String zone2) {
        this.name = name;
        this.mobile = mobile;
        this.fieldname = fieldname;
        this.address = address;
        this.cost = cost;
        this.zone1 = zone1;
        this.zone2 = zone2;

    }


    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

     public String getAddress() {
        return address;
        }

public String getFieldname() {
        return fieldname;
        }

public String getCost() {
        return cost;
        }

public String getZone1() {
        return zone1;
        }

public String getZone2() {
        return zone2;
        }

}




