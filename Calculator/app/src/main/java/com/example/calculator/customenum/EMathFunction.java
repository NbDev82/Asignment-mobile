package com.example.calculator.customenum;

public enum EMathFunction {
    SQRT("sqrt"),
    LOG("log"),
    LN("ln"),
    SIN("sin"),
    COS("cos"),
    PARTX("1/"),
    EPOW("e^"),
    POW2("^(2)"),
    XPOWY("^"),
    ABS("abs"),
    PI("pi"),
    E("e"),
    TAN("tan"),

    TWOPOWX("2^"),
    XPOW3("^(3)"),
    ARCHYPERSIN("asinh"),
    ARCHYPERCOS("acosh"),
    ARCHYPERTAN("atanh"),
    HYPERSIN("sinh"),
    HYPERCOS("cosh"),
    HYPERTAN("tanh"),
    ARCSIN("asin"),
    ARCCOS("acos"),
    ARCTAN("atan");

    private final String value;

    EMathFunction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
