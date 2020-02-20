package com.example.android.e7gzlykora.viewmodels;


import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.Bookings;

import java.util.Date;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchFragmentViewModel extends ViewModel {

    private MutableLiveData<String> singleTime = new MutableLiveData<>();
    private MutableLiveData<String> weeklyTime = new MutableLiveData<>();
    private MutableLiveData<Date> date = new MutableLiveData<>();

    public SearchFragmentViewModel() {
    }


    public MutableLiveData<Date> getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date.setValue(date);
    }

    public MutableLiveData<String> getSingleTime() {
        return singleTime;
    }

    public void setSingleTime(String singleTime) {
        this.singleTime.setValue(singleTime);
    }

    public MutableLiveData<String> getWeeklyTime() {
        return weeklyTime;
    }

    public void setWeeklyTime(String weeklyTime) {
        this.weeklyTime.setValue(weeklyTime);
    }

    public boolean SaveDataToSearch(){

        if (getSingleTime().getValue() != null) {
            Bookings.getInstance().setDateTypes(getSingleTime().getValue());
        }else if (getWeeklyTime().getValue() != null){
            Bookings.getInstance().setDateTypes(getWeeklyTime().getValue());
        }else  {
            Bookings.getInstance().setDateTypes(getSingleTime().getValue());
        }
        Bookings.getInstance().setDateDetails(String.valueOf(getDate().getValue()));
        Bookings.getInstance().setUserMobile(Auth.getInstance().getMobile());
        Bookings.getInstance().setUserName(Auth.getInstance().getUserName());
        Bookings.getInstance().setNameUser(Auth.getInstance().getName());
        return true;
    }
}
