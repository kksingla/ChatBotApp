package com.docsapp.bean.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kushaal singla on 18-Nov-18.
 */
@Entity(tableName = "MyChatHead")
public class MyChatHead implements Serializable {
    @PrimaryKey
    @SerializedName("createdOn")
    private long createdOn = System.currentTimeMillis();
    @SerializedName("externalId")
    private String externalId;
    @SerializedName("name")
    private String name;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return externalId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyChatHead) {
            MyChatHead bean = (MyChatHead) obj;
            return externalId.equalsIgnoreCase(bean.externalId);
        }
        return super.equals(obj);
    }
}
