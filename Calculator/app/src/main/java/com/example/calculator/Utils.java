package com.example.calculator;

import com.example.calculator.customenum.EConstant;
import com.example.calculator.customenum.EMathFunction;
import com.example.calculator.customenum.ENumber;
import com.example.calculator.customenum.EOperation;
import com.example.calculator.customexpression.CubeRootFunction;
import com.example.calculator.customexpression.FactorialFunction;
import com.udojava.evalex.Expression;

import org.jetbrains.annotations.Nullable;

public class Utils {

    public static final String EXPRESSION_ERROR = "Error";
    public static final int PRECISION = 10;

    public static String getResult(String expression) {
        if (expression == null || expression.isEmpty()) {
            return "NaN";
        }

        expression = replaceFactorial(expression);
        expression = replacePI(expression);
        expression = replaceLnE(expression);

        try {
            Expression exp = new Expression(expression);
            exp.addFunction(new FactorialFunction());
            exp.addFunction(new CubeRootFunction());
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

    private static String replaceLnE(String expression) {
        return expression.replaceAll("ln", "log");
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

    public static String addNumberToExpression(@Nullable String expression, ENumber number) {
        if (expression == null) {
            expression = "0";
        }

        String numberStr = number.getValue();
        if (expression.equals(ENumber.ZERO.getValue())) {
            expression = numberStr;
        } else {
            expression = expression + numberStr;
        }
        return expression;
    }

    public static String addOperationToExpression(@Nullable String expression, EOperation operation) {
        if (expression == null) {
            expression = "0";
        }

        String operationStr = operation.getOperationString();
        if (Utils.isEndOfOperationCharacter(expression)) {
            StringBuilder stringBuilder = new StringBuilder(expression);
            if (stringBuilder.length() > 0) {
                stringBuilder.setCharAt(stringBuilder.length() - 1, operationStr.charAt(0));
            }
            expression = stringBuilder.toString();
            return expression;
        }

        return expression + operationStr;
    }

    public static String addParenthesesToExpression(@Nullable String expression) {
        if (expression == null || expression.equals(ENumber.ZERO.getValue())) {
            return "(";
        }

        int openParenthesesCount = Utils.countOccurrences(expression, '(');
        int closeParenthesesCount = Utils.countOccurrences(expression, ')');
        expression += openParenthesesCount > closeParenthesesCount ? ")" : "(";
        return expression;
    }

    public static String deleteCharacterOrFunction(@Nullable String expression) {
        if (isExpressionEmpty(expression) || isSingleNonZeroCharacter(expression)) {
            return ENumber.ZERO.getValue();
        }

        if (!isEndOfFunction(expression)) {
            return expression.substring(0, expression.length() - 1);
        }

        int startIndex = getStartIndexOfEndFunction(expression);
        String newExpression = expression.substring(0, startIndex);
        if (isExpressionEmpty(newExpression)) {
            return ENumber.ZERO.getValue();
        }
        return newExpression;
    }

    public static boolean isExpressionEmpty(@Nullable String expression) {
        return expression == null
                || expression.isEmpty()
                || expression.equals(ENumber.ZERO.getValue());
    }

    public static boolean isSingleNonZeroCharacter(@Nullable String expression) {
        return expression != null
                && expression.length() == 1
                && expression.charAt(0) != ENumber.ZERO.getValue().charAt(0);
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

    public static String toggleLastNumberSign(@Nullable String expression) {
        if (expression == null || expression.equals(ENumber.ZERO.getValue())) {
            return expression;
        }

        int lastNumberIndex = findLastNumberIndex(expression);
        if (lastNumberIndex == -1) {
            return expression;
        }

        String lastNumber = expression.substring(lastNumberIndex);
        String newLastNumber;
        if (lastNumber.startsWith(EOperation.SUBTRACTION.getOperationString())) {
            newLastNumber = lastNumber.substring(1);
        } else {
            newLastNumber = "(" + EOperation.SUBTRACTION.getOperationString() + lastNumber + ")";
        }

        return expression.substring(0, lastNumberIndex) + newLastNumber;
    }

    public static String addDecimalPointToExpression(@Nullable String expression) {
        if (expression == null || expression.isEmpty()) {
            return "0.";
        }

        int lastNumberIndex = findLastNumberIndex(expression);
        if (lastNumberIndex == -1) {
            return expression;
        }

        String lastNumber = expression.substring(lastNumberIndex);
        if (lastNumber.contains(".")) {
            return expression;
        }

        return expression.substring(0, lastNumberIndex) + lastNumber + ".";
    }

    public static String addFunctionToExpression(String curExpression, EMathFunction Efunction) {
        int length = curExpression.length();
        String function = Efunction.getValue();
        String newExpression = curExpression;

        if(length == 0)
            return newExpression + function + "(";

        char lastChar = newExpression.charAt(length - 1);
        Boolean lastCharIsOperator = isOperator(lastChar);

        if (!lastCharIsOperator &&
                (Character.isDigit(lastChar) || lastChar == '!' || lastChar == ')')) {
            newExpression += "*" + function + "(";
        } else {
            newExpression += function + "(";
        }

        return newExpression;
    }

    public static String addFunctionToExpressionHaveToast(String curExpression, EMathFunction function) {
        return curExpression + function.getValue();
    }

    public static String addConstantToExpression(String curExpression, EConstant Econstant, String result) {
        int length = curExpression.length();
        String constant = Econstant.getValue();
        String newExpression = curExpression;
        Boolean isDigitResult = isDigit(result);
        Boolean isZeroResult = result.equals("0");

        if(length == 0)
            return newExpression + constant;

        char lastChar = newExpression.charAt(length - 1);
        Boolean lastCharIsOperator = isOperator(lastChar);

        if (!lastCharIsOperator && (!isZeroResult && isDigitResult) ||
                (Character.isDigit(lastChar) || lastChar == '!')
        ) {
            newExpression += "*" + constant;
        } else {
            newExpression += constant;
        }

        return newExpression;
    }

    private static Boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(';
    }

    private static Boolean isDigit(String result) {
        try {
            if (Float.isNaN(Float.parseFloat(result))) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Boolean isValidPreviewResult(String previewResult) {
        return !previewResult.equals("") &&
                !previewResult.equals("0") &&
                !previewResult.equals("NaN") &&
                !previewResult.equals("Error");
    }
}
