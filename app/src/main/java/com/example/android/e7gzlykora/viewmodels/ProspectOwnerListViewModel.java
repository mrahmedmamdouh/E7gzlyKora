package com.example.android.e7gzlykora.viewmodels;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.BaseActivity;
import com.example.android.e7gzlykora.model.Bookings;
import com.example.android.e7gzlykora.model.Owner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProspectOwnerListViewModel extends ViewModel {

    private Activity mContext;
    private Owner owner;
    private MutableLiveData<ArrayList<Owner>> ownerList;
    private MutableLiveData<Boolean> response = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Owner>> getOwnerList() {
        if (Objects.requireNonNull(ownerList.getValue()).size() == 0) {
            ownerList = new MutableLiveData<>();
        }
        return ownerList;
    }

    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public void setResponse(MutableLiveData<Boolean> response) {
        this.response.setValue(response.getValue());
    }

    public MutableLiveData<Integer> getPosition() {
        return position;
    }

    public void setPosition(MutableLiveData<Integer> position) {
        this.position.postValue(position.getValue());
    }

    public ProspectOwnerListViewModel(Activity mContext) {
        this.mContext = mContext;
    }

    public void GetListData() {
        AndroidNetworking.get("http://192.168.1.123:8089/api/User/GetFields")
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
                            Objects.requireNonNull(getOwnerList().getValue()).add(owner);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: " + anError);
                    }
                });
    }

    public void Book(String name, String mobile, String fieldName,int Position,ArrayList<Owner> ownerlist) {
        Bookings.getInstance().setOwnerName(name);
        Bookings.getInstance().setOwnerMobile(mobile);
        Bookings.getInstance().setOwnerField(fieldName);
        AndroidNetworking.post("http://192.168.1.123:8089/api/Bookings/Booked")
                .addBodyParameter(Bookings.getInstance())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BaseActivity.getmContext(), response, Toast.LENGTH_SHORT).show();
                        ownerlist.remove(Position);
                        getPosition().setValue(Position);
                        getResponse().setValue(true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: " + anError);
                        getResponse().setValue(false);
                    }
                });
    }


}
