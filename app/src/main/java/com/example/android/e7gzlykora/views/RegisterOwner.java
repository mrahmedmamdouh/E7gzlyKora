package com.example.android.e7gzlykora.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.model.Auth;

public class RegisterOwner extends AppCompatActivity {

    private String Name, Password, UserName, Mobile, UserGUID;
    private Integer UserType;
    TextInputEditText name, password, userName, mobile;
    Button register, back;
    private String TAG = "RegisterOwner.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout_owner);

        name = findViewById(R.id.Name);
        password = findViewById(R.id.Password);
        userName = findViewById(R.id.UserName);
        mobile = findViewById(R.id.Mobile);


         register =  findViewById(R.id.button_register_owner);
         back =  findViewById(R.id.button_back_register_owner);


        // Save / update the user
        onClick();


    }

    private void onClick() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Password = password.getText().toString().trim();
                UserName = userName.getText().toString();
                Mobile = mobile.getText().toString();
                UserType = 102;
                createUser(Name,UserName,Password,Mobile,UserType);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterOwner.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    @SuppressLint("HardwareIds")
    private void createUser(final String Name, final String UserName, final String Password, final String Mobile, final Integer UserType) {


        UserGUID = Settings.Secure.getString(RegisterOwner.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
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
                            Intent i = new Intent(RegisterOwner.this, Loginowner.class);
                            i.putExtra("mobile", Mobile);
                            i.putExtra("Name",Name);
                            i.putExtra("Password",Password);
                            i.putExtra("UserName",UserName);
                            i.putExtra("UserType",UserType);
                            startActivity(i);
                        } else{
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

