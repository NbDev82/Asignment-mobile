package com.example.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> title = new MutableLiveData<>();

    public LiveData<String> getTitle() {
        return title;
    }

    public MainViewModel() {
        title.postValue("Hello world");
    }
}
