package com.example.android.e7gzlykora;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String ownerId;
    EditText a;
    EditText b;
    EditText c;
    EditText d;
    EditText e;
    Spinner f;
    Spinner g;

    Object value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText a = (EditText) findViewById(R.id.editEmail);
        final EditText b = (EditText) findViewById(R.id.editMobilePhone);
        final EditText c = (EditText) findViewById(R.id.editEmail1);
        final EditText d = (EditText) findViewById(R.id.editEmail2);
        final EditText e = (EditText) findViewById(R.id.editEmail3);
        Button save = (Button) findViewById(R.id.save);
        final Spinner f = (Spinner) findViewById(R.id.spinner);
        final Spinner g = (Spinner) findViewById(R.id.spinner2);
        final String mobile = b.getText().toString();




        String[] items = new String[]{"Cairo","Giza", "Alexandria","Others"};
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, items);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        f.setAdapter(adapter);

        f.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView <?> parent, View view,
                                                                   int position, long id) {

                                            if (f.getSelectedItem().equals("Cairo")) {


                                                ArrayAdapter adapter2 = ArrayAdapter.createFromResource(MainActivity.this,
                                                        R.array.cairo, android.R.layout.simple_spinner_item);
                                                g.setAdapter(adapter2);
                                            } else if (f.getSelectedItem().equals("Giza")) {
                                                ArrayAdapter adapter3 = ArrayAdapter.createFromResource(MainActivity.this,
                                                        R.array.Giza, android.R.layout.simple_spinner_item);
                                                g.setAdapter(adapter3);
                                            } else if (f.getSelectedItem().equals("Alexandria")) {
                                                ArrayAdapter adapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                                                        R.array.Alex, android.R.layout.simple_spinner_item);
                                                g.setAdapter(adapter4);
                                            } else {
                                                ArrayAdapter adapter5 = ArrayAdapter.createFromResource(MainActivity.this,
                                                        R.array.Others, android.R.layout.simple_spinner_item);
                                                g.setAdapter(adapter5);
                                            }
                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView <?> parent) {
                                            // TODO Auto-generated method stub
                                        }


                                    });

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("owners");
        mFirebaseDatabase.push().setValue(ownerId);

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("E7gzlykora").setValue("Realtime Database");





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, com.example.android.e7gzlykora.Loginowner.class);
                intent1.putExtra("mobile",mobile);
                startActivity(intent1);
                Toast.makeText(MainActivity.this,mobile,Toast.LENGTH_LONG).show();


                final String zone1 = f.getSelectedItem().toString();
                final String zone2 = g.getSelectedItem().toString();
                final String name = a.getText().toString();
                final String mobile = b.getText().toString();
                final String fieldname = c.getText().toString();
                final String address = d.getText().toString();
                final String cost = e.getText().toString();
                // Check for already existed userId
                if (TextUtils.isEmpty(ownerId)) {
                    createUser(name, mobile, fieldname, address, cost, zone1, zone2);
                } else {
                    updateUser(name, mobile, fieldname, address, cost, zone1, zone2);
                }
            }
        });

        return;
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String mobile, String fieldname, String address, String cost, String zone1, String zone2) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(ownerId)) {
            ownerId = mFirebaseDatabase.push().getKey();
        }


        owner owner = new owner(name, mobile, fieldname, address, cost, zone1, zone2);

        mFirebaseDatabase.child(ownerId).setValue(owner);
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(ownerId).child("name").setValue(name);

        if (!TextUtils.isEmpty(mobile))
            mFirebaseDatabase.child(ownerId).child("mobile").setValue(mobile);
        if (!TextUtils.isEmpty(fieldname))
            mFirebaseDatabase.child(ownerId).child("Field Name").setValue(fieldname);

        if (!TextUtils.isEmpty(address))
            mFirebaseDatabase.child(ownerId).child("Address").setValue(address);
        if (!TextUtils.isEmpty(cost))
            mFirebaseDatabase.child(ownerId).child("Cost").setValue(cost);

        if (!TextUtils.isEmpty(zone1))
            mFirebaseDatabase.child(ownerId).child("Zone1").setValue(zone1);
        if (!TextUtils.isEmpty(zone2))
            mFirebaseDatabase.child(ownerId).child("Zone2").setValue(zone2);


        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(ownerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                owner owner = dataSnapshot.getValue(owner.class);

                // Check for null
                if (owner == null) {
                    Log.e(TAG, "owner data is null!");
                    return;
                }

                Log.e(TAG, "owner data is changed!" + owner.name + ", " + owner.mobile+ ", " + owner.fieldname+ ", " + owner.address+ ", " + owner.cost+ ", " + owner.zone1+ ", " + owner.zone2);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(final String name, final String mobile, final String fieldname, final String address, final String cost, final String zone1, final String zone2) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(ownerId).child("name").setValue(name);

        if (!TextUtils.isEmpty(mobile))
            mFirebaseDatabase.child(ownerId).child("mobile").setValue(mobile);
        if (!TextUtils.isEmpty(fieldname))
            mFirebaseDatabase.child(ownerId).child("Field Name").setValue(fieldname);

        if (!TextUtils.isEmpty(address))
            mFirebaseDatabase.child(ownerId).child("Address").setValue(address);
        if (!TextUtils.isEmpty(cost))
            mFirebaseDatabase.child(ownerId).child("Cost").setValue(cost);

        if (!TextUtils.isEmpty(zone1))
            mFirebaseDatabase.child(ownerId).child("Zone1").setValue(zone1);
        if (!TextUtils.isEmpty(zone2))
            mFirebaseDatabase.child(ownerId).child("Zone2").setValue(zone2);

        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //If email exists then toast shows else store the data on new key
                    if (!data.getValue(owner.class).getMobile().equals(mobile)) {
                        mFirebaseDatabase.child(mFirebaseDatabase.push().getKey()).setValue(new owner(name, mobile, fieldname, address, cost, zone1, zone2));
                    } else {
                        Toast.makeText(MainActivity.this, "Mobile Number Already exists.", Toast.LENGTH_SHORT).show();


                    }

                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
            }
        });
    }
}