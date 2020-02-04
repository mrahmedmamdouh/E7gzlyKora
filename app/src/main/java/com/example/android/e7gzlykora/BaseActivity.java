package com.example.android.e7gzlykora;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import androidx.annotation.Nullable;

public class BaseActivity extends AppCompatActivity {
        private static final String TAG_DIALOG_FRAGMENT = "tagDialogFragment";
    public static Context mContext = null;

    public static Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = getApplicationContext();
    }

    protected void dismissProgressDialog() {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getExistingDialogFragment();
            if (prev != null) {
                ft.remove(prev).commit();
            }
        }

        private Fragment getExistingDialogFragment() {
            return getSupportFragmentManager().findFragmentByTag(TAG_DIALOG_FRAGMENT);
        }
    }

