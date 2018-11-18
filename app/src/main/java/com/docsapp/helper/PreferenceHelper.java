package com.docsapp.helper;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceHelper {
    private static SharedPreferences preferences;
    private static PreferenceHelper helper;

    public static void createInstance(Context context) {
        if (preferences == null)
            preferences = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
    }

    public static PreferenceHelper getInstance() {
        if (helper == null)
            helper = new PreferenceHelper();
        return helper;
    }


    @SuppressLint("ApplySharedPref")
    public void clearAll() {
        preferences.edit().clear().commit();
    }

}
