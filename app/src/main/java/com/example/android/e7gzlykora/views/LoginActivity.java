package com.example.android.e7gzlykora.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.ActivityLoginBinding;
import com.example.android.e7gzlykora.model.Auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


public class LoginActivity extends Fragment {

    private ActivityLoginBinding binding;

    public LoginActivity(ActivityLoginBinding binding) {
        // Required empty public constructor
        this.binding = binding;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        onClick();
    }

    private void onClick() {
        binding.buttonLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignInUser(binding.UserName.getText().toString(), binding.Password.getText().toString());

            }
        });


        binding.buttonBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), identity.class);
                startActivity(i);
            }
        });
        binding.linkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getActivity(), Register.class);
                startActivity(i1);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init(View view) {
        String userName = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("UserName");
        binding.UserName.setText(userName);
    }


    private void SignInUser(String xAuthName, final String xAuthPass) {
        if (xAuthPass.isEmpty() || xAuthPass.length() < 6) {
            binding.Password.setError("Enter a valid Password");
            binding.Password.requestFocus();
            return;
        }

        AndroidNetworking.get("http://192.168.2.8:8089/api/Auth/UpdateAuthData")
                .addQueryParameter("xAuthName", xAuthName)
                .addQueryParameter("xAuthPass", xAuthPass)
                .addQueryParameter("userType", "101")
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
                                Intent intent = new Intent(getActivity(), searchActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(getActivity(), "No User found with this credintials", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: " + anError);

                    }
                });
    }
}

