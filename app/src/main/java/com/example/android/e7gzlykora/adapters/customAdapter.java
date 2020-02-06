package com.example.android.e7gzlykora.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.ProspectownerListviewBinding;
import com.example.android.e7gzlykora.model.Owner;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class customAdapter extends RecyclerView.Adapter <customAdapter.MyViewHolder> {

    private ArrayList <Owner> ownerlist;
    private Context mContext;
    private ProspectownerListviewBinding binding;

    public customAdapter(Context c, ArrayList <Owner> p,ProspectownerListviewBinding binding) {
        this.mContext = c;
        this.ownerlist = p;
        this.binding=binding;
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

        holder.bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.getViewModel().Book(owner.getName(),owner.getMobile(),owner.getFieldName(),position,ownerlist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ownerlist.size();
    }

    public void setNewData(ArrayList<Owner> newData) {
        this.ownerlist= newData;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView field;
        public TextView mobile;
        public TextView address;
        public TextView cost;
        public Button bttn;


        public MyViewHolder(View itemView) {
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


