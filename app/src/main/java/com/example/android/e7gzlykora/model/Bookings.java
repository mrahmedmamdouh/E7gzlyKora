package com.example.android.e7gzlykora.model;

public class Bookings {

    private static Bookings bookings;
    String OwnerName;
    String OwnerMobile;
    String OwnerField;
    String DateDetails;
    String DateTypes;
    String BookingTimeFrom;
    String BookingTimeTo;
    String UserName;
    String NameUser;
    String UserMobile;
    String Zone1;
    String Zone2;


    public Bookings() {
    }

    public Bookings(String ownerName, String ownerMobile, String ownerField, String dateDetails, String dateTypes, String bookingTimeFrom, String bookingTimeTo, String userName, String nameUser, String userMobile, String zone1, String zone2) {
        OwnerName = ownerName;
        OwnerMobile = ownerMobile;
        OwnerField = ownerField;
        DateDetails = dateDetails;
        DateTypes = dateTypes;
        BookingTimeFrom = bookingTimeFrom;
        BookingTimeTo = bookingTimeTo;
        UserName = userName;
        NameUser = nameUser;
        UserMobile = userMobile;
        Zone1 = zone1;
        Zone2 = zone2;
    }

    public static Bookings getBookings() {
        return bookings;
    }

    public static void setBookings(Bookings bookings) {
        Bookings.bookings = bookings;
    }

    public String getBookingTimeFrom() {
        return BookingTimeFrom;
    }

    public void setBookingTimeFrom(String bookingTimeFrom) {
        BookingTimeFrom = bookingTimeFrom;
    }

    public String getBookingTimeTo() {
        return BookingTimeTo;
    }

    public void setBookingTimeTo(String bookingTimeTo) {
        BookingTimeTo = bookingTimeTo;
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

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getOwnerMobile() {
        return OwnerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        OwnerMobile = ownerMobile;
    }

    public String getOwnerField() {
        return OwnerField;
    }

    public void setOwnerField(String ownerField) {
        OwnerField = ownerField;
    }

    public String getDateDetails() {
        return DateDetails;
    }

    public void setDateDetails(String dateDetails) {
        DateDetails = dateDetails;
    }

    public String getDateTypes() {
        return DateTypes;
    }

    public void setDateTypes(String dateTypes) {
        DateTypes = dateTypes;
    }



    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public static Bookings getInstance(){
        if (bookings!= null){
            return bookings;
        }else {
            bookings = new Bookings();
            return bookings;
        }
    }
}
