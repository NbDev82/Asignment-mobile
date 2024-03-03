package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.calculator.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private MaterialButton btnHistory;
    private MaterialButton btnOrientation;
    private MaterialButton btnMenu;
    private MaterialButton btnSprt;
    private MaterialButton btnSprt3;
    private MaterialButton btnRadian;
    private MaterialButton btnDegree;
    private LinearLayout llBtnsV1line2;
    private LinearLayout llBtnsV1line3;
    private LinearLayout llBtnsV1line4;
    private LinearLayout llBtnsV1line5;
    private LinearLayout llBtnsV1line6;
    private LinearLayout llBtnsV1line7;
    private LinearLayout llBtnsV1line8;
    private LinearLayout llBtnsV1line9;
    private LinearLayout llHistory;
    private ScrollView svHistory;
    private LinearLayout llButtonV1;
    private boolean isLayoutACurrentlyVisible = true;
    private boolean isLayoutHistoryVisible = false;
    private Animation fade;
    private Animation fade_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        ActivityMainBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        btnHistory = findViewById(R.id.btn_history);
        btnOrientation = findViewById(R.id.btn_orientation);
        btnMenu = findViewById(R.id.btn_menu);
        btnSprt = findViewById(R.id.btn_sprt);
        btnSprt3 = findViewById(R.id.btn_sprt3);
        btnRadian = findViewById(R.id.btn_radian);
        btnDegree = findViewById(R.id.btn_degree);
        llBtnsV1line2 = findViewById(R.id.ll_buttons_v1_line2);
        llBtnsV1line3 = findViewById(R.id.ll_buttons_v1_line3);
        llBtnsV1line4 = findViewById(R.id.ll_buttons_v1_line4);
        llBtnsV1line5 = findViewById(R.id.ll_buttons_v1_line5);
        llBtnsV1line6 = findViewById(R.id.ll_buttons_v1_line6);
        llBtnsV1line7 = findViewById(R.id.ll_buttons_v1_line7);
        llBtnsV1line8 = findViewById(R.id.ll_buttons_v1_line8);
        llBtnsV1line9 = findViewById(R.id.ll_buttons_v1_line9);
        llHistory = findViewById(R.id.ll_history);
        svHistory = findViewById(R.id.sv_history);
        llButtonV1 = findViewById(R.id.ll_buttons_v1);

        fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLayoutHistoryVisible) {
                    if (llButtonV1 != null) {
                        llButtonV1.setVisibility(View.GONE);
                    }
                    llHistory.setVisibility(View.VISIBLE);
                    svHistory.startAnimation(fade);
                } else {
                    llHistory.startAnimation(fade_out);
                    llHistory.setVisibility(View.GONE);
                    if (llButtonV1 != null) {
                        llButtonV1.setVisibility(View.VISIBLE);
                    }
                }

                isLayoutHistoryVisible = !isLayoutHistoryVisible;
            }
        });


        btnOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentOrientation = getResources().getConfiguration().orientation;

                if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        if (btnMenu != null) {
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reverseMenu();
                }
            });
        }

        if (btnRadian != null) {
            btnRadian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnRadian.setVisibility(View.GONE);
                    btnDegree.setVisibility(View.VISIBLE);
                }
            });
        }

        if (btnDegree != null) {
            btnDegree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnDegree.setVisibility(View.GONE);
                    btnRadian.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void reverseMenu() {
        if (isLayoutACurrentlyVisible) {
            btnSprt.setVisibility(View.VISIBLE);
            llBtnsV1line2.setVisibility(View.VISIBLE);
            llBtnsV1line3.setVisibility(View.VISIBLE);
            llBtnsV1line4.setVisibility(View.VISIBLE);
            llBtnsV1line5.setVisibility(View.VISIBLE);
            btnSprt3.setVisibility(View.GONE);
            llBtnsV1line6.setVisibility(View.GONE);
            llBtnsV1line7.setVisibility(View.GONE);
            llBtnsV1line8.setVisibility(View.GONE);
            llBtnsV1line9.setVisibility(View.GONE);
        } else {
            btnSprt.setVisibility(View.GONE);
            llBtnsV1line2.setVisibility(View.GONE);
            llBtnsV1line3.setVisibility(View.GONE);
            llBtnsV1line4.setVisibility(View.GONE);
            llBtnsV1line5.setVisibility(View.GONE);
            btnSprt3.setVisibility(View.VISIBLE);
            llBtnsV1line6.setVisibility(View.VISIBLE);
            llBtnsV1line7.setVisibility(View.VISIBLE);
            llBtnsV1line8.setVisibility(View.VISIBLE);
            llBtnsV1line9.setVisibility(View.VISIBLE);
        }

        isLayoutACurrentlyVisible = !isLayoutACurrentlyVisible;
    }
}