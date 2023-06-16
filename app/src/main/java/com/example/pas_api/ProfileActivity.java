package com.example.pas_api;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pas_api.databinding.ActivityProfileBinding;
import com.example.pas_api.login.LoginActivity;
import com.example.pas_api.login.Preference;

import java.util.prefs.Preferences;

public class ProfileActivity extends AppCompatActivity {
    Preference preferences;
    ActivityProfileBinding binding;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferences = new Preference(this);
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.setSessionLogin(false);
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });
    }
}
