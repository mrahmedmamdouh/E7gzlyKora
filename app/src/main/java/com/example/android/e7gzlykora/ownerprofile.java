package com.example.android.e7gzlykora;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ownerprofile extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ownerprofile);

        Button editprofile = (Button) findViewById(R.id.edit);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ownerprofile.this,MainActivity.class);
                startActivity(i);
            }
        });

        mFirebaseInstance = FirebaseDatabase.getInstance();





        DatabaseReference reference = mFirebaseInstance.getInstance().getReference("owners");




        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot datas: dataSnapshot.getChildren()){
                String name11 = datas.child("name").getValue().toString();
                String mobile1 = datas.child("mobile").getValue().toString();
                String field1 = datas.child("Field Name").getValue().toString();
                String Address1 = datas.child("Address").getValue().toString();
                String cost1 = datas.child("Cost").getValue().toString();
                String zone11 = datas.child("Zone1").getValue().toString();
                String zone22 = datas.child("Zone2").getValue().toString();

                TextView name = (TextView) findViewById(R.id.name);

                name.setText(name11);

                TextView name1 = (TextView) findViewById(R.id.mobilephone);

                name1.setText(mobile1);

                TextView fieldname = (TextView) findViewById(R.id.field);

                fieldname.setText(field1);

                TextView address = (TextView) findViewById(R.id.addressuser);
                address.setText(Address1);

                TextView cost = (TextView) findViewById(R.id.costhour);
                cost.setText(cost1);

                TextView zone1 = (TextView) findViewById(R.id.zone1);
                zone1.setText(zone11);

                TextView zone2 = (TextView) findViewById(R.id.zone2);
                zone2.setText(zone22);


            }
        }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());

            }
        });










    }}
