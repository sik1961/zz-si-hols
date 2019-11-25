package com.sik.sihols.core;

public enum HalfDay {
    AM(0),
    PM(1);

    private int value;

    private  HalfDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
