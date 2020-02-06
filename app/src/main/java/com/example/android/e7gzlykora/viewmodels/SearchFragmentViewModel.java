package com.example.android.e7gzlykora.viewmodels;

import android.app.Activity;

import com.example.android.e7gzlykora.databinding.FragmentSearchBinding;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.Bookings;

import java.util.Date;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchFragmentViewModel extends ViewModel {

    private Activity mContext;
    private FragmentSearchBinding binding;
    private MutableLiveData<String> singleTime = new MutableLiveData<>();
    private MutableLiveData<String> weeklyTime = new MutableLiveData<>();
    private MutableLiveData<Date> date = new MutableLiveData<>();
    public SearchFragmentViewModel(Activity mContext,FragmentSearchBinding binding) {
        this.mContext=mContext;
        this.binding=binding;
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
        final String fromtime = binding.spinner3.getSelectedItem().toString();
        final String totime = binding.spinner4.getSelectedItem().toString();
        final String zone3 = binding.spinner.getSelectedItem().toString();
        final String zone4 = binding.spinner2.getSelectedItem().toString();
        Bookings.getInstance().setZone1(zone3);
        Bookings.getInstance().setZone2(zone4);
        Bookings.getInstance().setBookingTimeFrom(fromtime);
        Bookings.getInstance().setBookingTimeTo(totime);
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
