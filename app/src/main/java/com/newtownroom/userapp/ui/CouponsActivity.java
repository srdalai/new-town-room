package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.CouponsAdapter;

import java.util.ArrayList;

public class CouponsActivity extends AppCompatActivity {

    RecyclerView couponsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Coupons Available");

        couponsRecycler = findViewById(R.id.couponsRecycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        CouponsAdapter couponsAdapter = new CouponsAdapter(this, new ArrayList<>());
        couponsRecycler.setLayoutManager(layoutManager);
        couponsRecycler.setAdapter(couponsAdapter);
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

    public void applyCoupon() {
        Intent intent = new Intent();
        setResult(200, intent);
    }
}
