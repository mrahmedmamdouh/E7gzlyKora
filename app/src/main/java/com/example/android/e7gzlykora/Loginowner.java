package com.example.android.e7gzlykora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Loginowner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_owner);

        final EditText mobilephone = (EditText) findViewById(R.id.editMobilePhone);
        Button signIn = (Button)findViewById(R.id.buttonLoginUser);
        Button backuser = (Button)findViewById(R.id.buttonBackLogin);
        TextView Signup = (TextView) findViewById(R.id.linkToSignUp);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = mobilephone.getText().toString().trim();

                if(a.isEmpty() || a.length() < 10){
                    mobilephone.setError("Enter a valid mobile");
                    mobilephone.requestFocus();
                    return;
                }

                Intent intent = new Intent(Loginowner.this, verifynumber_owner.class);
                intent.putExtra("mobile", a);
                startActivity(intent);
            }
        });



        backuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Loginowner.this,identity.class);
                startActivity(i);
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Loginowner.this, MainActivity.class);
                startActivity(i1);
            }
        });










    }

}

