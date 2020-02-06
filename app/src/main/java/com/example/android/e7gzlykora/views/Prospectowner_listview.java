package com.example.android.e7gzlykora.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.adapters.customAdapter;
import com.example.android.e7gzlykora.databinding.ProspectownerListviewBinding;
import com.example.android.e7gzlykora.model.Owner;
import com.example.android.e7gzlykora.viewmodels.ProspectOwnerListViewModel;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class Prospectowner_listview extends Fragment {

    private customAdapter adapter;
    private ArrayList<Owner> ownerlist = new ArrayList<>();
    private ProspectownerListviewBinding binding;
    public Prospectowner_listview() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.prospectowner_listview);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new ProspectOwnerListViewModel(getActivity()));
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.list.setLayoutManager(manager);
        binding.list.setHasFixedSize(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.getViewModel().GetListData();
        binding.getViewModel().getOwnerList().observe(getViewLifecycleOwner(), owners -> {
            adapter = new customAdapter(getActivity(), owners,binding);
            binding.list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        binding.getViewModel().getResponse().observe(getViewLifecycleOwner(),aBoolean -> {
            if (aBoolean){
                binding.getViewModel().getPosition().observe(getViewLifecycleOwner(),integer -> {
                    adapter.notifyItemRemoved(integer);
                    adapter.notifyItemRangeChanged(integer, ownerlist.size());
                    adapter.notifyDataSetChanged();
                });

            }
        });
    }




}