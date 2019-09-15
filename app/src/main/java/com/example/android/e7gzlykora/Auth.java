package com.example.android.e7gzlykora;

public class Auth {


    public Auth(String userName, String password, String name,String UserGUID,String Mobile,Integer UserType) {
        UserName = userName;
        Password = password;
        Name = name;
        this.UserGUID = UserGUID;
        this.Mobile = Mobile;
        this.UserType = UserType;
    }

    private String UserName;
    private String Password;
    private String Name;
    private String UserGUID;
    private String Mobile;
    private Integer UserType;

    public Integer getUserType() {
        return UserType;
    }

    public void setUserType(Integer userType) {
        UserType = userType;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUserGUID() {
        return UserGUID;
    }

    public void setUserGUID(String userGUID) {
        UserGUID = userGUID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
