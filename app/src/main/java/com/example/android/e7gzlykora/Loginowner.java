package com.example.android.e7gzlykora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.android.e7gzlykora.model.Auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Loginowner extends AppCompatActivity {

    String userName;
    private EditText UserName,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_owner);

         UserName = (EditText) findViewById(R.id.UserName);
         password = (EditText) findViewById(R.id.Password);
        Button signIn = (Button)findViewById(R.id.buttonLoginUser);
        Button backuser = (Button)findViewById(R.id.buttonBackLogin);
        TextView Signup = (TextView) findViewById(R.id.linkToSignUp);
         userName = getIntent().getStringExtra("UserName");
        UserName.setText(userName);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SignInUser(UserName.getText().toString(),password.getText().toString());
            }
        });



        backuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Loginowner.this,identity.class);
                startActivity(i);
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Loginowner.this, RegisterOwner.class);
                startActivity(i1);
            }
        });

    }

    private void SignInUser(String xAuthName, final String xAuthPass) {
        if(xAuthPass.isEmpty() || xAuthPass.length() < 6){
            password.setError("Enter a valid Password");
            password.requestFocus();
            return;
        }

        Map<String,String> SignInData =  new HashMap<>();
        SignInData.put("xAuthName",xAuthName);
        SignInData.put("xAuthPass",xAuthPass);


        AndroidNetworking.get("http://192.168.2.222:8089/api/Auth/UpdateAuthData")
                .addQueryParameter("xAuthName",xAuthName)
                .addQueryParameter("xAuthPass",xAuthPass)
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
                            Intent intent = new Intent(Loginowner.this, OwnerPendingBookings.class);
                            Auth.getInstance().setName(SignedUser.getName());
                            Auth.getInstance().setMobile(SignedUser.getMobile());
                            Auth.getInstance().setUserGUID(SignedUser.getUserGUID());
                            startActivity(intent);
                        } }else {
                            Toast.makeText(Loginowner.this,"No User found with this credintials",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+ anError);

                    }
                });
    }

}

