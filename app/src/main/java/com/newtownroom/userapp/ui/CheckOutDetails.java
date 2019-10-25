package com.newtownroom.userapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.newtownroom.userapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckOutDetails extends AppCompatActivity {

    CalendarView checkInCalendarView, checkOutCalendarView;
    LinearLayout checkInLinear, checkOutLinear, guestLinear;
    Calendar checkInCal, checkOutCal;
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
    String setDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_details);

        checkInCalendarView = findViewById(R.id.checkInCalendarView);
        checkOutCalendarView = findViewById(R.id.checkOutCalendarView);
        checkInLinear = findViewById(R.id.checkInLinear);
        checkOutLinear = findViewById(R.id.checkOutLinear);
        guestLinear = findViewById(R.id.guestLinear);

        Calendar curCalender = Calendar.getInstance();
        checkInCal = Calendar.getInstance();
        checkOutCal = Calendar.getInstance();

        checkInCalendarView.setMinDate(curCalender.getTime().getTime());
        checkInCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                Calendar selectedCal = Calendar.getInstance();
                selectedCal.clear();
                selectedCal.set(year, month, dayOfMonth);
                setDate = dateFormat.format(selectedCal.getTime());
                Toast.makeText(CheckOutDetails.this, setDate, Toast.LENGTH_SHORT).show();
                checkInCal.clear();
                checkInCal.setTime(selectedCal.getTime());

            }
        });

        checkOutCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedCal = Calendar.getInstance();
                selectedCal.clear();
                selectedCal.set(year, month, dayOfMonth);
                String setNewDate = dateFormat.format(selectedCal.getTime());
                Toast.makeText(CheckOutDetails.this, setNewDate, Toast.LENGTH_SHORT).show();
                checkOutCal.clear();
                checkOutCal.setTime(selectedCal.getTime());
            }
        });


        registerClickListeners();

        checkInLinear.performClick();


    }

    public void setCheckInCalendarView() {
        //checkOutCalendarView.invalidate();
        Calendar instanceCal = Calendar.getInstance();
        //Toast.makeText(this, dateFormat.format(checkInCal.getTime()), Toast.LENGTH_SHORT).show();
        instanceCal.setTime(checkInCal.getTime());
        instanceCal.add(Calendar.DATE, 2);
        checkOutCalendarView.setMinDate(instanceCal.getTime().getTime());
    }

    private void registerClickListeners() {
        checkInLinear.setOnClickListener((view) -> {
            checkInLinear.setSelected(true);
            checkOutLinear.setSelected(false);
            guestLinear.setSelected(false);

            checkInCalendarView.setVisibility(View.VISIBLE);
            checkOutCalendarView.setVisibility(View.GONE);
        });

        checkOutLinear.setOnClickListener((view) -> {
            checkInLinear.setSelected(false);
            checkOutLinear.setSelected(true);
            guestLinear.setSelected(false);

            setCheckInCalendarView();

            checkInCalendarView.setVisibility(View.GONE);
            checkOutCalendarView.setVisibility(View.VISIBLE);
        });

        guestLinear.setOnClickListener((view) -> {
            checkInLinear.setSelected(false);
            checkOutLinear.setSelected(false);
            guestLinear.setSelected(true);

            checkInCalendarView.setVisibility(View.GONE);
            checkOutCalendarView.setVisibility(View.GONE);
        });
    }

}
