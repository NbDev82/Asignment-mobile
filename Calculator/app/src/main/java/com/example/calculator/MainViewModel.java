package com.example.calculator;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calculator.customenum.EConstant;
import com.example.calculator.customenum.EMathFunction;
import com.example.calculator.customenum.ENumber;
import com.example.calculator.customenum.EOperation;
import com.example.calculator.history.History;
import com.example.calculator.history.HistoryDao;
import com.example.calculator.history.adapter.HistoryListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainViewModel extends ViewModel implements HistoryListener {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private final MutableLiveData<String> expression = new MutableLiveData<>("0");
    private final MutableLiveData<String> previewResult = new MutableLiveData<>("0");
    private final MutableLiveData<String> mode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isHistoryVisible = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isMenuVisible = new MutableLiveData<>(false);
    private final MutableLiveData<List<History>> historyList = new MutableLiveData<>(new ArrayList<>());
    private final HistoryDao historyDao;

    public LiveData<String> getExpression() {
        return expression;
    }

    public LiveData<String> getPreviewResult() {
        return previewResult;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<String> getMode() {
        return mode;
    }


    public LiveData<Boolean> getIsHistoryVisible() {
        return isHistoryVisible;
    }

    public LiveData<Boolean> getIsMenuVisible() {
        return isMenuVisible;
    }

    public LiveData<List<History>> getHistoryList() {
        return historyList;
    }

    public MainViewModel(HistoryDao historyDao) {
        this.historyDao = historyDao;

        if (expression.getValue() == null) {
            expression.setValue("");
        }

        if (mode.getValue() == null) {
            mode.setValue("Deg");
        }
    }

    public void fetchHistoryList() {
        List<History> historyList = historyDao.getAll();
        this.historyList.postValue(historyList);
    }

    // Concatenate String
    public String concatenateStrings(String initialString, String stringToAppend) {
        int length = initialString.length();

        if ((length > 0 && Character.isDigit(initialString.charAt(length - 1))) || initialString.charAt(length - 1) == '!') {
            initialString += "*" + stringToAppend + "(";
        } else {
            initialString += stringToAppend + "(";
        }

        return initialString;
    }

    public String concatenateConstant(String initialString, String stringToAppend) {
        int length = initialString.length();

        if ((length > 0 && Character.isDigit(initialString.charAt(length - 1))) || initialString.charAt(length - 1) == '!') {
            initialString += "*" + stringToAppend;
        } else {
            initialString += stringToAppend;
        }

        return initialString;
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

    public void addConstantToExpression(EConstant constant) {
        String curExpression = expression.getValue();
        if (curExpression == null) {
            curExpression = "0";
        }
        String functionStr = constant.getValue();
        curExpression = concatenateConstant(curExpression,functionStr);

        this.expression.postValue(curExpression);
        updatePreviewResult(curExpression);
    }

    public void addFunctionToExpressionHaveToast(EMathFunction function) {
        if(!previewResult.getValue().equals("") && !previewResult.getValue().equals("0")) {
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
        try {
            String curExpression = expression.getValue();
            String newExpression = Utils.addNumberToExpression(curExpression, number);
            updateExpressionAndPreviewResult(newExpression);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void addOperationToExpression(EOperation operation) {
        try {
            String curExpression = expression.getValue();
            String newExpression = Utils.addOperationToExpression(curExpression, operation);
            updateExpressionAndPreviewResult(newExpression);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void applyResultForExpression() {
        try {
            String expression = this.expression.getValue();
            String result = Utils.getResult(expression);

            if (Objects.equals(result, Utils.EXPRESSION_ERROR) || Objects.equals(expression, result)) {
                this.previewResult.postValue(result);
                return;
            }

            this.expression.postValue(result);
            this.previewResult.postValue("");

            History history = new History(expression, result, System.currentTimeMillis());
            historyDao.insert(history);
            addHistoryItem(history);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void addHistoryItem(History newItem) {
        List<History> currentList = this.historyList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(0, newItem);
        this.historyList.setValue(currentList);
    }

    public void addParenthesesToExpression() {
        try {
            String curExpression = expression.getValue();
            String newExpression = Utils.addParenthesesToExpression(curExpression);
            updateExpressionAndPreviewResult(newExpression);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void deleteCharacterOrFunction() {
        try {
            String curExpression = expression.getValue();
            String newExpression = Utils.deleteCharacterOrFunction(curExpression);
            updateExpressionAndPreviewResult(newExpression);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void updateExpressionAndPreviewResult(String newExpression) {
        this.expression.postValue(newExpression);
        updatePreviewResult(newExpression);
    }

    private void updatePreviewResult(String expression) {
        String result = Utils.getResult(expression);
        if (!Objects.equals(result, Utils.EXPRESSION_ERROR)) {
            this.previewResult.postValue(result);
        }
    }

    public void clearExpression() {
        this.expression.postValue("0");
        this.previewResult.postValue("");
    }

    public void toggleLastNumberSign() {
        try {
            String curExpression = expression.getValue();
            String newExpression = Utils.toggleLastNumberSign(curExpression);
            updateExpressionAndPreviewResult(newExpression);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void addDecimalPointToExpression() {
        try {
            String curExpression = expression.getValue();
            String newExpression = Utils.addDecimalPointToExpression(curExpression);
            updateExpressionAndPreviewResult(newExpression);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void toggleHistory() {
        boolean isHistoryVisible = this.isHistoryVisible.getValue();
        this.isHistoryVisible.postValue(!isHistoryVisible);
    }

    public void toggleMenu() {
        boolean isMenuVisible = this.isMenuVisible.getValue();
        this.isMenuVisible.postValue(!isMenuVisible);
    }

    public void clearAllHistory() {
        historyDao.deleteAll();
        this.historyList.postValue(new ArrayList<>());
        toastMessage.postValue("Delete all history");
    }

    @Override
    public void onItemClick(int position) {
        History history = this.historyList.getValue().get(position);
        if (history != null) {
            this.expression.postValue(history.getExpression());
            this.previewResult.postValue(history.getResult());
        }
    }
}
