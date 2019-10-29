package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.RoomAdapter;
import com.newtownroom.userapp.models.GuestData;
import com.newtownroom.userapp.models.RoomData;

import java.util.ArrayList;

public class GuestDetails extends AppCompatActivity {

    private static final String TAG = GuestDetails.class.getSimpleName();

    RecyclerView roomRecycler;
    RoomAdapter roomAdapter;
    ArrayList<RoomData> roomDataArrayList = new ArrayList<>();
    ProgressDialog progressDialog;
    ImageView imageViewClose;
    MaterialButton btnApply;
    int maxAdult, maxChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_details);

        roomRecycler = findViewById(R.id.roomRecycler);
        imageViewClose = findViewById(R.id.imageViewClose);
        btnApply = findViewById(R.id.btnApply);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing... Wait...");

        maxAdult = getIntent().getIntExtra("maxAdult", 1);
        maxChild = getIntent().getIntExtra("maxChild", 1);

        if (getIntent().getStringExtra("guest_data") != null) {
            String guest_data = getIntent().getStringExtra("guest_data");
            Gson gson = new Gson();
            roomDataArrayList = gson.fromJson(guest_data, GuestData.class).getGuestData();
        } else {
            roomDataArrayList = new ArrayList<>();
            roomDataArrayList.add(new RoomData(1, 0));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        roomAdapter = new RoomAdapter(this, roomDataArrayList);
        roomRecycler.setLayoutManager(layoutManager);
        roomRecycler.setAdapter(roomAdapter);

        imageViewClose.setOnClickListener((view -> onBackPressed()));

        btnApply.setOnClickListener((view -> {
            prepareRoomData();
        }));
    }

    private void prepareRoomData() {
        for (int i = 0; i < roomDataArrayList.size(); i++) {
            RoomData roomData = roomDataArrayList.get(i);
            //Log.d(TAG, "Room " + i+1 + "\nAdults - " + roomData.getAdults() + "\nChildren - " + roomData.getChildren());
        }

        Gson gson = new Gson();
        GuestData guestData = new GuestData(roomDataArrayList);
        String guest_data = gson.toJson(guestData);
        Intent intent = new Intent();
        intent.putExtra("guest_data", guest_data);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void addRoom() {
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                roomDataArrayList.add(new RoomData(1, 0));
                roomAdapter.notifyItemInserted(roomDataArrayList.size() - 1);
                roomAdapter.notifyDataSetChanged();
                roomRecycler.scrollToPosition(roomDataArrayList.size() - 1);
                progressDialog.dismiss();
            }
        }, 1000);
    }

    public void deleteRoom(int position) {
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                roomDataArrayList.remove(position);
                roomAdapter.notifyItemRemoved(position);
                roomAdapter.notifyDataSetChanged();
                roomRecycler.scrollToPosition(position - 1);
                progressDialog.dismiss();
            }
        }, 1000);
    }

    public void addAdult(int position) {
        int adults = roomDataArrayList.get(position).getAdults();
        if (adults < maxAdult) {
            roomDataArrayList.get(position).setAdults(adults+1);
            roomAdapter.notifyItemChanged(position);
        }
    }

    public void removeAdult(int position) {
        int adults = roomDataArrayList.get(position).getAdults();
        if (adults > 1) {
            roomDataArrayList.get(position).setAdults(adults-1);
            roomAdapter.notifyItemChanged(position);
        }
    }

    public void addChildren(int position) {
        int child = roomDataArrayList.get(position).getChildren();
        child++;
        roomDataArrayList.get(position).setChildren(child);
        roomAdapter.notifyItemChanged(position);
    }

    public void removeChildren(int position) {
        int child = roomDataArrayList.get(position).getChildren();
        if (child > 0) {
            child--;
            roomDataArrayList.get(position).setChildren(child);
            roomAdapter.notifyItemChanged(position);
        }
    }

    public void setChildren(int position) {
        int child = roomDataArrayList.get(position).getChildren();
        if (child == 1) {
            roomDataArrayList.get(position).setChildren(0);
        } else {
            roomDataArrayList.get(position).setChildren(1);
        }
        roomAdapter.notifyItemChanged(position);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                roomAdapter.notifyItemChanged(position);
            }
        }, 500);*/

    }
}
