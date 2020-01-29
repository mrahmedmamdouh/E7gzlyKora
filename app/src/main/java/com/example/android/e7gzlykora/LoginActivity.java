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

/**
 * Created by Kevin on 2-Mar-17.
 */
public class LoginActivity extends AppCompatActivity {

    String userName;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText UserName = (EditText) findViewById(R.id.UserName);
         password =  findViewById(R.id.Password);
        Button signIn = (Button)findViewById(R.id.buttonLoginUser);
        Button backuser = (Button)findViewById(R.id.buttonBackLogin);
        TextView Signup = (TextView) findViewById(R.id.linkToSignUp);
         userName = getIntent().getStringExtra("UserName");
        UserName.setText(userName);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignInUser(UserName.getText().toString(),password.getText().toString() );

                           }
        });



        backuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,identity.class);
                startActivity(i);
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(LoginActivity.this, Register.class);
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

        AndroidNetworking.get("http://192.168.2.222:8089/api/Auth/UpdateAuthData")
                .addQueryParameter("xAuthName",xAuthName)
                .addQueryParameter("xAuthPass",xAuthPass)
                .addQueryParameter("userType","101")
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
                            Intent intent = new Intent(LoginActivity.this, searchActivity.class);
                            startActivity(intent);
                        }} else {
                            Toast.makeText(LoginActivity.this,"No User found with this credintials",Toast.LENGTH_SHORT).show();

                        }
                        }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+ anError);

                    }
                });
    }
}

