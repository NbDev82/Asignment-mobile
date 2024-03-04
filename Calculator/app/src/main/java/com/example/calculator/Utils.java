package com.example.calculator;

import com.example.calculator.customenum.EMathFunction;
import com.example.calculator.customenum.EOperation;
import com.example.calculator.customexpression.FactorialFunction;
import com.udojava.evalex.Expression;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static final String EXPRESSION_ERROR = "Error";
    public static final int PRECISION = 10;

    public static String getResult(String expression) {
        if (expression == null || expression.isEmpty()) {
            return "NaN";
        }

        expression = replaceFactorial(expression);
        expression = replacePI(expression);

        try {
            Expression exp = new Expression(expression);
            exp.addFunction(new FactorialFunction());
            exp.setPrecision(PRECISION);

            return exp.eval().toPlainString();
        } catch (Exception e) {
            e.printStackTrace();
            return EXPRESSION_ERROR;
        }
    }

    private static String replacePI(String expression) {
        return expression.replaceAll("π", "pi");
    }

    private static String replaceFactorial(String expression) {
        String factorialRegex = "(\\d+|\\w+)!";

        return expression.replaceAll(factorialRegex, "factorial($1)");
    }

    public static int findLastNumberIndex(String expression) {
        int lastIndex = -1;

        for (int i = expression.length() - 1; i >= 0; i--) {
            char ch = expression.charAt(i);
            if (Character.isDigit(ch) || ch == '.') {
                lastIndex = i;
            } else if (ch == '-' && lastIndex == i - 1) {
                lastIndex = i;
            } else {
                break;
            }
        }

        return lastIndex;
    }

    public static int countOccurrences(String text, char target) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        return count;
    }

    public static boolean isEndOfFunction(String expression) {
        EMathFunction[] functions = EMathFunction.values();

        for (EMathFunction function : functions) {
            if (expression.endsWith(function.getValue() + "(")) {
                return true;
            }
        }

        return false;
    }

    public static int getStartIndexOfEndFunction(String expression) {
        EMathFunction[] functions = EMathFunction.values();

        for (int i = expression.length() - 1; i >= 0; i--) {
            char currentChar = expression.charAt(i);
            if (Character.isLetter(currentChar)) {
                for (EMathFunction function : functions) {
                    if (expression.startsWith(function.getValue() + "(", i)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public static boolean isEndOfOperationCharacter(String expression) {
        char lastChar = expression.charAt(expression.length() - 1);
        EOperation[] operations = EOperation.values();

        for (EOperation operation : operations) {
            if (lastChar == operation.getOperationString().charAt(0)) {
                return true;
            }
        }
        return false;
    }
}
