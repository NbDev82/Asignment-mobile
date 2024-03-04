package com.example.calculator;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculator.databinding.ActivityMainBinding;
import com.example.calculator.history.CalculatorDatabase;
import com.example.calculator.history.HistoryDao;
import com.example.calculator.history.adapter.HistoryAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private MaterialButton btnOrientation;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalculatorDatabase db = CalculatorDatabase.getDatabase(getApplicationContext());
        HistoryDao historyDao = db.historyDao();
        MainViewModelFactory factory = new MainViewModelFactory(historyDao);
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        historyAdapter = new HistoryAdapter(new ArrayList<>(), viewModel);

        ActivityMainBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);
        binding.setHistoryAdapter(historyAdapter);
        binding.setLifecycleOwner(this);

        setObservers();
        setListeners();
    }

    private void setListeners() {
        btnOrientation = findViewById(R.id.btn_orientation);
        btnOrientation.setOnClickListener(v -> {
            int currentOrientation = getResources().getConfiguration().orientation;

            if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });

        viewModel.getHistoryList().observe(this, historyList -> {
            if (historyList != null) {
                historyAdapter.setItems(historyList);
            }
        });
    }

    private void setObservers()  {
        viewModel.getToastMessage().observe(this, message
                -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.fetchHistoryList();
    }
}