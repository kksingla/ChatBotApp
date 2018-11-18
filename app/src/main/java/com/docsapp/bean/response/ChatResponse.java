package com.docsapp.bean.response;

import com.docsapp.bean.model.MyMessage;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kushaal singla on 18-Nov-18.
 */
public class ChatResponse extends MasterResponse {

    @SerializedName("message")
    private MyMessage message;

    public MyMessage getMessage() {
        return message;
    }

    public void setMessage(MyMessage message) {
        this.message = message;
    }

}
