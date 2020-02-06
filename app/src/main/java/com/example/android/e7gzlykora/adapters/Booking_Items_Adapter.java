package com.example.android.e7gzlykora.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.OwnerPendingBookingsBinding;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.PendingItems;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Booking_Items_Adapter extends RecyclerView.Adapter <Booking_Items_Adapter.MyViewHolder> {

    private  ArrayList<PendingItems> pendingItemsArrayList;
    private Context mContext;
    private OwnerPendingBookingsBinding binding;

    public Booking_Items_Adapter(Context c, ArrayList<PendingItems> p,OwnerPendingBookingsBinding binding) {
        this.mContext = c;
        this.pendingItemsArrayList = p;
        this.binding=binding;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Booking_Items_Adapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pending_booking_items, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final PendingItems pendingItems = pendingItemsArrayList.get(i);
        myViewHolder.name.setText(pendingItems.getNameUser());
        myViewHolder.mobile.setText(pendingItems.getUserMobile());
        myViewHolder.date.setText(pendingItems.getDateTypes());
        myViewHolder.timeFrom.setText(pendingItems.getBookingTimeFrom());
        myViewHolder.timeTo.setText(String.valueOf(pendingItems.getBookingTimeTo()));

        myViewHolder.confirm.setOnClickListener(view -> binding.getViewModel().Confirm(pendingItems.getBookingTimeFrom(),pendingItems.getBookingTimeTo(),mContext,Auth.getInstance().getMobile(),pendingItems.getUserMobile(),i,pendingItemsArrayList));

        myViewHolder.reject.setOnClickListener(view -> binding.getViewModel().Reject(pendingItems.getBookingTimeFrom(),pendingItems.getBookingTimeTo(),mContext, Auth.getInstance().getMobile(),pendingItems.getUserMobile(),i,pendingItemsArrayList));
    }

    @Override
    public int getItemCount() {
        return pendingItemsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name,mobile,date,timeFrom,timeTo;
        private Button confirm,reject;

        MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.item_name);
            this.date = itemView.findViewById(R.id.item_date);
            this.mobile = itemView.findViewById(R.id.item_mobile);
            this.timeFrom = itemView.findViewById(R.id.item_time_from);
            this.timeTo = itemView.findViewById(R.id.item_time_to);
            this.confirm = itemView.findViewById(R.id.confirm);
            this.reject = itemView.findViewById(R.id.reject);
        }
    }
}
