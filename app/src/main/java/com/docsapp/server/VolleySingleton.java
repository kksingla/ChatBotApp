package com.docsapp.server;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.docsapp.config.MyApplication;

public class VolleySingleton {

    private RequestQueue mRequestQueue;

    private static VolleySingleton mInstance;

    private VolleySingleton() {
        mRequestQueue = getRequestQueue();

    }

    public static VolleySingleton getInstance() {
        synchronized (VolleySingleton.class) {
            if (mInstance == null) {
                synchronized (VolleySingleton.class) {
                    mInstance = new VolleySingleton();
                }
            }
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        synchronized (VolleySingleton.class) {
            if (mRequestQueue == null) {
                synchronized (VolleySingleton.class) {
                    mRequestQueue = Volley.newRequestQueue(MyApplication.getContext());
                }
            }
        }
        return mRequestQueue;
    }


}
