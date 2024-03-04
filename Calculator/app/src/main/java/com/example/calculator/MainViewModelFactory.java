package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculator.history.HistoryDao;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final HistoryDao historyDao;

    public MainViewModelFactory(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(historyDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
