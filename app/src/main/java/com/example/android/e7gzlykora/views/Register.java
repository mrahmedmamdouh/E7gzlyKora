package com.example.android.e7gzlykora.views;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.Utils.FragmentUtils;
import com.example.android.e7gzlykora.databinding.ActivityRegisterLayoutBinding;
import com.example.android.e7gzlykora.viewmodels.RegisterViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class Register extends Fragment {

    private String UserGUID;
    private ActivityRegisterLayoutBinding binding;


    public Register() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.activity_register_layout);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new RegisterViewModel(getActivity()));
        return binding.getRoot();

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

        binding.getViewModel().getUserAdded().observe(getViewLifecycleOwner(),aBoolean -> {
            if (aBoolean) {
                FragmentUtils.addFragment(getActivity(), new LoginFragment(), R.id.register_layout, false);
            }});

        binding.getViewModel().getUserNotAdded().observe(getViewLifecycleOwner(),aBoolean -> {
            if (aBoolean){
                Toast.makeText(getActivity(), "UserName Already Exists", Toast.LENGTH_SHORT).show();
            }
        });

        binding.getViewModel().getError().observe(getViewLifecycleOwner(),aBoolean -> {
            if (aBoolean){
                Toast.makeText(getActivity(), "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onClick() {
        binding.buttonBackRegister.setOnClickListener(v -> {
            FragmentUtils.addFragment(getActivity(), new LoginFragment(), R.id.register_layout, false);

        });
        binding.buttonRegisterUser.setOnClickListener(view -> {
            binding.getViewModel().setName(binding.Name.getText().toString());
            binding.getViewModel().setPassword(binding.Password.getText().toString());
            binding.getViewModel().setUserName(binding.UserName.getText().toString());
            binding.getViewModel().setMobile(binding.Mobile.getText().toString());
            binding.getViewModel().setUserGUID(UserGUID);
            binding.getViewModel().setUserType(101);
            String a = binding.Password.getText().toString().trim();
            if (a.isEmpty() || a.length() < 6) {
                binding.Password.setError("Enter a valid Password");
                binding.Password.requestFocus();
                return;
            }
            binding.getViewModel().createUser();
        });
    }



}

