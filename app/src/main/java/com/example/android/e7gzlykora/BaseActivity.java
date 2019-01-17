package com.example.android.e7gzlykora;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;





    public class BaseActivity extends AppCompatActivity {
        private static final String TAG_DIALOG_FRAGMENT = "tagDialogFragment";



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

