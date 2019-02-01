package com.example.android.e7gzlykora;

import com.google.firebase.database.IgnoreExtraProperties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class owner extends ArrayList <owner> {

    public String name;
    public String mobile;
    public String fieldname;
    public String address;
    public String cost;
    public String zone1;
    public String zone2;
    private Class thisClass;


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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setZone1(String zone1) {
        this.zone1 = zone1;
    }

    public void setZone2(String zone2) {
        this.zone2 = zone2;
    }

    public void setThisClass(Class thisClass) {
        this.thisClass = thisClass;
    }

    public String getFieldname() {
        return fieldname;
        }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
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




