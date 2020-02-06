package com.example.android.e7gzlykora;

import android.os.Bundle;

import com.example.android.e7gzlykora.Utils.FragmentUtils;
import com.example.android.e7gzlykora.views.Identity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentUtils.addFragment(MainActivity.this, new Identity(),R.id.join_community_layout,true);

    }


}
