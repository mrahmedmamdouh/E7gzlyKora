package com.example.android.e7gzlykora.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.PendingItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OwnerPendingBookingsViewModel extends ViewModel {

    private Activity mContext;
    private PendingItems pendingItems;
    private MutableLiveData<ArrayList<PendingItems>> pendingItemsList;
    private MutableLiveData<Boolean> responseConfirm = new MutableLiveData<>();
    private MutableLiveData<Boolean> responseReject = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();


    public OwnerPendingBookingsViewModel(Activity mContext){
        this.mContext = mContext;
    }

    public MutableLiveData<ArrayList<PendingItems>> getPendingItemsList() {
        if (pendingItemsList.getValue().size()==0){
            pendingItemsList = new MutableLiveData<>();
        }
        return pendingItemsList;
    }


    public PendingItems getPendingItems() {
        return pendingItems;
    }

    public void setPendingItems(PendingItems pendingItems) {
        this.pendingItems = pendingItems;
    }

    public void setPendingItemsList(MutableLiveData<ArrayList<PendingItems>> pendingItemsList) {
        this.pendingItemsList = pendingItemsList;
    }

    public MutableLiveData<Boolean> getresponseConfirm() {
        return responseConfirm;
    }

    public void setresponseConfirm(MutableLiveData<Boolean> responseConfirm) {
        this.responseConfirm = responseConfirm;
    }

    public MutableLiveData<Boolean> getresponseReject() {
        return responseReject;
    }

    public void setresponseReject(MutableLiveData<Boolean> responseReject) {
        this.responseReject = responseReject;
    }

    public MutableLiveData<Integer> getPosition() {
        return position;
    }

    public void setPosition(MutableLiveData<Integer> position) {
        this.position = position;
    }

    public void getData() {
        AndroidNetworking.get("http://192.168.1.123:8089/api/Bookings/Pending")
                .addQueryParameter("OwnerMobileNo", Auth.getInstance().getMobile())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener(){
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = null;
                            pendingItems = new PendingItems();
                            try {
                                obj = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                assert obj != null;
                                pendingItems.setNameUser(String.valueOf(obj.get("NameUser")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                pendingItems.setUserMobile(String.valueOf(obj.get("UserMobile")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                pendingItems.setDateTypes(String.valueOf(obj.get("DateDetails")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                pendingItems.setBookingTimeFrom(String.valueOf(obj.get("BookingTimeFrom")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                pendingItems.setBookingTimeTo(String.valueOf(obj.get("BookingTimeTo")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Objects.requireNonNull(pendingItemsList.getValue()).add(pendingItems);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: "+anError );
                    }
                });
    }


    public void Confirm(String TimeFrom, String TimeTo, final Context mContext, String OwnerMobileNo, String UserMobile, final int Position, final ArrayList<PendingItems> pendingItemsArrayList) {

        AndroidNetworking.get("http://192.168.1.123:8089/api/Bookings/UpdateToConfirm")
                .addQueryParameter("status", "Confirmed")
                .addQueryParameter("OwnerMobileNo", OwnerMobileNo)
                .addQueryParameter("UserMobile", UserMobile)
                .addQueryParameter("TimeFrom", TimeFrom)
                .addQueryParameter("TimeTo", TimeTo)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override

                    public void onResponse(String response) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        pendingItemsArrayList.remove(Position);
                        getPosition().setValue(Position);
                        getresponseConfirm().setValue(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: " + anError);
                        getresponseConfirm().setValue(false);
                    }

                });
    }


    public void Reject(String TimeFrom, String TimeTo,final Context mContext, String OwnerMobileNo, String UserMobile, final int Position, final ArrayList<PendingItems> pendingItemsArrayList) {

        AndroidNetworking.get("http://192.168.1.123:8089/api/Bookings/UpdateToConfirm")
                .addQueryParameter("status","reject")
                .addQueryParameter("OwnerMobileNo",OwnerMobileNo)
                .addQueryParameter("UserMobile",UserMobile)
                .addQueryParameter("TimeFrom",TimeFrom)
                .addQueryParameter("TimeTo",TimeTo)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mContext,response,Toast.LENGTH_SHORT).show();
                        pendingItemsArrayList.remove(Position);
                        getPosition().setValue(Position);
                        getresponseReject().setValue(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: "+anError );
                        getresponseReject().setValue(false);

                    }
                });

    }

}
