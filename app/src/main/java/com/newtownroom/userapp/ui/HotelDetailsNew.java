package com.newtownroom.userapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.adapters.AmenitiesAdapter;
import com.newtownroom.userapp.models.AmenitiesData;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HotelDetailsNew extends AppCompatActivity {

    int[] sampleImages = {R.drawable.hotel_1, R.drawable.hotel_2, R.drawable.hotel_3, R.drawable.hotel_4, R.drawable.hotel_5};
    ArrayList<AmenitiesData> amenitiesList = new ArrayList<>();

    CarouselView carouselView;
    AmenitiesAdapter amenitiesAdapter;
    RecyclerView amenitiesRecycler;
    RelativeLayout dateRelative, guestRelative;
    MaterialButton btnDescMore;
    TextView txtDescription, txtStayTime, txtRoomsGuests;
    LinearLayout detailsLinear;

    int guestNum = 0, roomNum = 0;
    String checkInDate = "", checkOutDate = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
    Calendar checkInCal, checkOutCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details_new);

        carouselView = findViewById(R.id.carouselView);
        amenitiesRecycler = findViewById(R.id.amenitiesRecycler);
        dateRelative = findViewById(R.id.dateRelative);
        guestRelative = findViewById(R.id.guestRelative);
        btnDescMore = findViewById(R.id.btnDescMore);
        txtDescription = findViewById(R.id.txtDescription);
        detailsLinear = findViewById(R.id.detailsLinear);
        txtStayTime = findViewById(R.id.txtStayTime);
        txtRoomsGuests = findViewById(R.id.txtRoomsGuests);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        prepareAmenitiesData();

        amenitiesAdapter = new AmenitiesAdapter(this, amenitiesList);
        amenitiesRecycler.setLayoutManager(layoutManager);
        amenitiesRecycler.setAdapter(amenitiesAdapter);

        btnDescMore.setOnClickListener((view) -> {

        });

        dateRelative.setOnClickListener((view) -> {
            /*Intent intent = new Intent(HotelDetailsNew.this, CheckOutDetails.class);
            startActivityForResult(intent, 400);*/
            showDatePicker("checkin");
        });

        guestRelative.setOnClickListener((view) -> {
            showGuestNumDialog("Number of Rooms", 8, "rooms");
        });


        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.length);

        updateUI();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    public void prepareAmenitiesData() {
        AmenitiesData amenitiesData = new AmenitiesData(R.drawable.ic_action_wifi, "Free Wifi", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_ac_unit, "AC", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_live_tv, "TV", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_power, "Power Backup", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_credit_card, "Card Payment", true);
        amenitiesList.add(amenitiesData);

        amenitiesData = new AmenitiesData(R.drawable.ic_action_videocam, "CCTV Cameras", true);
        amenitiesList.add(amenitiesData);
    }

    private void showDatePicker(String type /*"checkin"/"checkout"*/) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedCal = Calendar.getInstance();
                selectedCal.clear();
                selectedCal.set(year, month, dayOfMonth);
                String setDate = dateFormat.format(selectedCal.getTime());

                if (type.equals("checkin")) {
                    checkInDate = setDate;
                    checkInCal = selectedCal;
                    showDatePicker("checkout");
                } else {
                    checkOutDate = setDate;
                    checkOutCal = selectedCal;
                    updateUI();
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        View titleView = LayoutInflater.from(this).inflate(R.layout.ui_custom_title_layout, null);
        TextView titleText = titleView.findViewById(R.id.titleText);
        if (type.equals("checkin")) {
            titleText.setText("Set Check In Date");
            datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
        } else {
            titleText.setText("Set Check Out Date");
            Calendar instanceCal = Calendar.getInstance();
            instanceCal.setTime(checkInCal.getTime());
            instanceCal.add(Calendar.DATE, 1);
            datePickerDialog.getDatePicker().setMinDate(instanceCal.getTime().getTime());
        }
        datePickerDialog.setCustomTitle(titleView);

        datePickerDialog.show();
    }

    private void showGuestNumDialog(String title, int maxNum, String type /*"guests"/"rooms"*/) {
        ArrayList<String> numberList = new ArrayList<>();
        for (int i = 0; i < maxNum; i++) {
            numberList.add(String.valueOf(i+1));
        }
        String[] numbersChars = numberList.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        int selectedID = -1;
        if (type.equals("guests") && guestNum != 0) {
            selectedID = numberList.indexOf(String.valueOf(guestNum));
        } else if (type.equals("rooms") && roomNum != 0) {
            selectedID = numberList.indexOf(String.valueOf(roomNum));
        }

        builder.setSingleChoiceItems(numbersChars, selectedID, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = numbersChars[which];
                if (type.equals("guests")) {
                    guestNum = Integer.parseInt(value);
                    updateUI();
                    dialog.dismiss();

                } else {
                    roomNum = Integer.parseInt(value);
                    dialog.dismiss();
                    showGuestNumDialog("Number of Guests", 20, "guests");
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateUI() {
        String dateString = "Set Dates";
        if (!checkInDate.equals("") && !checkOutDate.equals("")) {
            dateString = checkInDate + " - " + checkOutDate;
        }

        txtStayTime.setText(dateString);

        String guestRoom = "Select Guests";
        if (guestNum != 0 && roomNum != 0) {
            guestRoom = roomNum + " Room, " + guestNum + " Guest";
        }
        txtRoomsGuests.setText(guestRoom);
    }
}
