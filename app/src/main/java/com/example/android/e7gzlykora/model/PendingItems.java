package com.example.android.e7gzlykora.model;

public class PendingItems {

    private String DateTypes;
    private String BookingTimeFrom;
    private String BookingTimeTo;
    private String NameUser;
    private String UserMobile;

    public PendingItems() {
    }

    public PendingItems(String dateTypes, String bookingTimeFrom, String bookingTimeTo, String nameUser, String userMobile) {
        DateTypes = dateTypes;
        BookingTimeFrom = bookingTimeFrom;
        BookingTimeTo = bookingTimeTo;
        NameUser = nameUser;
        UserMobile = userMobile;
    }

    public String getDateTypes() {
        return DateTypes;
    }

    public void setDateTypes(String dateTypes) {
        DateTypes = dateTypes;
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
}
