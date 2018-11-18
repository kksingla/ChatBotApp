package com.docsapp.bean.model;

import com.google.gson.annotations.SerializedName;

public class MyMessage {
    @SerializedName("emotion")
    private String emotion;
    @SerializedName("message")
    private String message;
    @SerializedName("chatBotID")
    private int chatbotid;
    @SerializedName("chatBotName")
    private String chatbotname;

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getChatbotid() {
        return chatbotid;
    }

    public void setChatbotid(int chatbotid) {
        this.chatbotid = chatbotid;
    }

    public String getChatbotname() {
        return chatbotname;
    }

    public void setChatbotname(String chatbotname) {
        this.chatbotname = chatbotname;
    }
}