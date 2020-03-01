package com.example.android.e7gzlykora;

import android.app.Application;
import android.content.Context;

import com.facebook.appevents.AppEventsLogger;

public class BaseActivity extends Application {
        private static final String TAG_DIALOG_FRAGMENT = "tagDialogFragment";
    public static Context mContext = null;

    public static Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        AppEventsLogger.activateApp(this);

    }


}

