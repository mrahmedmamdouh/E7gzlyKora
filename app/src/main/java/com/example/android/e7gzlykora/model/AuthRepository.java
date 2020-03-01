package com.example.android.e7gzlykora.model;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AuthRepository {

    private static AuthRepository authRepository;
    private MutableLiveData<Auth> authResponse = new MutableLiveData<>();
    private MutableLiveData<String> registerResponse = new MutableLiveData<>();

    public static AuthRepository getAuthRepository() {
        if (authRepository == null) {
            authRepository = new AuthRepository();
        }
        return authRepository;
    }


    public LiveData<Auth> verifyUser() {
        AndroidNetworking.get("http://192.168.2.117:8089/api/Auth/UpdateAuthData")
                .addQueryParameter("xAuthName", Auth.getInstance().getUserName())
                .addQueryParameter("xAuthPass", Auth.getInstance().getPassword())
                .addQueryParameter("userType", String.valueOf(Auth.getInstance().getUserType()))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0) {
                            Log.d("TAG", "onResponse: " + response);
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = null;
                                try {
                                    obj = response.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    assert obj != null;
                                    Auth.getInstance().setName(obj.getString("Name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Auth.getInstance().setUserName(obj.getString("UserName"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Auth.getInstance().setMobile(obj.getString("Mobile"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Auth.getInstance().setUserGUID(obj.getString("UserGUID"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Auth.getInstance().setUserType(obj.getInt("UserType"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Auth.getInstance().setPassword(obj.getString("Password"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                authResponse.setValue(Auth.getInstance());
                            }
                        } else {
                            authResponse.setValue(Auth.getInstance());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
        return authResponse;
    }

    public LiveData<String> registerUser() {
        AndroidNetworking.post("http://192.168.2.117:8089/api/Auth/ExportData")
                .addBodyParameter(Auth.getInstance())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        registerResponse.setValue(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        registerResponse.setValue(anError.getMessage());
                    }
                });
        return registerResponse;
    }

}
