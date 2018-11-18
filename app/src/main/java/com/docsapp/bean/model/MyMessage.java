package com.docsapp.bean.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "MyMessage")
public class MyMessage {
    @PrimaryKey
    @SerializedName("createdOn")
    private long createdOn = System.currentTimeMillis();
    @SerializedName("emotion")
    private String emotion;
    @SerializedName("message")
    private String message;
    @SerializedName("chatBotID")
    private int chatbotid;
    @SerializedName("chatBotName")
    private String chatbotname;
    @SerializedName("externalId")
    private String externalId;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

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