package com.example.android.e7gzlykora;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import okhttp3.Response;

public class Register extends AppCompatActivity {

    private String Name,Password,UserName,Mobile;
    private Integer UserType;
    EditText a1,a2,a3,a4;
    private String TAG = "Register.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

          a1 = findViewById(R.id.Name);
          a2 = findViewById(R.id.Password);
          a3 = findViewById(R.id.UserName);
          a4 = findViewById(R.id.Mobile);


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
                UserName = a3.getText().toString();
                Mobile = a4.getText().toString();
                UserType = 101;

                createUser(Name,UserName,Password,Mobile,UserType);

            }
        });

        return;
    }


    /**
     * Creating new user node under 'users'
     */
    private void createUser(final String Name, final String UserName, final String Password, final String Mobile, final Integer UserType) {

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        @SuppressLint("HardwareIds") String UserGUID = tManager.getDeviceId();
        Auth auth = new Auth(UserName,Password,Name,UserGUID,Mobile,UserType);



        AndroidNetworking.post("http://192.168.1.94:8089/api/Auth/ExportData")
                .addBodyParameter(auth)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+ response);
                        Intent i = new Intent(Register.this, LoginActivity.class);
                        i.putExtra("mobile", Mobile);
                        i.putExtra("Name",Name);
                        i.putExtra("Password",Password);
                        i.putExtra("UserName",UserName);
                        i.putExtra("UserType",UserType);
                        startActivity(i);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }



    }

