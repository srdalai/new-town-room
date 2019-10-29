package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.AmenitiesAdapter;
import com.newtownroom.userapp.models.AmenitiesData;
import com.newtownroom.userapp.models.AmenitiesListData;

import java.util.ArrayList;

public class AmenitiesListing extends AppCompatActivity {

    RecyclerView amenitiesRecycler;
    AmenitiesAdapter amenitiesAdapter;
    ArrayList<AmenitiesData> amenitiesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities_listing);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Amenities Available");

        String amenities = getIntent().getStringExtra("amenities");
        Log.d("TAG", amenities);
        Gson gson = new Gson();
        AmenitiesListData data = gson.fromJson(amenities, AmenitiesListData.class);

        amenitiesList.clear();
        amenitiesList.addAll(data.getAmenitiesData());

        amenitiesRecycler = findViewById(R.id.amenitiesRecycler);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        amenitiesAdapter = new AmenitiesAdapter(this, amenitiesList, true);
        amenitiesRecycler.setLayoutManager(layoutManager);
        amenitiesRecycler.setAdapter(amenitiesAdapter);
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
}
