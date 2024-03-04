package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.calculator.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private MaterialButton btnOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        ActivityMainBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);
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
    }

    private void setObservers()  {
        viewModel.getToastMessage().observe(this, message
                -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
    }
}