package com.docsapp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.docsapp.R;
import com.docsapp.config.MyApplication;
import com.ks.myutils.utils.AndroidUtilsLib;

import java.util.List;
import java.util.Locale;

/*
 * Created by Kushaal Singla on 14-Oct-17.
 */

public class AndroidUtils extends AndroidUtilsLib {

    @NonNull
    public static String getVolleyErrorMsg(VolleyError error) {
        int msg;

        if (error instanceof NetworkError) {
            msg = R.string.no_internet;
        } else if (error instanceof ServerError) {
            msg = R.string.server_error;
        } else if (error instanceof TimeoutError) {
            msg = R.string.timeout_error;
        } else {
            return error.getMessage();
        }

        return MyApplication.getContext().getString(msg);

    }

    public static String getAddressViaCoordinates(Context context, Double latitude, Double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address obj = addresses.get(0);

            return obj.getAddressLine(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
