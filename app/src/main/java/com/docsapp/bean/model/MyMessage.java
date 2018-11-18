package com.docsapp.bean.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.docsapp.enums.RequestStatus;
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
    @SerializedName("reqStatus")
    private int reqStatus = RequestStatus.PENDING.getValue();

    public MyMessage(Long tag) {
        createdOn = tag;
    }

    public MyMessage() {
    }

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

    public int getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(int reqStatus) {
        this.reqStatus = reqStatus;
    }

    @Override
    public int hashCode() {
        return String.valueOf(createdOn).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyMessage) {
            MyMessage bean = (MyMessage) obj;
            return createdOn == bean.createdOn;
        }
        return super.equals(obj);
    }

    public boolean isPending() {
        return reqStatus == RequestStatus.PENDING.getValue();
    }
}