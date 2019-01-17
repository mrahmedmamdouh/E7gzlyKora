package com.example.android.e7gzlykora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private static final String TAG = Register.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        final EditText a1 = (EditText) findViewById(R.id.editEmail);
        final EditText a2 = (EditText) findViewById(R.id.editPassword);
        final EditText a3 = (EditText) findViewById(R.id.editPassword1);
        Button bttn1 = (Button) findViewById(R.id.button_register_user);
        Button bttn2 = (Button) findViewById(R.id.button_back_register);

        bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("E7gzlykora").setValue("Realtime Database");



        // Save / update the user
        bttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(Register.this, searchActivity.class);
                startActivity(intent1);

                String name = a1.getText().toString();
                String mobile = a2.getText().toString();
                String password = a3.getText().toString();

                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(name, mobile, password);
                } else {
                    updateUser(name, mobile, password);
                }
            }
        });

        return;
    }


    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String mobile, String password) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        User user = new User(name, mobile, password);

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.name + ", " + user.mobile + ", " + user.password);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(final String name, final String mobile, final String password) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(mobile))
            mFirebaseDatabase.child(userId).child("mobile").setValue(mobile);
        if (!TextUtils.isEmpty(password))
            mFirebaseDatabase.child(userId).child("password").setValue(password);

        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //If email exists then toast shows else store the data on new key
                    if (!data.getValue(User.class).getMobile().equals(mobile)) {
                        mFirebaseDatabase.child(mFirebaseDatabase.push().getKey()).setValue(new User(name, mobile, password));
                    } else {
                        Toast.makeText(Register.this, "Mobile Number Already exists.", Toast.LENGTH_SHORT).show();


                    }

                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
            }
        });
    }

}