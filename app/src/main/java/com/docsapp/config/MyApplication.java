package com.docsapp.config;

import android.app.Application;
import android.content.Context;

import com.docsapp.helper.PreferenceHelper;


/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PreferenceHelper.createInstance(this);
    }

    public static Context getContext() {
        return context;
    }
}
