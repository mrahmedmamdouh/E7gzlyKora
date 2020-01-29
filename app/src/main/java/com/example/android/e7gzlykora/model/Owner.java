package com.example.android.e7gzlykora.model;


/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

public class Owner  {

    private static Owner owner ;


    public String Name;
    public String Mobile;
    public String FieldName;
    public String Address;
    public double Cost;
    public String Zone1;
    public String Zone2;
    public String OwnerGUID;

    public Owner() {
    }

    public Owner(String name, String mobile, String fieldName, String address, double cost, String zone1, String zone2, String ownerGUID) {
        Name = name;
        Mobile = mobile;
        FieldName = fieldName;
        Address = address;
        Cost = cost;
        Zone1 = zone1;
        Zone2 = zone2;
        OwnerGUID = ownerGUID;
    }

    public static Owner getOwner() {
        return owner;
    }

    public static void setOwner(Owner owner) {
        Owner.owner = owner;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public String getZone1() {
        return Zone1;
    }

    public void setZone1(String zone1) {
        Zone1 = zone1;
    }

    public String getZone2() {
        return Zone2;
    }

    public void setZone2(String zone2) {
        Zone2 = zone2;
    }

    public String getOwnerGUID() {
        return OwnerGUID;
    }

    public void setOwnerGUID(String ownerGUID) {
        OwnerGUID = ownerGUID;
    }

    public static Owner getInstance() {
        if (owner == null) {
            owner = new Owner();
            return owner;
        } else {
            return owner;
        }
    }

}




