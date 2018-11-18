package com.docsapp.bean.response;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */

import com.docsapp.enums.ServerCodes;
import com.google.gson.annotations.SerializedName;
import com.ks.myutils.utils.GSONUtils;

public class MasterResponse {
    @SerializedName("errorMessage")
    private String errormessage;
    @SerializedName("success")
    private int success;
    @SerializedName("tag")
    private Long tag;

    @Override
    public String toString() {
        return GSONUtils.toString(this);
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public int getSuccess() {
        return success;
    }

    public boolean isSuccess() {
        return success == ServerCodes.SUCCESS.getValue();
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public void setTag(Long tag) {
        this.tag = tag;
    }

    public Long getTag() {
        return tag;
    }
}
