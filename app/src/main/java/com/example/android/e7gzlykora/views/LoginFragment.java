package com.example.android.e7gzlykora.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.Utils.FragmentUtils;
import com.example.android.e7gzlykora.databinding.ActivityLoginBinding;
import com.example.android.e7gzlykora.viewmodels.LoginFragmentViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class LoginFragment extends Fragment {

    private ActivityLoginBinding binding;



    public LoginFragment(){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new LoginFragmentViewModel(getActivity()));
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
    }

    private void onClick() {
        binding.buttonLoginUser.setOnClickListener(v -> {
            if (binding.Password.getText().toString().isEmpty() || binding.Password.getText().toString().length() < 6) {
                binding.Password.setError("Enter a valid Password");
                binding.Password.requestFocus();
            }else {
                binding.getViewModel().setPassword(binding.Password.getText().toString());
                binding.getViewModel().setEmail(binding.UserName.getText().toString());
                binding.getViewModel().SignInUser();

                binding.getViewModel().getError().observe(getViewLifecycleOwner(), Boolean -> {
                    if (Boolean){
                        Toast.makeText(getActivity(),"Try Again Later",Toast.LENGTH_SHORT).show();
                    }
                });

                binding.getViewModel().getVerified().observe(getViewLifecycleOwner(), Boolean -> {
                    if (Boolean){
                        FragmentUtils.addFragment(getActivity(),new SearchFragment(), R.id.login_layout,false);

                    }else {
                        Toast.makeText(getActivity(), "No User found with this credintials", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });


        binding.buttonBackLogin.setOnClickListener(v -> FragmentUtils.addFragment(getActivity(),new Identity(),4,false));
        binding.linkToSignUp.setOnClickListener(v -> FragmentUtils.addFragment(getActivity(),new Register(),5,true));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        binding.executePendingBindings();
        String userName = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("UserName");
        binding.UserName.setText(userName);
    }


}

