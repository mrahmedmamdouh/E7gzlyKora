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
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.viewmodels.RegisterViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class Register extends Fragment {

    private static final String TAG = "Register";
    private String UserGUID;
    private ActivityRegisterLayoutBinding binding;
    private RegisterViewModel viewModel;

    public Register() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.activity_register_layout);
        viewModel = ViewModelProviders.of(getActivity()).get(RegisterViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new RegisterViewModel());
        binding.executePendingBindings();
        viewModel.getRegisterResponse().observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                if (s.equals("\"User has been added successfully\"")) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    FragmentUtils.addFragment(getActivity(), new LoginFragment(), R.id.register_layout, false);
                } else {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Try Again Later", Toast.LENGTH_SHORT).show();

            }
        });
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
        binding.buttonBackRegister.setOnClickListener(v -> FragmentUtils.addFragment(getActivity(), new LoginFragment(), R.id.register_layout, false));
        binding.buttonRegisterUser.setOnClickListener(view -> {
            Auth.getInstance().setName(binding.Name.getText().toString());
            Auth.getInstance().setPassword(binding.Password.getText().toString());
            Auth.getInstance().setUserName(binding.UserName.getText().toString());
            Auth.getInstance().setMobile(binding.Mobile.getText().toString());
            Auth.getInstance().setUserGUID(UserGUID);
            Auth.getInstance().setUserType(101);

            String a = binding.Password.getText().toString().trim();
            if (a.isEmpty() || a.length() < 6) {
                binding.Password.setError("Enter a valid Password");
                binding.Password.requestFocus();
                return;
            }
            viewModel.createUser();
        });
    }


}

