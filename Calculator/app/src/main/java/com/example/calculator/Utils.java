package com.example.calculator;

import com.example.calculator.customexpression.FactorialFunction;
import com.udojava.evalex.Expression;

public class Utils {

    public static final String EXPRESSION_ERROR = "Error";
    public static final int PRECISION = 10;

    public static String getResult(String expression) {
        if (expression == null || expression.isEmpty()) {
            return "NaN";
        }

        expression = replaceFactorial(expression);

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
}
