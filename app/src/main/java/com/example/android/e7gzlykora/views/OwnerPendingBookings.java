package com.example.android.e7gzlykora.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.adapters.Booking_Items_Adapter;
import com.example.android.e7gzlykora.databinding.OwnerPendingBookingsBinding;
import com.example.android.e7gzlykora.model.PendingItems;
import com.example.android.e7gzlykora.viewmodels.OwnerPendingBookingsViewModel;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class OwnerPendingBookings extends Fragment {
    private Booking_Items_Adapter adapter;
    private ArrayList<PendingItems> pendingItemsArrayList = new ArrayList<>();
    private OwnerPendingBookingsBinding binding;
    private OwnerPendingBookingsViewModel viewModel;

    public OwnerPendingBookings() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.owner_pending_bookings);
        viewModel = ViewModelProviders.of(getActivity()).get(OwnerPendingBookingsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new OwnerPendingBookingsViewModel());
        binding.executePendingBindings();
        return inflater.inflate(R.layout.owner_pending_bookings, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setHasFixedSize(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        viewModel.getData();
        viewModel.getPendingItemsList().observe(getViewLifecycleOwner(), pendingItems -> {
            adapter = new Booking_Items_Adapter(getActivity(), pendingItems, binding);
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        viewModel.getresponseConfirm().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                viewModel.getPosition().observe(getViewLifecycleOwner(), integer -> {
                    adapter.notifyItemRemoved(integer);
                    adapter.notifyItemRangeChanged(integer, pendingItemsArrayList.size());
                    adapter.notifyDataSetChanged();
                });
            }
        });

        viewModel.getresponseReject().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                viewModel.getPosition().observe(getViewLifecycleOwner(), integer -> {
                    adapter.notifyItemRemoved(integer);
                    adapter.notifyItemRangeChanged(integer, pendingItemsArrayList.size());
                    adapter.notifyDataSetChanged();
                });
            }
        });

    }


}
