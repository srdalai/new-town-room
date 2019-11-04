package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.CouponsAdapter;
import com.newtownroom.userapp.models.Coupon;
import com.newtownroom.userapp.rest.GetDataService;
import com.newtownroom.userapp.rest.RetrofitClientInstance;
import com.newtownroom.userapp.restmodels.CouponsInput;
import com.newtownroom.userapp.restmodels.CouponsResponse;
import com.newtownroom.userapp.utils.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsActivity extends AppCompatActivity {

    RecyclerView couponsRecycler;

    GetDataService service;
    ProgressDialog progressDialog;
    ArrayList<Coupon> coupons = new ArrayList<>();
    CouponsAdapter couponsAdapter;
    PreferenceManager preferenceManager;
    String hotelId;
    TextView noDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Coupons Available");

        preferenceManager = new PreferenceManager(this);

        hotelId = getIntent().getStringExtra("hotelId");

        couponsRecycler = findViewById(R.id.couponsRecycler);
        noDataTextView = findViewById(R.id.noDataTextView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        couponsAdapter = new CouponsAdapter(this, coupons);
        couponsRecycler.setLayoutManager(layoutManager);
        couponsRecycler.setAdapter(couponsAdapter);

        makeAPICall();
    }

    public void makeAPICall() {
        progressDialog.show();
        CouponsInput couponsInput = new CouponsInput();
        couponsInput.setHotelId(hotelId);
        couponsInput.setUserId(String.valueOf(preferenceManager.getUserID()));
        couponsInput.setUniqid(preferenceManager.getUniqueID());

        Call<CouponsResponse> call = service.getAllCoupons(couponsInput);

        call.enqueue(new Callback<CouponsResponse>() {
            @Override
            public void onResponse(@NotNull Call<CouponsResponse> call, @NotNull Response<CouponsResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200 && response.body() != null && response.body().getCode() == 200 && response.body().getCoupons().size() > 0) {
                    coupons.clear();
                    coupons.addAll(response.body().getCoupons());
                    couponsAdapter.notifyDataSetChanged();
                } else {
                    noDataTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CouponsResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                noDataTextView.setVisibility(View.VISIBLE);
                Log.d("Error", t.toString());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void applyCoupon(int position) {
        Gson gson = new Gson();
        String couponData = gson.toJson(coupons.get(position));
        Intent intent = new Intent();
        intent.putExtra("couponData", couponData);
        setResult(RESULT_OK, intent);
        finish();
    }
}
