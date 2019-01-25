package com.example.android.e7gzlykora;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class prospectowner_listview extends AppCompatActivity {
    ListView list;
    TextView fieldName;
    TextView nameowner;
    TextView mobileowner;
    TextView address;
    TextView cost;
    ArrayList <String> arraylist;
    ArrayAdapter <String> adapter;
    owner owner;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prospectowner_listview);

        fieldName = (TextView) findViewById(R.id.fieldName);
        nameowner = (TextView) findViewById(R.id.nameowner);
        mobileowner = (TextView) findViewById(R.id.mobileowner);
        address = (TextView) findViewById(R.id.address);
        cost = (TextView) findViewById(R.id.cost);

        final ListView list = (ListView) findViewById(R.id.list);
        owner = new owner();
        arraylist = new ArrayList <String>();
        adapter = new ArrayAdapter <>(this, R.layout.prospectowners, R.id.fieldName, arraylist);


        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("owners");


        // store app title to 'app_title' node
        mFirebaseInstance.getReference("E7gzlykora").setValue("Realtime Database");


        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    owner = data.getValue(owner.class);
                    arraylist.add(owner.getName());


                }

                list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}