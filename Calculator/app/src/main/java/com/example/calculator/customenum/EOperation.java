package com.example.calculator.customenum;

public enum EOperation {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    MODULO("%");

    private final String operationString;

    EOperation(String operationString) {
        this.operationString = operationString;
    }

    public String getOperationString() {
        return operationString;
    }
}
