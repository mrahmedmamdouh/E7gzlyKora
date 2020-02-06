package com.example.android.e7gzlykora.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.Utils.FragmentUtils;
import com.example.android.e7gzlykora.databinding.ActivityIdentityBinding;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class Identity extends Fragment {

    private ActivityIdentityBinding binding;

    public Identity(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.activity_identity);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        return inflater.inflate(R.layout.activity_identity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClick();
    }

    private void onClick() {
        binding.ownerBtn.setOnClickListener(v -> FragmentUtils.addFragment(getActivity(),new Loginowner(),R.id.join_community_layout,true));

        binding.playerBtn.setOnClickListener(v -> FragmentUtils.addFragment(getActivity(),new LoginFragment(),R.id.join_community_layout,true));

    }



}
