package com.example.android.e7gzlykora.viewmodels;

import android.app.Activity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.model.Auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private Activity mContext;
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> userName = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<String> mobile = new MutableLiveData<>();
    private MutableLiveData<Integer> userType = new MutableLiveData<>();
    private MutableLiveData<String> userGUID = new MutableLiveData<>();
    private MutableLiveData<Boolean> userAdded = new MutableLiveData<>();
    private MutableLiveData<Boolean> userNotAdded = new MutableLiveData<>();
    private MutableLiveData<Boolean> Error = new MutableLiveData<>();

    public RegisterViewModel(Activity mContext) {
        this.mContext = mContext;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    private MutableLiveData<String> getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.setValue(userName);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public MutableLiveData<String> getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.setValue(mobile);
    }


    private MutableLiveData<Integer> getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType.setValue(userType);
    }

    private MutableLiveData<String> getUserGUID() {
        return userGUID;
    }

    public void setUserGUID(String userGUID) {
        this.userGUID.setValue(userGUID);
    }

    public MutableLiveData<Boolean> getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(MutableLiveData<Boolean> userAdded) {
        this.userAdded = userAdded;
    }

    public MutableLiveData<Boolean> getUserNotAdded() {
        return userNotAdded;
    }

    public void setUserNotAdded(MutableLiveData<Boolean> userNotAdded) {
        this.userNotAdded = userNotAdded;
    }

    public MutableLiveData<Boolean> getError() {
        return Error;
    }

    public void setError(MutableLiveData<Boolean> error) {
        Error = error;
    }

    public void createUser() {
        Auth auth = new Auth(getUserName().getValue(), getPassword().getValue(), getName().getValue(), getUserGUID().getValue(), getMobile().getValue(), getUserType().getValue());
        AndroidNetworking.post("http://192.168.1.123:8089/api/Auth/ExportData")
                .addBodyParameter(auth)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("\"User has been added successfully\"")) {
                            getUserAdded().setValue(true);
                        } else {
                            getUserNotAdded().setValue(true);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        getError().setValue(true);
                    }
                });

    }

}
