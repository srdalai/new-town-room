package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.newtownroom.userapp.R;
import com.newtownroom.userapp.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferenceManager = new PreferenceManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferenceManager.isLoggedIn()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, UserAuthentication.class));
                }
                finish();
            }
        }, 1000);
    }
}
