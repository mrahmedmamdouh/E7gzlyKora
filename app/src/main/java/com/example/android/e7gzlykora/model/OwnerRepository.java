package com.example.android.e7gzlykora.model;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OwnerRepository {

    private static OwnerRepository ownerRepository;
    private Owner owner;
    private MutableLiveData<ArrayList<Owner>> ownersList = new MutableLiveData<>();
    private ArrayList<Owner> ownerArrayList = new ArrayList<>();

    public static OwnerRepository getOwnerRepository() {
        if (ownerRepository == null) {
            ownerRepository = new OwnerRepository();
        }
        return ownerRepository;
    }

    public LiveData<ArrayList<Owner>> GetListData() {
        AndroidNetworking.get("http://192.168.2.94:8089/api/User/GetFields")
                .addQueryParameter("Zone1", Bookings.getInstance().getZone1())
                .addQueryParameter("Zone2", Bookings.getInstance().getZone2())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = null;
                            owner = new Owner();
                            try {
                                obj = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                assert obj != null;
                                owner.setName(String.valueOf(obj.get("Name")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setAddress(String.valueOf(obj.get("Address")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setCost(Double.parseDouble(String.valueOf(obj.get("Cost"))));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setFieldName(String.valueOf(obj.get("FieldName")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setMobile(String.valueOf(obj.get("Mobile")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setZone1(String.valueOf(obj.get("Zone1")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setZone2(String.valueOf(obj.get("Zone2")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ownerArrayList.add(owner);
                            ownersList.setValue(ownerArrayList);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: " + anError);
                    }
                });
        return ownersList;
    }

}
