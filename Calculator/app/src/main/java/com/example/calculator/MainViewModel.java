package com.example.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calculator.customenum.ENumber;
import com.example.calculator.customenum.EOperation;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public MutableLiveData<String> getMode() {
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

    public void addSQRTString() {
        String curExpression = expression.getValue();

        if(!result.equals("")) {
            expression.setValue(curExpression+"SQRT(");
        } else {
            expression.setValue(curExpression+"*SQRT(");
        }
    }

    public void addSinString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "sin"));
        result.setValue("");
    }

    public void addCosString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "cos"));
        result.setValue("");
    }

    public void addTanString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "tan"));
        result.setValue("");
    }

    public void addLnString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "ln"));
        result.setValue("");
    }

    public void addLogString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "log"));
        result.setValue("");
    }

    public void addPartXString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "1/"));
        result.setValue("");
    }

    public void addEPowString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "e^"));
        result.setValue("");
    }

    public void addPow2String() {
        String curExpression = expression.getValue();

        if(!result.equals("")) {
            expression.setValue(curExpression+"^(2)");
            evaluateExpression();
        } else {
            this.toastMessage.setValue("Định dạng đã dùng không hợp lệ");
        }
    }

    public void addXPowYString() {
        String curExpression = expression.getValue();

        if(!result.equals("")) {
            expression.setValue(curExpression+"^");
            result.setValue("");
        } else {
            this.toastMessage.setValue("Định dạng đã dùng không hợp lệ");
        }
    }

    public void addAbsString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "abs"));
        result.setValue("");
    }

    public void addPIString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "pi"));
        result.setValue("");
    }

    public void addEString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "e"));
        result.setValue("");
    }

    // Extends function
    public void addArcSinString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "asin"));
        result.setValue("");
    }

    public void addArcCosString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "acos"));
        result.setValue("");
    }

    public void addArcTanString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "atan"));
        result.setValue("");
    }

    public void addHyperSinString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "sinh"));
        result.setValue("");
    }

    public void addHyperCosString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "cosh"));
        result.setValue("");
    }

    public void addHyperTanString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "tanh"));
        result.setValue("");
    }

    public void addArcHyperSinString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "asinh"));
        result.setValue("");
    }

    public void addArcHyperCosString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "acosh"));
        result.setValue("");
    }

    public void addArcHyperTanString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "atanh"));
        result.setValue("");
    }

    public void add2PowXString() {
        String curExpression = expression.getValue();

        expression.setValue(concatenateStrings(curExpression, "2^"));
        result.setValue("");
    }

    public void addXPow3String() {
        String curExpression = expression.getValue();

        if(!result.equals("")) {
            expression.setValue(curExpression+"^(3)");
            result.setValue("");
        } else {
            this.toastMessage.setValue("Định dạng đã dùng không hợp lệ");
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

    public void evaluateExpression() {
        try{
            Expression expression = new Expression(this.expression.getValue());
            BigDecimal result = expression.eval();

            this.result.setValue(result.toEngineeringString());
        } catch (ArithmeticException | IllegalArgumentException | Expression.ExpressionException e) {
            this.result.setValue("");
            this.toastMessage.setValue("Không thể hiển thị kết quả không xác định");
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

        this.expression.postValue(curExpression);
        updatePreviewResult(curExpression);
    }

    public void addOperationToExpression(EOperation operation) {
        String curExpression = expression.getValue();
        if (curExpression == null) {
            curExpression = "0";
        }

        curExpression = curExpression + operation.getOperationString();

        this.expression.postValue(curExpression);
        updatePreviewResult(curExpression);
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

    private void updatePreviewResult(String expression) {
        String result = Utils.getResult(expression);
        if (!Objects.equals(result, Utils.EXPRESSION_ERROR)) {
            this.result.postValue(result);
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

        if (curExpression == null
                || curExpression.equals(ENumber.ZERO.getValue())
                || curExpression.length() < 2) {
            return;
        }

        if (!Utils.isEndOfFunction(curExpression)) {
            String newExpression = curExpression.substring(0, curExpression.length() - 1);
            this.expression.postValue(newExpression);
            updatePreviewResult(newExpression);
            return;
        }

        int startIndex = Utils.getStartIndexOfEndFunction(curExpression);
        if (startIndex != -1) {
            String newExpression = curExpression.substring(0, startIndex);
            this.expression.postValue(newExpression);
            updatePreviewResult(newExpression);
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
