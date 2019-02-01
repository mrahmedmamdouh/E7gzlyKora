package com.example.android.e7gzlykora;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import butterknife.ButterKnife;


public class customAdapter extends RecyclerView.Adapter <customAdapter.MyViewHolder> {

    private final ArrayList <owner> ownerlist;
    private Context mContext;

    public customAdapter(Context c, ArrayList <owner> p) {
        this.mContext = c;
        this.ownerlist = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.prospectowners, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        owner owner = ownerlist.get(position);

        holder.name.setText(owner.getName());
        holder.field.setText(owner.getFieldname());
        holder.mobile.setText(owner.getMobile());
        holder.address.setText(owner.getAddress());
        holder.cost.setText(owner.getCost());
    }

    @Override
    public int getItemCount() {
        return ownerlist.size();
    }

    public void addData(ArrayList <owner> newData) {
        this.ownerlist.addAll(newData);
        notifyOnDataSetChanged();
    }

    private void notifyOnDataSetChanged() {

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
            this.name = itemView.findViewById(R.id.name);
            this.field = itemView.findViewById(R.id.fieldName);
            this.mobile = itemView.findViewById(R.id.mobileowner);
            this.address = itemView.findViewById(R.id.address);
            this.cost = itemView.findViewById(R.id.cost);
            this.bttn = itemView.findViewById(R.id.reserve);
        }
    }
}


