package com.example.android.e7gzlykora.viewmodels;

import android.app.Activity;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.android.e7gzlykora.model.Auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginOwnerFragmentViewModel extends ViewModel {

    private Activity mContext;
    private MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<Boolean> verified = new MutableLiveData<>();
    private MutableLiveData<Boolean> Error = new MutableLiveData<>();


    public LoginOwnerFragmentViewModel(Activity mContext){
        this.mContext=mContext;
    }
    public MutableLiveData<Boolean> getError() {
        return Error;
    }

    private void setError() {
        this.Error.setValue(true);
    }

    public MutableLiveData<Boolean> getVerified() {
        return verified;
    }

    private void setVerified(boolean verified) {
        this.verified.setValue(verified);
    }

    private MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }


    public void SignInUser() {
        AndroidNetworking.get("http://192.168.1.123:8089/api/Auth/UpdateAuthData")
                .addQueryParameter("xAuthName",getEmail().getValue())
                .addQueryParameter("xAuthPass",getPassword().getValue())
                .addQueryParameter("userType","102")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() != 0){
                            Log.d("TAG", "onResponse: "+ response);
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = null;
                                try {
                                    obj = response.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Auth SignedUser = new Auth();
                                try {
                                    assert obj != null;
                                    SignedUser.setName(obj.getString("Name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    SignedUser.setUserName(obj.getString("UserName"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    SignedUser.setMobile(obj.getString("Mobile"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    SignedUser.setUserGUID(obj.getString("UserGUID"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    SignedUser.setUserType(obj.getInt("UserType"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    SignedUser.setPassword(obj.getString("Password"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Auth.getInstance().setName(SignedUser.getName());
                                Auth.getInstance().setMobile(SignedUser.getMobile());
                                Auth.getInstance().setUserGUID(SignedUser.getUserGUID());
                                setVerified(true);

                            } }else {
                            setVerified(false);

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        setError();
                        Log.d("TAG", "onResponse: "+ anError);

                    }
                });
    }
}
