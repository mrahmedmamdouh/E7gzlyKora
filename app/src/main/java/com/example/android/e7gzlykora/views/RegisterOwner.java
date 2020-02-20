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
import com.example.android.e7gzlykora.databinding.ActivityRegisterLayoutOwnerBinding;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.viewmodels.RegisterOwnerViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class RegisterOwner extends Fragment {

    private String Name;
    private String Password;
    private String UserName;
    private String Mobile;
    private Integer UserType;
    private String UserGUID;
    private ActivityRegisterLayoutOwnerBinding binding;
    private RegisterOwnerViewModel viewModel;
    public RegisterOwner() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.activity_register_layout_owner);
        viewModel = ViewModelProviders.of(getActivity()).get(RegisterOwnerViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new RegisterOwnerViewModel());
        binding.executePendingBindings();
        viewModel.getRegisterResponse().observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                if (s.equals("\"User has been added successfully\"")) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    FragmentUtils.addFragment(getActivity(), new LoginFragment(), R.id.registerOwner_layout, false);
                } else {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Try Again Later", Toast.LENGTH_SHORT).show();

            }
        });
        return inflater.inflate(R.layout.activity_register_layout_owner, container, false);

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
        binding.buttonRegisterOwner.setOnClickListener(view -> {
            Auth.getInstance().setName(binding.Name.getText().toString());
            Auth.getInstance().setPassword(binding.Password.getText().toString());
            Auth.getInstance().setUserName(binding.UserName.getText().toString());
            Auth.getInstance().setMobile(binding.Mobile.getText().toString());
            Auth.getInstance().setUserGUID(UserGUID);
            Auth.getInstance().setUserType(102);
            String a = binding.Password.getText().toString().trim();
            if (a.isEmpty() || a.length() < 6) {
                binding.Password.setError("Enter a valid Password");
                binding.Password.requestFocus();
                return;
            }
            viewModel.createUser();
        });

        binding.buttonBackRegisterOwner.setOnClickListener(v -> FragmentUtils.addFragment(getActivity(), new LoginFragment(), R.id.registerOwner_layout, false));

    }



}

