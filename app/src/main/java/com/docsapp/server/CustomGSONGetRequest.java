package com.docsapp.server;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ks.myutils.utils.DebugUtils;
import com.ks.myutils.utils.GSONUtils;

import java.lang.reflect.Type;

import com.docsapp.constants.URLConstants;
import com.docsapp.utils.UIUtils;

public class CustomGSONGetRequest<T> extends Request<T> {

    private Type type;
    private Response.Listener<T> listener;

    /**
     * Make a POST request and return a parsed object from JSON.
     */
    public CustomGSONGetRequest(Context context, String url, Type tClass, @NonNull String params,
                                Response.Listener<T> listener, Response.ErrorListener errorListener, boolean isShowProgressDialog, String progressMsg) {
        super(Method.GET, URLConstants.BASE_URL + url + params, errorListener);
        this.type = tClass;
        this.listener = listener;
        if (isShowProgressDialog) {
            if (TextUtils.isEmpty(progressMsg)) {
                UIUtils.showDialog(context);
            } else {
                UIUtils.showDialog(context, progressMsg);
            }
        }

        setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        DebugUtils.log(URLConstants.BASE_URL + url + params);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            DebugUtils.log(json);
            return (Response<T>) Response.success(GSONUtils.getObj(json, type), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            return Response.error(new VolleyError(ex.getMessage()));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        UIUtils.dismissDialog();
        try {
            DebugUtils.log(response.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        try {
            DebugUtils.log(error.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        UIUtils.dismissDialog();
    }


    @Override
    public String getBodyContentType() {
        return "application/json";
    }

}
