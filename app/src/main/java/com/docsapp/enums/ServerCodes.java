package com.docsapp.enums;

/*
 * Created by Kushaal Singla on 18-Nov-18.
 */
public enum ServerCodes {
    SUCCESS(1);

    private final int value;

    public int getValue() {
        return value;
    }

    ServerCodes(int i) {
        this.value = i;
    }
}
