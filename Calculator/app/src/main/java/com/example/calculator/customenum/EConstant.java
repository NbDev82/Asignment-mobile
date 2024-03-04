package com.example.calculator.customenum;

public enum EConstant {
    PI("π"),
    E("e");
    private final String value;

    EConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
