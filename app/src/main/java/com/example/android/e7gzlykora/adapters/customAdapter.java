package com.example.android.e7gzlykora.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.ProspectownerListviewBinding;
import com.example.android.e7gzlykora.model.Bookings;
import com.example.android.e7gzlykora.model.Owner;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class customAdapter extends RecyclerView.Adapter<customAdapter.MyViewHolder> {

    private ArrayList<Owner> ownerlist;
    private Context mContext;
    private ProspectownerListviewBinding binding;

    public customAdapter(Context c, ArrayList<Owner> p, ProspectownerListviewBinding binding) {
        this.mContext = c;
        this.ownerlist = p;
        this.binding = binding;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.prospectowners, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final Owner owner = ownerlist.get(position);
        holder.name.setText(owner.getName());
        holder.field.setText(owner.getFieldName());
        holder.mobile.setText(owner.getMobile());
        holder.address.setText(owner.getAddress());
        holder.cost.setText(String.valueOf(owner.getCost()));

        holder.bttn.setOnClickListener(view -> {
            Bookings.getInstance().setOwnerName(owner.getName());
            Bookings.getInstance().setOwnerMobile(owner.getMobile());
            Bookings.getInstance().setOwnerField(owner.getFieldName());
            binding.getViewModel().getbookingsApi(position, ownerlist);
        });

        binding.getViewModel().getbookingsResponse().observe(Objects.requireNonNull(binding.getLifecycleOwner()), s -> {
            if (s != null) {
                if (s.equals("\"Booked Successfully\"")) {
                    binding.getViewModel().getPosition().observe(binding.getLifecycleOwner(), integer -> {
                        if (integer != null) {
                            notifyItemRemoved(integer);
                            notifyItemRangeChanged(integer, ownerlist.size());
                            notifyDataSetChanged();
                        }
                    });

                } else {
                    Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ownerlist.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextInputEditText name;
        TextInputEditText field;
        TextInputEditText mobile;
        TextInputEditText address;
        TextInputEditText cost;
        MaterialButton bttn;


        MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.nameowner);
            this.field = itemView.findViewById(R.id.fieldNameowner);
            this.mobile = itemView.findViewById(R.id.mobileowner);
            this.address = itemView.findViewById(R.id.addressowner);
            this.cost = itemView.findViewById(R.id.costowner);
            this.bttn = itemView.findViewById(R.id.reserve);
        }
    }
}


