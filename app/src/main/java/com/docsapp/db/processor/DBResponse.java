package com.docsapp.db.processor;

/**
 * Created by kushaal singla on 17-May-18.
 */
public class DBResponse {
    /** Callback interface for delivering parsed responses. */
    public interface Listener<T> {
        /** Called when a response is received. */
        void onResponse(T response);
    }

    /** Callback interface for delivering error responses. */
    public interface ErrorListener {
        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        void onErrorResponse(DBError error);
    }

}
