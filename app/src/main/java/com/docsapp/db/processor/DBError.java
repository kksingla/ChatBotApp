package com.docsapp.db.processor;

/**
 * Created by kushaal singla on 17-May-18.
 */
public class DBError {
    private String errorMsg;

    public DBError(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
