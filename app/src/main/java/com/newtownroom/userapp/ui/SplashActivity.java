package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.newtownroom.userapp.R;
import com.newtownroom.userapp.models.TxnStatusResponse;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.utils.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;
    GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferenceManager = new PreferenceManager(this);

        service = RetrofitClientInstance.getPayURetrofitInstance().create(GetDataService.class);
        //checkLastTxn();

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

    private void checkLastTxn() {

        Map<String, String> map = new HashMap<>();
        map.put("authorization", "zcgdBxpYPBAzz4GOddYCrGRF86/SQDYD0DcPGgcMDmA=");
        map.put("Content-Type", "application/json");
        Call<TxnStatusResponse> call = service.checkTxnStatus(map, "0lMDzMDB", "ORDER-OD-201900001");
        call.enqueue(new Callback<TxnStatusResponse>() {
            @Override
            public void onResponse(Call<TxnStatusResponse> call, Response<TxnStatusResponse> response) {
                Toast.makeText(SplashActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", response.toString());
            }

            @Override
            public void onFailure(Call<TxnStatusResponse> call, Throwable t) {

            }
        });
    }
}
