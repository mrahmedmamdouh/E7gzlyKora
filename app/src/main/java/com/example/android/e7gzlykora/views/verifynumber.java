package com.example.android.e7gzlykora.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.VerifynumberBinding;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class verifynumber extends Fragment {
    private VerifynumberBinding binding;

    public verifynumber(VerifynumberBinding binding) {
        this.binding = binding;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.verifynumber, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        String mobile = intent.getStringExtra("mobile");
    }

    private void onClick() {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.otp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    binding.otp.setError("Enter valid code");
                    binding.otp.requestFocus();
                    return;
                }

                //verifying the code entered manually
            }
        });
    }


}