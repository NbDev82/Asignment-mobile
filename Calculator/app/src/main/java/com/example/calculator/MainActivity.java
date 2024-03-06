package com.example.calculator;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
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
    private Animation fade;
    private Animation fadeOut;

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

        fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

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

        viewModel.getIsHistoryVisible().observe(this, isHistoryVisible -> {
            if(isHistoryVisible == true){
                ScrollView svHistory = findViewById(R.id.sv_history);
                svHistory.startAnimation(fade);
            }
        });

        viewModel.getIsHistoryLandscapeVisible().observe(this, isHistoryLandscapeVisible -> {
            if(isHistoryLandscapeVisible == true){
                ScrollView svHistory = findViewById(R.id.sv_history);
                svHistory.startAnimation(fade);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.fetchHistoryList();
    }
}