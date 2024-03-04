package com.example.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calculator.customenum.EMathFunction;
import com.example.calculator.customenum.ENumber;
import com.example.calculator.customenum.EOperation;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.Objects;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<String> expression = new MutableLiveData<>("0");
    private MutableLiveData<String> result = new MutableLiveData<>("0");
    private MutableLiveData<String> mode = new MutableLiveData<>();

    public LiveData<String> getExpression() {
        return expression;
    }

    public LiveData<String> getResult() {
        return result;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<String> getMode() {
        return mode;
    }


    public MainViewModel() {
        if (expression.getValue() == null) {
            expression.setValue("");
        }

        if (mode.getValue() == null) {
            mode.setValue("Deg");
        }
    }

    // Concatenate String
    public String concatenateStrings(String initialString, String stringToAppend) {
        int length = initialString.length();
        if (length > 0 && Character.isDigit(initialString.charAt(length - 1))) {
            initialString += "*" + stringToAppend + "(";
        } else {
            initialString += stringToAppend + "(";
        }

        return initialString;
    }

    public void changeRadMode() {
        mode.setValue("Rad");
    }

    public void changeDegMode() {
        mode.setValue("Deg");
    }

    public void addFunctionToExpression(EMathFunction function) {
        String curExpression = expression.getValue();
        if (curExpression == null) {
            curExpression = "0";
        }
        String functionStr = function.getValue();
        curExpression = concatenateStrings(curExpression,functionStr);

        this.expression.postValue(curExpression);
        updatePreviewResult(curExpression);
    }

    public void addFunctionToExpressionHaveToast(EMathFunction function) {
        if(!result.getValue().equals("") && !result.getValue().equals("0")) {
            String functionStr = function.getValue();
            String curExpression = expression.getValue();
            curExpression = curExpression + functionStr;
            this.expression.postValue(curExpression);
            updatePreviewResult(curExpression);
        } else {
            this.toastMessage.setValue("Định dạng đã dùng không hợp lệ");
        }
    }

    public void addNumberToExpression(ENumber number) {
        String curExpression = expression.getValue();
        if (curExpression == null) {
            curExpression = "0";
        }

        String numberStr = number.getValue();
        if (curExpression.equals(ENumber.ZERO.getValue())) {
            curExpression = numberStr;
        } else {
            curExpression = curExpression + numberStr;
        }

        updateExpressionAndPreviewResult(curExpression);
    }

    public void addOperationToExpression(EOperation operation) {
        String curExpression = this.expression.getValue();
        String curChar = operation.getOperationString();
        if (curExpression == null) {
            curExpression = "0";
        }

        if (Utils.isEndOfOperationCharacter(curExpression)) {
            StringBuilder stringBuilder = new StringBuilder(curExpression);
            if (stringBuilder.length() > 0) {
                stringBuilder.setCharAt(stringBuilder.length() - 1, curChar.charAt(0));
            }
            curExpression = stringBuilder.toString();
            updateExpressionAndPreviewResult(curExpression);
            return;
        }

        curExpression = curExpression + curChar;
        updateExpressionAndPreviewResult(curExpression);
    }

    public void applyResultForExpression() {
        String expression = this.expression.getValue();
        String result = Utils.getResult(expression);

        if (Objects.equals(result, Utils.EXPRESSION_ERROR)) {
            String oldResult = this.result.getValue();
            this.expression.postValue(oldResult);
        } else {
            this.expression.postValue(result);
        }
    }


    public void addParenthesesToExpression() {
        String curExpression = this.expression.getValue();
        if (curExpression == null || curExpression.equals(ENumber.ZERO.getValue())) {
            this.expression.postValue("(");
        } else {
            int openParenthesesCount = Utils.countOccurrences(curExpression, '(');
            int closeParenthesesCount = Utils.countOccurrences(curExpression, ')');
            curExpression += openParenthesesCount > closeParenthesesCount ? ")" : "(";
            this.expression.postValue(curExpression);
        }
        updatePreviewResult(curExpression);
    }

    public void deleteCharacterOrFunction() {
        String curExpression = this.expression.getValue();

        if (curExpression == null || curExpression.equals(ENumber.ZERO.getValue())) {
            return;
        }

        if (curExpression.length() == 1 && curExpression.charAt(0) != ENumber.ZERO.getValue().charAt(0)) {
            updateExpressionAndPreviewResult(ENumber.ZERO.getValue());
            return;
        }

        if (!Utils.isEndOfFunction(curExpression)) {
            String newExpression = curExpression.substring(0, curExpression.length() - 1);
            updateExpressionAndPreviewResult(newExpression);
            return;
        }

        int startIndex = Utils.getStartIndexOfEndFunction(curExpression);
        if (startIndex != -1) {
            String newExpression = curExpression.substring(0, startIndex);
            updateExpressionAndPreviewResult(newExpression);
        }
    }

    private void updateExpressionAndPreviewResult(String newExpression) {
        this.expression.postValue(newExpression);
        updatePreviewResult(newExpression);
    }

    private void updatePreviewResult(String expression) {
        String result = Utils.getResult(expression);
        if (!Objects.equals(result, Utils.EXPRESSION_ERROR)) {
            this.result.postValue(result);
        }
    }

    public void clearExpression() {
        this.expression.postValue("0");
        this.result.postValue("");
    }

    public void makeNegativeNumber() {
        String curExpression = expression.getValue();
        if (curExpression == null || curExpression.equals(ENumber.ZERO.getValue())) {
            return;
        }

        int lastNumberIndex = Utils.findLastNumberIndex(curExpression);
        String lastNumber = curExpression.substring(lastNumberIndex);
        String newLastNumber;
        if (lastNumber.startsWith(EOperation.SUBTRACTION.getOperationString())) {
            newLastNumber = lastNumber.substring(1);
        } else {
            newLastNumber = "(" + EOperation.SUBTRACTION.getOperationString() + lastNumber + ")";
        }

        String newExpression = curExpression.substring(0, lastNumberIndex) + newLastNumber;
        this.expression.postValue(newExpression);
    }

    public void addDecimalPointToExpression() {
        String currentExpression = this.expression.getValue();
        if (currentExpression == null || currentExpression.isEmpty()) {
            this.expression.postValue("0.");
            return;
        }

        int lastNumberIndex = Utils.findLastNumberIndex(currentExpression);
        if (lastNumberIndex == -1) {
            return;
        }

        String lastNumber = currentExpression.substring(lastNumberIndex);
        if (lastNumber.contains(".")) {
            return;
        }

        String newExpression = currentExpression.substring(0, lastNumberIndex) + lastNumber + ".";
        this.expression.postValue(newExpression);
    }
}
