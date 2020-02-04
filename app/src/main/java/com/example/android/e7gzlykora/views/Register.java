package com.example.android.e7gzlykora.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.ActivityRegisterLayoutBinding;
import com.example.android.e7gzlykora.model.Auth;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class Register extends Fragment {

    private String Name,Password,UserName,Mobile,UserGUID;
    private Integer UserType;
    private String TAG = "Register.class";
    private ActivityRegisterLayoutBinding binding;

    public Register(ActivityRegisterLayoutBinding binding) {
        this.binding = binding;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_register_layout, container, false);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
    }

    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        UserGUID = Settings.Secure.getString(Objects.requireNonNull(getActivity()).getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    private void onClick() {
        binding.buttonBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        // Save / update the user
        binding.buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = binding.Name.getText().toString();
                Password = binding.Password.getText().toString();
                String a = binding.Password.getText().toString().trim();

                if (a.isEmpty() || a.length() < 6) {
                    binding.Password.setError("Enter a valid Password");
                    binding.Password.requestFocus();
                    return;
                }
                UserName = binding.UserName.getText().toString();
                Mobile = binding.Mobile.getText().toString();
                UserType = 101;
                createUser(Name, UserName, Password, Mobile, UserType);

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
                            Intent i = new Intent(getActivity(), LoginActivity.class);
                        i.putExtra("mobile", Mobile);
                        i.putExtra("Name",Name);
                        i.putExtra("Password",Password);
                        i.putExtra("UserName",UserName);
                        i.putExtra("UserType",UserType);
                        startActivity(i);} else{
                            Toast.makeText(getActivity(), "UserName Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), anError.getErrorBody(), Toast.LENGTH_SHORT).show();

                    }
                });

    }



    }

