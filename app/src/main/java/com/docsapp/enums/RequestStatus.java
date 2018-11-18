package com.docsapp.enums;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */
public enum RequestStatus {
    INIT(0),
    COMPLETE(1),
    PENDING(2);

    private final int value;

    public int getValue() {
        return value;
    }

    RequestStatus(int i) {
        this.value = i;
    }
}
