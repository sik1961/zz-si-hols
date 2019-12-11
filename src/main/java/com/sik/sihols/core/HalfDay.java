package com.sik.sihols.core;

public enum HalfDay {
    AM(0),
    PM(1);

    private int value;

    private HalfDay(String amOrPM) {
        if (amOrPM.equalsIgnoreCase("AM")) {
            this.value = 0;
        } else if (amOrPM.equalsIgnoreCase("PM")) {
            this.value = 1;
        } else {
            throw new IllegalArgumentException("Invalid value: " + amOrPM);
        }
    }

    private  HalfDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
