package com.docsapp.db.processor;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by kushaal singla on 18-Nov-18.
 */
public class DBAsync<T> extends RequestProcessor {
    private String operation;
    private Object requestBody;
    private DBResponse.ErrorListener errorListener;
    private DBResponse.Listener<T> listener;

    private DBAsync() {
    }


    private DBAsync(String operation, Object requestBody, DBResponse.Listener<T> listener, DBResponse.ErrorListener errorListener) {
        this.operation = operation;
        this.requestBody = requestBody;
        this.errorListener = errorListener;
        this.listener = listener;
    }

    public void get() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    processRequest(operation, requestBody);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    sendError(new DBError(ex.getMessage()));
                }
            }
        };

        new Thread(runnable).start();
    }


    public static class Builder<T> {
        private String operation;
        private Object requestBody = null;
        private DBResponse.ErrorListener errorListener;
        private DBResponse.Listener<T> listener;

        public Builder(String operation, DBResponse.Listener<T> listener, DBResponse.ErrorListener errorListener) {
            this.operation = operation;
            this.errorListener = errorListener;
            this.listener = listener;
        }

        public DBAsync build() {
            return new DBAsync<T>(operation, requestBody, listener, errorListener);
        }

        public Builder setRequestBody(Object requestBody) {
            this.requestBody = requestBody;
            return this;
        }
    }

    @Override
    protected void sendResponse(final Object obj) {
//        DebugUtils.log(GSONUtils.toString(obj));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onResponse((T) obj);
                }
            }
        });
    }

    @Override
    protected void sendError(final DBError dbError) {
//        DebugUtils.log(GSONUtils.toString(dbError));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (errorListener != null)
                    errorListener.onErrorResponse(dbError);
            }
        });
    }


}
