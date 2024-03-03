package com.example.calculator;

import com.example.calculator.customexpression.FactorialFunction;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Utils {

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
            return "Error";
        }
    }

    private static String replaceFactorial(String expression) {
        String factorialRegex = "(\\d+|\\w+)!";

        return expression.replaceAll(factorialRegex, "factorial($1)");
    }

}
