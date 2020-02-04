package com.example.android.e7gzlykora.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.model.Auth;

public class Register extends AppCompatActivity {

    private String Name,Password,UserName,Mobile,UserGUID;
    private Integer UserType;
    EditText a1,a2,a3,a4;
    private String TAG = "Register.class";

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

          a1 = findViewById(R.id.Name);
          a2 = findViewById(R.id.Password);
          a3 = findViewById(R.id.UserName);
          a4 = findViewById(R.id.Mobile);
        UserGUID = Settings.Secure.getString(Register.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Button bttn1 =  findViewById(R.id.button_register_user);
        Button bttn2 =  findViewById(R.id.button_back_register);


        bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        // Save / update the user
        bttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = a1.getText().toString();
                Password = a2.getText().toString();
                String a = a2.getText().toString().trim();

                if(a.isEmpty() || a.length() < 6){
                    a2.setError("Enter a valid Password");
                    a2.requestFocus();
                    return;
                }
                UserName = a3.getText().toString();
                Mobile = a4.getText().toString();
                UserType = 101;

                createUser(Name,UserName,Password,Mobile,UserType);

            }
        });

    }


    /**
     * Creating new user node under 'users'
     */
    private void createUser(final String Name, final String UserName, final String Password, final String Mobile, final Integer UserType) {

        Auth auth = new Auth(UserName,Password,Name,UserGUID,Mobile,UserType);



        AndroidNetworking.post("http://192.168.2.8:8089/api/Auth/ExportData")
                .addBodyParameter(auth)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+ response);
                        if(response.equals("\"User has been added successfully\"")){
                        Intent i = new Intent(Register.this, LoginActivity.class);
                        i.putExtra("mobile", Mobile);
                        i.putExtra("Name",Name);
                        i.putExtra("Password",Password);
                        i.putExtra("UserName",UserName);
                        i.putExtra("UserType",UserType);
                        startActivity(i);} else{
                            Toast.makeText(getApplicationContext(),"UserName Already Exists",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(),anError.getErrorBody(),Toast.LENGTH_SHORT).show();

                    }
                });

    }



    }

