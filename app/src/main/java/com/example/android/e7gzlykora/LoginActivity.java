package com.example.android.e7gzlykora;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.fasterxml.jackson.databind.deser.Deserializers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Kevin on 2-Mar-17.
 */
public class LoginActivity extends AppCompatActivity {

    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText UserName = (EditText) findViewById(R.id.UserName);
        final EditText password = (EditText) findViewById(R.id.Password);
        Button signIn = (Button)findViewById(R.id.buttonLoginUser);
        Button backuser = (Button)findViewById(R.id.buttonBackLogin);
        TextView Signup = (TextView) findViewById(R.id.linkToSignUp);
         userName = getIntent().getStringExtra("UserName");
        UserName.setText(userName);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = password.getText().toString().trim();

                if(a.isEmpty() || a.length() < 10){
                    password.setError("Enter a valid mobile");
                    password.requestFocus();
                    return;
                }

                Intent i = new Intent(LoginActivity.this, searchActivity.class);
                i.putExtra("Password", a);
                i.putExtra("mobile", getIntent().getStringExtra("mobile"));
                i.putExtra("Name",getIntent().getStringExtra("Name"));
                i.putExtra("Password",getIntent().getStringExtra("Password"));
                i.putExtra("UserName",userName);
                i.putExtra("UserType",getIntent().getStringExtra("UserType"));
                startActivity(i);
            }
        });



        backuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,identity.class);
                startActivity(i);
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(LoginActivity.this, Register.class);
                startActivity(i1);
            }
        });

    }

}

