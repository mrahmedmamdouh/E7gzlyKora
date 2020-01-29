package com.example.android.e7gzlykora.model;

public class Auth {
    private static Auth auth;

    public Auth(String userName, String password, String name,String UserGUID,String Mobile,Integer UserType) {
        this.UserName = userName;
        this.Password = password;
        this.Name = name;
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

    public Auth() {}

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

    public static Auth getInstance () {
        if (auth != null) {
            return auth;
        } else {
            auth = new Auth();
            return auth;
        }
    }
}
