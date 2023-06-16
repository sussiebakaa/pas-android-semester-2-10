package com.example.pas_api;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pas_api.databinding.ActivitySplashBinding;
import com.example.pas_api.login.LoginActivity;
import com.example.pas_api.login.Preference;

import java.util.prefs.Preferences;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    Preference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Toast.makeText(this, "Halo Pak Dwi Semoga Sehat Selalu - Vania, Ayla", Toast.LENGTH_SHORT).show();

        pref = new Preference(this);

        boolean isFirstRun = pref.getFirstRun();
        if (isFirstRun) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
                    finish();
                }
            },1500);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1500);
        }


    }
}
