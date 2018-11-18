package com.docsapp.server;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ks.myutils.utils.DebugUtils;
import com.ks.myutils.utils.GSONUtils;
import com.ks.myutils.utils.UIUtils;

import java.lang.reflect.Type;

import com.docsapp.constants.URLConstants;

public class CustomGSONPostRequest<T> extends Request<T> {

    private final String mBody;
    private Type tClass;
    private Response.Listener<T> listener;

    /**
     * Make a POST request and return a parsed object from JSON.
     */
    public CustomGSONPostRequest(Context context, String url, String jsonRequestBody, Type tClass,
                                 Response.Listener<T> listener, Response.ErrorListener errorListener, boolean isShowProgressDialog, String progressMsg) {
        super(Method.POST, URLConstants.BASE_URL + url, errorListener);
        this.mBody = jsonRequestBody;
        this.tClass = tClass;
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


        DebugUtils.log(URLConstants.BASE_URL + url);
        DebugUtils.log(jsonRequestBody);

    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            DebugUtils.log(json);
            return (Response<T>) Response.success(GSONUtils.getObj(json, tClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            return Response.error(new VolleyError(ex.getMessage()));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        UIUtils.dismissDialog();
        DebugUtils.log(response.toString());
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        DebugUtils.log(error.toString());
        UIUtils.dismissDialog();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mBody.getBytes("utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return super.getBody();
    }
}
