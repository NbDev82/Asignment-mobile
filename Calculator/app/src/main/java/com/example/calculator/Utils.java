package com.example.calculator;

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
        List<String> functionPrefixes = Arrays.asList("log(", "ln(", "sin(", "cos(", "tan(", "sqrt(");

        for (String prefix : functionPrefixes) {
            if (expression.endsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    public static int getStartIndexOfEndFunction(String expression) {
        for (int i = expression.length() - 1; i >= 0; i--) {
            char currentChar = expression.charAt(i);
            if (Character.isLetter(currentChar)) {
                for (String prefix : Arrays.asList("log", "ln", "sin", "cos", "tan", "sqrt")) {
                    if (expression.startsWith(prefix, i)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}
