package com.example.android.e7gzlykora.Utils;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils extends FragmentManager {

    private static final String TAG = "FragmentsManager";
    private static FragmentTransaction transaction;

    private static FragmentTransaction getTransaction(Activity activity) {

        return getFragmentManager(activity).beginTransaction();
    }

    private static FragmentManager getFragmentManager(Activity activity) {
        return ((AppCompatActivity) activity).getSupportFragmentManager();
    }


    public static void addFragment(Activity activity, Fragment fragment, int id, boolean add_to_backstack) {
        transaction = getTransaction(activity);
        transaction.add(id, fragment, fragment.getClass().getName());
        if (add_to_backstack)
            transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }


    static void replaceFragment(Activity activity, Fragment fragment, int id, boolean add_to_backstack) {
        Fragment check_Fragment = getFragmentManager(activity).findFragmentByTag(fragment.getClass().getName());
        if (check_Fragment == null) {
            transaction = getTransaction(activity)
                    .replace(id, fragment, fragment.getClass().getName());
            if (add_to_backstack)
                transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
        } else {
            transaction = getTransaction(activity);
            transaction.replace(id, check_Fragment, check_Fragment.getClass().getName())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
